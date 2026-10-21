package org.kinotic.domain.api.model.security.identity;

import lombok.NoArgsConstructor;

/**
 * A user of one application's own user base, signing in at that application's login and acting as an
 * {@link org.kinotic.domain.api.model.security.participant.ApplicationParticipant}. Carries the
 * {@code organizationId} and {@code applicationId} of its application, and the {@code tenantId} of its tenant
 * when the application has tenants; unique by email within the application.
 */
@NoArgsConstructor
public final class ApplicationUserParticipantIdentity extends UserParticipantIdentity {

    @Override
    public ParticipantIdentityType getType() {
        return ParticipantIdentityType.APPLICATION_USER;
    }
}
