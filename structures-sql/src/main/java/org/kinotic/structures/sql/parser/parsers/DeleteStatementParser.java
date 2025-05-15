package org.kinotic.structures.sql.parser.parsers;

import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.WhereClause;
import org.kinotic.structures.sql.domain.statements.DeleteStatement;
import org.kinotic.structures.sql.parser.StatementParser;
import org.kinotic.structures.sql.parser.StructuresSQLParser;
import org.kinotic.structures.sql.parser.WhereClauseVisitor;
import org.springframework.stereotype.Component;

/**
 * Parses DELETE statements into DeleteStatement objects.
 * Handles deletion of documents from an Elasticsearch index based on a WHERE clause.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
public class DeleteStatementParser implements StatementParser {
    private final WhereClauseVisitor whereClauseVisitor = new WhereClauseVisitor();

    @Override
    public boolean supports(StructuresSQLParser.StatementContext ctx) {
        return ctx.deleteStatement() != null;
    }

    @Override
    public Statement parse(StructuresSQLParser.StatementContext ctx) {
        StructuresSQLParser.DeleteStatementContext deleteCtx = ctx.deleteStatement();
        String tableName = deleteCtx.ID().getText();
        WhereClause whereClause = whereClauseVisitor.visit(deleteCtx.whereClause());
        return new DeleteStatement(tableName, whereClause);
    }
}