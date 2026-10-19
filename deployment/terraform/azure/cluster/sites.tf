# ── UI sites ─────────────────────────────────────────────────────────────────
# Every published UI is served at <app>--<ui>.<org>.<cloudflare zone> by the sites Worker from
# the sites storage account (cloudflare.tf). The sites module owns the account and nothing
# changes in storage when a UI is published; the platform's own UIs are hosted outside the
# cluster (the frontend root), so the Worker serves none here.

module "sites" {
  source = "../modules/sites"

  name_prefix         = local.name_prefix
  location            = var.location
  resource_group_name = azurerm_resource_group.main.name
  tags                = local.common_tags
  server_principal_id = azurerm_user_assigned_identity.kinotic_server.principal_id
}

output "sites_storage_blob_endpoint" {
  description = "The blob endpoint the system server publishes sites into"
  value       = module.sites.storage_blob_endpoint
}
