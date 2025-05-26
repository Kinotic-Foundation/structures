package org.kinotic.structures.sql.executor.executors;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.statements.CreateTableStatement;
import org.kinotic.structures.sql.executor.StatementExecutor;
import org.kinotic.structures.sql.executor.TypeMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Executes CREATE TABLE statements against Elasticsearch.
 * Creates indices with specified field mappings.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
@RequiredArgsConstructor
public class CreateTableStatementExecutor implements StatementExecutor<CreateTableStatement, Void> {
    private final ElasticsearchAsyncClient client;

    @Override
    public boolean supports(Statement statement) {
        return statement instanceof CreateTableStatement;
    }

    @Override
    public void executeMigration(CreateTableStatement statement) {
        try {
            executeQuery(statement, null).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute migration CREATE TABLE", e);
        }
    }

    @Override
    public CompletableFuture<Void> executeQuery(CreateTableStatement statement, Map<String, Object> parameters) {
        return client.indices().exists(e -> e.index(statement.tableName()))
            .thenCompose(exists -> {
                if (exists.value()) {
                    if (statement.ifNotExists()) {
                        return CompletableFuture.completedFuture(null);
                    } else {
                        return CompletableFuture.failedFuture(
                            new IllegalArgumentException("Index '" + statement.tableName() + "' already exists"));
                    }
                }

                Map<String, Property> properties = new HashMap<>();
                statement.columns().forEach(column -> 
                    properties.put(column.name(), TypeMapper.mapType(column.type())));

                return client.indices().create(c -> c
                    .index(statement.tableName())
                    .mappings(m -> m
                        .properties(properties)
                    )
                ).thenApply(response -> null);
            });
    }
}