package org.kinotic.domain.api.model.security;

import org.apache.commons.lang3.Validate;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;

/**
 * The organization user an invitation is sent on behalf of: the organization the invitee joins, and the sender the
 * invitation names.
 *
 * @param organizationId the organization the invitee joins
 * @param id             the inviting organization user's id
 * @param displayName    the name the invitation gives its sender
 */
public record Inviter(String organizationId, String id, String displayName) {

    /**
     * The inviter for an organization user signed in as {@code participant}, named by its display name, else its
     * email, else its id.
     */
    public static Inviter from(OrganizationParticipant participant) {
        Validate.notNull(participant, "participant is required");
        String displayName = participant.getId();
        if (participant.getMetadata() != null) {
            String name = participant.getMetadata().get("displayName");
            String email = participant.getMetadata().get("email");
            if (name != null && !name.isBlank()) {
                displayName = name;
            } else if (email != null && !email.isBlank()) {
                displayName = email;
            }
        }
        return new Inviter(participant.getOrganizationId(), participant.getId(), displayName);
    }
}
