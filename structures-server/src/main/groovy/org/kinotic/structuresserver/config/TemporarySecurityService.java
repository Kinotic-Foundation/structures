package org.kinotic.structuresserver.config;

import org.kinotic.continuum.core.api.security.Participant;
import org.kinotic.continuum.core.api.security.SecurityService;

import javax.security.sasl.AuthenticationException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class TemporarySecurityService implements SecurityService {

    private static final String PASSWORD = "structures";
    private static final Participant participant = new Participant("admin");

    @Override
    public CompletableFuture<Participant> authenticate(Map<String, String> authenticationInfo) {
        if(authenticationInfo.containsKey("login") && Objects.equals(authenticationInfo.get("login"), "admin")
            && authenticationInfo.containsKey("passcode") && Objects.equals(authenticationInfo.get("passcode"), PASSWORD)){
            return CompletableFuture.completedFuture(participant);
        }
        return CompletableFuture.failedFuture(new AuthenticationException("username/password pair provided was not correct."));
    }
}
