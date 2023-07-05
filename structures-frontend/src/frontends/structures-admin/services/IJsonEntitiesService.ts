import {Continuum, IServiceProxy, Page, Pageable} from '@kinotic/continuum-client'


export interface IJsonEntitiesService {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param structureId the id of the structure to save the entity for
     * @param entity      must not be {@literal null}
     * @return {@link Promise} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    save(structureId: string, entity: any): Promise<any>


    /**
     * Retrieves an entity by its id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @return {@link Promise} with the entity with the given id or {@link Promise} emitting null if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    findById(structureId: string, id: string): Promise<any>

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
    findAll(structureId: string, pageable: Pageable): Promise<Page<any>>

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
    search(structureId: string, searchText: string, pageable: Pageable): Promise<Page<any>>

}

export class JsonEntitiesService implements IJsonEntitiesService {

    protected serviceProxy: IServiceProxy

    constructor() {
        this.serviceProxy = Continuum.serviceProxy('org.kinotic.structures.api.services.JsonEntitiesService')
    }

    public save(structureId: string, entity: any): Promise<any> {
        return this.serviceProxy.invoke('save', [structureId, entity])
    }

    public findById(structureId: string, id: string): Promise<any> {
        return this.serviceProxy.invoke('findById', [structureId, id])
    }

    public count(structureId: string): Promise<number> {
        return this.serviceProxy.invoke('count', [structureId])
    }

    public deleteById(structureId: string, id: string): Promise<void> {
        return this.serviceProxy.invoke('deleteById', [structureId, id])
    }

    public findAll(structureId: string, pageable: Pageable): Promise<Page<any>> {
        return this.serviceProxy.invoke('findAll', [structureId, pageable])
    }

    public search(structureId: string, searchText: string, pageable: Pageable): Promise<Page<any>> {
        return this.serviceProxy.invoke('search', [structureId, searchText, pageable])
    }

}

export const JSON_ENTITIES_SERVICE: IJsonEntitiesService = new JsonEntitiesService()
