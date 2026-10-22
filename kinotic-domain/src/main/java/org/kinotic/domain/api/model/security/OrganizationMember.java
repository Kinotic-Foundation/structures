package org.kinotic.domain.api.model.security;

import org.kinotic.domain.api.model.security.identity.OrganizationMembership;
import org.kinotic.domain.api.model.security.identity.OrganizationParticipantIdentity;

import java.util.Date;

/**
 * A member of one organization, for listing the organization's members: an {@link OrganizationParticipantIdentity}
 * joined with its {@link OrganizationMembership} of the organization.
 *
 * @param id          the id of the organization user, which member operations name
 * @param email       the organization user's email
 * @param displayName the organization user's display name, or {@code null}
 * @param enabled     whether the membership is enabled, so the member may sign in to this organization
 * @param joined      when the membership was created
 * @param pending     whether the member has yet to sign in for the first time: an administrator added them and no
 *                    sign-in method claims the organization user yet, so the invitation they were sent, or the
 *                    organization's own provider, is what admits them
 */
public record OrganizationMember(String id, String email, String displayName, boolean enabled, Date joined, boolean pending) {
}
