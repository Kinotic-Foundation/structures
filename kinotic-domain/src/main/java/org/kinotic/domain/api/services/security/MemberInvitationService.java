package org.kinotic.domain.api.services.security;

import io.vertx.core.Future;
import org.kinotic.domain.api.model.security.Inviter;
import org.kinotic.domain.api.model.security.OrganizationMember;
import org.kinotic.domain.api.model.security.PendingInvite;

/**
 * Adds people to an organization and invites them into its applications on behalf of one of its users, the
 * {@link Inviter}, whose organization they join and whom each invitation names as its sender. Each invitation is
 * emailed with the invitation {@link org.kinotic.domain.api.model.SystemEmailTemplate} {@code emailTemplateId} names,
 * or the default one when it is {@code null}; an id no template has fails the call.
 */
public interface MemberInvitationService {

    /**
     * Adds a person to the inviter's organization by email. An address with no organization user yet gets one,
     * with no sign-in method, and its membership at once, so the person appears among the members as pending. With
     * {@code sendInvitation} an invitation is emailed; without one the person signs in through one of the
     * organization's own providers, so the organization must have one. An address that already has an organization
     * user is emailed an invitation whatever {@code sendInvitation} says, and joins once it is accepted. Fails for an
     * address that is already a member or one an invitation is already pending for.
     *
     * @return the member as listed, pending until they first sign in; for an address that already has an organization
     *         user, a pending member with no id yet
     */
    Future<OrganizationMember> addMember(Inviter inviter, String email, String displayName,
                                         boolean sendInvitation, String emailTemplateId);

    /**
     * Emails an invitation to a pending member of the inviter's organization: the one they have, again, or a new one
     * for a member added without. Fails for an address that is not a pending member's.
     */
    Future<Void> sendInvitation(Inviter inviter, String email, String emailTemplateId);

    /**
     * Invites someone into application {@code applicationId} of the inviter's organization, whose acceptance creates
     * the application's user. Fails if the email already has a user in the application or an invitation is already
     * pending for it.
     *
     * @return the pending invitation
     */
    Future<PendingInvite> inviteMember(Inviter inviter, String email, String displayName,
                                       String applicationId, String emailTemplateId);
}
