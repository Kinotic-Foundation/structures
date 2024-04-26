import { IEntitiesService } from './IEntitiesService';
import { Pageable, IterablePage } from '@kinotic/continuum-client';
import { QueryParameter } from './domain/QueryParameter.js';

/**
 * This is the base interface for all entity services.
 * It provides the basic CRUD operations for entities.
 */
export interface IEntityService<T> {
    /**
     * The namespace of the structure this service is for
     */
    structureNamespace: string;
    /**
     * The name of the structure this service is for
     */
    structureName: string;
    /**
     * Saves all given entities.
     * @param entities all the entities to save
     * @return Promise that will complete when all entities have been saved
     */
    bulkSave(entities: T[]): Promise<void>;
    /**
     * Updates all given entities.
     * @param entities all the entities to save
     * @return Promise that will complete when all entities have been saved
     */
    bulkUpdate(entities: T[]): Promise<void>;
    /**
     * Returns the number of entities available.
     *
     * @return Promise emitting the number of entities
     */
    count(): Promise<number>;
    /**
     * Returns the number of entities available for the given query.
     *
     * @param query       the query used to limit result
     * @return Promise    emitting the number of entities
     */
    countByQuery(query: string): Promise<number>;
    /**
     * Deletes the entity with the given id.
     *
     * @param id      must not be {@literal null}
     * @return Promise signaling when operation has completed
     * @throws Error in case the given {@literal id} is {@literal null}
     */
    deleteById(id: string): Promise<void>;
    /**
     * Deletes the entity with the given id.
     *
     * @param query      the query used to filter records to delete, must not be {@literal null}
     * @return Promise signaling when operation has completed
     * @throws Error in case the given {@literal query} is {@literal null}
     */
    deleteByQuery(query: string): Promise<void>;
    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@link Pageable} object.
     *
     * @param pageable the page settings to be used
     * @return a page of entities
     */
    findAll(pageable: Pageable): Promise<IterablePage<T>>;
    /**
     * Retrieves an entity by its id.
     *
     * @param id      must not be {@literal null}
     * @return Promise emitting the entity with the given id or Promise emitting null if none found
     * @throws Error in case the given {@literal id} is {@literal null}
     */
    findById(id: string): Promise<T>;
    /**
     * Retrieves a list of entities by their id.
     *
     * @param ids      must not be {@literal null}
     * @return Promise emitting the entities with the given ids or Promise emitting null if none found
     * @throws Error in case the given {@literal ids} is {@literal null}
     */
    findByIds(ids: string[]): Promise<T[]>;
    /**
     * Executes a named query.
     * @param queryName the name of the function that defines the query
     * @param parameters to pass to the query
     * @returns Promise with the result of the query
     */
    namedQuery<U>(queryName: string, parameters: QueryParameter[]): Promise<U>;
    /**
     * Executes a named query and returns a Page of results.
     * @param queryName the name of the function that defines the query
     * @param parameters to pass to the query
     * @param pageable the page settings to be used
     * @returns Promise with the result of the query
     */
    namedQueryPage<U>(queryName: string, parameters: QueryParameter[], pageable: Pageable): Promise<IterablePage<U>>;
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
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@link Pageable} object.
     *
     * @param searchText the text to search for entities for
     * @param pageable   the page settings to be used
     * @return a page of entities
     */
    search(searchText: string, pageable: Pageable): Promise<IterablePage<T>>;
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
}
/**
 * This is the base class for all entity services.
 * It provides the basic CRUD operations for entities.
 */
export declare class EntityService<T> implements IEntityService<T> {
    structureNamespace: string;
    structureName: string;
    private entitiesService;
    private readonly structuresId;
    constructor(structureNamespace: string, structureName: string, entitiesService?: IEntitiesService);
    bulkSave(entities: T[]): Promise<void>;
    bulkUpdate(entities: T[]): Promise<void>;
    count(): Promise<number>;
    countByQuery(query: string): Promise<number>;
    deleteById(id: string): Promise<void>;
    deleteByQuery(query: string): Promise<void>;
    findAll(pageable: Pageable): Promise<IterablePage<T>>;
    findById(id: string): Promise<T>;
    findByIds(ids: string[]): Promise<T[]>;
    namedQuery<U>(queryName: string, parameters: QueryParameter[]): Promise<U>;
    namedQueryPage<U>(queryName: string, parameters: QueryParameter[], pageable: Pageable): Promise<IterablePage<U>>;
    save(entity: T): Promise<T>;
    search(searchText: string, pageable: Pageable): Promise<IterablePage<T>>;
    update(entity: T): Promise<T>;
    protected beforeBulkSaveOrUpdate(entities: T[]): Promise<T[]>;
    protected beforeSaveOrUpdate(entity: T): Promise<T>;
}
