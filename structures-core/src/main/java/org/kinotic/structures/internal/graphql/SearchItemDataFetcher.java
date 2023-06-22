package org.kinotic.structures.internal.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.endpoints.RoutingContextToEntityContextAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
@SuppressWarnings("rawtypes")
public class SearchItemDataFetcher implements DataFetcher<CompletableFuture<Page<Map>>> {

    private final String structureId;
    private final EntitiesService entitiesService;
    private final ObjectMapper objectMapper;

    public SearchItemDataFetcher(String structureId,
                                 EntitiesService entitiesService,
                                 ObjectMapper objectMapper) {
        this.structureId = structureId;
        this.entitiesService = entitiesService;
        this.objectMapper = objectMapper;
    }

    @Override
    public CompletableFuture<Page<Map>> get(DataFetchingEnvironment environment) throws Exception {
        Pageable pageable = objectMapper.convertValue(environment.getArgument("pageable"), Pageable.class);
        String searchText = environment.getArgument("searchText");
        return entitiesService.search(structureId,
                                      searchText,
                                      pageable,
                                      Map.class,
                                      new RoutingContextToEntityContextAdapter(environment.getContext()));
    }
}
