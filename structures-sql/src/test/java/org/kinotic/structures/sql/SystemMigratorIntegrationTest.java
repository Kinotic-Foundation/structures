package org.kinotic.structures.sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kinotic.structures.sql.executor.MigrationExecutor;
import org.kinotic.structures.sql.parsers.MigrationParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.cluster.GetComponentTemplateResponse;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexTemplateResponse;
import co.elastic.clients.elasticsearch.indices.GetMappingResponse;

class SystemMigratorIntegrationTest extends ElasticsearchSqlTestBase {

    @Autowired
    private MigrationExecutor migrationExecutor;

    @Autowired
    private MigrationParser migrationParser;

    private SystemMigrator systemMigrator;
    private PathMatchingResourcePatternResolver resourceLoader;

    @BeforeEach
    void init() {
        resourceLoader = new PathMatchingResourcePatternResolver();
        systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);
    }

    @Test
    void whenNoMigrations_thenNoMigrationsApplied() throws Exception {
        // Given
        resourceLoader = new PathMatchingResourcePatternResolver() {
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                return new Resource[0];
            }
        };
        systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        // No exception should be thrown
    }

    @Test
    void whenCreateTableMigration_thenTableCreated() throws Exception {
        // Given
        String migrationContent = """
            CREATE TABLE test_table_create (
                id TEXT,
                name TEXT,
                age INTEGER,
                active BOOLEAN,
                created_at DATE
            );
            """;
        
        resourceLoader = new PathMatchingResourcePatternResolver() {
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                Resource resource = mock(Resource.class);
                when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(migrationContent.getBytes()));
                when(resource.getFilename()).thenReturn("V1__create_test_table.sql");
                return new Resource[]{resource};
            }
        };
        systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        assertTrue(asyncClient.indices().exists(e -> e.index("test_table_create")).get().value());
        
        // Verify mapping
        GetMappingResponse mapping = client.indices().getMapping(m -> m.index("test_table_create"));
        var indexMapping = mapping.get("test_table_create");
        assertNotNull(indexMapping, "Mapping for test_table_create should exist");
        Map<String, Property> properties = indexMapping.mappings().properties();
        
        assertNotNull(properties.get("id"), "Property 'id' should exist");
        assertEquals("text", properties.get("id")._kind().name());
        assertEquals("text", properties.get("name")._kind().name());
        assertEquals("integer", properties.get("age")._kind().name());
        assertEquals("boolean", properties.get("active")._kind().name());
        assertEquals("date", properties.get("created_at")._kind().name());
    }

    @Test
    void whenCreateIndexTemplate_thenTemplateCreated() throws Exception {
        // Given
        String componentTemplateContent = """
            CREATE COMPONENT TEMPLATE test_template_index (
                NUMBER_OF_SHARDS == 3,
                NUMBER_OF_REPLICAS == 1,
                id KEYWORD,
                name TEXT
            );
            """;
        String indexTemplateContent = """
            CREATE INDEX TEMPLATE test_index_template_index FOR "test-index-*" USING test_template_index WITH (
                NUMBER_OF_SHARDS == 2,
                NUMBER_OF_REPLICAS == 1,
                status TEXT
            );
            """;
        
        resourceLoader = new PathMatchingResourcePatternResolver() {
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                Resource resource1 = mock(Resource.class);
                when(resource1.getInputStream()).thenReturn(new ByteArrayInputStream(componentTemplateContent.getBytes()));
                when(resource1.getFilename()).thenReturn("V1__create_component_template.sql");

                Resource resource2 = mock(Resource.class);
                when(resource2.getInputStream()).thenReturn(new ByteArrayInputStream(indexTemplateContent.getBytes()));
                when(resource2.getFilename()).thenReturn("V2__create_index_template.sql");

                return new Resource[]{resource1, resource2};
            }
        };
        systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        GetComponentTemplateResponse componentTemplate = client.cluster().getComponentTemplate(g -> g.name("test_template_index"));
        assertTrue(componentTemplate.componentTemplates().stream().anyMatch(t -> t.name().equals("test_template_index")));

        GetIndexTemplateResponse indexTemplate = client.indices().getIndexTemplate(g -> g.name("test_index_template_index"));
        assertTrue(indexTemplate.indexTemplates().stream().anyMatch(t -> t.name().equals("test_index_template_index")));
    }

    @Test
    void whenAlterTable_thenColumnAdded() throws Exception {
        // Given
        String createTableContent = """
            CREATE TABLE test_table_alter (
                id TEXT,
                name TEXT
            );
            """;
        String alterTableContent = """
            ALTER TABLE test_table_alter ADD COLUMN age INTEGER;
            """;
        
        resourceLoader = new PathMatchingResourcePatternResolver() {
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                Resource resource1 = mock(Resource.class);
                when(resource1.getInputStream()).thenReturn(new ByteArrayInputStream(createTableContent.getBytes()));
                when(resource1.getFilename()).thenReturn("V1__create_test_table.sql");

                Resource resource2 = mock(Resource.class);
                when(resource2.getInputStream()).thenReturn(new ByteArrayInputStream(alterTableContent.getBytes()));
                when(resource2.getFilename()).thenReturn("V2__add_age_column.sql");

                return new Resource[]{resource1, resource2};
            }
        };
        systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        GetMappingResponse mapping = client.indices().getMapping(m -> m.index("test_table_alter"));
        var indexMapping = mapping.get("test_table_alter");
        assertNotNull(indexMapping, "Mapping for test_table_alter should exist");
        Map<String, Property> properties = indexMapping.mappings().properties();
        assertNotNull(properties.get("age"), "Property 'age' should exist");
        assertEquals("integer", properties.get("age")._kind().name());
    }

    @Test
    void whenReindex_thenDataReindexed() throws Exception {
        // Given
        String createSourceContent = """
            CREATE TABLE test_table_reindex_source (
                id TEXT,
                name TEXT
            );
            """;
        String createDestContent = """
            CREATE TABLE test_table_reindex_dest (
                id TEXT,
                name TEXT
            );
            """;
        String insertContent = """
            INSERT INTO test_table_reindex_source (id, name) VALUES ('1', 'test');
            """;
        String reindexContent = """
            REINDEX test_table_reindex_source INTO test_table_reindex_dest WITH (
                CONFLICTS == PROCEED,
                MAX_DOCS == 100,
                SLICES == AUTO,
                SIZE == 1000,
                SOURCE_FIELDS == 'id,name',
                QUERY == 'name:test'
            );
            """;
        
        resourceLoader = new PathMatchingResourcePatternResolver() {
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                Resource resource1 = mock(Resource.class);
                when(resource1.getInputStream()).thenReturn(new ByteArrayInputStream(createSourceContent.getBytes()));
                when(resource1.getFilename()).thenReturn("V1__create_source_table.sql");

                Resource resource2 = mock(Resource.class);
                when(resource2.getInputStream()).thenReturn(new ByteArrayInputStream(createDestContent.getBytes()));
                when(resource2.getFilename()).thenReturn("V2__create_dest_table.sql");

                Resource resource3 = mock(Resource.class);
                when(resource3.getInputStream()).thenReturn(new ByteArrayInputStream(insertContent.getBytes()));
                when(resource3.getFilename()).thenReturn("V3__insert_test_data.sql");

                Resource resource4 = mock(Resource.class);
                when(resource4.getInputStream()).thenReturn(new ByteArrayInputStream(reindexContent.getBytes()));
                when(resource4.getFilename()).thenReturn("V4__reindex_data.sql");

                return new Resource[]{resource1, resource2, resource3, resource4};
            }
        };
        systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        CountResponse count = client.count(c -> c.index("test_table_reindex_dest"));
        assertEquals(1, count.count());
    }

    @Test
    void whenUpdate_thenDataUpdated() throws Exception {
        // Given
        String createTableContent = """
            CREATE TABLE test_table_update (
                id TEXT,
                name TEXT,
                age INTEGER
            );
            """;
        String insertContent = """
            INSERT INTO test_table_update (id, name, age) VALUES ('1', 'test', 20);
            """;
        String updateContent = """
            UPDATE test_table_update SET age == 21 WHERE id == '1';
            """;
        
        resourceLoader = new PathMatchingResourcePatternResolver() {
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                Resource resource1 = mock(Resource.class);
                when(resource1.getInputStream()).thenReturn(new ByteArrayInputStream(createTableContent.getBytes()));
                when(resource1.getFilename()).thenReturn("V1__create_test_table.sql");

                Resource resource2 = mock(Resource.class);
                when(resource2.getInputStream()).thenReturn(new ByteArrayInputStream(insertContent.getBytes()));
                when(resource2.getFilename()).thenReturn("V2__insert_test_data.sql");

                Resource resource3 = mock(Resource.class);
                when(resource3.getInputStream()).thenReturn(new ByteArrayInputStream(updateContent.getBytes()));
                when(resource3.getFilename()).thenReturn("V3__update_age.sql");

                return new Resource[]{resource1, resource2, resource3};
            }
        };
        systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        SearchResponse<Map> response = client.search(s -> s
            .index("test_table_update")
            .query(q -> q.term(t -> t.field("id").value("1"))),
            Map.class
        );
        assertEquals(21, response.hits().hits().get(0).source().get("age"));
    }

    @Test
    void whenDelete_thenDataDeleted() throws Exception {
        // Given
        String createTableContent = """
            CREATE TABLE test_table_delete (
                id TEXT,
                name TEXT
            );
            """;
        String insertContent = """
            INSERT INTO test_table_delete (id, name) VALUES ('1', 'test');
            """;
        String deleteContent = """
            DELETE FROM test_table_delete WHERE id == '1';
            """;
        
        resourceLoader = new PathMatchingResourcePatternResolver() {
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                Resource resource1 = mock(Resource.class);
                when(resource1.getInputStream()).thenReturn(new ByteArrayInputStream(createTableContent.getBytes()));
                when(resource1.getFilename()).thenReturn("V1__create_test_table.sql");

                Resource resource2 = mock(Resource.class);
                when(resource2.getInputStream()).thenReturn(new ByteArrayInputStream(insertContent.getBytes()));
                when(resource2.getFilename()).thenReturn("V2__insert_test_data.sql");

                Resource resource3 = mock(Resource.class);
                when(resource3.getInputStream()).thenReturn(new ByteArrayInputStream(deleteContent.getBytes()));
                when(resource3.getFilename()).thenReturn("V3__delete_data.sql");

                return new Resource[]{resource1, resource2, resource3};
            }
        };
        systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        CountResponse count = client.count(c -> c.index("test_table_delete"));
        assertEquals(0, count.count());
    }

    @Test
    void whenInvalidVersionNumber_thenExceptionThrown() throws Exception {
        // Given
        String migrationContent = """
            CREATE TABLE test_table (
                id TEXT,
                name TEXT
            );
            """;
        
        resourceLoader = new PathMatchingResourcePatternResolver() {
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                Resource resource = mock(Resource.class);
                when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(migrationContent.getBytes()));
                when(resource.getFilename()).thenReturn("Vinvalid__create_test_table.sql");
                return new Resource[]{resource};
            }
        };
        systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When/Then
        assertThrows(RuntimeException.class, () -> 
            systemMigrator.onApplicationEvent(null)
        );
    }

    @Test
    void whenDuplicateVersionNumber_thenExceptionThrown() throws Exception {
        // Given
        String migration1Content = """
            CREATE TABLE test_table1 (
                id TEXT,
                name TEXT
            );
            """;
        String migration2Content = """
            CREATE TABLE test_table2 (
                id TEXT,
                name TEXT
            );
            """;
        
        resourceLoader = new PathMatchingResourcePatternResolver() {
            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                Resource resource1 = mock(Resource.class);
                when(resource1.getInputStream()).thenReturn(new ByteArrayInputStream(migration1Content.getBytes()));
                when(resource1.getFilename()).thenReturn("V1__create_test_table1.sql");

                Resource resource2 = mock(Resource.class);
                when(resource2.getInputStream()).thenReturn(new ByteArrayInputStream(migration2Content.getBytes()));
                when(resource2.getFilename()).thenReturn("V1__create_test_table2.sql");

                return new Resource[]{resource1, resource2};
            }
        };
        systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When/Then
        assertThrows(RuntimeException.class, () -> 
            systemMigrator.onApplicationEvent(null)
        );
    }
} 