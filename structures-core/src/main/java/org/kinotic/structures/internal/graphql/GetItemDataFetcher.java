package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;

import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
public class GetItemDataFetcher implements DataFetcher<CompletableFuture<LinkedHashMap<String, Object>>> {

        private final String structureId;
        private final EntitiesService entitiesService;

        public GetItemDataFetcher(String structureId, EntitiesService entitiesService) {
            this.structureId = structureId;
            this.entitiesService = entitiesService;
        }

        @Override
        public CompletableFuture<LinkedHashMap<String, Object>> get(DataFetchingEnvironment environment) throws Exception {
            String id = environment.getArgument("id");
           // return itemService.getItemById(structureId, id, null).orElseThrow();
            return null;
        }
}

