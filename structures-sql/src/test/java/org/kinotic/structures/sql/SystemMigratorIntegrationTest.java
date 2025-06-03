package org.kinotic.structures.sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.kinotic.structures.sql.executor.MigrationExecutor;
import org.kinotic.structures.sql.parsers.MigrationParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.cluster.GetComponentTemplateResponse;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexTemplateResponse;
import co.elastic.clients.elasticsearch.indices.GetMappingResponse;

@SpringBootTest
class SystemMigratorIntegrationTest extends ElasticsearchSqlTestBase {

    @Autowired
    private ElasticsearchAsyncClient asyncClient;

    @Autowired
    private ElasticsearchClient client;

    @Autowired
    private MigrationExecutor migrationExecutor;

    @Autowired
    private MigrationParser migrationParser;


    @Test
    void whenNoMigrations_thenNoMigrationsApplied() throws Exception {
        // Given
        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver();
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

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

        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver(
            migrationContent,
            "V1__create_test_table.sql"
        );
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

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
        assertEquals("Text", properties.get("id")._kind().name());
        assertEquals("Text", properties.get("name")._kind().name());
        assertEquals("Integer", properties.get("age")._kind().name());
        assertEquals("Boolean", properties.get("active")._kind().name());
        assertEquals("Date", properties.get("created_at")._kind().name());
    }

    // @Test
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

        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver(
            new TestResourceUtils.MigrationContent(componentTemplateContent, "V1__create_component_template.sql"),
            new TestResourceUtils.MigrationContent(indexTemplateContent, "V2__create_index_template.sql")
        );
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

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

        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver(
            new TestResourceUtils.MigrationContent(createTableContent, "V1__create_test_table.sql"),
            new TestResourceUtils.MigrationContent(alterTableContent, "V2__add_age_column.sql")
        );
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        GetMappingResponse mapping = client.indices().getMapping(m -> m.index("test_table_alter"));
        var indexMapping = mapping.get("test_table_alter");
        assertNotNull(indexMapping, "Mapping for test_table_alter should exist");
        Map<String, Property> properties = indexMapping.mappings().properties();
        assertNotNull(properties.get("age"), "Property 'age' should exist");
        assertEquals("Integer", properties.get("age")._kind().name());
    }

    // @Test
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
            INSERT INTO test_table_reindex_source (id, name) VALUES ('1', 'test') WITH REFRESH;
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

        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver(
            new TestResourceUtils.MigrationContent(createSourceContent, "V1__create_source_table.sql"),
            new TestResourceUtils.MigrationContent(createDestContent, "V2__create_dest_table.sql"),
            new TestResourceUtils.MigrationContent(insertContent, "V3__insert_test_data.sql"),
            new TestResourceUtils.MigrationContent(reindexContent, "V4__reindex_data.sql")
        );
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        CountResponse count = client.count(c -> c.index("test_table_reindex_dest"));
        assertEquals(1, count.count());
    }

    @SuppressWarnings("null")
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
            INSERT INTO test_table_update (id, name, age) VALUES ('1', 'test', 20) WITH REFRESH;
            """;
        String updateContent = """
            UPDATE test_table_update SET age == 21 WHERE id == '1' WITH REFRESH;
            """;

        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver(
            new TestResourceUtils.MigrationContent(createTableContent, "V1__create_test_table.sql"),
            new TestResourceUtils.MigrationContent(insertContent, "V2__insert_test_data.sql"),
            new TestResourceUtils.MigrationContent(updateContent, "V3__update_age.sql")
        );
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        @SuppressWarnings("rawtypes")
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
            INSERT INTO test_table_delete (id, name) VALUES ('1', 'test') WITH REFRESH;
            """;
        String deleteContent = """
            DELETE FROM test_table_delete WHERE id == '1' WITH REFRESH;
            """;

        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver(
            new TestResourceUtils.MigrationContent(createTableContent, "V1__create_test_table.sql"),
            new TestResourceUtils.MigrationContent(insertContent, "V2__insert_test_data.sql"),
            new TestResourceUtils.MigrationContent(deleteContent, "V3__delete_data.sql")
        );
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

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

        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver(
            migrationContent,
            "Vinvalid__create_test_table.sql"
        );
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

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

        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver(
            new TestResourceUtils.MigrationContent(migration1Content, "V1__create_test_table1.sql"),
            new TestResourceUtils.MigrationContent(migration2Content, "V1__create_test_table2.sql")
        );
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When/Then
        assertThrows(IllegalStateException.class, () -> 
            systemMigrator.onApplicationEvent(null)
        );
    }

    @Test
    void whenCreateTableIfNotExists_thenNoErrorOnSecondCreate() throws Exception {
        // Given
        String createTableContent = """
            CREATE TABLE IF NOT EXISTS test_table_if_not_exists (
                id TEXT,
                name TEXT
            );
            """;

        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver(
            new TestResourceUtils.MigrationContent(createTableContent, "V1__create_test_table.sql"),
            new TestResourceUtils.MigrationContent(createTableContent, "V2__create_test_table_again.sql")
        );
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        assertTrue(asyncClient.indices().exists(e -> e.index("test_table_if_not_exists")).get().value());
        
        // Verify mapping
        GetMappingResponse mapping = client.indices().getMapping(m -> m.index("test_table_if_not_exists"));
        var indexMapping = mapping.get("test_table_if_not_exists");
        assertNotNull(indexMapping, "Mapping for test_table_if_not_exists should exist");
        Map<String, Property> properties = indexMapping.mappings().properties();
        
        assertNotNull(properties.get("id"), "Property 'id' should exist");
        assertEquals("Text", properties.get("id")._kind().name());
        assertEquals("Text", properties.get("name")._kind().name());
    }

    @Test
    void whenCreateTableWithNumericTypes_thenTableCreated() throws Exception {
        // Given
        String migrationContent = """
            CREATE TABLE test_table_numeric (
                id TEXT,
                int_field INTEGER,
                long_field LONG,
                float_field FLOAT,
                double_field DOUBLE
            );
            """;

        PathMatchingResourcePatternResolver resourceLoader = TestResourceUtils.createResourceResolver(
            migrationContent,
            "V1__create_numeric_table.sql"
        );
        SystemMigrator systemMigrator = new SystemMigrator(migrationExecutor, migrationParser, resourceLoader);

        // When
        systemMigrator.onApplicationEvent(null);

        // Then
        assertTrue(asyncClient.indices().exists(e -> e.index("test_table_numeric")).get().value());
        
        // Verify mapping
        GetMappingResponse mapping = client.indices().getMapping(m -> m.index("test_table_numeric"));
        var indexMapping = mapping.get("test_table_numeric");
        assertNotNull(indexMapping, "Mapping for test_table_numeric should exist");
        Map<String, Property> properties = indexMapping.mappings().properties();
        
        assertNotNull(properties.get("id"), "Property 'id' should exist");
        assertEquals("Text", properties.get("id")._kind().name());
        assertEquals("Integer", properties.get("int_field")._kind().name());
        assertEquals("Long", properties.get("long_field")._kind().name());
        assertEquals("Float", properties.get("float_field")._kind().name());
        assertEquals("Double", properties.get("double_field")._kind().name());
    }
} 