package org.kinotic.structures.internal.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.internal.api.services.CacheEvictionService;
import org.kinotic.structures.internal.endpoints.graphql.DelegatingGqlHandler;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
@Component
@RequiredArgsConstructor
public class DefaultCacheEvictionService implements CacheEvictionService {

    private final EntitiesService entitiesService;
    private final DelegatingGqlHandler delegatingGqlHandler;
    private final NamedQueriesService namedQueriesService;

    @Override
    public void evictCachesFor(Structure structure) {
        delegatingGqlHandler.evictCachesFor(structure);
        entitiesService.evictCachesFor(structure);
    }

    @Override
    public void evictCachesFor(NamedQueriesDefinition namedQueriesDefinition) {
        namedQueriesService.evictCachesFor(namedQueriesDefinition);
    }
}
