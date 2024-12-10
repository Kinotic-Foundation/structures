package org.kinotic.structures.internal.api.services;

import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.kinotic.structures.api.domain.Namespace;

import java.util.concurrent.CompletableFuture;

public interface NamespaceDAO extends IdentifiableCrudService<Namespace, String> {
    /**
     * Creates a new namespace if it does not already exist.
     * @param id the id of the namespace to create
     * @param description the description of the namespace to create
     * @return {@link CompletableFuture} emitting the created namespace
     */
    CompletableFuture<Namespace> createNamespaceIfNotExist(String id, String description);

}
