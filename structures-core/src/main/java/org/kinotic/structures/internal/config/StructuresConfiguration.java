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

package org.kinotic.structures.internal.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableConfigurationProperties
@EnableElasticsearchRepositories(basePackages = "org.kinotic.structures.internal.repositories")
@ComponentScan(basePackages = "org.kinotic.structures")
@Profile("!test")
public class StructuresConfiguration extends AbstractElasticsearchConfiguration {

    private StructuresProperties structuresProperties;

    public StructuresConfiguration(StructuresProperties structuresProperties){
        this.structuresProperties = structuresProperties;
    }

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder
                = ClientConfiguration.builder()
                                     .connectedTo(structuresProperties.getElasticUris().split(","));

        if(structuresProperties.isElasticUseSsl()){
            builder.usingSsl();
        }

        if(structuresProperties.getElasticUsername() != null && !structuresProperties.getElasticUsername().isBlank()){
            builder.withBasicAuth(structuresProperties.getElasticUsername(), structuresProperties.getElasticPassword());
        }

        builder.withConnectTimeout(structuresProperties.getElasticConnectionTimeout())
                .withSocketTimeout(structuresProperties.getElasticSocketTimeout());

        return RestClients.create(builder.build()).rest();
    }
}
