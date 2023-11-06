package org.kinotic.structures.internal.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.endpoints.RoutingContextToEntityContextAdapter;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
@SuppressWarnings("rawtypes")
public class GetAllItemsDataFetcher implements DataFetcher<CompletableFuture<Page<Map>>> {

    private final String structureId;
    private final EntitiesService entitiesService;
    private final ObjectMapper objectMapper;

    public GetAllItemsDataFetcher(String structureId,
                                  EntitiesService entitiesService,
                                  ObjectMapper objectMapper) {
        this.structureId = structureId;
        this.entitiesService = entitiesService;
        this.objectMapper = objectMapper;
    }

    @Override
    public CompletableFuture<Page<Map>> get(DataFetchingEnvironment environment) throws Exception {
        Pageable pageable = objectMapper.convertValue(environment.getArgument("pageable"), Pageable.class);
        return entitiesService.findAll(structureId,
                                       pageable,
                                       Map.class,
                                       new RoutingContextToEntityContextAdapter(environment.getContext()));
    }
}
