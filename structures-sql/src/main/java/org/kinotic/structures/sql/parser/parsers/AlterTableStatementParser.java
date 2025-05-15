package org.kinotic.structures.sql.parser.parsers;

import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.statements.AlterTableStatement;
import org.kinotic.structures.sql.parser.StructuresSQLParser;
import org.springframework.stereotype.Component;

/**
 * Parses ALTER TABLE statements into AlterTableStatement objects.
 * Adds new fields to existing Elasticsearch indices.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
public class AlterTableStatementParser implements StatementParser {
    @Override
    public boolean supports(StructuresSQLParser.StatementContext ctx) {
        return ctx.alterTableStatement() != null;
    }

    @Override
    public Statement parse(StructuresSQLParser.StatementContext ctx) {
        StructuresSQLParser.AlterTableStatementContext alterCtx = ctx.alterTableStatement();
        String tableName = alterCtx.ID(0).getText();
        String columnName = alterCtx.ID(1).getText();
        String type = alterCtx.type().getText();
        return new AlterTableStatement(tableName, columnName, type);
    }
}