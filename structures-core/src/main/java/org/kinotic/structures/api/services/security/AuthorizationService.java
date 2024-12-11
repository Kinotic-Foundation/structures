package org.kinotic.structures.api.services.security;

import org.kinotic.structures.api.domain.SecurityContext;

import java.util.concurrent.CompletableFuture;

public interface AuthorizationService {

    CompletableFuture<Void> authorize(String action,
                                      SecurityContext securityContext);

}
