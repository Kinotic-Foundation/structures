package org.kinotic.structures.api.services;

import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.Structure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

/**
 * Provides access to entities for a given structure.
 * Created by Navíd Mitchell 🤪on 5/10/23.
 */
public interface EntitiesService {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param structureId the id of the structure to save the entity for
     * @param entity      must not be {@literal null}
     * @param context     the context for this operation
     * @return {@link CompletableFuture} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    <T> CompletableFuture<T> save(String structureId, T entity, EntityContext context);

    /**
     * Saves all given entities.
     * @param structureId the id of the structure to save the entity for
     * @param entities all the entities to save
     * @param context the context for this operation
     * @return {@link CompletableFuture} that will complete when all entities have been saved
     * @param <T> the type of the entities
     */
    <T> CompletableFuture<Void> bulkSave(String structureId, T entities, EntityContext context);

    /**
     * Retrieves an entity by its id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @param type        the type of the entity
     * @param context     the context for this operation
     * @return {@link CompletableFuture} with the entity with the given id or {@link CompletableFuture} emitting null if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    <T> CompletableFuture<T> findById(String structureId, String id, Class<T> type, EntityContext context);

    /**
     * Returns the number of entities available.
     * @param structureId the id of the structure to count
     * @param context     the context for this operation
     * @return {@link CompletableFuture} emitting the number of entities.
     */
    CompletableFuture<Long> count(String structureId, EntityContext context);

    /**
     * Deletes the entity with the given id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @param context     the context for this operation
     * @return {@link CompletableFuture} emitting when delete is complete
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    CompletableFuture<Void> deleteById(String structureId, String id, EntityContext context);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param structureId the id of the structure to save the entity for
     * @param pageable    the page settings to be used
     * @param type        the type of the entity
     * @param context     the context for this operation
     * @return a page of entities
     */
    <T> CompletableFuture<Page<T>> findAll(String structureId, Pageable pageable, Class<T> type, EntityContext context);

    /**
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@code Pageable} object.
     * <p>
     * You can find more information about the search syntax <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-query-string-query.html#query-string-syntax">here</a>
     *
     * @param structureId the id of the structure to save the entity for
     * @param searchText  the text to search for entities for
     * @param pageable    the page settings to be used
     * @param type        the type of the entity
     * @param context     the context for this operation
     * @return a page of entities
     */
    <T> CompletableFuture<Page<T>> search(String structureId, String searchText, Pageable pageable, Class<T> type, EntityContext context);

    /**
     * Evicts the cache for a given structure
     * @param structure to evict the cache for
     */
    void evictCachesFor(Structure structure);

}
