package org.kinotic.gateway.internal.endpoints.stomp;

import lombok.extern.slf4j.Slf4j;
import org.kinotic.core.api.event.CRI;
import org.kinotic.core.api.event.Event;
import org.kinotic.core.api.event.EventConstants;
import org.kinotic.core.api.event.ListenerStatus;
import org.kinotic.core.api.event.Metadata;
import org.kinotic.core.api.exceptions.RpcServiceUnavailableException;
import org.kinotic.core.internal.utils.EventUtil;
import org.kinotic.gateway.internal.endpoints.Services;
import reactor.core.Disposable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The invocations delivered to the services one STOMP connection's client publishes, whose terminal
 * replies have not come back through it. The acknowledgement for such an invocation named this
 * gateway, so the requester's lease cannot tell one connection on it from another; the connection can, and
 * when it closes every invocation still outstanding on it is answered on the requester's reply destination
 * with an {@link RpcServiceUnavailableException}. An invocation that answers with a stream is cancelled on
 * the connection when its requester's reply destination is gone, so the service stops producing for it.
 *
 * Created by Navíd Mitchell 🤪 on 9/9/26.
 */
@Slf4j
public class OutgoingInvocations {

    private final Services services;
    // every delivered invocation, keyed by correlation id, until its terminal reply
    private final ConcurrentHashMap<String, DeliveredInvocation> invocations = new ConcurrentHashMap<>();
    // the requester watch of every invocation that has answered with a stream value, keyed the same way
    private final ConcurrentHashMap<String, Disposable> requesterMonitors = new ConcurrentHashMap<>();

    public OutgoingInvocations(Services services) {
        this.services = services;
    }

    /**
     * Records an event about to be delivered to one of the connection's service subscriptions. An
     * invocation without a correlation id or reply destination has no reply to await and is not recorded;
     * a cancel control forgets the invocation it names.
     * @param event the event being delivered
     * @param subscriptionHandler the subscription delivering it, which also carries a cancel back to the service
     */
    public void deliver(Event<byte[]> event, StompSubscriptionHandler subscriptionHandler) {
        Metadata metadata = event.metadata();
        String correlationId = metadata.get(EventConstants.CORRELATION_ID_HEADER);
        if (correlationId != null) {
            String control = metadata.get(EventConstants.CONTROL_HEADER);
            if (control == null) {
                if (metadata.contains(EventConstants.REPLY_TO_HEADER)) {
                    invocations.put(correlationId, new DeliveredInvocation(event.cri(),
                                                                                       EventUtil.replyMetadataOf(metadata),
                                                                                       subscriptionHandler,
                                                                                       services.vertx.getOrCreateContext()));
                }
            } else if (EventConstants.CONTROL_VALUE_CANCEL.equals(control)) {
                forget(correlationId);
            }
        }
    }

    /**
     * Observes a reply the connection sends. A terminal reply forgets the invocation it answers; a stream
     * value starts watching the requester's reply destination, so the stream is cancelled on the connection
     * once nothing listens there.
     */
    public void observeReply(Event<byte[]> reply) {
        String correlationId = reply.metadata().get(EventConstants.CORRELATION_ID_HEADER);
        if (correlationId != null) {
            if (EventUtil.isTerminalReply(reply.metadata())) {
                forget(correlationId);
            } else {
                DeliveredInvocation invocation = invocations.get(correlationId);
                if (invocation != null) {
                    requesterMonitors.computeIfAbsent(correlationId, _ -> watchRequester(correlationId, invocation));
                }
            }
        }
    }

    /**
     * Ends the callee side of the connection: every invocation still outstanding is answered with an
     * {@link RpcServiceUnavailableException} on its requester's reply destination.
     */
    public void dispose() {
        requesterMonitors.values().forEach(Disposable::dispose);
        requesterMonitors.clear();
        invocations.forEach((correlationId, invocation) -> {
            RpcServiceUnavailableException cause = new RpcServiceUnavailableException(
                    "The connection serving the request disconnected before replying");
            try {
                services.eventBusService.send(services.exceptionConverter.convert(invocation.replyMetadata(), cause));
            } catch (Exception e) {
                log.error("Could not answer invocation {} after its connection closed", correlationId, e);
            }
        });
        invocations.clear();
    }

    private Disposable watchRequester(String correlationId, DeliveredInvocation invocation) {
        CRI replyCri = CRI.create(invocation.replyMetadata().get(EventConstants.REPLY_TO_HEADER));
        return services.eventBusService
                       .monitorListenerStatus(replyCri)
                       .subscribe(status -> {
                                      // the status arrives on the cluster manager's thread; the cancel is a
                                      // frame on the connection, so it is written from the connection's context
                                      if (status == ListenerStatus.INACTIVE) {
                                          invocation.context().runOnContext(_ -> cancel(correlationId));
                                      }
                                  },
                                  throwable -> log.warn("Requester watch for invocation {} failed", correlationId, throwable));
    }

    // Delivers a cancel control for a stream whose requester is gone; a stream that ended in the meantime is
    // already forgotten and takes nothing
    private void cancel(String correlationId) {
        DeliveredInvocation invocation = invocations.get(correlationId);
        if (invocation != null) {
            forget(correlationId);
            Metadata metadata = Metadata.create(Map.of(EventConstants.CONTROL_HEADER, EventConstants.CONTROL_VALUE_CANCEL,
                                                       EventConstants.CORRELATION_ID_HEADER, correlationId));
            invocation.subscriptionHandler().handleEvent(Event.create(invocation.cri(), metadata, null));
        }
    }

    private void forget(String correlationId) {
        invocations.remove(correlationId);
        Disposable monitor = requesterMonitors.remove(correlationId);
        if (monitor != null) {
            monitor.dispose();
        }
    }
}
