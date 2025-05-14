package org.kinotic.structures.internal.sql;

import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.internal.sql.domain.Migration;
import org.kinotic.structures.internal.sql.executor.MigrationExecutor;
import org.kinotic.structures.internal.sql.parser.MigrationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Loads system migrations from the filesystem and applies them early in application startup.
 * This component has the highest precedence to ensure migrations are applied before dependent beans are initialized.
 * Created by Navíd Mitchell 🤪🤝Grok on 5/14/25.
 */
@Component
@Order(0) // Highest priority - ensure this runs before any other beans are initialized
public class SystemMigrator implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(SystemMigrator.class);
    private static final String MIGRATIONS_PATH = "classpath:migrations/system/*.sql";
    
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
    public void afterPropertiesSet() throws Exception {
        log.info("Initializing system migrations...");
        
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
    }
    
    /**
     * Loads system migrations from migrations/system/*.sql
     */
    private List<Migration> loadSystemMigrations() {
        List<Migration> allMigrations = new ArrayList<>();
        try {
            log.debug("Loading system migrations from: {}", MIGRATIONS_PATH);
            
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(resourceLoader);
            Resource[] resources = resolver.getResources(MIGRATIONS_PATH);
            
            // Sort resources by filename to ensure migrations are applied in order
            Arrays.sort(resources, Comparator.comparing(Resource::getFilename));
            
            for (Resource resource : resources) {
                log.debug("Processing migration file: {}", resource.getFilename());
                try {
                    Path path = Paths.get(resource.getURI());
                    List<Migration> migrations = migrationParser.parse(path.toString());
                    allMigrations.addAll(migrations);
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