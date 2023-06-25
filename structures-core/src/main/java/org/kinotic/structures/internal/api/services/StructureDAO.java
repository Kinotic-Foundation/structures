package org.kinotic.structures.internal.api.services;

import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.kinotic.structures.api.domain.Structure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

/**
 * Internal DAO for common functionality so we don't have circular references
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
public interface StructureDAO extends IdentifiableCrudService<Structure, String> {

    /**
     * Finds all published structures for the given namespace.
     * @param namespace the namespace to find structures for
     * @param pageable the page to return
     * @return a future that will complete with a page of structures
     */
    CompletableFuture<Page<Structure>> findAllPublishedForNamespace(String namespace, Pageable pageable);

    /**
     * Counts all structures for the given namespace.
     * @param namespace the namespace to find structures for
     * @return a future that will complete with a page of structures
     */
    CompletableFuture<Long> countForNamespace(String namespace);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}
     * @return {@link CompletableFuture} emitting the entity with the given id or {@link CompletableFuture} emitting null if none found
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    CompletableFuture<Structure> findById(String id);

}
