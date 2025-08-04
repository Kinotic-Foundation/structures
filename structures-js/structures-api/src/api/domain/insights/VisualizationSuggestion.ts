/**
 * Enumeration for visualization complexity levels.
 */
export enum ComplexityLevel {
    SIMPLE = 'SIMPLE',    // Basic charts like bar, pie, line
    MODERATE = 'MODERATE',  // Multi-series charts, grouped visualizations
    COMPLEX = 'COMPLEX'    // Advanced visualizations like network diagrams, custom charts
}

/**
 * Represents a suggestion for a specific type of data visualization
 * based on the structure's schema and data patterns.
 */
export interface VisualizationSuggestion {
    
    /**
     * The type of visualization suggested.
     * Examples: "bar-chart", "pie-chart", "line-chart", "scatter-plot", "table", "heatmap"
     */
    visualizationType: string;
    
    /**
     * Human-readable name for this visualization.
     * Examples: "Sales by Category", "Trend Over Time", "Distribution Analysis"
     */
    title: string;
    
    /**
     * Description of why this visualization is suitable for the data.
     */
    description: string;
    
    /**
     * Explanation of what insights this visualization would reveal.
     */
    insights: string;
    
    /**
     * The structure fields that would be used in this visualization.
     */
    involvedFields: string[];
    
    /**
     * The specific chart library recommended for this visualization.
     * Examples: "recharts", "nivo", "victory"
     */
    recommendedLibrary: string;
    
    /**
     * Confidence score (0-100) indicating how well this visualization
     * matches the data characteristics.
     */
    confidenceScore: number;
    
    /**
     * Level of complexity for implementing this visualization.
     */
    complexity: ComplexityLevel;
    
    /**
     * Whether this visualization is suitable for the current data size.
     */
    suitableForDataSize: boolean;
    
    /**
     * Sample query or description that would generate this visualization.
     * Helps users understand how to request it.
     */
    sampleQuery: string;
    
    /**
     * Any prerequisites or data requirements for this visualization.
     */
    requirements: string[];
}