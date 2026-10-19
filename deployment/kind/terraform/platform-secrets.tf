# ── Platform secrets (KinD) ───────────────────────────────
# Generates each server's JWT signing keys, the secret-storage master key, the GitHub App's
# secrets and the Turnstile test site's secret as a K8s Secret the servers mount as a volume, each only its own key set. Holds the
# objects the Azure Key Vault holds, under the same names, so the helm chart's pod spec is
# identical across environments — only the source of the files differs.

locals {
  servers = ["kinotic-server-management", "kinotic-server-system", "kinotic-server-app"]
}

# random_bytes marks the keys sensitive, so plan, apply and state show never print them
resource "random_bytes" "jwt_signing_key_v1" {
  for_each = toset(local.servers)
  length   = 32
}

resource "random_bytes" "secret_storage_master_key" {
  length = 32
}

locals {
  jwt_signing_keys_json = { for server in local.servers : server => jsonencode({
    activeKeyId = "v1"
    keys = [
      { id = "v1", key = random_bytes.jwt_signing_key_v1[server].base64 },
    ]
  }) }
}

resource "kubernetes_secret" "platform_secrets" {
  metadata {
    name      = "kinotic-platform-secrets"
    namespace = kubernetes_namespace.kinotic.metadata[0].name
    labels    = { "app.kubernetes.io/managed-by" = "terraform" }
  }

  type = "Opaque"

  data = merge(
    { for server, json in local.jwt_signing_keys_json : "${server}-jwt-signing-keys" => json },
    {
      # SecretNameDeriver derives every stored secret's name from it, so every server holds the same key
      "kinotic-secret-storage-master-key" = random_bytes.secret_storage_master_key.base64
      # KinD runs no GitHub App: the placeholders the development profile uses too, which the org
      # and system servers boot with; a real App's values go here to exercise GitHub
      "kinotic-github-app-private-key" = "-"
      "kinotic-github-webhook-secret"  = "-"
      # The secret key of Cloudflare's always-passing Turnstile test site, which KinD's values name as the
      # management server's site; the portal's find-my-organizations form passes its challenge every time
      "kinotic-turnstile-secret-key" = "1x0000000000000000000000000000000AA"
    },
  )
}
