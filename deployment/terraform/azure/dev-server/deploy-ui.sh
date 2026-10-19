#!/usr/bin/env bash
# Builds the portal and the system console for the development server and uploads them into
# the sites account, where the sites Worker serves each from sites/<hostname>/ exactly as it
# serves a published site. Run after `terraform apply` here, and again whenever kinotic-frontend
# changes; the account's blob role comes from this root. The edge keeps a site's index for a
# minute and its hashed assets by name, so a new build shows within the minute.
#
#   deploy-ui.sh                # build + upload both
#   deploy-ui.sh --deploy-only  # upload the existing dist/ directories
set -euo pipefail

HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
FRONTEND="$HERE/../../../../kinotic-frontend"
DEPLOY_ONLY=false
[[ "${1:-}" == "--deploy-only" ]] && DEPLOY_ONLY=true

endpoint=$(terraform -chdir="$HERE" output -raw sites_storage_blob_endpoint)
account=$(sed -E 's#https://([^.]+)\..*#\1#' <<<"$endpoint")

if [[ "$DEPLOY_ONLY" == "false" ]]; then
  (cd "$FRONTEND" && pnpm install --frozen-lockfile)
fi

# The tree the build came from, recorded in each site's version.json the way a published
# site's is, so redeploy.sh in the Proxmox root can tell whether the sites are current
tree=$(git -C "$FRONTEND" rev-parse HEAD:kinotic-frontend)
[[ -n "$(git -C "$FRONTEND" status --porcelain -- .)" ]] && tree="$tree-dirty"

deploy() {
  local app="$1" hostname="$2"
  if [[ "$DEPLOY_ONLY" == "false" ]]; then
    echo "==> Building $app for $hostname"
    (cd "$FRONTEND/apps/$app" && pnpm build --mode dev-server)
    printf '{"commitSha":"%s"}\n' "$tree" > "$FRONTEND/apps/$app/dist/version.json"
  fi
  echo "==> Uploading $app to sites/$hostname/"
  az storage blob upload-batch --auth-mode login --account-name "$account" \
    --destination sites --destination-path "$hostname" \
    --source "$FRONTEND/apps/$app/dist" --overwrite --only-show-errors --no-progress >/dev/null 2>&1
}

portal_hostname=$(terraform -chdir="$HERE" output -raw portal_hostname)
console_hostname=$(terraform -chdir="$HERE" output -raw console_hostname)
deploy portal "$portal_hostname"
deploy system "$console_hostname"
echo "Deployed: https://$portal_hostname and https://$console_hostname"
