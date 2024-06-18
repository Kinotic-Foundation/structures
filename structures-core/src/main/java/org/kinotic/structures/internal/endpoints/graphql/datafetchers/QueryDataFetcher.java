package org.kinotic.structures.internal.endpoints.graphql.datafetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.sql.MapParameterHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 6/17/24.
 */
@SuppressWarnings("rawtypes")
@RequiredArgsConstructor
public class QueryDataFetcher implements DataFetcher<CompletableFuture<List<Map>>> {

    private final EntitiesService entitiesService;
    private final String queryName;
    private final String structureId;

    @Override
    public CompletableFuture<List<Map>> get(DataFetchingEnvironment environment) throws Exception {
        return entitiesService.namedQuery(structureId,
                                          queryName,
                                          new MapParameterHolder(environment.getArguments()),
                                          Map.class,
                                          environment.getLocalContext());
    }
}
