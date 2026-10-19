output "hostname" {
  description = "The hostname the app is served at, <label>.<zone>"
  value       = "${var.label}.${var.dns_zone_name}"
}

output "default_host_name" {
  description = "The app's own azurestaticapps.net hostname, which the CNAME points at"
  value       = azurerm_static_web_app.ui.default_host_name
}

output "deployment_token" {
  description = "The token the Static Web Apps CLI deploys a build with"
  value       = azurerm_static_web_app.ui.api_key
  sensitive   = true
}
