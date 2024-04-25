import { Identifiable } from '@kinotic/continuum-client'
import {ServiceDefinition} from '@kinotic/continuum-idl'

/**
 * Provides Metadata that represents Named Queries for a Namespace
 */
export class NamedQueryServiceDefinition implements Identifiable<string> {
    public id: string
    public namespace: string
    public serviceName: string
    public serviceDefinition: ServiceDefinition

    constructor(id: string,
                namespace: string,
                serviceName: string,
                serviceDefinition: ServiceDefinition) {
        this.id = id;
        this.namespace = namespace;
        this.serviceName = serviceName;
        this.serviceDefinition =serviceDefinition;
    }

}

