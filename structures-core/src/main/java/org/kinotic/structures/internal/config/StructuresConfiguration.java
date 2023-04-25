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

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
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

import java.util.concurrent.atomic.AtomicBoolean;

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

    @Bean(destroyMethod = "close")
    @Override
    public RestHighLevelClient elasticsearchClient() {
        AtomicBoolean useSsl = new AtomicBoolean(false);
        String[] uris = structuresProperties.getElasticConnections()
                .stream()
                .peek(v -> {
                    if(v.getScheme().equalsIgnoreCase("https")){
                        useSsl.set(true);
                     }
                })
                .map(ElasticConnectionInfo::toHostAndPort)
                .toArray(String[]::new);

        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder
                = ClientConfiguration.builder()
                                     .connectedTo(uris);

        if(useSsl.get()){
            builder.usingSsl();
        }

        if(structuresProperties.hasElasticUsernameAndPassword()){
            builder.withBasicAuth(structuresProperties.getElasticUsername(), structuresProperties.getElasticPassword());
        }

        builder.withConnectTimeout(structuresProperties.getElasticConnectionTimeout())
                .withSocketTimeout(structuresProperties.getElasticSocketTimeout());

        return RestClients.create(builder.build()).rest();
    }

    @Bean
    public ElasticsearchAsyncClient elasticsearchAsyncClient(ObjectMapper objectMapper){

        HttpHost[] hosts = structuresProperties.getElasticConnections()
                                            .stream()
                                            .map(v -> new HttpHost(v.getHost(), v.getPort(), v.getScheme()))
                                            .toArray(HttpHost[]::new);

        RestClientBuilder builder = RestClient.builder(hosts);

        if(structuresProperties.hasElasticUsernameAndPassword()){
            builder.setHttpClientConfigCallback(httpClientBuilder -> {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY,
                                                   new UsernamePasswordCredentials(structuresProperties.getElasticUsername(),
                                                                                   structuresProperties.getElasticPassword()));

                return httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider);
            });
        }

        RestClient restClient = builder.build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper(objectMapper));

        return new ElasticsearchAsyncClient(transport);
    }

}
