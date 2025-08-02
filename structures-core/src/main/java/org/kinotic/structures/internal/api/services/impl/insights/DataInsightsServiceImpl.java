package org.kinotic.structures.internal.api.services.impl.insights;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.domain.insights.*;
import org.kinotic.structures.api.domain.Project;
import org.kinotic.structures.api.domain.ProjectType;
import org.kinotic.structures.api.services.insights.DataInsightsService;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.ProjectService;
import org.kinotic.structures.internal.api.services.impl.insights.blobstore.InsightsBlobStorage;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of AiInsightsService that uses Spring AI to analyze structure data
 * and generate React visualization components.
 * 
 * This service orchestrates the entire AI pipeline:
 * 1. Structure discovery and schema analysis  
 * 2. Data sampling and pattern recognition
 * 3. Visualization recommendation
 * 4. React code generation
 * 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DataInsightsServiceImpl implements DataInsightsService {

    private final ChatClient chatClient;
    private final InsightsContextService contextService;
    private final StructureDiscoveryTools structureDiscoveryTools;
    private final EntitiesService entitiesService;
    private final InsightsBlobStorage blobStorage;
    private final ProjectService projectService;

    @Override
    public CompletableFuture<InsightResponse> analyzeAndVisualize(InsightRequest request, Participant participant) {
        log.info("Starting AI analysis for query: '{}' in application: {}", request.getQuery(), request.getApplicationId());
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                long startTime = System.currentTimeMillis();
                String analysisId = UUID.randomUUID().toString();
                
                // Create or get existing project for this analysis
                String projectId = getOrCreateProject(request, participant).join();
                
                // Create new DataAnalysisTools instance with participant context
                // StructureDiscoveryTools is autowired since it doesn't need participant context
                DataAnalysisTools dataAnalysisTools = new DataAnalysisTools(entitiesService, participant);
                
                // Build context for Spring AI
                String context = contextService.buildAnalysisContext(request, participant);
                
                // Create the prompt for Spring AI
                String systemPrompt = contextService.getSystemPrompt();
                String userPrompt = buildUserPrompt(request, context);
                
                log.debug("Sending request to Spring AI with {} characters of context", context.length());
                
                // Let Spring AI orchestrate the entire analysis using available tools
                String aiResponse = chatClient
                    .prompt()
                    .system(systemPrompt)
                    .user(userPrompt)
                    .tools(structureDiscoveryTools, dataAnalysisTools) // Spring AI can call these tools
                    .call()
                    .content();
                
                // Store generated code in blob storage
                storeGeneratedCode(projectId, aiResponse, request).join();
                
                // Parse AI response into structured result
                InsightResponse response = parseAiResponse(aiResponse, analysisId, request, startTime, projectId);
                
                log.info("AI analysis completed in {}ms for analysis ID: {} (project: {})", 
                    System.currentTimeMillis() - startTime, analysisId, projectId);
                
                return response;
                
            } catch (Exception e) {
                log.error("Error during AI analysis for query: '{}'", request.getQuery(), e);
                return createErrorResponse(request, e);
            }
        });
    }

    @Override
    public CompletableFuture<List<VisualizationSuggestion>> suggestVisualizations(String structureId, Participant participant) {
        log.info("Generating visualization suggestions for structure: {}", structureId);
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Create new tool instances with participant context

                DataAnalysisTools dataAnalysisTools = new DataAnalysisTools(entitiesService, participant);
                
                // Build context about the specific structure
                String context = contextService.buildStructureContext(structureId, participant);
                
                String systemPrompt = contextService.getVisualizationSuggestionPrompt();
                String userPrompt = String.format(
                    "Analyze the structure '%s' and suggest appropriate visualizations. Context: %s", 
                    structureId, context
                );
                
                String aiResponse = chatClient
                    .prompt()
                    .system(systemPrompt)
                    .user(userPrompt)
                    .tools(structureDiscoveryTools, dataAnalysisTools)
                    .call()
                    .content();
                
                return parseSuggestionsResponse(aiResponse);
                
            } catch (Exception e) {
                log.error("Error generating visualization suggestions for structure: {}", structureId, e);
                return List.of(); // Return empty list on error
            }
        });
    }

    @Override
    public CompletableFuture<InsightResponse> refineAnalysis(String analysisId, String refinementQuery, Participant participant) {
        log.info("Refining analysis {} with query: '{}'", analysisId, refinementQuery);
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Create new tool instances with participant context

                DataAnalysisTools dataAnalysisTools = new DataAnalysisTools(entitiesService, participant);
                
                // TODO: Retrieve previous analysis from storage
                // For now, create a new analysis with refinement context
                
                String systemPrompt = contextService.getSystemPrompt();
                String userPrompt = String.format(
                    "This is a refinement request for analysis ID: %s. " +
                    "User refinement: '%s'. Please modify the previous analysis accordingly.",
                    analysisId, refinementQuery
                );
                
                String aiResponse = chatClient
                    .prompt()
                    .system(systemPrompt)
                    .user(userPrompt)
                    .tools(structureDiscoveryTools, dataAnalysisTools)
                    .call()
                    .content();
                
                return parseAiResponse(aiResponse, analysisId, null, System.currentTimeMillis(), "unknown-project");
                
            } catch (Exception e) {
                log.error("Error refining analysis {}: {}", analysisId, e.getMessage(), e);
                return createErrorResponse(null, e);
            }
        });
    }

    @Override
    public CompletableFuture<InsightResponse> getAnalysis(String analysisId, Participant participant) {
        log.info("Retrieving analysis: {}", analysisId);
        
        return CompletableFuture.supplyAsync(() -> {
            // TODO: Implement analysis storage and retrieval
            // For now, return a placeholder response
            return InsightResponse.builder()
                .analysisId(analysisId)
                .status(AnalysisStatus.ERROR)
                .analysisSummary("Analysis retrieval not yet implemented")
                .createdAt(Instant.now())
                .build();
        });
    }

    /**
     * Builds the user prompt that will be sent to Spring AI along with the context.
     */
    private String buildUserPrompt(InsightRequest request, String context) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("User Query: ").append(request.getQuery()).append("\n\n");
        prompt.append("Application Context:\n").append(context).append("\n\n");
        
        if (request.getFocusStructureId() != null) {
            prompt.append("Focus Structure: ").append(request.getFocusStructureId()).append("\n");
        }
        
        if (request.getPreferredVisualization() != null) {
            prompt.append("Preferred Visualization: ").append(request.getPreferredVisualization()).append("\n");
        }
        
        if (request.getAdditionalContext() != null) {
            prompt.append("Additional Context: ").append(request.getAdditionalContext()).append("\n");
        }
        
        prompt.append("\nPlease analyze this request and generate appropriate visualization code.");
        
        return prompt.toString();
    }

    /**
     * Gets or creates a project for storing the generated code.
     */
    private CompletableFuture<String> getOrCreateProject(InsightRequest request, Participant participant) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Create a new project for each analysis (could be modified to reuse projects)
                String projectId = "insights-" + UUID.randomUUID().toString();
                
                Project project = new Project()
                    .setId(projectId)
                    .setApplicationId(request.getApplicationId())
                    .setName("Data Insights: " + request.getQuery())
                    .setDescription("Generated visualization project from AI analysis")
                    .setSourceOfTruth(ProjectType.DATA_INSIGHTS)
                    .setUpdated(new Date());
                
                // Create project in the system  
                projectService.create(project).join();
                
                // Initialize project structure in blob storage
                blobStorage.initializeProject(projectId, "typescript-react").join();
                
                log.info("Created new DATA_INSIGHTS project: {} for application: {}", projectId, request.getApplicationId());
                
                return projectId;
                
            } catch (Exception e) {
                log.error("Failed to create project for request: {}", request.getQuery(), e);
                throw new RuntimeException("Failed to create project", e);
            }
        });
    }
    
    /**
     * Stores the generated code from AI response in blob storage.
     */
    private CompletableFuture<Void> storeGeneratedCode(String projectId, String aiResponse, InsightRequest request) {
        return CompletableFuture.runAsync(() -> {
            try {
                // TODO: Parse AI response to extract generated components
                // For now, store the raw response as a markdown file
                
                String timestamp = Instant.now().toString().replaceAll("[:\\-.]", "_");
                String filename = "generated/analysis_" + timestamp + ".md";
                
                String content = String.format("""
                    # AI Analysis Results
                    
                    **Query:** %s
                    **Generated:** %s
                    **Application:** %s
                    
                    ## AI Response
                    
                    ```
                    %s
                    ```
                    
                    ## Next Steps
                    
                    This response should be parsed to extract:
                    - Generated React components (.tsx files)
                    - TypeScript interfaces (.ts files) 
                    - Configuration updates (package.json dependencies)
                    - Styling files (.css files)
                    """, 
                    request.getQuery(),
                    Instant.now(),
                    request.getApplicationId(),
                    aiResponse
                );
                
                blobStorage.store(projectId, filename, content.getBytes(), "text/markdown").join();
                
                log.debug("Stored generated code for project: {} at path: {}", projectId, filename);
                
            } catch (Exception e) {
                log.error("Failed to store generated code for project: {}", projectId, e);
                throw new RuntimeException("Failed to store generated code", e);
            }
        });
    }

    /**
     * Parses the AI response into a structured InsightResponse object.
     * This is a simplified implementation - in practice, you might want more
     * sophisticated parsing or use structured output from Spring AI.
     */
    private InsightResponse parseAiResponse(String aiResponse, String analysisId, InsightRequest request, long startTime, String projectId) {
        // TODO: Implement sophisticated AI response parsing
        // For now, create a basic response structure
        
        return InsightResponse.builder()
            .analysisId(analysisId)
            .originalQuery(request != null ? request.getQuery() : "Unknown")
            .applicationId(request != null ? request.getApplicationId() : "Unknown")
            .projectId(projectId) // Include project ID in response
            .createdAt(Instant.now())
            .analysisSummary("AI analysis completed and code stored in project: " + projectId)
            .status(AnalysisStatus.PARTIAL_SUCCESS)
            .keyFindings(List.of("Generated code stored in blob storage", "Project created: " + projectId))
            .warnings(List.of("Response parsing is currently simplified"))
            .metadata(AnalysisMetadata.builder()
                .processingTime(java.time.Duration.ofMillis(System.currentTimeMillis() - startTime))
                .aiModel("gpt-4") // TODO: Get from configuration
                .qualityScore(75) // Placeholder
                .build())
            .build();
    }

    /**
     * Parses visualization suggestions from AI response.
     */
    private List<VisualizationSuggestion> parseSuggestionsResponse(String aiResponse) {
        // TODO: Implement parsing of visualization suggestions
        return List.of(
            VisualizationSuggestion.builder()
                .visualizationType("bar-chart")
                .title("Sample Suggestion")
                .description("Placeholder suggestion - parsing needs implementation")
                .complexity(VisualizationSuggestion.ComplexityLevel.SIMPLE)
                .confidenceScore(50)
                .build()
        );
    }

    /**
     * Creates an error response when analysis fails.
     */
    private InsightResponse createErrorResponse(InsightRequest request, Exception error) {
        return InsightResponse.builder()
            .analysisId(UUID.randomUUID().toString())
            .originalQuery(request != null ? request.getQuery() : "Unknown")
            .applicationId(request != null ? request.getApplicationId() : "Unknown")
            .createdAt(Instant.now())
            .status(AnalysisStatus.ERROR)
            .analysisSummary("Analysis failed due to error: " + error.getMessage())
            .warnings(List.of("Error occurred during analysis: " + error.getClass().getSimpleName()))
            .build();
    }
}