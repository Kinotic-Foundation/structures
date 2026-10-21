package org.kinotic.domain.api.model.security.identity;

import lombok.NoArgsConstructor;

/**
 * A Kinotic OS user who signs in to the system console with a password and acts as a
 * {@link org.kinotic.domain.api.model.security.participant.SystemParticipant}. Carries no
 * {@code organizationId} or {@code applicationId}, and is unique by email among system users.
 */
@NoArgsConstructor
public final class SystemUserParticipantIdentity extends UserParticipantIdentity {

    @Override
    public ParticipantIdentityType getType() {
        return ParticipantIdentityType.SYSTEM_USER;
    }
}
