package org.kinotic.structures.internal.sql.executor.executors;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.sql.domain.Statement;
import org.kinotic.structures.internal.sql.domain.statements.CreateComponentTemplateStatement;
import org.kinotic.structures.internal.sql.executor.StatementExecutor;
import org.kinotic.structures.internal.sql.executor.TypeMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Executes CREATE COMPONENT TEMPLATE statements against Elasticsearch.
 * Creates reusable component templates with settings and mappings.
 * Created by Navíd Mitchell 🤪🤝Grok on 3/31/25.
 */
@Component
@RequiredArgsConstructor
public class CreateComponentTemplateStatementExecutor implements StatementExecutor<CreateComponentTemplateStatement, Void> {
    private final ElasticsearchAsyncClient client;

    @Override
    public boolean supports(Statement statement) {
        return statement instanceof CreateComponentTemplateStatement;
    }

    @Override
    public void executeMigration(CreateComponentTemplateStatement statement) {
        try {
            Map<String, Property> properties = new HashMap<>();
            statement.getDefinitions().forEach(def -> {
                if (def.isColumn()) {
                    properties.put(def.getKey(), TypeMapper.mapType(def.getValue()));
                }
            });

            client.cluster().putComponentTemplate(t -> t
                    .name(statement.getTemplateName())
                    .template(te -> te
                            .settings(s -> {
                                statement.getDefinitions().forEach(def -> {
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
            throw new RuntimeException("Failed to execute CREATE COMPONENT TEMPLATE migration", e);
        }
    }

    @Override
    public CompletableFuture<Void> executeQuery(CreateComponentTemplateStatement statement, Map<String, Object> parameters) {
        throw new UnsupportedOperationException("CREATE COMPONENT TEMPLATE not supported as a named query");
    }
}