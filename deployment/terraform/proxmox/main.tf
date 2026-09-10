# ── The development server on Proxmox ────────────────────────────────────────
# Two VMs on one host: the platform VM runs the compose stack — kinotic-server, three
# Elasticsearch nodes on their own disks, the observability stack — and the node VM runs the
# vm-manager with the Cloud Hypervisor provider under nested KVM. The two cannot share an
# operating system: the node's firewall floor drops every guest packet addressed to the node
# itself, so a gateway on the node's own address is unreachable from every workload.
#
# Terraform owns what the Proxmox API exposes: images, cloud-init, the VMs and their disks.
# What is inside each VM is cloud-init's, run once on first boot from the templates under
# cloud-init/, and the files it lays down come straight from this repository — the compose
# stack from deployment/docker-compose, the node kit from deployment/vm-node. Nothing secret
# passes through here: credentials are placed on the VMs by hand (see README.md).

data "terraform_remote_state" "azure" {
  backend = "local"
  config = {
    path = var.azure_state_path
  }
}

locals {
  azure       = data.terraform_remote_state.azure.outputs
  platform_ip = split("/", var.platform_ip)[0]
  node_ip     = split("/", var.node_ip)[0]

  compose_dir = "${path.module}/../../docker-compose"
  # The stack exactly as compose.dev-server.yml includes it, plus the config files those
  # services mount; cloud-init writes them under /opt/kinotic/compose on the platform VM
  compose_files = [
    "compose.dev-server.yml",
    "compose.elasticsearch-dev-server.yml",
    "compose-otel.yml",
    "compose-otel.dev-server.yml",
    "compose.kinotic-migration.yml",
    "compose.kinotic-migration.dev-server.yml",
    "compose.kinotic-server.yml",
    "compose.kinotic-server.dev-server.yml",
    "otel-collector-config.yaml",
    "tempo.yml",
    "mimir.yml",
    "grafana-datasource.yaml",
    "grafana-dashboards.yaml",
    "dashboards/kinotic-server.json",
  ]

  vm_node_dir = "${path.module}/../../vm-node"
  vm_node_files = [
    "setup-node.sh",
    "verify-node.sh",
    "kinotic-node-firewall",
    "install-vm-manager.sh",
    "kinotic-vm-manager.service",
  ]

  # The non-secret half of the server's environment: the Azure outputs, plus the one value
  # only this root knows — where the gateway is, as the workloads on the node VM dial it
  dev_server_env = "${local.azure.dev_server_env}KINOTIC_SYSTEMAPI_DEPLOYMENT_SERVERHOST=${local.platform_ip}\n"

  vm_manager_env = <<-EOT
    KINOTIC_VM_PROVIDER=CLOUD_HYPERVISOR
    KINOTIC_NODE_ID=${var.node_id}
    KINOTIC_SERVER_HOST=${local.platform_ip}
    KINOTIC_SERVER_PORT=58503
    KINOTIC_SERVER_USE_SSL=true
    KINOTIC_WORKLOAD_DATA_DIR=/var/lib/kinotic/workloads
    KINOTIC_WORKLOAD_DNS=${var.dns_servers[0]}
    KINOTIC_LOKI_URL=http://${local.platform_ip}:3100
    KINOTIC_TEMPO_URL=http://${local.platform_ip}:4318
    KINOTIC_MIMIR_URL=http://${local.platform_ip}:9009/otlp
  EOT
}

# ── Images ────────────────────────────────────────────────────────────────────

resource "proxmox_download_file" "platform_image" {
  content_type = "iso"
  datastore_id = var.iso_datastore_id
  node_name    = var.proxmox_node
  url          = var.platform_image_url
  file_name    = "kinotic-platform-cloudimg-amd64.img"
  overwrite    = false
}

resource "proxmox_download_file" "node_image" {
  content_type = "iso"
  datastore_id = var.iso_datastore_id
  node_name    = var.proxmox_node
  url          = var.node_image_url
  file_name    = "kinotic-node-cloudimg-amd64.img"
  overwrite    = false
}

# ── cloud-init ────────────────────────────────────────────────────────────────

resource "proxmox_virtual_environment_file" "platform_cloud_init" {
  content_type = "snippets"
  datastore_id = var.snippets_datastore_id
  node_name    = var.proxmox_node

  source_raw {
    file_name = "kinotic-platform.cloud-config.yaml"
    data = templatefile("${path.module}/cloud-init/platform.yaml.tftpl", {
      ssh_public_key = var.ssh_public_key
      dev_server_env = local.dev_server_env
      compose_files  = { for f in local.compose_files : f => base64encode(file("${local.compose_dir}/${f}")) }
    })
  }
}

resource "proxmox_virtual_environment_file" "node_cloud_init" {
  content_type = "snippets"
  datastore_id = var.snippets_datastore_id
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

# ── The platform VM ───────────────────────────────────────────────────────────

resource "proxmox_virtual_environment_vm" "platform" {
  node_name   = var.proxmox_node
  name        = "kinotic-dev-platform"
  description = "kinotic-server, Elasticsearch, and the observability stack (deployment/docker-compose/compose.dev-server.yml)"
  tags        = ["kinotic", "dev-server"]
  on_boot     = true

  agent {
    enabled = true
  }

  cpu {
    cores = var.platform_cores
    type  = "host"
  }

  memory {
    dedicated = var.platform_memory_mb
  }

  scsi_hardware = "virtio-scsi-single"
  boot_order    = ["scsi0"]

  disk {
    datastore_id = var.vm_datastore_id
    file_id      = proxmox_download_file.platform_image.id
    interface    = "scsi0"
    size         = var.platform_os_disk_gb
    discard      = "on"
    iothread     = true
  }

  # scsi1..scsi3: the Elasticsearch disks, whole devices, in the order cloud-init labels them
  dynamic "disk" {
    for_each = { for i, d in var.es_disks : i => d }
    content {
      datastore_id      = ""
      path_in_datastore = disk.value.device
      file_format       = "raw"
      interface         = "scsi${disk.key + 1}"
      size              = disk.value.size_gb
    }
  }

  network_device {
    bridge = var.bridge
    model  = "virtio"
  }

  operating_system {
    type = "l26"
  }

  serial_device {}

  initialization {
    datastore_id      = var.vm_datastore_id
    user_data_file_id = proxmox_virtual_environment_file.platform_cloud_init.id

    ip_config {
      ipv4 {
        address = var.platform_ip
        gateway = var.gateway
      }
    }

    dns {
      servers = var.dns_servers
    }
  }
}

# ── The node VM ───────────────────────────────────────────────────────────────

resource "proxmox_virtual_environment_vm" "node" {
  node_name   = var.proxmox_node
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
    datastore_id      = ""
    path_in_datastore = var.node_disk.device
    file_format       = "raw"
    interface         = "scsi1"
    size              = var.node_disk.size_gb
  }

  network_device {
    bridge = var.bridge
    model  = "virtio"
  }

  operating_system {
    type = "l26"
  }

  serial_device {}

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
