package org.kinotic.structures.sql.executor.executors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.kinotic.structures.sql.domain.Statement;
import org.kinotic.structures.sql.domain.statements.ColumnTemplatePart;
import org.kinotic.structures.sql.domain.statements.CreateIndexTemplateStatement;
import org.kinotic.structures.sql.domain.statements.SettingTemplatePart;
import org.kinotic.structures.sql.executor.StatementExecutor;
import org.kinotic.structures.sql.executor.TypeMapper;
import org.springframework.stereotype.Component;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import lombok.RequiredArgsConstructor;

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
            Map<String, String> settings = new HashMap<>();

            statement.parts().forEach(part -> {
                if (part instanceof ColumnTemplatePart columnPart) {
                    properties.put(columnPart.column().name(), TypeMapper.mapType(columnPart.column().type()));
                } else if (part instanceof SettingTemplatePart settingPart) {
                    settings.put(settingPart.name(), settingPart.value());
                }
            });

            client.indices().putIndexTemplate(t -> t
                    .name(statement.templateName())
                    .indexPatterns(Collections.singletonList(statement.indexPattern()))
                    .composedOf(Collections.singletonList(statement.componentTemplate()))
                    .template(te -> te
                            .settings(s -> {
                                settings.forEach((key, value) -> {
                                    switch (key) {
                                        case "NUMBER_OF_SHARDS" -> s.numberOfShards(value);
                                        case "NUMBER_OF_REPLICAS" -> s.numberOfReplicas(value);
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