package org.kinotic.structuresserver.tests.config;

import org.junit.jupiter.api.Test;
import org.kinotic.structuresserver.config.ElasticsearchTestConfiguration;
import org.kinotic.structuresserver.ElasticTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestConfigurationTest extends ElasticTestBase {
    
    private static final Logger log = LoggerFactory.getLogger(TestConfigurationTest.class);

    @Test
    public void testTestContainersStarted() {
        // Verify that TestContainers are running
        assertNotNull(ElasticsearchTestConfiguration.ELASTICSEARCH_CONTAINER);
        assertTrue(ElasticsearchTestConfiguration.areContainersRunning());
        assertTrue(ElasticsearchTestConfiguration.areContainersReady());
    }

    @Test
    public void testElasticsearchUrlGenerated() {
        // Verify that Elasticsearch URL is generated correctly
        String elasticsearchUrl = ElasticsearchTestConfiguration.getElasticsearchUrl();
        assertNotNull(elasticsearchUrl);
        assertTrue(elasticsearchUrl.startsWith("http://"));
        assertTrue(elasticsearchUrl.contains(":"));
    }

    @Test
    public void testElasticsearchServiceAccessible() {
        // Verify that Elasticsearch service is accessible
        String host = ElasticsearchTestConfiguration.ELASTICSEARCH_CONTAINER.getHost();
        Integer port = ElasticsearchTestConfiguration.ELASTICSEARCH_CONTAINER.getMappedPort(9200);
        
        assertNotNull(host);
        assertNotNull(port);
        assertTrue(port > 0);
    }
    
    @Test
    public void testContainersAreRunning() {
        // Containers should already be ready from TestBase.setUp()
        assertTrue(ElasticsearchTestConfiguration.areContainersRunning(), "Containers should be running");
        assertTrue(ElasticsearchTestConfiguration.areContainersReady(), "Containers should be ready");
    }
    
    @Test
    public void testElasticsearchUrl() {
        String url = ElasticsearchTestConfiguration.getElasticsearchUrl();
        assertNotNull(url);
        assertTrue(url.startsWith("http://"));
        assertTrue(url.contains(":"));
    }
    
    @Test
    public void testContainersAreHealthy() {
        // Containers should already be healthy from TestBase.setUp()
        assertTrue(ElasticsearchTestConfiguration.areContainersHealthy(), "Containers should be healthy");
    }
    
    @Test
    public void testContainerStatus() {
        String status = ElasticsearchTestConfiguration.getContainerStatus();
        assertNotNull(status);
        assertTrue(status.contains("Elasticsearch"));
        assertTrue(status.contains("Containers Ready: true"));
    }
    
    @Test
    public void testElasticsearchHealthCheck() {
        // Test that Elasticsearch health check works
        boolean isHealthy = ContainerHealthChecker.isElasticsearchHealthy(
            ElasticsearchTestConfiguration.ELASTICSEARCH_CONTAINER.getHost(),
            ElasticsearchTestConfiguration.ELASTICSEARCH_CONTAINER.getMappedPort(9200)
        );
        assertTrue(isHealthy, "Elasticsearch should be healthy");
    }
    
}
