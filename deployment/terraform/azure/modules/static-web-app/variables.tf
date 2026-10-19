variable "name" {
  description = "The Static Web App's resource name, e.g. kinotic-portal-production"
  type        = string
}

variable "resource_group_name" {
  description = "Resource group the app is created in"
  type        = string
}

variable "location" {
  description = "Azure region, one Static Web Apps is offered in"
  type        = string
}

variable "tags" {
  type    = map(string)
  default = {}
}

variable "label" {
  description = "The app's label in the platform zone, e.g. portal for portal.kinotic.ai"
  type        = string
}

variable "dns_zone_name" {
  description = "The platform zone the app is reached under, e.g. kinotic.ai (global/ output dns_zone_name)"
  type        = string
}

variable "dns_zone_resource_group_name" {
  description = "Resource group of the platform zone (global/ output resource_group_name)"
  type        = string
}
