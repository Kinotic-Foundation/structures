#!/usr/bin/env bash
# Copies a secrets directory (generate-secrets.sh) to the host's secrets directory, owned by
# the user the server's container runs as, and re-applies the containers that read it. The
# certificates certbot placed on the host are left alone.
#
#   sync-secrets.sh <secrets dir> [host]      host defaults to the root's proxmox_host output
set -euo pipefail

SRC="${1:?usage: $0 <secrets dir> [host]}"
HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
HOST="${2:-$(terraform -chdir="$HERE" output -raw proxmox_host)}"
DEST="${SECRETS_DIR:-/etc/kinotic/secrets}"
SNIPPETS="${SNIPPETS_DIR:-/var/lib/vz/snippets}"

rsync -a --chmod=D0700,F0600 "$SRC/" "root@$HOST:$DEST/"
# cnb, uid 1000 in the server's container, is uid 101000 on the host
ssh "root@$HOST" "
  chown -R 101000:101000 '$DEST/kinotic-server'
  for manifest in '$SNIPPETS/kinotic-kinotic-server.manifest.json' '$SNIPPETS/kinotic-grafana.manifest.json'; do
    [ -e \"\$manifest\" ] && python3 '$SNIPPETS/kinotic-apply-container.py' \"\$manifest\"
  done
  true
"
