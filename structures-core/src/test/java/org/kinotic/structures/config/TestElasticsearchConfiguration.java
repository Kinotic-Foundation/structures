package org.kinotic.structures.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "org.kinotic.structures.internal.repositories")
@ComponentScan(basePackages = "org.kinotic.structures")
@Profile("test")
public class TestElasticsearchConfiguration {
    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
    public static final ElasticsearchTestContainer ELASTICSEARCH_CONTAINER;
    static {
        ELASTICSEARCH_CONTAINER = ElasticsearchTestContainer.create();
        ELASTICSEARCH_CONTAINER.start();
    }
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder
                = ClientConfiguration.builder()
                .connectedTo(ELASTICSEARCH_CONTAINER.getHttpHostAddress());

        builder.withConnectTimeout(60000)
                .withSocketTimeout(60000);

        return RestClients.create(builder.build()).rest();
    }
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.data.elasticsearch.cluster-nodes=" + ELASTICSEARCH_CONTAINER.getHttpHostAddress())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
