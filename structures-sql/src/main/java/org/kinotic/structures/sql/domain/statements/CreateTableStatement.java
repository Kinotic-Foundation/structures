package org.kinotic.structures.sql.domain.statements;

import java.util.List;

import org.kinotic.structures.sql.domain.Column;
import org.kinotic.structures.sql.domain.Statement;

/**
 * Represents a CREATE TABLE statement in the DSL.
 * Defines an Elasticsearch index or component template with column mappings.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
public record CreateTableStatement(String tableName, List<Column> columns) implements Statement {
}