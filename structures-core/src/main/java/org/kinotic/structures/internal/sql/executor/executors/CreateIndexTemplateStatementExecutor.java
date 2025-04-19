package org.kinotic.structures.internal.sql.executor.executors;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.sql.domain.Statement;
import org.kinotic.structures.internal.sql.domain.statements.CreateIndexTemplateStatement;
import org.kinotic.structures.internal.sql.executor.StatementExecutor;
import org.kinotic.structures.internal.sql.executor.TypeMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Executes CREATE INDEX TEMPLATE statements against Elasticsearch.
 * Creates index templates with patterns and component templates.
 * Created by Navíd Mitchell 🤪🤝Grok on 3/31/25.
 */
@Component
@RequiredArgsConstructor
public class CreateIndexTemplateStatementExecutor implements StatementExecutor<CreateIndexTemplateStatement, Void> {
    private final ElasticsearchAsyncClient client;

    @Override
    public boolean supports(Statement statement) {
        return statement instanceof CreateIndexTemplateStatement;
    }

    @Override
    public void executeMigration(CreateIndexTemplateStatement statement) {
        try {
            Map<String, Property> properties = new HashMap<>();
            statement.getAdditionalDefinitions().forEach(def -> {
                if (def.isColumn()) {
                    properties.put(def.getKey(), TypeMapper.mapType(def.getValue()));
                }
            });

            client.indices().putIndexTemplate(t -> t
                    .name(statement.getTemplateName())
                    .indexPatterns(Collections.singletonList(statement.getIndexPattern()))
                    .composedOf(Collections.singletonList(statement.getComponentTemplate()))
                    .template(te -> te
                            .settings(s -> {
                                statement.getAdditionalDefinitions().forEach(def -> {
                                    if (!def.isColumn()) {
                                        switch (def.getKey()) {
                                            case "NUMBER_OF_SHARDS":
                                                s.numberOfShards(def.getValue());
                                                break;
                                            case "NUMBER_OF_REPLICAS":
                                                s.numberOfReplicas(def.getValue());
                                                break;
                                        }
                                    }
                                });
                                return s;
                            })
                            .mappings(m -> properties.isEmpty() ? m : m.properties(properties))
                    )
            ).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute CREATE INDEX TEMPLATE migration", e);
        }
    }

    @Override
    public CompletableFuture<Void> executeQuery(CreateIndexTemplateStatement statement, Map<String, Object> parameters) {
        throw new UnsupportedOperationException("CREATE INDEX TEMPLATE not supported as a named query");
    }
}