package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.elasticsearch.search.SearchHits;
import org.kinotic.structures.internal.api.services.ItemServiceInternal;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
public class GetAllItemsDataFetcher implements DataFetcher<ItemListResponse> {

    private final String structureId;
    private final ItemServiceInternal itemService;

    public GetAllItemsDataFetcher(String structureId, ItemServiceInternal itemService) {
        this.structureId = structureId;
        this.itemService = itemService;
    }

    @Override
    public ItemListResponse get(DataFetchingEnvironment environment) throws Exception {
        Integer offset = environment.getArgument("offset");
        Integer limit = environment.getArgument("limit");
        SearchHits searchHits = itemService.getAll(structureId, limit, offset, null);
        return new ItemListResponse(searchHits);
    }
}
