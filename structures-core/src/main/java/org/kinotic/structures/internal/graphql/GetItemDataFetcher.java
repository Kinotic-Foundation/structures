package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.internal.api.services.ItemServiceInternal;

import java.util.LinkedHashMap;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
public class GetItemDataFetcher implements DataFetcher<LinkedHashMap<String, Object>>{

        private final String structureId;
        private final ItemServiceInternal itemService;

        public GetItemDataFetcher(String structureId, ItemServiceInternal itemService) {
            this.structureId = structureId;
            this.itemService = itemService;
        }

        @Override
        public LinkedHashMap<String, Object> get(DataFetchingEnvironment environment) throws Exception {
            String id = environment.getArgument("id");
            return itemService.getItemById(structureId, id, null).orElseThrow();
        }
}
