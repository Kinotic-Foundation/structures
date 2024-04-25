import { CrudServiceProxy, ICrudServiceProxy } from '@kinotic/continuum-client';
import { NamedQueryServiceDefinition } from './domain/NamedQueryServiceDefinition';

export interface INamedQueriesService extends ICrudServiceProxy<NamedQueryServiceDefinition> {
}
export declare class NamedQueriesService extends CrudServiceProxy<NamedQueryServiceDefinition> implements INamedQueriesService {
    constructor();
}
