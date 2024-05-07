package org.kinotic.structures.internal.api.services.sql;

import co.elastic.clients.elasticsearch.sql.TranslateResponse;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.SimpleJsonpMapper;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.core.api.crud.CursorPage;
import org.kinotic.continuum.core.api.crud.CursorPageable;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.internal.api.services.sql.executors.ElasticColumn;
import org.kinotic.structures.internal.api.services.sql.executors.ElasticSQLResponse;
import org.kinotic.structures.internal.config.ElasticConnectionInfo;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final ObjectMapper objectMapper;
    private final Vertx vertx;
    private final WebClient webClient;

    public DefaultElasticVertxClient(ObjectMapper objectMapper,
                                     StructuresProperties structuresProperties,
                                     Vertx vertx) {
        this.objectMapper = objectMapper;
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

    @PreDestroy
    public void destroy(){
        webClient.close();
    }

    @Override
    public <T> CompletableFuture<Page<T>> querySql(String statement,
                                                   List<?> parameters,
                                                   JsonObject filter,
                                                   Pageable pageable,
                                                   Class<T> type) {
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

        VertxCompletableFuture<Page<T>> fut = new VertxCompletableFuture<>(vertx);
        sqlQueryRequest.sendJsonObject(json, ar -> {
            if(ar.succeeded()){
                Buffer buffer = ar.result().body();
                if(RawJson.class.isAssignableFrom(type)){
                    try {
                        //noinspection unchecked
                        fut.complete((Page<T>)processBufferToRawJson(buffer));
                    } catch (Exception e) {
                        fut.completeExceptionally(new IllegalStateException("Failed to process buffer to raw json", e));
                    }
                } else if(Map.class.isAssignableFrom(type)){
                    try {
                        //noinspection unchecked
                        fut.complete((Page<T>)processBufferToMap(buffer));
                    } catch (Exception e) {
                        fut.completeExceptionally(new IllegalStateException("Failed to process buffer to map", e));
                    }
                } else {
                    fut.completeExceptionally(new IllegalArgumentException("Type: " + type.getName() + " is not supported at this time"));
                }
            }else{
                fut.completeExceptionally(ar.cause());
            }
        });
        return fut;
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

    private Page<Map<String, Object>> processBufferToMap(Buffer buffer) throws Exception {
        ElasticSQLResponse response = objectMapper.readValue(buffer.getBytes(), ElasticSQLResponse.class);
        List<ElasticColumn> elasticColumns = response.getColumns();
        List<Map<String,Object>> ret = new ArrayList<>(response.getRows().size());

        for(List<Object> row : response.getRows()){
            Map<String, Object> obj = new HashMap<>(response.getRows().size(), 1.5F);

            for(int colIdx = 0; colIdx < row.size(); colIdx++){
                obj.put(elasticColumns.get(colIdx).getName(), row.get(colIdx));
            }
            ret.add(obj);
        }
        return new CursorPage<>(ret, response.getCursor(), null);
    }

    private Page<RawJson> processBufferToRawJson(Buffer buffer) throws Exception {
        ElasticSQLResponse response = objectMapper.readValue(buffer.getBytes(), ElasticSQLResponse.class);
        List<ElasticColumn> elasticColumns = response.getColumns();
        List<RawJson> ret = new ArrayList<>(response.getRows().size());

        for(List<Object> row : response.getRows()){

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8);
            jsonGenerator.writeStartObject();

            for(int colIdx = 0; colIdx < row.size(); colIdx++){
                jsonGenerator.writeFieldName(elasticColumns.get(colIdx).getName());
                jsonGenerator.writePOJO(row.get(colIdx));
            }
            jsonGenerator.writeEndObject();
            jsonGenerator.flush();
            ret.add(new RawJson(outputStream.toByteArray()));
        }
        return new CursorPage<>(ret, response.getCursor(), null);
    }
}
