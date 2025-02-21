package org.kinotic.structures.internal.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.CacheEvictionService;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.kinotic.structures.internal.endpoints.graphql.DelegatingGqlHandler;
import org.kinotic.structures.internal.endpoints.graphql.GqlOperationDefinitionService;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.springframework.stereotype.Component;

/**
 * Created By Navíd Mitchell 🤪on 2/12/25
 */
@Component
@RequiredArgsConstructor
public class DefaultCacheEvictionService implements CacheEvictionService {

    private final DelegatingGqlHandler delegatingGqlHandler;
    private final EntitiesService entitiesService;
    private final GqlOperationDefinitionService gqlOperationDefinitionService;
    private final StructureDAO structureDAO;

    @Override
    public void evictCachesFor(Structure structure) {
        Validate.notNull(structure, "structure must not be null");
        entitiesService.evictCachesFor(structure);
        gqlOperationDefinitionService.evictCachesFor(structure);
        delegatingGqlHandler.evictCachesFor(structure);
    }

    @Override
    public void evictCachesFor(NamedQueriesDefinition namedQueriesDefinition) {
        String structureId = StructuresUtil.structureNameToId(namedQueriesDefinition.getNamespace(), namedQueriesDefinition.getStructure());
        structureDAO.findById(structureId)
                    .thenAccept(structure -> {
                        gqlOperationDefinitionService.evictCachesFor(structure);
                        delegatingGqlHandler.evictCachesFor(structure);
                    }).join();
    }
}
