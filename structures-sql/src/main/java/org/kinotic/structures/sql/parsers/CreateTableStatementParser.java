package org.kinotic.structures.sql.parsers;

import org.kinotic.structures.sql.domain.Column;
import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.statements.CreateTableStatement;
import org.kinotic.structures.sql.parser.StructuresSQLParser;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Parses CREATE TABLE statements into CreateTableStatement objects.
 * Creates Elasticsearch indices with specified column mappings.
 * Created by Navíd Mitchell 🤪🤝Grok on 3/31/25.
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
        return new CreateTableStatement(tableName, columns);
    }
}