package org.kinotic.structures.sql;

import java.util.List;

import org.kinotic.structures.sql.domain.Migration;
import org.kinotic.structures.sql.domain.ResourceMigration;
import org.kinotic.structures.sql.executor.MigrationExecutor;
import org.kinotic.structures.sql.parsers.MigrationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 * Loads system migrations from the filesystem and applies them after the Elasticsearch client is configured
 * but before other components are initialized.
 */
@Component
public class SystemMigrator implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(SystemMigrator.class);
    private static final String MIGRATIONS_PATH = "classpath:migrations/*.sql";
    
    private final MigrationExecutor migrationExecutor;
    private final MigrationParser migrationParser;
    private final ResourceLoader resourceLoader;
    
    public SystemMigrator(MigrationExecutor migrationExecutor, 
                         MigrationParser migrationParser,
                         ResourceLoader resourceLoader) {
        this.migrationExecutor = migrationExecutor;
        this.migrationParser = migrationParser;
        this.resourceLoader = resourceLoader;
    }
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Initializing system migrations...");
        try {
            PathMatchingResourcePatternResolver resolver = resourceLoader instanceof PathMatchingResourcePatternResolver ?
                (PathMatchingResourcePatternResolver) resourceLoader :
                new PathMatchingResourcePatternResolver(resourceLoader);

            Resource[] resources = resolver.getResources(MIGRATIONS_PATH);
            
            List<Migration> migrations = new java.util.ArrayList<>();
            for (Resource resource : resources) {
                migrations.add(new ResourceMigration(resource, migrationParser));
            }
            log.info("Found {} migration files to process", migrations.size());
            migrationExecutor.executeSystemMigrations(migrations).get();
            log.info("System migrations processing complete");
        } catch (Exception e) {
            log.error("Error during system migration", e);
            throw new IllegalStateException("Failed to initialize system migrations", e);
        }
    }
    
}