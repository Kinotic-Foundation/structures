package org.kinotic.structures.api.services.insights;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.domain.insights.InsightRequest;
import org.kinotic.structures.api.domain.insights.InsightResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Provides AI-powered data analysis and visualization code generation capabilities.
 * This service analyzes user queries about their structures and generates appropriate
 * web components with charts and visualizations.
 * 
 */
@Publish
public interface DataInsightsService {

    /**
     * Processes a user's natural language request to either create a new visualization
     * or modify an existing one. This method implements a chain of steps:
     * 
     * 1. Request Analysis: Determines if the user wants a new visualization or to modify existing
     * 2. New Visualization Path: Generates a new visualization using AI analysis
     * 3. Modify Existing Path: Loads existing visualization and modifies it based on user feedback
     * 
     * @param request the analysis request containing query and context
     * @param participant the participant of the logged-in user
     * @return CompletableFuture with complete analysis results and generated code
     */
    CompletableFuture<InsightResponse> processRequest(InsightRequest request, Participant participant);
}