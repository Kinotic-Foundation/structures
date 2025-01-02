package org.kinotic.structures.internal.api.services.impl.security;

import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.structures.api.domain.EntityOperation;
import org.kinotic.structures.api.domain.NamedQueryOperation;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.security.AuthorizationService;
import org.kinotic.structures.api.services.security.AuthorizationServiceFactory;

import java.util.concurrent.CompletableFuture;

public class NoopAuthorizationServiceFactory implements AuthorizationServiceFactory {

    @Override
    public CompletableFuture<AuthorizationService<EntityOperation>> createStructureAuthorizationService(Structure structure) {
        return CompletableFuture.completedFuture(new NoopAuthorizationService<>());
    }

    @Override
    public CompletableFuture<AuthorizationService<NamedQueryOperation>> createNamedQueryAuthorizationService(FunctionDefinition namedQuery) {
        return CompletableFuture.completedFuture(new NoopAuthorizationService<>());
    }
}
