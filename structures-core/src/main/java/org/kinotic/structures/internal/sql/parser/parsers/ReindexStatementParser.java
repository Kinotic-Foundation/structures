package org.kinotic.structures.internal.sql.parser.parsers;

import org.kinotic.structures.internal.sql.domain.Statement;
import org.kinotic.structures.internal.sql.domain.statements.ReindexStatement;
import org.kinotic.structures.internal.sql.parser.StatementParser;
import org.kinotic.structures.internal.sql.parser.StructuresSQLParser;
import org.springframework.stereotype.Component;

/**
 * Parses REINDEX statements into ReindexStatement objects.
 * Manages reindexing operations between Elasticsearch indices.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
public class ReindexStatementParser implements StatementParser {
    @Override
    public boolean supports(StructuresSQLParser.StatementContext ctx) {
        return ctx.reindexStatement() != null;
    }

    @Override
    public Statement parse(StructuresSQLParser.StatementContext ctx) {
        StructuresSQLParser.ReindexStatementContext reindexCtx = ctx.reindexStatement();
        String source = reindexCtx.ID(0).getText();
        String dest = reindexCtx.ID(1).getText();
        String conflicts = null;
        Integer maxDocs = null;
        String slices = null;
        Integer size = null;
        String sourceFields = null;
        String query = null;
        String script = null;

        if (reindexCtx.reindexOptions() != null) {
            for (var option : reindexCtx.reindexOptions().reindexOption()) {
                if (option.CONFLICTS() != null) {
                    conflicts = option.ABORT() != null ? "abort" : "proceed";
                } else if (option.MAX_DOCS() != null) {
                    maxDocs = Integer.parseInt(option.INTEGER_LITERAL().getText());
                } else if (option.SLICES() != null) {
                    slices = option.AUTO() != null ? "auto" : option.INTEGER_LITERAL().getText();
                } else if (option.SIZE() != null) {
                    size = Integer.parseInt(option.INTEGER_LITERAL().getText());
                } else if (option.SOURCE_FIELDS() != null) {
                    sourceFields = option.STRING().getText().replaceAll("'", "");
                } else if (option.QUERY() != null) {
                    query = option.STRING().getText().replaceAll("'", "");
                } else if (option.SCRIPT() != null) {
                    script = option.STRING().getText().replaceAll("'", "");
                }
            }
        }
        return new ReindexStatement(source, dest, conflicts, maxDocs, slices, size, sourceFields, query, script);
    }
}