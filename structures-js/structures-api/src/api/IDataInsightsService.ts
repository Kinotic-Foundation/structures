import { Continuum, IServiceProxy } from '@kinotic/continuum-client';
import { InsightRequest } from '@/api/domain/insights/InsightRequest';
import { InsightResponse } from '@/api/domain/insights/InsightResponse';

/**
 * Provides AI-powered data analysis and visualization code generation capabilities.
 * This service analyzes user queries about their structures and generates appropriate
 * web components with charts and visualizations.
 */
export interface IDataInsightsService {

    /**
     * Processes a user's natural language request to either create a new visualization
     * or modify an existing one. This method implements a chain of steps:
     * 
     * 1. Request Analysis: Determines if the user wants a new visualization or to modify existing
     * 2. New Visualization Path: Generates a new visualization using AI analysis
     * 3. Modify Existing Path: Loads existing visualization and modifies it based on user feedback
     * 
     * @param request the analysis request containing query and context
     * @return Promise with complete analysis results and generated code
     */
    processRequest(request: InsightRequest): Promise<InsightResponse>;
}

export class DataInsightsService implements IDataInsightsService {
    
    private readonly serviceProxy: IServiceProxy;

    constructor() {
        this.serviceProxy = Continuum.serviceProxy('org.kinotic.structures.api.services.insights.DataInsightsService');
    }

    public processRequest(request: InsightRequest): Promise<InsightResponse> {
        return this.serviceProxy.invoke('processRequest', [request]);
    }
}