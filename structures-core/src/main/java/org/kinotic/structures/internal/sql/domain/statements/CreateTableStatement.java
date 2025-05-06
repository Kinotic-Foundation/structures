package org.kinotic.structures.internal.sql.domain.statements;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.sql.domain.Column;
import org.kinotic.structures.internal.sql.domain.Statement;

import java.util.List;

/**
 * Represents a CREATE TABLE statement in the DSL.
 * Defines an Elasticsearch index or component template with column mappings.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
public record CreateTableStatement(String tableName, List<Column> columns) implements Statement {
}