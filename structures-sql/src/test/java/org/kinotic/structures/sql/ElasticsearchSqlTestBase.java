package org.kinotic.structures.sql;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.kinotic.structures.sql.executor.MigrationExecutor;
import java.util.UUID;

@SpringBootTest
public abstract class ElasticsearchSqlTestBase {

    public static final ElasticsearchContainer ELASTICSEARCH_CONTAINER;
    private static final String MIGRATION_INDEX_PREFIX = "migration_history_";

    static {
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");

        ELASTICSEARCH_CONTAINER = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.17.3");
        ELASTICSEARCH_CONTAINER.withEnv("discovery.type", "single-node")
                               .withEnv("xpack.security.enabled", "false");

        // We need this until this is resolved https://github.com/elastic/elasticsearch/issues/118583
        if(osName != null && osName.startsWith("Mac") && osArch != null && osArch.equals("aarch64")){
            ELASTICSEARCH_CONTAINER.withEnv("_JAVA_OPTIONS", "-XX:UseSVE=0");
        }

        ELASTICSEARCH_CONTAINER.start();
    }

    @Autowired
    protected ElasticsearchAsyncClient asyncClient;

    @Autowired
    protected MigrationExecutor migrationExecutor;

    protected String migrationIndex;

    @BeforeEach
    void setUp() throws Exception {
        // Create a unique migration index for this test
        migrationIndex = MIGRATION_INDEX_PREFIX + UUID.randomUUID();
        
        // Create the index with proper mappings
        asyncClient.indices().create(c -> c
            .index(migrationIndex)
            .mappings(m -> m
                .properties("version", p -> p.keyword(k -> k))
                .properties("projectId", p -> p.keyword(k -> k))
                .properties("appliedAt", p -> p.date(d -> d))
            )
        ).get();

        // Set the migration index on the executor
        migrationExecutor.setMigrationIndex(migrationIndex);
    }

    @DynamicPropertySource
    static void registerElasticProperties(DynamicPropertyRegistry registry) {
        String[] parts = ELASTICSEARCH_CONTAINER.getHttpHostAddress().split(":");
        registry.add("elasticsearch.test.hostname", () -> parts[0]);
        registry.add("elasticsearch.test.port", () -> Integer.parseInt(parts[1]));
    }
} 