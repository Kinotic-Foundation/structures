package org.kinotic.test.tests.core.security;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.core.api.security.Participant;
import org.kinotic.core.api.security.ParticipantConstants;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.security.Member;
import org.kinotic.domain.api.model.security.OidcProviderType;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.PendingInvite;
import org.kinotic.domain.api.model.security.identity.OrganizationParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.SystemParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.model.security.participant.DefaultOrganizationParticipant;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.security.InviteService;
import org.kinotic.domain.api.services.security.OrganizationMembershipService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.management.api.services.security.MemberService;
import org.kinotic.management.api.services.security.OwnedOidcConfigurationService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies how an organization's administrators add members and how those members first sign in: an address with
 * no organization user becomes a pending member at once, with or without an invitation; the organization's own
 * provider claims the pending member it verifies under that address, once, and no one else; an invitation claims a
 * pending member with a password or a provider identity; an address that already has an organization user is
 * invited to accept instead; removing a pending member removes the organization user nobody signed in as; an
 * address added twice at once is one member; and a system user's address is added as a new organization user.
 */
@SpringBootTest
public class MemberProvisioningTests extends KinoticTestBase {

    private static final String PASSWORD = "Correct-Horse-Battery-9";

    @Autowired
    private MemberService members;

    @Autowired
    private OwnedOidcConfigurationService ownedConfigurations;

    @Autowired
    private ParticipantIdentityService identities;

    @Autowired
    private OrganizationMembershipService memberships;

    @Autowired
    private InviteService invites;

    @Autowired
    private OidcConfigurationService oidcConfigurations;

    @Autowired
    private OrganizationService organizations;

    // an invitation names an organization that exists, so each test adds members to one of its own, as its administrator
    private Organization organization;
    private OrganizationParticipant administrator;

    // the organization's own provider, through which a member added without an invitation signs in
    private OrganizationOidcConfiguration okta;

    @BeforeEach
    public void createOrganizationWithProvider() throws Exception {
        organization = await(organizations.create(new Organization().setName("Member provisioning " + suffix())
                                                                    .setDescription("Organization of one member provisioning test")));
        administrator = new DefaultOrganizationParticipant("member-admin-" + suffix(), organization.getId(),
                                                           Map.of(ParticipantConstants.PARTICIPANT_TYPE_METADATA_KEY, ParticipantConstants.PARTICIPANT_TYPE_USER,
                                                                  "displayName", "Member Admin"),
                                                           List.of("ADMIN"));
        OrganizationOidcConfiguration configuration = new OrganizationOidcConfiguration();
        configuration.setProviderAccount("acme.okta.com");
        configuration.setName("Acme Okta " + suffix())
                     .setProvider(OidcProviderType.OKTA)
                     .setClientId("acme-client")
                     .setEnabled(true);
        okta = (OrganizationOidcConfiguration) await(asAdministrator(() -> ownedConfigurations.save(configuration, null)));
        // offered configurations are a search, which sees the row once the index has refreshed
        await(oidcConfigurations.syncIndex());
    }

    private <T> Future<T> asAdministrator(Supplier<Future<T>> call) {
        return runAs(administrator, call);
    }

    @Test
    public void aMemberAddedWithoutAnInvitationIsClaimedByTheOrganizationsProviderOnce() throws Exception {
        String email = "pending-" + suffix() + "@example.test";
        Member added = await(asAdministrator(() -> members.addMember(email, "Pat Pending", false)));
        assertNotNull(added.id());
        assertTrue(added.pending());
        assertTrue(added.enabled());
        assertNull(await(invites.findPendingInvite(email, organization.getId())));
        assertFalse(await(identities.hasSignInMethod(added.id())));
        assertNotNull(await(memberships.findMembership(added.id(), organization.getId())));
        assertTrue(listed(email).pending());

        // the organization's provider claims the member under the verified email, binding the identity as the first
        // method; the organization user is found by a search, which sees it once the index has refreshed
        await(identities.syncIndex());
        Participant claimed = await(identities.claimProvisionedOrganizationUser(okta.getId(), "sub-" + suffix(), email, organization.getId()));
        assertNotNull(claimed);
        assertEquals(added.id(), claimed.getId());
        assertTrue(await(identities.hasSignInMethod(added.id())));
        assertFalse(listed(email).pending());

        // once claimed, no other identity under the same email takes the member, and no stranger's email finds one
        assertNull(await(identities.claimProvisionedOrganizationUser(okta.getId(), "sub-" + suffix(), email, organization.getId())));
        assertNull(await(identities.claimProvisionedOrganizationUser(okta.getId(), "sub-" + suffix(), "nobody-" + suffix() + "@example.test", organization.getId())));
    }

    @Test
    public void anAddressAddedTwiceAtOnceIsOneMember() throws Exception {
        String email = "twice-" + suffix() + "@example.test";
        // both calls look the address up before either organization user is written, so the index tells neither of the
        // other; the organization user's id is derived from the address, so the create of one of them is refused
        CompositeFuture both = await(asAdministrator(() -> {
            CompositeFuture joined = Future.join(members.addMember(email, "Pat Twice", false), members.addMember(email, "Pat Twice", false));
            return joined.transform(v -> Future.succeededFuture(joined));
        }));
        int added = 0;
        for (int i = 0; i < both.size(); i++) {
            if (both.succeeded(i)) {
                added++;
            } else {
                assertInstanceOf(IllegalArgumentException.class, both.cause(i));
            }
        }
        assertEquals(1, added, "exactly one addition succeeds");
        await(identities.syncIndex());
        Page<Member> page = await(asAdministrator(() -> members.findMembers(null, Pageable.ofSize(1000))));
        assertEquals(added, page.getContent().stream().filter(member -> email.equals(member.email())).count());
    }

    @Test
    public void aSystemUsersAddressIsAddedAsANewOrganizationUser() throws Exception {
        String email = "system-user-" + suffix() + "@example.test";
        SystemParticipantIdentity systemUser = new SystemParticipantIdentity();
        systemUser.setEmail(email).setDisplayName("Sam System");
        await(identities.createUser(systemUser, PASSWORD));
        await(identities.syncIndex());

        // the address has no organization user, so the organization adds one, pending, beside the system user
        Member added = await(asAdministrator(() -> members.addMember(email, "Sam Member", false)));
        assertNotNull(added.id());
        assertNotEquals(systemUser.getId(), added.id());
        assertTrue(added.pending());
        assertNotNull(await(memberships.findMembership(added.id(), organization.getId())));
        assertTrue(await(identities.hasPassword(systemUser.getId())));
        assertTrue(await(memberships.findMemberships(systemUser.getId())).isEmpty());
    }

    @Test
    public void aMemberIsClaimedOnlyWhereTheOrganizationAddedThem() throws Exception {
        // an organization user nobody signs in as that holds no membership of the organization is no one's to claim here
        String email = "elsewhere-" + suffix() + "@example.test";
        OrganizationParticipantIdentity user = await(identities.createUser(organizationUser(email, "Added elsewhere"), null));
        await(identities.syncIndex());
        assertNull(await(identities.claimProvisionedOrganizationUser(okta.getId(), "sub-" + suffix(), email, organization.getId())));
        assertFalse(await(identities.hasSignInMethod(user.getId())));
    }

    @Test
    public void aMemberAddedWithAnInvitationClaimsTheOrganizationUserByAccepting() throws Exception {
        String withPassword = "accepts-" + suffix() + "@example.test";
        Member added = await(asAdministrator(() -> members.addMember(withPassword, "Pat Accepts", true)));
        await(identities.syncIndex());
        PendingInvite invite = await(invites.findPendingInvite(withPassword, organization.getId()));
        assertNotNull(invite);
        await(asAdministrator(() -> members.sendInvitation(withPassword)));

        Participant member = await(invites.acceptLocalInvite(invite.getVerificationToken(), PASSWORD, "Pat Herself"));
        assertEquals(added.id(), member.getId());
        assertTrue(await(identities.hasPassword(added.id())));
        assertEquals("Pat Herself", ((UserParticipantIdentity) await(identities.findById(added.id()))).getDisplayName());
        assertFalse(listed(withPassword).pending());

        // or through one of the organization's providers, which binds the identity
        String withProvider = "binds-" + suffix() + "@example.test";
        Member bound = await(asAdministrator(() -> members.addMember(withProvider, "Pat Binds", true)));
        await(identities.syncIndex());
        PendingInvite providerInvite = await(invites.findPendingInvite(withProvider, organization.getId()));
        Participant viaProvider = await(invites.acceptOidcInvite(providerInvite.getVerificationToken(), "sub-" + suffix(), okta.getId(), withProvider));
        assertEquals(bound.id(), viaProvider.getId());
        assertTrue(await(identities.hasSignInMethod(bound.id())));
    }

    @Test
    public void anAddressWithAnOrganizationUserIsInvitedToAcceptInstead() throws Exception {
        String email = "existing-" + suffix() + "@example.test";
        OrganizationParticipantIdentity user = await(identities.createUser(organizationUser(email, "Existing person"), PASSWORD));
        await(identities.syncIndex());

        Member invited = await(asAdministrator(() -> members.addMember(email, null, false)));
        assertNull(invited.id());
        assertTrue(invited.pending());
        assertNotNull(await(invites.findPendingInvite(email, organization.getId())));
        assertNull(await(memberships.findMembership(user.getId(), organization.getId())));

        // a second invitation is refused while one is pending, and the address once it is a member
        assertTrue(failure(() -> members.addMember(email, null, true)).getMessage().contains("already pending"));
        await(memberships.addMembership(user.getId(), organization.getId()));
        assertTrue(failure(() -> members.addMember(email, null, true)).getMessage().contains("already exists in this organization"));
    }

    @Test
    public void removingAPendingMemberRemovesTheOrganizationUserNobodySignedInAs() throws Exception {
        String email = "removed-" + suffix() + "@example.test";
        Member added = await(asAdministrator(() -> members.addMember(email, "Pat Removed", true)));

        await(asAdministrator(() -> members.removeMember(added.id())));
        assertNull(await(identities.findById(added.id())));
        assertNull(await(invites.findPendingInvite(email, organization.getId())));

        // the address can be added again: the organization user takes the id the address derives, and nothing of the old one is left
        Member again = await(asAdministrator(() -> members.addMember(email, "Pat Again", false)));
        assertEquals(added.id(), again.id());
        assertTrue(again.pending());
        assertFalse(await(identities.hasSignInMethod(again.id())));
        assertNotNull(await(memberships.findMembership(again.id(), organization.getId())));
    }

    @Test
    public void anInvitationIntoAnApplicationNamesOne() throws Exception {
        assertInstanceOf(IllegalArgumentException.class, failure(() -> members.inviteMember("someone-" + suffix() + "@example.test", null, null)));
    }

    // the member at email as the organization's members page lists them
    private Member listed(String email) throws Exception {
        Page<Member> page = await(asAdministrator(() -> members.findMembers(null, Pageable.ofSize(1000))));
        return page.getContent().stream().filter(member -> email.equals(member.email())).findFirst().orElseThrow();
    }

    private static OrganizationParticipantIdentity organizationUser(String email, String displayName) {
        OrganizationParticipantIdentity ret = new OrganizationParticipantIdentity();
        ret.setEmail(email).setDisplayName(displayName);
        return ret;
    }

    private Throwable failure(Supplier<Future<?>> call) {
        return assertThrows(ExecutionException.class, () -> await(asAdministrator(call::get))).getCause();
    }
}
