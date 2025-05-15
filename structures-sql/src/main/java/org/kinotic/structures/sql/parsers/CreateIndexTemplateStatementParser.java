package org.kinotic.structures.sql.parsers;

import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.statements.ComponentDefinition;
import org.kinotic.structures.sql.domain.statements.CreateIndexTemplateStatement;
import org.kinotic.structures.sql.parser.StructuresSQLParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses CREATE INDEX TEMPLATE statements into CreateIndexTemplateStatement objects.
 * Handles index template creation with patterns and component templates for Elasticsearch.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
public class CreateIndexTemplateStatementParser implements StatementParser {
    @Override
    public boolean supports(StructuresSQLParser.StatementContext ctx) {
        return ctx.createIndexTemplateStatement() != null;
    }

    @Override
    public Statement parse(StructuresSQLParser.StatementContext ctx) {
        StructuresSQLParser.CreateIndexTemplateStatementContext templateCtx = ctx.createIndexTemplateStatement();
        String templateName = templateCtx.ID().getText();
        String indexPattern = templateCtx.STRING(0).getText().replaceAll("'", "");
        String componentTemplate = templateCtx.STRING(1).getText().replaceAll("'", "");

        List<ComponentDefinition> additionalDefinitions = new ArrayList<>();
        if (templateCtx.WITH() != null) {
            for (StructuresSQLParser.ComponentDefinitionContext def : templateCtx.componentDefinition()) {
                if (def.NUMBER_OF_SHARDS() != null) {
                    additionalDefinitions.add(new ComponentDefinition(
                            "NUMBER_OF_SHARDS", def.INTEGER_LITERAL().getText(), false));
                } else if (def.NUMBER_OF_REPLICAS() != null) {
                    additionalDefinitions.add(new ComponentDefinition(
                            "NUMBER_OF_REPLICAS", def.INTEGER_LITERAL().getText(), false));
                } else if (def.columnDefinition() != null) {
                    additionalDefinitions.add(new ComponentDefinition(
                            def.columnDefinition().ID().getText(), def.columnDefinition().type().getText(), true));
                }
            }
        }

        return new CreateIndexTemplateStatement(templateName, indexPattern, componentTemplate, additionalDefinitions);
    }
}