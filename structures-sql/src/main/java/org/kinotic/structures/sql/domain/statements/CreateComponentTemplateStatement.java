package org.kinotic.structures.sql.domain.statements;

import org.kinotic.structures.sql.domain.Statement;

import java.util.List;

/**
 * Represents a CREATE COMPONENT TEMPLATE statement in the DSL.
 * Defines reusable settings and mappings for Elasticsearch indices.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
public record CreateComponentTemplateStatement(String templateName,
                                               List<ComponentDefinition> definitions) implements Statement {
}