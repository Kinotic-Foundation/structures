package org.kinotic.structures.internal.endpoints;

import io.vertx.ext.web.RoutingContext;
import org.kinotic.continuum.core.api.event.EventConstants;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.domain.EntityContext;

import java.util.List;
import java.util.Map;

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

    @Override
    public EntityContext put(String key, Object obj) {
        routingContext.put(key, obj);
        return this;
    }

    @Override
    public <T> T get(String key) {
        return routingContext.get(key);
    }

    @Override
    public <T> T remove(String key) {
        return routingContext.remove(key);
    }

    @Override
    public Map<String, Object> data() {
        return routingContext.data();
    }
}
