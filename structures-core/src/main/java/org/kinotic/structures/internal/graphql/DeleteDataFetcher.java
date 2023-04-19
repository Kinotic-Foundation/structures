package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.internal.api.services.ItemServiceInternal;

/**
 * Created by Navíd Mitchell 🤪 on 4/18/23.
 */
public class DeleteDataFetcher implements DataFetcher<Boolean> {

    private final String structureId;
    private final ItemServiceInternal itemService;

    public DeleteDataFetcher(String structureId, ItemServiceInternal itemService) {
        this.structureId = structureId;
        this.itemService = itemService;
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        String id = environment.getArgument("id");
        itemService.delete(structureId, id, null);
        return true;
    }
}
