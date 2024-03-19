package org.kinotic.structures.internal.endpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.api.security.SecurityService;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.gateway.api.security.AuthenticationHandler;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.OpenApiService;
import org.kinotic.structures.internal.graphql.GqlOperationService;
import org.kinotic.structures.internal.utils.VertxWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Creates all needed verticles at runtime so multiple instances can be used
 * Created By navidmitchell 🤯on 3/6/24
 */
@Component
public class StructuresVerticleFactory {

    // Common Deps
    private final StructuresProperties properties;
    private final SecurityService securityService;
    private final Vertx vertx;

    // Gql Deps
    private final GqlOperationService gqlOperationService;

    // Open Api Deps
    private final EntitiesService entitiesService;
    private final ObjectMapper objectMapper;
    private final OpenApiService openApiService;
    private Router router;

    // Web Server Deps
    private final HealthChecks healthChecks;
    private final Handler<RoutingContext> failureHandler = VertxWebUtil.createExceptionConvertingFailureHandler();



    @PostConstruct
    public void init(){
        String apiBasePath = properties.getOpenApiPath();
        router = Router.router(vertx);

        router.route().handler(CorsHandler.create(properties.getCorsAllowedOriginPattern())
                                          .allowedHeaders(Set.of("Accept", "Authorization", "Content-Type")));

        // Open API Docs
        router.get("/api-docs/:structureNamespace/openapi.json")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(ctx ->{

                  String structureNamespace = ctx.pathParam("structureNamespace");
                  Validate.notNull(structureNamespace, "structureNamespace must not be null");

                  VertxCompletableFuture.from(vertx, openApiService.getOpenApiSpec(structureNamespace))
                                        .thenApply((Function<OpenAPI, Void>) openAPI -> {
                                            try {

                                                ObjectMapper mapper = new ObjectMapper();
                                                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                                                byte[] bytes = mapper.writeValueAsBytes(openAPI);
                                                ctx.response().putHeader("Content-Type", "application/json");
                                                ctx.response().end(Buffer.buffer(bytes));

                                            } catch (JsonProcessingException e) {
                                                VertxWebUtil.writeException(ctx.response(), e);
                                            }
                                            return null;
                                        });
              });

        if(securityService != null){
            router.route().handler(new AuthenticationHandler(securityService, vertx));
        }

        // Get Entity By ID
        router.get(apiBasePath+":structureNamespace/:structureName/:id")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(ctx -> {

                  String id = ctx.pathParam("id");
                  Validate.notNull(id, "id must not be null");

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.findById(structureId,
                                                                              id,
                                                                              RawJson.class,
                                                                              new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new SingleEntityHandler(ctx));
              });


        // Get Total Count for entity
        router.get(apiBasePath+":structureNamespace/:structureName/count/all")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.count(structureId,
                                                                           new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle((BiFunction<Long, Throwable, Void>) (v, throwable) -> {
                                            if (throwable == null) {
                                                ctx.response().putHeader("Content-Type", "application/json");
                                                ctx.response().setStatusCode(200);
                                                ctx.response().end(Buffer.buffer("{ \"count\": " + v.toString() + '}'));
                                            } else {
                                                VertxWebUtil.writeException(ctx.response(), throwable);
                                            }
                                            return null;
                                        });
              });


        // Get Count for query against entity
        router.post(apiBasePath+":structureNamespace/:structureName/count/by-query")
              .consumes("text/plain")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(BodyHandler.create(false))
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);
                  String query = ctx.getBody().toString();

                  VertxCompletableFuture.from(vertx, entitiesService.countByQuery(structureId,
                                                                                  query,
                                                                                  new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle((BiFunction<Long, Throwable, Void>) (v, throwable) -> {
                                            if (throwable == null) {
                                                ctx.response().putHeader("Content-Type", "application/json");
                                                ctx.response().setStatusCode(200);
                                                ctx.response().end(Buffer.buffer("{ \"count\": " + v.toString() + '}'));
                                            } else {
                                                VertxWebUtil.writeException(ctx.response(), throwable);
                                            }
                                            return null;
                                        });
              });

        // Delete Entity By ID
        router.delete(apiBasePath+":structureNamespace/:structureName/:id")
              .failureHandler(failureHandler)
              .handler(ctx -> {

                  String id = ctx.pathParam("id");
                  Validate.notNull(id, "id must not be null");

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.deleteById(structureId,
                                                                                id,
                                                                                new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle((BiFunction<Void, Throwable, Void>) (v, throwable) -> {
                                            if (throwable == null) {
                                                ctx.response().end();
                                            } else {
                                                VertxWebUtil.writeException(ctx.response(), throwable);
                                            }
                                            return null;
                                        });
              });

        // Delete Entity By Query
        router.post(apiBasePath+":structureNamespace/:structureName/delete/by-query")
              .consumes("text/plain")
              .failureHandler(failureHandler)
              .handler(BodyHandler.create(false))
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);
                  String query = ctx.getBody().toString();

                  VertxCompletableFuture.from(vertx, entitiesService.deleteByQuery(structureId,
                                                                                   query,
                                                                                   new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle((BiFunction<Void, Throwable, Void>) (v, throwable) -> {
                                            if (throwable == null) {
                                                ctx.response().end();
                                            } else {
                                                VertxWebUtil.writeException(ctx.response(), throwable);
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

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.save(structureId,
                                                                          new RawJson(ctx.getBody().getBytes()),
                                                                          new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new SingleEntityHandler(ctx));

              });

        // Bulk save
        router.post(apiBasePath+":structureNamespace/:structureName/bulk")
              .consumes("application/json")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(BodyHandler.create(false))
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.bulkSave(structureId,
                                                                              new RawJson(ctx.getBody().getBytes()),
                                                                              new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new NoValueHandler(ctx));

              });

        // Update entity
        router.post(apiBasePath+":structureNamespace/:structureName/update")
              .consumes("application/json")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(BodyHandler.create(false))
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.update(structureId,
                                                                            new RawJson(ctx.getBody().getBytes()),
                                                                            new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new SingleEntityHandler(ctx));

              });

        // Bulk Update
        router.post(apiBasePath+":structureNamespace/:structureName/bulk-update")
              .consumes("application/json")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(BodyHandler.create(false))
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.bulkUpdate(structureId,
                                                                                new RawJson(ctx.getBody().getBytes()),
                                                                                new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new NoValueHandler(ctx));

              });

        // Find all entities
        router.get(apiBasePath+":structureNamespace/:structureName")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  Pageable pageable = VertxWebUtil.validateAndReturnPageable(ctx);

                  VertxCompletableFuture.from(vertx, entitiesService.findAll(structureId,
                                                                             pageable,
                                                                             RawJson.class,
                                                                             new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new MultiEntityHandler(ctx, objectMapper));
              });

        // Get Entity By IDs
        router.post(apiBasePath+":structureNamespace/:structureName/find/by-ids")
              .consumes("application/json")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(BodyHandler.create(false))
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  try{
                      VertxCompletableFuture.from(vertx, entitiesService.findByIds(structureId,
                                                                                   objectMapper.readValue(ctx.getBody().getBytes(), new TypeReference<>() {}),
                                                                                   RawJson.class,
                                                                                   new RoutingContextToEntityContextAdapter(ctx)))
                                            .handle((BiFunction<List<RawJson>, Throwable, Void>) (v, throwable) -> {
                                                try {
                                                    if (throwable == null) {
                                                        ctx.response().putHeader("Content-Type", "application/json");
                                                        ctx.response().setStatusCode(200);
                                                        byte[] data = objectMapper.writeValueAsBytes(v);
                                                        ctx.response().end(Buffer.buffer(data));
                                                    } else {
                                                        VertxWebUtil.writeException(ctx.response(), throwable);
                                                    }
                                                }catch (IOException e){
                                                    VertxWebUtil.writeException(ctx.response(), e);
                                                }
                                                return null;
                                            });
                  }catch(IOException e){
                      VertxWebUtil.writeException(ctx.response(), e);
                  }
              });

        // Search for entities
        router.post(apiBasePath+":structureNamespace/:structureName/search")
              .consumes("text/plain")
              .produces("application/json")
              .failureHandler(failureHandler)
              .handler(BodyHandler.create(false))
              .handler(ctx -> {

                  String structureId = VertxWebUtil.validateAndReturnStructureId(ctx);

                  Pageable pageable = VertxWebUtil.validateAndReturnPageable(ctx);

                  String searchString = ctx.getBody().toString();

                  Validate.notBlank(searchString, "A request body containing a search string must be provided");

                  VertxCompletableFuture.from(vertx, entitiesService.search(structureId,
                                                                            searchString,
                                                                            pageable,
                                                                            RawJson.class,
                                                                            new RoutingContextToEntityContextAdapter(ctx)))
                                        .handle(new MultiEntityHandler(ctx, objectMapper));
              });
    }

    public StructuresVerticleFactory(StructuresProperties properties,
                                     Vertx vertx,
                                     GqlOperationService gqlOperationService,
                                     EntitiesService entitiesService,
                                     ObjectMapper objectMapper,
                                     OpenApiService openApiService,
                                     HealthChecks healthChecks,
                                     @Autowired(required = false) SecurityService securityService) {
        this.properties = properties;
        this.vertx = vertx;
        this.securityService = securityService;
        this.gqlOperationService = gqlOperationService;
        this.entitiesService = entitiesService;
        this.objectMapper = objectMapper;
        this.openApiService = openApiService;
        this.healthChecks = healthChecks;
    }

    public GqlVerticle createGqlVerticle(){
        return new GqlVerticle(gqlOperationService, properties, securityService);
    }

    public OpenApiVerticle createOpenApiVerticle(){
        return new OpenApiVerticle(properties, router);
    }

    public WebServerVerticle createWebServerVerticle(){
        return new WebServerVerticle(properties, healthChecks);
    }

}
