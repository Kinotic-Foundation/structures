package org.kinotic.system.api.services;

import io.vertx.core.Future;
import org.kinotic.core.api.annotations.Publish;
import org.kinotic.domain.api.model.security.Inviter;
import org.kinotic.domain.api.model.security.OrganizationMember;
import org.kinotic.management.api.model.PendingInviteSummary;

/**
 * Invitations a platform service sends on behalf of an organization's user, the {@link Inviter}, with an invitation
 * {@link org.kinotic.domain.api.model.SystemEmailTemplate} the service chooses. Each call behaves as the
 * {@code MemberService} call of the same name made by the inviter, its email rendered from the template
 * {@code emailTemplateId}, or from the default one when it is {@code null}; an id no template has fails the call.
 * <p>
 * Published in the system zone. Only a cluster member calling as itself, as
 * {@code SecurityContext.callWithoutParticipant} sends a call, may call it, since it trusts the inviter it is given; a
 * call made for any participant, SYSTEM participants included, is refused.
 */
@Publish
public interface SystemMemberInvitationService {

    /**
     * Adds a person to the inviter's organization by email and emails them an invitation, as {@code MemberService.addMember}
     * does when it sends one.
     *
     * @return the member as listed, pending until they first sign in; for an address that already has an organization
     *         user, a pending member with no id yet
     */
    Future<OrganizationMember> addMember(Inviter inviter, String email, String displayName, String emailTemplateId);

    /**
     * Emails an invitation to a pending member of the inviter's organization, as {@code MemberService.sendInvitation}
     * does.
     */
    Future<Void> sendInvitation(Inviter inviter, String email, String emailTemplateId);

    /**
     * Invites someone into application {@code applicationId} of the inviter's organization, as
     * {@code MemberService.inviteMember} does.
     *
     * @return the pending invitation
     */
    Future<PendingInviteSummary> inviteMember(Inviter inviter, String email, String displayName,
                                              String applicationId, String emailTemplateId);
}
