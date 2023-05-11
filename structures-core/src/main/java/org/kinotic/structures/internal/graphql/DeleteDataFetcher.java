package org.kinotic.structures.internal.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.kinotic.structures.api.services.EntitiesService;

/**
 * Created by Navíd Mitchell 🤪 on 4/18/23.
 */
public class DeleteDataFetcher implements DataFetcher<Boolean> {

    private final String structureId;
    private final EntitiesService entitiesService;

    public DeleteDataFetcher(String structureId, EntitiesService entitiesService) {
        this.structureId = structureId;
        this.entitiesService = entitiesService;
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        String id = environment.getArgument("id");
        entitiesService.deleteById(structureId, id);
        return true;
    }
}
