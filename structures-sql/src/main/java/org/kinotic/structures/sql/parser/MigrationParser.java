package org.kinotic.structures.sql.parser;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.kinotic.structures.sql.domain.Migration;
import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.parser.parsers.StatementParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parses migration files (.sql) into a list of Migration objects.
 * Uses StatementParser implementations to process individual statements.
 * The version is extracted from the filename in the format V<number>__<description>.sql
 */
@Component
@RequiredArgsConstructor
public class MigrationParser {
    private final List<StatementParser> statementParsers;
    private static final Pattern VERSION_PATTERN = Pattern.compile("V(\\d+)__.*\\.sql$");

    public List<Migration> parse(String filePath) throws IOException {
        String version = extractVersionFromFilename(filePath);
        String sqlContent = new String(Files.readAllBytes(Paths.get(filePath)));
        CharStream input = CharStreams.fromString(sqlContent);
        StructuresSQLLexer lexer = new StructuresSQLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        StructuresSQLParser parser = new StructuresSQLParser(tokens);
        StructuresSQLParser.MigrationsContext tree = parser.migrations();
        return new MigrationVisitor(statementParsers, version).visit(tree);
    }

    private String extractVersionFromFilename(String filePath) {
        Path path = Paths.get(filePath);
        String filename = path.getFileName().toString();
        Matcher matcher = VERSION_PATTERN.matcher(filename);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid migration filename format. Expected V<number>__<description>.sql");
        }
        return "v" + matcher.group(1);
    }

    private static class MigrationVisitor extends StructuresSQLBaseVisitor<List<Migration>> {
        private final List<StatementParser> statementParsers;
        private final String version;

        MigrationVisitor(List<StatementParser> statementParsers, String version) {
            this.statementParsers = statementParsers;
            this.version = version;
        }

        @Override
        public List<Migration> visitMigrations(StructuresSQLParser.MigrationsContext ctx) {
            Migration migration = new Migration(version);
            for (StructuresSQLParser.StatementContext stmt : ctx.statement()) {
                List<StatementParser> supportingParsers = statementParsers.stream()
                        .filter(p -> p.supports(stmt))
                        .toList();
                
                if (supportingParsers.isEmpty()) {
                    throw new IllegalStateException("No parser found for statement: " + stmt.getText());
                }
                if (supportingParsers.size() > 1) {
                    throw new IllegalStateException("Multiple parsers found for statement: " + stmt.getText() + 
                        ". Parsers: " + supportingParsers.stream()
                            .map(p -> p.getClass().getSimpleName())
                            .collect(Collectors.joining(", ")));
                }
                
                Statement parsedStatement = supportingParsers.get(0).parse(stmt);
                migration.addStatement(parsedStatement);
            }
            return List.of(migration);
        }
    }
} 