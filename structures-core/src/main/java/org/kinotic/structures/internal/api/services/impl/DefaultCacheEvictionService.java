package org.kinotic.structures.internal.api.services.impl;

import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.CacheEvictionService;
import org.kinotic.structures.internal.graphql.GqlProviderService;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
@Component
public class DefaultCacheEvictionService implements CacheEvictionService {

    private final GqlProviderService gqlProviderService;
    private final EntitiesService entitiesService;

    public DefaultCacheEvictionService(GqlProviderService gqlProviderService,
                                       EntitiesService entitiesService) {
        this.gqlProviderService = gqlProviderService;
        this.entitiesService = entitiesService;
    }

    @Override
    public void evictCachesFor(Structure structure) {
        gqlProviderService.evictCachesFor(structure);
        entitiesService.evictCachesFor(structure);
    }
}
