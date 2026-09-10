package org.kinotic.gateway.internal.endpoints.stomp;

import io.vertx.core.Context;
import org.kinotic.core.api.event.CRI;
import org.kinotic.core.api.event.Metadata;

/**
 * An invocation delivered to a service instance over one STOMP connection: the address it was delivered
 * to, the metadata every reply to it carries, the subscription it was delivered through, and the Vert.x
 * context the connection runs on.
 *
 * Created by Navíd Mitchell 🤪 on 9/10/26.
 */
public record DeliveredInvocation(CRI cri,
                                  Metadata replyMetadata,
                                  StompSubscriptionHandler subscriptionHandler,
                                  Context context) {
}
