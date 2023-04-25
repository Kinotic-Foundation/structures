import {IServiceProxy, IServiceRegistry} from '@kinotic-foundation/continuum-js'
import { Trait } from '@/frontends/structures-admin/pages/structures/traits/Trait'
import { StructureHolder } from '@/frontends/structures-admin/pages/structures/structures/StructureHolder'
import { inject, injectable, container } from 'inversify-props'

export interface IStructureManager {
    save(structureHolder: StructureHolder): Promise<StructureHolder>
    getAll(numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]>
    getAllIdLike(nameLike: string, numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]>
    getStructureById(id: string): Promise<StructureHolder>
    getAllPublishedAndIdLike(idLike: string, numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]>
    getAllPublished(numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]>
    getAllPublishedForNamespace(namespace: string, numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]>
    getAllNamespaceEquals(namespace: string, numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]>
    delete(structureId: string): Promise<void>
    publish(structureId: string): Promise<void>
    unPublish(structureId: string): Promise<StructureHolder>
}

@injectable()
class StructureManager implements IStructureManager {

    private serviceProxy: IServiceProxy

    constructor(@inject() serviceRegistry: IServiceRegistry) {
        this.serviceProxy = serviceRegistry.serviceProxy('org.kinotic.structures.api.services.StructureService')
    }

    public save(structureHolder: StructureHolder): Promise<StructureHolder> {
        return this.serviceProxy.invoke('save', [structureHolder])
    }

    public getAll(numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]> {
        return this.serviceProxy.invoke('getAll', [numberPerPage, page, columnToSortBy, descending])
    }

    public getAllIdLike(idLike: string, numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]> {
        return this.serviceProxy.invoke('getAllIdLike', [idLike, numberPerPage, page, columnToSortBy, descending])
    }

    public getStructureById(id: string): Promise<StructureHolder> {
        return this.serviceProxy.invoke('getStructureById', [id])
    }

    public getAllPublishedAndIdLike(idLike: string, numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]> {
        return this.serviceProxy.invoke('getAllPublishedAndIdLike', [idLike, numberPerPage, page, columnToSortBy, descending])
    }

    public getAllPublishedForNamespace(namespace: string, numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]> {
        return this.serviceProxy.invoke('getAllPublishedForNamespace', [namespace, numberPerPage, page, columnToSortBy, descending])
    }

    public getAllNamespaceEquals(namespace: string, numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]> {
        return this.serviceProxy.invoke('getAllNamespaceEquals', [namespace, numberPerPage, page, columnToSortBy, descending])
    }

    public getAllPublished(numberPerPage: number, page: number, columnToSortBy: string, descending: boolean): Promise<StructureHolder[]> {
        return this.serviceProxy.invoke('getAllPublished', [numberPerPage, page, columnToSortBy, descending])
    }
    public delete(structureId: string): Promise<void> {
        return this.serviceProxy.invoke('delete', [structureId])
    }

    public publish(structureId: string): Promise<void> {
        return this.serviceProxy.invoke('publish', [structureId])
    }

    public unPublish(structureId: string): Promise<StructureHolder> {
        return this.serviceProxy.invoke('unPublish', [structureId])
    }

}

container.addSingleton<IStructureManager>(StructureManager)
