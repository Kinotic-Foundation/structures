package org.kinotic.structures.internal.sql.domain.statements;

import org.kinotic.structures.internal.sql.domain.Statement;

/**
 * Represents a REINDEX statement in the DSL.
 * Reindexes data from one Elasticsearch index to another with optional configurations.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 *
 * @param conflicts    "abort" or "proceed", null defaults to "abort"
 * @param maxDocs      null if not specified
 * @param slices       "auto" or integer as string, null if not specified
 * @param size         null if not specified
 * @param sourceFields null if not specified, comma-separated list
 * @param query        JSON query payload, null if not specified
 * @param script       JSON script payload, null if not specified
 */
public record ReindexStatement(String source,
                               String dest,
                               String conflicts,
                               Integer maxDocs,
                               String slices,
                               Integer size,
                               String sourceFields,
                               String query,
                               String script) implements Statement {
}