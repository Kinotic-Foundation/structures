package org.kinotic.structures.internal.endpoints.graphql;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.graphql.impl.GraphQLQuery;
import org.kinotic.structures.api.domain.Structure;

import java.util.concurrent.CompletableFuture;

/**
 * Service provides the functionality to process a GraphQL request and return the result.
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
public interface GqlExecutionService {

    /**
     * Executes the given query and returns the result
     * @param routingContext the {@link RoutingContext} for the request
     * @param query the {@link GraphQLQuery} to execute
     * @return a {@link CompletableFuture} that will be completed with the result of the query
     */
    CompletableFuture<Buffer> execute(RoutingContext routingContext, GraphQLQuery query);

    /**
     * Evicts the cache for a given structure
     * @param structure to evict the cache for
     */
    void evictCachesFor(Structure structure);

}
