package org.kinotic.structures.internal.api.services.impl.security;

import org.kinotic.structures.api.domain.SecurityContext;
import org.kinotic.structures.api.services.security.AuthorizationService;

import java.util.concurrent.CompletableFuture;

public class NoopAuthorizationService implements AuthorizationService {
    @Override
    public CompletableFuture<Void> authorize(String action, SecurityContext securityContext) {
        return CompletableFuture.completedFuture(null);
    }
}
