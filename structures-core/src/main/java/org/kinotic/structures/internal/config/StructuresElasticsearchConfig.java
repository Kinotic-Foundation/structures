package org.kinotic.structures.internal.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.json.JsonpMapper;
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
import org.kinotic.structures.api.config.StructuresProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Navíd Mitchell 🤪 on 4/26/23.
 */
@Configuration
@Profile("!test")
public class StructuresElasticsearchConfig extends ReactiveElasticsearchConfiguration {

    private final StructuresProperties structuresProperties;

    public StructuresElasticsearchConfig(StructuresProperties structuresProperties) {
        this.structuresProperties = structuresProperties;
    }

    @Override
    public ClientConfiguration clientConfiguration() {
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

        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder = ClientConfiguration.builder().connectedTo(uris);

        if(useSsl.get()){
            builder.usingSsl();
        }

        if(structuresProperties.hasElasticUsernameAndPassword()){
            builder.withBasicAuth(structuresProperties.getElasticUsername(), structuresProperties.getElasticPassword());
        }

        builder.withConnectTimeout(structuresProperties.getElasticConnectionTimeout())
               .withSocketTimeout(structuresProperties.getElasticSocketTimeout());

        return builder.build();
    }

    @Bean
    public ElasticsearchAsyncClient elasticsearchAsyncClient(JsonpMapper jsonpMapper){
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

        int connectTimeout = Long.valueOf(structuresProperties.getElasticConnectionTimeout().toMillis()).intValue();
        int socketTimeout = Long.valueOf(structuresProperties.getElasticSocketTimeout().toMillis()).intValue();

        builder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout));

        RestClient restClient = builder.build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, jsonpMapper);

        return new ElasticsearchAsyncClient(transport);
    }

    @Bean
    public JsonpMapper jsonpMapper(ObjectMapper objectMapper){
        return new JacksonJsonpMapper(objectMapper);
    }

    @Override
    protected boolean writeTypeHints() {
        return false;
    }

}

