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
 * The reply side of one STOMP connection: its subscriptions on its reply destinations, and the service
 * requests it forwarded whose replies are still outstanding. An outstanding request is pinned to the node
 * that acknowledged it, and one whose node leaves the cluster is answered on the connection's reply
 * destination with an {@link RpcServiceUnavailableException}, the way a request that fails to send is.
 *
 * Created by Navíd Mitchell 🤪 on 9/9/26.
 */
@Slf4j
public class ReplySessionState {

    private final Services services;
    private final Map<String, EventConsumer> replySubscriptions = new HashMap<>();
    // the reply metadata of every forwarded request, keyed by correlation id, until its terminal reply
    private final ConcurrentHashMap<String, Metadata> pendingRequests = new ConcurrentHashMap<>();

    public ReplySessionState(Services services) {
        this.services = services;
    }

    /**
     * Subscribes the connection to one of its reply destinations. Every reply passing through settles the
     * request it answers before it is handed to the subscription handler.
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
     * Records a service request the connection is about to forward, or applies the control message it is.
     * A request without a correlation id has no reply that could be matched to it and is not recorded.
     */
    public void track(Event<byte[]> request) {
        String correlationId = request.metadata().get(EventConstants.CORRELATION_ID_HEADER);
        if (correlationId != null) {
            String control = request.metadata().get(EventConstants.CONTROL_HEADER);
            if (control == null) {
                pendingRequests.put(correlationId, EventUtil.replyMetadataOf(request.metadata()));
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
            pendingRequests.computeIfPresent(correlationId, (_, replyMetadata) -> {
                services.requestLivenessWatcher.watch(correlationId, nodeId, () -> fail(correlationId, destination, nodeId));
                return replyMetadata;
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
     * Ends the reply side of the connection: nothing stays pinned and no reply destination stays subscribed.
     */
    public void dispose() {
        pendingRequests.keySet().forEach(this::settle);
        replySubscriptions.values().forEach(EventConsumer::unregister);
        replySubscriptions.clear();
    }

    private void settleIfTerminal(Event<byte[]> reply) {
        if (EventUtil.isTerminalReply(reply.metadata())) {
            settle(reply.metadata().get(EventConstants.CORRELATION_ID_HEADER));
        }
    }

    // Runs on the connection's context when the node that took the request leaves the cluster. Only the
    // party that removes the record answers, so a reply that settled the request first leaves nothing to fail.
    private void fail(String correlationId, CRI destination, String nodeId) {
        Metadata replyMetadata = pendingRequests.remove(correlationId);
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
