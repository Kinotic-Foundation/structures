package org.kinotic.structures.internal.api.services.sql.executors;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.continuum.idl.api.schema.ParameterDefinition;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.idl.PageableC3Type;
import org.kinotic.structures.api.idl.decorators.MultiTenancyType;
import org.kinotic.structures.internal.api.services.sql.ElasticVertxClient;
import org.kinotic.structures.internal.api.services.sql.ParameterHolder;
import org.kinotic.structures.internal.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/24.
 */
public class AggregateQueryExecutor extends AbstractQueryExecutor {

    private final ElasticVertxClient elasticVertxClient;
    private final List<String> parameterNames = new ArrayList<>();
    private final String statement;

    public AggregateQueryExecutor(Structure structure,
                                  ElasticVertxClient elasticVertxClient,
                                  FunctionDefinition namedQueryDefinition,
                                  String statement) {
        super(structure);
        this.elasticVertxClient = elasticVertxClient;
        this.statement = statement;

        if(!namedQueryDefinition.getParameters().isEmpty()){
            for(ParameterDefinition definition : namedQueryDefinition.getParameters()) {
                if(!(definition.getType() instanceof PageableC3Type)){
                    parameterNames.add(definition.getName());
                }
            }
        }
    }

    @Override
    public <T> CompletableFuture<List<T>> execute(ParameterHolder parameterHolder,
                                                  Class<T> type,
                                                  EntityContext context) {
        List<Object> paramsToUse = null;
        if(!parameterNames.isEmpty()){
            paramsToUse = QueryUtils.extractOrderedParameterList(parameterHolder, parameterNames);
        }else if(parameterHolder != null){
            throw new IllegalArgumentException("This query does not support any parameters");

        }

        JsonObject filter = createFilterIfNeeded(context);

        return elasticVertxClient.querySql(statement, paramsToUse, filter, null, type)
                                 .thenApply(Page::getContent);
    }

    @Override
    public <T> CompletableFuture<Page<T>> executePage(ParameterHolder parameterHolder,
                                                      Pageable pageable,
                                                      Class<T> type,
                                                      EntityContext context) {
        List<Object> paramsToUse = null;
        if(!parameterNames.isEmpty()){
            paramsToUse = QueryUtils.extractOrderedParameterList(parameterHolder, parameterNames);
        }else if(parameterHolder != null){
            throw new IllegalArgumentException("This query does not support any parameters");

        }

        JsonObject filter = createFilterIfNeeded(context);

        return elasticVertxClient.querySql(statement, paramsToUse, filter, pageable, type);
    }

    private JsonObject createFilterIfNeeded(EntityContext context) {
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
        return filter;
    }
}
