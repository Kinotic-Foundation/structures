import {IServiceProxy, IServiceRegistry} from '@kinotic-foundation/continuum-js'
import {Namespace} from './Namespace'
import { inject, injectable, container } from 'inversify-props'

export interface INamespaceManager {

    save(saveNamespace: Namespace): Promise<Namespace>
    getNamespace(namespace: string): Promise<Namespace>
    getAll(numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<any>
    getAllNamespaceLike(namespaceLike: string, numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<any>
    delete(namespace: string): Promise<void>

}

@injectable()
class NamespaceManager implements INamespaceManager {

    private serviceProxy: IServiceProxy

    constructor(@inject() serviceRegistry: IServiceRegistry) {
        this.serviceProxy = serviceRegistry.serviceProxy('org.kinotic.structuresserver.namespace.INamespaceManager')
    }

    public save(saveNamespace: Namespace): Promise<Namespace> {
        return this.serviceProxy.invoke('save', [saveNamespace])
    }

    public getNamespace(namespace: string): Promise<Namespace> {
        return this.serviceProxy.invoke('getNamespace', [namespace])
    }

    public getAll(numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<any> {
        return this.serviceProxy.invoke('getAll', [numberPerPage, page, columnToSortBy, descending])
    }

    public getAllNamespaceLike(namespaceLike: string, numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<any> {
        return this.serviceProxy.invoke('getAllNamespaceLike', [namespaceLike, numberPerPage, page, columnToSortBy, descending])
    }

    public delete(namespace: string): Promise<void> {
        return this.serviceProxy.invoke('delete', [namespace])
    }

}

container.addSingleton<INamespaceManager>(NamespaceManager)
