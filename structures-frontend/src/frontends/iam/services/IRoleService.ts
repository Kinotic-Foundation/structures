import { ICrudServiceProxy, IServiceProxy, IServiceRegistry, Page, Pageable } from '@kinotic-foundation/continuum-js'
import { Role } from '@/frontends/iam/models/Role'
import { container, inject, injectable } from 'inversify-props'
import { ServiceIdentifierConstants } from '@/frontends/iam/Constants'

/**
 * Role Service
 */
export interface IRoleService extends ICrudServiceProxy<Role> {

    findByIdNotIn(ids: string[], page: Pageable): Promise<Page<Role>>

}


@injectable()
export class RoleService implements IRoleService {

    protected serviceProxy: IServiceProxy


    constructor(@inject() serviceRegistry: IServiceRegistry) {
        this.serviceProxy = serviceRegistry.serviceProxy(ServiceIdentifierConstants.ROLE_SERVICE)
    }

    public count(): Promise<number> {
        return this.serviceProxy.invoke('count')
    }

    public create(entity: Role): Promise<Role> {
        return this.serviceProxy.invoke('create', [entity])
    }

    public deleteByIdentity(identity: string): Promise<void> {
        return this.serviceProxy.invoke('deleteByIdentity', [identity])
    }

    public findAll(pageable: Pageable): Promise<Page<Role>> {
        return this.serviceProxy.invoke('findAll', [pageable])
    }

    public findByIdentity(identity: string): Promise<Role> {
        return this.serviceProxy.invoke('findByIdentity', [identity])
    }

    public save(entity: Role): Promise<Role> {
        return this.serviceProxy.invoke('save', [entity])
    }

    public findByIdNotIn(ids: string[], page: Pageable): Promise<Page<Role>> {
        return this.serviceProxy.invoke('findByIdNotIn',[ids, page])
    }

    public search(searchText: string, pageable: Pageable): Promise<Page<Role>> {
        return this.serviceProxy.invoke('search',[searchText, pageable])
    }

}

container.addSingleton<IRoleService>(RoleService)
