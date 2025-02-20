package org.kinotic.structures.api.domain;

import org.kinotic.structures.internal.api.services.DefaultTenantSpecificId;

/**
 * Created By Navíd Mitchell 🤪on 2/12/25
 */
public interface TenantSpecificId {

    String entityId();

    String tenantId();

    static TenantSpecificId create(String entityId, String tenantId){
        return new DefaultTenantSpecificId(entityId, tenantId);
    }

}
