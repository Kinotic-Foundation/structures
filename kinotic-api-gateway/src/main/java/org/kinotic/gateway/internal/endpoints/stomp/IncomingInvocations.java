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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The invocations one STOMP connection's client has made to services on the cluster and is still waiting
 * on, with the subscriptions on its reply destinations they come back through. Each is pinned to the node
 * that acknowledged it, and one whose node leaves the cluster is answered on the connection's reply
 * destination with an {@link RpcServiceUnavailableException}, the way one that fails to send is.
 *
 * Created by Navíd Mitchell 🤪 on 9/9/26.
 */
@Slf4j
public class IncomingInvocations {

    private final Services services;
    private final Map<String, EventConsumer> replySubscriptions = new HashMap<>();
    // the reply metadata of every forwarded invocation, keyed by correlation id, until its terminal reply
    private final ConcurrentHashMap<String, Metadata> invocations = new ConcurrentHashMap<>();

    public IncomingInvocations(Services services) {
        this.services = services;
    }

    /**
     * Subscribes the connection to one of its reply destinations. Every reply passing through settles the
     * invocation it answers before it is handed to the subscription handler.
     */
    public void subscribe(CRI cri, String subscriptionIdentifier, StompSubscriptionHandler subscriptionHandler) {
        EventConsumer eventConsumer = services.eventBusService.listen(cri);
        eventConsumer.handler(event -> {
                         settleIfTerminal(event);
                         subscriptionHandler.handleEvent(event);
                     })
                     .exceptionHandler(subscriptionHandler::handleError);
        replySubscriptions.put(subscriptionIdentifier, eventConsumer);
    }

    /**
     * @return true when the identifier named one of this connection's reply subscriptions, which is now gone
     */
    public boolean unsubscribe(String subscriptionIdentifier) {
        EventConsumer consumer = replySubscriptions.remove(subscriptionIdentifier);
        if (consumer != null) {
            consumer.unregister();
        }
        return consumer != null;
    }

    /**
     * Records an invocation the connection is about to forward, or applies the control message it is. One
     * without a correlation id has no reply that could be matched to it and is not recorded.
     */
    public void track(Event<byte[]> request) {
        String correlationId = request.metadata().get(EventConstants.CORRELATION_ID_HEADER);
        if (correlationId != null) {
            String control = request.metadata().get(EventConstants.CONTROL_HEADER);
            if (control == null) {
                invocations.put(correlationId, EventUtil.replyMetadataOf(request.metadata()));
            } else if (EventConstants.CONTROL_VALUE_CANCEL.equals(control)) {
                // the caller gave up on the stream, so no reply is owed to it any more
                settle(correlationId);
            }
        }
    }

    /**
     * Pins a recorded invocation to the node that acknowledged it. One already settled, by a reply that
     * arrived before the acknowledgement was processed, stays settled.
     */
    public void pin(String correlationId, String nodeId, CRI destination) {
        if (correlationId != null) {
            // computeIfPresent serializes with settle() on this key
            invocations.computeIfPresent(correlationId, (_, replyMetadata) -> {
                services.requestLivenessWatcher.watch(correlationId, nodeId, () -> fail(correlationId, destination, nodeId));
                return replyMetadata;
            });
        }
    }

    /**
     * Forgets an invocation: its reply arrived, its send failed, or its caller cancelled it.
     */
    public void settle(String correlationId) {
        if (correlationId != null && invocations.remove(correlationId) != null) {
            services.requestLivenessWatcher.settle(correlationId);
        }
    }

    /**
     * Ends the connection's incoming invocations: nothing stays pinned and no reply destination stays subscribed.
     */
    public void dispose() {
        invocations.keySet().forEach(this::settle);
        replySubscriptions.values().forEach(EventConsumer::unregister);
        replySubscriptions.clear();
    }

    private void settleIfTerminal(Event<byte[]> reply) {
        if (EventUtil.isTerminalReply(reply.metadata())) {
            settle(reply.metadata().get(EventConstants.CORRELATION_ID_HEADER));
        }
    }

    // Runs on the connection's context when the node that took the invocation leaves the cluster. Only the
    // party that removes the record answers, so a reply that settled the invocation first leaves nothing to fail.
    private void fail(String correlationId, CRI destination, String nodeId) {
        Metadata replyMetadata = invocations.remove(correlationId);
        if (replyMetadata != null) {
            RpcServiceUnavailableException cause = new RpcServiceUnavailableException(
                    "Node " + nodeId + " left the cluster while serving the request to " + destination.raw());
            try {
                services.eventBusService.send(services.exceptionConverter.convert(replyMetadata, cause));
            } catch (Exception e) {
                log.error("Could not answer request {} to {} after node {} left", correlationId, destination.raw(), nodeId, e);
            }
        }
    }
}
