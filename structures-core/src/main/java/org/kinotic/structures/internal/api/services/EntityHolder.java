package org.kinotic.structures.internal.api.services;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.structures.api.decorators.MultiTenancyType;

/**
 * {@link EntityHolder} holds the data and the id for an entity
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
@Getter
@Setter
@Accessors(chain = true)
public class EntityHolder {

    private Object entity;
    private String id;
    private MultiTenancyType multiTenancyType;
    private String tenantId;

    public EntityHolder(String id,
                        String tenantId,
                        MultiTenancyType multiTenancyType,
                        Object entity) {
        this.id = id;
        this.tenantId = tenantId;
        this.multiTenancyType = multiTenancyType;
        this.entity = entity;
    }

    public String getDocumentId() {
        return multiTenancyType == MultiTenancyType.SHARED ? tenantId + "-" + id : id;
    }

}
