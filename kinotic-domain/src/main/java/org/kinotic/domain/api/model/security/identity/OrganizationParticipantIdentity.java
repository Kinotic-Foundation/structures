package org.kinotic.domain.api.model.security.identity;

import lombok.NoArgsConstructor;

/**
 * A person who signs in to organizations: one user, unique by email among organization users, whose sign-in
 * methods reach every organization it holds an {@link OrganizationMembership} of. It acts as an
 * {@link org.kinotic.domain.api.model.security.participant.OrganizationParticipant} of one organization at a time,
 * through its membership there.
 */
@NoArgsConstructor
public final class OrganizationParticipantIdentity extends UserParticipantIdentity {

    @Override
    public ParticipantIdentityType getType() {
        return ParticipantIdentityType.ORGANIZATION_USER;
    }
}
