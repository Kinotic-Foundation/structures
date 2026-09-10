# The development server on Proxmox

Two VMs on one Proxmox host, created by terraform from a freshly installed Proxmox and
configured on first boot by cloud-init from files in this repository: the **platform VM**
runs the compose stack (kinotic-server, three Elasticsearch nodes on their own disks, Loki,
Tempo, Mimir, Grafana; `deployment/docker-compose/compose.dev-server.yml`), the **node VM**
runs the vm-manager with the Cloud Hypervisor provider (`deployment/vm-node`). The design and
the reasons are on the [Development Server](https://kinotic.ai/platform/development-server)
page; this is the runbook.

Terraform owns what the Proxmox API exposes. Proxmox itself is installed by hand, and
nothing secret goes through terraform: credentials are generated and placed by the two
scripts here.

## Before the first apply

On the Proxmox host, once:

```bash
# An API token for terraform. Privileges: VM and datastore management on the node
pveum user add terraform@pve
pveum role add Terraform -privs "Datastore.Allocate Datastore.AllocateSpace Datastore.AllocateTemplate Datastore.Audit Sys.Audit Sys.Modify VM.Allocate VM.Audit VM.Clone VM.Config.CDROM VM.Config.CPU VM.Config.Cloudinit VM.Config.Disk VM.Config.HWType VM.Config.Memory VM.Config.Network VM.Config.Options VM.Migrate VM.Monitor VM.PowerMgmt SDN.Use"
pveum aclmod / -user terraform@pve -role Terraform
pveum user token add terraform@pve terraform --privsep 0     # prints the token once

# Snippets on the local datastore, for the cloud-init files
pvesm set local --content iso,vztmpl,backup,snippets

# The disks the VMs get whole, by stable id
ls -l /dev/disk/by-id/ | grep -v part
```

Snippets are uploaded over SSH, so the key of whoever runs terraform must be in root's
`authorized_keys` on the host and loaded in their agent. The enterprise repository the
installer enables answers 401 without a subscription; disable it and enable
`pve-no-subscription` under Node → Repositories.

The Azure side comes first: `deployment/terraform/azure/dev-server` (its README), applied
from the same checkout, because this root reads its outputs from that state file.

## Applying

```hcl
# local.auto.tfvars (gitignored)
proxmox_endpoint  = "https://192.168.1.10:8006/"
proxmox_api_token = "terraform@pve!terraform=00000000-0000-0000-0000-000000000000"
platform_ip       = "192.168.1.20/24"
node_ip           = "192.168.1.21/24"
gateway           = "192.168.1.1"
dns_servers       = ["192.168.1.1"]
ssh_public_key    = "ssh-ed25519 AAAA... you@laptop"
es_disks = [
  { device = "/dev/disk/by-id/nvme-Samsung_SSD_990_PRO_1TB_S6Z1NL0W123457B", size_gb = 931 },
  { device = "/dev/disk/by-id/nvme-Samsung_SSD_990_PRO_1TB_S6Z1NL0W123458C", size_gb = 931 },
  { device = "/dev/disk/by-id/nvme-Samsung_SSD_990_PRO_1TB_S6Z1NL0W123459D", size_gb = 931 },
]
node_disk = { device = "/dev/disk/by-id/nvme-Samsung_SSD_990_PRO_1TB_S6Z1NL0W123456A", size_gb = 931 }
```

```bash
cd deployment/terraform/proxmox
terraform init
terraform apply
```

Both VMs boot and run their cloud-init: the platform VM formats and mounts the three
Elasticsearch disks, installs Docker, writes the stack and `/etc/kinotic/dev-server.env`, and
enables `kinotic-dev-server.service`, which waits for the secrets below; the node VM splits
its disk into the Docker data root and the workload checkouts, runs `setup-node.sh`, turns on
egress default-deny, and installs the vm-manager as `kinotic-vm-manager.service`, which waits
for the machine credentials. Watch either with `ssh kinotic@<ip> sudo cloud-init status --wait`.

## After the first apply

1. **Secrets.** Generate them, fill in the two values that come from elsewhere, place them:

   ```bash
   ./generate-secrets.sh ./dev-server-secrets
   (cd ../azure/dev-server && terraform output -raw secrets_env)   # AZURE_CLIENT_SECRET
   # KINOTIC_MANAGEMENTAPI_GITHUB_APPPRIVATEKEY and _WEBHOOKSECRET: the shared GitHub App's settings page
   ./sync-platform.sh secrets ./dev-server-secrets
   ```

   The generated directory is the only copy of the JWT signing key and the master key. Keep
   it somewhere safe and out of the repository; both are carried to the cloud at migration.

2. **The certificate.** On the platform VM, certbot with the DNS-01 plugin, as the server's
   principal (the `dev-server` root granted it DNS Zone Contributor):

   ```bash
   ssh kinotic@<platform ip>
   sudo pip install certbot-dns-azure
   sudo tee /etc/kinotic/certbot-azure.ini <<EOT   # then chmod 0600
   dns_azure_sp_client_id = <AZURE_CLIENT_ID>
   dns_azure_sp_client_secret = <AZURE_CLIENT_SECRET>
   dns_azure_tenant_id = <AZURE_TENANT_ID>
   dns_azure_environment = AzurePublicCloud
   dns_azure_zone1 = kinotic.ai:/subscriptions/<subscription>/resourceGroups/<global rg>
   EOT
   sudo certbot certonly --authenticator dns-azure --dns-azure-config /etc/kinotic/certbot-azure.ini \
     --deploy-hook 'install -m 0644 -o kinotic "$RENEWED_LINEAGE"/fullchain.pem /etc/kinotic/certs/ && install -m 0640 -o kinotic "$RENEWED_LINEAGE"/privkey.pem /etc/kinotic/certs/ && systemctl try-restart kinotic-dev-server' \
     -d dev.kinotic.ai
   ```

   The deploy hook runs on every renewal too, which is all the certificate rotation there is.

3. **Start the stack**: `sudo systemctl start kinotic-dev-server`. The migration runs once
   against the cluster, then the server comes up; `./sync-platform.sh logs kinotic-server`
   follows it. The portal is on `https://dev.kinotic.ai` once the router forwards 443 to the
   platform VM's 9090 and 58503 to 58503.

4. **The GitHub App's webhook** → `https://dev.kinotic.ai:58503/api/github/webhook`.

5. **The node.** In the system console create a SYSTEM-scope machine for the node, then:

   ```bash
   ssh kinotic@<node ip> 'sudo tee /etc/kinotic/vm-manager.secrets.env >/dev/null && sudo chmod 0600 /etc/kinotic/vm-manager.secrets.env && sudo systemctl start kinotic-vm-manager' <<EOT
   KINOTIC_CLIENT_ID=<machine id>
   KINOTIC_CLIENT_SECRET=<machine secret>
   EOT
   ```

   The node appears `ONLINE` in the console with no health message.

6. **Snapshots.** Register the Azure repository and a daily policy on the cluster (the
   account key is `terraform output -raw snapshots_storage_account_key` in the Azure root,
   and goes into each node's keystore as `azure.client.default.key`).

## Day 2

- `./sync-platform.sh stack` pushes `deployment/docker-compose` to the VM and restarts the
  stack: after a compose or config change, or to pull newer images.
- `terraform apply` again after changing a VM's size. Changing a cloud-init template does
  not re-run it on an existing VM; cloud-init runs once.
- The node kit is idempotent: `ssh kinotic@<node ip> sudo /opt/kinotic/vm-node/verify-node.sh`
  after a reboot, `setup-node.sh` again to pick up a new Kata release.
- `terraform destroy` removes both VMs and their OS disks. The passthrough disks are not
  touched: a new apply mounts the same Elasticsearch data and the same checkouts.
