#!/usr/bin/env bash
# Builds the portal and the system console for the development server and deploys each to its
# Static Web App (the dev-server root), as deploy.sh in the frontend root does for the cluster's
# portal. Run after `terraform apply` here, and again whenever kinotic-frontend changes.
#
#   deploy-ui.sh                # build + deploy both
#   deploy-ui.sh --deploy-only  # deploy the existing dist/ directories
set -euo pipefail

HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
FRONTEND="$HERE/../../../../kinotic-frontend"
DEPLOY_ONLY=false
[[ "${1:-}" == "--deploy-only" ]] && DEPLOY_ONLY=true

if [[ "$DEPLOY_ONLY" == "false" ]]; then
  (cd "$FRONTEND" && pnpm install --frozen-lockfile)
fi

# The tree the build came from, recorded in each app's version.json the way a published site's
# is, so redeploy.sh in the Proxmox root can tell whether the deployed UIs are current
tree=$(git -C "$FRONTEND" rev-parse HEAD:kinotic-frontend)
[[ -n "$(git -C "$FRONTEND" status --porcelain -- .)" ]] && tree="$tree-dirty"

deploy() {
  local app="$1" output="$2" hostname token
  hostname=$(terraform -chdir="$HERE" output -raw "${output}_hostname")
  token=$(terraform -chdir="$HERE" output -raw "${output}_deployment_token")
  if [[ "$DEPLOY_ONLY" == "false" ]]; then
    echo "==> Building $app for $hostname"
    (cd "$FRONTEND/apps/$app" && pnpm build --mode dev-server)
    printf '{"commitSha":"%s"}\n' "$tree" > "$FRONTEND/apps/$app/dist/version.json"
  fi
  echo "==> Deploying $app to https://$hostname"
  (cd "$FRONTEND/apps/$app" && npx @azure/static-web-apps-cli deploy dist --deployment-token "$token" --env production)
}

deploy portal portal
deploy system console
echo "Deployed: https://$(terraform -chdir="$HERE" output -raw portal_hostname) and https://$(terraform -chdir="$HERE" output -raw console_hostname)"
