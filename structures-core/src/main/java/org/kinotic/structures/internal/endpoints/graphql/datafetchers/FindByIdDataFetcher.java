package org.kinotic.structures.internal.endpoints.graphql.datafetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
@SuppressWarnings("rawtypes")
public class FindByIdDataFetcher implements DataFetcher<CompletableFuture<Map>> {

        private final String structureId;
        private final EntitiesService entitiesService;

        public FindByIdDataFetcher(String structureId, EntitiesService entitiesService) {
            this.structureId = structureId;
            this.entitiesService = entitiesService;
        }

        @Override
        public CompletableFuture<Map> get(DataFetchingEnvironment environment) throws Exception {
            String id = environment.getArgument("id");
            return entitiesService.findById(structureId,
                                            id,
                                            Map.class,
                                            environment.getLocalContext());
        }
}

