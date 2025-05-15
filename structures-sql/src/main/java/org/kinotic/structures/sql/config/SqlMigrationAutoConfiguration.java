package org.kinotic.structures.sql.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;

/**
 * Auto-configuration for Elasticsearch migrations, similar to Flyway's auto-configuration.
 * This ensures migrations are applied during application startup at the right time.
 * 
 * Created by Navíd Mitchell 🤪🤝Grok on 5/14/25.
 */
@AutoConfiguration
@ConditionalOnClass(ElasticsearchAsyncClient.class)
public class SqlMigrationAutoConfiguration {

    /**
     * The primary configuration for SQL migrations
     */
    @Configuration
    @ComponentScan(basePackages = {
        "org.kinotic.structures.sql"  // Scan the base package to find SystemMigrator
    })
    static class MigrationConfiguration {
        // ComponentScan will find and register all required components
    }
}