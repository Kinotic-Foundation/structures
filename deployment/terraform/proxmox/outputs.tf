output "platform_ip" {
  description = "The platform VM: ssh kinotic@<ip>, the gateway on :58503, Grafana on :3000"
  value       = local.platform_ip
}

output "node_ip" {
  description = "The node VM: ssh kinotic@<ip>"
  value       = local.node_ip
}

output "hostname" {
  description = "The hostname the router forwards to the platform VM"
  value       = local.azure.hostname
}
