package org.kinotic.structures.sql;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@SpringBootTest
public abstract class ElasticsearchSqlTestBase {

    public static final ElasticsearchContainer ELASTICSEARCH_CONTAINER;
    protected static ElasticsearchClient client;
    protected static ElasticsearchAsyncClient asyncClient;

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

    @BeforeAll
    static void setUp() {
        // Create the low-level client
        RestClient restClient = RestClient.builder(
            new HttpHost(ELASTICSEARCH_CONTAINER.getHost(), ELASTICSEARCH_CONTAINER.getMappedPort(9200), "http"))
            .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper());

        // Create the API client
        client = new ElasticsearchClient(transport);
        asyncClient = new ElasticsearchAsyncClient(transport);
    }
} 