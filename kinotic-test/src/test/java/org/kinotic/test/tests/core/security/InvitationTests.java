package org.kinotic.test.tests.core.security;

import io.vertx.core.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.exceptions.UserDisabledException;
import org.kinotic.domain.api.exceptions.ExistingUserException;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.security.Member;
import org.kinotic.domain.api.model.security.PendingInvite;
import org.kinotic.domain.api.model.security.identity.OrganizationParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.SystemParticipantIdentity;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.security.InviteService;
import org.kinotic.domain.api.services.security.LocalAuthenticationService;
import org.kinotic.domain.api.services.security.OrganizationMembershipService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Invitations into an organization: an invitation is for someone who is not yet a member, and accepting one signs
 * in only an organization user that may sign in to the organization, never a user of another scope with the same
 * email. Also covers the member search, which must list the organization's members alone.
 */
@SpringBootTest
public class InvitationTests extends KinoticTestBase {

    private static final String PASSWORD = "Correct-Horse-Battery-9";
    private static final String SYSTEM_USER_PASSWORD = "Staple-Battery-Horse-7";

    @Autowired
    private InviteService invites;

    @Autowired
    private ParticipantIdentityService identities;

    @Autowired
    private OrganizationMembershipService memberships;

    @Autowired
    private LocalAuthenticationService localAuthentication;

    @Autowired
    private OrganizationService organizations;

    // an invitation names an organization that exists, so each test invites into one of its own
    private Organization organization;

    @BeforeEach
    public void createOrganization() throws Exception {
        organization = await(organizations.create(new Organization().setName("Invitation tests " + suffix())
                                                                    .setDescription("Organization of one invitation test")));
    }

    @Test
    public void anInvitationIsRefusedForAnEmailAlreadyAMemberOfTheOrganization() throws Exception {
        String email = "member-" + suffix() + "@example.test";
        OrganizationParticipantIdentity user = await(identities.createUser(organizationUser(email, "Existing member"), PASSWORD));
        await(memberships.addMembership(user.getId(), organization.getId()));

        Throwable refusal = failure(() -> invites.createInvite(invite(email)));
        assertInstanceOf(IllegalArgumentException.class, refusal);
        assertTrue(refusal.getMessage().contains("already exists in this organization"), refusal.getMessage());
    }

    @Test
    public void anInvitationAcceptedByADisabledMemberSignsNoOneIn() throws Exception {
        String email = "disabled-" + suffix() + "@example.test";
        PendingInvite invite = await(invites.createInvite(invite(email)));
        // the organization user joins and is disabled in the organization while the invitation is still out
        OrganizationParticipantIdentity user = await(identities.createUser(organizationUser(email, "Disabled member"), PASSWORD));
        await(memberships.addMembership(user.getId(), organization.getId()));
        await(memberships.setMembershipEnabled(user.getId(), organization.getId(), false));

        String token = invite.getVerificationToken();
        assertInstanceOf(ExistingUserException.class, failure(() -> invites.acceptLocalInvite(token, "not-the-password", null)));
        assertInstanceOf(UserDisabledException.class, failure(() -> invites.acceptLocalInvite(token, PASSWORD, null)));
        // a refused acceptance does not consume the invitation
        assertNotNull(await(invites.getValidInvite(token)));
    }

    @Test
    public void aSystemUsersEmailJoinsAnOrganizationAsAnOrganizationUserWithItsOwnPassword() throws Exception {
        String email = "both-" + suffix() + "@example.test";
        SystemParticipantIdentity systemUser = new SystemParticipantIdentity();
        systemUser.setEmail(email).setDisplayName("Sam System");
        await(identities.createUser(systemUser, SYSTEM_USER_PASSWORD));

        // the invitation sees no organization user for the address, so accepting it sets a new password
        PendingInvite invite = await(invites.createInvite(invite(email)));
        Participant member = await(invites.acceptLocalInvite(invite.getVerificationToken(), PASSWORD, null));
        assertEquals(organization.getId(), assertInstanceOf(OrganizationParticipant.class, member).getOrganizationId());

        // each scope signs in with its own password, and neither reaches the other
        assertNotNull(await(localAuthentication.authenticateSystemUser(email, SYSTEM_USER_PASSWORD)));
        assertNull(await(localAuthentication.authenticateSystemUser(email, PASSWORD)));
        assertNotNull(await(localAuthentication.authenticateOrganizationUser(email, PASSWORD)));
        assertNull(await(localAuthentication.authenticateOrganizationUser(email, SYSTEM_USER_PASSWORD)));
    }

    @Test
    public void memberSearchIsHeldToTheOrganizationsMembers() throws Exception {
        // one token, so the search matches these two organization users and nothing another test created
        String name = "zq" + suffix();
        OrganizationParticipantIdentity member = await(identities.createUser(organizationUser("zq-member-" + suffix() + "@example.test", name), PASSWORD));
        await(identities.createUser(organizationUser("zq-other-" + suffix() + "@example.test", name), PASSWORD));
        await(memberships.addMembership(member.getId(), TEST_ORG_ID));

        // the search reads the index, which sees a write only once it has refreshed
        Page<Member> found = awaitUntil(() -> memberships.searchMembers(name, TEST_ORG_ID, Pageable.ofSize(10)),
                                        page -> !page.getContent().isEmpty());
        assertEquals(List.of(member.getId()), found.getContent().stream().map(Member::id).toList());
        assertEquals(1, found.getTotalElements());
    }

    private PendingInvite invite(String email) {
        PendingInvite invite = new PendingInvite();
        invite.setEmail(email);
        invite.setDisplayName("Invitee");
        invite.setOrganizationId(organization.getId());
        invite.setInvitedByName("Invitation tests");
        return invite;
    }

    private static OrganizationParticipantIdentity organizationUser(String email, String displayName) {
        OrganizationParticipantIdentity user = new OrganizationParticipantIdentity();
        user.setEmail(email);
        user.setDisplayName(displayName);
        return user;
    }

    private Throwable failure(Supplier<Future<?>> call) {
        return assertThrows(ExecutionException.class, () -> await(runAsOrganization(call::get))).getCause();
    }

    private static <T> T awaitUntil(Supplier<Future<T>> call, Predicate<T> done) throws Exception {
        T ret = await(call.get());
        for (int attempt = 0; attempt < 20 && !done.test(ret); attempt++) {
            Thread.sleep(500);
            ret = await(call.get());
        }
        return ret;
    }
}
