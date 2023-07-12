import {Continuum, IServiceProxy, Page, Pageable} from '@kinotic/continuum-client'

export interface IEntitiesService {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param structureId the id of the structure to save the entity for
     * @param entity      must not be {@literal null}
     * @return {@link Promise} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    save<T>(structureId: string, entity: T): Promise<T>

    /**
     * Saves all given entities.
     * @param structureId the id of the structure to save the entities for
     * @param entities all the entities to save
     * @return Promise that will complete when all entities have been saved
     */
    bulkSave<T>(structureId: string, entities: T[]): Promise<void>

    /**
     * Updates a given entity. This will only override the fields that are present in the given entity.
     * If any fields are not present in the given entity data they will not be changed.
     * If the entity does not exist it will be created.
     * Use the returned instance for further operations as the save operation might have changed the entity instance.
     *
     * @param structureId the id of the structure to update the entity for
     * @param entity      must not be {@literal null}
     * @return Promise emitting the saved entity
     * @throws Error in case the given {@literal entity} is {@literal null}
     */
    update<T>(structureId: string, entity: T): Promise<T>

    /**
     * Updates all given entities.
     * @param structureId the id of the structure to update the entities for
     * @param entities all the entities to save
     * @return Promise that will complete when all entities have been saved
     */
    bulkUpdate<T>(structureId: string, entities: T[]): Promise<void>

    /**
     * Retrieves an entity by its id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @return {@link Promise} with the entity with the given id or {@link Promise} emitting null if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    findById<T>(structureId: string, id: string): Promise<T>

    /**
     * Returns the number of entities available.
     * @param structureId the id of the structure to count
     * @return {@link Promise} emitting the number of entities.
     */
    count(structureId: string): Promise<number>

    /**
     * Deletes the entity with the given id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @return {@link Promise} emitting when delete is complete
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    deleteById(structureId: string, id: string): Promise<void>

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param structureId the id of the structure to save the entity for
     * @param pageable    the page settings to be used
     * @return a page of entities
     */
    findAll<T>(structureId: string, pageable: Pageable): Promise<Page<T>>

    /**
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@code Pageable} object.
     * <p>
     * You can find more information about the search syntax <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-query-string-query.html#query-string-syntax">here</a>
     *
     * @param structureId the id of the structure to save the entity for
     * @param searchText  the text to search for entities for
     * @param pageable    the page settings to be used
     * @return a page of entities
     */
    search<T>(structureId: string, searchText: string, pageable: Pageable): Promise<Page<T>>

}

export class EntitiesService implements IEntitiesService {

    protected serviceProxy: IServiceProxy

     constructor() {
        this.serviceProxy = Continuum.serviceProxy('org.kinotic.structures.api.services.JsonEntitiesService')
    }

    public save<T>(structureId: string, entity: T): Promise<T> {
        return this.serviceProxy.invoke('save', [structureId, entity])
    }

    public bulkSave<T>(structureId: string, entities: T[]): Promise<void>{
        return this.serviceProxy.invoke('bulkSave', [structureId, entities])
    }

    public update<T>(structureId: string, entity: T): Promise<T>{
        return this.serviceProxy.invoke('update', [structureId, entity])
    }

    public bulkUpdate<T>(structureId: string, entities: T[]): Promise<void>{
        return this.serviceProxy.invoke('bulkUpdate', [structureId, entities])
    }

    public findById<T>(structureId: string, id: string): Promise<T> {
        return this.serviceProxy.invoke('findById', [structureId, id])
    }

    public count(structureId: string): Promise<number> {
        return this.serviceProxy.invoke('count', [structureId])
    }

    public deleteById(structureId: string, id: string): Promise<void> {
        return this.serviceProxy.invoke('deleteById', [structureId, id])
    }

    public findAll<T>(structureId: string, pageable: Pageable): Promise<Page<T>> {
        return this.serviceProxy.invoke('findAll', [structureId, pageable])
    }

    public search<T>(structureId: string, searchText: string, pageable: Pageable): Promise<Page<T>> {
        return this.serviceProxy.invoke('search', [structureId, searchText, pageable])
    }
}
