import { Identifiable } from '@kinotic/continuum-client';
import { ObjectC3Type } from '@kinotic/continuum-idl';

export declare class Structure implements Identifiable<string> {
    id: string | null;
    namespace: string;
    name: string;
    entityDefinition: ObjectC3Type;
    description?: string | null;
    created: number;
    updated: number;
    published: boolean;
    publishedTimestamp: number;
    constructor(namespace: string, name: string, entityDefinition: ObjectC3Type, description?: string | null);
}
