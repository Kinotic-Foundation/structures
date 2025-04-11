package org.kinotic.structures.internal.sql.domain.statements;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.sql.domain.Statement;
import org.kinotic.structures.internal.sql.domain.WhereClause;

/**
 * Represents a DELETE statement in the DSL.
 * Deletes documents from an Elasticsearch index based on a WHERE clause.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Getter
@RequiredArgsConstructor
public class DeleteStatement implements Statement {
    private final String tableName;
    private final WhereClause whereClause;
}