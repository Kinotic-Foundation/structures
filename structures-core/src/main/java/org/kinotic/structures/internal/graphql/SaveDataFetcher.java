package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.EntitiesService;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/18/23.
 */
public class SaveDataFetcher implements DataFetcher<CompletableFuture<RawJson>> {

    private final String structureId;
    private final EntitiesService entitiesService;

    public SaveDataFetcher(String structureId, EntitiesService entitiesService) {
        this.structureId = structureId;
        this.entitiesService = entitiesService;
    }

    @Override
    public CompletableFuture<RawJson> get(DataFetchingEnvironment environment) throws Exception {
        RawJson entity = environment.getArgument("input");
        return entitiesService.save(structureId, entity);
    }
}
