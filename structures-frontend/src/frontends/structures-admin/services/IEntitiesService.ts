import {Continuum, IServiceProxy, Page, Pageable} from '@kinotic/continuum'


export interface IEntitiesService {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param structureId the id of the structure to save the entity for
     * @param entity      must not be {@literal null}
     * @param context     the context for this operation
     * @return {@link Promise} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    save(structureId: string, entity: any, context?: any | null): Promise<any>


    /**
     * Retrieves an entity by its id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @param context     the context for this operation
     * @return {@link Promise} with the entity with the given id or {@link Promise} emitting null if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    findById(structureId: string, id: string, context?: any | null): Promise<any>

    /**
     * Returns the number of entities available.
     * @param structureId the id of the structure to count
     * @param context     the context for this operation
     * @return {@link Promise} emitting the number of entities.
     */
    count(structureId: string, context?: any | null): Promise<number>

    /**
     * Deletes the entity with the given id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @param context     the context for this operation
     * @return {@link Promise} emitting when delete is complete
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    deleteById(structureId: string, id: string, context?: any | null): Promise<void>

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param structureId the id of the structure to save the entity for
     * @param pageable    the page settings to be used
     * @param context     the context for this operation
     * @return a page of entities
     */
    findAll(structureId: string, pageable: Pageable, context?: any | null): Promise<Page<any>>

    /**
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@code Pageable} object.
     * <p>
     * You can find more information about the search syntax <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-query-string-query.html#query-string-syntax">here</a>
     *
     * @param structureId the id of the structure to save the entity for
     * @param searchText  the text to search for entities for
     * @param pageable    the page settings to be used
     * @param context     the context for this operation
     * @return a page of entities
     */
    search(structureId: string, searchText: string, pageable: Pageable, context?: any | null): Promise<Page<any>>

}

export class EntitiesService implements IEntitiesService {

    protected serviceProxy: IServiceProxy

    constructor(serviceIdentifier: string) {
        this.serviceProxy = Continuum.serviceProxy(serviceIdentifier)
    }

    public save(structureId: string, entity: any, context: any | null = null): Promise<any> {
        return this.serviceProxy.invoke('save', [structureId, entity, context])
    }

    public findById(structureId: string, id: string, context: any | null = null): Promise<any> {
        return this.serviceProxy.invoke('findById', [structureId, id, 'org.kinotic.structures.api.domain.RawJson', context])
    }

    public count(structureId: string, context: any | null = null): Promise<number> {
        return this.serviceProxy.invoke('count', [structureId, context])
    }

    public deleteById(structureId: string, id: string, context: any | null = null): Promise<void> {
        return this.serviceProxy.invoke('deleteById', [structureId, id, context])
    }

    public findAll(structureId: string, pageable: Pageable, context: any | null = null): Promise<Page<any>> {
        return this.serviceProxy.invoke('findAll', [structureId, pageable, 'org.kinotic.structures.api.domain.RawJson', context])
    }

    public search(structureId: string, searchText: string, pageable: Pageable, context: any | null = null): Promise<Page<any>> {
        return this.serviceProxy.invoke('search', [structureId, searchText, pageable, 'org.kinotic.structures.api.domain.RawJson', context])
    }

}

