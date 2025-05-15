package org.kinotic.structures.sql.parsers;

import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.statements.InsertStatement;
import org.kinotic.structures.sql.parser.StructuresSQLParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses INSERT statements into InsertStatement objects.
 * Handles insertion of new documents into an Elasticsearch index.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
public class InsertStatementParser implements StatementParser {

    @Override
    public boolean supports(StructuresSQLParser.StatementContext ctx) {
        return ctx.insertStatement() != null;
    }

    @Override
    public Statement parse(StructuresSQLParser.StatementContext ctx) {
        StructuresSQLParser.InsertStatementContext insertContext = ctx.insertStatement();
        
        String tableName = insertContext.tableName().getText();
        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        // Parse column names if specified
        if (insertContext.LPAREN() != null) {
            insertContext.columnName().forEach(column -> columns.add(column.getText()));
        }

        // Parse values
        insertContext.expression().forEach(expr -> {
            if (expr.STRING() != null) {
                // Remove quotes from string literals
                values.add(expr.STRING().getText().substring(1, expr.STRING().getText().length() - 1));
            } else if (expr.INTEGER_LITERAL() != null) {
                values.add(Integer.parseInt(expr.INTEGER_LITERAL().getText()));
            } else if (expr.BOOLEAN_LITERAL() != null) {
                values.add(Boolean.parseBoolean(expr.BOOLEAN_LITERAL().getText()));
            } else if (expr.PARAMETER() != null) {
                values.add(null); // Parameter will be set later
            } else if (expr.ID() != null) {
                values.add(expr.ID().getText()); // Field reference
            }
        });

        return new InsertStatement(tableName, columns, values);
    }
} 