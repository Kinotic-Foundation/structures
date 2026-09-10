# Development Server

> A development environment for a few peers on one physical host, what it keeps in Azure, and how their applications move to Kinotic Cloud.

## Overview

This page is the design record and setup plan for the development server: a development
environment on one physical host — a Ryzen 9 with 96 GB of RAM and several SSDs — where a few
peers build Kinotic applications against the real platform: organization sign-up,
push-to-deploy into micro VMs, published UIs served through Front Door, email, logs and traces
in the portal. Everything that can run on the host runs on the host. Azure keeps only what a
single host cannot provide (Front Door, the sites storage account, Communication Services
email, the DNS zone) and what the migration path needs (a Key Vault, a snapshot container).

When Kinotic Cloud opens, the peers' organizations, applications, and data move there and keep
working. The migration is a designed step of this environment rather than an afterthought, and
two of the decisions below exist only because of it.

<callout type="info">

**Status.** Nothing here is built yet. This page records the decisions, the target topology,
and the steps that build it, in the order they have to happen.

</callout>

<development-server-diagram>



</development-server-diagram>

## Decisions

### Proxmox VE hosts two VMs

The platform and the vm-manager node cannot share one operating system. The node's provisioning
(`kinotic-js/vmm-r&d/docker-kata-ch-test/setup-node.sh`) owns `/etc/docker/daemon.json`,
`br_netfilter`, and the `DOCKER-USER` chain, and its firewall floor (`kinotic-node-firewall`)
drops every guest packet addressed to the node itself — so a gateway on the node's own address
is unreachable from every workload, and the kit's README says outright not to colocate the
api-gateway with workloads. On one host that means a hypervisor.

Proxmox over plain Ubuntu with libvirt because it adds snapshots (re-running `setup-node.sh`
deletes `/opt/kata`), whole-disk passthrough by stable id, ZFS, and the `bpg/proxmox` Terraform
provider, so the host can be a third terraform root beside `kind/` and `azure/`. Incus is the
credible alternative for an all-code, no-UI host; XCP-ng is out because Xen's nested
virtualization is off the path the vm-manager was tested on; Harvester wants three nodes. The
node VM runs nested KVM, which is the configuration every measurement in the Cloud Hypervisor
evaluation was taken in (Azure `Standard_D4s_v3`).

The node is a VM, never an LXC container: it needs its own kernel modules, sysctls, and
iptables.

### No Kubernetes

Nothing the server needs comes from Kubernetes. Ignite discovers peers by shared filesystem or
static addresses as readily as by the Kubernetes API (`KinoticIgniteConfig`), platform secrets
are two JSON files at a path, TLS is two PEM files at a path, and the docker-compose stack in
`deployment/docker-compose/` is the complete platform — with Tempo and Mimir, which the Helm
charts do not deploy yet. The Helm charts stay the production artifact; KinD on a laptop is
where they are rehearsed. If the host ever needs to rehearse them too, a k3s VM slots in beside
the two below without touching them.

### Three Elasticsearch nodes, one physical disk each, one shard and one replica

On one host, a node is a failure domain only if its disk is. Redundancy is therefore the
conjunction of replicas across nodes **and** one physical disk per node: two nodes whose data
directories share a disk, or two virtual disks carved from one Proxmox pool, replicate a shard
onto the same failure domain and lose it together. Each ES node's data directory is a whole-disk
passthrough (`qm set <vm> -scsiN /dev/disk/by-id/<disk>`), never a volume from a shared pool.

Three nodes rather than two because of quorum, not storage: with two master-eligible nodes the
cluster stops accepting writes the moment one disk dies, even though the surviving replica holds
every shard. With three, one disk can fail and the cluster stays writable.

One shard and one replica per index. The production defaults are three and two
(`PersistenceProperties`), and every published EntityDefinition costs `shards × (1 + replicas)`
against the 1000-shard-per-node ceiling, so the production numbers on three small nodes would
cap the peers' definitions early for no gain.

### The node runs the Cloud Hypervisor provider

`BOXLITE` is the provider that runs anywhere a developer works; `CLOUD_HYPERVISOR` is the one a
node is provisioned for, with a health contract the node re-checks on every heartbeat, egress
denied by default, host-side stdout capture, and no mount limit (see
[VM provider](/platform/configuration#vm-provider)). The development server is a production
shape, so the node is provisioned with the kit under `docker-kata-ch-test/` exactly as an Azure
node is.

### Azure keeps Front Door, the sites account, email, DNS, and a server Key Vault

The developer root `deployment/terraform/azure/dev` already builds everything a server off Azure
needs to publish UIs and send email: a resource group, the sites module under
`apps-<environment>.<zone>`, a Key Vault holding the wildcard certificate Front Door reads, and
a service principal whose `AZURE_CLIENT_ID`, `AZURE_CLIENT_SECRET`, and `AZURE_TENANT_ID``DefaultAzureCredential` takes before anything else. A `dev-server` root is that root plus three
things: a Key Vault for the server, DNS rights for certificate issuance, and a container for
Elasticsearch snapshots.

The server's Key Vault is kept deliberately. Without it the secret storage backend is in-memory
(`SecretStorageConfiguration`): nothing stored survives a restart and nothing can be carried to
the cloud. Nothing calls `SecretStorageService` today, so the cost is nil this week; the first
feature that stores a peer's secret would turn it into silent data loss. With the `AZURE`
backend the peers' secrets are already in Azure on migration day.

### Migrations become append-only from the first peer sign-up

Index mappings are strict, `V1__init.sql` is edited in place while the version is `-SNAPSHOT`,
and a restored `migration_history` says every migration is applied — so a field added to a table
between the development server's launch and cutover fails the first save of that entity on the
cloud side. The repository rule starts append-only discipline "when the first release exists";
for every table holding a peer's data, that launch is the release. From then on a change to one
of those tables is a new versioned file. Tables no peer writes (`kinotic_vm_node`,
`kinotic_workload`, the fixtures) can keep being edited in place.

### The same GitHub App as Kinotic Cloud

`kinotic_github_app_installation.githubInstallationId` belongs to one App (the `appId` in
`kinotic-server/src/main/resources/application.yml`), and one App has one webhook URL. Using
the cloud's App for the development server means the installation rows migrate untouched and
cutover is a change of webhook URL; a separate App would mean every peer re-installs on their
repos after migration. The cost is that while the development server owns the webhook URL,
nothing else receives pushes from that App.

### Backups are Elasticsearch snapshots to Azure Blob, and they are the migration vehicle

Replicas across disks protect against a disk. They do nothing for a dead host, a power supply,
or a bad migration. A snapshot repository of type `azure` (the module ships with ES 9) with a
daily SLM policy is the backup, and restoring the latest snapshot into the cloud cluster is how
the peers' data gets there. `deployment/terraform/azure/PRODUCTION.md` lists ES snapshots to
Azure Blob as an open item for the cloud; the development server builds it first.

## Host

Proxmox VE on the first SSD. Every other disk is passed through whole to exactly one VM, so a
disk failure is contained to the node that owns it and `smartd` on the host watches each drive.

<table>
<thead>
  <tr>
    <th>
      Disk
    </th>
    
    <th>
      Passed to
    </th>
    
    <th>
      Filesystem
    </th>
    
    <th>
      Holds
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      0
    </td>
    
    <td>
      Proxmox
    </td>
    
    <td>
      ZFS or ext4
    </td>
    
    <td>
      Proxmox, both VMs' OS images, Loki/Tempo/Mimir data
    </td>
  </tr>
  
  <tr>
    <td>
      1
    </td>
    
    <td>
      Node VM
    </td>
    
    <td>
      XFS <code>
        prjquota
      </code>
      
      , two partitions
    </td>
    
    <td>
      <code>
        /var/lib/docker
      </code>
      
       (guest rootfs, per-workload disk caps) and <code>
        /var/lib/kinotic/workloads
      </code>
      
       (checkouts, <code>
        sizeLimitMb
      </code>
      
       quotas)
    </td>
  </tr>
  
  <tr>
    <td>
      2
    </td>
    
    <td>
      Platform VM
    </td>
    
    <td>
      XFS or ext4
    </td>
    
    <td>
      <code>
        es-1
      </code>
      
       data
    </td>
  </tr>
  
  <tr>
    <td>
      3
    </td>
    
    <td>
      Platform VM
    </td>
    
    <td>
      XFS or ext4
    </td>
    
    <td>
      <code>
        es-2
      </code>
      
       data
    </td>
  </tr>
  
  <tr>
    <td>
      4
    </td>
    
    <td>
      Platform VM
    </td>
    
    <td>
      XFS or ext4
    </td>
    
    <td>
      <code>
        es-3
      </code>
      
       data
    </td>
  </tr>
</tbody>
</table>

With four disks, disk 1 becomes a partition of disk 0: a workload checkout is redeployable from
GitHub and not worth a slot. The three ES disks are not negotiable.

<table>
<thead>
  <tr>
    <th>
      VM
    </th>
    
    <th>
      vCPU
    </th>
    
    <th>
      RAM
    </th>
    
    <th>
      Notes
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      Platform
    </td>
    
    <td>
      12
    </td>
    
    <td>
      32 GB
    </td>
    
    <td>
      server 4 GB, three ES nodes at 4 GB each (2 GB heap), otel stack ~4 GB, headroom for the migration job
    </td>
  </tr>
  
  <tr>
    <td>
      Node
    </td>
    
    <td>
      8
    </td>
    
    <td>
      40 GB
    </td>
    
    <td>
      <code>
        cpu: host
      </code>
      
       for nested KVM; ~165 MiB per idle Kata guest plus touched pages; sync VMs 2 GB, runtime VMs 1 GB (<code>
        DeploymentProperties
      </code>
      
      )
    </td>
  </tr>
  
  <tr>
    <td>
      Proxmox
    </td>
    
    <td>
      —
    </td>
    
    <td>
      24 GB
    </td>
    
    <td>
      ZFS ARC and spare
    </td>
  </tr>
</tbody>
</table>

Both VMs sit on `vmbr0` with static LAN addresses. Only the platform VM is reachable from the
internet, on two forwarded ports.

The host is a terraform root, `deployment/proxmox/terraform`, using `bpg/proxmox`: two VMs
cloned from a cloud-init Ubuntu template, the passthrough disks, and the cloud-init files that
lay down the compose stack on one and run the node kit on the other.

## Platform VM

Ubuntu 24.04 LTS with Docker Engine. The stack is the existing compose files plus a
`dev-server` overlay, run under a systemd unit:

```text
deployment/docker-compose/
  compose.dev-server.yml                # includes: elasticsearch-dev-server, compose-otel, migration, server, with overrides
  compose.elasticsearch-dev-server.yml  # es-1, es-2, es-3 — one service per passthrough disk
kinotic-server/src/main/resources/
  application-dev-server.yml            # the dev-server profile: everything below that is not a secret
/etc/kinotic/                           # on the VM, outside the repo
  env                                   # AZURE_*, ES password, GitHub App key and webhook secret, machine secrets
  platform-secrets/                     # jwt-signing-keys, secret-storage-master-keys
  certs/                                # fullchain.pem, privkey.pem from certbot
```

The server runs with `SPRING_PROFILES_ACTIVE=production,compose,dev-server`. `production` is
what takes it off the development conveniences: no auto-seeded platform secrets
(`DevPlatformSecretsGenerator` is development-only), the halting Ignite failure handler, the
production shard defaults — which `dev-server` then overrides.

### Elasticsearch

Three services in `compose.elasticsearch-dev-server.yml`, identical except for name and mount:

<table>
<thead>
  <tr>
    <th>
      Setting
    </th>
    
    <th>
      Value
    </th>
    
    <th>
      Why
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code>
        node.roles
      </code>
    </td>
    
    <td>
      <code>
        master,data,ingest,transform
      </code>
    </td>
    
    <td>
      Three master-eligible nodes give quorum through one disk loss; a dedicated master is wasted on one host
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        discovery.seed_hosts
      </code>
      
      , <code>
        cluster.initial_master_nodes
      </code>
    </td>
    
    <td>
      the three service names
    </td>
    
    <td>
      Cluster bootstrap
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        xpack.security.enabled
      </code>
    </td>
    
    <td>
      <code>
        true
      </code>
    </td>
    
    <td>
      The compose default of <code>
        false
      </code>
      
       is marked local-only; three nodes with security on require transport TLS, generated once with <code>
        elasticsearch-certutil
      </code>
      
       and mounted
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        ES_JAVA_OPTS
      </code>
    </td>
    
    <td>
      <code>
        -Xms2g -Xmx2g
      </code>
    </td>
    
    <td>
      Half of each container's 4 GB
    </td>
  </tr>
  
  <tr>
    <td>
      data mount
    </td>
    
    <td>
      <code>
        /mnt/es-N:/usr/share/elasticsearch/data
      </code>
    </td>
    
    <td>
      One passthrough disk per node
    </td>
  </tr>
  
  <tr>
    <td>
      published ports
    </td>
    
    <td>
      none on the LAN
    </td>
    
    <td>
      The server, migration, and Grafana reach ES on the compose network; 9200 is never on the LAN
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        vm.max_map_count
      </code>
    </td>
    
    <td>
      <code>
        262144
      </code>
      
       on the VM
    </td>
    
    <td>
      ES refuses to start without it
    </td>
  </tr>
</tbody>
</table>

Server side, in `application-dev-server.yml` and the env file:

<table>
<thead>
  <tr>
    <th>
      Property
    </th>
    
    <th>
      Value
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code>
        kinotic.domain.elasticConnections[0..2]
      </code>
    </td>
    
    <td>
      <code>
        es-1
      </code>
      
      , <code>
        es-2
      </code>
      
      , <code>
        es-3
      </code>
      
       on 9200, <code>
        http
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.domain.elasticUsername
      </code>
      
       / <code>
        elasticPassword
      </code>
    </td>
    
    <td>
      the <code>
        elastic
      </code>
      
       user; password from the env file
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.persistence.numberOfShards
      </code>
      
       / <code>
        numberOfReplicas
      </code>
    </td>
    
    <td>
      <code>
        1
      </code>
      
       / <code>
        1
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        KINOTIC_MIGRATION_ELASTIC_HOST
      </code>
      
       / <code>
        _USERNAME
      </code>
      
       / <code>
        _PASSWORD
      </code>
    </td>
    
    <td>
      <code>
        es-1
      </code>
      
       and the same user, for the one-shot migration container
    </td>
  </tr>
</tbody>
</table>

The snapshot repository is registered once ES is up: an `azure` repository named for the
environment, its account key or SAS token in the ES keystore (built once and mounted, since
compose cannot run `elasticsearch-keystore` at start), and an SLM policy taking a daily
snapshot of every index with 30 days' retention.

### Server

<table>
<thead>
  <tr>
    <th>
      Property
    </th>
    
    <th>
      Value
    </th>
    
    <th>
      Why
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code>
        kinotic.domain.appBaseUrl
      </code>
    </td>
    
    <td>
      <code>
        https://dev.kinotic.ai
      </code>
    </td>
    
    <td>
      The SPA, served by the server's own web server on 9090
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.domain.apiBaseUrl
      </code>
    </td>
    
    <td>
      <code>
        https://dev.kinotic.ai:58503
      </code>
    </td>
    
    <td>
      REST, STOMP, MCP, and the OIDC redirect URIs are on the api-gateway port; <code>
        issuerBaseUrl
      </code>
      
       falls back to it
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.apiGateway.ssl.enabled
      </code>
      
       / <code>
        certPath
      </code>
      
       / <code>
        keyPath
      </code>
    </td>
    
    <td>
      <code>
        true
      </code>
      
      , the two certbot PEMs
    </td>
    
    <td>
      Vert.x terminates TLS on both ports, no reverse proxy, matching KinD and Azure
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.platformSecrets.jwtSigningKeysPath
      </code>
      
       / <code>
        secretStorageMasterKeysPath
      </code>
    </td>
    
    <td>
      files under <code>
        /etc/kinotic/platform-secrets
      </code>
    </td>
    
    <td>
      Same JSON shape <code>
        deployment/kind/terraform/platform-secrets.tf
      </code>
      
       generates: <code>
        activeKeyId
      </code>
      
       plus a <code>
        keys
      </code>
      
       list
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.domain.secretStorage.backend
      </code>
      
       / <code>
        azure.vaultUrl
      </code>
    </td>
    
    <td>
      <code>
        AZURE
      </code>
      
      , the development server's vault
    </td>
    
    <td>
      The Key Vault decision above
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.domain.email.enabled
      </code>
      
       / <code>
        endpoint
      </code>
      
       / <code>
        senderAddress
      </code>
    </td>
    
    <td>
      <code>
        true
      </code>
      
      , the global ACS endpoint, <code>
        DoNotReply@kinotic.ai
      </code>
    </td>
    
    <td>
      Sign-up and invite mail; the principal holds Contributor on the email service
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.systemApi.uiDeployment.disableProvisioner
      </code>
      
       / <code>
        sitesDomain
      </code>
      
       / <code>
        sitesStorageEndpoint
      </code>
    </td>
    
    <td>
      <code>
        false
      </code>
      
      , <code>
        apps-dev.kinotic.ai
      </code>
      
      , the development server's sites account
    </td>
    
    <td>
      The <code>
        dev-server
      </code>
      
       root's outputs, as the <code>
        dev
      </code>
      
       root emits them
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.systemApi.deployment.serverHost
      </code>
      
       / <code>
        serverPort
      </code>
      
       / <code>
        serverUseSsl
      </code>
    </td>
    
    <td>
      the platform VM's LAN IPv4, <code>
        58503
      </code>
      
      , <code>
        true
      </code>
    </td>
    
    <td>
      Workloads reach the gateway across VMs; a <code>
        CLOUD_HYPERVISOR
      </code>
      
       node rejects a hostname here. The guest trusts the Let's Encrypt chain
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.systemApi.deployment.syncAllowedHosts
      </code>
    </td>
    
    <td>
      GitHub's and the npm registry's published address ranges, as CIDRs
    </td>
    
    <td>
      Egress rules match addresses, not names; the development value uses names because it runs <code>
        BOXLITE
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.apiGateway.cors.allowedOriginPattern
      </code>
      
       / <code>
        allowCredentials
      </code>
    </td>
    
    <td>
      <code>
        https://dev\.kinotic\.ai(:\d+)?|https://[a-z0-9-]+\.apps-dev\.kinotic\.ai
      </code>
      
      , <code>
        true
      </code>
    </td>
    
    <td>
      The <code>
        kubernetes
      </code>
      
       profile hardcodes <code>
        kinotic.ai
      </code>
      
       and is not active here
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        kinotic.managementApi.github.appPrivateKey
      </code>
      
       / <code>
        webhookSecret
      </code>
    </td>
    
    <td>
      from the env file
    </td>
    
    <td>
      The cloud's App; its webhook URL points at <code>
        https://dev.kinotic.ai:58503/api/github/webhook
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        AZURE_CLIENT_ID
      </code>
      
       / <code>
        AZURE_CLIENT_SECRET
      </code>
      
       / <code>
        AZURE_TENANT_ID
      </code>
    </td>
    
    <td>
      from the env file
    </td>
    
    <td>
      The <code>
        dev-server
      </code>
      
       root's service principal
    </td>
  </tr>
</tbody>
</table>

The Loki, Tempo, and Mimir URLs the server queries are the ones `compose.kinotic-server.yml`
already sets. `compose.dev-server.yml` additionally publishes Loki's 3100, Tempo's OTLP 4318,
and Mimir's 9009 on the LAN interface, for the node's Alloy.

### TLS

certbot with the `dns-azure` plugin, authenticating as the same service principal, which the
`dev-server` root grants DNS Zone Contributor on `kinotic.ai`. One certificate for
`dev.kinotic.ai` serves both ports. The deploy hook copies the PEMs into `/etc/kinotic/certs`
and runs `docker compose restart kinotic-server`, which is the whole of what Reloader does in
the cluster.

## Node VM

Ubuntu 22.04 (what the kit is verified on), `cpu: host`, disk 1 passed through. Two XFS
partitions mounted with `prjquota` before the kit runs: `/var/lib/docker`, which `setup-node.sh`
then leaves alone instead of creating its 40 GB loop image, and `/var/lib/kinotic/workloads`.

```bash
sudo ./setup-node.sh          # docker, kata 4.1.0 on cloud-hypervisor, daemon.json, firewall floor
sudo touch /etc/kinotic/egress-default-deny && sudo systemctl restart kinotic-node-firewall
sudo ./verify-node.sh         # every invariant, again after every reboot
sudo bun run src/requirements-test.ts
```

The Azure IMDS and WireServer drops install and verify unchanged; they protect nothing here and
are left in so every node is provisioned by one path.

The vm-manager is installed from npm as `@kinotic-ai/vm-manager` and runs as root under a
systemd unit with Bun (root for iptables, the Docker socket, and project quotas), with this
environment:

<table>
<thead>
  <tr>
    <th>
      Variable
    </th>
    
    <th>
      Value
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code>
        KINOTIC_VM_PROVIDER
      </code>
    </td>
    
    <td>
      <code>
        CLOUD_HYPERVISOR
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        KINOTIC_NODE_ID
      </code>
    </td>
    
    <td>
      <code>
        dev-node-1
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        KINOTIC_SERVER_HOST
      </code>
      
       / <code>
        KINOTIC_SERVER_PORT
      </code>
      
       / <code>
        KINOTIC_SERVER_USE_SSL
      </code>
    </td>
    
    <td>
      the platform VM's LAN IPv4, <code>
        58503
      </code>
      
      , <code>
        true
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        KINOTIC_CLIENT_ID
      </code>
      
       / <code>
        KINOTIC_CLIENT_SECRET
      </code>
    </td>
    
    <td>
      a SYSTEM-scope machine created in the system console; client credentials are the non-Azure machine path
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        KINOTIC_WORKLOAD_DATA_DIR
      </code>
    </td>
    
    <td>
      <code>
        /var/lib/kinotic/workloads
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        KINOTIC_WORKLOAD_DNS
      </code>
    </td>
    
    <td>
      the LAN resolver, permitted on port 53 for every workload
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        KINOTIC_LOKI_URL
      </code>
      
       / <code>
        KINOTIC_TEMPO_URL
      </code>
      
       / <code>
        KINOTIC_MIMIR_URL
      </code>
    </td>
    
    <td>
      <code>
        http://<platform VM>:3100
      </code>
      
      , <code>
        http://<platform VM>:4318
      </code>
      
      , <code>
        http://<platform VM>:9009/otlp
      </code>
    </td>
  </tr>
</tbody>
</table>

## Azure: the `dev-server` terraform root

`deployment/terraform/azure/dev-server`, modeled on `dev/` with local state and
`environment = "dev"`, creating:

<table>
<thead>
  <tr>
    <th>
      Resource
    </th>
    
    <th>
      Purpose
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      Resource group <code>
        rg-kinotic-dev
      </code>
    </td>
    
    <td>
      Everything below but the principal
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        modules/sites
      </code>
      
       under <code>
        apps-dev.<zone>
      </code>
    </td>
    
    <td>
      Front Door profile, endpoint, wildcard domain and certificate, the sites storage account
    </td>
  </tr>
  
  <tr>
    <td>
      Key Vault <code>
        kv-kinotic-dev-sites
      </code>
    </td>
    
    <td>
      The wildcard certificate Front Door reads, as in <code>
        dev/
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      Key Vault <code>
        kv-kinotic-dev
      </code>
    </td>
    
    <td>
      The server's secret storage; the principal holds Key Vault Secrets Officer, as <code>
        cluster/keyvault.tf
      </code>
      
       grants the workload identity
    </td>
  </tr>
  
  <tr>
    <td>
      Storage account <code>
        stkinoticdevsnapshots
      </code>
    </td>
    
    <td>
      The ES snapshot repository's container
    </td>
  </tr>
  
  <tr>
    <td>
      DNS <code>
        dev.<zone>
      </code>
      
       A record
    </td>
    
    <td>
      The host's public address; a dynamic-DNS updater if the ISP changes it
    </td>
  </tr>
  
  <tr>
    <td>
      Service principal <code>
        kinotic-dev-server
      </code>
    </td>
    
    <td>
      Storage Blob Data Contributor on the sites account, Contributor on the email service, Key Vault Secrets Officer on the server vault, DNS Zone Contributor on the zone for certbot
    </td>
  </tr>
</tbody>
</table>

Its outputs are the values the `dev-server` profile tables above name, and the principal's
three `AZURE_*` values go into `/etc/kinotic/env` on the platform VM rather than `.env.local`.

## Network and access

The router forwards two ports to the platform VM: 443 to 9090 (the SPA) and 58503 to 58503
(REST, STOMP, MCP, the GitHub webhook). This is the KinD layout with a public address; there is
no reverse proxy. A Cloudflare Tunnel routing `/api`, `/v1`, `/.well-known`, and `/mcp` to 58503
and everything else to 9090 would collapse the two ports into one origin, the way the
development tunnel does, and hide the host's address; it is the alternative if exposing the
address is unwelcome.

Peers use `https://dev.kinotic.ai`: organization sign-up with email verification through ACS,
social login through the platform OIDC providers registered with `https://dev.kinotic.ai:58503`
redirect URIs, the CLI's device grant, and MCP hosts through the authorization-code grant.
Nothing on the LAN besides the two forwarded ports is reachable from outside.

## Backups

<table>
<thead>
  <tr>
    <th>
      What
    </th>
    
    <th>
      How
    </th>
    
    <th>
      Restore drill
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      Elasticsearch
    </td>
    
    <td>
      SLM daily snapshot to the Azure Blob repository, 30 days kept
    </td>
    
    <td>
      Restore the latest snapshot into a scratch three-node compose on the platform VM, once, before the first peer signs up, and again before cutover
    </td>
  </tr>
  
  <tr>
    <td>
      Platform VM
    </td>
    
    <td>
      Proxmox <code>
        vzdump
      </code>
      
       weekly to disk 0
    </td>
    
    <td>
      Restore the VM; ES data lives on the passthrough disks and rejoins
    </td>
  </tr>
  
  <tr>
    <td>
      Node VM
    </td>
    
    <td>
      Proxmox snapshot before each <code>
        setup-node.sh
      </code>
      
       re-run
    </td>
    
    <td>
      Roll back
    </td>
  </tr>
  
  <tr>
    <td>
      Workload checkouts
    </td>
    
    <td>
      none
    </td>
    
    <td>
      Redeploy from GitHub
    </td>
  </tr>
  
  <tr>
    <td>
      Logs, traces, metrics
    </td>
    
    <td>
      none
    </td>
    
    <td>
      Accepted loss
    </td>
  </tr>
</tbody>
</table>

## Migration to Kinotic Cloud

Everything a peer has lives in one of these places, and moves as follows:

<table>
<thead>
  <tr>
    <th>
      State
    </th>
    
    <th>
      Lives in
    </th>
    
    <th>
      Moves by
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      Organizations, identities, credentials, OIDC configurations, applications, projects, entity definitions, named queries, refresh tokens, GitHub installations, job runs
    </td>
    
    <td>
      os-data indices (<code>
        V1__init.sql
      </code>
      
      )
    </td>
    
    <td>
      The last snapshot, restored into the cloud cluster
    </td>
  </tr>
  
  <tr>
    <td>
      Customer entity data
    </td>
    
    <td>
      entity indices
    </td>
    
    <td>
      The same snapshot
    </td>
  </tr>
  
  <tr>
    <td>
      Access tokens in flight
    </td>
    
    <td>
      Signed with the development server's <code>
        jwt-signing-keys
      </code>
    </td>
    
    <td>
      That key is added to the cloud key set as a non-active entry; <code>
        KinoticJwtIssuer
      </code>
      
       verifies by <code>
        kid
      </code>
      
       against every key in the set, so tokens it issued verify until they expire
    </td>
  </tr>
  
  <tr>
    <td>
      Refresh tokens
    </td>
    
    <td>
      <code>
        kinotic_refresh_token
      </code>
      
       rows, opaque and hashed
    </td>
    
    <td>
      Restored with the snapshot; they do not depend on the signing key
    </td>
  </tr>
  
  <tr>
    <td>
      Stored secrets
    </td>
    
    <td>
      The development server's Key Vault
    </td>
    
    <td>
      Copied to the cluster vault, or the cluster is pointed at this vault until it is empty
    </td>
  </tr>
  
  <tr>
    <td>
      Published UIs
    </td>
    
    <td>
      The development server's sites account under <code>
        sites/<label>.apps-dev.kinotic.ai/
      </code>
    </td>
    
    <td>
      Each project is redeployed after cutover; the URL changes to <code>
        <label>.apps.kinotic.ai
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      Deployments and checkouts
    </td>
    
    <td>
      <code>
        kinotic_project_deployment.nodeId
      </code>
      
       and <code>
        hostDir
      </code>
      
      , the node's disk
    </td>
    
    <td>
      Scrubbed, then redeployed
    </td>
  </tr>
  
  <tr>
    <td>
      Sessions, logs, traces, metrics
    </td>
    
    <td>
      Ignite, Loki, Tempo, Mimir
    </td>
    
    <td>
      Not migrated
    </td>
  </tr>
</tbody>
</table>

The cutover, in order:

1. Announce a freeze to the peers; take a final snapshot; stop the development server.
2. Restore the snapshot into the cloud os-data and entity clusters. Both clusters must be at the
development server's migration version or carry only appended migrations beyond it.
3. Delete the restored `kinotic_vm_node` and `kinotic_workload` rows and clear `nodeId`,
`hostDir`, `syncWorkloadId`, and `uiPublishWorkloadId` on every `kinotic_project_deployment`
row. `resolveTarget` reuses an existing deployment's node without checking it exists, so a row
still pointing at the development node would fail its next deployment. A re-home operation
is the durable version of this step.
4. Add the development server's signing key to the cloud `jwt-signing-keys` set, inactive; drop
it a day later.
5. Point the GitHub App's webhook URL at the cloud gateway.
6. Re-run the deployment of every project, which places it on a cloud node and republishes its
UIs under `apps.kinotic.ai`.
7. Change `dev.kinotic.ai` to a CNAME of the cloud gateway so the peers' CLI logins and project
configuration keep resolving; they sign in again on `portal.kinotic.ai`, since sessions are
`__Host-` cookies and the social-login redirect URIs are registered per host.
8. Disable the development node's machine identity; keep the development server running
read-only for a week, then tear it down.

## Build order

1. **Azure root.** `deployment/terraform/azure/dev-server`; apply; record the outputs.
2. **Host.** Proxmox on disk 0; `deployment/proxmox/terraform` with the two VMs, the passthrough
disks, and cloud-init.
3. **Platform VM.** `compose.elasticsearch-dev-server.yml`, `compose.dev-server.yml`,
`application-dev-server.yml`; the env file, platform secrets, and certbot; bring up ES,
register the snapshot repository and SLM policy, run the migration, start the server; confirm
sign-up mail arrives and the portal loads on `https://dev.kinotic.ai`.
4. **Node VM.** Partition and mount disk 1; run the kit; enable default-deny; create the machine
in the system console; start the vm-manager; confirm the node is `ONLINE` with no health
message.
5. **End to end.** Deploy the template project from a peer's organization: the sync VM fetches
through the allowlist, the runtime VM registers its microservice, the UI appears at
`<label>.apps-dev.kinotic.ai`, and the run's log and the microservice's traces show in the
portal.
6. **Restore drill.** Restore the previous night's snapshot into a scratch cluster and open the
portal against it. Repeat before cutover.

## Open items

- `setup-node.sh` persists its own loop image in `/etc/fstab` even when `/var/lib/docker` is
already a mounted disk, then verifies the fstab. With a passthrough disk mounted first, the
kit needs to accept a pre-mounted data root before step 4 runs.
- The compose stack runs Elasticsearch 9.5.1 and the ECK values pin 9.0.2. The cloud cluster
must be at or above the development server's version on restore day.
- Tempo and Mimir are in compose and not in Helm. When the charts gain them nothing changes on
the development server; until then the cloud has no trace or metric history to migrate into,
which is accepted.
- Whether `dev.kinotic.ai` stays as a CNAME indefinitely or is retired after the peers have
moved their configuration.
- The one-time scrub in cutover step 3 versus building the re-home operation before cutover.
- Whether the node's IPv4-only egress allowlist for GitHub and npm is maintained by hand from
their published ranges or by a resolver-backed ipset, which the kit's README names as the path
for hostname allowlists.
