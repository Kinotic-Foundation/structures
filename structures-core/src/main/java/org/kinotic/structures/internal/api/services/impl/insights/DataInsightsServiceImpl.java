package org.kinotic.structures.internal.api.services.impl.insights;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.domain.insights.*;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.insights.DataInsightsService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of DataInsightsService that processes user requests through a chain of steps:
 * 1. Request Analysis: Determines if user wants new visualization or to modify existing
 * 2. New Visualization Path: Generates new visualization using AI analysis
 * 3. Modify Existing Path: Loads and modifies existing visualization
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DataInsightsServiceImpl implements DataInsightsService {

    private final ChatClient chatClient;
    private final InsightsContextService contextService;
    private final StructureDiscoveryTools structureDiscoveryTools;
    private final EntitiesService entitiesService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public CompletableFuture<InsightResponse> processRequest(InsightRequest request, Participant participant) {
        log.info("Processing data insights request: '{}'", request.getQuery());

        return CompletableFuture.supplyAsync(() -> {
            try {
                // Step 1: Analyze the request to determine intent
                RequestAnalysisResult analysisResult = analyzeRequest(request, participant);
                log.info("Request analysis result: {}", analysisResult);

                // Step 2: Route to appropriate processing path
                if (analysisResult.getRequestType() == RequestAnalysisResult.RequestType.NEW_VISUALIZATION) {
                    return generateNewVisualization(request, participant);
                } else {
                    return modifyExistingVisualization(request, participant, analysisResult);
                }

            } catch (Exception e) {
                log.error("Error processing request: {}", e.getMessage(), e);
                return createErrorResponse(e.getMessage());
            }
        });
    }

    /**
     * Step 1: Analyzes the user's request to determine if they want a new visualization
     * or to modify an existing one.
     */
    private RequestAnalysisResult analyzeRequest(InsightRequest request, Participant participant) throws Exception {
        log.debug("Analyzing request intent for query: '{}'", request.getQuery());

        String systemPrompt = contextService.getRequestAnalysisPrompt();
        String userPrompt = String.format("User request: %s", request.getQuery());

        String aiResponse = chatClient
                .prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();

        // Parse the JSON response from the AI
        try {
            return objectMapper.readValue(aiResponse.trim(), RequestAnalysisResult.class);
        } catch (Exception e) {
            log.warn("Failed to parse AI response as JSON, treating as new visualization request: {}", aiResponse);
            return new RequestAnalysisResult().setRequestType(RequestAnalysisResult.RequestType.NEW_VISUALIZATION)
                                              .setAdditionalContext(request.getQuery());
        }
    }

    /**
     * Step 2a: Generates a new visualization using the existing AI pipeline.
     */
    private InsightResponse generateNewVisualization(InsightRequest request, Participant participant) {
        log.info("Generating new visualization for query: '{}'", request.getQuery());

        try {
            // Create tools with participant context
            DataAnalysisTools dataAnalysisTools = new DataAnalysisTools(entitiesService, participant);

            // Build context
            String context = contextService.buildAnalysisContext(request, participant);
            String systemPrompt = contextService.getHtmlGenerationSystemPrompt();
            String userPrompt = buildUserPrompt(request, context);

            // Generate visualization with AI
            String aiResponse = chatClient
                    .prompt()
                    .system(systemPrompt)
                    .user(userPrompt)
                    .tools(structureDiscoveryTools, dataAnalysisTools)
                    .call()
                    .content();

            // Create response
            return createVisualizationResponse(aiResponse, request);

        } catch (Exception e) {
            log.error("Error generating new visualization: {}", e.getMessage(), e);
            return createErrorResponse(e.getMessage());
        }
    }

    /**
     * Step 2b: Modifies an existing visualization based on user feedback.
     */
    private InsightResponse modifyExistingVisualization(InsightRequest request, Participant participant, RequestAnalysisResult analysisResult) {
        log.info("Modifying existing visualization: '{}' with request: '{}'",
                 analysisResult.getVisualizationName(), request.getQuery());

        try {
            // TODO: Load the existing visualization code
            // For now, this is a no-op placeholder
            String existingVisualizationCode = loadExistingVisualization(analysisResult.getVisualizationName());

            if (existingVisualizationCode == null) {
                log.warn("Could not load existing visualization '{}', falling back to new visualization",
                         analysisResult.getVisualizationName());
                return generateNewVisualization(request, participant);
            }

            // Create tools with participant context
            DataAnalysisTools dataAnalysisTools = new DataAnalysisTools(entitiesService, participant);

            // Build context
            String context = contextService.buildAnalysisContext(request, participant);
            String systemPrompt = contextService.getVisualizationModificationPrompt();
            String userPrompt = buildModificationPrompt(request, context, existingVisualizationCode, analysisResult);

            // Generate modified visualization with AI
            String aiResponse = chatClient
                    .prompt()
                    .system(systemPrompt)
                    .user(userPrompt)
                    .tools(structureDiscoveryTools, dataAnalysisTools)
                    .call()
                    .content();

            // Create response
            return createVisualizationResponse(aiResponse, request);

        } catch (Exception e) {
            log.error("Error modifying existing visualization: {}", e.getMessage(), e);
            return createErrorResponse(e.getMessage());
        }
    }

    /**
     * Loads an existing visualization code (placeholder implementation).
     */
    private String loadExistingVisualization(String visualizationName) {
        // TODO: Implement actual visualization storage and retrieval
        // For now, return null to indicate no existing visualization found
        log.debug("Loading existing visualization: {} (placeholder implementation)", visualizationName);
        return null;
    }

    /**
     * Builds the user prompt for new visualization generation.
     */
    private String buildUserPrompt(InsightRequest request, String context) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("User Query: ").append(request.getQuery()).append("\n\n");
        prompt.append("Application Context:\n").append(context).append("\n\n");

        // Add hardcoded values for the web components
        prompt.append("HARDCODED VALUES FOR WEB COMPONENTS:\n");
        prompt.append("Application ID: ").append(request.getApplicationId()).append("\n");
        
        if (request.getFocusStructureId() != null) {
            prompt.append("Structure ID: ").append(request.getFocusStructureId()).append("\n");
            // Extract structure name from the structure ID
            String structureName = request.getFocusStructureId().contains(".") ? 
                request.getFocusStructureId().substring(request.getFocusStructureId().indexOf(".") + 1) : 
                request.getFocusStructureId();
            prompt.append("Structure Name: ").append(structureName).append("\n");
        }

        if (request.getPreferredVisualization() != null) {
            prompt.append("Preferred Visualization: ").append(request.getPreferredVisualization()).append("\n");
        }

        if (request.getAdditionalContext() != null) {
            prompt.append("Additional Context: ").append(request.getAdditionalContext()).append("\n");
        }

        prompt.append("\nIMPORTANT: In your JavaScript code, hardcode these values directly:");
        prompt.append("\n- Use '").append(request.getApplicationId()).append("' as the applicationId");
        if (request.getFocusStructureId() != null) {
            String structureName = request.getFocusStructureId().contains(".") ? 
                request.getFocusStructureId().substring(request.getFocusStructureId().indexOf(".") + 1) : 
                request.getFocusStructureId();
            prompt.append("\n- Use '").append(structureName).append("' as the structureName");
            prompt.append("\n- Use '").append(request.getFocusStructureId()).append("' as the structureId");
        }
        prompt.append("\n\nPlease analyze this request and generate a custom web component that visualizes the data. The response should be ONLY the JavaScript code for the web component.");

        return prompt.toString();
    }

    /**
     * Builds the user prompt for visualization modification.
     */
    private String buildModificationPrompt(InsightRequest request, String context, String existingCode, RequestAnalysisResult analysisResult) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("User Modification Request: ").append(request.getQuery()).append("\n\n");
        prompt.append("Application Context:\n").append(context).append("\n\n");
        prompt.append("Visualization to Modify: ").append(analysisResult.getVisualizationName()).append("\n");
        prompt.append("Additional Context: ").append(analysisResult.getAdditionalContext()).append("\n\n");
        prompt.append("Existing Visualization Code:\n").append(existingCode).append("\n\n");
        prompt.append("Please modify the existing visualization according to the user's request. Return ONLY the complete modified JavaScript code.");

        return prompt.toString();
    }

    /**
     * Creates a visualization response from the AI-generated code.
     */
    private InsightResponse createVisualizationResponse(String aiResponse, InsightRequest request) {
        try {
            // Parse the AI response to extract multiple components
            List<DataInsightsComponent> components = parseComponentsFromResponse(aiResponse, request);

            return InsightResponse.builder()
                                  .components(components)
                                  .createdAt(Instant.now())
                                  .status(AnalysisStatus.SUCCESS)
                                  .build();

        } catch (Exception e) {
            log.error("Error parsing components from AI response: {}", e.getMessage());
            return InsightResponse.builder()
                                  .components(List.of())
                                  .createdAt(Instant.now())
                                  .status(AnalysisStatus.ERROR)
                                  .errorMessage("Failed to parse components: " + e.getMessage())
                                  .build();
        }
    }

    /**
     * Parses the AI response to extract multiple DataInsightsComponent objects from JSON.
     */
    private List<DataInsightsComponent> parseComponentsFromResponse(String aiResponse, InsightRequest request) {
        try {
            // Clean the response and try to parse as JSON array
            String cleanedResponse = aiResponse.trim();

            // Handle potential markdown code blocks
            if (cleanedResponse.startsWith("```json")) {
                cleanedResponse = cleanedResponse.substring(7);
            }
            if (cleanedResponse.startsWith("```")) {
                cleanedResponse = cleanedResponse.substring(3);
            }
            if (cleanedResponse.endsWith("```")) {
                cleanedResponse = cleanedResponse.substring(0, cleanedResponse.length() - 3);
            }

            cleanedResponse = cleanedResponse.trim();

            // Parse JSON array directly into DataInsightsComponent objects
            CollectionType collectionType = objectMapper.getTypeFactory()
                                                        .constructCollectionType(List.class,
                                                                                 DataInsightsComponent.class);
            List<DataInsightsComponent> components = objectMapper.readValue(cleanedResponse, collectionType);

            // Set system-generated fields for each component
            for (DataInsightsComponent component : components) {
                if (component.getId() == null) {
                    component.setId(UUID.randomUUID().toString());
                }
                component.setApplicationId(request.getApplicationId());
                component.setModifiedAt(Instant.now());
            }

            return components;

        } catch (Exception e) {
            log.error("Failed to parse JSON response from AI: {}", e.getMessage());
            log.debug("Raw AI response: {}", aiResponse);
            throw new RuntimeException("AI response was not in the expected JSON format. Expected an array of DataInsightsComponent objects.", e);
        }
    }
    



    /**
     * Creates an error response when visualization generation fails.
     */
    private InsightResponse createErrorResponse(String errorMessage) {
        return InsightResponse.builder()
                              .createdAt(Instant.now())
                              .status(AnalysisStatus.ERROR)
                              .errorMessage(errorMessage)
                              .build();
    }
}