package org.kinotic.structures.internal.api.services.sql.executors;

import org.kinotic.structures.api.domain.Structure;

/**
 * Created by Navíd Mitchell 🤪 on 4/29/24.
 */
public abstract class AbstractQueryExecutor implements QueryExecutor {

    protected final Structure structure;

    public AbstractQueryExecutor(Structure structure) {
        this.structure = structure;
    }
}
