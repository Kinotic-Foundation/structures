package org.kinotic.structures.internal.endpoints.openapi;

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
    private List<String> tenantSelection;

    public RoutingContextToEntityContextAdapter(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    @Override
    public Map<String, Object> data() {
        return routingContext.data();
    }

    @Override
    public <T> T get(String key) {
        return routingContext.get(key);
    }

    @Override
    public List<String> getIncludedFieldsFilter() {
        return null;
    }

    @Override
    public Participant getParticipant() {
        return routingContext.get(EventConstants.SENDER_HEADER);
    }

    @Override
    public boolean hasIncludedFieldsFilter() {
        return false;
    }

    @Override
    public List<String> getTenantSelection() {
        return tenantSelection;
    }

    @Override
    public RoutingContextToEntityContextAdapter setTenantSelection(List<String> tenantSelection) {
        this.tenantSelection = tenantSelection;
        return this;
    }

    @Override
    public EntityContext put(String key, Object obj) {
        routingContext.put(key, obj);
        return this;
    }

    @Override
    public EntityContext putAll(Map<String, Object> value) {
        for(Map.Entry<String, Object> entry : value.entrySet()){
            routingContext.put(entry.getKey(), entry.getValue());
        }
        return null;
    }

    @Override
    public <T> T remove(String key) {
        return routingContext.remove(key);
    }
}
