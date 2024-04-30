package org.kinotic.structures.internal.api.services.sql;

import co.elastic.clients.elasticsearch.sql.TranslateResponse;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.apache.commons.lang3.Validate;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.internal.config.ElasticConnectionInfo;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
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
                                                             Object[] params){
        VertxCompletableFuture<TranslateResponse> responseFuture = new VertxCompletableFuture<>(vertx);
        JsonObject json = new JsonObject().put("query", statement);
        if(params != null) {
            json.put("params", params);
        }
        sqlTranslateRequest.sendJsonObject(json, ar -> {
            if(ar.succeeded()){
                TranslateResponse translateResponse = TranslateResponse.of(builder -> {
                    builder.withJson(new ByteArrayInputStream(ar.result()
                                                                .body()
                                                                .getBytes()));
                    return builder;
                });
                responseFuture.complete(translateResponse);
            }else{
                responseFuture.completeExceptionally(ar.cause());
            }
        });
        return responseFuture;
    }

    @Override
    public CompletableFuture<Buffer> querySql(String statement,
                                              Object[] params){
        VertxCompletableFuture<Buffer> responseFuture = new VertxCompletableFuture<>(vertx);
        JsonObject json = new JsonObject().put("query", statement);
        if(params != null) {
            json.put("params", params);
        }
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
