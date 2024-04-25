import { IServiceProxy, Page, Pageable, IterablePage, IServiceRegistry } from '@kinotic/continuum-client';

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
    save<T>(structureId: string, entity: T): Promise<T>;
    /**
     * Saves all given entities.
     * @param structureId the id of the structure to save the entities for
     * @param entities all the entities to save
     * @return Promise that will complete when all entities have been saved
     */
    bulkSave<T>(structureId: string, entities: T[]): Promise<void>;
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
    update<T>(structureId: string, entity: T): Promise<T>;
    /**
     * Updates all given entities.
     * @param structureId the id of the structure to update the entities for
     * @param entities all the entities to save
     * @return Promise that will complete when all entities have been saved
     */
    bulkUpdate<T>(structureId: string, entities: T[]): Promise<void>;
    /**
     * Retrieves an entity by its id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @return {@link Promise} with the entity with the given id or {@link Promise} emitting null if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    findById<T>(structureId: string, id: string): Promise<T>;
    /**
     * Retrieves a list of entities by their id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param ids      must not be {@literal null}
     * @return Promise emitting the entities with the given ids or Promise emitting null if none found
     * @throws Error in case the given {@literal ids} is {@literal null}
     */
    findByIds<T>(structureId: string, ids: string[]): Promise<T[]>;
    /**
     * Returns the number of entities available.
     * @param structureId the id of the structure to count
     * @return {@link Promise} emitting the number of entities.
     */
    count(structureId: string): Promise<number>;
    /**
     * Returns the number of entities available for the given query.
     *
     * @param structureId the id of the structure to save the entity for
     * @param query       the query used to limit result
     * @return Promise    emitting the number of entities
     */
    countByQuery(structureId: string, query: string): Promise<number>;
    /**
     * Deletes the entity with the given id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @return {@link Promise} emitting when delete is complete
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    deleteById(structureId: string, id: string): Promise<void>;
    /**
     * Deletes the entity with the given id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param query      the query used to filter records to delete, must not be {@literal null}
     * @return Promise signaling when operation has completed
     * @throws Error in case the given {@literal query} is {@literal null}
     */
    deleteByQuery(structureId: string, query: string): Promise<void>;
    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param structureId the id of the structure to save the entity for
     * @param pageable    the page settings to be used
     * @return a page of entities
     */
    findAll<T>(structureId: string, pageable: Pageable): Promise<IterablePage<T>>;
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
    search<T>(structureId: string, searchText: string, pageable: Pageable): Promise<IterablePage<T>>;
}
export declare class EntitiesService implements IEntitiesService {
    protected serviceProxy: IServiceProxy;
    constructor(serviceRegistry?: IServiceRegistry);
    save<T>(structureId: string, entity: T): Promise<T>;
    bulkSave<T>(structureId: string, entities: T[]): Promise<void>;
    update<T>(structureId: string, entity: T): Promise<T>;
    bulkUpdate<T>(structureId: string, entities: T[]): Promise<void>;
    findById<T>(structureId: string, id: string): Promise<T>;
    findByIds<T>(structureId: string, ids: string[]): Promise<T[]>;
    count(structureId: string): Promise<number>;
    countByQuery(structureId: string, query: string): Promise<number>;
    deleteById(structureId: string, id: string): Promise<void>;
    deleteByQuery(structureId: string, query: string): Promise<void>;
    findAll<T>(structureId: string, pageable: Pageable): Promise<IterablePage<T>>;
    findAllSinglePage<T>(structureId: string, pageable: Pageable): Promise<IterablePage<T>>;
    search<T>(structureId: string, searchText: string, pageable: Pageable): Promise<IterablePage<T>>;
    searchSinglePage<T>(structureId: string, searchText: string, pageable: Pageable): Promise<Page<T>>;
}
export declare const EntitiesServiceSingleton: IEntitiesService;
