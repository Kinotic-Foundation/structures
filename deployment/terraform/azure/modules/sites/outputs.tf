output "sites_domain" {
  description = "The domain every published UI is a label under"
  value       = local.sites_domain
}

output "profile_id" {
  value = azurerm_cdn_frontdoor_profile.sites.id
}

output "profile_principal_id" {
  description = "The profile's managed identity, which reads every site's files"
  value       = azurerm_cdn_frontdoor_profile.sites.identity[0].principal_id
}

output "endpoint_host_name" {
  description = "The Front Door endpoint the wildcard record points at"
  value       = azurerm_cdn_frontdoor_endpoint.sites.host_name
}

output "storage_account_id" {
  value = azurerm_storage_account.sites.id
}

output "storage_blob_endpoint" {
  description = "The blob endpoint kinotic-server publishes sites into"
  value       = azurerm_storage_account.sites.primary_blob_endpoint
}
