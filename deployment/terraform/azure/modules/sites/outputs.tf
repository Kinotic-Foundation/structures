output "storage_blob_endpoint" {
  description = "The blob endpoint the system server publishes sites into and the sites Worker serves from"
  value       = azurerm_storage_account.sites.primary_blob_endpoint
}

output "storage_account_id" {
  description = "The sites storage account, for the role the operator uploads the platform's UIs with"
  value       = azurerm_storage_account.sites.id
}

output "sites_read_sas" {
  description = "The query string of the read-only SAS on the sites container, for the edge's reads; bound to the sites Worker as a secret"
  value       = trimprefix(data.azurerm_storage_account_blob_container_sas.worker_read.sas, "?")
  sensitive   = true
}
