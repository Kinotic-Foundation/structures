package org.kinotic.domain.api.model.security.identity;

import lombok.NoArgsConstructor;

/**
 * A Kinotic OS user who signs in to the system console with a password and acts as a
 * {@link org.kinotic.domain.api.model.security.participant.SystemParticipant}; unique by email among system
 * users.
 */
@NoArgsConstructor
public final class SystemParticipantIdentity extends UserParticipantIdentity {

    @Override
    public ParticipantIdentityType getType() {
        return ParticipantIdentityType.SYSTEM_USER;
    }
}
