<template>
  <div class="beta-diagram-wrap">
    <svg class="beta-diagram" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1172 860" role="img" aria-label="Home beta server topology: peers and GitHub reach kinotic-server over two forwarded ports; Azure keeps Front Door, the sites storage account, email, DNS, a Key Vault and a snapshot container; one Proxmox host runs a platform VM with kinotic-server, three Elasticsearch nodes and the observability stack, and a node VM running the vm-manager with Cloud Hypervisor micro VMs; each Elasticsearch node and the node runtime own a whole physical disk.">

      <defs>
        <marker id="hb-ink" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse">
          <polygon class="mk-ink" points="0,0 10,5 0,10"></polygon>
        </marker>
        <marker id="hb-ind" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse">
          <polygon class="mk-ind" points="0,0 10,5 0,10"></polygon>
        </marker>
        <marker id="hb-vio" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse">
          <polygon class="mk-vio" points="0,0 10,5 0,10"></polygon>
        </marker>
        <marker id="hb-amb" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse">
          <polygon class="mk-amb" points="0,0 10,5 0,10"></polygon>
        </marker>
      </defs>

      <!-- ═════════ internet band ═════════ -->
      <text class="t-tag" x="24" y="26">PEERS · INTERNET</text>

      <rect class="chip" x="40" y="44" width="160" height="46" rx="8"></rect>
      <text class="t-chip" x="120" y="63" text-anchor="middle">GitHub</text>
      <text class="t-sub"  x="120" y="79" text-anchor="middle">App webhooks · repo fetch</text>

      <rect class="chip" x="210" y="44" width="200" height="46" rx="8"></rect>
      <text class="t-chip" x="310" y="63" text-anchor="middle">Peers</text>
      <text class="t-sub"  x="310" y="79" text-anchor="middle">portal · CLI · MCP hosts</text>

      <!-- inbound: two forwarded ports, no reverse proxy -->
      <line class="flow" x1="120" y1="90" x2="120" y2="344" marker-end="url(#hb-ink)"></line>
      <text class="t-tiny" x="128" y="256">POST /api/github/webhook</text>
      <line class="flow" x1="310" y1="90" x2="310" y2="344" marker-end="url(#hb-ink)"></line>
      <text class="t-tiny" x="318" y="256">:443 → 9090 · :58503 → 58503</text>

      <!-- ═════════ Azure, kept ═════════ -->
      <rect class="encl-plat" x="660" y="24" width="488" height="202" rx="10"></rect>
      <text class="t-plane-p" x="676" y="46">AZURE · KEPT</text>

      <rect class="chip" x="676" y="64" width="120" height="46" rx="8"></rect>
      <text class="t-chip" x="736" y="83" text-anchor="middle">ACS</text>
      <text class="t-sub"  x="736" y="99" text-anchor="middle">email</text>

      <rect class="chip" x="812" y="64" width="140" height="46" rx="8"></rect>
      <text class="t-chip" x="882" y="83" text-anchor="middle">Front Door</text>
      <text class="t-sub"  x="882" y="99" text-anchor="middle">*.apps-beta.kinotic.ai</text>

      <rect class="chip" x="978" y="64" width="154" height="46" rx="8"></rect>
      <text class="t-chip" x="1055" y="83" text-anchor="middle">Sites storage account</text>
      <text class="t-sub"  x="1055" y="99" text-anchor="middle">sites/&lt;hostname&gt;/</text>
      <line class="flow-ind" x1="952" y1="87" x2="978" y2="87" marker-end="url(#hb-ind)"></line>

      <rect class="chip" x="676" y="148" width="136" height="46" rx="8"></rect>
      <text class="t-chip" x="744" y="167" text-anchor="middle">Key Vault</text>
      <text class="t-sub"  x="744" y="183" text-anchor="middle">server secret storage</text>

      <rect class="chip" x="828" y="148" width="140" height="46" rx="8"></rect>
      <text class="t-chip" x="898" y="167" text-anchor="middle">Blob container</text>
      <text class="t-sub"  x="898" y="183" text-anchor="middle">ES snapshots · daily</text>

      <rect class="chip" x="984" y="148" width="148" height="46" rx="8"></rect>
      <text class="t-chip" x="1058" y="167" text-anchor="middle">DNS zone</text>
      <text class="t-sub"  x="1058" y="183" text-anchor="middle">A record · DNS-01 certs</text>

      <!-- ═════════ home host ═════════ -->
      <rect class="wall" x="24" y="262" width="1124" height="580" rx="12"></rect>

      <!-- platform VM -->
      <rect class="encl-app" x="40" y="306" width="580" height="390" rx="10"></rect>
      <text class="t-plane-a" x="590" y="330" text-anchor="end">PLATFORM VM · DOCKER COMPOSE</text>

      <rect class="gw gw-app" x="60" y="344" width="290" height="112" rx="8"></rect>
      <text class="t-name" x="205" y="367" text-anchor="middle">kinotic-server</text>
      <text class="t-sub"  x="205" y="383" text-anchor="middle">UI :9090 · REST · STOMP · MCP :58503</text>
      <line class="sep" x1="78" y1="392" x2="332" y2="392"></line>
      <text class="t-mono" x="205" y="409" text-anchor="middle">Vert.x terminates TLS on both ports</text>
      <text class="t-mono" x="205" y="424" text-anchor="middle">profiles: production · compose · home</text>
      <text class="t-mono" x="205" y="439" text-anchor="middle">secrets → Key Vault · mail → ACS</text>

      <rect class="chip" x="370" y="402" width="220" height="46" rx="8"></rect>
      <text class="t-chip" x="480" y="421" text-anchor="middle">Loki · Tempo · Mimir · Grafana</text>
      <text class="t-sub"  x="480" y="437" text-anchor="middle">otel-collector in front</text>
      <line class="link" x1="350" y1="425" x2="370" y2="425" marker-end="url(#hb-ink)"></line>

      <!-- three ES nodes, one whole disk each -->
      <path class="cyl" d="M 54 586 a 56 9 0 0 0 112 0 v 38 a 56 9 0 0 1 -112 0 z"></path>
      <ellipse class="cyl" cx="110" cy="586" rx="56" ry="9"></ellipse>
      <text class="t-chip" x="110" y="606" text-anchor="middle">es-1</text>
      <text class="t-tiny" x="110" y="620" text-anchor="middle">master + data</text>

      <path class="cyl" d="M 234 586 a 56 9 0 0 0 112 0 v 38 a 56 9 0 0 1 -112 0 z"></path>
      <ellipse class="cyl" cx="290" cy="586" rx="56" ry="9"></ellipse>
      <text class="t-chip" x="290" y="606" text-anchor="middle">es-2</text>
      <text class="t-tiny" x="290" y="620" text-anchor="middle">master + data</text>

      <path class="cyl" d="M 414 586 a 56 9 0 0 0 112 0 v 38 a 56 9 0 0 1 -112 0 z"></path>
      <ellipse class="cyl" cx="470" cy="586" rx="56" ry="9"></ellipse>
      <text class="t-chip" x="470" y="606" text-anchor="middle">es-3</text>
      <text class="t-tiny" x="470" y="620" text-anchor="middle">master + data</text>

      <text class="t-tiny" x="200" y="660" text-anchor="middle">1 shard · 1 replica per index</text>

      <line class="link" x1="205" y1="456" x2="110" y2="577"></line>
      <line class="link" x1="205" y1="456" x2="290" y2="577"></line>
      <line class="link" x1="205" y1="456" x2="470" y2="577"></line>

      <!-- node VM -->
      <rect class="wbox" x="640" y="306" width="492" height="390" rx="10"></rect>
      <text class="t-plane-w" x="656" y="330">NODE VM · UBUNTU 22.04 · NESTED KVM</text>

      <rect class="chip" x="660" y="344" width="180" height="62" rx="8"></rect>
      <text class="t-chip" x="750" y="364" text-anchor="middle">vm-manager</text>
      <text class="t-tiny" x="750" y="380" text-anchor="middle">CLOUD_HYPERVISOR provider</text>
      <text class="t-tiny" x="750" y="394" text-anchor="middle">Alloy · machine credentials</text>

      <rect class="gw gw-node" x="660" y="426" width="452" height="112" rx="8"></rect>
      <text class="t-name" x="886" y="449" text-anchor="middle">Cloud Hypervisor micro VMs</text>
      <line class="sep" x1="678" y1="458" x2="1094" y2="458"></line>
      <text class="t-mono" x="886" y="475" text-anchor="middle">sync VM · runtime VM per microservice · UI publish VM</text>
      <text class="t-mono" x="886" y="490" text-anchor="middle">reach the gateway at the platform VM's IPv4</text>
      <text class="t-mono" x="886" y="505" text-anchor="middle">egress denied by default · allowlisted CIDRs only</text>
      <text class="t-mono" x="886" y="520" text-anchor="middle">stdout and stderr captured, shipped by Alloy</text>

      <rect class="chip" x="660" y="558" width="452" height="62" rx="8"></rect>
      <text class="t-chip" x="886" y="578" text-anchor="middle">Docker + kata-clh runtime</text>
      <text class="t-tiny" x="886" y="594" text-anchor="middle">XFS prjquota data root · icc: false · live-restore</text>
      <text class="t-tiny" x="886" y="608" text-anchor="middle">DOCKER-USER floor · egress default-deny</text>

      <line class="link" x1="750" y1="406" x2="750" y2="426" marker-end="url(#hb-ink)"></line>
      <line class="link" x1="886" y1="538" x2="886" y2="558" marker-end="url(#hb-ink)"></line>

      <!-- node → platform -->
      <line class="flow-vio" x1="660" y1="372" x2="350" y2="372" marker-end="url(#hb-vio)"></line>
      <text class="t-tiny" x="505" y="386" text-anchor="middle">STOMP · machine credentials · heartbeat</text>
      <line class="flow-vio" x1="660" y1="396" x2="590" y2="425" marker-end="url(#hb-vio)"></line>
      <text class="t-tiny" x="608" y="458" text-anchor="end">Alloy → Loki · Tempo · Mimir</text>
      <line class="flow-amb" x1="660" y1="480" x2="350" y2="456" marker-end="url(#hb-amb)"></line>
      <text class="t-tiny" x="490" y="492" text-anchor="middle">gateway = platform VM IPv4 · :58503 · TLS</text>

      <!-- platform → Azure: one service principal -->
      <polyline class="flow-ind" points="350,350 600,350 600,171 660,171" marker-end="url(#hb-ind)"></polyline>
      <text class="t-tiny" x="594" y="276" text-anchor="end">service principal</text>
      <text class="t-tiny" x="594" y="288" text-anchor="end">Key Vault · ACS · site URLs</text>

      <!-- ES → snapshot container -->
      <polyline class="data" points="526,586 630,586 630,254 898,254 898,194" marker-end="url(#hb-ink)"></polyline>
      <text class="t-tiny" x="764" y="247" text-anchor="middle">SLM daily snapshot</text>

      <!-- publish workload → sites account -->
      <polyline class="flow-amb" points="1112,482 1140,482 1140,87 1132,87" marker-end="url(#hb-amb)"></polyline>
      <text class="t-tiny" x="1128" y="300" text-anchor="end">signed upload URL</text>

      <!-- ═════════ disks ═════════ -->
      <text class="t-tag" x="40" y="738">DISKS · WHOLE-DISK PASSTHROUGH · ONE PER ES NODE</text>

      <rect class="disk" x="35" y="756" width="150" height="44" rx="8"></rect>
      <text class="t-chip" x="110" y="774" text-anchor="middle">disk 2</text>
      <text class="t-tiny" x="110" y="790" text-anchor="middle">es-1 data</text>
      <line class="data" x1="110" y1="756" x2="110" y2="636"></line>

      <rect class="disk" x="215" y="756" width="150" height="44" rx="8"></rect>
      <text class="t-chip" x="290" y="774" text-anchor="middle">disk 3</text>
      <text class="t-tiny" x="290" y="790" text-anchor="middle">es-2 data</text>
      <line class="data" x1="290" y1="756" x2="290" y2="636"></line>

      <rect class="disk" x="395" y="756" width="150" height="44" rx="8"></rect>
      <text class="t-chip" x="470" y="774" text-anchor="middle">disk 4</text>
      <text class="t-tiny" x="470" y="790" text-anchor="middle">es-3 data</text>
      <line class="data" x1="470" y1="756" x2="470" y2="636"></line>

      <rect class="disk" x="575" y="756" width="150" height="44" rx="8"></rect>
      <text class="t-chip" x="650" y="774" text-anchor="middle">disk 0</text>
      <text class="t-tiny" x="650" y="790" text-anchor="middle">Proxmox · VM OS images</text>

      <rect class="disk" x="811" y="756" width="150" height="44" rx="8"></rect>
      <text class="t-chip" x="886" y="774" text-anchor="middle">disk 1</text>
      <text class="t-tiny" x="886" y="790" text-anchor="middle">XFS prjquota · node</text>
      <line class="data" x1="886" y1="756" x2="886" y2="622"></line>

      <text class="t-tag" x="36" y="826">HOME HOST · PROXMOX VE · RYZEN 9 · 96 GB · 5 SSDS</text>
    </svg>
  </div>
</template>

<style>
/* Palette follows the site color-mode class, so the diagram tracks the theme toggle. */
svg.beta-diagram {
    --bg: #F6F7F9;
    --surface: #FFFFFF;
    --ink: #1A2332;
    --muted: #5C6879;
    --line: #D8DDE5;
    --indigo: #4A5FD0;
    --indigo-tint: rgba(74, 95, 208, 0.10);
    --green: #2C8F5E;
    --green-tint: rgba(44, 143, 94, 0.10);
    --violet: #7C4FD0;
    --amber: #A97C22;
    --amber-tint: rgba(169, 124, 34, 0.10);
    --pink: #B0487E;
    --host-tint: rgba(100, 116, 139, 0.07);
}
.dark svg.beta-diagram {
  --bg: #0D1320;
  --surface: #151D2D;
  --ink: #E6EBF4;
  --muted: #96A2B6;
  --line: #2B3648;
  --indigo: #7E8EF0;
  --indigo-tint: rgba(126, 142, 240, 0.14);
  --green: #48B482;
  --green-tint: rgba(72, 180, 130, 0.13);
  --violet: #A47EF0;
  --amber: #D3A24C;
  --amber-tint: rgba(211, 162, 76, 0.13);
  --pink: #D879AC;
  --host-tint: rgba(148, 163, 184, 0.08);
}

.beta-diagram-wrap { overflow-x: auto; }
svg.beta-diagram { min-width: 700px; width: 100%; height: auto; display: block; }

  /* ── SVG vocabulary ─────────────────────────────── */
  svg.beta-diagram .wall      { fill: var(--host-tint);  stroke: var(--line);   stroke-width: 1.5; }
  svg.beta-diagram .chip      { fill: var(--surface);    stroke: var(--line);   stroke-width: 1.25; }
  svg.beta-diagram .disk      { fill: var(--surface);    stroke: var(--muted);  stroke-width: 1.25; stroke-dasharray: 3 3; }
  svg.beta-diagram .gw        { fill: var(--surface);    stroke-width: 2; }
  svg.beta-diagram .gw-app    { stroke: var(--green); }
  svg.beta-diagram .gw-node   { stroke: var(--amber); }
  svg.beta-diagram .encl-app  { fill: var(--green-tint);  stroke: var(--green);  stroke-width: 1.25; stroke-dasharray: 6 5; }
  svg.beta-diagram .encl-plat { fill: var(--indigo-tint); stroke: var(--indigo); stroke-width: 1.25; stroke-dasharray: 6 5; }
  svg.beta-diagram .wbox      { fill: var(--amber-tint);  stroke: var(--amber);  stroke-width: 1.25; stroke-dasharray: 6 5; }
  svg.beta-diagram .cyl       { fill: var(--surface); stroke: var(--pink); stroke-width: 1.5; }
  svg.beta-diagram .sep       { stroke: var(--line); stroke-width: 1; }

  svg.beta-diagram .flow      { stroke: var(--ink);    stroke-width: 1.6; fill: none; }
  svg.beta-diagram .flow-ind  { stroke: var(--indigo); stroke-width: 1.8; fill: none; }
  svg.beta-diagram .flow-vio  { stroke: var(--violet); stroke-width: 1.6; fill: none; }
  svg.beta-diagram .flow-amb  { stroke: var(--amber);  stroke-width: 1.6; fill: none; }
  svg.beta-diagram .link      { stroke: var(--muted);  stroke-width: 1.4; fill: none; }
  svg.beta-diagram .data      { stroke: var(--muted);  stroke-width: 1.3; fill: none; stroke-dasharray: 2 4; stroke-linecap: round; }

  svg.beta-diagram .mk-ink { fill: var(--ink); }
  svg.beta-diagram .mk-ind { fill: var(--indigo); }
  svg.beta-diagram .mk-vio { fill: var(--violet); }
  svg.beta-diagram .mk-amb { fill: var(--amber); }

  svg.beta-diagram text { font-family: "Avenir Next", "Segoe UI", system-ui, sans-serif; }
  svg.beta-diagram .t-name  { font-size: 13px; font-weight: 600; fill: var(--ink); }
  svg.beta-diagram .t-sub   { font-size: 11px; fill: var(--muted); }
  svg.beta-diagram .t-chip  { font-size: 12px; font-weight: 500; fill: var(--ink); }
  svg.beta-diagram .t-mono  { font-family: ui-monospace, "SF Mono", Menlo, monospace; font-size: 10px; fill: var(--muted); }
  svg.beta-diagram .t-tag   { font-family: ui-monospace, "SF Mono", Menlo, monospace; font-size: 11px; letter-spacing: 0.14em; fill: var(--muted); }
  svg.beta-diagram .t-tiny  { font-family: ui-monospace, "SF Mono", Menlo, monospace; font-size: 9.5px; fill: var(--muted); }
  svg.beta-diagram .t-plane-p { font-family: ui-monospace, Menlo, monospace; font-size: 11px; letter-spacing: 0.14em; font-weight: 600; fill: var(--indigo); }
  svg.beta-diagram .t-plane-a { font-family: ui-monospace, Menlo, monospace; font-size: 11px; letter-spacing: 0.14em; font-weight: 600; fill: var(--green); }
  svg.beta-diagram .t-plane-w { font-family: ui-monospace, Menlo, monospace; font-size: 11px; letter-spacing: 0.12em; font-weight: 600; fill: var(--amber); }
</style>
