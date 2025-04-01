package org.kinotic.structures.internal.sql.domain.statements;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.sql.domain.Statement;

import java.util.List;

/**
 * Represents a CREATE COMPONENT TEMPLATE statement in the DSL.
 * Defines reusable settings and mappings for Elasticsearch indices.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Getter
@RequiredArgsConstructor
public class CreateComponentTemplateStatement implements Statement {
    private final String templateName;
    private final List<ComponentDefinition> definitions;

    @Getter
    @RequiredArgsConstructor
    public static class ComponentDefinition {
        private final String key;  // e.g., "NUMBER_OF_SHARDS", "NUMBER_OF_REPLICAS", or column name
        private final String value; // e.g., "1", "2", or column type
        private final boolean isColumn; // True if this is a column definition
    }
}