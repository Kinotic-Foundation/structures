package org.kinotic.structures.internal.api.services.impl.security.graphos;

import org.kinotic.structures.api.services.security.graphos.PolicyAuthorizationRequest;

import java.util.Map;

/**
 * Represents a policy expression in a policy expression tree
 */
public interface PolicyExpression {

    /**
     * Evaluate the policy expression
     * @param policyRequests a map of policy requests to evaluate
     * @return true if the policy expression evaluates to true
     */
    boolean evaluate(Map<String, PolicyAuthorizationRequest> policyRequests);

}
