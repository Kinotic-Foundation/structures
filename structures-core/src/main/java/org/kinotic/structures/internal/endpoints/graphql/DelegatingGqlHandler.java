package org.kinotic.structures.internal.endpoints.graphql;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.kinotic.structures.api.domain.Structure;

/**
 * Delegates to the correct Vertx {@link io.vertx.ext.web.handler.graphql.GraphQLHandler} based on the path of the route
 * Created by Navíd Mitchell 🤪 on 11/19/24.
 */
public interface DelegatingGqlHandler extends Handler<RoutingContext> {

    /**
     * Evicts the cache for a given {@link Structure}
     * @param structure to evict the cache for
     */
    void evictCachesFor(Structure structure);


}
