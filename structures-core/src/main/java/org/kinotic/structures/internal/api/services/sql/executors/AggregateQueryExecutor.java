package org.kinotic.structures.internal.api.services.sql.executors;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.kinotic.structures.api.decorators.MultiTenancyType;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.QueryParameter;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.sql.ElasticVertxClient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/24.
 */
public class AggregateQueryExecutor extends AbstractQueryExecutor {

    private final ElasticVertxClient elasticVertxClient;
    private final ObjectMapper objectMapper;
    private final String statement;

    public AggregateQueryExecutor(Structure structure,
                                  ElasticVertxClient elasticVertxClient,
                                  ObjectMapper objectMapper,
                                  String statement) {
        super(structure);
        this.elasticVertxClient = elasticVertxClient;
        this.objectMapper = objectMapper;
        this.statement = statement;
    }

    @Override
    public <T> CompletableFuture<List<T>> execute(List<QueryParameter> parameters,
                                                  Class<T> type,
                                                  EntityContext context) {
        List<Object> paramsToUse = new ArrayList<>();
        if(parameters != null){
            paramsToUse = parameters.stream()
                                    .map(QueryParameter::getValue)
                                    .collect(Collectors.toList());
        }

        JsonObject filter = null;
        // add multi tenancy filters if needed
        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED) {
             // Filter must fit the Query DSL format, and look like the following
                //     "bool":{
                //         "filter":[
                //         {
                //             "term":{
                //             "structuresTenantId":{
                //                 "value":"kinotic"
                //             }
                //         }
                //         },
                //         {
                //             "terms": {
                //             "_routing": ["kinotic"]
                //         }
                //         }
                //       ]
                //     }
            String tenantId = context.getParticipant().getTenantId();
            filter = new JsonObject().put("bool", new JsonObject()
                    .put("filter", new JsonArray()
                            .add(new JsonObject().put("term", new JsonObject()
                                    .put("structuresTenantId", new JsonObject()
                                            .put("value", tenantId))))
                            .add(new JsonObject().put("terms", new JsonObject()
                                    .put("_routing", new JsonArray().add(tenantId))))
                    ));
        }
        return elasticVertxClient.querySql(statement, paramsToUse, filter)
                .thenApply(buffer -> {
                    if(RawJson.class.isAssignableFrom(type)){
                        try {
                            //noinspection unchecked
                            return (List<T>) processBufferToRawJson(buffer);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to process buffer to raw json", e);
                        }
                    } else if(Map.class.isAssignableFrom(type)){
                        try {
                            //noinspection unchecked
                            return (List<T>) processBufferToMap(buffer);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to process buffer to map", e);
                        }
                    } else {
                        throw new RuntimeException("Type: " + type.getName() + " is not supported at this time");
                    }
                });
    }

    private List<Map<String, Object>> processBufferToMap(Buffer buffer) throws Exception {
        ElasticSQLResponse response = objectMapper.readValue(buffer.getBytes(), ElasticSQLResponse.class);
        List<ElasticColumn> elasticColumns = response.getColumns();
        List<Map<String,Object>> ret = new ArrayList<>(response.getRows().size());

        for(List<Object> row : response.getRows()){
            Map<String, Object> obj = new HashMap<>(response.getRows().size(),1.5F);

            for(int colIdx = 0; colIdx < row.size(); colIdx++){
                obj.put(elasticColumns.get(colIdx).getName(), row.get(colIdx));
            }
            ret.add(obj);
        }
        return ret;
    }

    private List<RawJson> processBufferToRawJson(Buffer buffer) throws Exception {
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
        return ret;
    }

}
