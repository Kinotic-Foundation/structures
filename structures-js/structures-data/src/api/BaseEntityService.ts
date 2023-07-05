import {Continuum, IServiceProxy, Page, Pageable} from '@kinotic/continuum-client'
import {IEntityService} from '@/api/IEntityService.js'


/**
 * This is the base class for all entity services.
 * It provides the basic CRUD operations for entities.
 */
export abstract class BaseEntityService<T> implements IEntityService<T>{

    private serviceProxy: IServiceProxy
    private readonly structuresId: string

    protected constructor(namespace: string, entityName: string) {
        this.structuresId = (namespace + '.' + entityName).toLowerCase()
        this.serviceProxy = Continuum.serviceProxy("org.kinotic.structures.api.services.JsonEntitiesService")
    }

    public save(entity: T): Promise<T>{
        return this.serviceProxy.invoke('save', [this.structuresId, entity])
    }

    public bulkSave(entities: T[]): Promise<void>{
        return this.serviceProxy.invoke('bulkSave', [this.structuresId, entities])
    }

    public update(entity: T): Promise<T>{
        return this.serviceProxy.invoke('update', [this.structuresId, entity])
    }

    public bulkUpdate(entities: T[]): Promise<void>{
        return this.serviceProxy.invoke('bulkUpdate', [this.structuresId, entities])
    }

    public findById(id: string): Promise<T>{
        return this.serviceProxy.invoke('findById', [this.structuresId, id])
    }

    public count(): Promise<number>{
        return this.serviceProxy.invoke('count', [this.structuresId])
    }

    public deleteById(id: string): Promise<void>{
        return this.serviceProxy.invoke('deleteById', [this.structuresId, id])
    }

    public findAll(pageable: Pageable): Promise<Page<T>>{
        return this.serviceProxy.invoke('findAll', [this.structuresId, pageable])
    }

    public search(searchText: string, pageable: Pageable): Promise<Page<T>>{
        return this.serviceProxy.invoke('search', [this.structuresId, searchText, pageable])
    }

}
