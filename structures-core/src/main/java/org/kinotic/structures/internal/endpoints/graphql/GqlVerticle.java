package org.kinotic.structures.internal.endpoints.graphql;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.api.security.SecurityService;
import org.kinotic.continuum.gateway.api.security.AuthenticationHandler;
import org.kinotic.structures.api.config.StructuresProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
@RequiredArgsConstructor
public class GqlVerticle extends AbstractVerticle {

    public static final String NAMESPACE_PATH_PARAMETER = "structureNamespace";

    private static final Logger log = LoggerFactory.getLogger(GqlVerticle.class);
    private final GqlOperationService gqlOperationService;
    private final StructuresProperties properties;
    private final SecurityService securityService;
    private HttpServer server;


    @Override
    public void start(Promise<Void> startPromise) {
        server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route().handler(CorsHandler.create(properties.getCorsAllowedOriginPattern())
                                          .allowedHeaders(Set.of("Accept", "Authorization", "Content-Type")));

        if(securityService !=null){
            router.route().handler(new AuthenticationHandler(securityService, vertx));
        }

        router.post(properties.getGraphqlPath()+":"+NAMESPACE_PATH_PARAMETER)
              .consumes("application/json")
              .consumes("application/graphql")
              .produces("application/json")
              .handler(BodyHandler.create(false))
              .handler(new GqlHandler(gqlOperationService));

        // Begin listening for requests
        server.requestHandler(router).listen(properties.getGraphqlPort(), ar -> {
            if (ar.succeeded()) {
                log.info("GraphQL Started Listener on Thread "+Thread.currentThread().getName());
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