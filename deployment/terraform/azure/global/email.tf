# ── Azure Communication Services (Email) ──────────────────────────────────────
# Fully automated: creates ACS, email service, custom domain, all DNS
# verification records (domain, SPF, DKIM, DKIM2), and triggers verification.
# No manual steps after terraform apply.
#
# Lives in global because the email service is shared across all clusters and
# bound to the global DNS zone. Per-cluster RBAC (granting the servers'
# identity send permission) lives in the cluster terraform.

resource "azurerm_email_communication_service" "main" {
  name                = "ecs-${var.project}"
  resource_group_name = azurerm_resource_group.global.name
  data_location       = "United States"
  tags                = local.common_tags
}

resource "azurerm_email_communication_service_domain" "kinotic" {
  name              = var.domain_name
  email_service_id  = azurerm_email_communication_service.main.id
  domain_management = "CustomerManaged"
  tags              = local.common_tags
}

resource "azurerm_communication_service" "main" {
  name                = "acs-${var.project}"
  resource_group_name = azurerm_resource_group.global.name
  data_location       = "United States"
  tags                = local.common_tags
}

# Link email domain to communication service. The azurerm provider does not
# expose linkedDomains, so we patch via az CLI. `az communication email domain
# link` does not exist in CLI; the property lives on the communication service
# itself and is set via `az communication update --linked-domains`.
resource "terraform_data" "link_email_domain" {
  triggers_replace = [
    azurerm_communication_service.main.id,
    azurerm_email_communication_service_domain.kinotic.id,
  ]

  provisioner "local-exec" {
    command = <<-EOT
      set -euo pipefail
      az communication update \
        --name "acs-${var.project}" \
        --resource-group "${azurerm_resource_group.global.name}" \
        --linked-domains "${azurerm_email_communication_service_domain.kinotic.id}"
    EOT
  }

  depends_on = [
    azurerm_communication_service.main,
    azurerm_email_communication_service_domain.kinotic,
  ]
}

# ── DNS records (from the verification_records output) ────────────────────────
# ACS names the apex record by the zone itself and the DKIM records by their label within the
# zone; Cloudflare takes every name in full. The three apex TXT values are records of their own.

locals {
  email_records = azurerm_email_communication_service_domain.kinotic.verification_records[0]
  email_domain_record_name = (
    local.email_records.domain[0].name == var.domain_name
    ? var.domain_name
    : "${local.email_records.domain[0].name}.${var.domain_name}"
  )
}

# The Azure zone's records stay with it until it is deleted by hand
removed {
  from = azurerm_dns_txt_record.apex

  lifecycle {
    destroy = false
  }
}

removed {
  from = azurerm_dns_cname_record.email_dkim

  lifecycle {
    destroy = false
  }
}

removed {
  from = azurerm_dns_cname_record.email_dkim2

  lifecycle {
    destroy = false
  }
}

resource "cloudflare_dns_record" "email_domain" {
  zone_id = cloudflare_zone.main.id
  name    = local.email_domain_record_name
  type    = "TXT"
  content = local.email_records.domain[0].value
  ttl     = local.email_records.domain[0].ttl
  comment = "Azure Communication Services domain verification"
}

resource "cloudflare_dns_record" "email_spf" {
  zone_id = cloudflare_zone.main.id
  name    = var.domain_name
  type    = "TXT"
  content = local.email_records.spf[0].value
  ttl     = local.email_records.spf[0].ttl
  comment = "SPF for the email service"
}

resource "cloudflare_dns_record" "google_site_verification" {
  zone_id = cloudflare_zone.main.id
  name    = var.domain_name
  type    = "TXT"
  content = "google-site-verification=BC6-VIXGtc4Bvqkplz3FEulgnS25f31hfwVcD4aQG5U"
  ttl     = 3600
}

resource "cloudflare_dns_record" "email_dkim" {
  zone_id = cloudflare_zone.main.id
  name    = "${local.email_records.dkim[0].name}.${var.domain_name}"
  type    = "CNAME"
  content = local.email_records.dkim[0].value
  ttl     = local.email_records.dkim[0].ttl
  proxied = false
  comment = "DKIM for the email service"
}

resource "cloudflare_dns_record" "email_dkim2" {
  zone_id = cloudflare_zone.main.id
  name    = "${local.email_records.dkim2[0].name}.${var.domain_name}"
  type    = "CNAME"
  content = local.email_records.dkim2[0].value
  ttl     = local.email_records.dkim2[0].ttl
  proxied = false
  comment = "DKIM for the email service"
}

# ── Trigger domain verification ───────────────────────────────────────────────

resource "terraform_data" "verify_email_domain" {
  provisioner "local-exec" {
    command = <<-EOT
      set -e
      DOMAIN_ID="${azurerm_email_communication_service_domain.kinotic.id}"

      echo "Initiating domain verification..."
      for RECORD_TYPE in Domain SPF DKIM DKIM2; do
        echo "  Verifying $RECORD_TYPE..."
        az rest --method POST \
          --url "$DOMAIN_ID/initiateVerification?api-version=2023-04-01" \
          --body "{\"verificationType\": \"$RECORD_TYPE\"}" 2>/dev/null || true
        sleep 5
      done

      echo "Waiting for verification..."
      for i in $(seq 1 30); do
        STATUS=$(az communication email domain show \
          --name "${var.domain_name}" \
          --email-service-name "ecs-${var.project}" \
          --resource-group "${azurerm_resource_group.global.name}" \
          --query "verificationStates.domain.status" -o tsv 2>/dev/null || true)
        if [ "$STATUS" == "Verified" ]; then
          echo "Domain verified!"
          exit 0
        fi
        echo "  Attempt $i/30 — status: $STATUS, waiting 10s..."
        sleep 10
      done
      echo "WARNING: Verification not yet complete. Check: terraform output email_verification_status"
    EOT
  }

  depends_on = [
    cloudflare_dns_record.email_domain,
    cloudflare_dns_record.email_spf,
    cloudflare_dns_record.email_dkim,
    cloudflare_dns_record.email_dkim2,
  ]
}

# ── Outputs ───────────────────────────────────────────────────────────────────

output "email_communication_service_id" {
  value = azurerm_communication_service.main.id
}

output "email_service_endpoint" {
  value = "https://acs-${var.project}.unitedstates.communication.azure.com"
}

output "email_sender_domain" {
  value = var.domain_name
}

output "email_verification_status" {
  value = "az communication email domain show --name ${var.domain_name} --email-service-name ecs-${var.project} --resource-group ${azurerm_resource_group.global.name} --query verificationStates"
}
