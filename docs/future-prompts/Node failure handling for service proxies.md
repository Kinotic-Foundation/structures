# Node failure handling for service proxies — phase plan

Plan of record for making a proxy call fail when the node serving it dies, instead of hanging
forever. Phase 1 landed in PR #476; Phase 2 is PR #540. Everything below was re-validated against `develop` at `3adf17d`
(2026-09-08); the adjustments that pass produced are folded in, and the phase numbering below
supersedes the earlier chat numbering (mapping at the end).

Process: one PR per phase, review and merge before the next starts, ~10 files per phase. No
phase may rewrite, refactor, or restructure what an earlier phase produced; if a later phase
would force that, the earlier phase drew its boundary wrong and the plan is revisited first.

## The problem, in code

`sendWithAck` acks receipt, never completion:

```java
// DefaultEventConsumer.java:44-46
if (message.replyAddress() != null) {
    message.reply(null);            // "bytes received", nothing more
}
```

so every pending-request map in the system waits without a lifeline once the callee is gone:

```java
// DefaultRpcServiceProxyHandle.java — removed only on reply, send failure, or release()
private final ConcurrentHashMap<String, RpcReturnValueHandler> responseMap = new ConcurrentHashMap<>();
// McpToolInvoker.pendingCalls has the same shape; the TS EventBus holds an rxjs subscription per correlationId
```

A call issued *after* the callee's registration is gone already fails fast (`NO_HANDLERS` →
`RpcMissingServiceException`). A call in flight *when* the callee dies hangs, in every runtime.

## The design

A service's registration in the clustered event bus *is* its liveness: Ignite removes a dead
node's entries from `__vertx.subs`, and `KinoticIgniteClusterManager.statusFlux()` already turns
that into a push signal. The callee side already uses it in one direction:

```java
// ServiceInvocationSupervisor.java — callee watches the CALLER's reply address, cancels streams on INACTIVE
Flux<ListenerStatus> replyListenerStatus = eventBusService.monitorListenerStatus(replyCRI);
```

The plan makes that symmetric and universal: **every pending request becomes a watch on the
registration that serves it, at whichever hop already has cluster visibility.** No timeouts, no
timers, no per-call policy; a legitimate ten-hour call is never killed, and a dead node fails its
pending calls within the cluster's failure-detection window.

| Caller | Pending state lives | Who watches |
|---|---|---|
| Java proxy, MCP invoker | caller's JVM | the JVM, through one shared watcher (Phase 3) |
| TS client (UI, TS service) via STOMP | TS client, which cannot see the cluster | the **gateway**, on the client's behalf (Phase 4) |
| any caller of a TS service that dies while its gateway lives | — | the **gateway**, from the socket close (Phase 5) |

Semantics: two exceptions under one parent. `RpcMissingServiceException` (exists): rejected at
send, never executed. `RpcServiceUnavailableException` (Phase 3): the callee vanished mid-call,
may or may not have executed. Callers of non-idempotent operations need the distinction.

What this deliberately does not cover: a callee that is alive, registered, and wedged. No
liveness signal exists for that; the pending-count metric the watcher exposes is how it becomes
visible. If it ever needs bounding, it composes as a callee-declared deadline, later.

## Phase 1 — wire contract (PR #476)

Every terminal reply carries `control: complete` on the reply event itself: single-value replies
from the Java supervisor and the TS `BasicReturnValueConverter` carry it alongside their data,
stream completion events already did, error replies stay terminal via the `error` header. Any hop
holding per-request state can release it on that one event without knowing the method's shape.
Receivers accept marked and unmarked replies. `KinoticUtil.mapSendFailure` is the one
NO_HANDLERS mapping (proxy, gateway, MCP) and the proxy path now reports unreachability.

## Phase 2 — the ack names the node (~7 files)

Address-level watching alone has a blind spot: any address with more than one registration
round-robins, and it stays ACTIVE while *any* instance survives. That is every unscoped service
published on more than one node — Java on N JVMs, or N TS instances of one service — and also the
shared unscoped address a scoped service joins for its `@ScopeOptional` methods, which behaves
exactly like any other multi-instance address:

```java
// ServiceInvocationSupervisor.java:139-144
methodInvocationEventConsumer = listenAt(serviceDescriptor.serviceIdentifier().cri());              // srv://<scope>@...   one instance
if(serviceDescriptor.serviceIdentifier().scope() != null && !scopeOptionalMethodIds.isEmpty()){
    unscopedInvocationEventConsumer = listenAt(serviceDescriptor.serviceIdentifier().unscopedCri()); // srv://...          every instance
}
```

However an address comes to hold N registrations, a call to it lands on one of them, so the
caller has to know **which** registration took its request. The ack is where that is learned, and it costs nothing on the wire beyond the reply
body the ack already sends:

```java
// DefaultEventConsumer — the ack carries the id of the node whose consumer took the request
if (message.replyAddress() != null) {
    message.reply(nodeId);          // clusterManager.getNodeId(), the same id DefaultKinotic.serverInfo uses
}
```

```java
// EventBusService — the ack's payload reaches the sender
Future<String> sendWithAck(Event<byte[]> event);     // completes with the acking node id (was Future<Void>)

// KinoticIgniteClusterManager — a NodeListener on the discovery events vertx-ignite already
// subscribes to (EVT_NODE_JOINED, EVT_NODE_LEFT, EVT_NODE_FAILED) emits the cluster membership
Flux<Set<String>> clusterNodesFlux();
// exposed as EventBusService.monitorClusterNodes()
```

Membership, not the address's registration set, is what a pinned lease checks against. A JVM
unregisters an address while it is still up — `ServiceRegistrationBeanPostProcessor` runs
`ServiceInvocationSupervisor.stop()` on bean destruction, and an invocation already running there
still replies — so the registration set fires a false failure on every rolling restart, at the
exact moment Phase 3's drain wants callers to keep waiting. `nodeLeft` fires only when the node is
gone: at once after a graceful leave, after a crash once Ignite's `failureDetectionTimeout` (10 s)
expires. Cost is one N-sized snapshot per membership change per node, however many addresses have
calls in flight; the registration set would have cost a k-sized set per monitored address, since
every platform address is registered on all k nodes.

Nothing watches the address before the ack. `sendWithAck` is a Vert.x `request`, which fails with
`NO_HANDLERS` at once when nothing is registered and with `TIMEOUT` after `DeliveryOptions`'
default 30 s when the ack never arrives — a bound on one network hop, since `DefaultEventConsumer`
acks before dispatching, never on the invocation.

Files: `DefaultEventConsumer`, `DefaultEventBusService`, `EventBusService`,
`KinoticIgniteClusterManager`, the four `sendWithAck` call sites adapting to the new return type
(`DefaultRpcServiceProxyHandle`, `McpToolInvoker`, `DefaultEventService`,
`EndpointConnectionHandler`), `EventBusServiceTests` (the membership names the local node;
the ack names the local node).

Why not `RegistrationInfo.seq()`: it exists, but a `MessageConsumer` never learns its own seq, so
identifying below node granularity from the consumer side needs Vert.x internals. Node id plus
the gateway-side synthesis in Phase 5 covers every case without them.

## Phase 3 — the watcher, in kinotic-core (~11 files)

One component every in-cluster caller uses, in `api/service` because the MCP invoker lives in
`kinotic-domain`:

```java
public interface RequestLivenessWatcher {
    void watch(String correlationId, String nodeId, Runnable onLost);   // pin at the ack
    void settle(String correlationId);                                   // release when the reply settles it
    int pendingCount();                                                  // the kinotic.rpc.pending.requests gauge
}
```

Keyed by correlation id rather than handing out lease objects, so the two callers keep no lease
bookkeeping of their own: each pins in the ack handler and settles wherever it already removes
the request from its own map.

A lease starts at the ack. Until then Vert.x owns the outcome (`NO_HANDLERS` or `TIMEOUT` on the
`request`, see Phase 2), and `KinoticUtil.mapSendFailure` maps `TIMEOUT` to
`RpcServiceUnavailableException`, because an ack lost in flight cannot rule out that the handler
ran. From the ack on, the lease is pinned to the node id it named and fails when that id leaves
`monitorClusterNodes()`. The address may stay ACTIVE on other instances, and the node may already
have unregistered the address to drain; the lease holds until the node is gone.

Coverage of the round-robin cases:

| Call | Ack names | Fails when |
|---|---|---|
| scoped `srv://<scope>@…` | the one node | that node leaves the cluster |
| unscoped, N Java instances (one per JVM) — a service on N nodes, or `@ScopeOptional` methods | the JVM that took it | that JVM leaves the cluster |
| unscoped, N TS instances behind gateways | the **gateway** holding that instance's subscription | that gateway dies (this phase); the instance dies while its gateway lives (Phase 5) |

Wired into `DefaultRpcServiceProxyHandle` (pinned inside `responseMap.computeIfPresent` so a reply
that lands before the ack is processed leaves nothing pinned; `onLost` removes the handler and calls
`processError(new RpcServiceUnavailableException(...))`, and only the party that removes the handler
signals it) and `McpToolInvoker`. `onLost` only *dispatches* the failure to the request's own context, never
runs continuations on the cluster manager's delivery context: under a death burst that loop must
drain in queue submissions, not user code. One membership subscription per JVM, held for the
watcher's lifetime, and leases keyed by the node id they are pinned to, so a membership change costs
one lookup per pinned node. Non-clustered guards are gone from `develop` (#496), so there is no
fallback path.

Files: `RequestLivenessWatcher`, `DefaultRequestLivenessWatcher`, `RpcServiceUnavailableException`,
`KinoticUtil`, `DefaultRpcServiceProxyHandle`, `DefaultServiceRegistry`, `McpToolInvoker`,
`ServiceInvocationSupervisor`, `ServiceRegistrationBeanPostProcessor`, `RpcLivenessTests` +
`DrainTestService` (a second Ignite node in the test JVM hosts an address, acks, and leaves mid-call
→ `RpcServiceUnavailableException`; the local service unregistering mid-call → the reply still
arrives and `unregister` waits for it; a stream cut by `stop()` → `RpcServiceUnavailableException`;
unrelated membership churn → no false failure).

The proxy constructor grows by one parameter, the way `develop` grew it for `TraceLogFilter`;
it is not restructured.

The callee side of the same phase: `ServiceInvocationSupervisor.stop()` drains instead of cutting.

```java
// ServiceInvocationSupervisor.stop() — Phase 3
Future<Void> ret = methodInvocationEventConsumer.unregister()          // 1. stop accepting
    .compose(v -> inFlight.drained());                                 // 2. single-value invocations reply first
for(... : activeStreamingResults.entrySet()){
    streamSubscribers.getValue().fail(new RpcServiceUnavailableException(...));   // 3. streams end with a terminal error
}
```

Today `stop()` cancels streams with no terminal reply and returns before running invocations have
replied; with membership as the post-ack signal a cut stream's caller would otherwise wait for the
JVM to exit. The supervisor only tracked streams, so it gains an in-flight count for single-value invocations:
the synchronous handler run plus every pending single-value reactive result. The drain is bounded
by `DRAIN_TIMEOUT_MS` (30 s), a constant until an environment needs a different value, never a
per-call bound. The drain only helps while Vert.x and Ignite are still up, and
`ServiceRegistrationBeanPostProcessor.postProcessBeforeDestruction` runs on the service bean's
destruction, where a published bean that injects nothing from kinotic had no dependency edge forcing
that order; the post processor now records each published bean as dependent on the registry, so
Spring destroys it, and drains it, before the registry and the Vert.x behind it.

## Phase 4 — gateway, caller side: session state, leases, session touch (~10 files)

**The boundary-critical phase.** Introduce the object Phase 7 later parks: a
per-connection `ReplySessionState` owning what is spread through `EndpointConnectionHandler`
today — the `reply://` consumer and, new, the pending-request records
`(correlationId, metadata, destination)` with a Phase 3 lease each. On lease loss, synthesize the
error reply through the *existing* recover path (`exceptionConverter.convert` → `send`); release
leases on terminal-marked replies observed in `StompSubscriptionEventSubscriber`. Lifecycle is
still socket-bound here (`shutdown()` disposes it) — disposal is one call on a self-contained
object, which is exactly the seam Phase 7 repoints.

Also here, because parking cannot be built on it otherwise: the session touch on the
WebSocket path never reaches the store.

```java
// EndpointConnectionHandler.java:318-325 — and startSessionTouchTimer does the same
private void signalActivity() {
    if (sessionKeepAliveMode == SessionKeepAliveMode.ACTIVITY) {
        session.setAccessed();      // mutates the local copy; SessionHandler only flushes on response end,
    }                               // which on a WebSocket happened once, at the 101 upgrade
}
```

The clustered entry's TTL is frozen at upgrade, so an `ACTIVITY`/`CONNECTION` session can expire
under a live connection (the sizing doc's open item 7). Fix: an explicit store put where the touch
happens, pinned by a test that a session outlives its timeout under an active connection.

Files: `ReplySessionState`, `EndpointConnectionHandler`, `StompSubscriptionEventSubscriber`,
`Services`, the session-touch fix, tests (TS client observes the typed failure when its callee
dies mid-call; session survives its timeout under an open socket).

## Phase 5 — gateway, callee side: heartbeats and instance-level synthesis (~7 files)

Two things the gateway alone can see.

**Dead sockets.** Every socket-derived signal in this plan assumes the gateway notices a closed
connection. A hard-killed VM sends no FIN and the server sets no STOMP heartbeats:

```java
// ApiGatewayVertcleFactory.java:82 — no heartbeat configuration
StompServerOptions stompServerOptions = new StompServerOptions()
        .setWebsocketPath(STOMP_WEBSOCKET_PATH)
        .setMaxBodyLength(properties.getMaxEventPayloadSize());
```

The client already offers `heartbeatOutgoing: 30000`. Enforce it server-side (verify
vertx-stomp-lite's option) so a vanished workload's connection closes deterministically within
about two heartbeat intervals, and its `srv://` registration with it. Property beside
`sessionTimeout` and `sessionCookieSameSite` on `ApiGatewayProperties`.

**A TS instance dying while its gateway lives.** The Phase 2 ack names the gateway, so a
node-level lease cannot tell one TS instance on that gateway from another. The gateway can: it
forwards every invocation to a specific socket, and the terminal reply comes back through
`EndpointConnectionHandler.send()` on the reply scheme. Per `srv://` subscription, track each
delivered invocation `(correlationId, reply-to)` until its terminal reply passes back; on
`closed()`, synthesize `RpcServiceUnavailableException` to every reply-to still tracked, through
the same synthesis path as Phase 4. This closes the last row of the Phase 3 table and is also the
fastest signal there is for the scoped VM-node case — a socket close, ahead of Ignite's
failure-detection window.

Files: `ApiGatewayProperties`, `ApiGatewayVertcleFactory`, delivered-invocation tracking on the
service-subscription side of `EndpointConnectionHandler`/`ReplySessionState`'s sibling, tests
(heartbeat closes a dead socket; a caller of a multi-instance service fails when the TS instance
that took it disconnects while another instance stays up).

## Phase 6 — TS client edges (~4 files)

`develop` already closed two of the three: requests are no longer queued while disconnected
(`send()` requires `connected`, `retryIfDisconnected: false`), and the stale `replyToId` replay
on re-activation is skipped. What remains: typed error bodies so `RpcServiceUnavailableException`
crosses to TS as more than a message string (the `EventBus.ts` TODO); the `NONE` keep-alive edge
where a network drop keeps the session and resurrects the old `replyToId` until expiry; and the
vm-manager's overlapping heartbeat `setInterval`.

Not in scope: failing in-flight calls on a sticky reconnect. That is what parking exists to
avoid.

## Phase 7 — parked reply sessions, same node (~13 files, split 7a gateway / 7b client)

On `closed()` with a sticky session, `shutdown()` *parks* the `ReplySessionState` in a node-local
`ParkedReplySessions` instead of disposing: the reply consumer keeps consuming into a bounded
buffer, leases stay armed (a callee dying during the gap synthesizes a buffered error), and —
because the registration stays — `ServiceInvocationSupervisor`'s reply-listener monitor stays
ACTIVE, so long-running server streams survive the reconnect instead of being cancelled. Reattach
and flush on same-node reconnect.

Adjustments from the re-validation:

- **The window is a gateway property**, beside `sessionTimeout`; parking never derives its
  lifetime from the clustered session's expiry (the Phase 4 fix makes that expiry correct, but the
  window is gateway policy either way).
- **Replies only.** The client no longer queues requests during a gap, so a parked session holds
  inbound replies and nothing else.
- **A byte budget per parked session**, and a new heap term for the sizing doc: parked replies are
  heap-resident bodies up to `maxEventPayloadSize`, and the direct-memory-exhaustion scenario in
  NavidNotes parks many sessions on one node at once.

Two exits, one outcome. Window expiry: dispose, drop the buffer, close leases (streams then cancel
through the existing INACTIVE path). Overflow: dispose. Both **rotate the `replyToId`** in the
session's `ConnectedInfo`, so the next CONNECT mints a new reply CRI and the client's existing
`replyToCriChangedHandler` → `resetRequestReplies` path fails its in-flight calls — continuity
loss signals through a mechanism that already ships, and login is untouched. Expiry has to rotate
too: a single-value reply produced during the gap is gone with the buffer, and a client that
reconnects within the session but after the window would otherwise find its `replyToId` intact
and wait forever for that reply.

**The client half (7b).** A client whose socket stays down longer than the window has nothing left
to wait for, so it fails its in-flight calls itself instead of holding them for a reconnect that
may never come. One timer per connection, armed on `connectionState$` CLOSED and cleared on the
next CONNECTED, never a per-call timeout; on expiry it runs `resetRequestReplies`. The window comes
from the server: `ConnectedInfo` gains `replyBufferWindow` beside `replyToId`, mirrored in
`ConnectedInfo.ts`, so it is configured once, on the gateway. Every ordering is consistent given
the rotation above: a reconnect inside the window flushes; the client timer first, then a reconnect
inside the server's window, flushes to correlations already failed, which `cancelIfUnexpected`
already handles; the server first, then a reconnect, fails through the reply CRI change; no
reconnect at all fails on the client timer. The client's first reconnect attempt comes
`INITIAL_RECONNECT_DELAY` (2 s) after the drop, so the window's useful range starts there; five to
ten seconds absorbs a spotty network, and past that the network is down and failing fast is
cheaper than holding the calls.

A client that reconnects through a fresh handshake without its session (the vm-manager's
`reconnectOnFatalError` loop re-authenticates with credentials) gets a new `replyToId`; its old
parked session is orphaned until the window expires. The window bounds a leak, not only a wait.

Files, 7a: `ParkedReplySessions`, park/reattach in `EndpointConnectionHandler` +
`ReplySessionState`, `replyToId` rotation on both exits, `ApiGatewayProperties.replyBufferWindow`,
`ConnectedInfo` (Java) carrying it, tests (blip mid-stream on one gateway → stream continues; a
single-value reply during the blip → delivered on reattach; expiry and overflow → calls fail with
the reset error). 7b: `ConnectedInfo.ts`, `StompConnectionManager.ts`, `EventBus.ts`, a TS test
(blip shorter than the window → the call completes; longer → it fails).

## Phase 8 — cross-node handoff (~9 files)

The rollover case: the client reconnects to a different gateway node. `ClusteredSessionStore` is
now unconditional on `develop`, so the session (and `replyToId`) is found on any node; the parked
replies are on the old one. Protocol over the event bus itself:

```java
// EventBusService.java (develop) — fan-out, every consumer on the address, every node
void publish(Event<byte[]> event);
```

The new node registers its reply consumer, then `publish`es a `control: reply-session-release` to the
reply address — with `publish` it reaches both the parked session's consumer and the new consumer regardless of
registration order, which removes the split-brain reasoning the earlier `send`-based sketch
needed. The parked session stops consuming, re-sends its buffer to the same address (now routing only
to the new node), transfers its pending records, and ends with `flush-complete`; the new node
holds client forwarding until then, preserving per-correlation stream order. A parked session that never
answers is bounded by the same registration monitoring, no timeouts here either.

Files: control values in `EventConstants`, release/flush in `ParkedReplySessions`, the hold in
`ReplySessionState`, a pending-record codec, a two-gateway test (kill gateway A mid-stream,
reconnect to B, stream resumes complete and ordered).

## Phase 9 — orchestrator fast path (~5 files, optional)

The orchestrator now lives in `kinotic-system-api`
(`org.kinotic.system.internal.api.services.DefaultVmNodeOrchestrationService`,
`org.kinotic.system.api.workload.VmManagerProxy`). Feed `RpcServiceUnavailableException` /
`NO_HANDLERS` on `VmManagerProxy` into VmNode health verification as an invalidation trigger, the
way `ServiceLivenessUpdater` works — the orchestrator otherwise learns of node death only from
the heartbeat reaper. And fix the guard that keeps a draining node from ever going offline:

```java
// DefaultVmNodeOrchestrationService.java:228-230
if (node.getStatus().getType() == VmNodeStatusType.ONLINE      // DRAINING never enters
        && node.getLastSeen() != null
        && node.getLastSeen().before(cutoffDate)) {
```

This matters more now: the vm-manager reports telemetry-shipping problems as DRAINING, so a node
that loses Loki/Tempo and then dies keeps its workloads RUNNING forever.

## Numbering

| Earlier chat numbering | This document |
|---|---|
| 1 wire contract | 1 |
| 2 watcher (address-level) | 2 ack names the node, 3 watcher (node-level) |
| 3 gateway leases + heartbeats | 4 caller side + session touch, 5 callee side + heartbeats |
| 4 TS edges | 6 |
| 5 mailbox | 7 parked reply sessions |
| 6 handoff | 8 |
| 7 orchestrator | 9 |

Dependency spine: 1 → 2 → 3 → 4 → 7 → 8, with 5 after 4, 6 after 1, 9 after 3.

## Scaling

Watches are per address, not per request; the registration-event stream they ride on is
delivered to every node already (`__vertx.subs` is REPLICATED, FULL_SYNC); no new replicated
state is added. The genuinely new per-request cost is the gateway record (Phases 4 and 5): tens of
bytes, on the connection's own event loop, behind the per-send `pause()` in
`DefaultStompServerHandler` that bounds in-flight requests per connection today. The one place
to be deliberate is the cluster manager's shared delivery context under a death burst: `onLost`
dispatches, never runs continuations inline.
