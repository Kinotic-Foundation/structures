package org.kinotic.structures.internal.api.services;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.kinotic.structures.api.domain.idl.decorators.MultiTenancyType;

/**
 * {@link EntityHolder} holds the data and the id for an entity
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
public record EntityHolder(Object entity, String id, MultiTenancyType multiTenancyType, String tenantId) {

    public String getDocumentId() {
        return multiTenancyType == MultiTenancyType.SHARED ? tenantId + "-" + id : id;
    }

}
