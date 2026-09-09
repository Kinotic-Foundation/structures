package org.kinotic.gateway.internal.endpoints.stomp;

import lombok.extern.slf4j.Slf4j;
import org.kinotic.core.api.event.Event;
import org.kinotic.core.api.event.EventConstants;
import org.kinotic.core.api.event.Metadata;
import org.kinotic.core.api.exceptions.RpcServiceUnavailableException;
import org.kinotic.core.internal.utils.EventUtil;
import org.kinotic.gateway.internal.endpoints.Services;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The callee side of one STOMP connection: the invocations delivered to the services it publishes whose
 * terminal replies have not come back through it. The acknowledgement for such an invocation named this
 * gateway, so the requester's lease cannot tell one connection on it from another; the connection can, and
 * when it closes every invocation still outstanding on it is answered on the requester's reply destination
 * with an {@link RpcServiceUnavailableException}.
 *
 * Created by Navíd Mitchell 🤪 on 9/9/26.
 */
@Slf4j
public class ServiceSessionState {

    private final Services services;
    // the reply metadata of every delivered invocation, keyed by correlation id, until its terminal reply
    private final ConcurrentHashMap<String, Metadata> outstandingInvocations = new ConcurrentHashMap<>();

    public ServiceSessionState(Services services) {
        this.services = services;
    }

    /**
     * Records an event about to be delivered to one of the connection's service subscriptions. An
     * invocation without a correlation id or reply destination has no reply to await and is not recorded;
     * a cancel control forgets the invocation it names.
     */
    public void deliver(Event<byte[]> event) {
        Metadata metadata = event.metadata();
        String correlationId = metadata.get(EventConstants.CORRELATION_ID_HEADER);
        if (correlationId != null) {
            String control = metadata.get(EventConstants.CONTROL_HEADER);
            if (control == null) {
                if (metadata.contains(EventConstants.REPLY_TO_HEADER)) {
                    outstandingInvocations.put(correlationId, EventUtil.replyMetadataOf(metadata));
                }
            } else if (EventConstants.CONTROL_VALUE_CANCEL.equals(control)) {
                outstandingInvocations.remove(correlationId);
            }
        }
    }

    /**
     * Forgets the invocation a reply from the connection answers, once that reply is terminal.
     */
    public void settleIfTerminal(Event<byte[]> reply) {
        if (EventUtil.isTerminalReply(reply.metadata())) {
            String correlationId = reply.metadata().get(EventConstants.CORRELATION_ID_HEADER);
            if (correlationId != null) {
                outstandingInvocations.remove(correlationId);
            }
        }
    }

    /**
     * Ends the callee side of the connection: every invocation still outstanding is answered with an
     * {@link RpcServiceUnavailableException} on its requester's reply destination.
     */
    public void dispose() {
        outstandingInvocations.forEach((correlationId, replyMetadata) -> {
            RpcServiceUnavailableException cause = new RpcServiceUnavailableException(
                    "The connection serving the request disconnected before replying");
            try {
                services.eventBusService.send(services.exceptionConverter.convert(replyMetadata, cause));
            } catch (Exception e) {
                log.error("Could not answer invocation {} after its connection closed", correlationId, e);
            }
        });
        outstandingInvocations.clear();
    }
}
