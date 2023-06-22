package org.kinotic.structuresserver.endpoints;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.internal.endpoints.GraphQLVerticle;
import org.kinotic.structuresserver.config.StructuresServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪on 6/8/23.
 */
@Component
public class UiServerVerticle extends AbstractVerticle{

    private static final Logger log = LoggerFactory.getLogger(GraphQLVerticle.class);

    private final StructuresProperties properties;
    private final StructuresServerProperties serverProperties;
    private HttpServer server;

    public UiServerVerticle(StructuresProperties properties,
                            StructuresServerProperties serverProperties) {
        this.properties = properties;
        this.serverProperties = serverProperties;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route()
              .handler(CorsHandler.create(properties.getCorsAllowedOriginPattern())
                                  .allowedHeaders(Set.of("Accept", "Authorization", "Content-Type")))
              .handler(StaticHandler.create());

        // Begin listening for requests
        server.requestHandler(router).listen(serverProperties.getUiServerPort(), ar -> {
            if (ar.succeeded()) {
                log.debug("UI Server listening on port " + serverProperties.getUiServerPort());
                log.debug("UI Server available at http://localhost:" + serverProperties.getUiServerPort() +"/");
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
