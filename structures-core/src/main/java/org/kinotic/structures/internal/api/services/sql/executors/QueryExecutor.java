package org.kinotic.structures.internal.api.services.sql.executors;

import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.QueryParameter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Query executor for executing queries against a data store
 * Created by Navíd Mitchell 🤪 on 4/28/24.
 */
public interface QueryExecutor {

    <T> CompletableFuture<List<T>> execute(List<QueryParameter> queryParameters, Class<T> type, EntityContext context);

    <T> CompletableFuture<Page<T>> executePage(List<QueryParameter> queryParameters, Pageable pageable, Class<T> type, EntityContext context);

}