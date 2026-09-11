variable "environment" {
  description = "Names everything this root creates and the apps-<environment> sites domain; must differ from every developer's dev/ environment"
  type        = string
  default     = "dev"
}

variable "project" {
  description = "Project name"
  type        = string
  default     = "kinotic"
}

variable "location" {
  description = "Azure region"
  type        = string
  default     = "centralus"
}

variable "hostname_label" {
  description = "The server's label in the platform zone: dev for dev.kinotic.ai"
  type        = string
  default     = "dev"
}

variable "public_ip" {
  description = "The public IPv4 address the router forwards 443 and 58503 from; set it in local.auto.tfvars and re-apply when it changes"
  type        = string
}

variable "lets_encrypt_email" {
  description = "Account email for the Let's Encrypt registration that issues the sites wildcard certificate; set it in local.auto.tfvars"
  type        = string
}
