provider "proxmox" {
  endpoint  = "https://${var.proxmox_host}:8006/"
  api_token = var.proxmox_api_token
  # The installer's certificate is self-signed
  insecure = var.proxmox_insecure

  # Snippets are uploaded over SSH, not the API: the host's root key must be in the agent
  # of whoever runs terraform. The container applier runs over the same SSH.
  ssh {
    agent    = true
    username = "root"
  }
}
