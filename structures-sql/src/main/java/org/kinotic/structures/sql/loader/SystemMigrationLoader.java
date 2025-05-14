package org.kinotic.structures.sql.loader;

import org.kinotic.structures.sql.domain.Migration;
import org.kinotic.structures.sql.parser.MigrationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Loads system migrations from the filesystem.
 * 
 * Created by Navíd Mitchell 🤪🤝Grok on 5/14/25.
 */
public class SystemMigrationLoader {
    private static final Logger log = LoggerFactory.getLogger(SystemMigrationLoader.class);
    private static final String MIGRATIONS_PATH = "classpath:migrations/system/*.sql";
    
    private final ResourceLoader resourceLoader;
    private final MigrationParser migrationParser;
    
    public SystemMigrationLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.migrationParser = new MigrationParser(); // TODO: Replace with injected parser when available
    }

    /**
     * Loads system migrations from migrations/system/*.sql
     */
    public List<Migration> loadSystemMigrations() {
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
                } catch (Exception e) {
                    log.warn("Failed to parse migration file: {}", resource.getFilename(), e);
                }
            }
            
        } catch (Exception e) {
            log.error("Error loading system migrations", e);
        }
        
        return allMigrations;
    }
}