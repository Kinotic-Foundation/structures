package org.kinotic.management.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.model.security.Member;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.PendingInvite;
import org.kinotic.domain.api.model.security.identity.ApplicationUserParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.OrganizationUserParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.repositories.ApplicationRepository;
import org.kinotic.domain.api.services.security.InviteService;
import org.kinotic.domain.api.services.security.OrganizationMembershipService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.management.api.model.PendingInviteSummary;
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
    private final OidcConfigurationService oidcConfigurationService;
    private final ApplicationRepository applicationRepository;

    @Override
    public Future<Page<Member>> findMembers(String applicationId, Pageable pageable) {
        OrganizationParticipant participant = requireOrgParticipant();
        // an application's members are its users; scope-composed query, so a foreign or unknown application matches nothing
        return applicationId == null
                ? membershipService.findMembers(participant.getOrganizationId(), pageable)
                : identityService.findApplicationUsers(participant.getOrganizationId(), applicationId, pageable)
                                 .map(page -> page.map(Member::of));
    }

    @Override
    public Future<Page<Member>> searchMembers(String searchText, String applicationId, Pageable pageable) {
        OrganizationParticipant participant = requireOrgParticipant();
        return applicationId == null
                ? membershipService.searchMembers(searchText, participant.getOrganizationId(), pageable)
                : identityService.searchApplicationUsers(searchText, participant.getOrganizationId(), applicationId, pageable)
                                 .map(page -> page.map(Member::of));
    }

    @Override
    public Future<Member> addMember(String email, String displayName, boolean sendInvitation) {
        Validate.notBlank(email, "email is required");
        OrganizationParticipant participant = requireOrgParticipant();
        String address = DomainUtil.normalizeEmail(email);
        return identityService.findOrganizationUserByEmail(address)
                              .compose(user -> user == null
                                      ? provision(participant, address, displayName, sendInvitation)
                                      // the organization user is someone's, or another organization's member to claim: joining
                                      // it to this organization needs its holder's acceptance, whatever was asked
                                      : invite(participant, address, displayName, null)
                                              .map(invite -> new Member(null, invite.getEmail(), invite.getDisplayName(), true,
                                                                        invite.getCreated(), true)));
    }

    // the organization creates the organization user and its membership at once: an invitation lets the address
    // claim the user, and without one only the organization's own provider can, so it must have one
    private Future<Member> provision(OrganizationParticipant participant, String address, String displayName, boolean sendInvitation) {
        String organizationId = participant.getOrganizationId();
        Future<Void> admissible = sendInvitation ? Future.succeededFuture() : requireOwnProvider(organizationId);
        OrganizationUserParticipantIdentity user = new OrganizationUserParticipantIdentity();
        user.setEmail(address)
            .setDisplayName(StringUtils.trimToNull(displayName));
        return admissible.compose(v -> identityService.createUser(user, null))
                         .compose(created -> membershipService.addMembership(created.getId(), organizationId)
                                 .compose(membership -> {
                                     Future<Void> invited = sendInvitation
                                             ? invite(participant, address, displayName, null).mapEmpty()
                                             : Future.succeededFuture();
                                     return invited.map(v -> new Member(created.getId(), created.getEmail(), created.getDisplayName(),
                                                                        true, membership.getCreated(), true));
                                 }));
    }

    private Future<Void> requireOwnProvider(String organizationId) {
        return oidcConfigurationService.findEnabledForScope(organizationId, null).map(offered -> {
            Validate.isTrue(offered.stream().anyMatch(OrganizationOidcConfiguration.class::isInstance),
                            "Without an invitation a new member can sign in only through one of your organization's own"
                                    + " sign-in providers; configure one or send the invitation.");
            return null;
        });
    }

    @Override
    public Future<Void> sendInvitation(String email) {
        Validate.notBlank(email, "email is required");
        OrganizationParticipant participant = requireOrgParticipant();
        String organizationId = participant.getOrganizationId();
        String address = DomainUtil.normalizeEmail(email);
        return inviteService.findPendingInvite(address, organizationId).compose(pending -> {
            Future<Void> ret;
            if (pending != null) {
                ret = inviteService.resendInvite(pending.getId(), organizationId);
            } else {
                // a member added without an invitation gets one now, to claim the organization user with
                ret = pendingMember(address, organizationId)
                        .compose(member -> invite(participant, address, member.getDisplayName(), null))
                        .mapEmpty();
            }
            return ret;
        });
    }

    // the organization user of the organization's member at the address that no sign-in method signs in yet
    private Future<OrganizationUserParticipantIdentity> pendingMember(String address, String organizationId) {
        return identityService.findOrganizationUserByEmail(address).compose(user -> {
            Validate.isTrue(user != null, "No pending member has this email.");
            return membershipService.findMembership(user.getId(), organizationId).compose(membership -> {
                Validate.isTrue(membership != null, "No pending member has this email.");
                return identityService.hasSignInMethod(user.getId()).map(claimed -> {
                    Validate.isTrue(!claimed, "This member has already signed in.");
                    return user;
                });
            });
        });
    }

    @Override
    public Future<PendingInviteSummary> inviteMember(String email, String displayName, String applicationId) {
        Validate.notBlank(email, "email is required");
        Validate.isTrue(StringUtils.isNotBlank(applicationId), "applicationId is required; an organization's members are added with addMember");
        OrganizationParticipant participant = requireOrgParticipant();
        // an invitation persists a reference to the application, so it must be one of the organization's
        return applicationRepository.requireById(applicationId, participant.getOrganizationId())
                                    .compose(app -> invite(participant, DomainUtil.normalizeEmail(email), displayName, applicationId))
                                    .map(PendingInviteSummary::from);
    }

    private Future<PendingInvite> invite(OrganizationParticipant participant, String email, String displayName, String applicationId) {
        PendingInvite invite = new PendingInvite()
                .setEmail(email)
                .setDisplayName(displayName)
                .setOrganizationId(participant.getOrganizationId())
                .setApplicationId(applicationId)
                .setInvitedById(participant.getId())
                .setInvitedByName(participantDisplayName(participant));
        return inviteService.createInvite(invite);
    }

    @Override
    public Future<Void> setMemberEnabled(String identityId, boolean enabled) {
        OrganizationParticipant participant = requireOrgParticipant();
        return loadOwnedMember(identityId, participant)
                .compose(member -> member instanceof OrganizationUserParticipantIdentity
                        ? membershipService.setMembershipEnabled(member.getId(), participant.getOrganizationId(), enabled)
                        // saveSync so the console's immediate re-query sees the change
                        : identityService.saveSync(member.setEnabled(enabled)).mapEmpty());
    }

    @Override
    public Future<Void> removeMember(String identityId) {
        OrganizationParticipant participant = requireOrgParticipant();
        return loadOwnedMember(identityId, participant)
                .compose(member -> member instanceof OrganizationUserParticipantIdentity user
                        ? removeOrganizationMember(user, participant.getOrganizationId())
                        // Cascades the IdentityCredential; sync so the console's immediate re-query
                        // no longer shows the member.
                        : identityService.deleteByIdSync(member.getId()));
    }

    // the membership goes, with the invitation the member never accepted, and so does an organization user nobody
    // signs in as that holds no other membership, which nothing could reach
    private Future<Void> removeOrganizationMember(OrganizationUserParticipantIdentity user, String organizationId) {
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
                    new IllegalArgumentException("You cannot perform this action on your own account."));
        }
        return identityService.findById(identityId)
                .compose(identity -> {
                    // a delegate id gets the same not-found as a foreign one — member management
                    // operates on people; delegates are managed by their owner's revocation flow
                    UserParticipantIdentity user = DomainUtil.requireOwned(identity, UserParticipantIdentity.class,
                            found -> found instanceof OrganizationUserParticipantIdentity
                                    || (found instanceof ApplicationUserParticipantIdentity
                                        && participant.getOrganizationId().equals(found.getOrganizationId())),
                            "Member not found.");
                    // an organization user is the organization's member only through a membership of it
                    return user instanceof OrganizationUserParticipantIdentity
                            ? membershipService.findMembership(user.getId(), participant.getOrganizationId()).map(membership -> {
                                Validate.isTrue(membership != null, "Member not found.");
                                return user;
                            })
                            : Future.succeededFuture(user);
                });
    }

    private static String participantDisplayName(OrganizationParticipant participant) {
        if (participant.getMetadata() != null) {
            String name = participant.getMetadata().get("displayName");
            if (name != null && !name.isBlank()) {
                return name;
            }
            String email = participant.getMetadata().get("email");
            if (email != null && !email.isBlank()) {
                return email;
            }
        }
        return participant.getId();
    }
}
