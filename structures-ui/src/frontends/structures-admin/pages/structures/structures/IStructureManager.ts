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
    addTraitToStructure(structureId: string, fieldName: string, newTrait: Trait): Promise<void>
    insertTraitBeforeAnotherForStructure(structureId: string, movingTraitName: string, insertBeforeTraitName: string): Promise<void>
    insertTraitAfterAnotherForStructure(structureId: string, movingTraitName: string, insertAfterTraitName: string): Promise<void>
    getJsonSchema(structureId: string): Promise<string>
    getElasticSearchBaseMapping(structureId: string): Promise<string>
}

@injectable()
class StructureManager implements IStructureManager {

    private serviceProxy: IServiceProxy

    constructor(@inject() serviceRegistry: IServiceRegistry) {
        this.serviceProxy = serviceRegistry.serviceProxy('org.kinotic.structuresserver.structures.IStructureManager')
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

    public addTraitToStructure(structureId: string, fieldName: string, newTrait: Trait): Promise<void> {
        return this.serviceProxy.invoke('addTraitToStructure', [structureId, fieldName, newTrait])
    }

    public insertTraitBeforeAnotherForStructure(structureId: string, movingTraitName: string, insertBeforeTraitName: string): Promise<void> {
        return this.serviceProxy.invoke('insertTraitBeforeAnotherForStructure', [structureId, movingTraitName, insertBeforeTraitName])
    }

    public insertTraitAfterAnotherForStructure(structureId: string, movingTraitName: string, insertAfterTraitName: string): Promise<void> {
        return this.serviceProxy.invoke('insertTraitAfterAnotherForStructure', [structureId, movingTraitName, insertAfterTraitName])
    }

    public getJsonSchema(structureId: string): Promise<string> {
        return this.serviceProxy.invoke('getJsonSchema', [structureId])
    }

    public getElasticSearchBaseMapping(structureId: string): Promise<string> {
        return this.serviceProxy.invoke('getElasticSearchBaseMapping', [structureId])
    }
}

container.addSingleton<IStructureManager>(StructureManager)
