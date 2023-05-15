package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
public class GetAllItemsDataFetcher implements DataFetcher<CompletableFuture<ItemListResponse>> {

    private final String structureId;
    private final EntitiesService entitiesService;

    public GetAllItemsDataFetcher(String structureId, EntitiesService entitiesService) {
        this.structureId = structureId;
        this.entitiesService = entitiesService;
    }

    @Override
    public CompletableFuture<ItemListResponse> get(DataFetchingEnvironment environment) throws Exception {
        Integer offset = environment.getArgument("offset");
        Integer limit = environment.getArgument("limit");
        //entitiesService.findAll(structureId, offset, limit);
        return null;
    }
}
