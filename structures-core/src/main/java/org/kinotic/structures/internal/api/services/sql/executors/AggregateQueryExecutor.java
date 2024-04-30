package org.kinotic.structures.internal.api.services.sql.executors;

import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.QueryParameter;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.sql.ElasticVertxClient;

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
    public <T> CompletableFuture<List<T>> execute(List<QueryParameter> parameters,
                                                  Class<T> type,
                                                  EntityContext context) {

        return null;
    }

}
