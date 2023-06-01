package org.kinotic.structures.internal.endpoints;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.apache.commons.lang3.Validate;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.EntitiesService;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

/**
 * Created by Navíd Mitchell 🤪 on 5/29/23.
 */
@Component
public class OpenApiVerticle extends AbstractVerticle {

    private final StructuresProperties properties;
    private final EntitiesService entitiesService;

    private HttpServer server;

    public OpenApiVerticle(StructuresProperties properties, EntitiesService entitiesService) {
        this.properties = properties;
        this.entitiesService = entitiesService;
    }

    public void start(Promise<Void> startPromise) {

        server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        // Get Entity By ID
        router.get("/:structureId/:id").handler(ctx -> {

            String structureId = ctx.pathParam("structureId");
            String id = ctx.pathParam("id");

            try{
                Validate.notNull(structureId, "structureId must not be null");
                Validate.notNull(id, "id must not be null");

                entitiesService.findById(structureId, id)
                               .handle((BiFunction<RawJson, Throwable, Void>) (rawJson, throwable) -> {
                                   if (throwable == null) {
                                       ctx.response().end(Buffer.buffer(rawJson.data()));
                                   } else {
                                       writeException(ctx.response(), throwable);
                                   }
                                   return null;
                               });

            }catch (Exception e){
                writeException(ctx.response(), e);
            }
        }).produces("application/json");


        // Delete Entity By ID
        router.delete("/:structureId/:id").handler(ctx -> {

            String structureId = ctx.pathParam("structureId");
            String id = ctx.pathParam("id");

            try{
                Validate.notNull(structureId, "structureId must not be null");
                Validate.notNull(id, "id must not be null");

                entitiesService.deleteById(structureId, id)
                               .handle((BiFunction<Void, Throwable, Void>) (v, throwable) -> {
                                   if (throwable == null) {
                                       ctx.response().end();
                                   } else {
                                       writeException(ctx.response(), throwable);
                                   }
                                   return null;
                               });

            }catch (Exception e){
                writeException(ctx.response(), e);
            }
        });

        // Save entity
        router.post("/:structureId").handler(ctx -> {

            String structureId = ctx.pathParam("structureId");

            try{
                Validate.notNull(structureId, "structureId must not be null");

                entitiesService.save(structureId, new RawJson(ctx.getBody().getBytes()))
                               .handle((BiFunction<RawJson, Throwable, Void>) (rawJson, throwable) -> {
                                   if (throwable == null) {
                                       ctx.response().end(Buffer.buffer(rawJson.data()));
                                   } else {
                                       writeException(ctx.response(), throwable);
                                   }
                                   return null;
                               });

            }catch (Exception e){
                writeException(ctx.response(), e);
            }
        });

//        // Find all entities
//        router.get("/:structureId").handler(ctx -> {
//            ctx.response().end("Hello World");
//        });

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

    private void writeException(HttpServerResponse response, Throwable throwable){
        if(throwable instanceof IllegalArgumentException) {
            response.setStatusCode(400);
        }else if(throwable instanceof NullPointerException){
            response.setStatusCode(400);
        } else {
            response.setStatusCode(500);
        }
        response.putHeader("Content-Type", "application/json");
        response.end(new JsonObject().put("error", throwable.getMessage()).encode());
    }


}
