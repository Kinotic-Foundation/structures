package org.kinotic.structures.sql.parsers;

import org.kinotic.structures.sql.domain.Column;
import org.kinotic.structures.sql.domain.ColumnType;
import org.kinotic.structures.sql.domain.statements.TemplatePart;
import org.kinotic.structures.sql.domain.statements.ColumnTemplatePart;
import org.kinotic.structures.sql.domain.statements.SettingTemplatePart;
import org.kinotic.structures.sql.parser.StructuresSQLParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for parsing template parts from SQL statements.
 * Created by Navíd Mitchell 🤝 Grok on 3/31/25.
 */
public class TemplatePartParser {
    
    public static List<TemplatePart> parseTemplateParts(List<StructuresSQLParser.TemplatePartContext> parts) {
        var result = new ArrayList<TemplatePart>();
        
        for (var part : parts) {
            if (part.NUMBER_OF_SHARDS() != null) {
                result.add(new SettingTemplatePart(
                    "NUMBER_OF_SHARDS",
                    part.INTEGER_LITERAL().getText()
                ));
            } else if (part.NUMBER_OF_REPLICAS() != null) {
                result.add(new SettingTemplatePart(
                    "NUMBER_OF_REPLICAS",
                    part.INTEGER_LITERAL().getText()
                ));
            } else if (part.columnDefinition() != null) {
                var name = part.columnDefinition().ID().getText();
                var type = ColumnType.valueOf(part.columnDefinition().type().getText().toUpperCase());
                result.add(new ColumnTemplatePart(new Column(name, type)));
            }
        }
        
        return result;
    }
} 