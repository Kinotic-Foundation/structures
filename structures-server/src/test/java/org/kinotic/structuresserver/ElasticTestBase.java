package org.kinotic.structuresserver;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.kinotic.structures.api.annotations.EnableStructures;
import org.kinotic.structuresserver.config.ElasticsearchTestContextInitializer;

@ActiveProfiles("test")
@ContextConfiguration(initializers = ElasticsearchTestContextInitializer.class)
@SpringBootTest
@EnableStructures
public abstract class ElasticTestBase {
    
}
