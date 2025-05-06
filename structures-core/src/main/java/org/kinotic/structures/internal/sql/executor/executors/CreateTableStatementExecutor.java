package org.kinotic.structures.internal.sql.executor.executors;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.sql.domain.Statement;
import org.kinotic.structures.internal.sql.domain.statements.CreateTableStatement;
import org.kinotic.structures.internal.sql.executor.StatementExecutor;
import org.kinotic.structures.internal.sql.executor.TypeMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Executes CREATE TABLE statements against Elasticsearch.
 * Creates indices with specified column mappings.
 * Created by Navíd Mitchell 🤪🤝Grok on 3/31/25.
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
            Map<String, Property> properties = new HashMap<>();
            statement.columns().forEach(col ->
                                                   properties.put(col.name(), TypeMapper.mapType(col.type())));

            client.indices().create(c -> c
                    .index(statement.tableName())
                    .mappings(m -> m.properties(properties))
            ).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute CREATE TABLE migration", e);
        }
    }

    @Override
    public CompletableFuture<Void> executeQuery(CreateTableStatement statement, Map<String, Object> parameters) {
        throw new UnsupportedOperationException("CREATE TABLE not supported as a named query");
    }
}