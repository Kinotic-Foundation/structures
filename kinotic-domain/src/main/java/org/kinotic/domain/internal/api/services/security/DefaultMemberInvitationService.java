package org.kinotic.domain.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.kinotic.domain.api.model.security.Inviter;
import org.kinotic.domain.api.model.security.OrganizationMember;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.PendingInvite;
import org.kinotic.domain.api.model.security.identity.OrganizationParticipantIdentity;
import org.kinotic.domain.api.repositories.ApplicationRepository;
import org.kinotic.domain.api.services.security.InviteService;
import org.kinotic.domain.api.services.security.MemberInvitationService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.api.services.security.OrganizationMembershipService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultMemberInvitationService implements MemberInvitationService {

    private final ParticipantIdentityService identityService;
    private final OrganizationMembershipService membershipService;
    private final InviteService inviteService;
    private final OidcConfigurationService oidcConfigurationService;
    private final ApplicationRepository applicationRepository;

    @Override
    public Future<OrganizationMember> addMember(Inviter inviter, String email, String displayName,
                                                boolean sendInvitation, String emailTemplateId) {
        Validate.notNull(inviter, "inviter is required");
        Validate.notBlank(email, "email is required");
        String address = DomainUtil.normalizeEmail(email);
        return identityService.findOrganizationUserByEmail(address)
                              .compose(user -> user == null
                                      ? provision(inviter, address, displayName, sendInvitation, emailTemplateId)
                                      // the organization user is someone's, or another organization's member to claim: joining
                                      // it to this organization needs its holder's acceptance, whatever was asked
                                      : invite(inviter, address, displayName, null, emailTemplateId)
                                              .map(invite -> new OrganizationMember(null, invite.getEmail(), invite.getDisplayName(), true,
                                                                        invite.getCreated(), true)));
    }

    // the organization creates the organization user and its membership at once: an invitation lets the address
    // claim the user, and without one only the organization's own provider can, so it must have one
    private Future<OrganizationMember> provision(Inviter inviter, String address, String displayName,
                                                 boolean sendInvitation, String emailTemplateId) {
        String organizationId = inviter.organizationId();
        Future<Void> admissible = sendInvitation ? Future.succeededFuture() : requireOwnProvider(organizationId);
        OrganizationParticipantIdentity user = new OrganizationParticipantIdentity();
        user.setEmail(address)
            .setDisplayName(StringUtils.trimToNull(displayName));
        return admissible.compose(v -> identityService.createUser(user, null))
                         .compose(created -> membershipService.addMembership(created.getId(), organizationId)
                                 .compose(membership -> {
                                     Future<Void> invited = sendInvitation
                                             ? invite(inviter, address, displayName, null, emailTemplateId).mapEmpty()
                                             : Future.succeededFuture();
                                     return invited.map(v -> new OrganizationMember(created.getId(), created.getEmail(), created.getDisplayName(),
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
    public Future<Void> sendInvitation(Inviter inviter, String email, String emailTemplateId) {
        Validate.notNull(inviter, "inviter is required");
        Validate.notBlank(email, "email is required");
        String organizationId = inviter.organizationId();
        String address = DomainUtil.normalizeEmail(email);
        return inviteService.findPendingInvite(address, organizationId).compose(pending -> {
            Future<Void> ret;
            if (pending != null) {
                ret = inviteService.resendInvite(pending.getId(), organizationId, emailTemplateId);
            } else {
                // a member added without an invitation gets one now, to claim the organization user with
                ret = pendingMember(address, organizationId)
                        .compose(member -> invite(inviter, address, member.getDisplayName(), null, emailTemplateId))
                        .mapEmpty();
            }
            return ret;
        });
    }

    // the organization user of the organization's member at the address that no sign-in method signs in yet
    private Future<OrganizationParticipantIdentity> pendingMember(String address, String organizationId) {
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
    public Future<PendingInvite> inviteMember(Inviter inviter, String email, String displayName,
                                              String applicationId, String emailTemplateId) {
        Validate.notNull(inviter, "inviter is required");
        Validate.notBlank(email, "email is required");
        Validate.isTrue(StringUtils.isNotBlank(applicationId), "applicationId is required; an organization's members are added with addMember");
        // an invitation persists a reference to the application, so it must be one of the organization's
        return applicationRepository.requireById(applicationId, inviter.organizationId())
                                    .compose(app -> invite(inviter, DomainUtil.normalizeEmail(email), displayName, applicationId, emailTemplateId));
    }

    private Future<PendingInvite> invite(Inviter inviter, String email, String displayName, String applicationId,
                                         String emailTemplateId) {
        PendingInvite invite = new PendingInvite()
                .setEmail(email)
                .setDisplayName(displayName)
                .setOrganizationId(inviter.organizationId())
                .setApplicationId(applicationId)
                .setInvitedById(inviter.id())
                .setInvitedByName(inviter.displayName());
        return inviteService.createInvite(invite, emailTemplateId);
    }
}
