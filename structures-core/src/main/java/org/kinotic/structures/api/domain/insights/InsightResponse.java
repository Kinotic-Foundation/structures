package org.kinotic.structures.api.domain.insights;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents the complete response from AI analysis including insights,
 * generated code, and metadata.
 * 
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsightResponse {
    
    /**
     * Unique identifier for this analysis result.
     * Can be used for refinement or retrieval operations.
     */
    private String analysisId;
    
    /**
     * The original user query that generated this analysis.
     */
    private String originalQuery;
    
    /**
     * The application ID this analysis was performed for.
     */
    private String applicationId;
    
    /**
     * The project ID where generated code is stored (optional).
     */
    private String projectId;
    
    /**
     * Timestamp when this analysis was generated.
     */
    private Instant createdAt;
    
    /**
     * Human-readable summary of what the AI discovered about the data.
     * This explains the patterns, insights, and reasoning behind the visualization choice.
     */
    private String analysisSummary;
    
    /**
     * List of paths to newly generated or updated files in the project.
     */
    private List<String> generatedFiles;
    
    /**
     * Map of file paths to their modification diffs (for refinements).
     * Key: file path, Value: diff string or patch.
     */
    private Map<String, String> modificationDiffs;

    /**
     * Key insights and findings from the data analysis.
     * Each item represents a specific observation or pattern discovered.
     */
    private List<String> keyFindings;
    
    /**
     * The structures that were analyzed to generate this response.
     */
    private List<String> analyzedStructures;
    
    /**
     * Generated React component code ready for use.
     */
    private GeneratedComponent generatedComponent;
    
    /**
     * Alternative visualization suggestions if the user wants to try different approaches.
     */
    private List<VisualizationSuggestion> alternativeVisualizations;
    
    /**
     * Sample of the data used for the visualization (for preview purposes).
     * Limited to prevent large payloads.
     */
    private List<Map<String, Object>> sampleData;
    
    /**
     * Metadata about the analysis process.
     */
    private AnalysisMetadata metadata;
    
    /**
     * Status of the analysis (SUCCESS, PARTIAL_SUCCESS, ERROR).
     */
    private AnalysisStatus status;
    
    /**
     * Any warnings or notes about the analysis.
     */
    private List<String> warnings;
}