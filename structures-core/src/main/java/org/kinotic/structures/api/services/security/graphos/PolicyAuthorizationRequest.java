package org.kinotic.structures.api.services.security.graphos;

public interface PolicyAuthorizationRequest {

    String policy();

    void authorize();

    void deny();

    boolean isAuthorized();
}
