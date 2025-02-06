package org.kinotic.structures.internal.api.services;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.kinotic.structures.api.domain.idl.decorators.MultiTenancyType;

/**
 * {@link EntityHolder} holds the data and the id for an entity
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
public record EntityHolder<T>(T entity, String id, MultiTenancyType multiTenancyType, String tenantId, String version) {

    public String getDocumentId() {
        return multiTenancyType == MultiTenancyType.SHARED ? tenantId + "-" + id : id;
    }

    public ElasticVersion getElasticVersionIfPresent() {
        // For the first save we allow null or empty string for version
        if (version == null || version.isEmpty()) {
            return null;
        }
        String[] parts = version.split(":");
        if(parts.length != 2){
            throw new IllegalArgumentException("Invalid version format: " + version);
        }
        return new ElasticVersion(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

}
