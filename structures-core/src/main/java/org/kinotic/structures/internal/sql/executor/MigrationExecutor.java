package org.kinotic.structures.internal.sql.executor;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import org.kinotic.structures.internal.sql.domain.Migration;
import org.kinotic.structures.internal.sql.domain.Statement;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Executes a list of migrations against Elasticsearch, ensuring idempotency by tracking applied versions.
 * Created by Navíd Mitchell 🤪🤝Grok on 3/31/25.
 */
public class MigrationExecutor {
    private static final String MIGRATION_INDEX = "migration_history";

    private final ElasticsearchAsyncClient client;
    private final List<StatementExecutor<?, ?>> executors;

    public MigrationExecutor(ElasticsearchAsyncClient client, List<StatementExecutor<?, ?>> executors) {
        this.client = client;
        this.executors = executors;
    }

    public CompletableFuture<Void> executeMigrations(List<Migration> migrations) {
        return CompletableFuture.runAsync(() -> {
            for (Migration migration : migrations) {
                try {
                    if (!isMigrationApplied(migration.getVersion())) {
                        for (Statement statement : migration.getStatements()) {
                            executeStatement(statement);
                        }
                        recordMigration(migration.getVersion());
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to execute migration: " + migration.getVersion(), e);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void executeStatement(Statement statement) {
        StatementExecutor<Statement, ?> executor = (StatementExecutor<Statement, ?>) findExecutor(statement);
        if (executor != null) {
            executor.executeMigration(statement);
        } else {
            throw new IllegalStateException("No executor found for statement: " + statement.getClass().getSimpleName());
        }
    }

    private StatementExecutor<?, ?> findExecutor(Statement statement) {
        return executors.stream()
                        .filter(e -> e.supports(statement))
                        .findFirst()
                        .orElse(null);
    }

    private boolean isMigrationApplied(String version) throws Exception {
        return client.search(s -> s
                                     .index(MIGRATION_INDEX)
                                     .query(q -> q.term(t -> t.field("version").value(version))),
                             Object.class
        ).get().hits().total().value() > 0;
    }

    private void recordMigration(String version) throws Exception {
        client.index(i -> i
                .index(MIGRATION_INDEX)
                .document(new MigrationRecord(version))
        ).get();
    }

    // Simple record for migration history
    private record MigrationRecord(String version) {}
}