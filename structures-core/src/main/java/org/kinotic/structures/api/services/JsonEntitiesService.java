package org.kinotic.structures.api.services;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.RawJson;

import java.util.concurrent.CompletableFuture;

/**
 * Provides access to entities for a given structure.
 * Created by Nic Padilla 🤪on 6/18/23.
 */
@Publish
public interface JsonEntitiesService {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param structureId the id of the structure to save the entity for
     * @param entity      must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    CompletableFuture<RawJson> save(String structureId, RawJson entity, Participant participant);

    /**
     * Updates all given entities, this gives an opportunity to perform partial updates of the data structure.
     * @param structureId the id of the structure to save the entity for
     * @param entities all the entities to save
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} that will complete when all entities have been saved
     */
    CompletableFuture<Void> bulkSave(String structureId, RawJson entities, Participant participant);

    /**
     * Updates a given entity. This will only override the fields that are present in the given entity.
     * If any fields are not present in the given entity data they will not be changed.
     * If the entity does not exist it will be created.
     *
     * @param structureId the id of the structure to save the entity for
     * @param entity      must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    CompletableFuture<RawJson> update(String structureId, RawJson entity, Participant participant);

    /**
     * Saves all given entities.
     * @param structureId the id of the structure to save the entity for
     * @param entities all the entities to save
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} that will complete when all entities have been saved
     */
    CompletableFuture<Void> bulkUpdate(String structureId, RawJson entities, Participant participant);

    /**
     * Retrieves an entity by its id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} with the entity with the given id or {@link CompletableFuture} emitting null if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    CompletableFuture<RawJson> findById(String structureId, String id, Participant participant);

    /**
     * Returns the number of entities available.
     * @param structureId the id of the structure to count
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting the number of entities.
     */
    CompletableFuture<Long> count(String structureId, Participant participant);

    /**
     * Deletes the entity with the given id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id          must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting when delete is complete
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    CompletableFuture<Void> deleteById(String structureId, String id, Participant participant);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param structureId the id of the structure to save the entity for
     * @param pageable    the page settings to be used
     * @param participant the participant of the logged-in user
     * @return a page of entities
     */
    CompletableFuture<Page<RawJson>> findAll(String structureId, Pageable pageable, Participant participant);

    /**
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@code Pageable} object.
     * <p>
     * You can find more information about the search syntax <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-query-string-query.html#query-string-syntax">here</a>
     *
     * @param structureId the id of the structure to save the entity for
     * @param searchText  the text to search for entities for
     * @param pageable    the page settings to be used
     * @param participant the participant of the logged-in user
     * @return a page of entities
     */
    CompletableFuture<Page<RawJson>> search(String structureId, String searchText, Pageable pageable, Participant participant);

}
