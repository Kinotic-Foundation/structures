package org.kinotic.structures.internal.api.services.impl.security.graphos;

import org.kinotic.structures.api.domain.SecurityContext;

import java.util.concurrent.CompletableFuture;

/**
 * Responsible for evaluating GraphOS policies for a given security context
 */
public interface PolicyEvaluator {

    CompletableFuture<AuthorizationResult> evaluatePolicies(SecurityContext securityContext);
}
