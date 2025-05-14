package org.kinotic.structures.sql.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.sql.domain.Migration;
import org.kinotic.structures.sql.executor.MigrationExecutor;
import org.kinotic.structures.sql.initializer.MigrationInitializer;
import org.kinotic.structures.sql.loader.SystemMigrationLoader;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

/**
 * Auto-configuration for Elasticsearch migrations, similar to Flyway's auto-configuration.
 * This ensures migrations are applied during application startup at the right time.
 * 
 * Created by Navíd Mitchell 🤪🤝Grok on 5/14/25.
 */
@AutoConfiguration
@ConditionalOnClass({ElasticsearchAsyncClient.class})
@ConditionalOnBean(StructuresProperties.class)
@ComponentScan(basePackages = {
    "org.kinotic.structures.sql.executor",
    "org.kinotic.structures.sql.parser"
})
public class SqlMigrationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SystemMigrationLoader systemMigrationLoader(ResourceLoader resourceLoader) {
        return new SystemMigrationLoader(resourceLoader);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public List<Migration> systemMigrations(SystemMigrationLoader migrationLoader) {
        return migrationLoader.loadSystemMigrations();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public MigrationInitializer migrationInitializer(MigrationExecutor migrationExecutor, 
                                                    List<Migration> systemMigrations) {
        return new MigrationInitializer(migrationExecutor, systemMigrations);
    }
}