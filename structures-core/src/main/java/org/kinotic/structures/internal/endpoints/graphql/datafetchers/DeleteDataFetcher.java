package org.kinotic.structures.internal.endpoints.graphql.datafetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;

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
                                          environment.getLocalContext())
                              .thenCompose(aVoid -> CompletableFuture.completedFuture(id));
    }
}
