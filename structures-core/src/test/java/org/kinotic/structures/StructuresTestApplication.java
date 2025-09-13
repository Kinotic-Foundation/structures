

package org.kinotic.structures;

import org.kinotic.continuum.api.annotations.EnableContinuum;
import org.kinotic.structures.api.annotations.EnableStructures;
import org.springframework.ai.model.openai.autoconfigure.OpenAiChatAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ReactiveElasticsearchClientAutoConfiguration;
import org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration;
import org.springframework.boot.autoconfigure.graphql.reactive.GraphQlWebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication(exclude = {HazelcastAutoConfiguration.class,
                                  JpaRepositoriesAutoConfiguration.class,
                                  GraphQlAutoConfiguration.class,
                                  GraphQlWebFluxAutoConfiguration.class,
                                  ReactiveElasticsearchClientAutoConfiguration.class,
                                  OpenAiChatAutoConfiguration.class})
@EnableContinuum
@EnableStructures
@EnableConfigurationProperties
@ActiveProfiles("test")
public class StructuresTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(StructuresTestApplication.class, args);
    }
}
