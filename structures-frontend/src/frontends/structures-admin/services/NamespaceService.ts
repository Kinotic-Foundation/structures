import { ICrudServiceProxy, IServiceProxy, IServiceRegistry, Page, Pageable } from '@kinotic/continuum'
import { container, inject, injectable } from 'inversify-props'
import {Namespace} from '@/frontends/structures-admin/pages/structures/namespaces/Namespace'

/**
 * Role Service
 */
export interface INamespaceService extends ICrudServiceProxy<Namespace> {

    findByIdNotIn(ids: string[], page: Pageable): Promise<Page<Namespace>>

}


@injectable()
export class NamespaceService implements INamespaceService {

    protected serviceProxy: IServiceProxy


    constructor(@inject() serviceRegistry: IServiceRegistry) {
        this.serviceProxy = serviceRegistry.serviceProxy('org.kinotic.structures.api.services.NamespaceService')
    }

    findById(id: string): Promise<Namespace> {
        return this.serviceProxy.invoke('findById', [id])
    }
    deleteById(id: string): Promise<void> {
        return this.serviceProxy.invoke('deleteById', [id])
    }

    public count(): Promise<number> {
        return this.serviceProxy.invoke('count')
    }

    public create(entity: Namespace): Promise<Namespace> {
        return this.serviceProxy.invoke('create', [entity])
    }

    public findAll(pageable: Pageable): Promise<Page<Namespace>> {
        return this.serviceProxy.invoke('findAll', [pageable])
    }

    public save(entity: Namespace): Promise<Namespace> {
        return this.serviceProxy.invoke('save', [entity])
    }

    public findByIdNotIn(ids: string[], page: Pageable): Promise<Page<Namespace>> {
        return this.serviceProxy.invoke('findByIdNotIn',[ids, page])
    }

    public search(searchText: string, pageable: Pageable): Promise<Page<Namespace>> {
        return this.serviceProxy.invoke('search',[searchText, pageable])
    }

}

container.addSingleton<INamespaceService>(NamespaceService)
