# ── A platform UI on a Static Web App ────────────────────────────────────────
# One of the platform's own single-page applications, the portal or the system console, hosted
# on an Azure Static Web App at a label of the platform zone: the app, the CNAME that points the
# label at it, and the custom domain, whose certificate Static Web Apps issues and renews. The
# platform's UIs live on this zone and never on the customers', so no published site is ever
# same-site with them or with the servers. A build is deployed with the Static Web Apps CLI and
# the app's deployment token: deploy.sh in the frontend root, deploy-ui.sh in the dev-server root.

terraform {
  required_providers {
    azurerm = {
      source = "hashicorp/azurerm"
    }
  }
}

resource "azurerm_static_web_app" "ui" {
  name                = var.name
  resource_group_name = var.resource_group_name
  location            = var.location
  sku_tier            = "Free"
  sku_size            = "Free"
  tags                = var.tags
}

resource "azurerm_dns_cname_record" "ui" {
  name                = var.label
  zone_name           = var.dns_zone_name
  resource_group_name = var.dns_zone_resource_group_name
  ttl                 = 300
  record              = azurerm_static_web_app.ui.default_host_name
}

# Static Web Apps validates the domain through the CNAME, so the record exists first
resource "azurerm_static_web_app_custom_domain" "ui" {
  static_web_app_id = azurerm_static_web_app.ui.id
  domain_name       = "${var.label}.${var.dns_zone_name}"
  validation_type   = "cname-delegation"

  depends_on = [azurerm_dns_cname_record.ui]
}
