package org.kinotic.structures.api.services;

import org.kinotic.structures.api.domain.RawJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Provides access to entities for a given structure.
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public interface EntitiesService {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param structureId the id of the structure to save the entity for
     * @param entity must not be {@literal null}
     * @return {@link CompletableFuture} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    CompletableFuture<RawJson> save(String structureId, RawJson entity);

    /**
     * Retrieves an entity by its id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id must not be {@literal null}
     * @return {@link CompletableFuture} with the entity with the given id or {@link CompletableFuture#get()} with null if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    CompletableFuture<RawJson> findById(String structureId, String id);

    /**
     * Returns the number of entities available.
     *
     * @return {@link Mono} emitting the number of entities.
     */
    CompletableFuture<Long> count(String structureId);

    /**
     * Deletes the entity with the given id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id must not be {@literal null}
     * @return {@link CompletableFuture} emitting when delete is complete
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    CompletableFuture<Void> deleteById(String structureId, String id);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param structureId the id of the structure to save the entity for
     * @param pageable the page settings to be used
     * @return a page of entities
     */
    CompletableFuture<Page<RawJson>> findAll(String structureId, Pageable pageable);

    /**
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@code Pageable} object.
     *
     * @param structureId the id of the structure to save the entity for
     * @param searchText the text to search for entities for
     * @param pageable the page settings to be used
     * @return a page of entities
     */
    CompletableFuture<Page<RawJson>> search(String structureId, String searchText, Pageable pageable);

}
