package org.kinotic.structures.api.services;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.domain.Namespace;

import java.util.concurrent.CompletableFuture;

@Publish
public interface NamespaceService extends SecuredCrudService<Namespace, String> {

    /**
     * Creates a new namespace if it does not already exist.
     *
     * @param id          the id of the namespace to create
     * @param description the description of the namespace to create
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting the created namespace
     */
    CompletableFuture<Namespace> createNamespaceIfNotExist(String id, String description, Participant participant);

}
