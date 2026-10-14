package org.kinotic.test.tests.core.security;

import io.vertx.core.Future;
import org.junit.jupiter.api.Test;
import org.kinotic.domain.api.model.Application;
import org.kinotic.domain.api.model.security.LoginProvider;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.model.security.OidcProviderKind;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.OrgSignupOidcConfiguration;
import org.kinotic.domain.api.services.security.LoginProviderService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.management.api.services.ApplicationService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies where an organization's OIDC configuration is offered: a configuration offered at the
 * organization's own login and at an application's login is found at each, and nowhere it is not offered;
 * the organization's login offers the curated social providers before its own; a disabled configuration
 * is offered nowhere; and a row naming a configuration or an application the organization does not have,
 * or offering a configuration twice at one scope, is refused.
 */
@SpringBootTest
public class LoginProviderTests extends KinoticTestBase {

    @Autowired
    private LoginProviderService loginProviders;

    @Autowired
    private OidcConfigurationService oidcConfigurations;

    @Autowired
    private ApplicationService applicationService;

    @Test
    public void aConfigurationIsOfferedWhereARowNamesIt() throws Exception {
        String appId = "login-provider-app-" + suffix();
        Application application = await(runAsOrganization(() -> applicationService.createApplicationIfNotExist(appId, "Login provider application", null)));
        OrganizationOidcConfiguration okta = await(runAsOrganization(() -> oidcConfigurations.save(provider("Acme Okta"), null)));

        // offered nowhere until a row names it
        assertNull(await(loginProviders.findOffered(okta.getId(), TEST_ORG_ID, null)));
        assertNull(await(loginProviders.findOffered(okta.getId(), TEST_ORG_ID, application.getId())));
        assertTrue(await(loginProviders.findEnabledForScope(TEST_ORG_ID, application.getId())).isEmpty());

        await(runAsOrganization(() -> loginProviders.save(row(okta, null))));
        await(runAsOrganization(() -> loginProviders.save(row(okta, application.getId()))));
        assertEquals(okta.getId(), await(loginProviders.findOffered(okta.getId(), TEST_ORG_ID, null)).getId());
        assertEquals(okta.getId(), await(loginProviders.findOffered(okta.getId(), TEST_ORG_ID, application.getId())).getId());
        assertEquals(List.of(okta.getId()), ids(await(loginProviders.findEnabledForScope(TEST_ORG_ID, application.getId()))));

        // the organization's own login offers the curated social providers first, then its own
        List<OidcConfiguration> atOrganization = await(loginProviders.findEnabledForScope(TEST_ORG_ID, null));
        int social = (int) atOrganization.stream().filter(OrgSignupOidcConfiguration.class::isInstance).count();
        assertTrue(social > 0, "the curated social providers are offered at the organization's login");
        assertTrue(atOrganization.subList(0, social).stream().allMatch(OrgSignupOidcConfiguration.class::isInstance));
        assertEquals(okta.getId(), atOrganization.get(atOrganization.size() - 1).getId());

        // another application of the organization does not offer it
        String otherAppId = "login-provider-app-" + suffix();
        await(runAsOrganization(() -> applicationService.createApplicationIfNotExist(otherAppId, "Other application", null)));
        assertNull(await(loginProviders.findOffered(okta.getId(), TEST_ORG_ID, otherAppId)));

        // a disabled configuration is offered nowhere, whatever rows name it
        await(runAsOrganization(() -> oidcConfigurations.save(okta.setEnabled(false))));
        assertNull(await(loginProviders.findOffered(okta.getId(), TEST_ORG_ID, application.getId())));
        assertTrue(await(loginProviders.findEnabledForScope(TEST_ORG_ID, application.getId())).isEmpty());
    }

    @Test
    public void aRowIsRefusedUnlessItNamesTheOrganizationsOwn() throws Exception {
        String appId = "login-provider-app-" + suffix();
        Application application = await(runAsOrganization(() -> applicationService.createApplicationIfNotExist(appId, "Login provider application", null)));
        OrganizationOidcConfiguration okta = await(runAsOrganization(() -> oidcConfigurations.save(provider("Acme Okta"), null)));

        OrganizationOidcConfiguration unknown = new OrganizationOidcConfiguration();
        unknown.setOrganizationId(TEST_ORG_ID);
        unknown.setId(UUID.randomUUID().toString());
        assertInstanceOf(IllegalArgumentException.class, failure(() -> loginProviders.save(row(unknown, null))));
        assertInstanceOf(IllegalArgumentException.class, failure(() -> loginProviders.save(row(okta, "no-such-application-" + suffix()))));

        await(runAsOrganization(() -> loginProviders.save(row(okta, application.getId()))));
        assertInstanceOf(IllegalArgumentException.class, failure(() -> loginProviders.save(row(okta, application.getId()))));
    }

    private static LoginProvider row(OrganizationOidcConfiguration configuration, String applicationId) {
        LoginProvider ret = new LoginProvider();
        ret.setOrganizationId(TEST_ORG_ID)
           .setApplicationId(applicationId)
           .setConfigurationId(configuration.getId());
        return ret;
    }

    private static OrganizationOidcConfiguration provider(String name) {
        OrganizationOidcConfiguration ret = new OrganizationOidcConfiguration();
        ret.setOrganizationId(TEST_ORG_ID);
        ret.setProviderAccount("acme.okta.com");
        ret.setName(name)
           .setProvider(OidcProviderKind.OKTA)
           .setClientId("acme-client")
           .setEnabled(true);
        return ret;
    }

    private static List<String> ids(List<OidcConfiguration> configurations) {
        return configurations.stream().map(OidcConfiguration::getId).toList();
    }

    private Throwable failure(Supplier<Future<?>> call) {
        return assertThrows(ExecutionException.class, () -> await(runAsOrganization(call::get))).getCause();
    }

    private static String suffix() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    private static <T> T await(Future<T> future) throws Exception {
        return future.toCompletionStage().toCompletableFuture().get(30, TimeUnit.SECONDS);
    }
}
