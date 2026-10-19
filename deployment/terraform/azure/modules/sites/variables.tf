variable "name_prefix" {
  description = "Prefix every resource is named under, e.g. kinotic-production"
  type        = string
}

variable "location" {
  description = "Azure region of the storage account"
  type        = string
}

variable "resource_group_name" {
  description = "Resource group holding the sites storage account"
  type        = string
}

variable "tags" {
  type    = map(string)
  default = {}
}

variable "server_principal_id" {
  description = "Principal id the Kinotic servers run as; the system server publishes sites into the storage account"
  type        = string
}

variable "server_principal_skip_aad_check" {
  description = "True for a service principal created in the same apply, which may not have replicated to the RBAC lookup yet"
  type        = bool
  default     = false
}
