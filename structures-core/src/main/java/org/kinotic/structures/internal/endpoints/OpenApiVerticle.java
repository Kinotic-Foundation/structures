package org.kinotic.structures.internal.endpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import lombok.RequiredArgsConstructor;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.api.security.SecurityService;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.gateway.api.security.AuthenticationHandler;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.OpenApiService;
import org.kinotic.structures.internal.utils.VertxWebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * We have one OpenApi spec for all {@link Structure}'s in a namespace. But this handles all namespaces and all structures.
 * Created by Navíd Mitchell 🤪 on 5/29/23.
 */
@RequiredArgsConstructor
public class OpenApiVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(OpenApiVerticle.class);

    private final StructuresProperties properties;
    private final Router router;
    private HttpServer server;

    public void start(Promise<Void> startPromise) {
        server = vertx.createHttpServer();
        // Begin listening for requests
        server.requestHandler(router)
              .listen(properties.getOpenApiPort(), ar -> {
            if (ar.succeeded()) {
                log.info("OpenApi Started Listener on Thread "+Thread.currentThread().getName());
                startPromise.complete();
            } else {
                startPromise.fail(ar.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        server.close(stopPromise);
    }

}
