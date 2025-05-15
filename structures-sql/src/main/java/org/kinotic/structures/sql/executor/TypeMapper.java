package org.kinotic.structures.sql.executor;

import co.elastic.clients.elasticsearch._types.mapping.Property;

/**
 * Utility class for mapping SQL types to Elasticsearch field types.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
public class TypeMapper {

    public static Property mapType(String sqlType) {
        return switch (sqlType.toUpperCase()) {
            case "TEXT" -> Property.of(p -> p.text(t -> t));
            case "KEYWORD" -> Property.of(p -> p.keyword(k -> k));
            case "INTEGER" -> Property.of(p -> p.integer(i -> i));
            case "BOOLEAN" -> Property.of(p -> p.boolean_(b -> b));
            case "DATE" -> Property.of(p -> p.date(d -> d));
            default -> throw new IllegalArgumentException("Unsupported SQL type: " + sqlType);
        };
    }
}