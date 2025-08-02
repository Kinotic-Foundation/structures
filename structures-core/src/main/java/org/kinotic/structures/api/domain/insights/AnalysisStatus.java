package org.kinotic.structures.api.domain.insights;

/**
 * Enumeration of possible analysis status values.
 * 
 * 
 */
public enum AnalysisStatus {
    
    /**
     * Analysis completed successfully with high confidence in results.
     */
    SUCCESS,
    
    /**
     * Analysis completed but with some limitations or warnings.
     * Results are still usable but may not be optimal.
     */
    PARTIAL_SUCCESS,
    
    /**
     * Analysis failed due to technical issues, insufficient data,
     * or inability to understand the query.
     */
    ERROR,
    
    /**
     * Analysis is still in progress (for async operations).
     */
    PROCESSING,
    
    /**
     * Analysis was cancelled by user or system.
     */
    CANCELLED
}