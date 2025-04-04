package org.kinotic.structures.internal.sql.parser.parsers;

import org.kinotic.structures.internal.sql.domain.Expression;
import org.kinotic.structures.internal.sql.domain.Statement;
import org.kinotic.structures.internal.sql.domain.WhereClause;
import org.kinotic.structures.internal.sql.domain.statements.UpdateStatement;
import org.kinotic.structures.internal.sql.parser.ExpressionVisitor;
import org.kinotic.structures.internal.sql.parser.StatementParser;
import org.kinotic.structures.internal.sql.parser.StructuresSQLParser;
import org.kinotic.structures.internal.sql.parser.WhereClauseVisitor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parses UPDATE statements into UpdateStatement objects.
 * Handles SET assignments and complex WHERE clauses for Elasticsearch updates.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
public class UpdateStatementParser implements StatementParser {
    private final ExpressionVisitor expressionVisitor = new ExpressionVisitor();
    private final WhereClauseVisitor whereClauseVisitor = new WhereClauseVisitor();

    @Override
    public boolean supports(StructuresSQLParser.StatementContext ctx) {
        return ctx.updateStatement() != null;
    }

    @Override
    public Statement parse(StructuresSQLParser.StatementContext ctx) {
        StructuresSQLParser.UpdateStatementContext updateCtx = ctx.updateStatement();
        String tableName = updateCtx.ID().getText();

        Map<String, Expression> assignments = new LinkedHashMap<>();
        for (StructuresSQLParser.AssignmentContext assignment : updateCtx.assignment()) {
            String field = assignment.ID().getText();
            Expression expression = expressionVisitor.visit(assignment.expression());
            assignments.put(field, expression);
        }

        WhereClause whereClause = whereClauseVisitor.visit(updateCtx.whereClause());

        return new UpdateStatement(tableName, assignments, whereClause);
    }
}