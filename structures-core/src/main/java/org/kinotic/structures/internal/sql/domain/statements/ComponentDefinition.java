package org.kinotic.structures.internal.sql.domain.statements;

/**
 * @param key      e.g., "NUMBER_OF_SHARDS", "NUMBER_OF_REPLICAS", or column name
 * @param value    e.g., "1", "2", or column type
 * @param isColumn True if this is a column definition
 */
public record ComponentDefinition(String key, String value, boolean isColumn) {
}
