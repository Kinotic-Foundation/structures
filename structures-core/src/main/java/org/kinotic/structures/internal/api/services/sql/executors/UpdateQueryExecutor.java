package org.kinotic.structures.internal.api.services.sql.executors;

import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.internal.api.services.sql.ParameterHolder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/24.
 */
@RequiredArgsConstructor
public class UpdateQueryExecutor implements QueryExecutor {

    private final String statement;

    @Override
    public <T> CompletableFuture<List<T>> execute(ParameterHolder parameterHolder,
                                                  Class<T> type,
                                                  EntityContext context) {
        return null;
    }

    @Override
    public <T> CompletableFuture<Page<T>> executePage(ParameterHolder parameterHolder,
                                                      Pageable pageable,
                                                      Class<T> type,
                                                      EntityContext context) {
        return null;
    }
}
