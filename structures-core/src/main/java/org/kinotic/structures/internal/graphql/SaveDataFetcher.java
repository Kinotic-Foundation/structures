package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.endpoints.RoutingContextToEntityContextAdapter;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/18/23.
 */
@SuppressWarnings("rawtypes")
public class SaveDataFetcher implements DataFetcher<CompletableFuture<Map>> {

    private final String structureId;
    private final EntitiesService entitiesService;

    public SaveDataFetcher(String structureId, EntitiesService entitiesService) {
        this.structureId = structureId;
        this.entitiesService = entitiesService;
    }

    @Override
    public CompletableFuture<Map> get(DataFetchingEnvironment environment) throws Exception {
        Map entity = environment.getArgument("input");
        return entitiesService.save(structureId,
                                    entity,
                                    new RoutingContextToEntityContextAdapter(environment.getContext()));
    }
}
