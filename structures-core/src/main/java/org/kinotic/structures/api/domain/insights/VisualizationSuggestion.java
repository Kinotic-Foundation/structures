package org.kinotic.structures.api.domain.insights;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

/**
 * Represents a suggestion for a specific type of data visualization
 * based on the structure's schema and data patterns.
 * 
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisualizationSuggestion {
    
    /**
     * The type of visualization suggested.
     * Examples: "bar-chart", "pie-chart", "line-chart", "scatter-plot", "table", "heatmap"
     */
    private String visualizationType;
    
    /**
     * Human-readable name for this visualization.
     * Examples: "Sales by Category", "Trend Over Time", "Distribution Analysis"
     */
    private String title;
    
    /**
     * Description of why this visualization is suitable for the data.
     */
    private String description;
    
    /**
     * Explanation of what insights this visualization would reveal.
     */
    private String insights;
    
    /**
     * The structure fields that would be used in this visualization.
     */
    private List<String> involvedFields;
    
    /**
     * The specific chart library recommended for this visualization.
     * Examples: "recharts", "nivo", "victory"
     */
    private String recommendedLibrary;
    
    /**
     * Confidence score (0-100) indicating how well this visualization
     * matches the data characteristics.
     */
    private int confidenceScore;
    
    /**
     * Level of complexity for implementing this visualization.
     * Examples: "SIMPLE", "MODERATE", "COMPLEX"
     */
    private ComplexityLevel complexity;
    
    /**
     * Whether this visualization is suitable for the current data size.
     */
    private boolean suitableForDataSize;
    
    /**
     * Sample query or description that would generate this visualization.
     * Helps users understand how to request it.
     */
    private String sampleQuery;
    
    /**
     * Any prerequisites or data requirements for this visualization.
     */
    private List<String> requirements;
    
    /**
     * Enumeration for visualization complexity levels.
     */
    public enum ComplexityLevel {
        SIMPLE,    // Basic charts like bar, pie, line
        MODERATE,  // Multi-series charts, grouped visualizations
        COMPLEX    // Advanced visualizations like network diagrams, custom charts
    }
}