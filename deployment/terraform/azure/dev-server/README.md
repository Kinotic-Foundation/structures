# The development server's Azure side

Everything the shared development server needs from Azure and Cloudflare, applied once from an
operator's machine. It reuses `modules/dev-environment`, the same module a developer's `dev/`
root uses, and adds what a server peers depend on needs: a key vault for the servers' secret
storage, a storage account for Elasticsearch snapshots, the portal and the system console as
Static Web Apps on the platform zone, and a hostname for each server's API with the rights to
issue its certificate.

| Resource | Name | Purpose |
|---|---|---|
| Resource group | `rg-kinotic-dev` | Holds everything below but the service principal |
| Sites storage account | `stkinoticdevsites` | Every site's files under `sites/<hostname>/`, private; the Worker reads with the SAS this root mints (`modules/sites`) |
| Cloudflare sites Worker and routes | `kinotic-dev-sites` | Serves every published UI at `<app>--<ui>.<org>.dev.kinotic.io` from the account, with the zone's settings and Total TLS (`modules/cloudflare-sites`) |
| Static Web Apps | `kinotic-dev-portal`, `kinotic-dev-console` | The portal at `dev-portal.kinotic.ai` and the system console at `dev-console.kinotic.ai`, each with its CNAME and managed certificate, on the platform zone and never the customers' (`modules/static-web-app`, as the cluster's portal) |
| Cloudflare records | `apps-api.dev.kinotic.io`, `*.apps-api.dev.kinotic.io` | The app server and every application's API host `<organizationId>--<applicationId>.apps-api.dev.kinotic.io`, proxied to the router's public address |
| Key vault | `kv-kinotic-dev` | The servers' secret storage (`kinotic.domain.secretStorage.backend: AZURE`) |
| Storage account + container | `stkinoticdevsnapshots` / `elasticsearch-snapshots` | The nightly Elasticsearch snapshot repository, and the migration vehicle |
| DNS A records | `dev-api.kinotic.ai`, `dev-system-api.kinotic.ai` | The router's public address, for the management server and the system server; `kinotic-dyndns.timer` on the host keeps them current |
| Service principal | `kinotic-dev-server` | The identity the servers run as: Storage Blob Data Contributor on the sites account, Contributor on the email service, Key Vault Secrets Officer on `kv-kinotic-dev`, DNS Zone Contributor on the zone for certbot and the address updater |
| Role assignment | the operator | Key Vault Secrets Officer on `kv-kinotic-dev`, to place the social sign-in client secrets |

## Applying

```bash
az login
export ARM_SUBSCRIPTION_ID=$(az account show --query id -o tsv)
cd deployment/terraform/azure/dev-server
```

```hcl
# local.auto.tfvars (gitignored)
public_ip          = "203.0.113.10"     # the router's public address today; the host keeps the record current
lets_encrypt_email = "you@example.com"
```

```bash
terraform init
terraform apply
```

Then the two UIs, built for this server and deployed to their Static Web Apps with the
deployment tokens this root outputs. Again whenever `kinotic-frontend` changes:

```bash
./deploy-ui.sh                # pnpm build --mode dev-server for apps/portal and apps/system, then swa deploy
```

The proxmox root reads this root's state file directly (`../azure/dev-server/terraform.tfstate`),
so both are applied from the same checkout. Two outputs feed the servers:

```bash
terraform output dev_server_env         # merged into every server's environment by the proxmox root
terraform output -raw secrets_env       # → kinotic-servers.env in the secrets directory, placed by sync-secrets.sh
```

The social sign-in providers the migration seeds (the `ORG_SIGNUP` rows of `kinotic_oidc_configuration`)
resolve their client secrets from `kv-kinotic-dev` by name, so each provider's secret goes
there once, and its registration lists the management server's callback URLs under `dev-api`:

```bash
az keyvault secret set --vault-name kv-kinotic-dev --name github-platform --value "$(cat)" >/dev/null   # the App's client secret on stdin
```

`terraform destroy` removes the resource group with everything in it, the DNS records, the
service principal, and its role assignments. The snapshot account goes with the group, so
copy the last snapshot elsewhere first if it still matters.
