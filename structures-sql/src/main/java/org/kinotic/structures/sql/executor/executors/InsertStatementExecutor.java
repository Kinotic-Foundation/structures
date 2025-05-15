package org.kinotic.structures.sql.executor.executors;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.statements.InsertStatement;
import org.kinotic.structures.sql.executor.StatementExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Executes INSERT statements against Elasticsearch.
 * Handles bulk insertion of documents with specified field values.
 * Created by Navíd Mitchell 🤝Grok on 3/31/25.
 */
@Component
@RequiredArgsConstructor
public class InsertStatementExecutor implements StatementExecutor<InsertStatement, Void> {

    private final ElasticsearchClient client;

    @Override
    public boolean supports(Statement statement) {
        return statement instanceof InsertStatement;
    }

    @Override
    public void executeMigration(InsertStatement statement) {
        BulkRequest.Builder br = new BulkRequest.Builder();

        // Create a document with the specified values
        final Map<String, Object> document = new HashMap<>();
        if (!statement.columns().isEmpty()) {
            // If columns are specified, create a map with column-value pairs
            for (int i = 0; i < statement.columns().size(); i++) {
                document.put(statement.columns().get(i), statement.values().get(i));
            }
        } else {
            // If no columns specified, assume values are in order
            document.put("value", statement.values().get(0));
        }

        // Add the document to the bulk request
        br.operations(op -> op
            .index(idx -> idx
                .index(statement.tableName())
                .document(document)
            )
        );

        try {
            BulkResponse response = client.bulk(br.build());
            if (response.errors()) {
                for (BulkResponseItem item : response.items()) {
                    var error = item.error();
                    if (error != null && error.reason() != null) {
                        throw new RuntimeException("Error inserting document: " + error.reason());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute INSERT statement", e);
        }
    }

    @Override
    public CompletableFuture<Void> executeQuery(InsertStatement statement, Map<String, Object> parameters) {
        return CompletableFuture.runAsync(() -> executeMigration(statement));
    }
} 