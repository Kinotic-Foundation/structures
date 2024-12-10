package org.kinotic.structures.api.services;

import org.kinotic.continuum.api.Identifiable;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public interface SecuredCrudService<T extends Identifiable<ID>, ID> {
    /**
     * Creates a new entity if one does not already exist for the given id
     *
     * @param entity   to create if one does not already exist
     * @param participant the participant of the logged-in user
     * @return a {@link Mono} containing the new entity or an error if an exception occurred
     */
    CompletableFuture<T> create(T entity, Participant participant);

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity   must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    CompletableFuture<T> save(T entity, Participant participant);

    /**
     * Retrieves a entity by its id.
     *
     * @param id          must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting the entity with the given id or {@link CompletableFuture} emitting null if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    CompletableFuture<T> findById(ID id, Participant participant);

    /**
     * Returns the number of entities available.
     *
     * @return {@link CompletableFuture} emitting the number of entities
     */
    CompletableFuture<Long> count(Participant participant);

    /**
     * Deletes the entity with the given id.
     *
     * @param id          must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} signaling when operation has completed
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    CompletableFuture<Void> deleteById(ID id, Participant participant);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@link Pageable} object.
     *
     * @param pageable    the page settings to be used
     * @param participant the participant of the logged-in user
     * @return a page of entities
     */
    CompletableFuture<Page<T>> findAll(Pageable pageable, Participant participant);

    /**
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@link Pageable} object.
     *
     * @param searchText  the text to search for entities for
     * @param pageable    the page settings to be used
     * @param participant the participant of the logged-in user
     * @return a page of entities
     */
    CompletableFuture<Page<T>> search(String searchText, Pageable pageable, Participant participant);
}
