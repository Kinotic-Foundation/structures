terraform {
  required_version = ">= 1.9"

  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 4.0"
    }
  }

  backend "azurerm" {
    resource_group_name  = "rg-kinotic-tfstate"
    storage_account_name = "stkinotictfstate"
    container_name       = "tfstate"
    key                  = "frontend/terraform.tfstate"
  }
}

provider "azurerm" {
  features {}
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

locals {
  global = data.terraform_remote_state.global.outputs
}

# ── The portal ────────────────────────────────────────────────────────────────
# A Static Web App at portal.<zone> with its CNAME and managed certificate, deployed by deploy.sh

module "portal" {
  source = "../modules/static-web-app"

  name                         = "kinotic-portal-${var.environment}"
  resource_group_name          = local.global.resource_group_name
  location                     = var.location
  label                        = var.subdomain
  dns_zone_name                = local.global.dns_zone_name
  dns_zone_resource_group_name = local.global.resource_group_name

  tags = {
    project     = "kinotic"
    environment = var.environment
    managed_by  = "terraform"
  }
}

moved {
  from = azurerm_static_web_app.portal
  to   = module.portal.azurerm_static_web_app.ui
}

moved {
  from = azurerm_dns_cname_record.portal
  to   = module.portal.azurerm_dns_cname_record.ui
}

moved {
  from = azurerm_static_web_app_custom_domain.portal
  to   = module.portal.azurerm_static_web_app_custom_domain.ui
}

# ── Variables ─────────────────────────────────────────────────────────────────

variable "environment" {
  description = "Environment name (production, dev, qa)"
  type        = string
  default     = "production"
}

variable "location" {
  description = "Azure region"
  type        = string
  default     = "centralus"
}

variable "subdomain" {
  description = "Subdomain for the portal (e.g. portal, portal-dev)"
  type        = string
  default     = "portal"
}

variable "frontend_path" {
  description = "Path to the built frontend dist/ folder"
  type        = string
  default     = "../../../../kinotic-frontend/apps/portal/dist"
}

# ── Outputs ───────────────────────────────────────────────────────────────────

output "portal_url" {
  value = "https://${module.portal.hostname}"
}

output "default_hostname" {
  value = module.portal.default_host_name
}

output "deployment_token" {
  description = "The token deploy.sh deploys the build with, through the Static Web Apps CLI"
  value       = module.portal.deployment_token
  sensitive   = true
}
