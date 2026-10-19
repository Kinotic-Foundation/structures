variable "project" {
  description = "Project name, the first half of every resource name"
  type        = string
}

variable "environment" {
  description = "Names everything this module creates and the <environment>.<cloudflare zone> sites domain; unique across every developer and server, since each takes that label of the zone and names its Worker after it"
  type        = string
}

variable "location" {
  description = "Azure region"
  type        = string
}

variable "tags" {
  description = "Tags applied to every resource"
  type        = map(string)
  default     = {}
}

variable "email_communication_service_id" {
  description = "Resource id of the shared email communication service the server sends through (global/ output email_communication_service_id)"
  type        = string
}

variable "ui_labels" {
  description = "The platform's own UIs the sites Worker serves from the sites account, by the label under the sites domain the Worker reserves for each: [\"portal\", \"console\"] on the development server, none on a developer's machine"
  type        = set(string)
  default     = []
}

variable "cloudflare_zone_name" {
  description = "The Cloudflare zone the environment's sites domain is a label under, e.g. kinotic.io"
  type        = string
}

variable "cloudflare_zone_id" {
  type = string
}

variable "cloudflare_account_id" {
  description = "The Cloudflare account the sites Worker is deployed in"
  type        = string
}

variable "app_server_address" {
  description = "The public IPv4 address the environment's app server is reached at, for the apps-api records, or null on a developer machine with no reachable app server"
  type        = string
  default     = null
}

variable "portal_base_url" {
  description = "Where an organization's own host sends the browser"
  type        = string
}
