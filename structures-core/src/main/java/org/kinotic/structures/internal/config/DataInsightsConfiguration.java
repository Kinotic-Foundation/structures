package org.kinotic.structures.internal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            .defaultSystem("""
                You are an expert data analyst and React developer working with the Structures platform.
                You analyze user queries about their data and generate appropriate React visualization components.
                
                Always use the available tools to:
                1. Discover structures in the user's application
                2. Analyze data patterns and schemas
                3. Generate production-ready React code
                4. Provide clear explanations of your analysis
                
                Your goal is to help users understand their data through intelligent visualizations.
                """)
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