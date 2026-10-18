package org.kinotic.test.tests.core.security;

import io.vertx.core.Future;
import org.junit.jupiter.api.Test;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.domain.api.model.security.ApplicationOidcConfiguration;
import org.kinotic.domain.api.model.security.OidcProviderKind;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.OrgSignupOidcConfiguration;
import org.kinotic.domain.api.model.security.OwnedOidcConfiguration;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.management.api.services.ApplicationService;
import org.kinotic.management.api.services.security.OwnedOidcConfigurationService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies the published service an organization manages its own OIDC configurations through: a saved
 * configuration is the caller's organization's whatever organization it named, is found and listed, holds its
 * secret, is copied into an application's scope and deleted; another owner's row is neither written over nor
 * deleted by its id; and the organization's own login takes {@code createUserOnSignIn} for a provider that verifies
 * the organization's users alone, while an application's takes it for any.
 */
@SpringBootTest
public class OwnedOidcConfigurationServiceTests extends KinoticTestBase {

    @Autowired
    private OwnedOidcConfigurationService ownedConfigurations;

    @Autowired
    private OidcConfigurationService oidcConfigurations;

    @Autowired
    private ApplicationService applicationService;

    @Test
    public void aConfigurationIsTheCallersOrganizationsAndRoundTrips() throws Exception {
        String appId = "owned-oidc-app-" + suffix();
        await(runAsOrganization(() -> applicationService.createApplicationIfNotExist(appId, "Owned application", null)));
        OrganizationOidcConfiguration portalOkta = okta(new OrganizationOidcConfiguration(), "Acme Okta");
        portalOkta.setOrganizationId("globex");

        OwnedOidcConfiguration saved = await(runAsOrganization(() -> ownedConfigurations.save(portalOkta, "acme-secret")));
        assertNotNull(saved.getId());
        assertEquals(TEST_ORG_ID, saved.getOrganizationId());
        assertEquals("https://acme.okta.com", saved.getAuthority());
        assertTrue(saved.isConfidentialClient());
        assertEquals("acme-secret", await(oidcConfigurations.findClientSecret(saved)));
        assertEquals(saved.getId(), await(runAsOrganization(() -> ownedConfigurations.findById(saved.getId()))).getId());

        // the listing is a search, which sees a write only once the index has refreshed
        await(oidcConfigurations.syncIndex());
        Page<OwnedOidcConfiguration> page = await(runAsOrganization(() -> ownedConfigurations.findAll(Pageable.ofSize(100))));
        assertTrue(page.getContent().stream().anyMatch(configuration -> saved.getId().equals(configuration.getId())));
        assertTrue(page.getContent().stream().allMatch(configuration -> TEST_ORG_ID.equals(configuration.getOrganizationId())));

        OwnedOidcConfiguration copy = await(runAsOrganization(() -> ownedConfigurations.copy(saved.getId(), appId)));
        assertEquals(appId, assertInstanceOf(ApplicationOidcConfiguration.class, copy).getApplicationId());
        assertEquals("acme-secret", await(oidcConfigurations.findClientSecret(copy)));

        await(runAsOrganization(() -> ownedConfigurations.deleteById(saved.getId())));
        assertNull(await(runAsOrganization(() -> ownedConfigurations.findById(saved.getId()))));
        assertNotNull(await(runAsOrganization(() -> ownedConfigurations.findById(copy.getId()))));
    }

    @Test
    public void anotherOwnersRowIsNeitherWrittenOverNorDeletedByItsId() throws Exception {
        OrgSignupOidcConfiguration github = await(oidcConfigurations.findEnabledOrgSignupProviders()).getFirst();
        OrganizationOidcConfiguration squatter = okta(new OrganizationOidcConfiguration(), "Not GitHub");
        squatter.setId(github.getId());

        assertInstanceOf(AuthorizationException.class, failure(() -> ownedConfigurations.save(squatter, null)));
        assertInstanceOf(OrgSignupOidcConfiguration.class, await(oidcConfigurations.findById(github.getId())));

        await(runAsOrganization(() -> ownedConfigurations.deleteById(github.getId())));
        assertInstanceOf(OrgSignupOidcConfiguration.class, await(oidcConfigurations.findById(github.getId())));
    }

    @Test
    public void theOrganizationsLoginAdmitsOnSignInThroughItsOwnDirectoryOnly() throws Exception {
        String appId = "owned-oidc-app-" + suffix();
        await(runAsOrganization(() -> applicationService.createApplicationIfNotExist(appId, "Google application", null)));

        OrganizationOidcConfiguration portalGoogle = google(new OrganizationOidcConfiguration(), "Acme Google");
        portalGoogle.setCreateUserOnSignIn(true);
        assertInstanceOf(IllegalArgumentException.class, failure(() -> ownedConfigurations.save(portalGoogle, null)));
        portalGoogle.setCreateUserOnSignIn(false);
        assertNotNull(await(runAsOrganization(() -> ownedConfigurations.save(portalGoogle, null))).getId());

        OrganizationOidcConfiguration portalOkta = okta(new OrganizationOidcConfiguration(), "Acme Okta");
        portalOkta.setCreateUserOnSignIn(true);
        assertTrue(await(runAsOrganization(() -> ownedConfigurations.save(portalOkta, null))).isCreateUserOnSignIn());

        // an application's login takes it for any kind: sign-up with Google for the application
        ApplicationOidcConfiguration appGoogle = google(new ApplicationOidcConfiguration(), "Orders Google");
        appGoogle.setApplicationId(appId);
        appGoogle.setCreateUserOnSignIn(true);
        assertTrue(await(runAsOrganization(() -> ownedConfigurations.save(appGoogle, null))).isCreateUserOnSignIn());
    }

    private static <C extends OwnedOidcConfiguration> C okta(C configuration, String name) {
        configuration.setOrganizationId(TEST_ORG_ID)
                     .setProviderAccount("acme.okta.com");
        configuration.setName(name)
                     .setProvider(OidcProviderKind.OKTA)
                     .setClientId("acme-client")
                     .setEnabled(true);
        return configuration;
    }

    private static <C extends OwnedOidcConfiguration> C google(C configuration, String name) {
        configuration.setOrganizationId(TEST_ORG_ID);
        configuration.setName(name)
                     .setProvider(OidcProviderKind.GOOGLE)
                     .setClientId("acme-client")
                     .setEnabled(true);
        return configuration;
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
