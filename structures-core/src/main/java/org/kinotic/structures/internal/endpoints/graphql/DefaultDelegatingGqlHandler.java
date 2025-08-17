package org.kinotic.structures.internal.endpoints.graphql;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.graphql.GraphQLHandler;
import org.kinotic.structures.api.domain.Structure;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by Navíd Mitchell 🤪 on 11/19/24.
 */
@Component
public class DefaultDelegatingGqlHandler implements DelegatingGqlHandler {

    private final AsyncLoadingCache<String, GraphQLHandler> graphQLHandlerCache;

    public DefaultDelegatingGqlHandler(GqlSchemaHandlerCacheLoader gqlSchemaHandlerCacheLoader) {
        graphQLHandlerCache
                = Caffeine.newBuilder()
                          .expireAfterAccess(20, TimeUnit.HOURS)
                          .maximumSize(2000)
                          .buildAsync(gqlSchemaHandlerCacheLoader);
    }

    @Override
    public void evictCachesFor(Structure structure) {
        graphQLHandlerCache.asMap().remove(structure.getApplicationId());
    }

    @Override
    public void handle(RoutingContext rc) {
        String application = rc.pathParam(GqlVerticle.APPLICATION_PATH_PARAMETER);

        Future.fromCompletionStage(graphQLHandlerCache.get(application),
                                   rc.vertx().getOrCreateContext())
              .map(graphQLHandler -> {
                  graphQLHandler.handle(rc);
                  return null;
              });
    }

}
