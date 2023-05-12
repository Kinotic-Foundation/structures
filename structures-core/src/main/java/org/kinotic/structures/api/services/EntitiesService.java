package org.kinotic.structures.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
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
     * @return {@link Mono} emitting the saved entity
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}
     */
    CompletableFuture<ByteBuffer> save(String structureId, ByteBuffer entity);

    /**
     * Retrieves an entity by its id.
     *
     * @param structureId the id of the structure to save the entity for
     * @param id must not be {@literal null}
     * @return {@link Mono} emitting the entity with the given id or {@link Mono#empty()} if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    CompletableFuture<ByteBuffer> findById(String structureId, String id);

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
     * @return {@link Mono} signaling when operation has completed
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
    CompletableFuture<Page<ByteBuffer>> findAll(String structureId, Pageable pageable);

    /**
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@code Pageable} object.
     *
     * @param structureId the id of the structure to save the entity for
     * @param searchText the text to search for entities for
     * @param pageable the page settings to be used
     * @return a page of entities
     */
    CompletableFuture<Page<ByteBuffer>> search(String structureId, String searchText, Pageable pageable);

}
