<script setup lang="ts">
// One component, three views, so the page's diagrams share one palette and one vocabulary.
defineProps<{ view?: 'overview' | 'failure' | 'reconnect' }>()
</script>

<template>
  <div class="rpc-diagram-wrap">

    <!-- ═══════════════════════════ OVERVIEW: registration is liveness ═══════════════════════════ -->
    <svg v-if="!view || view === 'overview'" class="rpc-diagram" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1140 664" role="img" aria-label="Every hop that holds a pending request watches the callee's event bus registration: Java proxies and the MCP invoker through the request liveness watcher, TS clients through the gateway on their behalf. A dead node's registrations leave the replicated subscription cache, and every lease pinned to that node fails with RpcServiceUnavailableException.">
      <defs>
        <marker id="rk-ink" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-ink" points="0,0 10,5 0,10"/></marker>
        <marker id="rk-ind" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-ind" points="0,0 10,5 0,10"/></marker>
        <marker id="rk-red" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-red" points="0,0 10,5 0,10"/></marker>
        <marker id="rk-amb" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-amb" points="0,0 10,5 0,10"/></marker>
      </defs>

      <!-- callers band -->
      <text class="t-tag" x="24" y="26">CALLERS · EACH HOLDS PENDING REQUESTS</text>

      <rect class="node" x="40" y="40" width="300" height="104" rx="8"/>
      <text class="t-name" x="190" y="62" text-anchor="middle">Java caller · any kinotic-server node</text>
      <line class="sep" x1="56" y1="70" x2="324" y2="70"/>
      <text class="t-mono" x="190" y="87" text-anchor="middle">@Proxy → DefaultRpcServiceProxyHandle</text>
      <text class="t-mono" x="190" y="102" text-anchor="middle">responseMap · one lease per correlationId</text>
      <text class="t-mono" x="190" y="117" text-anchor="middle">McpToolInvoker · pendingCalls · one lease each</text>
      <text class="t-tiny" x="190" y="134" text-anchor="middle">in the cluster: watches directly</text>

      <rect class="node" x="800" y="40" width="300" height="104" rx="8"/>
      <text class="t-name" x="950" y="62" text-anchor="middle">TS caller · UI or TS service</text>
      <line class="sep" x1="816" y1="70" x2="1084" y2="70"/>
      <text class="t-mono" x="950" y="87" text-anchor="middle">EventBus.requestStream · rxjs subscription</text>
      <text class="t-mono" x="950" y="102" text-anchor="middle">per correlationId, on one reply destination</text>
      <text class="t-tiny" x="950" y="134" text-anchor="middle">outside the cluster: cannot see registrations</text>

      <!-- STOMP link caller → gateway -->
      <line class="link" x1="950" y1="144" x2="950" y2="206"/>
      <text class="t-tiny" x="958" y="178">STOMP over WebSocket</text>

      <!-- gateway -->
      <rect class="gw" x="700" y="206" width="400" height="150" rx="8"/>
      <text class="t-name" x="900" y="228" text-anchor="middle">kinotic-server gateway · in the cluster</text>
      <line class="sep" x1="716" y1="236" x2="1084" y2="236"/>
      <text class="t-plane-ind" x="716" y="254">CALLER SIDE · ReplySessionState</text>
      <text class="t-mono" x="716" y="270">reply consumer + pending records, one lease each</text>
      <text class="t-mono" x="716" y="284">on loss → error reply to the client's reply destination</text>
      <text class="t-plane-amb" x="716" y="306">CALLEE SIDE · per service socket</text>
      <text class="t-mono" x="716" y="322">outstanding invocations delivered to that socket</text>
      <text class="t-mono" x="716" y="336">socket closes → error reply for each outstanding</text>

      <!-- cluster band -->
      <rect class="bus" x="40" y="378" width="1060" height="30" rx="15"/>
      <text class="t-bus" x="570" y="398" text-anchor="middle">OS BUS · VERT.X CLUSTER ON IGNITE</text>
      <line class="link" x1="900" y1="356" x2="900" y2="378"/>
      <text class="t-tiny" x="1096" y="371" text-anchor="end">cluster member · listen() per TS service socket</text>

      <rect class="cache" x="40" y="446" width="300" height="88" rx="8"/>
      <text class="t-name" x="190" y="468" text-anchor="middle">__vertx.subs · REPLICATED</text>
      <line class="sep" x1="56" y1="476" x2="324" y2="476"/>
      <text class="t-mono" x="190" y="493" text-anchor="middle">address → { RegistrationInfo(nodeId, …) }</text>
      <text class="t-red-b" x="190" y="510" text-anchor="middle">registration = liveness</text>
      <text class="t-tiny" x="190" y="525" text-anchor="middle">Ignite drops a dead node's entries</text>

      <rect class="node" x="380" y="446" width="300" height="88" rx="8"/>
      <text class="t-name" x="530" y="468" text-anchor="middle">KinoticIgniteClusterManager</text>
      <line class="sep" x1="396" y1="476" x2="664" y2="476"/>
      <text class="t-mono" x="530" y="493" text-anchor="middle">statusFlux(address) · ACTIVE / INACTIVE</text>
      <text class="t-mono" x="530" y="508" text-anchor="middle">registeredNodesFlux(address) · { nodeId }</text>
      <text class="t-tiny" x="530" y="525" text-anchor="middle">fed by registration updates the bus already delivers</text>

      <rect class="watch" x="380" y="206" width="300" height="150" rx="8"/>
      <text class="t-name" x="530" y="228" text-anchor="middle">RequestLivenessWatcher</text>
      <line class="sep" x1="396" y1="236" x2="664" y2="236"/>
      <text class="t-mono" x="530" y="254" text-anchor="middle">lease = (address, correlationId)</text>
      <text class="t-mono" x="530" y="269" text-anchor="middle">before ack: address INACTIVE → onLost</text>
      <text class="t-mono" x="530" y="284" text-anchor="middle">after ack: pinned to nodeId</text>
      <text class="t-mono" x="530" y="299" text-anchor="middle">nodeId leaves the set → onLost</text>
      <text class="t-mono" x="530" y="320" text-anchor="middle">one monitor per address, refcounted</text>
      <text class="t-mono" x="530" y="335" text-anchor="middle">pendingCount() → node metric</text>

      <!-- callers use the watcher -->
      <line class="flow-ind" x1="340" y1="92" x2="380" y2="236" marker-end="url(#rk-ind)"/>
      <text class="t-tiny" x="350" y="150">watch(cri)</text>
      <line class="flow-ind" x1="700" y1="270" x2="680" y2="270" marker-end="url(#rk-ind)"/>

      <!-- signals cluster manager → watcher -->
      <line class="flow-red" x1="530" y1="446" x2="530" y2="356" marker-end="url(#rk-red)"/>
      <text class="t-tiny" x="540" y="432">status · node set</text>
      <line class="flow-red" x1="640" y1="446" x2="760" y2="356" marker-end="url(#rk-red)"/>
      <text class="t-tiny" x="700" y="432">to gateway leases</text>

      <!-- cache feeds cluster manager -->
      <line class="link" x1="340" y1="490" x2="380" y2="490" marker-end="url(#rk-ink)"/>

      <!-- callees band -->
      <text class="t-tag" x="24" y="650">CALLEES · ONE REGISTRATION EACH</text>

      <rect class="node" x="40" y="584" width="300" height="46" rx="8"/>
      <text class="t-chip" x="190" y="603" text-anchor="middle">Java service · ServiceInvocationSupervisor</text>
      <text class="t-tiny" x="190" y="619" text-anchor="middle">registers srv://… on its node · acks with nodeId</text>

      <rect class="node" x="380" y="584" width="300" height="46" rx="8"/>
      <text class="t-chip" x="530" y="603" text-anchor="middle">@Scope service · one address per instance</text>
      <text class="t-tiny" x="530" y="619" text-anchor="middle">+ shared unscoped address for @ScopeOptional</text>

      <rect class="wnode" x="800" y="584" width="300" height="46" rx="8"/>
      <text class="t-chip" x="950" y="603" text-anchor="middle">TS published service · VM node workload</text>
      <text class="t-tiny" x="950" y="619" text-anchor="middle">registered by the gateway · owned by its socket</text>

      <!-- callee registrations into the cache -->
      <line class="flow-amb" x1="190" y1="584" x2="190" y2="534" marker-end="url(#rk-amb)"/>
      <line class="flow-amb" x1="500" y1="584" x2="300" y2="534" marker-end="url(#rk-amb)"/>
      <text class="t-tiny" x="506" y="578">listen()</text>
      <!-- TS service → gateway socket; the gateway registers srv://… on the workload's behalf -->
      <polyline class="link" points="1100,607 1120,607 1120,330 1100,330"/>
      <text class="t-tiny" x="1096" y="650" text-anchor="end">STOMP · subscribe srv://… → the gateway registers it</text>
    </svg>

    <!-- ═══════════════════════════ FAILURE: a call in flight when its node dies ═══════════════════════════ -->
    <svg v-else-if="view === 'failure'" class="rpc-diagram" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1140 780" role="img" aria-label="Sequence: the caller sends a request and takes a lease; the ack names node N2 and the lease pins to it; N2 dies; Ignite removes N2's registrations; the watcher sees N2 leave the address's node set and fails the lease with RpcServiceUnavailableException even though another instance keeps the address active. Below, a TS instance dying behind a live gateway: the gateway synthesizes the error for every invocation outstanding on that socket.">
      <defs>
        <marker id="rf-ink" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-ink" points="0,0 10,5 0,10"/></marker>
        <marker id="rf-ind" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-ind" points="0,0 10,5 0,10"/></marker>
        <marker id="rf-red" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-red" points="0,0 10,5 0,10"/></marker>
      </defs>

      <text class="t-tag" x="24" y="26">A · CALLEE NODE DIES MID-CALL — @SCOPEOPTIONAL METHOD, TWO INSTANCES</text>

      <!-- lifeline heads -->
      <rect class="node" x="80" y="42" width="220" height="40" rx="8"/>
      <text class="t-name" x="190" y="67" text-anchor="middle">Caller · lease + watcher</text>
      <rect class="cache" x="420" y="42" width="240" height="40" rx="8"/>
      <text class="t-name" x="540" y="67" text-anchor="middle">Cluster · __vertx.subs</text>
      <rect class="node" x="760" y="42" width="140" height="40" rx="8"/>
      <text class="t-name" x="830" y="67" text-anchor="middle">Instance on N1</text>
      <rect class="node" x="940" y="42" width="140" height="40" rx="8"/>
      <text class="t-name" x="1010" y="67" text-anchor="middle">Instance on N2</text>

      <line class="life" x1="190" y1="82" x2="190" y2="420"/>
      <line class="life" x1="540" y1="82" x2="540" y2="420"/>
      <line class="life" x1="830" y1="82" x2="830" y2="420"/>
      <line class="life" x1="1010" y1="82" x2="1010" y2="300"/>

      <!-- 1 send + lease -->
      <circle class="step" cx="40" cy="118" r="11"/><text class="t-step" x="40" y="122" text-anchor="middle">1</text>
      <line class="flow" x1="190" y1="118" x2="1010" y2="118" marker-end="url(#rf-ink)"/>
      <text class="t-lbl" x="600" y="110" text-anchor="middle">send srv://…/method · reply-to · __correlation-id — round-robin lands on N2</text>
      <text class="t-tiny" x="198" y="134">lease(address) opened · address ACTIVE</text>

      <!-- 2 ack -->
      <circle class="step" cx="40" cy="166" r="11"/><text class="t-step" x="40" y="170" text-anchor="middle">2</text>
      <line class="flow-ind" x1="1010" y1="166" x2="190" y2="166" marker-end="url(#rf-ind)"/>
      <text class="t-lbl-ind" x="600" y="158" text-anchor="middle">ack: "N2"  — the consumer that took it names its node</text>
      <text class="t-tiny" x="198" y="182">lease pinned to (address, N2)</text>

      <!-- 3 invoking -->
      <rect class="act" x="1004" y="190" width="12" height="90"/>
      <text class="t-tiny" x="1022" y="214">method running…</text>
      <text class="t-tiny" x="1022" y="228">no reply yet</text>

      <!-- 4 N2 dies -->
      <circle class="step" cx="40" cy="300" r="11"/><text class="t-step" x="40" y="304" text-anchor="middle">3</text>
      <line class="dead" x1="996" y1="286" x2="1024" y2="314"/><line class="dead" x1="1024" y1="286" x2="996" y2="314"/>
      <text class="t-red-b" x="986" y="304" text-anchor="end">N2 dies</text>
      <text class="t-tiny" x="986" y="318" text-anchor="end">Ignite failure detection</text>

      <!-- 5 registrations removed -->
      <circle class="step" cx="40" cy="346" r="11"/><text class="t-step" x="40" y="350" text-anchor="middle">4</text>
      <rect class="note-red" x="420" y="330" width="240" height="46" rx="6"/>
      <text class="t-mono-red" x="540" y="349" text-anchor="middle">N2's registrations removed</text>
      <text class="t-mono-red" x="540" y="364" text-anchor="middle">address → { N1 }   still ACTIVE</text>
      <line class="flow-red" x1="420" y1="353" x2="190" y2="353" marker-end="url(#rf-red)"/>
      <text class="t-tiny" x="300" y="345" text-anchor="middle">registeredNodesFlux emits { N1 }</text>

      <!-- 6 lease fails -->
      <circle class="step" cx="40" cy="400" r="11"/><text class="t-step" x="40" y="404" text-anchor="middle">5</text>
      <rect class="note-red" x="80" y="384" width="410" height="32" rx="6"/>
      <text class="t-mono-red" x="285" y="404" text-anchor="middle">N2 ∉ { N1 } → onLost → RpcServiceUnavailableException</text>
      <text class="t-tiny" x="80" y="438">The address never went INACTIVE — N1 still serves it. The lease fails anyway, because it was pinned to the node that took the request.</text>
      <text class="t-tiny" x="80" y="452">Before the ack arrives, the lease instead fails on the address going INACTIVE — the single-registration case, every scoped call.</text>

      <!-- ─────────── B ─────────── -->
      <line class="sep" x1="24" y1="478" x2="1116" y2="478"/>
      <text class="t-tag" x="24" y="504">B · TS INSTANCE DIES WHILE ITS GATEWAY LIVES — THE ACK NAMED THE GATEWAY, SO THE GATEWAY MUST TELL THEM APART</text>

      <rect class="node" x="80" y="520" width="220" height="40" rx="8"/>
      <text class="t-name" x="190" y="545" text-anchor="middle">Caller · lease on (addr, G)</text>
      <rect class="gw" x="420" y="520" width="240" height="40" rx="8"/>
      <text class="t-name" x="540" y="545" text-anchor="middle">Gateway G · per-socket outstanding</text>
      <rect class="wnode" x="760" y="520" width="140" height="40" rx="8"/>
      <text class="t-name" x="830" y="545" text-anchor="middle">TS instance A</text>
      <rect class="wnode" x="940" y="520" width="140" height="40" rx="8"/>
      <text class="t-name" x="1010" y="545" text-anchor="middle">TS instance B</text>

      <line class="life" x1="190" y1="560" x2="190" y2="760"/>
      <line class="life" x1="540" y1="560" x2="540" y2="760"/>
      <line class="life" x1="830" y1="560" x2="830" y2="760"/>
      <line class="life" x1="1010" y1="560" x2="1010" y2="690"/>

      <circle class="step" cx="40" cy="596" r="11"/><text class="t-step" x="40" y="600" text-anchor="middle">1</text>
      <line class="flow" x1="190" y1="596" x2="540" y2="596" marker-end="url(#rf-ink)"/>
      <line class="flow" x1="540" y1="596" x2="1010" y2="596" marker-end="url(#rf-ink)"/>
      <text class="t-lbl" x="365" y="588" text-anchor="middle">send · ack "G"</text>
      <text class="t-lbl" x="775" y="588" text-anchor="middle">forwarded to B's socket · recorded (correlationId, reply-to)</text>

      <circle class="step" cx="40" cy="676" r="11"/><text class="t-step" x="40" y="680" text-anchor="middle">2</text>
      <line class="dead" x1="996" y1="662" x2="1024" y2="690"/><line class="dead" x1="1024" y1="662" x2="996" y2="690"/>
      <text class="t-red-b" x="986" y="680" text-anchor="end">B's socket closes</text>
      <text class="t-tiny" x="986" y="694" text-anchor="end">FIN, or heartbeat timeout</text>
      <text class="t-tiny" x="550" y="660">G still registered for the address (A's socket) — node-level lease cannot fire</text>

      <circle class="step" cx="40" cy="726" r="11"/><text class="t-step" x="40" y="730" text-anchor="middle">3</text>
      <line class="flow-red" x1="540" y1="726" x2="190" y2="726" marker-end="url(#rf-red)"/>
      <text class="t-lbl-red" x="365" y="718" text-anchor="middle">G synthesizes RpcServiceUnavailableException to each reply-to still outstanding on B's socket</text>
      <text class="t-tiny" x="80" y="756">Same synthesis path the gateway already uses for a send that finds no handler; a socket close is the fastest signal in the system, ahead of Ignite's window.</text>
    </svg>

    <!-- ═══════════════════════════ RECONNECT: mailbox and cross-node handoff ═══════════════════════════ -->
    <svg v-else class="rpc-diagram" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1140 720" role="img" aria-label="Sequence: a TS client mid-stream loses its socket; the gateway parks the connection's reply state in a mailbox, keeping the reply registration so the server stream keeps running and replies buffer. On reconnect to the same node the mailbox reattaches and flushes. On a node rollover the client lands on gateway B, which publishes a mailbox-release; gateway A flushes its buffer to the reply address and B forwards from then on. The mailbox window and buffer are bounded; overflow rotates the replyToId so the client fails its in-flight calls through its existing reset path.">
      <defs>
        <marker id="rr-ink" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-ink" points="0,0 10,5 0,10"/></marker>
        <marker id="rr-ind" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-ind" points="0,0 10,5 0,10"/></marker>
        <marker id="rr-amb" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-amb" points="0,0 10,5 0,10"/></marker>
        <marker id="rr-red" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><polygon class="mk-red" points="0,0 10,5 0,10"/></marker>
      </defs>

      <text class="t-tag" x="24" y="26">A STREAM SURVIVES A SOCKET BLIP AND A GATEWAY ROLLOVER · STICKY SESSION</text>

      <rect class="node" x="60" y="42" width="180" height="40" rx="8"/>
      <text class="t-name" x="150" y="67" text-anchor="middle">TS client</text>
      <rect class="gw" x="330" y="42" width="200" height="40" rx="8"/>
      <text class="t-name" x="430" y="67" text-anchor="middle">Gateway A · mailbox</text>
      <rect class="gw" x="620" y="42" width="200" height="40" rx="8"/>
      <text class="t-name" x="720" y="67" text-anchor="middle">Gateway B</text>
      <rect class="node" x="910" y="42" width="180" height="40" rx="8"/>
      <text class="t-name" x="1000" y="67" text-anchor="middle">Service · streaming</text>

      <line class="life" x1="150" y1="82" x2="150" y2="700"/>
      <line class="life" x1="430" y1="82" x2="430" y2="560"/>
      <line class="life" x1="720" y1="82" x2="720" y2="700"/>
      <line class="life" x1="1000" y1="82" x2="1000" y2="700"/>
      <rect class="act" x="994" y="110" width="12" height="560"/>
      <text class="t-tiny" x="986" y="140" text-anchor="end">stream running; its reply-listener</text>
      <text class="t-tiny" x="986" y="154" text-anchor="end">monitor stays ACTIVE throughout</text>

      <!-- 1 streaming normally -->
      <circle class="step" cx="30" cy="118" r="11"/><text class="t-step" x="30" y="122" text-anchor="middle">1</text>
      <line class="flow" x1="1000" y1="118" x2="430" y2="118" marker-end="url(#rr-ink)"/>
      <line class="flow" x1="430" y1="118" x2="150" y2="118" marker-end="url(#rr-ink)"/>
      <text class="t-lbl" x="715" y="110" text-anchor="middle">values → reply://replyToId:… (consumer on A)</text>
      <text class="t-lbl" x="290" y="110" text-anchor="middle">STOMP frames</text>

      <!-- 2 blip -->
      <circle class="step" cx="30" cy="176" r="11"/><text class="t-step" x="30" y="180" text-anchor="middle">2</text>
      <line class="dead" x1="276" y1="164" x2="304" y2="188"/><line class="dead" x1="304" y1="164" x2="276" y2="188"/>
      <text class="t-red-b" x="312" y="180">socket closes</text>
      <rect class="note-amb" x="330" y="200" width="290" height="60" rx="6"/>
      <text class="t-mono-amb" x="475" y="218" text-anchor="middle">closed() → park ReplySessionState</text>
      <text class="t-mono-amb" x="475" y="233" text-anchor="middle">reply consumer kept · leases armed</text>
      <text class="t-mono-amb" x="475" y="248" text-anchor="middle">replies buffer · byte budget</text>
      <line class="flow-amb" x1="1000" y1="280" x2="430" y2="280" marker-end="url(#rr-amb)"/>
      <text class="t-lbl-amb" x="715" y="272" text-anchor="middle">values keep arriving → buffered, nothing dropped</text>

      <!-- 3a same-node reconnect -->
      <line class="sep" x1="24" y1="306" x2="1116" y2="306"/>
      <text class="t-tag" x="24" y="330">3A · SAME NODE COMES BACK</text>
      <circle class="step" cx="30" cy="358" r="11"/><text class="t-step" x="30" y="362" text-anchor="middle">3</text>
      <line class="flow-ind" x1="150" y1="358" x2="430" y2="358" marker-end="url(#rr-ind)"/>
      <text class="t-lbl-ind" x="290" y="350" text-anchor="middle">reconnect · same session → same replyToId</text>
      <line class="flow-amb" x1="430" y1="390" x2="150" y2="390" marker-end="url(#rr-amb)"/>
      <text class="t-lbl-amb" x="290" y="382" text-anchor="middle">reattach · flush buffer in order · live again</text>
      <text class="t-tiny" x="60" y="414">The client saw a reconnect and nothing else: no reset, no failed calls, the stream's Observable never completed.</text>

      <!-- 3b rollover -->
      <line class="sep" x1="24" y1="434" x2="1116" y2="434"/>
      <text class="t-tag" x="24" y="458">3B · NODE ROLLOVER — CLIENT LANDS ON GATEWAY B</text>
      <circle class="step" cx="30" cy="486" r="11"/><text class="t-step" x="30" y="490" text-anchor="middle">3</text>
      <line class="flow-ind" x1="150" y1="486" x2="720" y2="486" marker-end="url(#rr-ind)"/>
      <text class="t-lbl-ind" x="435" y="478" text-anchor="middle">reconnect · session found in the clustered store → same replyToId</text>
      <text class="t-tiny" x="728" y="506">B registers its reply consumer,</text>
      <text class="t-tiny" x="728" y="520">holds forwarding to the client</text>

      <circle class="step" cx="30" cy="540" r="11"/><text class="t-step" x="30" y="544" text-anchor="middle">4</text>
      <line class="flow-ind" x1="720" y1="540" x2="430" y2="540" marker-end="url(#rr-ind)"/>
      <text class="t-lbl-ind" x="575" y="532" text-anchor="middle">publish(control: mailbox-release) — reaches every consumer on the address</text>

      <circle class="step" cx="30" cy="586" r="11"/><text class="t-step" x="30" y="590" text-anchor="middle">5</text>
      <line class="flow-amb" x1="430" y1="586" x2="720" y2="586" marker-end="url(#rr-amb)"/>
      <text class="t-lbl-amb" x="575" y="578" text-anchor="middle">A unregisters, re-sends its buffer to the reply address, then flush-complete</text>
      <text class="t-tiny" x="60" y="606">A's mailbox is done; B is now the only consumer for the address.</text>

      <circle class="step" cx="30" cy="640" r="11"/><text class="t-step" x="30" y="644" text-anchor="middle">6</text>
      <line class="flow" x1="1000" y1="640" x2="720" y2="640" marker-end="url(#rr-ink)"/>
      <line class="flow" x1="720" y1="640" x2="150" y2="640" marker-end="url(#rr-ink)"/>
      <text class="t-lbl" x="860" y="632" text-anchor="middle">new values</text>
      <text class="t-lbl" x="435" y="632" text-anchor="middle">buffered values first, then live — per-correlation order kept</text>

      <!-- exits -->
      <rect class="note-red" x="60" y="664" width="1030" height="40" rx="6"/>
      <text class="t-mono-red" x="575" y="681" text-anchor="middle">Exits: window expiry → dispose, streams cancel through the reply-listener INACTIVE path already in place.</text>
      <text class="t-mono-red" x="575" y="696" text-anchor="middle">Buffer overflow → dispose and rotate replyToId; the next CONNECT hands the client a new reply CRI and its existing reset path fails the in-flight calls.</text>
    </svg>
  </div>
</template>

<style>
/* Palette follows the site color-mode class, so the diagram tracks the theme toggle. */
svg.rpc-diagram {
    --surface: #FFFFFF;
    --ink: #1A2332;
    --muted: #5C6879;
    --line: #D8DDE5;
    --indigo: #4A5FD0;
    --indigo-tint: rgba(74, 95, 208, 0.10);
    --amber: #A97C22;
    --amber-tint: rgba(169, 124, 34, 0.10);
    --red: #C64A4A;
    --red-tint: rgba(198, 74, 74, 0.10);
    --pink: #B0487E;
    --pink-tint: rgba(176, 72, 126, 0.08);
}
.dark svg.rpc-diagram {
  --surface: #151D2D;
  --ink: #E6EBF4;
  --muted: #96A2B6;
  --line: #2B3648;
  --indigo: #7E8EF0;
  --indigo-tint: rgba(126, 142, 240, 0.14);
  --amber: #D3A24C;
  --amber-tint: rgba(211, 162, 76, 0.13);
  --red: #E06A6A;
  --red-tint: rgba(224, 106, 106, 0.14);
  --pink: #D879AC;
  --pink-tint: rgba(216, 121, 172, 0.12);
}

.rpc-diagram-wrap { overflow-x: auto; margin: 1.5rem 0; }
svg.rpc-diagram { min-width: 760px; width: 100%; height: auto; display: block; }

/* ── SVG vocabulary ─────────────────────────────── */
svg.rpc-diagram .node     { fill: var(--surface); stroke: var(--line);   stroke-width: 1.25; }
svg.rpc-diagram .gw       { fill: var(--surface); stroke: var(--indigo); stroke-width: 2; }
svg.rpc-diagram .watch    { fill: var(--indigo-tint); stroke: var(--indigo); stroke-width: 1.5; }
svg.rpc-diagram .cache    { fill: var(--pink-tint);   stroke: var(--pink);   stroke-width: 1.5; }
svg.rpc-diagram .wnode    { fill: var(--amber-tint);  stroke: var(--amber);  stroke-width: 1.5; }
svg.rpc-diagram .bus      { fill: var(--indigo-tint); stroke: var(--indigo); stroke-width: 1.5; }
svg.rpc-diagram .note-red { fill: var(--red-tint);    stroke: var(--red);    stroke-width: 1.25; }
svg.rpc-diagram .note-amb { fill: var(--amber-tint);  stroke: var(--amber);  stroke-width: 1.25; }
svg.rpc-diagram .act      { fill: var(--indigo-tint); stroke: var(--indigo); stroke-width: 1; }
svg.rpc-diagram .step     { fill: var(--surface); stroke: var(--ink); stroke-width: 1.25; }
svg.rpc-diagram .sep      { stroke: var(--line); stroke-width: 1; }
svg.rpc-diagram .life     { stroke: var(--line); stroke-width: 1.25; stroke-dasharray: 4 4; }
svg.rpc-diagram .dead     { stroke: var(--red); stroke-width: 3; stroke-linecap: round; }

svg.rpc-diagram .flow     { stroke: var(--ink);    stroke-width: 1.6; fill: none; }
svg.rpc-diagram .flow-ind { stroke: var(--indigo); stroke-width: 1.8; fill: none; }
svg.rpc-diagram .flow-red { stroke: var(--red);    stroke-width: 1.8; fill: none; stroke-dasharray: 6 4; }
svg.rpc-diagram .flow-amb { stroke: var(--amber);  stroke-width: 1.8; fill: none; }
svg.rpc-diagram .link     { stroke: var(--muted);  stroke-width: 1.4; fill: none; }

svg.rpc-diagram .mk-ink { fill: var(--ink); }
svg.rpc-diagram .mk-ind { fill: var(--indigo); }
svg.rpc-diagram .mk-red { fill: var(--red); }
svg.rpc-diagram .mk-amb { fill: var(--amber); }

svg.rpc-diagram text { font-family: "Avenir Next", "Segoe UI", system-ui, sans-serif; }
svg.rpc-diagram .t-name  { font-size: 13px; font-weight: 600; fill: var(--ink); }
svg.rpc-diagram .t-chip  { font-size: 12px; font-weight: 500; fill: var(--ink); }
svg.rpc-diagram .t-step  { font-size: 11px; font-weight: 700; fill: var(--ink); }
svg.rpc-diagram .t-lbl   { font-size: 11px; fill: var(--ink); }
svg.rpc-diagram .t-lbl-ind { font-size: 11px; font-weight: 600; fill: var(--indigo); }
svg.rpc-diagram .t-lbl-red { font-size: 11px; font-weight: 600; fill: var(--red); }
svg.rpc-diagram .t-lbl-amb { font-size: 11px; font-weight: 600; fill: var(--amber); }
svg.rpc-diagram .t-red-b { font-size: 11px; font-weight: 700; fill: var(--red); }
svg.rpc-diagram .t-mono  { font-family: ui-monospace, "SF Mono", Menlo, monospace; font-size: 10px; fill: var(--muted); }
svg.rpc-diagram .t-mono-red { font-family: ui-monospace, "SF Mono", Menlo, monospace; font-size: 10px; fill: var(--red); }
svg.rpc-diagram .t-mono-amb { font-family: ui-monospace, "SF Mono", Menlo, monospace; font-size: 10px; fill: var(--amber); }
svg.rpc-diagram .t-tag   { font-family: ui-monospace, "SF Mono", Menlo, monospace; font-size: 11px; letter-spacing: 0.14em; fill: var(--muted); }
svg.rpc-diagram .t-tiny  { font-family: ui-monospace, "SF Mono", Menlo, monospace; font-size: 9.5px; fill: var(--muted); }
svg.rpc-diagram .t-bus   { font-family: ui-monospace, "SF Mono", Menlo, monospace; font-size: 11px; letter-spacing: 0.1em; font-weight: 600; fill: var(--indigo); }
svg.rpc-diagram .t-plane-ind { font-family: ui-monospace, Menlo, monospace; font-size: 9.5px; letter-spacing: 0.12em; font-weight: 600; fill: var(--indigo); }
svg.rpc-diagram .t-plane-amb { font-family: ui-monospace, Menlo, monospace; font-size: 9.5px; letter-spacing: 0.12em; font-weight: 600; fill: var(--amber); }
</style>
