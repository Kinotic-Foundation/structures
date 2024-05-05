import { Namespace } from './domain/Namespace.js';
import { CrudServiceProxy, ICrudServiceProxy } from '@kinotic/continuum-client';

export interface INamespaceService extends ICrudServiceProxy<Namespace> {
    /**
     * Creates a new namespace if it does not already exist.
     * @param id the id of the namespace to create
     * @param description the description of the namespace to create
     * @return {@link Promise} emitting the created namespace
     */
    createNamespaceIfNotExist(id: string, description: string): Promise<Namespace>;
}
export declare class NamespaceService extends CrudServiceProxy<Namespace> implements INamespaceService {
    constructor();
    createNamespaceIfNotExist(id: string, description: string): Promise<Namespace>;
}
