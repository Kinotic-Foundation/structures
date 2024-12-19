package org.kinotic.structures.api.services.security;

import org.kinotic.structures.api.domain.SecurityContext;

import java.util.concurrent.CompletableFuture;

/**
 * The {@link AuthorizationService} is responsible for authorizing a given action
 * This is a generic service that can be used to authorize any action
 */
public interface AuthorizationService {

    CompletableFuture<Void> authorize(String action,
                                      SecurityContext securityContext);

}
