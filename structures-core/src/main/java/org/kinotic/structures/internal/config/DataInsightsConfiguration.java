package org.kinotic.structures.internal.config;

import org.elasticsearch.client.RestClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for Spring AI components used in the insights functionality.
 * 
 * This configuration sets up the ChatClient and other AI-related beans
 * needed for the insights service to function properly.
 *
 */
@Configuration
@Slf4j
public class DataInsightsConfiguration {

    /**
     * Creates a ChatClient bean configured for insights analysis.
     * This ChatClient will be used by the AiInsightsServiceImpl to communicate
     * with the OpenAI API for data analysis and code generation.
     */
    @Bean
    public ChatClient insightsChatClient(OpenAiChatModel openAiChatModel) {
        log.info("Configuring ChatClient for AI insights functionality");
        
        return ChatClient.builder(openAiChatModel)
                         .build();
    }
    
    @Bean
    public VectorStore vectorStore(RestClient restClient, EmbeddingModel embeddingModel) {
        ElasticsearchVectorStoreOptions options = new ElasticsearchVectorStoreOptions();
        options.setIndexName("struct_data-insights");

        return ElasticsearchVectorStore.builder(restClient, embeddingModel)
                                       .options(options)                     // Optional: use custom options
                                       .initializeSchema(true)               // Optional: defaults to false
                                       .build();
    }
    
    /**
     * Configuration bean to verify Spring AI setup.
     * This logs important configuration information for debugging.
     */
    @Bean
    public DataInsightsConfigurationInfo configurationInfo() {
        log.info("Spring AI Insights configuration loaded successfully");
        log.info("Available tools will be automatically registered with ChatClient");
        
        return new DataInsightsConfigurationInfo();
    }
    
    /**
     * Simple info class to track configuration status.
     */
    public static class DataInsightsConfigurationInfo {
        private final long configuredAt = System.currentTimeMillis();
        
        public long getConfiguredAt() {
            return configuredAt;
        }
    }
}