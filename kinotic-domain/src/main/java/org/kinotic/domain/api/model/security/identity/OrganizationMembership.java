package org.kinotic.domain.api.model.security.identity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Identifiable;

import java.util.Date;

/**
 * An {@link OrganizationUserParticipantIdentity}'s membership of one organization: an organization the user may
 * sign in to, and the user's standing there. A person has one organization user and any number of memberships, so
 * one email, one password and one GitHub binding reach every organization the person belongs to. A membership can
 * be disabled or removed by its organization without touching the organization user or its other memberships.
 *
 * <p>The id is derived from the organization user and the organization ({@link #idOf}), so a membership is read by
 * a get and exists at most once per organization user and organization.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class OrganizationMembership implements Identifiable<String> {

    private String id;

    /** The organization user the membership belongs to. */
    private String organizationUserId;

    /** The organization the organization user is a member of. */
    private String organizationId;

    /** Whether the organization user may sign in to this organization; a disabled membership refuses it while the user stays usable elsewhere. */
    private boolean enabled;

    private Date created;

    private Date updated;

    /** The id of the membership of organization user {@code organizationUserId} in organization {@code organizationId}. */
    public static String idOf(String organizationUserId, String organizationId) {
        Validate.notBlank(organizationUserId, "organizationUserId cannot be blank");
        Validate.notBlank(organizationId, "organizationId cannot be blank");
        return organizationUserId + ":" + organizationId;
    }
}
