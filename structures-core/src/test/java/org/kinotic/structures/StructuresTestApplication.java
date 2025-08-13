/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
