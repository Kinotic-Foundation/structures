package org.kinotic.structuresserver;

import org.kinotic.continuum.api.annotations.EnableContinuum;
import org.kinotic.structures.api.annotations.EnableStructures;
import org.kinotic.structuresserver.config.ElasticsearchTestContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ReactiveElasticsearchClientAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = ElasticsearchTestContextInitializer.class)
@EnableAutoConfiguration(exclude = {HazelcastAutoConfiguration.class,
                                    JpaRepositoriesAutoConfiguration.class,
                                    ReactiveElasticsearchClientAutoConfiguration.class})
public class StructuresServerTestApplication {
    public static void main(String[] args) {
		SpringApplication.run(StructuresServerTestApplication.class, args);
	}

}
