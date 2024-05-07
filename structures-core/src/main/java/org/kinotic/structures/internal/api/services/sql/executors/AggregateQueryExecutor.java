package org.kinotic.structures.internal.api.services.sql.executors;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.idl.decorators.MultiTenancyType;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.QueryParameter;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.sql.ElasticVertxClient;
import org.kinotic.structures.internal.utils.NamedQueryUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/24.
 */
public class AggregateQueryExecutor extends AbstractQueryExecutor {

    private final ElasticVertxClient elasticVertxClient;
    private final String statement;

    public AggregateQueryExecutor(Structure structure,
                                  ElasticVertxClient elasticVertxClient,
                                  String statement) {
        super(structure);
        this.elasticVertxClient = elasticVertxClient;
        this.statement = statement;
    }

    @Override
    public <T> CompletableFuture<List<T>> execute(List<QueryParameter> queryParameters,
                                                  Class<T> type,
                                                  EntityContext context) {

        JsonObject filter = createFilterIfNeeded(context);
        List<Object> paramsToUse = NamedQueryUtils.extractArgumentsList(queryParameters);
        return elasticVertxClient.querySql(statement, paramsToUse, filter, null, type)
                                 .thenApply(Page::getContent);
    }

    @Override
    public <T> CompletableFuture<Page<T>> executePage(List<QueryParameter> queryParameters,
                                                      Pageable pageable,
                                                      Class<T> type,
                                                      EntityContext context) {
        JsonObject filter = createFilterIfNeeded(context);
        List<Object> paramsToUse = NamedQueryUtils.extractArgumentsList(queryParameters);
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
