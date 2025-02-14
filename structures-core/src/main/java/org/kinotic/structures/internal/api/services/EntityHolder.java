package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.domain.idl.decorators.MultiTenancyType;

/**
 * {@link EntityHolder} holds the data and the id for an entity
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
public record EntityHolder<T>(T entity, String id, MultiTenancyType multiTenancyType, String tenantId, String version) {

    public String getDocumentId() {
        return multiTenancyType == MultiTenancyType.SHARED ? tenantId + "-" + id : id;
    }

    public boolean isElasticVersionPresent(){
        // For the initial save we require a null or empty string
        return version != null && !version.isEmpty();
    }

    public ElasticVersion getElasticVersionIfPresent() {
        if (!isElasticVersionPresent()) {
            return null;
        }
        String[] parts = version.split(":");
        if(parts.length != 2){
            throw new IllegalArgumentException("Invalid version format: " + version);
        }
        return new ElasticVersion(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

}
