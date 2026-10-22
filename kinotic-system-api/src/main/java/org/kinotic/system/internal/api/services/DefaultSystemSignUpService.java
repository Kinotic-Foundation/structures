package org.kinotic.system.internal.api.services;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.services.security.SignUpService;
import org.kinotic.system.api.services.SystemSignUpService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultSystemSignUpService implements SystemSignUpService {

    private final SecurityContext securityContext;
    private final SignUpService signUpService;

    @Override
    public Future<Void> initiateLocalSignUp(String email, String displayName, String emailTemplateId) {
        // every gateway binds the connection's participant to the calls it forwards, so a call without one comes
        // from a cluster member's own code
        securityContext.requireNoParticipant();
        return signUpService.initiateLocalSignUp(email, displayName, emailTemplateId);
    }
}
