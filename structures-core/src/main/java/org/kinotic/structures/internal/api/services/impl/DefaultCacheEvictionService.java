package org.kinotic.structures.internal.api.services.impl;

import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.CacheEvictionService;
import org.kinotic.structures.internal.graphql.GraphQLProviderService;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
@Component
public class DefaultCacheEvictionService implements CacheEvictionService {

    private final GraphQLProviderService graphQLProviderService;
    private final EntitiesService entitiesService;

    public DefaultCacheEvictionService(GraphQLProviderService graphQLProviderService,
                                       EntitiesService entitiesService) {
        this.graphQLProviderService = graphQLProviderService;
        this.entitiesService = entitiesService;
    }

    @Override
    public void evictCachesFor(Structure structure) {
        graphQLProviderService.evictCachesFor(structure);
        entitiesService.evictCachesFor(structure);
    }
}
