package org.kinotic.structures.internal.endpoints.graphql;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.graphql.impl.GraphQLQuery;

import java.util.concurrent.CompletableFuture;

/**
 * Service provides the functionality to process a GraphQL request and return the result.
 * Created by Navíd Mitchell 🤪 on 12/13/23.
 */
public interface GraphQLOperationService {

    CompletableFuture<Buffer> execute(RoutingContext routingContext, GraphQLQuery query);

}
