package org.kinotic.structures.internal.endpoints.graphql.datafetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.vertx.ext.web.RoutingContext;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.endpoints.openapi.RoutingContextToEntityContextAdapter;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
@SuppressWarnings("rawtypes")
public class FindByIdDataFetcher implements DataFetcher<CompletableFuture<Object>> {

        private final String structureId;
        private final EntitiesService entitiesService;

        public FindByIdDataFetcher(String structureId, EntitiesService entitiesService) {
            this.structureId = structureId;
            this.entitiesService = entitiesService;
        }

        @Override
        public CompletableFuture<Object> get(DataFetchingEnvironment environment) throws Exception {
            RoutingContext rc = environment.getGraphQlContext().get(RoutingContext.class);
            Objects.requireNonNull(rc);
            EntityContext ec = new RoutingContextToEntityContextAdapter(rc);

            String id = environment.getArgument("id");

            return entitiesService.findById(structureId,
                                            id,
                                            Map.class,
                                            ec);
        }
}

