package org.kinotic.structures.internal.api.services.impl;

import org.kinotic.structures.api.services.security.AuthorizationService;
import org.kinotic.structures.api.services.security.AuthorizationServiceFactory;

import java.util.concurrent.CompletableFuture;

public class NoopAuthorizationServiceFactory implements AuthorizationServiceFactory {
    @Override
    public CompletableFuture<AuthorizationService> createAuthorizationService(String service, Object data) {
        return CompletableFuture.completedFuture(new NoopAuthorizationService());
    }
}
