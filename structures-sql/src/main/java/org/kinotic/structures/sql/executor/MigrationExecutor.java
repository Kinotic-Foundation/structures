package org.kinotic.structures.sql.executor;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import org.kinotic.structures.sql.domain.Migration;
import org.kinotic.structures.sql.domain.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Executes migrations against Elasticsearch, ensuring idempotency by tracking applied versions.
 * 
 * This executor supports two types of migrations:
 * 1. System-level migrations - Applied during application startup via the SystemMigrator
 * 2. Project-specific migrations - Can be applied on-demand at runtime from external sources
 * 
 * Each project tracks its own set of applied migrations independently, allowing for
 * isolated migration paths per project.
 * 
 * Created by Navíd Mitchell 🤪🤝Grok on 3/31/25.
 */
@Component
public class MigrationExecutor {
    private static final Logger log = LoggerFactory.getLogger(MigrationExecutor.class);
    private static final String MIGRATION_INDEX = "migration_history";
    public static final String SYSTEM_PROJECT = "_structures_system";

    private final ElasticsearchAsyncClient client;
    private final List<StatementExecutor<?, ?>> executors;
    private String migrationIndex = MIGRATION_INDEX;

    public MigrationExecutor(ElasticsearchAsyncClient client, 
                             List<StatementExecutor<?, ?>> executors) {
        this.client = client;
        this.executors = executors;
    }

    /**
     * For testing purposes - allows setting a custom migration index
     */
    public void setMigrationIndex(String migrationIndex) {
        this.migrationIndex = migrationIndex;
    }

    /**
     * Ensures that the migration tracking index exists in Elasticsearch
     * @return CompletableFuture<Boolean> that completes with true if index was created, false if it already existed
     */
    public CompletableFuture<Boolean> ensureMigrationIndexExists() {
        return client.indices().exists(ExistsRequest.of(r -> r.index(migrationIndex)))
            .thenCompose(exists -> {
                if (!exists.value()) {
                    log.info("Creating migration history index...");
                    return client.indices().create(c -> c
                            .index(migrationIndex)
                            .mappings(m -> m
                                    .properties("version", p -> p.keyword(k -> k))
                                    .properties("projectId", p -> p.keyword(k -> k))
                                    .properties("appliedAt", p -> p.date(d -> d))
                            )
                    ).thenApply(response -> true);
                }
                return CompletableFuture.completedFuture(false);
            });
    }

    /**
     * Executes system-level migrations that apply to the entire application
     */
    public CompletableFuture<Void> executeSystemMigrations(List<Migration> migrations) {
        return executeMigrationsForProject(migrations, SYSTEM_PROJECT);
    }

    /**
     * Executes project-specific migrations
     * These will be executed on request for a specific project
     */
    public CompletableFuture<Void> executeProjectMigrations(List<Migration> migrations, String projectId) {
        if (projectId == null || projectId.isEmpty()) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("Project ID cannot be null or empty"));
        }
        return executeMigrationsForProject(migrations, projectId);
    }

    /**
     * Executes migrations for a specific project context
     */
    private CompletableFuture<Void> executeMigrationsForProject(List<Migration> migrations, String projectId) {
        return CompletableFuture.runAsync(() -> {
            for (Migration migration : migrations) {
                try {
                    if (!isMigrationApplied(migration.getVersion(), projectId)) {
                        log.info("Applying migration {} for project {}", migration.getVersion(), projectId);
                        for (Statement statement : migration.getStatements()) {
                            executeStatement(statement);
                        }
                        recordMigration(migration.getVersion(), projectId);
                    } else {
                        log.debug("Migration {} already applied for project {}", migration.getVersion(), projectId);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to execute migration: " + migration.getVersion() + 
                                              " for project: " + projectId, e);
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

    public boolean isMigrationApplied(String version, String projectId) throws Exception {
        SearchResponse<Object> response = client.search(s -> s
                .index(migrationIndex)
                .query(q -> q
                        .bool(b -> b
                                .must(m -> m.term(t -> t.field("version").value(version)))
                                .must(m -> m.term(t -> t.field("projectId").value(projectId)))
                        )
                ),
                Object.class
        ).get();
        TotalHits totalHits = response.hits().total();
        return totalHits != null && totalHits.value() > 0;
    }

    private void recordMigration(String version, String projectId) throws Exception {
        client.index(i -> i
                .index(migrationIndex)
                .document(new MigrationRecord(version, projectId, System.currentTimeMillis()))
        ).get();
    }

    // Enhanced record to track migrations by project with timestamp
    private record MigrationRecord(String version, String projectId, long appliedAt) {}
}