package org.kinotic.structures.internal.endpoints.graphql.datafetchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.utils.GqlUtils;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
@SuppressWarnings("rawtypes")
public class SearchDataFetcher implements DataFetcher<CompletableFuture<Page<Map>>> {

    private final String structureId;
    private final EntitiesService entitiesService;
    private final ObjectMapper objectMapper;

    public SearchDataFetcher(String structureId,
                             EntitiesService entitiesService,
                             ObjectMapper objectMapper) {
        this.structureId = structureId;
        this.entitiesService = entitiesService;
        this.objectMapper = objectMapper;
    }

    @Override
    public CompletableFuture<Page<Map>> get(DataFetchingEnvironment environment) throws Exception {
        Pageable pageable =  GqlUtils.parseVariable(environment.getArguments(),
                                                    "pageable",
                                                    Pageable.class,
                                                    objectMapper);
        String searchText = environment.getArgument("searchText");
        return entitiesService.search(structureId,
                                      searchText,
                                      pageable,
                                      Map.class,
                                      environment.getLocalContext());
    }
}
