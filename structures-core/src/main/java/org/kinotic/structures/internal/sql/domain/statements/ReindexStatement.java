package org.kinotic.structures.internal.sql.domain.statements;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.sql.domain.Statement;

/**
 * Represents a REINDEX statement in the DSL.
 * Reindexes data from one Elasticsearch index to another with optional configurations.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Getter
@RequiredArgsConstructor
public class ReindexStatement implements Statement {
    private final String source;
    private final String dest;
    private final String conflicts; // "abort" or "proceed", null defaults to "abort"
    private final Integer maxDocs; // null if not specified
    private final String slices; // "auto" or integer as string, null if not specified
    private final Integer size; // null if not specified
    private final String sourceFields; // null if not specified, comma-separated list
    private final String query; // JSON query payload, null if not specified
    private final String script; // JSON script payload, null if not specified
}