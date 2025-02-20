package org.kinotic.structures.internal.endpoints.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.swagger.v3.core.util.ObjectMapperFactory;
import io.swagger.v3.oas.models.OpenAPI;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import lombok.RequiredArgsConstructor;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.api.security.SecurityService;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.gateway.api.security.AuthenticationHandler;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.FastestType;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.sql.MapParameterHolder;
import org.kinotic.structures.internal.api.services.sql.ParameterHolder;
import org.kinotic.structures.internal.utils.VertxWebUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by Navíd Mitchell 🤪 on 5/6/24.
 */
@RequiredArgsConstructor
@Component
public class OpenApiVertxRouterFactory {

    private final EntitiesService entitiesService;
    private final ObjectMapper objectMapper;
    private final OpenApiService openApiService;
    private final StructuresProperties properties;
    private final SecurityService securityService;
    private final Vertx vertx;

    private static ObjectMapper openApiMapper;

    @WithSpan
    public Router createRouter() {
        String apiBasePath = properties.getOpenApiPath();
        Router router = Router.router(vertx);

        router.route().failureHandler(VertxWebUtil.createExceptionConvertingFailureHandler());

        CorsHandler corsHandler = CorsHandler.create(properties.getCorsAllowedOriginPattern())
                                             .allowedHeaders(properties.getCorsAllowedHeaders());
        if(properties.getCorsAllowCredentials() != null){
            corsHandler.allowCredentials(properties.getCorsAllowCredentials());
        }

        router.route().handler(corsHandler);

        BodyHandler bodyHandler = BodyHandler.create(false);
        bodyHandler.setBodyLimit(properties.getMaxHttpBodySize());

        // Open API Docs
        router.get("/api-docs/:structureNamespace/openapi.json")
              .produces("application/json")
              .handler(ctx -> {

                  String structureNamespace = ctx.pathParam("structureNamespace");
                  Validate.notNull(structureNamespace, "structureNamespace must not be null");

                  VertxCompletableFuture.from(vertx, openApiService.getOpenApiSpec(structureNamespace))
                                        .thenApply((Function<OpenAPI, Void>) openAPI -> {
                                            try {
                                                if(openApiMapper == null){
                                                    openApiMapper = ObjectMapperFactory.createJson();
                                                }

                                                byte[] bytes = openApiMapper.writeValueAsBytes(openAPI);
                                                ctx.response().putHeader("Content-Type", "application/json");
                                                ctx.response().end(Buffer.buffer(bytes));

                                            } catch (JsonProcessingException e) {
                                                VertxWebUtil.writeException(ctx, e);
                                            }
                                            return null;
                                        });
              });

        if (securityService != null) {
            router.route().handler(new AuthenticationHandler(securityService, vertx));
        }

        // Delete Entity By ID
        router.delete(apiBasePath + ":structureNamespace/:structureName/:id")
              .handler(ctx -> {

                  String id = ctx.pathParam("id");
                  Validate.notNull(id, "id must not be null");

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.deleteById(structureId,
                                                                                id,
                                                                                new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new NoValueHandler(ctx))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });
              });

        // Get all entities
        router.get(apiBasePath + ":structureNamespace/:structureName")
              .produces("application/json")
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  Pageable pageable = VertxWebUtil.getPageableOrDefaultOffsetPageable(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.findAll(structureId,
                                                                             pageable,
                                                                             FastestType.class,
                                                                             new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new ValueToJsonHandler<>(ctx, objectMapper))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });
              });

        // Get Entity By ID
        router.get(apiBasePath + ":structureNamespace/:structureName/:id")
              .produces("application/json")
              .handler(ctx -> {

                  String id = ctx.pathParam("id");
                  Validate.notNull(id, "id must not be null");

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.findById(structureId,
                                                                              id,
                                                                              FastestType.class,
                                                                              new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new ValueToJsonHandler<>(ctx, objectMapper, true))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });
              });


        // Get Total Count for entity
        router.get(apiBasePath + ":structureNamespace/:structureName/count/all")
              .produces("application/json")
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.count(structureId,
                                                                           new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new CountHandler(ctx))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });
              });

        // Get Count for query against entity
        router.post(apiBasePath + ":structureNamespace/:structureName/count/by-query")
              .consumes("text/plain")
              .produces("application/json")
              .handler(bodyHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);
                  String query = ctx.body().asString();

                  VertxCompletableFuture.from(vertx, entitiesService.countByQuery(structureId,
                                                                                  query,
                                                                                  new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new CountHandler(ctx))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });
              });

        // Delete Entity By Query
        router.post(apiBasePath + ":structureNamespace/:structureName/delete/by-query")
              .consumes("text/plain")
              .handler(bodyHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);
                  String query = ctx.body().asString();

                  VertxCompletableFuture.from(vertx, entitiesService.deleteByQuery(structureId,
                                                                                   query,
                                                                                   new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new NoValueHandler(ctx))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });
              });

        // Get Entity By IDs
        router.post(apiBasePath + ":structureNamespace/:structureName/find/by-ids")
              .consumes("application/json")
              .produces("application/json")
              .handler(bodyHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  try {
                      TypeFactory typeFactory = this.objectMapper.getTypeFactory();
                      JavaType listType = typeFactory.constructCollectionType(List.class, String.class);
                      List<String> ids = this.objectMapper.readValue(ctx.body().buffer().getBytes(), listType);

                      VertxCompletableFuture.from(vertx, entitiesService.findByIds(structureId,
                                                                                   ids,
                                                                                   FastestType.class,
                                                                                   new RoutingContextToEntityContextAdapter(ctx)))
                                            .handle(new ValueToJsonHandler<>(ctx, objectMapper))
                                            .exceptionally(throwable -> {
                                                VertxWebUtil.writeException(ctx, throwable);
                                                return null;
                                            });
                  } catch (IOException e) {
                      VertxWebUtil.writeException(ctx, e);
                  }
              });

        // Named Query
        router.post(apiBasePath + ":structureNamespace/:structureName/named-query/:queryName")
              .produces("application/json")
              .handler(bodyHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);
                  String queryName = VertxWebUtil.validateAndReturnPathParam("queryName", ctx);

                  try {
                      ParameterHolder parameterHolder = null;
                      if(!ctx.body().isEmpty()){
                          Map<String, Object> paramMap = this.objectMapper.readValue(ctx.body().buffer().getBytes(), new TypeReference<>() {});
                          parameterHolder = new MapParameterHolder(paramMap);
                      }

                      VertxCompletableFuture.from(vertx, entitiesService.namedQuery(structureId,
                                                                                    queryName,
                                                                                    parameterHolder,
                                                                                    RawJson.class,
                                                                                    new RoutingContextToEntityContextAdapter(ctx)))
                                            .handle(new ValueToJsonHandler<>(ctx, objectMapper))
                                            .exceptionally(throwable -> {
                                                VertxWebUtil.writeException(ctx, throwable);
                                                return null;
                                            });
                  } catch (IOException e) {
                      VertxWebUtil.writeException(ctx, e);
                  }
              });

        // Named Query Page
        router.post(apiBasePath + ":structureNamespace/:structureName/named-query-page/:queryName")
              .produces("application/json")
              .handler(bodyHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);
                  String queryName = VertxWebUtil.validateAndReturnPathParam("queryName", ctx);
                  Pageable pageable = VertxWebUtil.getPageableOrDefaultCursorPageable(ctx);

                  try {
                      ParameterHolder parameterHolder = null;
                      if(!ctx.body().isEmpty()){
                          Map<String, Object> paramMap = this.objectMapper.readValue(ctx.body().buffer().getBytes(), new TypeReference<>() {});
                          parameterHolder = new MapParameterHolder(paramMap);
                      }

                      VertxCompletableFuture.from(vertx, entitiesService.namedQueryPage(structureId,
                                                                                        queryName,
                                                                                        parameterHolder,
                                                                                        pageable,
                                                                                        RawJson.class,
                                                                                        new RoutingContextToEntityContextAdapter(ctx)))
                                            .handle(new ValueToJsonHandler<>(ctx, objectMapper))
                                            .exceptionally(throwable -> {
                                                VertxWebUtil.writeException(ctx, throwable);
                                                return null;
                                            });
                  } catch (IOException e) {
                      VertxWebUtil.writeException(ctx, e);
                  }
              });

        // Search for entities
        router.post(apiBasePath + ":structureNamespace/:structureName/search")
              .consumes("text/plain")
              .produces("application/json")
              .handler(bodyHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  Pageable pageable = VertxWebUtil.getPageableOrDefaultOffsetPageable(ctx);

                  String searchString = ctx.body().asString();

                  Validate.notBlank(searchString, "A request body containing a search string must be provided");

                  VertxCompletableFuture.from(vertx, entitiesService.search(structureId,
                                                                            searchString,
                                                                            pageable,
                                                                            FastestType.class,
                                                                            new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new ValueToJsonHandler<>(ctx, objectMapper))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });
              });

        // Bulk save
        router.post(apiBasePath + ":structureNamespace/:structureName/bulk")
              .consumes("application/json")
              .produces("application/json")
              .handler(bodyHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.bulkSave(structureId,
                                                                              new RawJson(ctx.body().buffer().getBytes()),
                                                                              new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new NoValueHandler(ctx))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });

              });

        // Bulk Update
        router.post(apiBasePath + ":structureNamespace/:structureName/bulk-update")
              .consumes("application/json")
              .produces("application/json")
              .handler(bodyHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.bulkUpdate(structureId,
                                                                                new RawJson(ctx.body().buffer().getBytes()),
                                                                                new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new NoValueHandler(ctx))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });

              });

        // Update entity
        router.post(apiBasePath + ":structureNamespace/:structureName/update")
              .consumes("application/json")
              .produces("application/json")
              .handler(bodyHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.update(structureId,
                                                                            new RawJson(ctx.body().buffer().getBytes()),
                                                                            new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new ValueToJsonHandler<>(ctx, objectMapper))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });

              });

        // Save entity
        router.post(apiBasePath + ":structureNamespace/:structureName")
              .consumes("application/json")
              .produces("application/json")
              .handler(bodyHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.save(structureId,
                                                                          new RawJson(ctx.body().buffer().getBytes()),
                                                                          new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new ValueToJsonHandler<>(ctx, objectMapper))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });

              });

        // Sync Structure
        router.get(apiBasePath + ":structureNamespace/:structureName/util/sync")
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.syncIndex(structureId,
                                                                               new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new NoValueHandler(ctx))
                                        .exceptionally(throwable -> {
                                            VertxWebUtil.writeException(ctx, throwable);
                                            return null;
                                        });
              });

        return router;
    }

}
