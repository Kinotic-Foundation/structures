package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;

import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/18/23.
 */
public class UpsertDataFetcher implements DataFetcher<CompletableFuture<LinkedHashMap<String, Object>>> {

    private final String structureId;
    private final EntitiesService entitiesService;

    public UpsertDataFetcher(String structureId, EntitiesService entitiesService) {
        this.structureId = structureId;
        this.entitiesService = entitiesService;
    }

    @Override
    public CompletableFuture<LinkedHashMap<String, Object>> get(DataFetchingEnvironment environment) throws Exception {
        LinkedHashMap<String, Object> item = environment.getArgument("input");
       // return itemService.upsertItem(structureId, new TypeCheckMap(item), null);
        return null;
    }
}
