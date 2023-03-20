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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "org.kinotic.structures.internal.repositories")
@ComponentScan(basePackages = "org.kinotic.structures")
@Profile("!test")
public class StructuresConfiguration extends AbstractElasticsearchConfiguration implements InitializingBean {
    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Value("${elastic.hosts}")
    private String hosts;

    @Value("${elastic.useSSL}")
    private boolean useSSL;

    @Value("${elastic.user}")
    private String user;

    @Value("${elastic.password}")
    private String password;

    @Override
    public void afterPropertiesSet() throws Exception {
        // LOOK: use of InitBean and Static block is outlined here -> https://github.com/elastic/elasticsearch/issues/25741
    }

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder
                = ClientConfiguration.builder()
                                     .connectedTo(hosts.split(","));

        if(useSSL){
            builder.usingSsl();
        }

        if(user != null && !user.isEmpty()){
            builder.withBasicAuth(user, password);
        }

        builder.withConnectTimeout(60000)
                .withSocketTimeout(60000);

        return RestClients.create(builder.build()).rest();
    }
}
