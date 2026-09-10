# ── The development server on Proxmox ────────────────────────────────────────
# One container per service and one VM, on one host. Every service the compose stack runs
# locally — kinotic-server, the one-shot migration, three Elasticsearch nodes, Loki, Tempo,
# Mimir, Grafana — is an unprivileged LXC container created from the image compose pulls,
# with its state in a host directory: the Elasticsearch nodes on a physical disk each. The
# node VM runs the vm-manager with the Cloud Hypervisor provider under nested KVM; it needs
# its own kernel, and cannot share an operating system with the gateway anyway, since its
# firewall floor drops every guest packet addressed to the node itself.
#
# Terraform owns what the Proxmox API exposes: the private network, the images, the
# containers with their mounts, the VM, and the files it uploads to the host. What the API
# does not take for a container created from an OCI image yet — the environment its
# entrypoint sees (bpg/terraform-provider-proxmox#2789) — host/kinotic-apply-container.py
# applies on the host from a manifest per container, merging in the secrets the operator
# placed there, so nothing secret passes through this root or its state (README.md).

data "terraform_remote_state" "azure" {
  backend = "local"
  config = {
    path = var.azure_state_path
  }
}

locals {
  azure = data.terraform_remote_state.azure.outputs

  server_ip  = split("/", var.server_ip)[0]
  loki_ip    = split("/", var.loki_ip)[0]
  tempo_ip   = split("/", var.tempo_ip)[0]
  mimir_ip   = split("/", var.mimir_ip)[0]
  grafana_ip = split("/", var.grafana_ip)[0]
  node_ip    = split("/", var.node_ip)[0]

  # The private network: the host is its gateway, the ES nodes take .11 to .13
  private_prefix       = split("/", var.private_cidr)[1]
  private_gateway      = cidrhost(var.private_cidr, 1)
  es_ips               = [for i in range(3) : cidrhost(var.private_cidr, 11 + i)]
  server_private_ip    = cidrhost(var.private_cidr, 20)
  migration_private_ip = cidrhost(var.private_cidr, 21)

  compose_dir = "${path.module}/../../docker-compose"
  vm_node_dir = "${path.module}/../../vm-node"
  config_root = "${var.data_dir}/config"
  data_root   = "${var.data_dir}/data"

  # The cluster is healthy once all three nodes have joined; the migration and the server
  # wait for it, and the host reaches the private network directly
  es_healthy = "curl -sf 'http://${local.es_ips[0]}:9200/_cluster/health?wait_for_nodes=3&wait_for_status=yellow&timeout=30s' >/dev/null"

  # Loki, Tempo, Mimir and Grafana run the compose stack's config files, with the compose
  # service names replaced by the containers' addresses. Grafana's `$$` is compose escaping.
  service_urls = {
    "http://loki:3100"  = "http://${local.loki_ip}:3100"
    "http://mimir:9009" = "http://${local.mimir_ip}:9009"
    "http://tempo:3200" = "http://${local.tempo_ip}:3200"
  }
  tempo_config = replace(file("${local.compose_dir}/tempo.yml"), "http://mimir:9009", local.service_urls["http://mimir:9009"])
  grafana_datasources = replace(replace(replace(replace(file("${local.compose_dir}/grafana-datasource.yaml"),
    "http://loki:3100", local.service_urls["http://loki:3100"]),
    "http://mimir:9009", local.service_urls["http://mimir:9009"]),
    "http://tempo:3200", local.service_urls["http://tempo:3200"]),
  "$$", "$")

  es_nodes = { for i in range(3) : "es-${i + 1}" => {
    vm_id    = 101 + i
    ip       = local.es_ips[i]
    data_dir = var.es_data_dirs[i]
  } }

  # Every setting the docker image turns into -E flags. Security is off: the cluster is
  # reachable only on the private network, from the containers on it and the host.
  es_env = {
    "cluster.name"                 = "kinotic"
    "cluster.initial_master_nodes" = join(",", keys(local.es_nodes))
    "discovery.seed_hosts"         = join(",", local.es_ips)
    "node.roles"                   = "master,data,ingest,transform"
    "xpack.security.enabled"       = "false"
    "ES_JAVA_OPTS"                 = "-Xms${var.es_memory_mb / 2}m -Xmx${var.es_memory_mb / 2}m"
  }

  es_containers = { for name, node in local.es_nodes : name => {
    vm_id         = node.vm_id
    description   = "Elasticsearch ${name}: master-eligible, data on ${node.data_dir}"
    image         = proxmox_oci_image.elasticsearch.id
    cores         = var.es_cores
    memory        = var.es_memory_mb
    order         = 10
    start_on_boot = true
    run_once      = false
    interfaces    = [{ bridge = var.private_network, address = "${node.ip}/${local.private_prefix}", gateway = local.private_gateway }]
    mounts        = [{ volume = node.data_dir, path = "/usr/share/elasticsearch/data", read_only = false }]
    entrypoint    = null
    env           = merge(local.es_env, { "node.name" = name })
    secrets_env   = null
    files         = {}
    uid           = 1000
    gid           = 0
    dirs          = [node.data_dir]
    wait_for      = null
    verify        = null
    timeout       = 300
  } }

  # The non-secret half of the server's environment: the compose service's, the Azure root's
  # outputs, and the addresses only this root knows. The secret half is merged on the host.
  server_env = merge(local.azure.dev_server_env, merge([for i, ip in local.es_ips : {
    "KINOTIC_DOMAIN_ELASTICCONNECTIONS_${i}_SCHEME" = "http"
    "KINOTIC_DOMAIN_ELASTICCONNECTIONS_${i}_HOST"   = ip
    "KINOTIC_DOMAIN_ELASTICCONNECTIONS_${i}_PORT"   = "9200"
    }]...), {
    SPRING_PROFILES_ACTIVE      = "production,dev-server"
    BPL_JVM_HEAD_ROOM           = "10"
    JAVA_TOOL_OPTIONS           = "-XX:MaxDirectMemorySize=512m -javaagent:/workspace/BOOT-INF/classes/opentelemetry-javaagent.jar --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.invoke=ALL-UNNAMED"
    KINOTIC_MAX_OFF_HEAP_MEMORY = "419430400"

    KINOTIC_DOMAIN_APPBASEURL    = "https://${local.azure.hostname}"
    KINOTIC_DOMAIN_APIBASEURL    = "https://${local.azure.hostname}:58503"
    KINOTIC_DOMAIN_EMAIL_ENABLED = "true"
    # What a workload on the node VM dials, and the one destination every egress policy permits
    KINOTIC_SYSTEMAPI_DEPLOYMENT_SERVERHOST = local.server_ip
    KINOTIC_MANAGEMENTAPI_LOKIURL           = local.service_urls["http://loki:3100"]
    KINOTIC_MANAGEMENTAPI_TEMPOURL          = local.service_urls["http://tempo:3200"]
    KINOTIC_MANAGEMENTAPI_MIMIRURL          = local.service_urls["http://mimir:9009"]

    # No collector: the agent exports each signal to its store's OTLP endpoint, under the
    # platform tenant, which is what the compose collector stamps on the server's telemetry
    OTEL_SERVICE_NAME                   = "kinotic-server"
    OTEL_RESOURCE_ATTRIBUTES            = "service.name=kinotic-server"
    OTEL_TRACES_EXPORTER                = "otlp"
    OTEL_METRICS_EXPORTER               = "otlp"
    OTEL_LOGS_EXPORTER                  = "otlp"
    OTEL_EXPORTER_OTLP_PROTOCOL         = "http/protobuf"
    OTEL_EXPORTER_OTLP_TRACES_ENDPOINT  = "http://${local.tempo_ip}:4318/v1/traces"
    OTEL_EXPORTER_OTLP_METRICS_ENDPOINT = "http://${local.mimir_ip}:9009/otlp/v1/metrics"
    OTEL_EXPORTER_OTLP_LOGS_ENDPOINT    = "http://${local.loki_ip}:3100/otlp/v1/logs"
    OTEL_EXPORTER_OTLP_HEADERS          = "X-Scope-OrgID=kinotic-system"
    # jvm.buffer.* and jvm.system.cpu.*, which the agent gates behind this flag
    OTEL_INSTRUMENTATION_RUNTIME_TELEMETRY_EMIT_EXPERIMENTAL_TELEMETRY = "true"
    # Names the bare transport spans under each Elasticsearch call
    OTEL_INSTRUMENTATION_COMMON_PEER_SERVICE_MAPPING = join(",", [for ip in local.es_ips : "${ip}:9200=elasticsearch"])
  })

  containers = merge(local.es_containers, {
    loki = {
      vm_id         = 110
      description   = "Loki: logs, multi-tenant; the node's Alloy and the server push here"
      image         = proxmox_oci_image.loki.id
      cores         = 2
      memory        = 1024
      order         = 20
      start_on_boot = true
      run_once      = false
      interfaces    = [{ bridge = var.bridge, address = var.loki_ip, gateway = var.gateway }]
      mounts        = [{ volume = "${local.data_root}/loki", path = "/loki", read_only = false }]
      entrypoint    = "/usr/bin/loki -config.file=/etc/loki/local-config.yaml -auth.enabled=true -querier.multi-tenant-queries-enabled=true"
      env           = {}
      secrets_env   = null
      files         = {}
      uid           = 10001
      gid           = 10001
      dirs          = ["${local.data_root}/loki"]
      wait_for      = null
      verify        = null
      timeout       = 300
    }
    tempo = {
      vm_id         = 111
      description   = "Tempo: traces, multi-tenant; span metrics to Mimir"
      image         = proxmox_oci_image.tempo.id
      cores         = 2
      memory        = 1024
      order         = 20
      start_on_boot = true
      run_once      = false
      interfaces    = [{ bridge = var.bridge, address = var.tempo_ip, gateway = var.gateway }]
      mounts = [
        { volume = "${local.config_root}/tempo", path = "/etc/tempo", read_only = true },
        { volume = "${local.data_root}/tempo", path = "/var/tempo", read_only = false },
      ]
      entrypoint  = "/tempo -config.file=/etc/tempo/tempo.yml"
      env         = {}
      secrets_env = null
      files       = { "tempo.yml" = local.tempo_config }
      uid         = 10001
      gid         = 10001
      dirs        = ["${local.data_root}/tempo"]
      wait_for    = null
      verify      = null
      timeout     = 300
    }
    mimir = {
      vm_id         = 112
      description   = "Mimir: metrics, multi-tenant"
      image         = proxmox_oci_image.mimir.id
      cores         = 2
      memory        = 2048
      order         = 20
      start_on_boot = true
      run_once      = false
      interfaces    = [{ bridge = var.bridge, address = var.mimir_ip, gateway = var.gateway }]
      mounts = [
        { volume = "${local.config_root}/mimir", path = "/etc/mimir", read_only = true },
        { volume = "${local.data_root}/mimir", path = "/var/mimir", read_only = false },
      ]
      entrypoint  = "/bin/mimir -target=all -config.file=/etc/mimir/mimir.yml"
      env         = {}
      secrets_env = null
      files       = { "mimir.yml" = file("${local.compose_dir}/mimir.yml") }
      uid         = 10001
      gid         = 10001
      dirs        = ["${local.data_root}/mimir"]
      wait_for    = null
      verify      = null
      timeout     = 300
    }
    grafana = {
      vm_id         = 113
      description   = "Grafana on :3000, with a login: the LAN reaches it"
      image         = proxmox_oci_image.grafana.id
      cores         = 1
      memory        = 512
      order         = 20
      start_on_boot = true
      run_once      = false
      interfaces    = [{ bridge = var.bridge, address = var.grafana_ip, gateway = var.gateway }]
      # The data directory first: the dashboards mount lands inside it
      mounts = [
        { volume = "${local.data_root}/grafana", path = "/var/lib/grafana", read_only = false },
        { volume = "${local.config_root}/grafana/dashboards", path = "/var/lib/grafana/dashboards", read_only = true },
        { volume = "${local.config_root}/grafana/provisioning/datasources", path = "/etc/grafana/provisioning/datasources", read_only = true },
        { volume = "${local.config_root}/grafana/provisioning/dashboards", path = "/etc/grafana/provisioning/dashboards", read_only = true },
      ]
      entrypoint = null
      env = {
        GF_AUTH_ANONYMOUS_ENABLED                 = "false"
        GF_SECURITY_ADMIN_USER                    = "admin"
        GF_DASHBOARDS_DEFAULT_HOME_DASHBOARD_PATH = "/var/lib/grafana/dashboards/kinotic-server.json"
      }
      secrets_env = "${var.secrets_dir}/grafana.env"
      files = {
        "provisioning/datasources/datasource.yaml" = local.grafana_datasources
        "provisioning/dashboards/dashboards.yaml"  = file("${local.compose_dir}/grafana-dashboards.yaml")
        "dashboards/kinotic-server.json"           = file("${local.compose_dir}/dashboards/kinotic-server.json")
      }
      uid      = 472
      gid      = 0
      dirs     = ["${local.data_root}/grafana"]
      wait_for = null
      verify   = null
      timeout  = 300
    }
    kinotic-migration = {
      vm_id         = 120
      description   = "The one-shot migration: runs to completion against es-1, once per image and configuration"
      image         = proxmox_oci_image.kinotic_migration.id
      cores         = 2
      memory        = 2048
      order         = 25
      start_on_boot = false
      run_once      = true
      interfaces    = [{ bridge = var.private_network, address = "${local.migration_private_ip}/${local.private_prefix}", gateway = local.private_gateway }]
      mounts        = []
      entrypoint    = null
      # The production profile applies no fixture migration: no test users, no console samples
      env = {
        SPRING_PROFILES_ACTIVE           = "production"
        KINOTIC_MIGRATION_ELASTIC_SCHEME = "http"
        KINOTIC_MIGRATION_ELASTIC_HOST   = local.es_ips[0]
        KINOTIC_MIGRATION_ELASTIC_PORT   = "9200"
      }
      secrets_env = null
      files       = {}
      uid         = 1000
      gid         = 1000
      dirs        = []
      wait_for    = local.es_healthy
      verify      = "curl -sf http://${local.es_ips[0]}:9200/migration_history >/dev/null"
      timeout     = 900
    }
    kinotic-server = {
      vm_id         = 121
      description   = "kinotic-server: the portal on :9090, REST, STOMP and MCP on :58503, both TLS; the router forwards here"
      image         = proxmox_oci_image.kinotic_server.id
      cores         = var.server_cores
      memory        = var.server_memory_mb
      order         = 30
      start_on_boot = true
      run_once      = false
      # The LAN for peers, the node VM and Azure; the private network for Elasticsearch
      interfaces = [
        { bridge = var.bridge, address = var.server_ip, gateway = var.gateway },
        { bridge = var.private_network, address = "${local.server_private_ip}/${local.private_prefix}", gateway = null },
      ]
      # secrets.yml, the JWT key set and the certificate, placed by sync-secrets.sh and certbot
      mounts      = [{ volume = "${var.secrets_dir}/kinotic-server", path = "/etc/kinotic", read_only = true }]
      entrypoint  = null
      env         = local.server_env
      secrets_env = "${var.secrets_dir}/kinotic-server.env"
      files       = {}
      uid         = 1000
      gid         = 1000
      dirs        = []
      wait_for    = local.es_healthy
      verify      = null
      timeout     = 300
    }
  })

  # The startup order is the apply order too: Elasticsearch, the stores, the migration, the server
  apply_order = [for entry in sort([for name, c in local.containers : format("%02d %s", c.order, name)]) : split(" ", entry)[1]]

  # Every config file, uploaded flat as a snippet and copied by the applier into the host
  # directory the container bind-mounts
  config_uploads = merge([for name, c in local.containers : {
    for rel, content in c.files : "${name}/${rel}" => {
      container = name
      file_name = "kinotic-${name}-${replace(rel, "/", "-")}"
      content   = content
      dst       = "${local.config_root}/${name}/${rel}"
    }
  }]...)

  manifests = { for name, c in local.containers : name => {
    vmid        = c.vm_id
    name        = name
    image       = c.image
    env         = c.env
    secrets_env = c.secrets_env
    files = [for key, upload in local.config_uploads : {
      src  = "${var.snippets_dir}/${upload.file_name}"
      dst  = upload.dst
      mode = "0644"
      uid  = c.uid
      gid  = c.gid
    } if upload.container == name]
    dirs        = [for dir in c.dirs : { path = dir, uid = c.uid, gid = c.gid }]
    console_log = "/var/log/kinotic/${name}.log"
    run_once    = c.run_once
    wait_for    = c.wait_for
    verify      = c.verify
    timeout     = c.timeout
  } }

  vm_manager_env = <<-EOT
    KINOTIC_VM_PROVIDER=CLOUD_HYPERVISOR
    KINOTIC_NODE_ID=${var.node_id}
    KINOTIC_SERVER_HOST=${local.server_ip}
    KINOTIC_SERVER_PORT=58503
    KINOTIC_SERVER_USE_SSL=true
    KINOTIC_WORKLOAD_DATA_DIR=/var/lib/kinotic/workloads
    KINOTIC_WORKLOAD_DNS=${var.dns_servers[0]}
    KINOTIC_LOKI_URL=${local.service_urls["http://loki:3100"]}
    KINOTIC_TEMPO_URL=http://${local.tempo_ip}:4318
    KINOTIC_MIMIR_URL=${local.service_urls["http://mimir:9009"]}/otlp
  EOT

  vm_node_files = [
    "setup-node.sh",
    "verify-node.sh",
    "kinotic-node-firewall",
    "install-vm-manager.sh",
    "kinotic-vm-manager.service",
  ]
}

# ── The private network ───────────────────────────────────────────────────────
# A simple SDN zone: a bridge with no physical port, whose subnet the host gateways and
# source-NATs, so the Elasticsearch nodes reach the snapshot container in Azure while nothing
# on the LAN reaches them.

resource "proxmox_sdn_zone_simple" "private" {
  id    = var.private_network
  nodes = [var.proxmox_node]
}

resource "proxmox_sdn_vnet" "private" {
  id   = var.private_network
  zone = proxmox_sdn_zone_simple.private.id
}

resource "proxmox_sdn_subnet" "private" {
  vnet    = proxmox_sdn_vnet.private.id
  cidr    = var.private_cidr
  gateway = local.private_gateway
  snat    = true
}

# SDN objects are pending until the cluster's SDN configuration is applied
resource "proxmox_sdn_applier" "private" {
  depends_on = [proxmox_sdn_subnet.private]

  lifecycle {
    replace_triggered_by = [proxmox_sdn_zone_simple.private, proxmox_sdn_vnet.private, proxmox_sdn_subnet.private]
  }
}

# ── Images ────────────────────────────────────────────────────────────────────
# The images compose pulls, as container templates. A tag is pulled once: to pick up a
# republished SNAPSHOT, replace the image and the containers built from it (README.md).

resource "proxmox_oci_image" "kinotic_server" {
  node_name    = var.proxmox_node
  datastore_id = var.files_datastore_id
  reference    = "docker.io/kinoticai/kinotic-server:${var.kinotic_version}"
}

resource "proxmox_oci_image" "kinotic_migration" {
  node_name    = var.proxmox_node
  datastore_id = var.files_datastore_id
  reference    = "docker.io/kinoticai/kinotic-migration:${var.kinotic_version}"
}

resource "proxmox_oci_image" "elasticsearch" {
  node_name    = var.proxmox_node
  datastore_id = var.files_datastore_id
  reference    = "docker.elastic.co/elasticsearch/elasticsearch:${var.elasticsearch_version}"
}

resource "proxmox_oci_image" "loki" {
  node_name    = var.proxmox_node
  datastore_id = var.files_datastore_id
  reference    = "docker.io/grafana/loki:${var.loki_version}"
}

resource "proxmox_oci_image" "tempo" {
  node_name    = var.proxmox_node
  datastore_id = var.files_datastore_id
  reference    = "docker.io/grafana/tempo:${var.tempo_version}"
}

resource "proxmox_oci_image" "mimir" {
  node_name    = var.proxmox_node
  datastore_id = var.files_datastore_id
  reference    = "docker.io/grafana/mimir:${var.mimir_version}"
}

resource "proxmox_oci_image" "grafana" {
  node_name    = var.proxmox_node
  datastore_id = var.files_datastore_id
  reference    = "docker.io/grafana/grafana:${var.grafana_version}"
}

resource "proxmox_download_file" "node_image" {
  content_type = "iso"
  datastore_id = var.iso_datastore_id
  node_name    = var.proxmox_node
  url          = var.node_image_url
  file_name    = "kinotic-node-cloudimg-amd64.img"
  overwrite    = false
}

# ── Files on the host ─────────────────────────────────────────────────────────

resource "proxmox_virtual_environment_file" "applier" {
  content_type = "snippets"
  datastore_id = var.files_datastore_id
  node_name    = var.proxmox_node

  source_raw {
    file_name = "kinotic-apply-container.py"
    data      = file("${path.module}/host/kinotic-apply-container.py")
  }
}

resource "proxmox_virtual_environment_file" "config" {
  for_each = local.config_uploads

  content_type = "snippets"
  datastore_id = var.files_datastore_id
  node_name    = var.proxmox_node

  source_raw {
    file_name = each.value.file_name
    data      = each.value.content
  }
}

resource "proxmox_virtual_environment_file" "manifest" {
  for_each = local.manifests

  content_type = "snippets"
  datastore_id = var.files_datastore_id
  node_name    = var.proxmox_node

  source_raw {
    file_name = "kinotic-${each.key}.manifest.json"
    data      = jsonencode(each.value)
  }
}

resource "proxmox_virtual_environment_file" "node_cloud_init" {
  content_type = "snippets"
  datastore_id = var.files_datastore_id
  node_name    = var.proxmox_node

  source_raw {
    file_name = "kinotic-node.cloud-config.yaml"
    data = templatefile("${path.module}/cloud-init/node.yaml.tftpl", {
      ssh_public_key     = var.ssh_public_key
      vm_manager_env     = local.vm_manager_env
      vm_manager_version = var.vm_manager_version
      vm_node_files      = { for f in local.vm_node_files : f => base64encode(file("${local.vm_node_dir}/${f}")) }
    })
  }
}

# ── The containers ────────────────────────────────────────────────────────────

resource "proxmox_virtual_environment_container" "fleet" {
  for_each = local.containers

  node_name    = var.proxmox_node
  vm_id        = each.value.vm_id
  description  = each.value.description
  tags         = ["kinotic", "dev-server"]
  unprivileged = true

  # The applier starts a container once its environment is in place and stops it to change
  # it, so whether one runs is not this resource's to reconcile
  started       = false
  start_on_boot = each.value.start_on_boot

  operating_system {
    template_file_id = each.value.image
  }

  cpu {
    cores = each.value.cores
  }

  memory {
    dedicated = each.value.memory
    swap      = 0
  }

  disk {
    datastore_id = var.vm_datastore_id
    size         = 8
  }

  dynamic "network_interface" {
    for_each = each.value.interfaces
    content {
      name   = "eth${network_interface.key}"
      bridge = network_interface.value.bridge
    }
  }

  initialization {
    hostname   = each.key
    entrypoint = each.value.entrypoint

    dns {
      servers = var.dns_servers
    }

    dynamic "ip_config" {
      for_each = each.value.interfaces
      content {
        ipv4 {
          address = ip_config.value.address
          gateway = ip_config.value.gateway
        }
      }
    }
  }

  dynamic "mount_point" {
    for_each = each.value.mounts
    content {
      volume    = mount_point.value.volume
      path      = mount_point.value.path
      read_only = mount_point.value.read_only
    }
  }

  startup {
    order    = each.value.order
    up_delay = each.value.order == 10 ? 30 : 0
  }

  lifecycle {
    ignore_changes = [started]
  }

  depends_on = [proxmox_sdn_applier.private]
}

# Runs the applier over every manifest in startup order, after any of them, their config
# files, or the applier itself changed. A container replaced by hand keeps its vmid, so
# nothing here notices: run the same command yourself (README.md).
resource "terraform_data" "apply" {
  triggers_replace = {
    applier   = sha256(file("${path.module}/host/kinotic-apply-container.py"))
    manifests = { for name, m in local.manifests : name => sha256(jsonencode(m)) }
    configs   = { for key, upload in local.config_uploads : key => sha256(upload.content) }
  }

  provisioner "local-exec" {
    command = "ssh -o BatchMode=yes root@${var.proxmox_host} python3 ${var.snippets_dir}/kinotic-apply-container.py ${join(" ", [for name in local.apply_order : "${var.snippets_dir}/kinotic-${name}.manifest.json"])}"
  }

  depends_on = [
    proxmox_virtual_environment_container.fleet,
    proxmox_virtual_environment_file.applier,
    proxmox_virtual_environment_file.config,
    proxmox_virtual_environment_file.manifest,
  ]
}

# ── The node VM ───────────────────────────────────────────────────────────────

resource "proxmox_virtual_environment_vm" "node" {
  node_name   = var.proxmox_node
  vm_id       = 200
  name        = "kinotic-dev-node"
  description = "vm-manager with the Cloud Hypervisor provider (deployment/vm-node)"
  tags        = ["kinotic", "dev-server"]
  on_boot     = true

  agent {
    enabled = true
  }

  # type = host exposes the CPU's virtualization extensions, which the Kata micro VMs need
  cpu {
    cores = var.node_cores
    type  = "host"
  }

  memory {
    dedicated = var.node_memory_mb
  }

  scsi_hardware = "virtio-scsi-single"
  boot_order    = ["scsi0"]

  disk {
    datastore_id = var.vm_datastore_id
    file_id      = proxmox_download_file.node_image.id
    interface    = "scsi0"
    size         = var.node_os_disk_gb
    discard      = "on"
    iothread     = true
  }

  # scsi1: Docker's data root and the workload checkouts, partitioned by cloud-init
  disk {
    datastore_id = var.vm_datastore_id
    interface    = "scsi1"
    size         = var.node_data_disk_gb
    discard      = "on"
    iothread     = true
  }

  network_device {
    bridge = var.bridge
    model  = "virtio"
  }

  operating_system {
    type = "l26"
  }

  serial_device {}

  # The server comes up before the node connects to it
  startup {
    order = 40
  }

  initialization {
    datastore_id      = var.vm_datastore_id
    user_data_file_id = proxmox_virtual_environment_file.node_cloud_init.id

    ip_config {
      ipv4 {
        address = var.node_ip
        gateway = var.gateway
      }
    }

    dns {
      servers = var.dns_servers
    }
  }
}
