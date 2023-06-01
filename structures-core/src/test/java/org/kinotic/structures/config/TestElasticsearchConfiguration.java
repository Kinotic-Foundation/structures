package org.kinotic.structures.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;

@Configuration
@Profile("test")
public class TestElasticsearchConfiguration extends ReactiveElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {

        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder
                = ClientConfiguration.builder().connectedTo(ElasticsearchProvider.ELASTICSEARCH_CONTAINER.getHttpHostAddress());

        builder.withConnectTimeout(60000)
               .withSocketTimeout(60000);

        return builder.build();
    }

    @Bean
    public JsonpMapper jsonpMapper(ObjectMapper objectMapper){
        return new JacksonJsonpMapper(objectMapper);
    }

    @Bean
    public ElasticsearchAsyncClient elasticsearchAsyncClient(JsonpMapper jsonpMapper){

        String[] parts = ElasticsearchProvider.ELASTICSEARCH_CONTAINER.getHttpHostAddress().split(":");

        RestClientBuilder builder = RestClient.builder(new HttpHost(parts[0], Integer.parseInt(parts[1])));

        RestClient restClient = builder.build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, jsonpMapper);

        return new ElasticsearchAsyncClient(transport);
    }

    @Override
    protected boolean writeTypeHints() {
        return false;
    }

}
