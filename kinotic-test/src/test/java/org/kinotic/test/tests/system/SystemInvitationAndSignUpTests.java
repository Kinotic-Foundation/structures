package org.kinotic.test.tests.system;

import io.vertx.core.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.core.api.security.ParticipantConstants;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.SystemEmailTemplate;
import org.kinotic.domain.api.model.security.Inviter;
import org.kinotic.domain.api.model.security.OrganizationMember;
import org.kinotic.domain.api.model.security.PendingInvite;
import org.kinotic.domain.api.model.security.participant.DefaultOrganizationParticipant;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.security.InviteService;
import org.kinotic.system.api.services.SystemMemberInvitationService;
import org.kinotic.system.api.services.SystemSignUpService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies the invitations and sign-ups a platform service sends with its own email template: a cluster member
 * calling as itself invites on behalf of the organization user it names, with the template it names, and starts a
 * sign-up; a call made for an organization's user is refused, so no gateway connection chooses a template.
 */
@SpringBootTest
public class SystemInvitationAndSignUpTests extends KinoticTestBase {

    @Autowired
    private SystemMemberInvitationService systemInvitations;

    @Autowired
    private SystemSignUpService systemSignUps;

    @Autowired
    private OrganizationService organizations;

    @Autowired
    private InviteService invites;

    private Organization organization;
    private OrganizationParticipant inviterParticipant;
    private Inviter inviter;

    @BeforeEach
    public void createOrganization() throws Exception {
        organization = await(organizations.create(new Organization().setName("System invitations " + suffix())
                                                                    .setDescription("Organization of one system invitation test")));
        inviterParticipant = new DefaultOrganizationParticipant("inviter-" + suffix(), organization.getId(),
                                                     Map.of(ParticipantConstants.PARTICIPANT_TYPE_METADATA_KEY, ParticipantConstants.PARTICIPANT_TYPE_USER,
                                                            "displayName", "Ivy Inviter"),
                                                     List.of("ADMIN"));
        inviter = Inviter.from(inviterParticipant);
    }

    @Test
    public void aClusterMemberInvitesOnBehalfOfTheInviterWithTheTemplateItNames() throws Exception {
        String email = "invited-" + suffix() + "@example.test";
        OrganizationMember added = asClusterMember(() -> systemInvitations.addMember(inviter, email, "Pat Invited",
                                                                                     SystemEmailTemplate.DEFAULT_INVITATION_ID));
        assertNotNull(added.id());
        assertTrue(added.pending());

        PendingInvite invite = await(invites.findPendingInvite(email, organization.getId()));
        assertNotNull(invite);
        assertEquals(inviterParticipant.getId(), invite.getInvitedById());
        assertEquals("Ivy Inviter", invite.getInvitedByName());

        ExecutionException unknown = assertThrows(ExecutionException.class,
                                                  () -> asClusterMember(() -> systemInvitations.sendInvitation(inviter, email,
                                                                                                               "no-such-template-" + suffix())));
        assertTrue(unknown.getCause().getMessage().contains("No email template has id"), unknown.getCause().getMessage());
    }

    @Test
    public void aCallForAnOrganizationsUserIsRefused() {
        String email = "refused-" + suffix() + "@example.test";
        ExecutionException invitation = assertThrows(ExecutionException.class,
                                                     () -> await(runAs(inviterParticipant, () -> systemInvitations.addMember(inviter, email, null,
                                                                                                                  SystemEmailTemplate.DEFAULT_INVITATION_ID))));
        assertInstanceOf(AuthorizationException.class, invitation.getCause());

        ExecutionException signUp = assertThrows(ExecutionException.class,
                                                 () -> await(runAs(inviterParticipant, () -> systemSignUps.initiateLocalSignUp(email, "Pat Refused",
                                                                                                                    SystemEmailTemplate.DEFAULT_VERIFICATION_ID))));
        assertInstanceOf(AuthorizationException.class, signUp.getCause());
    }

    @Test
    public void aClusterMemberStartsASignUpWithTheTemplateItNames() throws Exception {
        asClusterMember(() -> systemSignUps.initiateLocalSignUp("founder-" + suffix() + "@example.test", "Fran Founder",
                                                                SystemEmailTemplate.DEFAULT_VERIFICATION_ID));
    }

    private <T> T asClusterMember(Supplier<Future<T>> call) throws Exception {
        return securityContext.callWithoutParticipant(() -> call.get().toCompletionStage())
                              .get(30, TimeUnit.SECONDS);
    }
}
