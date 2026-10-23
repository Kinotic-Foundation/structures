package org.kinotic.test.tests.core.security;

import io.vertx.core.Future;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.model.Application;
import org.kinotic.domain.api.model.ApplicationKey;
import org.kinotic.domain.api.model.TenantManagement;
import org.kinotic.domain.api.model.security.PendingSignUp;
import org.kinotic.domain.api.model.security.identity.ApplicationParticipantIdentity;
import org.kinotic.domain.api.services.security.LocalAuthenticationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.services.security.SignUpService;
import org.kinotic.domain.internal.api.repositories.PendingSignUpRepository;
import org.kinotic.management.api.services.ApplicationService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Verifies sign-up to an application with an email and password: an application whose tenants the platform manages
 * may allow it, and no other; the emailed link's token creates the user, with a tenant of its own, who then signs in
 * with the password; the token is spent once, at the application it was issued for, and founds no organization; and
 * a sign-up under an email that has a user already sends no link.
 */
@SpringBootTest
public class ApplicationSignUpTests extends KinoticTestBase {

    private static final String PASSWORD = "Correct-Horse-Battery-9";
    private static final String UI_BASE_URL = "https://sign-up--web.test-org.example.com";

    @Autowired
    private ApplicationService applications;

    @Autowired
    private SignUpService signUps;

    @Autowired
    private PendingSignUpRepository pendingSignUps;

    @Autowired
    private ParticipantIdentityService identities;

    @Autowired
    private LocalAuthenticationService authentication;

    private ApplicationKey open;
    private ApplicationKey closed;

    @BeforeEach
    public void createApplications() throws Exception {
        open = create("Sign Up Open " + suffix(), true);
        closed = create("Sign Up Closed " + suffix(), false);
    }

    @AfterEach
    public void deleteApplications() throws Exception {
        await(runAsOrganization(() -> applications.deleteById(open.applicationId())));
        await(runAsOrganization(() -> applications.deleteById(closed.applicationId())));
    }

    @Test
    public void onlyAnApplicationWhoseTenantsThePlatformManagesAllowsSignUp() {
        Application password = new Application("Sign Up Tenanted " + suffix(), "Tenants of its administrators")
                .setOrganizationId(TEST_ORG_ID)
                .setPasswordSignUp(true);
        assertInstanceOf(IllegalArgumentException.class, failure(() -> applications.create(password)));

        Application social = new Application("Sign Up Tenanted " + suffix(), "Tenants of its administrators")
                .setOrganizationId(TEST_ORG_ID)
                .setSocialSignUp(true);
        assertInstanceOf(IllegalArgumentException.class, failure(() -> applications.create(social)));
    }

    @Test
    public void theEmailedTokenCreatesAUserWithATenantOfItsOwnWhoSignsInWithThePassword() throws Exception {
        String email = "signs-up-" + suffix() + "@example.test";
        await(runAsOrganization(() -> signUps.initiateApplicationSignUp(open, email, "Pat Signs Up", UI_BASE_URL)));

        // a later start for the same email replaces the link of the earlier one
        String first = pendingToken(open, email);
        await(runAsOrganization(() -> signUps.initiateApplicationSignUp(open, email, "Pat Signs Up", UI_BASE_URL)));
        String token = pendingToken(open, email);
        assertNotEquals(first, token);
        assertInstanceOf(IllegalArgumentException.class, failure(() -> signUps.completeApplicationSignUp(open, first, PASSWORD)));

        Participant participant = await(runAsOrganization(() -> signUps.completeApplicationSignUp(open, token, PASSWORD)));
        assertNotNull(participant);

        ApplicationParticipantIdentity user = await(authentication.authenticateApplicationUser(email, PASSWORD, open.organizationId(),
                                                                                               open.applicationId()));
        assertNotNull(user);
        assertEquals(participant.getId(), user.getId());
        assertEquals("Pat Signs Up", user.getDisplayName());
        assertNotNull(user.getTenantId());

        // the token is spent, and a sign-up under the email that now has a user sends no link
        assertInstanceOf(IllegalArgumentException.class, failure(() -> signUps.completeApplicationSignUp(open, token, PASSWORD)));
        await(identities.syncIndex());
        await(runAsOrganization(() -> signUps.initiateApplicationSignUp(open, email, "Pat Again", UI_BASE_URL)));
        await(pendingSignUps.syncIndex());
        assertNull(await(pendingSignUps.findByApplicationAndEmail(open, email)));
    }

    @Test
    public void aTokenIsSpentOnlyAtTheApplicationItWasIssuedForAndFoundsNoOrganization() throws Exception {
        String email = "elsewhere-" + suffix() + "@example.test";
        await(runAsOrganization(() -> signUps.initiateApplicationSignUp(open, email, "Pat Elsewhere", UI_BASE_URL)));
        String token = pendingToken(open, email);

        ApplicationKey other = create("Sign Up Other " + suffix(), true);
        try {
            assertInstanceOf(IllegalArgumentException.class, failure(() -> signUps.completeApplicationSignUp(other, token, PASSWORD)));
        } finally {
            await(runAsOrganization(() -> applications.deleteById(other.applicationId())));
        }
        assertInstanceOf(IllegalArgumentException.class,
                         failure(() -> signUps.completeLocalSignUp(token, "Founded " + suffix(), null, PASSWORD)));

        assertNotNull(await(runAsOrganization(() -> signUps.completeApplicationSignUp(open, token, PASSWORD))));
    }

    @Test
    public void anApplicationThatDoesNotAllowSignUpRefusesIt() {
        String email = "refused-" + suffix() + "@example.test";
        assertInstanceOf(AuthorizationException.class,
                         failure(() -> signUps.initiateApplicationSignUp(closed, email, "Pat Refused", UI_BASE_URL)));
        assertInstanceOf(AuthorizationException.class,
                         failure(() -> signUps.completeApplicationSignUp(closed, "no-such-token", PASSWORD)));
    }

    private ApplicationKey create(String name, boolean passwordSignUp) throws Exception {
        Application application = new Application(name, "Application of one sign-up test")
                .setOrganizationId(TEST_ORG_ID)
                .setTenantManagement(TenantManagement.PLATFORM)
                .setPasswordSignUp(passwordSignUp);
        Application created = await(runAsOrganization(() -> applications.create(application)));
        return new ApplicationKey(TEST_ORG_ID, created.getId());
    }

    // the token the emailed link carries; a pending sign-up is found by a search, which sees it once the index has refreshed
    private String pendingToken(ApplicationKey application, String email) throws Exception {
        await(pendingSignUps.syncIndex());
        PendingSignUp pending = await(pendingSignUps.findByApplicationAndEmail(application, email));
        assertNotNull(pending);
        return pending.getVerificationToken();
    }

    private Throwable failure(Supplier<Future<?>> call) {
        return assertThrows(ExecutionException.class, () -> await(runAsOrganization(call::get))).getCause();
    }
}
