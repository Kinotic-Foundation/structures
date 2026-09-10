output "proxmox_host" {
  description = "The host, for sync-secrets.sh and ssh"
  value       = var.proxmox_host
}

output "hostname" {
  description = "The hostname peers use; the router forwards its 443 and 58503 to server_ip"
  value       = local.azure.hostname
}

output "server_ip" {
  description = "kinotic-server's LAN address: 443 → 9090 and 58503 → 58503 at the router"
  value       = local.server_ip
}

output "grafana_url" {
  description = "Grafana, on the LAN, admin with the password in grafana.env"
  value       = "http://${local.grafana_ip}:3000"
}

output "node_ip" {
  description = "The node VM: ssh kinotic@<ip>"
  value       = local.node_ip
}

output "containers" {
  description = "Each container's vmid, for pct"
  value       = { for name, c in local.containers : name => c.vm_id }
}
