package org.kinotic.structures.internal.api.services.sql.executors;

import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.QueryParameter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/24.
 */
public class UpdateQueryExecutor implements QueryExecutor {

    private final String updateStatement;

    public UpdateQueryExecutor(String updateStatement) {
        this.updateStatement = updateStatement;
    }

    @Override
    public <T> CompletableFuture<List<T>> execute(List<QueryParameter> parameters,
                                                  Class<T> type,
                                                  EntityContext context) {
        return null;
    }

}
