/**
 * Status of the visualization generation.
 */
export enum AnalysisStatus {
    PENDING = 'PENDING',
    SUCCESS = 'SUCCESS',
    ERROR = 'ERROR'
}

/**
 * Lightweight response for data insights containing just the visualization
 * and essential metadata for rendering to end users.
 *
 * This is designed for "data insights lite" mode where users simply want
 * to see their data visualized without any code or technical details.
 */
export interface InsightResponse {

    /**
     * Unique identifier for this visualization.
     */
    id: string;

    /**
     * The complete web component JavaScript code ready to execute.
     * This is self-contained code that can be executed to create the visualization.
     */
    htmlContent: string;

    /**
     * When this visualization was created.
     */
    createdAt: string; // ISO date string

    /**
     * Status of the visualization generation.
     */
    status: AnalysisStatus;

    /**
     * User-friendly error message if status is ERROR.
     */
    errorMessage?: string;
}