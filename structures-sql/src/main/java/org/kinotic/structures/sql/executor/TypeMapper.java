package org.kinotic.structures.sql.executor;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.structures.sql.domain.ColumnType;

/**
 * Utility class for mapping SQL types to Elasticsearch field types.
 * Created by Navíd Mitchell 🤝 Grok on 3/31/25.
 */
public class TypeMapper {

    public static Property mapType(ColumnType type) {
        return switch (type) {
            case TEXT -> Property.of(p -> p.text(t -> t));
            case KEYWORD -> Property.of(p -> p.keyword(k -> k));
            case INTEGER -> Property.of(p -> p.integer(i -> i));
            case LONG -> Property.of(p -> p.long_(l -> l));
            case FLOAT -> Property.of(p -> p.float_(f -> f));
            case DOUBLE -> Property.of(p -> p.double_(d -> d));
            case BOOLEAN -> Property.of(p -> p.boolean_(b -> b));
            case DATE -> Property.of(p -> p.date(d -> d));
            case JSON -> Property.of(p -> p.flattened(f -> f));
            case BINARY -> Property.of(p -> p.binary(b -> b));
            case GEO_POINT -> Property.of(p -> p.geoPoint(gp -> gp));
            case GEO_SHAPE -> Property.of(p -> p.geoShape(gs -> gs));
            case UUID -> Property.of(p -> p.keyword(k -> k));
            case DECIMAL -> Property.of(p -> p.scaledFloat(sf -> sf));
            default -> throw new IllegalArgumentException("Unsupported SQL type: " + type);
        };
    }
}