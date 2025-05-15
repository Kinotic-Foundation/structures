package org.kinotic.structures.sql.domain.statements;

import java.util.Map;

import org.kinotic.structures.sql.domain.Expression;
import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.WhereClause;

/**
 * Represents an UPDATE statement in the DSL.
 * Updates documents in an Elasticsearch index with SET assignments and a WHERE clause.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 *
 * @param assignments e.g., {"status": Literal("'active'"), "age": BinaryExpression("age", "+", "1")}
 */
public record UpdateStatement(String tableName,
                              Map<String, Expression> assignments,
                              WhereClause whereClause) implements Statement {
}