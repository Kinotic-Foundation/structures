# ── The development server's Azure side ──────────────────────────────────────
# What the shared development server (a Proxmox host, deployment/terraform/proxmox) needs from
# Azure and Cloudflare: the dev-environment module — a resource group, the sites storage
# account, the sites Worker and domain <environment>.<cloudflare zone> with the app server's
# hosts under it, a service principal with the roles the servers need — plus four things a
# developer's machine does without: a key vault the servers store secrets in, a storage account
# Elasticsearch snapshots go to, the portal and the system console as Static Web Apps on the
# platform zone, and the DNS records and rights that put the management and system servers'
# APIs on a hostname with a certificate.
#
# State is local, like dev/: one server, applied from one machine. The proxmox root reads
# this root's outputs from that state file.

terraform {
  required_version = ">= 1.9"

  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 4.0"
    }
    azuread = {
      source  = "hashicorp/azuread"
      version = "~> 3.0"
    }
    cloudflare = {
      source  = "cloudflare/cloudflare"
      version = "~> 5.0"
    }
  }
}

provider "azurerm" {
  features {
    resource_group {
      # destroy removes the sites account's and the snapshot account's contents along with the group
      prevent_deletion_if_contains_resources = false
    }
    key_vault {
      purge_soft_delete_on_destroy    = false
      recover_soft_deleted_key_vaults = true
    }
  }
}

provider "azuread" {}

provider "cloudflare" {
  api_token = var.cloudflare_api_token
}

# ── Read global state ─────────────────────────────────────────────────────────

data "terraform_remote_state" "global" {
  backend = "azurerm"
  config = {
    resource_group_name  = "rg-kinotic-tfstate"
    storage_account_name = "stkinotictfstate"
    container_name       = "tfstate"
    key                  = "global/terraform.tfstate"
  }
}

data "azurerm_client_config" "current" {}

locals {
  name_prefix         = "${var.project}-${var.environment}"
  global              = data.terraform_remote_state.global.outputs
  api_hostname        = "${var.api_label}.${local.global.dns_zone_name}"
  system_api_hostname = "${var.system_api_label}.${local.global.dns_zone_name}"
  apps_api_domain     = module.environment.apps_api_domain

  common_tags = {
    environment = var.environment
    project     = var.project
    managed_by  = "terraform"
  }
}

# ── The environment ───────────────────────────────────────────────────────────

module "environment" {
  source = "../modules/dev-environment"

  project     = var.project
  environment = var.environment
  location    = var.location
  tags        = local.common_tags

  email_communication_service_id = local.global.email_communication_service_id

  cloudflare_zone_name  = var.cloudflare_zone_name
  cloudflare_zone_id    = var.cloudflare_zone_id
  cloudflare_account_id = var.cloudflare_account_id
  # the app server's hosts, proxied to the router; the host's certificate must cover them
  app_server_address = var.public_ip
  portal_base_url    = "https://${var.portal_label}.${local.global.dns_zone_name}"
}

# ── The portal and the system console ─────────────────────────────────────────
# The platform's own UIs, on the platform zone as the cluster's portal is (the frontend root)
# and apart from the customers' sites on the Cloudflare zone, so no published site is ever
# same-site with them or with the servers. Each is a Static Web App at its label, which
# deploy-ui.sh deploys with the token this root outputs.

module "portal" {
  source = "../modules/static-web-app"

  name                         = "${local.name_prefix}-portal"
  resource_group_name          = module.environment.resource_group_name
  location                     = var.location
  tags                         = local.common_tags
  label                        = var.portal_label
  dns_zone_name                = local.global.dns_zone_name
  dns_zone_resource_group_name = local.global.resource_group_name
}

module "console" {
  source = "../modules/static-web-app"

  name                         = "${local.name_prefix}-console"
  resource_group_name          = module.environment.resource_group_name
  location                     = var.location
  tags                         = local.common_tags
  label                        = var.console_label
  dns_zone_name                = local.global.dns_zone_name
  dns_zone_resource_group_name = local.global.resource_group_name
}

# ── Key Vault for the servers' secret storage ─────────────────────────────────
# Each server's SecretStorageService keeps every stored secret here (the in-memory backend
# would lose them on restart), and here is where they already are on the day the
# organizations move to the cloud. Secrets Officer: the servers write as well as read.

resource "azurerm_key_vault" "server" {
  name                       = "kv-${local.name_prefix}"
  location                   = var.location
  resource_group_name        = module.environment.resource_group_name
  tenant_id                  = data.azurerm_client_config.current.tenant_id
  sku_name                   = "standard"
  soft_delete_retention_days = 7
  rbac_authorization_enabled = true
  tags                       = local.common_tags
}

resource "azurerm_role_assignment" "server_key_vault_secrets" {
  scope                            = azurerm_key_vault.server.id
  role_definition_name             = "Key Vault Secrets Officer"
  principal_id                     = module.environment.server_principal_object_id
  skip_service_principal_aad_check = true
}

# The operator places the social sign-in providers' client secrets, which the seeded
# kinotic_org_signup_oidc_configuration rows name by secretNameRef
resource "azurerm_role_assignment" "operator_key_vault_secrets" {
  scope                = azurerm_key_vault.server.id
  role_definition_name = "Key Vault Secrets Officer"
  principal_id         = data.azurerm_client_config.current.object_id
}

# ── Storage for Elasticsearch snapshots ───────────────────────────────────────
# The nightly snapshot repository, and the vehicle the data moves to the cloud in. The
# cluster authenticates with the account key from its keystore, so no role is needed.

resource "azurerm_storage_account" "snapshots" {
  name                     = "st${var.project}${var.environment}snapshots"
  resource_group_name      = module.environment.resource_group_name
  location                 = var.location
  account_tier             = "Standard"
  account_replication_type = "LRS"
  min_tls_version          = "TLS1_2"
  tags                     = local.common_tags
}

resource "azurerm_storage_container" "snapshots" {
  name                  = "elasticsearch-snapshots"
  storage_account_id    = azurerm_storage_account.snapshots.id
  container_access_type = "private"
}

# ── The servers' hostnames ────────────────────────────────────────────────────
# An A record on the router's address for each name the servers answer to in the platform zone —
# the management server's and the system server's; the app server's are in the Cloudflare zone
# (the dev-environment module) — and DNS Zone Contributor so certbot on the host answers the
# DNS-01 challenges, and kinotic-dyndns keeps the records on the router's current address, as
# the servers' principal.

locals {
  public_labels = {
    api        = var.api_label
    system_api = var.system_api_label
  }
}

resource "azurerm_dns_a_record" "public" {
  for_each = local.public_labels

  name                = each.value
  zone_name           = local.global.dns_zone_name
  resource_group_name = local.global.resource_group_name
  ttl                 = 300
  records             = [var.public_ip]
  tags                = local.common_tags

  # The host's updater owns the value once the record exists
  lifecycle {
    ignore_changes = [records]
  }
}

moved {
  from = azurerm_dns_a_record.api
  to   = azurerm_dns_a_record.public["api"]
}

resource "azurerm_role_assignment" "server_dns" {
  scope                            = local.global.dns_zone_id
  role_definition_name             = "DNS Zone Contributor"
  principal_id                     = module.environment.server_principal_object_id
  skip_service_principal_aad_check = true
}
