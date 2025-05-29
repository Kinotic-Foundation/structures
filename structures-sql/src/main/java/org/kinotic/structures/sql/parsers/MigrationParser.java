package org.kinotic.structures.sql.parsers;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.kinotic.structures.sql.domain.Migration;
import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.parser.StructuresSQLBaseVisitor;
import org.kinotic.structures.sql.parser.StructuresSQLLexer;
import org.kinotic.structures.sql.parser.StructuresSQLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Parses migration files (.sql) into a Migration object.
 * Uses StatementParser implementations to process individual statements.
 * The version is extracted from the filename in the format V<number>__<description>.sql
 */
@Component
@RequiredArgsConstructor
public class MigrationParser {
    private final List<StatementParser> statementParsers;
    private static final Pattern VERSION_PATTERN = Pattern.compile("V(\\d+)__.*\\.sql$");
    private static final Logger log = LoggerFactory.getLogger(MigrationParser.class);

    public Migration parse(Resource resource) throws IOException {
        String version = extractVersionFromFilename(resource.getFilename());
        String sqlContent = new String(resource.getInputStream().readAllBytes());
        log.debug("Parsing migration file: {} with content:\n{}", resource.getFilename(), sqlContent);
        
        CharStream input = CharStreams.fromString(sqlContent);
        StructuresSQLLexer lexer = new StructuresSQLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        StructuresSQLParser parser = new StructuresSQLParser(tokens);
        StructuresSQLParser.MigrationsContext tree = parser.migrations();
        return new MigrationVisitor(statementParsers, version).visit(tree);
    }

    private String extractVersionFromFilename(String filename) {
        Matcher matcher = VERSION_PATTERN.matcher(filename);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid migration filename format. Expected V<number>__<description>.sql");
        }
        return "v" + matcher.group(1);
    }

    private static class MigrationVisitor extends StructuresSQLBaseVisitor<Migration> {
        private final List<StatementParser> statementParsers;
        private final String version;

        MigrationVisitor(List<StatementParser> statementParsers, String version) {
            this.statementParsers = statementParsers;
            this.version = version;
        }

        @Override
        public Migration visitMigrations(StructuresSQLParser.MigrationsContext ctx) {
            Migration migration = new Migration(version);
            List<StructuresSQLParser.StatementContext> statements = ctx.statement();
            log.debug("Found {} statements in migration file", statements.size());
            
            for (int i = 0; i < statements.size(); i++) {
                StructuresSQLParser.StatementContext stmt = statements.get(i);
                String statementText = stmt.getText();
                log.debug("Processing statement {} of {}: {}", i + 1, statements.size(), statementText);
                
                List<StatementParser> supportingParsers = statementParsers.stream()
                        .filter(p -> p.supports(stmt))
                        .toList();
                
                if (supportingParsers.isEmpty()) {
                    throw new IllegalStateException("No parser found for statement: " + statementText);
                }
                if (supportingParsers.size() > 1) {
                    throw new IllegalStateException("Multiple parsers found for statement: " + statementText + 
                        ". Parsers: " + supportingParsers.stream()
                            .map(p -> p.getClass().getSimpleName())
                            .collect(Collectors.joining(", ")));
                }
                
                Statement parsedStatement = supportingParsers.get(0).parse(stmt);
                migration.addStatement(parsedStatement);
                log.debug("Added statement {} to migration. Total statements: {}. Statement type: {}", 
                    i + 1,
                    migration.getStatements().size(), 
                    parsedStatement.getClass().getSimpleName());
            }
            
            log.debug("Finished processing migration file. Total statements in migration: {}", 
                migration.getStatements().size());
            return migration;
        }
    }
} 