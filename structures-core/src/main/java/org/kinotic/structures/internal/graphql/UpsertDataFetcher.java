package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.domain.TypeCheckMap;
import org.kinotic.structures.internal.api.services.ItemServiceInternal;

import java.util.LinkedHashMap;

/**
 * Created by Navíd Mitchell 🤪 on 4/18/23.
 */
public class UpsertDataFetcher implements DataFetcher<LinkedHashMap<String, Object>>{

    private final String structureId;
    private final ItemServiceInternal itemService;

    public UpsertDataFetcher(String structureId, ItemServiceInternal itemService) {
        this.structureId = structureId;
        this.itemService = itemService;
    }

    @Override
    public LinkedHashMap<String, Object> get(DataFetchingEnvironment environment) throws Exception {
        LinkedHashMap<String, Object> item = environment.getArgument("input");
        return itemService.upsertItem(structureId, new TypeCheckMap(item), null);
    }
}
