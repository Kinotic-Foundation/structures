package org.kinotic.structures.api.services;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.kinotic.structures.api.domain.Application;

import java.util.concurrent.CompletableFuture;

@Publish
public interface ApplicationService extends IdentifiableCrudService<Application, String> {

    /**
     * Creates a new application if it does not already exist.
     * @param id the id of the application to create
     * @param description the description of the application to create
     * @return {@link CompletableFuture} emitting the created application or the existing application if it already exists
     */
    CompletableFuture<Application> createApplicationIfNotExist(String id, String description);

    /**
     * This operation makes all the recent writes immediately available for search.
     * @return a future that will complete when the index has been synced
     */
    CompletableFuture<Void> syncIndex();

}
