package org.kinotic.structures.internal.api.services.sql;

import co.elastic.clients.elasticsearch.sql.TranslateResponse;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.SimpleJsonpMapper;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.core.api.crud.CursorPageable;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.internal.config.ElasticConnectionInfo;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Provides access to ElasticSearch via Vertx.
 * This was done because the ElasticSearch Java client is missing functionality that we need.
 * Created by Navíd Mitchell 🤪 on 4/29/24.
 */
@Component
public class DefaultElasticVertxClient implements ElasticVertxClient {

    private final HttpRequest<Buffer> sqlQueryRequest;
    private final HttpRequest<Buffer> sqlTranslateRequest;
    private final StructuresProperties structuresProperties;
    private final WebClient webClient;
    private final Vertx vertx;

    public DefaultElasticVertxClient(StructuresProperties structuresProperties,
                                     Vertx vertx) {
        this.structuresProperties = structuresProperties;
        this.vertx = vertx;

        WebClientOptions options = new WebClientOptions()
                .setConnectTimeout((int) structuresProperties.getElasticConnectionTimeout().toMillis());
        this.webClient = WebClient.create(vertx, options);

        Validate.notEmpty(structuresProperties.getElasticConnections(), "No Elastic connections defined");

        ElasticConnectionInfo elasticConnectionInfo = structuresProperties.getElasticConnections().get(0);
        sqlQueryRequest = webClient.post(elasticConnectionInfo.getPort(),
                                         elasticConnectionInfo.getHost(), "/_sql");
        if(elasticConnectionInfo.getScheme().equalsIgnoreCase("https")){
            sqlQueryRequest.ssl(true);
        }
        if(structuresProperties.hasElasticUsernameAndPassword()){
            sqlQueryRequest.basicAuthentication(structuresProperties.getElasticUsername(),
                                                structuresProperties.getElasticPassword());
        }

        sqlTranslateRequest = sqlQueryRequest.copy().uri("/_sql/translate");
    }

    @Override
    public CompletableFuture<TranslateResponse> translateSql(String statement,
                                                             List<?> parameters){
        VertxCompletableFuture<TranslateResponse> responseFuture = new VertxCompletableFuture<>(vertx);
        JsonObject json = new JsonObject().put("query", statement);
        if(parameters != null) {
            JsonArray paramsJson = new JsonArray();
            for(Object param : parameters){
                paramsJson.add(param);
            }
            json.put("params", paramsJson);
        }
        sqlTranslateRequest.sendJsonObject(json, ar -> {
            if(ar.succeeded()){
                try {
                    TranslateResponse translateResponse = TranslateResponse.of(builder -> {
                        InputStream input = new ByteArrayInputStream(ar.result()
                                                                    .body()
                                                                    .getBytes());
                        JsonpMapper mapper = SimpleJsonpMapper.INSTANCE; // We don't want to fail on unknown fields
                        builder.withJson(mapper.jsonProvider().createParser(input), mapper);
                        return builder;
                    });
                    responseFuture.complete(translateResponse);
                } catch (Exception e) {
                    responseFuture.completeExceptionally(e);
                }
            }else{
                responseFuture.completeExceptionally(ar.cause());
            }
        });
        return responseFuture;
    }

    @Override
    public CompletableFuture<Buffer> querySql(String statement,
                                              List<?> parameters,
                                              JsonObject filter,
                                              Pageable pageable){
        JsonObject json = new JsonObject();
        boolean foundCursor = false;
        if(pageable != null){
            if(pageable instanceof CursorPageable){
                CursorPageable cursorPageable = (CursorPageable) pageable;
                if(cursorPageable.getCursor() != null) {
                    foundCursor = true;
                    json.put("cursor", cursorPageable.getCursor());
                }
                json.put("fetch_size", pageable.getPageSize());
            }else{
                throw new IllegalArgumentException("Only CursorPageable is supported for queries containing Aggregations.");
            }
        }

        // Only add the query if we are not using a cursor
        if(!foundCursor){
            json.put("query", statement)
                .put("page_timeout", "2m");
            if(parameters != null) {
                JsonArray paramsJson = new JsonArray();
                for(Object param : parameters){
                    paramsJson.add(param);
                }
                json.put("params", paramsJson);
            }
            if(filter != null){
                json.put("filter", filter);
            }
        }

        VertxCompletableFuture<Buffer> responseFuture = new VertxCompletableFuture<>(vertx);
        sqlQueryRequest.sendJsonObject(json, ar -> {
            if(ar.succeeded()){
                responseFuture.complete(ar.result().body());
            }else{
                responseFuture.completeExceptionally(ar.cause());
            }
        });
        return responseFuture;
    }

}
