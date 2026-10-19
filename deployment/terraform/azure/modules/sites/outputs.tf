output "storage_blob_endpoint" {
  description = "The blob endpoint the system server publishes sites into and the sites Worker serves from"
  value       = azurerm_storage_account.sites.primary_blob_endpoint
}

output "storage_account_id" {
  description = "The sites storage account, for roles a caller adds"
  value       = azurerm_storage_account.sites.id
}

output "hostnames" {
  description = "The named hostnames served from the account, by the caller's key"
  value       = { for key, domain in azurerm_cdn_frontdoor_custom_domain.named : key => domain.host_name }
}

output "frontdoor_profile_name" {
  description = "The Front Door profile serving the named hostnames, for a cache purge; null without hostnames"
  value       = local.front_door == 1 ? azurerm_cdn_frontdoor_profile.sites[0].name : null
}

output "frontdoor_endpoint_name" {
  description = "Its endpoint, for a cache purge; null without hostnames"
  value       = local.front_door == 1 ? azurerm_cdn_frontdoor_endpoint.sites[0].name : null
}

output "sites_read_sas" {
  description = "The query string of the read-only SAS on the sites container, for the edge's reads; bound to the sites Worker as a secret"
  value       = trimprefix(data.azurerm_storage_account_blob_container_sas.worker_read.sas, "?")
  sensitive   = true
}
