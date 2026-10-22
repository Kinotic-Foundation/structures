package org.kinotic.persistence.internal.api.services.sql.executors;

import io.vertx.core.Future;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.persistence.internal.api.services.sql.QueryContext;

import java.util.List;

/**
 * Query executor for executing SQL queries against a data store
 * Created by Navíd Mitchell 🤪 on 4/28/24.
 */
public interface QueryExecutor {

    <T> Future<List<T>> execute(QueryContext context,
                                Class<T> type);

    <T> Future<Page<T>> executePage(QueryContext context,
                                    Pageable pageable,
                                    Class<T> type);

    /**
     * @return the short permission a caller needs on the entity definition to run the query: {@code can_read}
     *         for one that reads rows, {@code can_create}, {@code can_edit} or {@code can_delete} for one that
     *         inserts, updates or deletes them
     */
    String permission();

}
