import { ICrudServiceProxy, IServiceRegistry, Pageable, Page, IServiceProxy } from '@kinotic-foundation/continuum-js'
import { inject, injectable, container } from 'inversify-props'
import { AccessPolicy } from '@/frontends/iam/models/AccessPolicy'
import { ServiceIdentifierConstants } from '@/frontends/iam/Constants'


/**
 * Access Policy Service
 */
export interface IAccessPolicyService extends ICrudServiceProxy<AccessPolicy> {

    findByIdNotIn(ids: string[], page: Pageable): Promise<Page<AccessPolicy>>

}

@injectable()
export class AccessPolicyService implements IAccessPolicyService {

    protected serviceProxy: IServiceProxy


    constructor(@inject() serviceRegistry: IServiceRegistry) {
        this.serviceProxy = serviceRegistry.serviceProxy(ServiceIdentifierConstants.ACCESS_POLICY_SERVICE)
    }

    public count(): Promise<number> {
        return this.serviceProxy.invoke('count')
    }

    public create(entity: AccessPolicy): Promise<AccessPolicy> {
        return this.serviceProxy.invoke('create', [entity])
    }

    public deleteByIdentity(identity: string): Promise<void> {
        return this.serviceProxy.invoke('deleteByIdentity', [identity])
    }

    public findAll(pageable: Pageable): Promise<Page<AccessPolicy>> {
        return this.serviceProxy.invoke('findAll', [pageable])
    }

    public findByIdentity(identity: string): Promise<AccessPolicy> {
        return this.serviceProxy.invoke('findByIdentity', [identity])
    }

    public save(entity: AccessPolicy): Promise<AccessPolicy> {
        return this.serviceProxy.invoke('save', [entity])
    }

    public findByIdNotIn(ids: string[], page: Pageable): Promise<Page<AccessPolicy>> {
        return (this.serviceProxy as IServiceProxy).invoke('findByIdNotIn', [ids, page])
    }

    public search(searchText: string, pageable: Pageable): Promise<Page<AccessPolicy>> {
        return this.serviceProxy.invoke('search', [searchText, pageable])
    }


}

container.addSingleton<IAccessPolicyService>(AccessPolicyService)
