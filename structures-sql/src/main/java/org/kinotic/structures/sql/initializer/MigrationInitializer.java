package org.kinotic.structures.sql.initializer;

import org.kinotic.structures.sql.domain.Migration;
import org.kinotic.structures.sql.executor.MigrationExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * Initializes the migration system and applies migrations during Spring context initialization.
 * Modeled after Flyway's FlywayMigrationInitializer with the same order value (0).
 * 
 * This component runs during the Spring bean initialization phase after dependencies are injected
 * but before dependent beans are fully initialized.
 * 
 * Created by Navíd Mitchell 🤪🤝Grok on 5/14/25.
 */
public class MigrationInitializer implements InitializingBean, Ordered {
    
    private static final Logger log = LoggerFactory.getLogger(MigrationInitializer.class);
    
    private final MigrationExecutor migrationExecutor;
    private final List<Migration> migrations;
    
    /**
     * Creates a new MigrationInitializer
     * 
     * @param migrationExecutor the executor used to run migrations
     * @param migrations the list of migrations to apply, or empty if no migrations
     */
    public MigrationInitializer(MigrationExecutor migrationExecutor, List<Migration> migrations) {
        this.migrationExecutor = migrationExecutor;
        this.migrations = migrations;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        if (migrations == null || migrations.isEmpty()) {
            log.info("No migrations to apply");
            return;
        }
        
        log.info("Applying {} migrations", migrations.size());
        
        try {
            // First ensure the migration index exists
            boolean indexCreated = migrationExecutor.ensureMigrationIndexExists().get();
            if (indexCreated) {
                log.info("Created migration history index");
            }
            
            // Apply the migrations
            migrationExecutor.executeSystemMigrations(migrations).get();
            log.info("Successfully applied all migrations");
        } catch (Exception ex) {
            log.error("Failed to apply migrations", ex);
            throw new RuntimeException("Failed to apply migrations", ex);
        }
    }
    
    @Override
    public int getOrder() {
        return 0; // Same order value as FlywayMigrationInitializer
    }
}