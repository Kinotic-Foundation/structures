package org.kinotic.structures.api.domain.insights;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Represents a request for AI-powered data analysis and visualization generation.
 * 
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsightRequest {
    
    /**
     * Constructor for creating a request with just query and projectId
     */
    public InsightRequest(String query, String projectId) {
        this.query = query;
        this.projectId = projectId;
    }
    
    /**
     * The user's natural language query about their data.
     * Examples:
     * - "Show me my products grouped by category"
     * - "Give me a chart of customer orders over time"
     * - "Display provider qualifications and specialties"
     */
    private String query;
    
    /**
     * The ID of the project to use for this request. The project must already exist and be of type DATA_INSIGHTS.
     */
    private String projectId;

    /**
     * Optional: Specific structure ID to focus the analysis on.
     * If provided, will prioritize this structure in the analysis.
     * Format: "applicationId.structureName"
     */
    private String focusStructureId;
    
    /**
     * Optional: Preferred visualization type if user has a preference.
     * Examples: "bar", "pie", "line", "scatter", "table"
     */
    private String preferredVisualization;
    
    /**
     * Optional: Additional context or constraints for the analysis.
     * Examples: "only show last 30 days", "include only active records"
     */
    private String additionalContext;
}