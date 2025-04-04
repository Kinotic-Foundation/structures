import {Continuum, CrudServiceProxy, ICrudServiceProxy,} from '@kinotic/continuum-client'
import {Namespace} from '@/api/domain/Namespace'


export interface INamespaceService extends ICrudServiceProxy<Namespace> {

    /**
     * Creates a new namespace if it does not already exist.
     * @param id the id of the namespace to create
     * @param description the description of the namespace to create
     * @return {@link Promise} emitting the created namespace
     */
    createNamespaceIfNotExist(id: string, description: string): Promise<Namespace>

    /**
     * This operation makes all the recent writes immediately available for search.
     * @return a Promise that resolves when the operation is complete
     */
    syncIndex(): Promise<void>

}

export class NamespaceService extends CrudServiceProxy<Namespace> implements INamespaceService{

    constructor() {
        super(Continuum.serviceProxy('org.kinotic.structures.api.services.NamespaceService'))
    }

    public createNamespaceIfNotExist(id: string, description: string): Promise<Namespace> {
        return this.serviceProxy.invoke('createNamespaceIfNotExist', [id, description])
    }

    public syncIndex(): Promise<void> {
        return this.serviceProxy.invoke('syncIndex', [])
    }

}
