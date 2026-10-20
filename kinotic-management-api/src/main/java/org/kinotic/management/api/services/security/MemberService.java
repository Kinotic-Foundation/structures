package org.kinotic.management.api.services.security;

import io.vertx.core.Future;
import org.kinotic.core.api.annotations.Publish;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.security.Member;
import org.kinotic.idl.api.annotations.AuthzCheck;
import org.kinotic.idl.api.annotations.AuthzResource;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.kinotic.management.api.model.PendingInviteSummary;

/**
 * Member management for the caller's organization and its applications, used by the web app. Every
 * method derives the organization from the authenticated participant — only org members may
 * call, and a supplied {@code applicationId} must belong to that organization. Members and
 * invitations are scoped: {@code applicationId} null addresses org members, set addresses
 * that application's members.
 */
@Publish
@AuthzResource(value = AuthzUtil.ORGANIZATION_TYPE, resourceId = "{@organizationId}")
public interface MemberService {

    /**
     * Lists the members of the scope: the accounts holding a membership of the caller's organization, or
     * the users of one of its applications.
     */
    @AuthzCheck(permission = AuthzUtil.CAN_READ_MEMBERS)
    Future<Page<Member>> findMembers(String applicationId, Pageable pageable);

    /**
     * Searches the scope's members by free text over email and display name. Blank
     * {@code searchText} is equivalent to {@link #findMembers}.
     */
    @AuthzCheck(permission = AuthzUtil.CAN_READ_MEMBERS)
    Future<Page<Member>> searchMembers(String searchText, String applicationId, Pageable pageable);

    /**
     * Adds a person to the caller's organization by email. An address with no account yet gets one, with no
     * sign-in method, and its membership at once, so the person appears among the members as pending. With
     * {@code sendInvitation} an invitation is emailed, whose acceptance claims the account with a password or
     * through one of the organization's providers; without one the person signs in through one of the
     * organization's own providers, which claims the account on the first sign-in, so the organization must have
     * one. An address that already has an account is emailed an invitation whatever {@code sendInvitation} says,
     * since joining an account to an organization needs its holder's acceptance, and joins once accepted. Fails for
     * an address that is already a member, one an invitation is already pending for, or an address with no account
     * when no invitation is sent and the organization has no provider of its own.
     *
     * @param email          the address the person signs in with
     * @param displayName    optional display name
     * @param sendInvitation whether to email an invitation
     * @return the member as listed, pending until they first sign in; for an address that already has an account,
     *         a pending member with no id yet, which the list shows once the invitation is accepted
     */
    @AuthzCheck(permission = AuthzUtil.CAN_MANAGE_MEMBERS)
    Future<Member> addMember(String email, String displayName, boolean sendInvitation);

    /**
     * Emails an invitation to a pending member of the caller's organization: the one they have, again, or a new
     * one for a member added without. Fails for an address that is not a pending member's.
     *
     * @param email the pending member's address
     */
    @AuthzCheck(permission = AuthzUtil.CAN_MANAGE_MEMBERS)
    Future<Void> sendInvitation(String email);

    /**
     * Invites someone into one of the caller's organization's applications by email. Sends the invitation email
     * and returns the pending invitation, whose acceptance creates the application's user. Fails if the email
     * already has a user in the application or an invitation is already pending for it.
     *
     * @param email         where to send the invitation
     * @param displayName   optional display name for the invitee
     * @param applicationId the application to invite into, one of the caller's organization's
     */
    @AuthzCheck(permission = AuthzUtil.CAN_MANAGE_MEMBERS)
    Future<PendingInviteSummary> inviteMember(String email, String displayName, String applicationId);

    /**
     * Enables or disables a member of the caller's organization or of one of its applications: an account's
     * membership of the organization, or an application user. Disabling gates future logins to that scope;
     * established sessions last until they expire. Callers cannot disable themselves.
     *
     * @param identityId the member's id, as {@link Member#id()} names it
     */
    @AuthzCheck(permission = AuthzUtil.CAN_MANAGE_MEMBERS, consistent = true)
    Future<Void> setMemberEnabled(String identityId, boolean enabled);

    /**
     * Removes a member: an account's membership of the caller's organization, leaving the account and its other
     * memberships as they are, unless no sign-in method signs the account in and no other membership remains,
     * when the account goes too, along with the invitation it never accepted; or an application user, with its
     * stored credential. Callers cannot remove themselves.
     *
     * @param identityId the member's id, as {@link Member#id()} names it
     */
    @AuthzCheck(permission = AuthzUtil.CAN_MANAGE_MEMBERS, consistent = true)
    Future<Void> removeMember(String identityId);

    /**
     * Lists the scope's live (unexpired) pending invitations: for the organization, those of people who already
     * had an account and of pending members sent one; for an application, those of its invited users.
     */
    @AuthzCheck(permission = AuthzUtil.CAN_READ_MEMBERS)
    Future<Page<PendingInviteSummary>> findPendingInvites(String applicationId, Pageable pageable);

    /** Cancels a pending invitation belonging to the caller's organization. */
    @AuthzCheck(permission = AuthzUtil.CAN_MANAGE_MEMBERS)
    Future<Void> cancelInvite(String inviteId);
}
