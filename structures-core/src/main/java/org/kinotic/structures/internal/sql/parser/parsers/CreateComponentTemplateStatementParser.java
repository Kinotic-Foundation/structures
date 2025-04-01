package org.kinotic.structures.internal.sql.parser.parsers;

import org.kinotic.structures.internal.sql.domain.Statement;
import org.kinotic.structures.internal.sql.domain.statements.CreateComponentTemplateStatement;
import org.kinotic.structures.internal.sql.parser.StatementParser;
import org.kinotic.structures.internal.sql.parser.StructuresSQLParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses CREATE COMPONENT TEMPLATE statements into CreateComponentTemplateStatement objects.
 * Handles component template creation for Elasticsearch.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
public class CreateComponentTemplateStatementParser implements StatementParser {
    @Override
    public boolean supports(StructuresSQLParser.StatementContext ctx) {
        return ctx.createComponentTemplateStatement() != null;
    }

    @Override
    public Statement parse(StructuresSQLParser.StatementContext ctx) {
        StructuresSQLParser.CreateComponentTemplateStatementContext templateCtx = ctx.createComponentTemplateStatement();
        String templateName = templateCtx.ID().getText();

        List<CreateComponentTemplateStatement.ComponentDefinition> definitions = new ArrayList<>();
        for (StructuresSQLParser.ComponentDefinitionContext def : templateCtx.componentDefinition()) {
            if (def.NUMBER_OF_SHARDS() != null) {
                definitions.add(new CreateComponentTemplateStatement.ComponentDefinition(
                        "NUMBER_OF_SHARDS", def.INTEGER_LITERAL().getText(), false));
            } else if (def.NUMBER_OF_REPLICAS() != null) {
                definitions.add(new CreateComponentTemplateStatement.ComponentDefinition(
                        "NUMBER_OF_REPLICAS", def.INTEGER_LITERAL().getText(), false));
            } else if (def.columnDefinition() != null) {
                definitions.add(new CreateComponentTemplateStatement.ComponentDefinition(
                        def.columnDefinition().ID().getText(), def.columnDefinition().type().getText(), true));
            }
        }

        return new CreateComponentTemplateStatement(templateName, definitions);
    }
}