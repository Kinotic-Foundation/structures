# ── Proxmox ───────────────────────────────────────────────────────────────────

variable "proxmox_endpoint" {
  description = "The Proxmox API, e.g. https://192.168.1.10:8006/"
  type        = string
}

variable "proxmox_api_token" {
  description = "An API token of a user allowed to manage VMs and storage, as user@realm!name=uuid; set it in local.auto.tfvars or PROXMOX_VE_API_TOKEN"
  type        = string
  sensitive   = true
}

variable "proxmox_insecure" {
  description = "Skip verification of the API's TLS certificate, which the installer self-signs"
  type        = bool
  default     = true
}

variable "proxmox_ssh_username" {
  description = "The user snippets are uploaded as, over SSH with the agent's key"
  type        = string
  default     = "root"
}

variable "proxmox_node" {
  description = "The node both VMs run on"
  type        = string
  default     = "pve"
}

variable "vm_datastore_id" {
  description = "Datastore the VMs' OS disks and cloud-init drives are created on"
  type        = string
  default     = "local-zfs"
}

variable "iso_datastore_id" {
  description = "Datastore the Ubuntu cloud images are downloaded to (content type iso)"
  type        = string
  default     = "local"
}

variable "snippets_datastore_id" {
  description = "Datastore the cloud-init files are uploaded to; it must allow the snippets content type"
  type        = string
  default     = "local"
}

# ── Network ───────────────────────────────────────────────────────────────────

variable "bridge" {
  description = "The bridge both VMs attach to"
  type        = string
  default     = "vmbr0"
}

variable "platform_ip" {
  description = "The platform VM's LAN address in CIDR notation, e.g. 192.168.1.20/24; the router forwards 443 and 58503 here"
  type        = string
}

variable "node_ip" {
  description = "The node VM's LAN address in CIDR notation, e.g. 192.168.1.21/24"
  type        = string
}

variable "gateway" {
  description = "The LAN's default gateway"
  type        = string
}

variable "dns_servers" {
  description = "Resolvers for both VMs, and the resolver every workload is given"
  type        = list(string)
}

# ── Disks ─────────────────────────────────────────────────────────────────────
# Whole physical disks passed through to the VMs, by stable id (ls -l /dev/disk/by-id on the
# host). Each Elasticsearch node's disk is its own failure domain; the node's disk holds the
# workload runtime and checkouts.

variable "es_disks" {
  description = "The three Elasticsearch disks, in order es-1, es-2, es-3"
  type = list(object({
    device  = string
    size_gb = number
  }))
  validation {
    condition     = length(var.es_disks) == 3
    error_message = "Three Elasticsearch disks, one per node: quorum survives one disk only with three master-eligible nodes."
  }
}

variable "node_disk" {
  description = "The node VM's disk: Docker's data root and the workload checkouts, on XFS with project quotas"
  type = object({
    device  = string
    size_gb = number
  })
}

# ── Sizing ────────────────────────────────────────────────────────────────────

variable "platform_cores" {
  type    = number
  default = 12
}

variable "platform_memory_mb" {
  type    = number
  default = 32768
}

variable "platform_os_disk_gb" {
  type    = number
  default = 64
}

variable "node_cores" {
  type    = number
  default = 8
}

variable "node_memory_mb" {
  type    = number
  default = 40960
}

variable "node_os_disk_gb" {
  type    = number
  default = 32
}

# ── Guests ────────────────────────────────────────────────────────────────────

variable "ssh_public_key" {
  description = "Authorized for the kinotic user on both VMs"
  type        = string
}

variable "platform_image_url" {
  description = "Ubuntu cloud image for the platform VM"
  type        = string
  default     = "https://cloud-images.ubuntu.com/noble/current/noble-server-cloudimg-amd64.img"
}

variable "node_image_url" {
  description = "Ubuntu cloud image for the node VM; 22.04 is what the node kit is verified on"
  type        = string
  default     = "https://cloud-images.ubuntu.com/jammy/current/jammy-server-cloudimg-amd64.img"
}

variable "vm_manager_version" {
  description = "The @kinotic-ai/vm-manager release the node installs"
  type        = string
  default     = "latest"
}

variable "node_id" {
  description = "The node's id in the orchestrator"
  type        = string
  default     = "dev-node-1"
}

variable "azure_state_path" {
  description = "The dev-server Azure root's state file, whose outputs configure the server"
  type        = string
  default     = "../azure/dev-server/terraform.tfstate"
}
