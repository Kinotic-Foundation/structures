package org.kinotic.structures.internal.api.services.sql.executors;

import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.QueryParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/29/24.
 */
public class CompositeQueryExecutor implements QueryExecutor {

    private List<QueryExecutor> queryExecutors = new ArrayList<>();

    public CompositeQueryExecutor addExecutor(QueryExecutor executor) {
        queryExecutors.add(executor);
        return this;
    }

    @Override
    public <T> CompletableFuture<T> execute(List<QueryParameter> queryParameters,
                                            Class<?> type,
                                            EntityContext context) {
//         CompletableFuture.allOf(queryExecutors.stream()
//                                             .map(executor -> executor.execute(parameters, type, context))
//                                             .toArray(CompletableFuture[]::new));
        return null;
    }

}
