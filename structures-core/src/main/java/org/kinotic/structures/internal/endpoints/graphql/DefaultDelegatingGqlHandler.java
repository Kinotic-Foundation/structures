package org.kinotic.structures.internal.endpoints.graphql;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.graphql.GraphQLHandler;
import org.apache.commons.lang3.Validate;
import org.kinotic.structures.api.domain.Structure;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by Navíd Mitchell 🤪 on 11/19/24.
 */
@Component
public class DefaultDelegatingGqlHandler implements DelegatingGqlHandler{

    private final GqlOperationDefinitionService gqlOperationDefinitionService;
    private final AsyncLoadingCache<String, GraphQLHandler> graphQLCache;

    public DefaultDelegatingGqlHandler(GqlOperationDefinitionService gqlOperationDefinitionService,
                                       GqlSchemaHandlerCacheLoader gqlSchemaHandlerCacheLoader) {
        this.gqlOperationDefinitionService = gqlOperationDefinitionService;

        graphQLCache = Caffeine.newBuilder()
                               .expireAfterAccess(20, TimeUnit.HOURS)
                               .maximumSize(2000)
                               .buildAsync(gqlSchemaHandlerCacheLoader);
    }

    @Override
    public void evictCachesFor(Structure structure) {
        Validate.notNull(structure, "structure must not be null");
        graphQLCache.asMap().remove(structure.getNamespace());
        gqlOperationDefinitionService.evictCachesFor(structure);
    }

    @Override
    public void handle(RoutingContext rc) {
        String namespace = rc.pathParam(GqlVerticle.NAMESPACE_PATH_PARAMETER);

        Future.fromCompletionStage(graphQLCache.get(namespace), rc.vertx().getOrCreateContext())
              .map(graphQLHandler -> {
                  graphQLHandler.handle(rc);
                  return null;
              });
    }

}
