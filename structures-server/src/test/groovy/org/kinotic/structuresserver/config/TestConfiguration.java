package org.kinotic.structuresserver.config;

import org.kinotic.continuum.api.annotations.EnableContinuum;
import org.kinotic.continuum.gateway.api.annotations.EnableContinuumGateway;
import org.kinotic.structures.api.annotations.EnableStructures;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableContinuum
@EnableStructures
@EnableContinuumGateway
public class TestConfiguration {
    public static final ElasticsearchTestContainer ELASTICSEARCH_CONTAINER;
    static {
        ELASTICSEARCH_CONTAINER = ElasticsearchTestContainer.create();
        ELASTICSEARCH_CONTAINER.start();
    }
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.data.elasticsearch.cluster-nodes=" + ELASTICSEARCH_CONTAINER.getHttpHostAddress())
                    .applyTo(configurableApplicationContext.getEnvironment());
            TestPropertyValues.of("structures.elastic-uris=" + ELASTICSEARCH_CONTAINER.getHttpHostAddress())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
