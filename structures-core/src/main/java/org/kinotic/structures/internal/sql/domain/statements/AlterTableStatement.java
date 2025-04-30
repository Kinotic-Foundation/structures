package org.kinotic.structures.internal.sql.domain.statements;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.sql.domain.Statement;

/**
 * Represents an ALTER TABLE statement in the DSL.
 * Adds a new field to an existing Elasticsearch index.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
public record AlterTableStatement(String tableName, String columnName, String type) implements Statement {
}