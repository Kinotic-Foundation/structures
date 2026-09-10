#!/usr/bin/env bash
# Day-2 operations on the platform VM, over SSH as the kinotic user:
#
#   ./sync-platform.sh secrets <dir>   copy a generate-secrets.sh directory (with the values
#                                      filled in) into /etc/kinotic
#   ./sync-platform.sh stack           push deployment/docker-compose to /opt/kinotic/compose
#                                      and restart the stack — after editing a compose file,
#                                      or to pull newer images
#   ./sync-platform.sh logs [service]  follow the stack's logs
#
# cloud-init lays the stack down once, on the VM's first boot; this is how it changes after.
set -euo pipefail

HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
HOST="kinotic@$(cd "$HERE" && terraform output -raw platform_ip)"
COMPOSE_DIR="$HERE/../../docker-compose"

case "${1:-}" in
  secrets)
    SRC="${2:?usage: $0 secrets <dir>}"
    [ -f "$SRC/secrets.env" ] || { echo "$SRC/secrets.env is missing" >&2; exit 1; }
    grep -q '^AZURE_CLIENT_SECRET=.\+' "$SRC/secrets.env" || { echo "AZURE_CLIENT_SECRET is empty in $SRC/secrets.env" >&2; exit 1; }
    rsync -a --chmod=F0600 "$SRC/secrets.env" "$HOST:/etc/kinotic/secrets.env"
    rsync -a --chmod=D0750,F0600 "$SRC/platform-secrets/" "$HOST:/etc/kinotic/platform-secrets/"
    echo "secrets placed; the stack starts with: ssh $HOST sudo systemctl start kinotic-dev-server"
    ;;
  stack)
    rsync -a --delete --exclude 'compose.kinotic-e2e-test.yml' --exclude '*.e2e-test.json' \
      "$COMPOSE_DIR/" "$HOST:/opt/kinotic/compose/"
    ssh "$HOST" sudo systemctl restart kinotic-dev-server
    ;;
  logs)
    ssh -t "$HOST" "cd /opt/kinotic/compose && docker compose -f compose.dev-server.yml logs -f ${2:-}"
    ;;
  *)
    sed -n '2,12p' "$0" | sed 's/^# \{0,1\}//'
    exit 1
    ;;
esac
