package org.kinotic.structures.internal.endpoints;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.api.config.StructuresProperties;

/**
 * Created by Navíd Mitchell 🤪on 6/8/23.
 */
@RequiredArgsConstructor
public class WebServerNextVerticle extends AbstractVerticle{

    private final HealthChecks healthChecks;
    private final StructuresProperties properties;
    private HttpServer server;

    @Override
    public void start(Promise<Void> startPromise) {
        server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        //TODO: Add a failure handler that can handle broken pipe without throwing an exception
        // the handler below cannot write a response since the connection is already closed
//        router.route().failureHandler(VertxWebUtil.createExceptionConvertingFailureHandler());

        CorsHandler corsHandler = CorsHandler.create(properties.getCorsAllowedOriginPattern())
                                             .allowedHeaders(properties.getCorsAllowedHeaders());
        if(properties.getCorsAllowCredentials() != null){
            corsHandler.allowCredentials(properties.getCorsAllowCredentials());
        }

        Route route =  router.route().handler(corsHandler);

        HealthCheckHandler healthCheckHandler = HealthCheckHandler.createWithHealthChecks(healthChecks);
        router.get("/health").handler(healthCheckHandler);

        if(properties.isEnableStaticFileServer()) {
            route.handler(StaticHandler.create("webroot2"));
        }

        // Begin listening for requests
        server.requestHandler(router)
              .listen(properties.getWebServerPort() + 1, ar -> {
                  if (ar.succeeded()) {
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
