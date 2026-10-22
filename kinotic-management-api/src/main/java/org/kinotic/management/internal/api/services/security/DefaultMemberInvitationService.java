package org.kinotic.management.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.model.EmailTemplate;
import org.kinotic.domain.api.model.security.OrganizationMember;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.PendingInvite;
import org.kinotic.domain.api.model.security.identity.OrganizationParticipantIdentity;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.repositories.ApplicationRepository;
import org.kinotic.domain.api.services.security.InviteService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.api.services.security.OrganizationMembershipService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.domain.internal.api.services.EmailService;
import org.kinotic.management.api.services.security.MemberInvitationService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultMemberInvitationService implements MemberInvitationService {

    private final SecurityContext securityContext;
    private final ParticipantIdentityService identityService;
    private final OrganizationMembershipService membershipService;
    private final InviteService inviteService;
    private final OidcConfigurationService oidcConfigurationService;
    private final ApplicationRepository applicationRepository;
    private final EmailService emailService;

    @Override
    public Future<OrganizationMember> addMember(String email, String displayName, boolean sendInvitation,
                                                EmailTemplate emailTemplate) {
        OrganizationParticipant inviter = requireOrgParticipant();
        emailService.validateTemplate(emailTemplate);
        Validate.notBlank(email, "email is required");
        String address = DomainUtil.normalizeEmail(email);
        return identityService.findOrganizationUserByEmail(address)
                              .compose(user -> user == null
                                      ? provision(inviter, address, displayName, sendInvitation, emailTemplate)
                                      // the organization user is someone's, or another organization's member to claim: joining
                                      // it to this organization needs its holder's acceptance, whatever was asked
                                      : invite(inviter, address, displayName, null, emailTemplate)
                                              .map(invite -> new OrganizationMember(null, invite.getEmail(), invite.getDisplayName(), true,
                                                                        invite.getCreated(), true)));
    }

    // the organization creates the organization user and its membership at once: an invitation lets the address
    // claim the user, and without one only the organization's own provider can, so it must have one
    private Future<OrganizationMember> provision(OrganizationParticipant inviter, String address, String displayName,
                                                 boolean sendInvitation, EmailTemplate emailTemplate) {
        String organizationId = inviter.getOrganizationId();
        Future<Void> admissible = sendInvitation ? Future.succeededFuture() : requireOwnProvider(organizationId);
        OrganizationParticipantIdentity user = new OrganizationParticipantIdentity();
        user.setEmail(address)
            .setDisplayName(StringUtils.trimToNull(displayName));
        return admissible.compose(v -> identityService.createUser(user, null))
                         .compose(created -> membershipService.addMembership(created.getId(), organizationId)
                                 .compose(membership -> {
                                     Future<Void> invited = sendInvitation
                                             ? invite(inviter, address, displayName, null, emailTemplate).mapEmpty()
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
    public Future<Void> sendInvitation(String email, EmailTemplate emailTemplate) {
        OrganizationParticipant inviter = requireOrgParticipant();
        emailService.validateTemplate(emailTemplate);
        Validate.notBlank(email, "email is required");
        String organizationId = inviter.getOrganizationId();
        String address = DomainUtil.normalizeEmail(email);
        return inviteService.findPendingInvite(address, organizationId).compose(pending -> {
            Future<Void> ret;
            if (pending != null) {
                ret = inviteService.resendInvite(pending.getId(), organizationId, emailTemplate);
            } else {
                // a member added without an invitation gets one now, to claim the organization user with
                ret = pendingMember(address, organizationId)
                        .compose(member -> invite(inviter, address, member.getDisplayName(), null, emailTemplate))
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
    public Future<PendingInvite> inviteMember(String email, String displayName, String applicationId,
                                              EmailTemplate emailTemplate) {
        OrganizationParticipant inviter = requireOrgParticipant();
        emailService.validateTemplate(emailTemplate);
        Validate.notBlank(email, "email is required");
        Validate.isTrue(StringUtils.isNotBlank(applicationId), "applicationId is required; an organization's members are added with addMember");
        // an invitation persists a reference to the application, so it must be one of the organization's
        return applicationRepository.requireById(applicationId, inviter.getOrganizationId())
                                    .compose(app -> invite(inviter, DomainUtil.normalizeEmail(email), displayName, applicationId, emailTemplate));
    }

    private Future<PendingInvite> invite(OrganizationParticipant inviter, String email, String displayName,
                                         String applicationId, EmailTemplate emailTemplate) {
        PendingInvite invite = new PendingInvite()
                .setEmail(email)
                .setDisplayName(displayName)
                .setOrganizationId(inviter.getOrganizationId())
                .setApplicationId(applicationId)
                .setInvitedById(inviter.getId())
                .setInvitedByName(participantDisplayName(inviter));
        return inviteService.createInvite(invite, emailTemplate);
    }

    private OrganizationParticipant requireOrgParticipant() {
        // ApplicationParticipant is a sibling type, so app end-users are rejected here.
        return securityContext.requireParticipant(OrganizationParticipant.class);
    }

    private static String participantDisplayName(OrganizationParticipant participant) {
        String ret = participant.getId();
        if (participant.getMetadata() != null) {
            String name = participant.getMetadata().get("displayName");
            String email = participant.getMetadata().get("email");
            if (name != null && !name.isBlank()) {
                ret = name;
            } else if (email != null && !email.isBlank()) {
                ret = email;
            }
        }
        return ret;
    }
}
