package org.kinotic.management.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.model.security.OrganizationMember;
import org.kinotic.domain.api.model.security.identity.ApplicationParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.OrganizationParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.services.security.InviteService;
import org.kinotic.domain.api.services.security.OrganizationMembershipService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.management.api.model.PendingInviteSummary;
import org.kinotic.management.api.services.security.MemberInvitationService;
import org.kinotic.management.api.services.security.MemberService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final SecurityContext securityContext;
    private final ParticipantIdentityService identityService;
    private final OrganizationMembershipService membershipService;
    private final InviteService inviteService;
    private final MemberInvitationService memberInvitationService;

    @Override
    public Future<Page<OrganizationMember>> findMembers(Pageable pageable) {
        return membershipService.findMembers(requireOrgParticipant().getOrganizationId(), pageable);
    }

    @Override
    public Future<Page<OrganizationMember>> searchMembers(String searchText, Pageable pageable) {
        return membershipService.searchMembers(searchText, requireOrgParticipant().getOrganizationId(), pageable);
    }

    // scope-composed queries, so a foreign or unknown application matches nothing
    @Override
    public Future<Page<ApplicationParticipantIdentity>> findApplicationUsers(String applicationId, Pageable pageable) {
        return identityService.findApplicationUsers(requireOrgParticipant().getOrganizationId(), applicationId, pageable);
    }

    @Override
    public Future<Page<ApplicationParticipantIdentity>> searchApplicationUsers(String searchText, String applicationId, Pageable pageable) {
        return identityService.searchApplicationUsers(searchText, requireOrgParticipant().getOrganizationId(), applicationId, pageable);
    }

    @Override
    public Future<OrganizationMember> addMember(String email, String displayName, boolean sendInvitation) {
        return memberInvitationService.addMember(email, displayName, sendInvitation, null);
    }

    @Override
    public Future<Void> sendInvitation(String email) {
        return memberInvitationService.sendInvitation(email, null);
    }

    @Override
    public Future<PendingInviteSummary> inviteMember(String email, String displayName, String applicationId) {
        return memberInvitationService.inviteMember(email, displayName, applicationId, null)
                                      .map(PendingInviteSummary::from);
    }

    @Override
    public Future<Void> setMemberEnabled(String identityId, boolean enabled) {
        OrganizationParticipant participant = requireOrgParticipant();
        return loadOwnedMember(identityId, participant)
                .compose(member -> member instanceof OrganizationParticipantIdentity
                        ? membershipService.setMembershipEnabled(member.getId(), participant.getOrganizationId(), enabled)
                        // saveSync so the console's immediate re-query sees the change
                        : identityService.saveSync(member.setEnabled(enabled)).mapEmpty());
    }

    @Override
    public Future<Void> removeMember(String identityId) {
        OrganizationParticipant participant = requireOrgParticipant();
        return loadOwnedMember(identityId, participant)
                .compose(member -> member instanceof OrganizationParticipantIdentity user
                        ? removeOrganizationMember(user, participant.getOrganizationId())
                        // Cascades the IdentityCredential; sync so the console's immediate re-query
                        // no longer shows the member.
                        : identityService.deleteByIdSync(member.getId()));
    }

    // the membership goes, with the invitation the member never accepted, and so does an organization user nobody
    // signs in as that holds no other membership, which nothing could reach
    private Future<Void> removeOrganizationMember(OrganizationParticipantIdentity user, String organizationId) {
        return membershipService.removeMembership(user.getId(), organizationId)
                .compose(v -> inviteService.findPendingInvite(user.getEmail(), organizationId))
                .compose(invite -> invite == null
                        ? Future.<Void>succeededFuture()
                        : inviteService.cancelInvite(invite.getId(), organizationId))
                .compose(v -> identityService.hasSignInMethod(user.getId()))
                .compose(claimed -> claimed
                        ? Future.succeededFuture(false)
                        : membershipService.findMemberships(user.getId()).map(List::isEmpty))
                .compose(orphaned -> orphaned
                        ? identityService.deleteByIdSync(user.getId())
                        : Future.<Void>succeededFuture());
    }

    @Override
    public Future<Page<PendingInviteSummary>> findPendingInvites(String applicationId, Pageable pageable) {
        OrganizationParticipant participant = requireOrgParticipant();
        return inviteService.findPendingInvites(participant.getOrganizationId(),
                                                applicationId,
                                                pageable)
                .map(page -> new Page<>(page.getContent().stream()
                                            .map(PendingInviteSummary::from)
                                            .toList(),
                                        page.getTotalElements()));
    }

    @Override
    public Future<Void> cancelInvite(String inviteId) {
        OrganizationParticipant participant = requireOrgParticipant();
        return inviteService.cancelInvite(inviteId, participant.getOrganizationId());
    }

    private OrganizationParticipant requireOrgParticipant() {
        // ApplicationParticipant is a sibling type, so app end-users are rejected here.
        return securityContext.requireParticipant(OrganizationParticipant.class);
    }

    /**
     * Loads a member of the participant's organization for mutation: an organization user holding a membership of
     * it, or a user of one of its applications. Rejects acting on the caller themselves, so an admin can't
     * disable or remove themselves out of the org.
     */
    private Future<UserParticipantIdentity> loadOwnedMember(String identityId, OrganizationParticipant participant) {
        Validate.notBlank(identityId, "identityId is required");
        if (identityId.equals(participant.getId())) {
            return Future.failedFuture(
                    new IllegalArgumentException("You cannot perform this action on yourself."));
        }
        return identityService.findById(identityId)
                .compose(identity -> {
                    // a delegate id gets the same not-found as a foreign one — member management
                    // operates on people; delegates are managed by their owner's revocation flow
                    UserParticipantIdentity user = DomainUtil.requireOwned(identity, UserParticipantIdentity.class,
                            found -> found instanceof OrganizationParticipantIdentity
                                    || (found instanceof ApplicationParticipantIdentity application
                                        && participant.getOrganizationId().equals(application.getOrganizationId())),
                            "Member not found.");
                    // an organization user is the organization's member only through a membership of it
                    return user instanceof OrganizationParticipantIdentity
                            ? membershipService.findMembership(user.getId(), participant.getOrganizationId()).map(membership -> {
                                Validate.isTrue(membership != null, "Member not found.");
                                return user;
                            })
                            : Future.succeededFuture(user);
                });
    }
}
