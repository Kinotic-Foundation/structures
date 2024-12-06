package org.kinotic.structures.api.services.security;

import java.util.concurrent.CompletableFuture;

public interface AuthorizationServiceFactory {

    CompletableFuture<AuthorizationService> createAuthorizationService(String service, Object data);

}
