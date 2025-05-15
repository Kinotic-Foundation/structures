package org.kinotic.structures.sql.parser;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.kinotic.structures.sql.domain.Migration;
import org.kinotic.structures.sql.domain.Statement;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses migration files (.sql) into a list of Migration objects.
 * Uses StatementParser implementations to process individual statements.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
@RequiredArgsConstructor
public class MigrationParser {
    private final List<StatementParser> statementParsers;

    public List<Migration> parse(String filePath) throws IOException {
        String sqlContent = new String(Files.readAllBytes(Paths.get(filePath)));
        CharStream input = CharStreams.fromString(sqlContent);
        StructuresSQLLexer lexer = new StructuresSQLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        StructuresSQLParser parser = new StructuresSQLParser(tokens);
        StructuresSQLParser.MigrationsContext tree = parser.migrations();
        return new MigrationVisitor(statementParsers).visit(tree);
    }

    private static class MigrationVisitor extends StructuresSQLBaseVisitor<List<Migration>> {
        private final List<StatementParser> statementParsers;

        MigrationVisitor(List<StatementParser> statementParsers) {
            this.statementParsers = statementParsers;
        }

        @Override
        public List<Migration> visitMigrations(StructuresSQLParser.MigrationsContext ctx) {
            List<Migration> migrations = new ArrayList<>();
            if (ctx.migrationStatement() != null) {
                for (StructuresSQLParser.MigrationStatementContext migrationCtx : ctx.migrationStatement()) {
                    Migration migration = processMigrationStatement(migrationCtx);
                    migrations.add(migration);
                }
            }
            return migrations;
        }

        @Override
        public List<Migration> visitMigrationStatement(StructuresSQLParser.MigrationStatementContext ctx) {
            return List.of(processMigrationStatement(ctx));
        }

        private Migration processMigrationStatement(StructuresSQLParser.MigrationStatementContext ctx) {
            String version = ctx.STRING().getText().replaceAll("'", "");
            Migration migration = new Migration(version);
            if (ctx.statement() != null) {
                for (StructuresSQLParser.StatementContext stmt : ctx.statement()) {
                    Statement parsedStatement = statementParsers.stream()
                                                                .filter(parser -> parser.supports(stmt))
                                                                .findFirst()
                                                                .map(parser -> parser.parse(stmt))
                                                                .orElseThrow(() -> new IllegalStateException("No parser found for statement"));
                    migration.addStatement(parsedStatement);
                }
            }
            return migration;
        }
    }
}