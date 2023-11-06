import {Page, Pageable} from '@kinotic/continuum-client'
import {EntitiesService, IEntitiesService} from '@/api/IEntitiesService.js'

/**
 * This is the base interface for all entity services.
 * It provides the basic CRUD operations for entities.
 */
export interface IEntityService<T> {

    /**
     * The namespace of the structure this service is for
     */
    structureNamespace: string

    /**
     * The name of the structure this service is for
     */
    structureName: string

    /**
     * Saves a given entity. This will override all data if there is an existing entity with the same id.
     * Use the returned instance for further operations as the save operation might have changed the entity instance.
     *
     * @param entity  must not be {@literal null}
     * @return Promise emitting the saved entity
     * @throws Error in case the given {@literal entity} is {@literal null}
     */
    save(entity: T): Promise<T>;

    /**
     * Saves all given entities.
     * @param entities all the entities to save
     * @return Promise that will complete when all entities have been saved
     */
    bulkSave(entities: T[]): Promise<void>;

    /**
     * Updates a given entity. This will only override the fields that are present in the given entity.
     * If any fields are not present in the given entity data they will not be changed.
     * If the entity does not exist it will be created.
     * Use the returned instance for further operations as the save operation might have changed the entity instance.
     *
     * @param entity      must not be {@literal null}
     * @return Promise emitting the saved entity
     * @throws Error in case the given {@literal entity} is {@literal null}
     */
    update(entity: T): Promise<T>;

    /**
     * Updates all given entities.
     * @param entities all the entities to save
     * @return Promise that will complete when all entities have been saved
     */
    bulkUpdate(entities: T[]): Promise<void>;

    /**
     * Retrieves an entity by its id.
     *
     * @param id      must not be {@literal null}
     * @return Promise emitting the entity with the given id or Promise emitting null if none found
     * @throws Error in case the given {@literal id} is {@literal null}
     */
    findById(id: string): Promise<T>;

    /**
     * Returns the number of entities available.
     *
     * @return Promise emitting the number of entities
     */
    count(): Promise<number>;

    /**
     * Deletes the entity with the given id.
     *
     * @param id      must not be {@literal null}
     * @return Promise signaling when operation has completed
     * @throws Error in case the given {@literal id} is {@literal null}
     */
    deleteById(id: string): Promise<void>;

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@link Pageable} object.
     *
     * @param pageable the page settings to be used
     * @return a page of entities
     */
    findAll(pageable: Pageable): Promise<Page<T>>;

    /**
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@link Pageable} object.
     *
     * @param searchText the text to search for entities for
     * @param pageable   the page settings to be used
     * @return a page of entities
     */
    search(searchText: string, pageable: Pageable): Promise<Page<T>>;

}

/**
 * This is the base class for all entity services.
 * It provides the basic CRUD operations for entities.
 */
export class EntityService<T> implements IEntityService<T>{

    public structureNamespace: string
    public structureName: string

    private entitiesService: IEntitiesService
    private readonly structuresId: string

    public constructor(structureNamespace: string, structureName: string) {
        this.structureNamespace = structureNamespace
        this.structureName = structureName
        this.structuresId = (structureNamespace + '.' + structureName).toLowerCase()
        this.entitiesService = new EntitiesService()
    }

    protected async beforeSaveOrUpdate(entity: T): Promise<T>{
        return Promise.resolve(entity)
    }

    protected async beforeBulkSaveOrUpdate(entities: T[]): Promise<T[]>{
        return Promise.resolve(entities)
    }

    public async save(entity: T): Promise<T>{
        const entityToSave = await this.beforeSaveOrUpdate(entity)
        return this.entitiesService.save(this.structuresId, entityToSave)
    }

    public async bulkSave(entities: T[]): Promise<void>{
        const entitiesToSave = await this.beforeBulkSaveOrUpdate(entities)
        return this.entitiesService.bulkSave(this.structuresId, entitiesToSave)
    }

    public async update(entity: T): Promise<T>{
        const entityToSave = await this.beforeSaveOrUpdate(entity)
        return this.entitiesService.update(this.structuresId, entityToSave)
    }

    public async bulkUpdate(entities: T[]): Promise<void>{
        const entitiesToSave = await this.beforeBulkSaveOrUpdate(entities)
        return this.entitiesService.bulkUpdate(this.structuresId, entitiesToSave)
    }

    public findById(id: string): Promise<T>{
        return this.entitiesService.findById(this.structuresId, id)
    }

    public count(): Promise<number>{
        return this.entitiesService.count(this.structuresId)
    }

    public deleteById(id: string): Promise<void>{
        return this.entitiesService.deleteById(this.structuresId, id)
    }

    public findAll(pageable: Pageable): Promise<Page<T>>{
        return this.entitiesService.findAll(this.structuresId, pageable)
    }

    public search(searchText: string, pageable: Pageable): Promise<Page<T>>{
        return this.entitiesService.search(this.structuresId, searchText, pageable)
    }
}
