# ── cert-manager ──────────────────────────────────────────────────────────────
# Answers Let's Encrypt's DNS-01 challenges for the servers' names in both zones, the platform
# zone and the customers' zone, through the Cloudflare DNS token (cloudflare.tf).

resource "kubernetes_namespace" "cert_manager" {
  metadata {
    name   = "cert-manager"
    labels = { "app.kubernetes.io/managed-by" = "terraform" }
  }
  depends_on = [module.aks]
}

resource "helm_release" "cert_manager" {
  name       = "cert-manager"
  repository = "https://charts.jetstack.io"
  chart      = "cert-manager"
  version    = "v1.16.3"
  namespace  = kubernetes_namespace.cert_manager.metadata[0].name

  set = [
    { name = "installCRDs", value = "true" },
  ]

  wait    = true
  timeout = 300

  depends_on = [
    kubernetes_namespace.cert_manager,
    module.aks,
  ]
}

# ── Let's Encrypt ClusterIssuer + Certificate ─────────────────────────────────
# These are cert-manager CRDs — kubernetes_manifest tries to validate at plan
# time before the cluster exists. Using kubectl apply instead.

locals {
  # The names the servers' certificate covers: the platform zone and the hosts under it, and in the
  # customers' zone the app server's shared host and every application's API host under it, which
  # Cloudflare's Full (strict) mode validates when it proxies them
  tls_dns_names = [
    local.global.dns_zone_name,
    "*.${local.global.dns_zone_name}",
    local.apps_api_domain,
    "*.${local.apps_api_domain}",
  ]
}

resource "terraform_data" "cert_manager_issuer_and_cert" {
  # A create-time provisioner runs again only when the resource is replaced, so a change to the
  # names re-applies the Certificate; cert-manager then reissues it, and Reloader restarts the
  # servers onto the new secret
  triggers_replace = local.tls_dns_names

  provisioner "local-exec" {
    command = <<-EOT
      set -e

      # Refresh credentials and wait for API server to be reachable
      echo "Refreshing AKS credentials..."
      az aks get-credentials \
        --resource-group "${azurerm_resource_group.main.name}" \
        --name "${module.aks.cluster_name}" \
        --overwrite-existing 2>/dev/null
      kubelogin convert-kubeconfig -l azurecli 2>/dev/null

      echo "Waiting for Kubernetes API server..."
      for i in $(seq 1 30); do
        if kubectl get nodes >/dev/null 2>&1; then
          echo "API server is reachable"
          break
        fi
        echo "  Attempt $i/30 — waiting 10s..."
        sleep 10
      done

      echo "Creating Let's Encrypt ClusterIssuer..."
      kubectl apply -f - <<YAML
      apiVersion: cert-manager.io/v1
      kind: ClusterIssuer
      metadata:
        name: letsencrypt-prod
      spec:
        acme:
          server: https://acme-v02.api.letsencrypt.org/directory
          email: ${var.lets_encrypt_email}
          privateKeySecretRef:
            name: letsencrypt-prod-account-key
          solvers:
            - dns01:
                cloudflare:
                  apiTokenSecretRef:
                    name: ${kubernetes_secret.cloudflare_dns_api_token.metadata[0].name}
                    key: api-token
      YAML

      echo "Creating TLS Certificate..."
      kubectl apply -f - <<YAML
      apiVersion: cert-manager.io/v1
      kind: Certificate
      metadata:
        name: ${var.tls_secret_name}
        namespace: kinotic
      spec:
        secretName: ${var.tls_secret_name}
        issuerRef:
          name: letsencrypt-prod
          kind: ClusterIssuer
        dnsNames: ${jsonencode(local.tls_dns_names)}
      YAML

      echo "ClusterIssuer and Certificate created"
    EOT
  }

  depends_on = [
    helm_release.cert_manager,
    kubernetes_namespace.kinotic,
    kubernetes_secret.cloudflare_dns_api_token,
  ]
}

# ── Wait for TLS cert to be issued ───────────────────────────────────────────
# cert-manager issues the cert asynchronously after the Certificate resource
# is created. On first deploy, this requires DNS propagation (the registrar's
# NS records pointing at Cloudflare). This resource blocks until the TLS secret
# exists with data, ensuring the servers only start after TLS is ready.

resource "terraform_data" "tls_cert_ready" {
  provisioner "local-exec" {
    command = <<-EOT
      set -e
      echo "Waiting for TLS certificate to be issued..."
      echo "If this is the first deploy, ensure NS records for ${local.global.dns_zone_name} point to Cloudflare."
      echo "Check: terraform output dns_nameservers"
      for i in $(seq 1 120); do
        DATA=$(kubectl get secret ${var.tls_secret_name} -n kinotic \
          -o jsonpath='{.data.tls\.crt}' 2>/dev/null || true)
        if [ -n "$DATA" ]; then
          echo "TLS certificate is ready"
          exit 0
        fi
        echo "  Attempt $i/120 — cert not issued yet, waiting 30s..."
        sleep 30
      done
      echo "ERROR: Timed out waiting for TLS certificate (60 min)."
      echo "Check: kubectl describe certificate ${var.tls_secret_name} -n kinotic"
      exit 1
    EOT
  }

  depends_on = [terraform_data.cert_manager_issuer_and_cert]
}

# ── Reloader ──────────────────────────────────────────────────────────────────
# Watches Kubernetes Secrets and ConfigMaps, triggers rolling restarts
# on Deployments that reference them via annotations.
# When cert-manager renews the TLS cert, Reloader restarts the servers
# so Vert.x picks up the new cert/key files.

resource "helm_release" "reloader" {
  name       = "reloader"
  repository = "https://stakater.github.io/stakater-charts"
  chart      = "reloader"
  version    = "1.2.1"
  namespace  = "kube-system"

  set = [
    { name = "reloader.watchGlobally", value = "true" },
  ]

  wait = true

  depends_on = [module.aks]
}
