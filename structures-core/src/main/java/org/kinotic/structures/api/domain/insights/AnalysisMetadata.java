package org.kinotic.structures.api.domain.insights;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Duration;
import java.util.Map;

/**
 * Metadata about the AI analysis process including performance metrics
 * and processing details.
 * 
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalysisMetadata {
    
    /**
     * Time taken to complete the analysis.
     */
    private Duration processingTime;
    
    /**
     * Number of structures examined during analysis.
     */
    private int structuresExamined;
    
    /**
     * Number of data records sampled for analysis.
     */
    private int recordsSampled;
    
    /**
     * Total number of records available in the analyzed structures.
     */
    private long totalRecordsAvailable;
    
    /**
     * The AI model used for analysis (e.g., "gpt-4", "gpt-3.5-turbo").
     */
    private String aiModel;
    
    /**
     * Number of API calls made to the AI service.
     */
    private int aiApiCalls;
    
    /**
     * Number of tools/functions called by the AI during analysis.
     */
    private int toolCallsExecuted;
    
    /**
     * Version of the analysis engine used.
     */
    private String analysisEngineVersion;
    
    /**
     * Quality score of the generated analysis (0-100).
     * Based on data completeness, code quality, and relevance.
     */
    private int qualityScore;
    
    /**
     * Additional processing metrics and debugging information.
     * May include performance breakdowns, cache hits, etc.
     */
    private Map<String, Object> additionalMetrics;
}