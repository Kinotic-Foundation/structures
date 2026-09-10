provider "proxmox" {
  endpoint  = var.proxmox_endpoint
  api_token = var.proxmox_api_token
  # The installer's certificate is self-signed
  insecure = var.proxmox_insecure

  # Cloud-init snippets are uploaded over SSH, not the API: the node's root key must be in
  # the agent of whoever runs terraform
  ssh {
    agent    = true
    username = var.proxmox_ssh_username
  }
}
