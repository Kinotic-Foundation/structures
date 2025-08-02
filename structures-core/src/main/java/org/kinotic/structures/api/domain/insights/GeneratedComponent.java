package org.kinotic.structures.api.domain.insights;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

/**
 * Represents a generated React component with all necessary code and metadata.
 * 
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneratedComponent {
    
    /**
     * The complete React component code in TypeScript.
     * This includes the component definition, hooks, and rendering logic.
     */
    private String reactCode;
    
    /**
     * TypeScript interfaces for the data types used in the component.
     * Generated based on the C3 type definitions from the structures.
     */
    private String typeDefinitions;
    
    /**
     * CSS styles for the component (if any custom styling is needed).
     */
    private String cssStyles;
    
    /**
     * NPM dependencies required for this component to work.
     * Examples: ["recharts", "@nivo/bar", "react", "@types/react"]
     */
    private List<String> requiredDependencies;
    
    /**
     * Import statements needed at the top of the file.
     * These are separated for easier integration into existing projects.
     */
    private List<String> importStatements;
    
    /**
     * The name of the generated React component.
     * Examples: "ProductSalesChart", "ProviderQualificationsView"
     */
    private String componentName;
    
    /**
     * Description of what the component does and how to use it.
     */
    private String description;
    
    /**
     * The type of visualization this component generates.
     * Examples: "bar-chart", "pie-chart", "line-chart", "table", "dashboard"
     */
    private String visualizationType;
    
    /**
     * The chart library used (if applicable).
     * Examples: "recharts", "nivo", "victory", "custom"
     */
    private String chartLibrary;
    
    /**
     * Props that can be passed to customize the component.
     * Each entry contains prop name and its TypeScript type.
     */
    private List<ComponentProp> configurableProps;
    
    /**
     * Usage example showing how to use the generated component.
     */
    private String usageExample;
    
    /**
     * Represents a configurable prop for the generated component.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComponentProp {
        private String name;
        private String type;
        private String description;
        private boolean required;
        private String defaultValue;
    }
}