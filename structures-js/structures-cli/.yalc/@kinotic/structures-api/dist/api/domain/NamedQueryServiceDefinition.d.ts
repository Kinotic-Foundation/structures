import { ServiceDefinition } from '@kinotic/continuum-idl';
import { Identifiable } from '@kinotic/continuum-client';

/**
 * Provides Metadata that represents Named Queries for a Namespace
 */
export declare class NamedQueryServiceDefinition implements Identifiable<string> {
    id: string;
    namespace: string;
    serviceName: string;
    serviceDefinition: ServiceDefinition;
    constructor(id: string, namespace: string, serviceName: string, serviceDefinition: ServiceDefinition);
}
