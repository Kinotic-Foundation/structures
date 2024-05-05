package org.kinotic.structures.internal.api.services.sql;

import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.sql.executors.QueryExecutor;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/29/24.
 */
public interface QueryExecutorFactory {
    /**
     * Creates a QueryExecutor for the given {@link Structure} and query name
     *
     * @param queryName the name of the query to create the QueryExecutor for
     * @param structure the {@link Structure} to create the QueryExecutor for
     * @return a {@link CompletableFuture} that will complete with the created {@link QueryExecutor} or an exception if the query could not be found
     */
    CompletableFuture<QueryExecutor> createQueryExecutor(String queryName, Structure structure);
}
