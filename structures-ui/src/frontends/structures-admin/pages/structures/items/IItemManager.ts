import {IServiceProxy, IServiceRegistry} from '@kinotic-foundation/continuum-js'
import { inject, injectable, container } from 'inversify-props'

export interface IItemManager {
    upsertItem(structureId: string, item: any): Promise<any>

    count(structureId: string): Promise<number>

    getItemById(structureId: string, id: string): Promise<any>

    searchForItemsByIdForStructureId(structureId: string, ids: string[]): Promise<any>

    searchForItemsById(structureId: string, ids: string[]): Promise<any>

    getAll(structureId: string, numberPerPage: number, from: number): Promise<any>

    searchTerms(structureId: string, numberPerPage: number, from: number, fieldName: string, searchTerms: any[]): Promise<any>

    searchFullText(structureId: string, numberPerPage: number, from: number, search: string, fieldNames: string[]): Promise<any>

    search(structureId: string, search: string, numberPerPage: number, from: number): Promise<any>

    searchWithSort(structureId: string, search: string, numberPerPage: number, from: number, sortField: string, sortOrder: string): Promise<any>

    delete(structureId: string, itemId: string): Promise<void>

    requestBulkUpdatesForStructure(structureId: string): Promise<void>

    pushItemForBulkUpdate(structureId: string, item: any): Promise<void>

    flushAndCloseBulkUpdate(structureId: string): Promise<void>
}

@injectable()
class ItemManager implements IItemManager {

    private serviceProxy: IServiceProxy

    constructor(@inject() serviceRegistry: IServiceRegistry) {
        this.serviceProxy = serviceRegistry.serviceProxy('org.kinotic.structures.api.services.ItemService')
    }

    public upsertItem(structureId: string, item: any): Promise<any> {
        return this.serviceProxy.invoke('upsertItem', [structureId, item])
    }

    public count(structureId: string): Promise<number> {
        return this.serviceProxy.invoke('count', [structureId])
    }

    public getItemById(structureId: string, id: string): Promise<any> {
        return this.serviceProxy.invoke('getItemById', [structureId, id])
    }

    public searchForItemsByIdForStructureId(structureId: string, ids: string[]): Promise<any> {
        return this.serviceProxy.invoke('searchForItemsByIdForStructureId', [structureId, ids])
    }

    public searchForItemsById(structureId: string, ids: string[]): Promise<any> {
        return this.serviceProxy.invoke('searchForItemsById', [structureId, ids])
    }

    public getAll(structureId: string, numberPerPage: number, from: number): Promise<any> {
        return this.serviceProxy.invoke('getAll', [structureId, numberPerPage, from])
    }

    public searchTerms(structureId: string, numberPerPage: number, from: number, fieldName: string, searchTerms: any[]): Promise<any> {
        return this.serviceProxy.invoke('searchTerms', [structureId, numberPerPage, from, fieldName, searchTerms])
    }

    public searchFullText(structureId: string, numberPerPage: number, from: number, search: string, fieldNames: string[]): Promise<any> {
        return this.serviceProxy.invoke('searchFullText', [structureId, numberPerPage, from, search, fieldNames])
    }

    public search(structureId: string, search: string, numberPerPage: number, from: number): Promise<any> {
        return this.serviceProxy.invoke('search', [structureId, search, numberPerPage, from])
    }

    public searchWithSort(structureId: string, search: string, numberPerPage: number, from: number, sortField: string, sortOrder: string): Promise<any> {
        return this.serviceProxy.invoke('searchWithSort', [structureId, search, numberPerPage, from, sortField, sortOrder])
    }

    public delete(structureId: string, itemId: string): Promise<void> {
        return this.serviceProxy.invoke('delete', [structureId, itemId])
    }

    public requestBulkUpdatesForStructure(structureId: string): Promise<void> {
        return this.serviceProxy.invoke('requestBulkUpdatesForStructure', [structureId])
    }

    public pushItemForBulkUpdate(structureId: string, item: any): Promise<void> {
        return this.serviceProxy.invoke('pushItemForBulkUpdate', [structureId, item])
    }

    public flushAndCloseBulkUpdate(structureId: string): Promise<void> {
        return this.serviceProxy.invoke('flushAndCloseBulkUpdate', [structureId])
    }

}

container.addSingleton<IItemManager>(ItemManager)
