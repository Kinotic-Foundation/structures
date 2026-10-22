package org.kinotic.system.internal.api.services;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.model.security.Inviter;
import org.kinotic.domain.api.model.security.OrganizationMember;
import org.kinotic.domain.api.services.security.MemberInvitationService;
import org.kinotic.management.api.model.PendingInviteSummary;
import org.kinotic.system.api.services.SystemMemberInvitationService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultSystemMemberInvitationService implements SystemMemberInvitationService {

    private final SecurityContext securityContext;
    private final MemberInvitationService memberInvitationService;

    @Override
    public Future<OrganizationMember> addMember(Inviter inviter, String email, String displayName, String emailTemplateId) {
        requireTrustedCaller();
        return memberInvitationService.addMember(inviter, email, displayName, true, emailTemplateId);
    }

    @Override
    public Future<Void> sendInvitation(Inviter inviter, String email, String emailTemplateId) {
        requireTrustedCaller();
        return memberInvitationService.sendInvitation(inviter, email, emailTemplateId);
    }

    @Override
    public Future<PendingInviteSummary> inviteMember(Inviter inviter, String email, String displayName,
                                                     String applicationId, String emailTemplateId) {
        requireTrustedCaller();
        return memberInvitationService.inviteMember(inviter, email, displayName, applicationId, emailTemplateId)
                                      .map(PendingInviteSummary::from);
    }

    // the inviter is taken on trust, so only a cluster member's own code may name one; every gateway binds the
    // connection's participant to the calls it forwards
    private void requireTrustedCaller() {
        securityContext.requireNoParticipant();
    }
}
