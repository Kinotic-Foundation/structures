import { CrudServiceProxy, ICrudServiceProxy } from '@kinotic/continuum-client';
import { NamedQueriesDefinition } from './domain/NamedQueriesDefinition.js';

export interface INamedQueriesService extends ICrudServiceProxy<NamedQueriesDefinition> {
}
export declare class NamedQueriesService extends CrudServiceProxy<NamedQueriesDefinition> implements INamedQueriesService {
    constructor();
}
