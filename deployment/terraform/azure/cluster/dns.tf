# ── DNS Records ───────────────────────────────────────────────────────────────
# Each server's names → its LoadBalancer, which carries 443 to the server's gateway (REST,
# STOMP/WebSocket, the login and OIDC callback endpoints): api.<domain> the management server
# and system-api.<domain> the system server, in the platform zone. The records are not proxied:
# the servers terminate TLS with their own certificate, and the WebSocket and STOMP traffic
# reaches them directly. The app server's names, apps-api.<zone> with every application's
# <organizationId>--<applicationId> under it, are in the customers' zone (cloudflare.tf),
# proxied to the same kind of load balancer.

locals {
  server_records = {
    "api"        = "kinotic-server-management"
    "system-api" = "kinotic-server-system"
  }
  server_services = toset(concat(values(local.server_records), ["kinotic-server-app"]))
}

data "kubernetes_service" "server" {
  for_each = local.server_services

  metadata {
    name      = each.key
    namespace = "kinotic"
  }
  depends_on = [helm_release.kinotic]
}

resource "cloudflare_dns_record" "server" {
  for_each = local.server_records

  zone_id = local.global.dns_zone_id
  name    = "${each.key}.${local.global.dns_zone_name}"
  type    = "A"
  content = data.kubernetes_service.server[each.value].status[0].load_balancer[0].ingress[0].ip
  proxied = false
  ttl     = 300
  comment = "The ${each.value} load balancer"
}
