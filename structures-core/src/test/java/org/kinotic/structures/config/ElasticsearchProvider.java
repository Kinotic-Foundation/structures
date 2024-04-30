package org.kinotic.structures.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class ElasticsearchProvider {
//    public static final ElasticsearchTestContainer ELASTICSEARCH_CONTAINER;
//
//    static {
//        ELASTICSEARCH_CONTAINER = ElasticsearchTestContainer.create();
//        ELASTICSEARCH_CONTAINER.start();
//    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
//            TestPropertyValues.of("spring.data.elasticsearch.cluster-nodes=" + ELASTICSEARCH_CONTAINER.getHttpHostAddress())
//                              .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
