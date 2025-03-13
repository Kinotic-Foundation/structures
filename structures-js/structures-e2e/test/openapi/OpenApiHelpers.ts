import axios, { AxiosResponse } from 'axios';

// Basic interface for an OpenAPI schema (v3.x minimum)
interface BasicOpenAPISchema {
    openapi: string; // Required OpenAPI version (e.g., "3.0.0" or "3.1.0")
    info?: {
        title?: string;
        version?: string;
    };
    paths?: object;
    components?: object;
}

/**
 * Fetches and validates an OpenAPI schema from a given URL
 * @param url The endpoint URL serving the OpenAPI schema
 * @returns Promise resolving to the schema if valid, or throws an error if invalid
 */
export async function loadOpenAPISchema(url: string): Promise<BasicOpenAPISchema> {
    try {
        // Fetch the schema using axios
        const response: AxiosResponse = await axios.get(url, {
            headers: {
                Accept: 'application/json, application/yaml'
            }
        });

        // Get the response data
        const schemaData: unknown = response.data;

        // Validate that it looks like an OpenAPI schema
        if (!isValidOpenAPISchema(schemaData)) {
            throw new Error('Response does not contain a valid OpenAPI schema');
        }

        return schemaData as BasicOpenAPISchema;

    } catch (error) {
        if (axios.isAxiosError(error)) {
            throw new Error(`Failed to fetch schema: ${error.message}`);
        }
        throw error;
    }
}

/**
 * Performs basic validation to check if the data is an OpenAPI schema
 * @param data The data to validate
 * @returns boolean indicating if it's a valid OpenAPI schema
 */
function isValidOpenAPISchema(data: unknown): boolean {
    // Check if data is an object
    if (!data || typeof data !== 'object') {
        return false;
    }

    const schema = data as Record<string, unknown>;

    // Check for required openapi version property
    if (typeof schema.openapi !== 'string' || !schema.openapi.startsWith('3.')) {
        return false;
    }

    // Optional: Check for typical OpenAPI structure
    const hasStructure =
              (schema.info && typeof schema.info === 'object') ||
              (schema.paths && typeof schema.paths === 'object') ||
              (schema.components && typeof schema.components === 'object');

    return true; // We have a valid version, structure is optional for basic validation
}
