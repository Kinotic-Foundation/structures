package org.kinotic.structures.internal.api.services.impl.insights;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.domain.insights.*;
import org.kinotic.structures.api.domain.ProjectType;
import org.kinotic.structures.api.services.insights.DataInsightsService;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.ProjectService;
import org.kinotic.structures.internal.api.services.impl.insights.blobstore.InsightsBlobStorage;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collections;
import java.util.HashMap;

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
    private final VectorStore vectorStore;
    private final ObjectMapper objectMapper;

    private CompletableFuture<String> validateProjectAndGetApplicationId(String projectId, Participant participant) {
        return projectService.findById(projectId).thenApply(project -> {
            if (project == null) {
                throw new IllegalArgumentException("Project not found: " + projectId);
            }
            if (project.getSourceOfTruth() != ProjectType.DATA_INSIGHTS) {
                throw new IllegalArgumentException("Project must be of type DATA_INSIGHTS, but was " + project.getSourceOfTruth());
            }
            // TODO: Validate user access via participant
            return project.getApplicationId();
        });
    }

    @Override
    public CompletableFuture<InsightResponse> analyzeAndVisualize(InsightRequest request, Participant participant) {
        log.info("Starting AI analysis for query: '{}' in project: {}", request.getQuery(), request.getProjectId());
        
        return validateProjectAndGetApplicationId(request.getProjectId(), participant)
            .thenCompose(applicationId -> performAnalysisStep(request, applicationId, participant)
                .thenCompose(analysisSummary -> performSuggestionStep(analysisSummary, request, participant)
                    .thenCompose(suggestions -> performCodeGenerationStep(suggestions, request, participant)
                        .thenApply(generatedFiles -> assembleResponse(request, generatedFiles, analysisSummary, suggestions)))));
    }



    private CompletableFuture<String> performAnalysisStep(InsightRequest request, String applicationId, Participant participant) {
        DataAnalysisTools dataAnalysisTools = new DataAnalysisTools(entitiesService, participant);
        
        String systemPrompt = contextService.getAnalysisPrompt();
        String userPrompt = buildUserPrompt(request, applicationId); // Use a helper to build prompt
        
        return CompletableFuture.supplyAsync(() -> 
            chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .tools(structureDiscoveryTools, dataAnalysisTools)
                .call()
                .content()
        );
    }

    private CompletableFuture<List<VisualizationSuggestion>> performSuggestionStep(String analysisSummary, InsightRequest request, Participant participant) {
        DataAnalysisTools dataAnalysisTools = new DataAnalysisTools(entitiesService, participant);
        
        String systemPrompt = contextService.getSuggestionPrompt();
        String userPrompt = "Analysis Summary: " + analysisSummary;
        
        return CompletableFuture.supplyAsync(() -> {
            String response = chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .tools(structureDiscoveryTools, dataAnalysisTools)
                .call()
                .content();
            try {
                return objectMapper.readValue(response, new TypeReference<List<VisualizationSuggestion>>(){});
            } catch (Exception e) {
                log.error("Parsing error", e);
                return Collections.emptyList();
            }
        });
    }

    private CompletableFuture<Map<String, String>> performCodeGenerationStep(List<VisualizationSuggestion> suggestions, InsightRequest request, Participant participant) {
        DataAnalysisTools dataAnalysisTools = new DataAnalysisTools(entitiesService, participant);
        
        // Vector retrieval for relevant existing code
        String retrievalQuery = "relevant code for: " + request.getQuery();
        List<Document> relevantDocs = vectorStore.similaritySearch(retrievalQuery);
        String existingCodeContext = relevantDocs.stream()
            .map(doc -> doc.getMetadata().get("filePath") + ": " + doc.getText())
            .collect(Collectors.joining("\n"));
        
        String systemPrompt = contextService.getCodeGenerationPrompt();
        String userPrompt = "Suggestions: " + suggestions.toString() + "\nExisting Code: " + existingCodeContext;
        
        return CompletableFuture.supplyAsync(() -> {
            String response = chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .tools(structureDiscoveryTools, dataAnalysisTools)
                .call()
                .content();
            try {
                // Parse the response to get files with their content and MIME types
                Map<String, Object> responseData = objectMapper.readValue(response, new TypeReference<Map<String, Object>>(){});
                List<Map<String, String>> files = (List<Map<String, String>>) responseData.get("files");
                
                Map<String, String> generatedFiles = new HashMap<>();
                if (files != null) {
                    for (Map<String, String> file : files) {
                        String filePath = file.get("path");
                        String content = file.get("content");
                        String mimeType = file.getOrDefault("mimeType", "application/octet-stream");
                        
                        if (filePath != null && content != null) {
                            generatedFiles.put(filePath, content);
                            
                            // Store with the correct MIME type
                            blobStorage.store(request.getProjectId(), filePath, content.getBytes(), mimeType).join();
                            
                            // Add to vector store for semantic search
                            vectorStore.add(List.of(new Document(content, Map.of(
                                "filePath", filePath, 
                                "projectId", request.getProjectId(),
                                "mimeType", mimeType
                            ))));
                        }
                    }
                }
                return generatedFiles;
            } catch (Exception e) {
                log.error("Error parsing generated code response", e);
                return Collections.emptyMap();
            }
        });
    }

    private InsightResponse assembleResponse(InsightRequest request, Map<String, String> generatedFiles, String analysisSummary, List<VisualizationSuggestion> suggestions) {
        String analysisId = UUID.randomUUID().toString();
        return InsightResponse.builder()
            .analysisId(analysisId)
            .projectId(request.getProjectId())
            .originalQuery(request.getQuery())
            .analysisSummary(analysisSummary)
            .alternativeVisualizations(suggestions)
            .generatedFiles(new ArrayList<>(generatedFiles.keySet()))
            .status(AnalysisStatus.SUCCESS)
            .createdAt(Instant.now())
            .build();
    }

    @Override
    public CompletableFuture<List<VisualizationSuggestion>> suggestVisualizations(String projectId, String structureId, Participant participant) {
        return validateProjectAndGetApplicationId(projectId, participant)
            .thenCompose(appId -> {
                // Implement suggestion logic similar to Step 2
                return performSuggestionStep("Suggest visualizations for structure " + structureId, new InsightRequest("", projectId), participant);
            });
    }

    @Override
    public CompletableFuture<InsightResponse> getAnalysis(String analysisId, String projectId, Participant participant) {
        return validateProjectAndGetApplicationId(projectId, participant)
            .thenApply(appId -> {
                // TODO: Retrieve from storage or blob
                return InsightResponse.builder().analysisId(analysisId).projectId(projectId).build();
            });
    }

    @Override
    public CompletableFuture<InsightResponse> refineAnalysis(String analysisId, String refinementQuery, String projectId, Participant participant) {
        log.info("Refining analysis {} in project {} with query: '{}'", analysisId, projectId, refinementQuery);
        InsightRequest refinementRequest = new InsightRequest(refinementQuery, projectId);
        return validateProjectAndGetApplicationId(projectId, participant)
            .thenCompose(applicationId -> performAnalysisStep(refinementRequest, applicationId, participant)
                .thenCompose(analysisSummary -> performSuggestionStep(analysisSummary, refinementRequest, participant)
                    .thenCompose(suggestions -> performCodeGenerationStep(suggestions, refinementRequest, participant)
                        .thenApply(generatedFiles -> assembleResponse(refinementRequest, generatedFiles, analysisSummary, suggestions)))));
    }

    /**
     * Builds the user prompt that will be sent to Spring AI along with the context.
     */
    private String buildUserPrompt(InsightRequest request, String applicationId) {
        return "Query: " + request.getQuery() + "\nApplication ID: " + applicationId;
    }


}