package org.kinotic.structures.internal.sql.parser.parsers;

import org.kinotic.structures.internal.sql.domain.Column;
import org.kinotic.structures.internal.sql.domain.Statement;
import org.kinotic.structures.internal.sql.domain.statements.CreateTableStatement;
import org.kinotic.structures.internal.sql.parser.StatementParser;
import org.kinotic.structures.internal.sql.parser.StructuresSQLParser;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Parses CREATE TABLE statements into CreateTableStatement objects.
 * Handles index creation or component template definitions for Elasticsearch.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
public class CreateTableStatementParser implements StatementParser {
    @Override
    public boolean supports(StructuresSQLParser.StatementContext ctx) {
        return ctx.createTableStatement() != null;
    }

    @Override
    public Statement parse(StructuresSQLParser.StatementContext ctx) {
        StructuresSQLParser.CreateTableStatementContext createCtx = ctx.createTableStatement();
        String tableName = createCtx.ID().getText();
        var columns = createCtx.columnDefinition().stream()
                               .map(col -> new Column(col.ID().getText(), col.type().getText()))
                               .collect(Collectors.toList());
        String template = createCtx.USES() != null ? createCtx.STRING().getText().replaceAll("'", "") : null;
        boolean isComponentTemplate = createCtx.COMPONENT_TEMPLATE() != null;
        return new CreateTableStatement(tableName, columns, template, isComponentTemplate);
    }
}