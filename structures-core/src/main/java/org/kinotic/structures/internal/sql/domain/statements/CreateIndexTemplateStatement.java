package org.kinotic.structures.internal.sql.domain.statements;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.sql.domain.Statement;

import java.util.List;

/**
 * Represents a CREATE INDEX TEMPLATE statement in the DSL.
 * Defines an index template with a pattern and component templates for Elasticsearch.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Getter
@RequiredArgsConstructor
public class CreateIndexTemplateStatement implements Statement {
    private final String templateName;
    private final String indexPattern; // e.g., "users-*"
    private final String componentTemplate; // Primary component template via USING
    private final List<CreateComponentTemplateStatement.ComponentDefinition> additionalDefinitions; // Optional WITH clause
}