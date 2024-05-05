import { Identifiable } from '@kinotic/continuum-client'
import {FunctionDefinition} from '@kinotic/continuum-idl'

/**
 * Provides Metadata that represents Named Queries for a Namespace
 */
export class NamedQueriesDefinition implements Identifiable<string> {
    public id: string
    public namespace: string
    public structure: string
    public namedQueries: FunctionDefinition[]

    constructor(id: string,
                namespace: string,
                structure: string,
                namedQueries: FunctionDefinition[]) {
        this.id = id;
        this.namespace = namespace;
        this.structure = structure;
        this.namedQueries = namedQueries;
    }

}

