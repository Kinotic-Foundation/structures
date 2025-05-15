package org.kinotic.structures.sql.domain.statements;

import org.kinotic.structures.sql.domain.Statement;

import java.util.List;

/**
 * Represents an INSERT statement in the DSL.
 * Inserts new documents into an Elasticsearch index with specified field values.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
public record InsertStatement(String tableName,
                            List<String> columns,
                            List<Object> values) implements Statement {
} 