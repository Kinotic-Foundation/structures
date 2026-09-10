#!/usr/bin/env bash
# Generates what the development server needs that nothing else issues: the JWT signing key
# set, in the shape deployment/kind/terraform/platform-secrets.tf produces (an activeKeyId
# and a keys list, so a key can be added to the set at migration), the secret-storage master
# key, and passwords for Elasticsearch and Grafana. Writes them under a directory the operator
# then copies to the platform VM; nothing here passes through terraform.
set -euo pipefail

OUT="${1:-./dev-server-secrets}"
[ -e "$OUT" ] && { echo "$OUT exists; refusing to overwrite generated secrets" >&2; exit 1; }
mkdir -p "$OUT/platform-secrets"
chmod 0700 "$OUT"

key() { openssl rand -base64 32; }
password() { openssl rand -base64 24 | tr -d '/+=' | cut -c1-24; }

cat > "$OUT/platform-secrets/jwt-signing-keys" <<JSON
{"activeKeyId":"v1","keys":[{"id":"v1","key":"$(key)"}]}
JSON

cat > "$OUT/secrets.env" <<ENV
# /etc/kinotic/secrets.env on the platform VM (mode 0600). AZURE_CLIENT_SECRET comes from
# \`terraform output -raw secrets_env\` in deployment/terraform/azure/dev-server; the GitHub
# App's key and webhook secret from the App's settings page.
AZURE_CLIENT_SECRET=
ELASTIC_PASSWORD=$(password)
GRAFANA_ADMIN_PASSWORD=$(password)
KINOTIC_DOMAIN_SECRETSTORAGE_MASTERKEY=$(key)
KINOTIC_MANAGEMENTAPI_GITHUB_APPPRIVATEKEY=
KINOTIC_MANAGEMENTAPI_GITHUB_WEBHOOKSECRET=
ENV
chmod 0600 "$OUT/secrets.env" "$OUT/platform-secrets/jwt-signing-keys"

echo "Written to $OUT:"
echo "  secrets.env                     -> /etc/kinotic/secrets.env"
echo "  platform-secrets/jwt-signing-keys -> /etc/kinotic/platform-secrets/jwt-signing-keys"
echo "Fill in AZURE_CLIENT_SECRET and the GitHub App values, then: ./sync-platform.sh secrets $OUT"
