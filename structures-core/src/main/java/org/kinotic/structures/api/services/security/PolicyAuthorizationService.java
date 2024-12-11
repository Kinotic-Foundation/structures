package org.kinotic.structures.api.services.security;

import org.kinotic.structures.api.domain.SecurityContext;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.impl.EntityOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PolicyAuthorizationService implements AuthorizationService{

    private final Map<EntityOperations, List<String>> operationPolicies = new HashMap<>();

    public PolicyAuthorizationService(Structure structure) {
    }

    @Override
    public CompletableFuture<Void> authorize(String serviceName, String methodName, SecurityContext securityContext) {
        return null;
    }


}
