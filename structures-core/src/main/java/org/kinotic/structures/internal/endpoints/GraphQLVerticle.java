package org.kinotic.structures.internal.endpoints;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.kinotic.continuum.core.api.security.SecurityService;
import org.kinotic.continuum.gateway.api.security.AuthenticationHandler;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.internal.api.services.GraphQLProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
@Component
public class GraphQLVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(GraphQLVerticle.class);

    private final StructuresProperties properties;
    private final GraphQLProviderService graphQLProviderService;
    private final SecurityService securityService;

    private HttpServer server;

    public GraphQLVerticle(StructuresProperties properties,
                           GraphQLProviderService graphQLProviderService,
                           @Autowired(required = false) SecurityService securityService) {
        this.properties = properties;
        this.graphQLProviderService = graphQLProviderService;
        this.securityService = securityService;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route().handler(CorsHandler.create(properties.getCorsAllowedOriginPattern())
                                          .allowedHeaders(Set.of("Accept", "Authorization", "Content-Type")));

        if(securityService !=null){
            router.route().handler(new AuthenticationHandler(securityService, vertx));
        }

        router.post(properties.getGraphqlPath()+":structureNamespace")
              .consumes("application/json")
              .produces("application/json")
              .handler(BodyHandler.create(false))
              .handler(new GraphQLHandler(graphQLProviderService, "structureNamespace"));

        // Begin listening for requests
        server.requestHandler(router).listen(properties.getGraphqlPort(), ar -> {
            if (ar.succeeded()) {
                log.debug("GraphQL listening on port " + properties.getGraphqlPort());
                log.debug("GraphQL available at http://localhost:" + properties.getGraphqlPort() + properties.getGraphqlPath()+"[STRUCTURE NAMESPACE]/");
                startPromise.complete();
            } else {
                startPromise.fail(ar.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        server.close(stopPromise);
    }
}
