package org.kinotic.management.api.services.security;

import io.vertx.core.Future;
import org.kinotic.domain.api.model.EmailTemplate;
import org.kinotic.domain.api.model.security.OrganizationMember;
import org.kinotic.domain.api.model.security.PendingInvite;
import org.kinotic.domain.api.services.security.InviteService;

/**
 * Adds people to the calling organization user's organization and invites them into its applications, naming that
 * user as each invitation's sender. Every method requires an {@link org.kinotic.domain.api.model.security.participant.OrganizationParticipant}
 * on the call. Each invitation is emailed with the {@link EmailTemplate} passed, or Kinotic's own when it is
 * {@code null}.
 */
public interface MemberInvitationService {

    /**
     * {@link MemberService#addMember}, emailing any invitation as {@code emailTemplate}.
     *
     * @return the member as listed, pending until they first sign in; for an address that already has an organization
     *         user, a pending member with no id yet
     * @throws IllegalArgumentException when {@code emailTemplate} is invalid, as {@link InviteService#createInvite}
     *                                  describes
     */
    Future<OrganizationMember> addMember(String email, String displayName, boolean sendInvitation,
                                         EmailTemplate emailTemplate);

    /**
     * {@link MemberService#sendInvitation}, emailing the invitation as {@code emailTemplate}.
     *
     * @throws IllegalArgumentException when {@code emailTemplate} is invalid, as {@link InviteService#createInvite}
     *                                  describes
     */
    Future<Void> sendInvitation(String email, EmailTemplate emailTemplate);

    /**
     * {@link MemberService#inviteMember}, emailing the invitation as {@code emailTemplate}.
     *
     * @return the pending invitation
     * @throws IllegalArgumentException when {@code emailTemplate} is invalid, as {@link InviteService#createInvite}
     *                                  describes
     */
    Future<PendingInvite> inviteMember(String email, String displayName, String applicationId,
                                       EmailTemplate emailTemplate);
}
