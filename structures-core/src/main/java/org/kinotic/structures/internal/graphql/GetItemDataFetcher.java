package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.endpoints.RoutingContextToEntityContextAdapter;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
@SuppressWarnings("rawtypes")
public class GetItemDataFetcher implements DataFetcher<CompletableFuture<Map>> {

        private final String structureId;
        private final EntitiesService entitiesService;

        public GetItemDataFetcher(String structureId, EntitiesService entitiesService) {
            this.structureId = structureId;
            this.entitiesService = entitiesService;
        }

        @Override
        public CompletableFuture<Map> get(DataFetchingEnvironment environment) throws Exception {
            String id = environment.getArgument("id");
            return entitiesService.findById(structureId,
                                            id,
                                            Map.class,
                                            new RoutingContextToEntityContextAdapter(environment.getContext()));
        }
}

