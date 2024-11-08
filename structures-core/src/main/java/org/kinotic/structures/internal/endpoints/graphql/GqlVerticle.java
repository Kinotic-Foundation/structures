package org.kinotic.structures.internal.endpoints.graphql;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.api.security.SecurityService;
import org.kinotic.continuum.gateway.api.security.AuthenticationHandler;
import org.kinotic.structures.api.config.StructuresProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
@RequiredArgsConstructor
public class GqlVerticle extends AbstractVerticle {

    public static final String NAMESPACE_PATH_PARAMETER = "structureNamespace";

    private static final Logger log = LoggerFactory.getLogger(GqlVerticle.class);
    private final GqlExecutionService gqlExecutionService;
    private final StructuresProperties properties;
    private final SecurityService securityService;
    private HttpServer server;


    @Override
    public void start(Promise<Void> startPromise) {
        HttpServerOptions options = new HttpServerOptions();
        options.setMaxHeaderSize(properties.getMaxHttpHeaderSize());
        server = vertx.createHttpServer(options);

        Router router = Router.router(vertx);

        CorsHandler corsHandler = CorsHandler.create(properties.getCorsAllowedOriginPattern())
                                             .allowedHeaders(properties.getCorsAllowedHeaders());
        if(properties.getCorsAllowCredentials() != null){
            corsHandler.allowCredentials(properties.getCorsAllowCredentials());
        }

        router.route().handler(corsHandler);

        if(securityService !=null){
            router.route().handler(new AuthenticationHandler(securityService, vertx));
        }

        router.post(properties.getGraphqlPath()+":"+NAMESPACE_PATH_PARAMETER)
              .consumes("application/json")
              .consumes("application/graphql")
              .produces("application/json")
              .handler(BodyHandler.create(false)
                                  .setBodyLimit(properties.getMaxHttpBodySize()))
              .handler(new GqlHandler(gqlExecutionService));

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
