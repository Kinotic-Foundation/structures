package org.kinotic.test.tests.core.security;

import io.vertx.core.Future;
import org.junit.jupiter.api.Test;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.OidcProviderKind;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies where an organization's OIDC configuration keeps its client secret: given at save, it is read
 * back for that configuration alone, under its organization, and never appears on the configuration a
 * read returns; a save without a secret keeps it, a new secret replaces it, null makes the configuration a
 * public client, a public client can be given a secret again, and deleting the configuration deletes it.
 * A provider whose host does not resolve, or that the server could only reach over plain http or at a
 * loopback, link-local or private address, is refused.
 */
@SpringBootTest
public class OidcConfigurationSecretTests extends KinoticTestBase {

    @Autowired
    private OidcConfigurationService oidcConfigurations;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void aConfigurationsSecretIsHeldForItsOrganizationAndNeverOnTheRow() throws Exception {
        OrganizationOidcConfiguration saved = await(runAsOrganization(() -> oidcConfigurations.save(provider("Acme Okta"), "acme-secret-1")));
        assertTrue(saved.isConfidentialClient());
        assertEquals("acme-secret-1", await(oidcConfigurations.findClientSecret(saved)));

        // what a client reads back is the row as persisted, which says a secret is held and names nothing about it
        OrganizationOidcConfiguration read = await(oidcConfigurations.findById(saved.getId(), TEST_ORG_ID));
        assertTrue(read.isConfidentialClient());
        String json = objectMapper.writeValueAsString(read);
        assertFalse(json.toLowerCase(Locale.ROOT).contains("secret"), json);

        // another organization's configuration of the same id reads nothing of it
        OrganizationOidcConfiguration other = new OrganizationOidcConfiguration();
        other.setOrganizationId("globex");
        other.setId(saved.getId());
        assertNull(await(oidcConfigurations.findClientSecret(other)));

        // a save of the row alone keeps the secret; a save with one replaces it
        await(runAsOrganization(() -> oidcConfigurations.save(read.setName("Acme Okta renamed"))));
        assertEquals("acme-secret-1", await(oidcConfigurations.findClientSecret(saved)));
        await(runAsOrganization(() -> oidcConfigurations.save(read, "acme-secret-2")));
        assertEquals("acme-secret-2", await(oidcConfigurations.findClientSecret(saved)));

        // null makes a public client, which can be given a secret again
        OrganizationOidcConfiguration publicClient = await(runAsOrganization(() -> oidcConfigurations.save(read, null)));
        assertFalse(publicClient.isConfidentialClient());
        assertNull(await(oidcConfigurations.findClientSecret(publicClient)));
        OrganizationOidcConfiguration confidentialAgain = await(runAsOrganization(() -> oidcConfigurations.save(read, "acme-secret-3")));
        assertTrue(confidentialAgain.isConfidentialClient());
        assertEquals("acme-secret-3", await(oidcConfigurations.findClientSecret(confidentialAgain)));

        // deleting the configuration deletes its secret
        await(runAsOrganization(() -> oidcConfigurations.deleteById(saved.getId())));
        assertNull(await(oidcConfigurations.findById(saved.getId(), TEST_ORG_ID)));
        assertNull(await(oidcConfigurations.findClientSecret(confidentialAgain)));
    }

    @Test
    public void aProviderTheServerCouldOnlyReachInsecurelyOrPrivatelyIsRefused() {
        for (String authority : List.of("http://acme.okta.test", "https://acme.okta.invalid", "https://127.0.0.1", "https://localhost:8443",
                                        "https://10.0.0.5", "https://192.168.1.10", "https://169.254.169.254",
                                        "https://[::1]", "https://[fd00::1]", "https://user:pass@acme.okta.test")) {
            assertInstanceOf(IllegalArgumentException.class,
                             failure(() -> oidcConfigurations.save(provider("Acme Okta", config -> config.setAuthority(authority)), null)),
                             authority);
        }
        // every endpoint the configuration names is held to the same rule as its authority
        assertInstanceOf(IllegalArgumentException.class,
                         failure(() -> oidcConfigurations.save(provider("Acme Okta", config -> config.setTokenUri("http://acme.okta.test/token")))));
        assertInstanceOf(IllegalArgumentException.class,
                         failure(() -> oidcConfigurations.save(provider("Acme Okta", config -> config.setAuthorizationUri("https://127.0.0.1/authorize")))));
        assertInstanceOf(IllegalArgumentException.class,
                         failure(() -> oidcConfigurations.save(provider("Acme Okta", config -> config.setUserInfoUri("https://10.0.0.5/userinfo")))));
        assertInstanceOf(IllegalArgumentException.class,
                         failure(() -> oidcConfigurations.save(provider("Acme Okta", config -> config.setUserEmailsUri("https://169.254.169.254/emails")))));
    }

    private Throwable failure(Supplier<Future<?>> call) {
        return assertThrows(ExecutionException.class, () -> await(runAsOrganization(call::get))).getCause();
    }

    private static OrganizationOidcConfiguration provider(String name, Consumer<OrganizationOidcConfiguration> change) {
        OrganizationOidcConfiguration ret = provider(name);
        change.accept(ret);
        return ret;
    }

    private static OrganizationOidcConfiguration provider(String name) {
        OrganizationOidcConfiguration ret = new OrganizationOidcConfiguration();
        ret.setOrganizationId(TEST_ORG_ID);
        ret.setName(name)
           .setProvider(OidcProviderKind.OIDC)
           .setClientId("acme-client")
           // an address literal from the documentation range, so the save resolves it without DNS
           .setAuthority("https://203.0.113.10")
           .setEnabled(true);
        return ret;
    }

    private static <T> T await(Future<T> future) throws Exception {
        return future.toCompletionStage().toCompletableFuture().get(30, TimeUnit.SECONDS);
    }
}
