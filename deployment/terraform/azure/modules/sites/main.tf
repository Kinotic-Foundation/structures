# ── The sites storage account ─────────────────────────────────────────────────
# One account for every published site of the environment, sites/<hostname>/..., which the
# system server's publish workloads write through upload URLs it signs and the Cloudflare sites
# Worker (deployment/terraform/azure/modules/cloudflare-sites) reads over the blob endpoint with
# a read-only SAS on the container minted here and bound to the Worker as a secret, so the
# container stays private and nothing reads it but the edge. The platform's own UIs, the portal
# and the console, are uploaded the same way under their hostnames by deploy-ui.sh.

terraform {
  required_providers {
    azurerm = {
      source = "hashicorp/azurerm"
    }
    time = {
      source = "hashicorp/time"
    }
  }
}

locals {
  # Storage account names allow 3 to 24 lowercase letters and digits
  storage_account_name = substr("st${replace(var.name_prefix, "-", "")}sites", 0, 24)
}

resource "azurerm_storage_account" "sites" {
  name                            = local.storage_account_name
  resource_group_name             = var.resource_group_name
  location                        = var.location
  account_kind                    = "StorageV2"
  account_tier                    = "Standard"
  account_replication_type        = "LRS"
  is_hns_enabled                  = true
  min_tls_version                 = "TLS1_2"
  allow_nested_items_to_be_public = false
  tags                            = var.tags
}

resource "azurerm_storage_container" "sites" {
  name                  = "sites"
  storage_account_id    = azurerm_storage_account.sites.id
  container_access_type = "private"
}

# The edge's read on the container: a SAS that reads blobs by name and lists nothing, re-minted
# every year by the rotation below and valid for two, so a yearly apply renews it with a year to
# spare. Minted from the account key, so key access stays enabled on the account.
resource "time_rotating" "sites_read_sas" {
  rotation_years = 1
}

data "azurerm_storage_account_blob_container_sas" "worker_read" {
  connection_string = azurerm_storage_account.sites.primary_connection_string
  container_name    = azurerm_storage_container.sites.name
  https_only        = true
  start             = time_rotating.sites_read_sas.rfc3339
  expiry            = timeadd(time_rotating.sites_read_sas.rfc3339, "17520h")

  permissions {
    read   = true
    add    = false
    create = false
    write  = false
    delete = false
    list   = false
  }
}

resource "azurerm_role_assignment" "server_writes_sites" {
  scope                            = azurerm_storage_account.sites.id
  role_definition_name             = "Storage Blob Data Contributor"
  principal_id                     = var.server_principal_id
  skip_service_principal_aad_check = var.server_principal_skip_aad_check
}
