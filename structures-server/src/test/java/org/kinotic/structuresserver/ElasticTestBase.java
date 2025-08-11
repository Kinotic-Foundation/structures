package org.kinotic.structuresserver;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.kinotic.structuresserver.config.ElasticsearchTestContextInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@ContextConfiguration(initializers = ElasticsearchTestContextInitializer.class)
public abstract class ElasticTestBase {
    
    private static final Logger log = LoggerFactory.getLogger(ElasticTestBase.class);
    

    // @BeforeAll
    // public void setUp(){
    //     log.info("Setting up test environment...");
        
    //     // The TestContextInitializer should have already started containers and set them as ready
    //     // Just verify they are running and healthy
    //     log.info("Verifying TestContainers are ready and healthy...");
        
    //     // Verify containers are running and ready
    //     assertNotNull("Elasticsearch container should be available", TestConfiguration.ELASTICSEARCH_CONTAINER);
    //     assertNotNull("Keycloak container should be available", TestConfiguration.KEYCLOAK_CONTAINER);
        
    //     assertTrue("Containers should be running", TestConfiguration.areContainersRunning());
    //     assertTrue("Containers should be ready", TestConfiguration.areContainersReady());
    //     assertTrue("Containers should be healthy", TestConfiguration.areContainersHealthy());
        
    //     log.info("Test environment setup complete. Container status: {}", TestConfiguration.getContainerStatus());
    // }

    // @AfterAll
    // public void tearDown() {
    //     log.info("Tearing down test environment...");
    //     TestConfiguration.shutdownContainers();
    //     log.info("Test environment teardown complete.");
    // }
}
