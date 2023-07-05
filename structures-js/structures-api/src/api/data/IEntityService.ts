import {Page, Pageable} from '@kinotic/continuum-client'


/**
 * This is the base interface for all entity services.
 * It provides the basic CRUD operations for entities.
 */
export interface IEntityService<T> {

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
