package org.kinotic.structures.api.services;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.kinotic.structures.api.domain.Namespace;

import java.util.concurrent.CompletableFuture;

@Publish
public interface NamespaceService extends IdentifiableCrudService<Namespace, String> {

    /**
     * Creates a new namespace if it does not already exist.
     * @param id the id of the namespace to create
     * @param description the description of the namespace to create
     * @return {@link CompletableFuture} emitting the created namespace
     */
    CompletableFuture<Namespace> createNamespaceIfNotExist(String id, String description);

    /**
     * This operation makes all the recent writes immediately available for search.
     * @return a future that will complete when the index has been synced
     */
    CompletableFuture<Void> syncIndex();

}
