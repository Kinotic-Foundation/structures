package org.kinotic.structures.sql.domain;

/**
 * Represents a column definition in a CREATE TABLE statement.
 * Includes the column name and its data type (e.g., TEXT, INTEGER).
 * Created by Navíd Mitchell 🤝 Grok on 3/31/25.
 */
public record Column(String name, ColumnType type) {
}