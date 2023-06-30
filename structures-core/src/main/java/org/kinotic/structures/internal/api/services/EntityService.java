package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.Structure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

/**
 * Provides functionality to query data for a single "Entity" for a given {@link Structure}.
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
public interface EntityService {

    /**
     * Saves a given entity. This will override all data if there is an existing entity with the same id.
     * Use the returned instance for further operations as the save operation might have changed the entity instance.
     *
     * @param entity  must not be {@literal null}
     * @param context the context for this operation
     * @return {@link CompletableFuture} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    <T> CompletableFuture<T> save(T entity, EntityContext context);

    /**
     * Saves all given entities.
     * @param entities all the entities to save
     * @param context the context for this operation
     * @return {@link CompletableFuture} that will complete when all entities have been saved
     * @param <T> the type of the entities
     */
    <T> CompletableFuture<Void> bulkSave(T entities, EntityContext context);

    /**
     * Updates a given entity. This will only override the fields that are present in the given entity.
     * If any fields are not present in the given entity data they will not be changed.
     * If the entity does not exist it will be created.
     * Use the returned instance for further operations as the save operation might have changed the entity instance.
     *
     * @param entity      must not be {@literal null}
     * @param context     the context for this operation
     * @return {@link CompletableFuture} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    <T> CompletableFuture<T> update(T entity, EntityContext context);

    /**
     * Updates all given entities.
     * @param entities all the entities to save
     * @param context the context for this operation
     * @return {@link CompletableFuture} that will complete when all entities have been saved
     * @param <T> the type of the entities
     */
    <T> CompletableFuture<Void> bulkUpdate(T entities, EntityContext context);

    /**
     * Retrieves an entity by its id.
     *
     * @param id      must not be {@literal null}
     * @param type    the type of the entity
     * @param context the context for this operation
     * @return {@link CompletableFuture} emitting the entity with the given id or {@link CompletableFuture} emitting null if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    <T> CompletableFuture<T> findById(String id, Class<T> type, EntityContext context);

    /**
     * Returns the number of entities available.
     *
     * @param context the context for this operation
     * @return {@link CompletableFuture} emitting the number of entities
     */
    CompletableFuture<Long> count(EntityContext context);

    /**
     * Deletes the entity with the given id.
     *
     * @param id      must not be {@literal null}
     * @param context the context for this operation
     * @return {@link CompletableFuture} signaling when operation has completed
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    CompletableFuture<Void> deleteById(String id, EntityContext context);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@link Pageable} object.
     *
     * @param pageable the page settings to be used
     * @param type     the type of the entity
     * @param context  the context for this operation
     * @return a page of entities
     */
    <T> CompletableFuture<Page<T>> findAll(Pageable pageable, Class<T> type, EntityContext context);

    /**
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@link Pageable} object.
     *
     * @param searchText the text to search for entities for
     * @param pageable   the page settings to be used
     * @param type       the type of the entity
     * @param context    the context for this operation
     * @return a page of entities
     */
    <T> CompletableFuture<Page<T>> search(String searchText, Pageable pageable, Class<T> type, EntityContext context);
}
