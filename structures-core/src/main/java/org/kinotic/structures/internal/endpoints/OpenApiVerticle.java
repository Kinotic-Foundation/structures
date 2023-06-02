package org.kinotic.structures.internal.endpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.commons.lang3.Validate;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.OpenApiService;
import org.kinotic.structures.internal.util.VertxWebUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

/**
 * We have one OpenApi spec for all {@link Structure}'s in a namespace. But this handles all namespaces and all structures.
 * Created by Navíd Mitchell 🤪 on 5/29/23.
 */
@Component
public class OpenApiVerticle extends AbstractVerticle {

    private final StructuresProperties properties;
    private final EntitiesService entitiesService;
    private final ObjectMapper objectMapper;
    private final OpenApiService openApiService;
    private final String apiBasePath;
    private final Handler<RoutingContext> failureHandler = VertxWebUtils.createExceptionConvertingFailureHandler();

    private HttpServer server;

    public OpenApiVerticle(StructuresProperties properties,
                           EntitiesService entitiesService,
                           ObjectMapper objectMapper,
                           OpenApiService openApiService) {
        this.properties = properties;
        this.entitiesService = entitiesService;
        this.objectMapper = objectMapper;
        this.openApiService = openApiService;
        apiBasePath = properties.getOpenApiPath();
    }

    public void start(Promise<Void> startPromise) {

        server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        // Get Entity By ID
        router.get(apiBasePath+":structureNamespace/:structureName/:id")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(ctx -> {

                  String id = ctx.pathParam("id");
                  Validate.notNull(id, "id must not be null");

                  String structureId = VertxWebUtils.validateAndReturnStructureId(ctx);

                  entitiesService.findById(structureId, id)
                                 .handle(new SingleEntityHandler(ctx));
              });

        // Delete Entity By ID
        router.delete(apiBasePath+":structureNamespace/:structureName/:id")
              .failureHandler(failureHandler)
              .handler(ctx -> {

                  String id = ctx.pathParam("id");
                  Validate.notNull(id, "id must not be null");

                  String structureId = VertxWebUtils.validateAndReturnStructureId(ctx);

                  entitiesService.deleteById(structureId, id)
                                 .handle((BiFunction<Void, Throwable, Void>) (v, throwable) -> {
                                     if (throwable == null) {
                                         ctx.response().end();
                                     } else {
                                         VertxWebUtils.writeException(ctx.response(), throwable);
                                     }
                                     return null;
                                 });
              });

        // Save entity
        router.post(apiBasePath+":structureNamespace/:structureName")
              .consumes("application/json")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(BodyHandler.create(false))
              .handler(ctx -> {

                  String structureId = VertxWebUtils.validateAndReturnStructureId(ctx);

                  entitiesService.save(structureId,
                                       new RawJson(ctx.getBody().getBytes()))
                                 .handle(new SingleEntityHandler(ctx));

              });

        // Find all entities
        router.get(apiBasePath+":structureNamespace/:structureName")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtils.validateAndReturnStructureId(ctx);

                  Pageable pageable = VertxWebUtils.validateAndReturnPageable(ctx);

                  entitiesService.findAll(structureId,
                                          pageable)
                                 .handle(new MultiEntityHandler(ctx, objectMapper));
              });

        // Search for entities
        router.get(apiBasePath+":structureNamespace/:structureName/search")
              .consumes("text/plain")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtils.validateAndReturnStructureId(ctx);

                  Pageable pageable = VertxWebUtils.validateAndReturnPageable(ctx);

                  String searchString = ctx.getBody().toString();

                  Validate.notBlank(searchString, "A request body containing a search string must be provided");

                  entitiesService.search(structureId,
                                         searchString,
                                         pageable)
                                 .handle(new MultiEntityHandler(ctx, objectMapper));
              });

        // Open API Docs
        router.get(apiBasePath + "/api-docs/:structureNamespace/openapi.json")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(ctx ->{
                  try {
                      String structureNamespace = ctx.pathParam("structureNamespace");
                      Validate.notNull(structureNamespace, "structureNamespace must not be null");

                      //This wacky stuff is needed since we do not want nulls in our output
                      ObjectMapper mapper = new ObjectMapper();
                      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

                      byte[] bytes = mapper.writeValueAsBytes(openApiService.getOpenApiSpec(structureNamespace));
                      ctx.response().end(Buffer.buffer(bytes));

                  } catch (JsonProcessingException e) {
                      throw new IllegalArgumentException(e);
                  }
              });

        // Begin listening for requests
        server.requestHandler(router).listen(properties.getOpenApiPort(), ar -> {
            if (ar.succeeded()) {
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
