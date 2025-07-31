package org.kinotic.structures.api.services.insights;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.domain.insights.InsightRequest;
import org.kinotic.structures.api.domain.insights.InsightResponse;
import org.kinotic.structures.api.domain.insights.VisualizationSuggestion;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Provides AI-powered data analysis and visualization code generation capabilities.
 * This service analyzes user queries about their structures and generates appropriate
 * React components with charts and visualizations.
 * 
 */
@Publish
public interface DataInsightsService {

    /**
     * Analyzes structure data based on natural language query and generates
     * appropriate React visualization components.
     * 
     * This is the main method that orchestrates the entire AI pipeline:
     * 1. Parse the user's natural language query
     * 2. Discover relevant structures in the application
     * 3. Analyze the data patterns and relationships
     * 4. Generate appropriate React visualization code
     * 5. Return complete analysis with executable code
     * 
     * @param request the analysis request containing query and context
     * @param participant the participant of the logged-in user
     * @return CompletableFuture with complete analysis results and generated code
     */
    CompletableFuture<InsightResponse> analyzeAndVisualize(InsightRequest request, Participant participant);

    /**
     * Suggests appropriate visualization types for a specific structure
     * based on its schema and data patterns.
     * 
     * @param structureId the id of the structure to analyze (applicationId.structureName)
     * @param participant the participant of the logged-in user
     * @return CompletableFuture with list of visualization suggestions
     */
    CompletableFuture<List<VisualizationSuggestion>> suggestVisualizations(String structureId, Participant participant);

    /**
     * Refines or modifies a previously generated analysis based on user feedback.
     * This allows users to iterate on the generated visualizations.
     * 
     * @param analysisId the id of the previous analysis to refine
     * @param refinementQuery user's refinement instructions (e.g., "make it a pie chart", "add more details")
     * @param participant the participant of the logged-in user
     * @return CompletableFuture with refined analysis and updated code
     */
    CompletableFuture<InsightResponse> refineAnalysis(String analysisId, String refinementQuery, Participant participant);

    /**
     * Gets the current status and details of a previous analysis.
     * Useful for retrieving analysis results or checking processing status.
     * 
     * @param analysisId the id of the analysis to retrieve
     * @param participant the participant of the logged-in user
     * @return CompletableFuture with the analysis details
     */
    CompletableFuture<InsightResponse> getAnalysis(String analysisId, Participant participant);
}