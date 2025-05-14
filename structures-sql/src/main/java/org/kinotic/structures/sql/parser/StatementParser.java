package org.kinotic.structures.internal.sql.parser;

import org.kinotic.structures.internal.sql.domain.Statement;

/**
 * Interface for parsing SQL-like statements from the ANTLR-generated parse tree.
 * Implementations handle specific statement types (e.g., CREATE TABLE, UPDATE).
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
public interface StatementParser {
    boolean supports(StructuresSQLParser.StatementContext ctx);
    Statement parse(StructuresSQLParser.StatementContext ctx);
}