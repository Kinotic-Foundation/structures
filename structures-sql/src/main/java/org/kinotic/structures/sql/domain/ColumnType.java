package org.kinotic.structures.sql.domain;

/**
 * Represents the supported data types for columns in the SQL DSL.
 * These types map directly to Elasticsearch field types.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
public enum ColumnType {
    TEXT,
    KEYWORD,
    INTEGER,
    BOOLEAN,
    DATE;

    @Override
    public String toString() {
        return name();
    }
} 