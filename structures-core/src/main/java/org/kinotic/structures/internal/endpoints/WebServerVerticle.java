package org.kinotic.structures.internal.endpoints;

import java.util.Set;

import org.kinotic.structures.api.config.StructuresProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Created by Navíd Mitchell 🤪on 6/8/23.
 */
@Component
public class WebServerVerticle extends AbstractVerticle{

    private static final Logger log = LoggerFactory.getLogger(GraphQLVerticle.class);

    private final StructuresProperties properties;
    private final HealthChecks healthChecks;
    private HttpServer server;


    public WebServerVerticle(StructuresProperties properties,
                             HealthChecks healthChecks) {
        this.properties = properties;
        this.healthChecks = healthChecks;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        HttpServerOptions opts = new HttpServerOptions();
        opts.setMaxWebSocketFrameSize(properties.getMaxWebSocketFrameSize());

        server = vertx.createHttpServer(opts);

        Router router = Router.router(vertx);

        Route route = router.route()
                            .handler(CorsHandler.create(properties.getCorsAllowedOriginPattern())
                                  .allowedHeaders(Set.of("Accept", "Authorization", "Content-Type")));

        if(properties.isEnableStaticFileServer()) {
            route.handler(StaticHandler.create());
        }

        HealthCheckHandler healthCheckHandler = HealthCheckHandler.createWithHealthChecks(healthChecks);
        router.get("/health").handler(healthCheckHandler);

        // Begin listening for requests
        server.requestHandler(router).listen(properties.getWebServerPort(), ar -> {
            if (ar.succeeded()) {
                if(properties.isEnableStaticFileServer()) {
                    log.debug("Web Server listening on port " + properties.getWebServerPort());
                    log.debug("Web Server available at http://localhost:" + properties.getWebServerPort() + "/");
                }
                log.debug("Health checks available at http://localhost:" + properties.getWebServerPort() + properties.getHealthCheckPath());
                log.debug("Using maxWebSocketFrameSize:" + properties.getMaxWebSocketFrameSize());
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
