package org.kinotic.gateway.internal.endpoints.stomp;

import io.vertx.core.Context;
import org.kinotic.core.api.event.CRI;
import org.kinotic.core.api.event.Metadata;

/**
 * One invocation the gateway wrote down a STOMP connection to a service its client publishes: the address
 * it was delivered to, the metadata every reply to it carries, the subscription it went through, and the
 * Vert.x context the connection runs on.
 *
 * Created by Navíd Mitchell 🤪 on 9/10/26.
 */
public record OutgoingInvocation(CRI cri,
                                  Metadata replyMetadata,
                                  StompSubscriptionHandler subscriptionHandler,
                                  Context context) {
}
