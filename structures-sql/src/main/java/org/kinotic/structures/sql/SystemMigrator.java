package org.kinotic.structures.sql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.kinotic.structures.sql.domain.Migration;
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
            // First ensure the migration index exists
            boolean indexCreated = migrationExecutor.ensureMigrationIndexExists().get();
            if (indexCreated) {
                log.info("Created migration history index");
            }
            
            // Load and apply system migrations
            List<Migration> systemMigrations = loadSystemMigrations();
            if (!systemMigrations.isEmpty()) {
                log.info("Found {} system migrations to apply", systemMigrations.size());
                try {
                    migrationExecutor.executeSystemMigrations(systemMigrations).get();
                    log.info("Successfully applied all system migrations");
                } catch (ExecutionException ex) {
                    log.error("Failed to apply system migrations", ex.getCause());
                    throw new RuntimeException("Failed to apply system migrations", ex.getCause());
                }
            } else {
                log.info("No system migrations found");
            }
        } catch (Exception e) {
            log.error("Error during system migration", e);
            throw new RuntimeException("Failed to initialize system migrations", e);
        }
    }
    
    /**
     * Loads system migrations from migrations/system/*.sql
     */
    private List<Migration> loadSystemMigrations() {
        List<Migration> allMigrations = new ArrayList<>();
        try {
            log.debug("Loading system migrations from: {}", MIGRATIONS_PATH);
            
            PathMatchingResourcePatternResolver resolver = resourceLoader instanceof PathMatchingResourcePatternResolver ?
                (PathMatchingResourcePatternResolver) resourceLoader :
                new PathMatchingResourcePatternResolver(resourceLoader);
            
            Resource[] resources = resolver.getResources(MIGRATIONS_PATH);
            
            // Sort resources by filename to ensure migrations are applied in order
            Arrays.sort(resources, Comparator.comparing(Resource::getFilename));
            
            for (Resource resource : resources) {
                log.debug("Processing migration file: {}", resource.getFilename());
                try {
                    Migration migration = migrationParser.parse(resource);
                    allMigrations.add(migration);
                } catch (IOException e) {
                    log.warn("Failed to parse migration file: {}", resource.getFilename(), e);
                }
            }
            
        } catch (IOException e) {
            log.error("Error loading system migrations", e);
        }
        
        return allMigrations;
    }
}