package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.endpoints.RoutingContextToEntityContextAdapter;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/18/23.
 */
public class DeleteDataFetcher implements DataFetcher<CompletableFuture<String>> {

    private final String structureId;
    private final EntitiesService entitiesService;

    public DeleteDataFetcher(String structureId, EntitiesService entitiesService) {
        this.structureId = structureId;
        this.entitiesService = entitiesService;
    }

    @Override
    public CompletableFuture<String> get(DataFetchingEnvironment environment) throws Exception {
        String id = environment.getArgument("id");
        return entitiesService.deleteById(structureId,
                                          id,
                                          new RoutingContextToEntityContextAdapter(environment.getContext()))
                              .thenCompose(aVoid -> CompletableFuture.completedFuture(id));
    }
}
