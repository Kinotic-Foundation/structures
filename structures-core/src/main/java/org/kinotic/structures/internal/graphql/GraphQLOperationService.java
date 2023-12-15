package org.kinotic.structures.internal.graphql;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.graphql.impl.GraphQLQuery;

import java.util.concurrent.CompletableFuture;

/**
 * Service provides the functionality to process a GraphQL request and return the result.
 * Created by Navíd Mitchell 🤪 on 12/13/23.
 */
public interface GraphQLOperationService {

    /**
     * Executes the given query and returns the result
     * @param routingContext the {@link RoutingContext} for the request
     * @param query the {@link GraphQLQuery} to execute
     * @return a {@link CompletableFuture} that will be completed with the result of the query
     */
    CompletableFuture<Buffer> execute(RoutingContext routingContext, GraphQLQuery query);

}
