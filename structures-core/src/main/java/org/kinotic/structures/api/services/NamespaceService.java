package org.kinotic.structures.api.services;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.kinotic.structures.api.domain.Namespace;

import java.util.concurrent.CompletableFuture;

@Publish
public interface NamespaceService extends IdentifiableCrudService<Namespace, String> {

    public CompletableFuture<Namespace> createNamespaceIfNotExist(String id, String description);

}
