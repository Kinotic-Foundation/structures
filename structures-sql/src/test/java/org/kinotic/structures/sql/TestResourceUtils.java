package org.kinotic.structures.sql;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Utility class for creating test resources in SQL migration tests.
 */
public class TestResourceUtils {

    /**
     * Creates a PathMatchingResourcePatternResolver that returns the given migration contents.
     * Each migration content will be wrapped in a Resource with a corresponding filename.
     *
     * @param migrations List of migration contents and their filenames
     * @return A PathMatchingResourcePatternResolver that returns the mocked resources
     */
    public static PathMatchingResourcePatternResolver createResourceResolver(List<MigrationContent> migrations) {
        return new PathMatchingResourcePatternResolver() {
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                return migrations.stream()
                    .map(migration -> {
                        Resource resource = mock(Resource.class);
                        try {
                            when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(migration.content().getBytes()));
                            when(resource.getFilename()).thenReturn(migration.filename());
                            when(resource.getURI()).thenReturn(URI.create("file:///test/" + migration.filename()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return resource;
                    })
                    .toArray(Resource[]::new);
            }
        };
    }

    /**
     * Creates a PathMatchingResourcePatternResolver that returns a single migration.
     *
     * @param content The migration content
     * @param filename The filename for the migration
     * @return A PathMatchingResourcePatternResolver that returns the mocked resource
     */
    public static PathMatchingResourcePatternResolver createResourceResolver(String content, String filename) {
        return createResourceResolver(List.of(new MigrationContent(content, filename)));
    }

    /**
     * Creates a PathMatchingResourcePatternResolver that returns multiple migrations.
     *
     * @param migrations Array of migration contents and their filenames
     * @return A PathMatchingResourcePatternResolver that returns the mocked resources
     */
    public static PathMatchingResourcePatternResolver createResourceResolver(MigrationContent... migrations) {
        return createResourceResolver(Arrays.asList(migrations));
    }

    /**
     * Record to hold migration content and filename.
     */
    public record MigrationContent(String content, String filename) {}
} 