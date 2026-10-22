package org.kinotic.system.api.services;

import io.vertx.core.Future;
import org.kinotic.core.api.annotations.Publish;

/**
 * Organization sign-ups a platform service starts for the people signing up through its UI, with a verification
 * {@link org.kinotic.domain.api.model.SystemEmailTemplate} the service chooses.
 * <p>
 * Published in the system zone. Only a cluster member calling as itself, as
 * {@code SecurityContext.callWithoutParticipant} sends a call, may call it; a call made for any participant, SYSTEM
 * participants included, is refused.
 */
@Publish
public interface SystemSignUpService {

    /**
     * Starts an email sign-up as {@code POST /api/auth/org/signup} does, emailing the verification email rendered from
     * the template {@code emailTemplateId}, or from the default one when it is {@code null}. Its link opens the
     * {@code /signup/verify} page of the template's UI, which completes the sign-up with
     * {@code POST /api/auth/org/signup/complete}.
     *
     * @throws IllegalArgumentException when no template has the id {@code emailTemplateId}
     */
    Future<Void> initiateLocalSignUp(String email, String displayName, String emailTemplateId);
}
