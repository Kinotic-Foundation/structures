package org.kinotic.gateway.internal.endpoints.stomp;

import lombok.extern.slf4j.Slf4j;
import org.kinotic.core.api.event.CRI;
import org.kinotic.core.api.event.Event;
import org.kinotic.core.api.event.EventConstants;
import org.kinotic.core.api.event.EventConsumer;
import org.kinotic.core.api.event.Metadata;
import org.kinotic.core.api.exceptions.RpcServiceUnavailableException;
import org.kinotic.core.internal.utils.EventUtil;
import org.kinotic.gateway.internal.endpoints.Services;

import tools.jackson.core.type.TypeReference;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The reply side of one STOMP connection: its subscriptions on its reply destinations, and the service
 * requests it forwarded whose replies are still outstanding. An outstanding request is pinned to the node
 * that acknowledged it, and one whose node leaves the cluster is answered on the connection's reply
 * destination with an {@link RpcServiceUnavailableException}, the way a request that fails to send is.
 * <p>
 * The state outlives a sticky session's connection: parked, it keeps its reply subscriptions registered and
 * buffers what they receive, up to a byte budget, so the same client reconnecting takes every reply and
 * every pinned request over through {@link #adopt} and {@link #rebind}. A client that reconnects to another
 * node has that node announce itself on the reply destination; the state then hands over across the
 * cluster: its subscription steps aside, what it held is replayed to the destination, and the requests it
 * was watching follow in the flush-complete message for the new node to watch.
 *
 * Created by Navíd Mitchell 🤪 on 9/9/26.
 */
@Slf4j
public class ReplySessionState {

    private final Services services;
    private final ConcurrentHashMap<String, ReplySubscription> replySubscriptions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, PendingRequest> pendingRequests = new ConcurrentHashMap<>();
    // names this state in the release it publishes, so the echo of its own announcement is ignored
    private final String stateId = UUID.randomUUID().toString();
    private volatile Runnable onOverflow;   // set while parked
    private volatile Runnable onHandedOff;  // set while parked
    private long bufferedBytes;              // touched only under the parking lock

    public ReplySessionState(Services services) {
        this.services = services;
    }

    /**
     * Subscribes the connection to one of its reply destinations. Every reply passing through settles the
     * request it answers before it is handed to the subscription handler.
     */
    public void subscribe(CRI cri, String subscriptionIdentifier, StompSubscriptionHandler subscriptionHandler) {
        ReplySubscription subscription = new ReplySubscription(cri.raw(), subscriptionIdentifier, subscriptionHandler);
        subscription.owner = this;
        EventConsumer eventConsumer = services.eventBusService.listen(cri);
        eventConsumer.handler(subscription::deliver)
                     .exceptionHandler(throwable -> subscription.handler.handleError(throwable));
        subscription.consumer = eventConsumer;
        replySubscriptions.put(cri.raw(), subscription);
        // Whoever holds this destination's state on another node hands it over. Published only once this
        // consumer is registered, so the replay routes here and nowhere else.
        eventConsumer.completion().onSuccess(_ -> {
            Metadata release = Metadata.create();
            release.put(EventConstants.CONTROL_HEADER, EventConstants.CONTROL_VALUE_REPLY_SESSION_RELEASE);
            release.put(EventConstants.RELEASE_ORIGIN_HEADER, stateId);
            services.eventBusService.publish(Event.create(cri, release, null));
        });
    }

    /**
     * Binds a reply destination this state already listens on to a new connection's subscription handler,
     * delivering what was buffered while parked before anything live.
     * @return true when the destination was one of this state's, false when a subscription is still needed
     */
    public boolean rebind(CRI cri, String subscriptionIdentifier, StompSubscriptionHandler subscriptionHandler) {
        ReplySubscription subscription = replySubscriptions.get(cri.raw());
        if (subscription != null) {
            subscription.rebind(subscriptionIdentifier, subscriptionHandler);
        }
        return subscription != null;
    }

    /**
     * @return true when the identifier named one of this connection's reply subscriptions, which is now gone
     */
    public boolean unsubscribe(String subscriptionIdentifier) {
        ReplySubscription found = null;
        for (ReplySubscription subscription : replySubscriptions.values()) {
            if (subscriptionIdentifier.equals(subscription.subscriptionId)) {
                found = subscription;
            }
        }
        if (found != null) {
            replySubscriptions.remove(found.cri);
            found.consumer.unregister();
        }
        return found != null;
    }

    /**
     * @return the reply destinations this state listens on
     */
    public Set<String> replyDestinations() {
        return Set.copyOf(replySubscriptions.keySet());
    }

    /**
     * Records a service request the connection is about to forward, or applies the control message it is.
     * A request without a correlation id has no reply that could be matched to it and is not recorded.
     */
    public void track(Event<byte[]> request) {
        String correlationId = request.metadata().get(EventConstants.CORRELATION_ID_HEADER);
        if (correlationId != null) {
            String control = request.metadata().get(EventConstants.CONTROL_HEADER);
            if (control == null) {
                pendingRequests.put(correlationId, new PendingRequest(EventUtil.replyMetadataOf(request.metadata()), null));
            } else if (EventConstants.CONTROL_VALUE_CANCEL.equals(control)) {
                // the caller gave up on the stream, so no reply is owed to it any more
                settle(correlationId);
            }
        }
    }

    /**
     * Pins a recorded request to the node that acknowledged it. A request already settled, by a reply that
     * arrived before the acknowledgement was processed, stays settled.
     */
    public void pin(String correlationId, String nodeId, CRI destination) {
        if (correlationId != null) {
            // computeIfPresent serializes with settle() on this key
            pendingRequests.computeIfPresent(correlationId, (_, pending) -> {
                services.requestLivenessWatcher.watch(correlationId, nodeId, () -> fail(correlationId, destination, nodeId));
                return new PendingRequest(pending.replyMetadata(), nodeId);
            });
        }
    }

    /**
     * Forgets a request: its reply arrived, its send failed, or its caller cancelled it.
     */
    public void settle(String correlationId) {
        if (correlationId != null && pendingRequests.remove(correlationId) != null) {
            services.requestLivenessWatcher.settle(correlationId);
        }
    }

    /**
     * Keeps the state alive without a connection: replies are buffered instead of delivered, up to the
     * configured byte budget, and the pinned requests stay pinned.
     * @param onOverflow run once if the buffered replies exceed the budget
     */
    public void park(Runnable onOverflow, Runnable onHandedOff) {
        this.onOverflow = onOverflow;
        this.onHandedOff = onHandedOff;
        replySubscriptions.values().forEach(ReplySubscription::park);
    }

    /**
     * Takes over everything another state holds: its pinned requests, re-pinned to this state, and its reply
     * subscriptions with whatever they buffered. The other state is left empty.
     */
    public void adopt(ReplySessionState other) {
        other.pendingRequests.forEach((correlationId, pending) -> {
            pendingRequests.put(correlationId, pending);
            if (pending.nodeId() != null) {
                // watch() replaces the lease, so the other state's callback is dropped with it
                services.requestLivenessWatcher.watch(correlationId, pending.nodeId(), () -> fail(correlationId, null, pending.nodeId()));
            }
        });
        other.pendingRequests.clear();
        other.replySubscriptions.forEach((cri, subscription) -> {
            subscription.owner = this;
            replySubscriptions.put(cri, subscription);
        });
        other.replySubscriptions.clear();
        other.onOverflow = null;
        other.onHandedOff = null;
    }

    /**
     * Ends the reply side of the connection: nothing stays pinned, nothing stays buffered, and no reply
     * destination stays subscribed.
     */
    public void dispose() {
        pendingRequests.keySet().forEach(this::settle);
        replySubscriptions.values().forEach(subscription -> subscription.consumer.unregister());
        replySubscriptions.clear();
        onOverflow = null;
        onHandedOff = null;
    }

    // Another node's connection took the destination over: this subscription steps aside, replays what it
    // held to the destination, which now routes only there, and sends the pending requests after it. The
    // requests are settled here because the watcher is node-local; the other node pins them again.
    private void handOff(ReplySubscription subscription) {
        replySubscriptions.remove(subscription.cri);
        subscription.consumer.unregister().onComplete(_ -> {
            CRI destination = CRI.create(subscription.cri);
            services.eventBusService.send(control(destination, EventConstants.CONTROL_VALUE_FLUSH_BEGIN, null));
            for (Event<byte[]> event : subscription.drain()) {
                event.metadata().put(EventConstants.REPLAYED_HEADER, "true");
                services.eventBusService.send(event);
            }
            List<PendingRequestRecord> records = new ArrayList<>();
            pendingRequests.forEach((correlationId, pending) -> {
                Map<String, String> replyMetadata = new HashMap<>();
                pending.replyMetadata().forEach(entry -> replyMetadata.put(entry.getKey(), entry.getValue()));
                records.add(new PendingRequestRecord(correlationId, pending.nodeId(), replyMetadata));
            });
            pendingRequests.keySet().forEach(this::settle);
            services.eventBusService.send(control(destination, EventConstants.CONTROL_VALUE_FLUSH_COMPLETE,
                                                  services.jsonMapper.writeValueAsBytes(records)));
            Runnable handedOff = onHandedOff;
            if (replySubscriptions.isEmpty() && handedOff != null) {
                handedOff.run();
            }
        });
    }

    // The flush-complete of a handing-over node: its pending requests are pinned here from now on
    private void adoptRecords(byte[] body) {
        if (body != null && body.length > 0) {
            List<PendingRequestRecord> records = services.jsonMapper.readValue(body, new TypeReference<List<PendingRequestRecord>>() {});
            for (PendingRequestRecord record : records) {
                pendingRequests.put(record.getCorrelationId(),
                                    new PendingRequest(Metadata.create(record.getReplyMetadata()), record.getNodeId()));
                if (record.getNodeId() != null) {
                    services.requestLivenessWatcher.watch(record.getCorrelationId(), record.getNodeId(),
                                                          () -> fail(record.getCorrelationId(), null, record.getNodeId()));
                }
            }
        }
    }

    private static Event<byte[]> control(CRI destination, String value, byte[] body) {
        Metadata metadata = Metadata.create();
        metadata.put(EventConstants.CONTROL_HEADER, value);
        return Event.create(destination, metadata, body);
    }

    private void settleIfTerminal(Event<byte[]> reply) {
        if (EventUtil.isTerminalReply(reply.metadata())) {
            settle(reply.metadata().get(EventConstants.CORRELATION_ID_HEADER));
        }
    }

    // Runs on the connection's context when the node that took the request leaves the cluster. Only the
    // party that removes the record answers, so a reply that settled the request first leaves nothing to fail.
    private void fail(String correlationId, CRI destination, String nodeId) {
        PendingRequest pending = pendingRequests.remove(correlationId);
        if (pending != null) {
            String target = destination != null ? destination.raw() : "the service";
            RpcServiceUnavailableException cause = new RpcServiceUnavailableException(
                    "Node " + nodeId + " left the cluster while serving the request to " + target);
            try {
                services.eventBusService.send(services.exceptionConverter.convert(pending.replyMetadata(), cause));
            } catch (Exception e) {
                log.error("Could not answer request {} to {} after node {} left", correlationId, target, nodeId, e);
            }
        }
    }

    // The buffered bytes are accounted across every subscription of the state, under one lock
    private synchronized boolean buffer(int bytes) {
        bufferedBytes += bytes;
        return bufferedBytes <= services.apiGatewayProperties.getReplyBufferMaxBytes();
    }

    private synchronized void release(int bytes) {
        bufferedBytes -= bytes;
    }

    private record PendingRequest(Metadata replyMetadata, String nodeId) {}

    /**
     * One reply destination the state listens on. Delivery and the buffered replies are guarded by the
     * subscription's own monitor: the consumer delivers on the context it was registered from, while a
     * reconnect rebinds and flushes from the new connection's.
     */
    private static class ReplySubscription {
        final String cri;
        volatile String subscriptionId;
        volatile StompSubscriptionHandler handler;
        volatile ReplySessionState owner;
        EventConsumer consumer;
        private final ArrayDeque<Event<byte[]>> buffered = new ArrayDeque<>();
        // between a flush-begin and its flush-complete, everything arriving waits behind the replay
        private final List<Event<byte[]>> held = new ArrayList<>();
        private boolean holding;
        private boolean parked;

        ReplySubscription(String cri, String subscriptionId, StompSubscriptionHandler handler) {
            this.cri = cri;
            this.subscriptionId = subscriptionId;
            this.handler = handler;
        }

        synchronized void deliver(Event<byte[]> event) {
            ReplySessionState state = owner;
            String control = event.metadata().get(EventConstants.CONTROL_HEADER);
            if (EventConstants.CONTROL_VALUE_REPLY_SESSION_RELEASE.equals(control)) {
                if (!state.stateId.equals(event.metadata().get(EventConstants.RELEASE_ORIGIN_HEADER))) {
                    state.handOff(this);
                }
            } else if (EventConstants.CONTROL_VALUE_FLUSH_BEGIN.equals(control)) {
                holding = true;
            } else if (EventConstants.CONTROL_VALUE_FLUSH_COMPLETE.equals(control)) {
                state.adoptRecords(event.data());
                holding = false;
                // everything replayed precedes everything that arrived live during the handoff
                List<Event<byte[]>> replayed = new ArrayList<>();
                List<Event<byte[]>> live = new ArrayList<>();
                for (Event<byte[]> heldEvent : held) {
                    if (heldEvent.metadata().contains(EventConstants.REPLAYED_HEADER)) {
                        heldEvent.metadata().remove(EventConstants.REPLAYED_HEADER);
                        replayed.add(heldEvent);
                    } else {
                        live.add(heldEvent);
                    }
                }
                held.clear();
                replayed.forEach(this::pass);
                live.forEach(this::pass);
            } else if (holding) {
                held.add(event);
            } else {
                pass(event);
            }
        }

        // Delivery proper: settle what the reply answers, then hand it on or hold it while parked
        private void pass(Event<byte[]> event) {
            ReplySessionState state = owner;
            state.settleIfTerminal(event);
            if (parked) {
                buffered.add(event);
                if (!state.buffer(sizeOf(event))) {
                    Runnable overflow = state.onOverflow;
                    if (overflow != null) {
                        overflow.run();
                    }
                }
            } else {
                handler.handleEvent(event);
            }
        }

        synchronized List<Event<byte[]>> drain() {
            List<Event<byte[]>> ret = new ArrayList<>(buffered);
            buffered.forEach(event -> owner.release(sizeOf(event)));
            buffered.clear();
            return ret;
        }

        synchronized void park() {
            parked = true;
        }

        synchronized void rebind(String newSubscriptionId, StompSubscriptionHandler newHandler) {
            subscriptionId = newSubscriptionId;
            handler = newHandler;
            parked = false;
            Event<byte[]> event;
            while ((event = buffered.poll()) != null) {
                owner.release(sizeOf(event));
                newHandler.handleEvent(event);
            }
        }

        private static int sizeOf(Event<byte[]> event) {
            return event.data() == null ? 0 : event.data().length;
        }
    }
}
