package org.kinotic.structures.internal.sql.domain.statements;

import org.kinotic.structures.internal.sql.domain.Statement;

import java.util.List;

/**
 * Represents a CREATE INDEX TEMPLATE statement in the DSL.
 * Defines an index template with a pattern and component templates for Elasticsearch.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 *
 * @param indexPattern          e.g., "users-*"
 * @param componentTemplate     Primary component template via USING
 * @param additionalDefinitions Optional WITH clause
 */
public record CreateIndexTemplateStatement(String templateName, String indexPattern, String componentTemplate,
                                           List<ComponentDefinition> additionalDefinitions) implements Statement {
}