package org.kinotic.structures.internal.endpoints;

import io.vertx.ext.web.RoutingContext;
import org.kinotic.continuum.core.api.event.EventConstants;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.domain.EntityContext;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public class RoutingContextToEntityContextAdapter implements EntityContext {

    private final RoutingContext routingContext;

    public RoutingContextToEntityContextAdapter(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    @Override
    public Participant getParticipant() {
        return routingContext.get(EventConstants.SENDER_HEADER);
    }

    @Override
    public List<String> getIncludedFieldsFilter() {
        return null;
    }

    @Override
    public boolean hasIncludedFieldsFilter() {
        return false;
    }
}
