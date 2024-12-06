package org.kinotic.structures.api.services.security;

import org.kinotic.continuum.api.security.Participant;

import java.util.concurrent.CompletableFuture;

public interface AuthorizationService {

    CompletableFuture<Boolean> authorize(String service, String action, Participant participant);



}
