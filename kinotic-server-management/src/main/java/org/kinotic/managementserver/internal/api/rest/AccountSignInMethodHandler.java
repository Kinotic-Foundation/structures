package org.kinotic.managementserver.internal.api.rest;

import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.model.security.OidcProviderKind;
import org.kinotic.domain.api.model.security.OrgSignupOidcConfiguration;
import org.kinotic.domain.api.rest.SuppliesGatewayRoutes;
import org.kinotic.domain.api.rest.support.AuthEndpointSupport;
import org.kinotic.domain.api.rest.support.CallbackResult;
import org.kinotic.domain.api.rest.support.OidcFlowOrchestrator;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.managementserver.api.config.ManagementServerProperties;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Connects a provider identity to the signed-in account as a further sign-in method. The person is already
 * signed in, which is what proves the account is theirs: the provider round-trip then asserts the identity,
 * and the callback binds it to the session's account. Nothing here signs anyone in; a sign-in through the
 * new binding runs through {@link OrganizationLoginHandler} like any other.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountSignInMethodHandler implements SuppliesGatewayRoutes {

    private static final String PROFILE_PATH = "/account/profile";
    private static final String CALLBACK_PATH = "/api/auth/account/methods/callback/";

    /** The identity is bound to another account already. */
    private static final String IDENTITY_IN_USE = "identity_in_use";

    private final AuthEndpointSupport authEndpointSupport;
    private final OidcConfigurationService oidcConfigurationService;
    private final OidcFlowOrchestrator oidcFlowOrchestrator;
    private final ParticipantIdentityService identityService;
    private final ManagementServerProperties properties;

    @Override
    public void mountRoutes(Router router) {
        router.post("/api/auth/account/methods/start/:provider").handler(this::handleStart);
        router.get(CALLBACK_PATH + ":configId").handler(this::handleCallback);
    }

    /**
     * {@code POST /api/auth/account/methods/start/:provider} — begins the round-trip to one of the platform's
     * registrations for the signed-in account, redirecting the browser to the provider; {@code 400} for an
     * unknown or disabled provider, {@code 401} without a signed-in user.
     */
    private void handleStart(RoutingContext ctx) {
        authEndpointSupport.requireSessionUser(ctx);
        String provider = ctx.pathParam("provider");
        OidcProviderKind providerKind;
        try {
            providerKind = OidcProviderKind.fromKey(provider);
        } catch (IllegalArgumentException ex) {
            authEndpointSupport.respondError(ctx, 400, "Unknown platform provider: " + provider);
            return;
        }
        oidcConfigurationService.findEnabledOrgSignupByProvider(providerKind)
              .compose(config -> config == null
                      ? Future.<String>failedFuture(new IllegalArgumentException("Unknown or disabled platform provider: " + provider))
                      : oidcFlowOrchestrator.startFlow(ctx, config, callbackUrl(config.getId()), null))
              .onSuccess(url -> ctx.response().setStatusCode(302).putHeader("Location", url).end())
              .onFailure(err -> {
                  if (err instanceof IllegalArgumentException) {
                      authEndpointSupport.respondError(ctx, 400, err.getMessage());
                  } else {
                      log.error("Connecting provider {} failed to start", provider, err);
                      authEndpointSupport.respondError(ctx, 500, "Provider initialization failed");
                  }
              });
    }

    /**
     * {@code GET /api/auth/account/methods/callback/:configId} — the provider returns here; validates the
     * callback and binds the asserted identity to the signed-in account, then sends the browser back to the
     * profile page with {@code connected=<provider>}, or with {@code error=identity_in_use} when the identity
     * already signs in another account.
     */
    private void handleCallback(RoutingContext ctx) {
        String pathConfigId = ctx.pathParam("configId");
        Participant account = authEndpointSupport.requireSessionUser(ctx);

        oidcFlowOrchestrator.handleCallback(ctx, pathConfigId, callbackUrl(pathConfigId),
                                            (_, _, _) -> oidcConfigurationService.findOrgSignupById(pathConfigId))
                            .onSuccess(result -> connect(ctx, account, result))
                            .onFailure(ex -> {
                                log.warn("Connecting a sign-in method failed for config {}: {}", pathConfigId, ex.getMessage());
                                authEndpointSupport.redirectToUi(ctx, PROFILE_PATH + "?error=exchange_failed");
                            });
    }

    private void connect(RoutingContext ctx, Participant account, CallbackResult<OrgSignupOidcConfiguration> result) {
        String sub = authEndpointSupport.verifiedSubject(ctx, result);
        if (sub != null) {
            identityService.bindOidcIdentity(account.getId(), result.config().getId(), sub)
                           .onSuccess(binding -> authEndpointSupport.redirectToUi(ctx, PROFILE_PATH + "?connected="
                                   + URLEncoder.encode(result.config().getProvider().key(), StandardCharsets.UTF_8)))
                           .onFailure(err -> {
                               String code = err instanceof IllegalArgumentException ? IDENTITY_IN_USE : "exchange_failed";
                               log.warn("Could not connect a sign-in method to account {}: {}", account.getId(), err.getMessage());
                               authEndpointSupport.redirectToUi(ctx, PROFILE_PATH + "?error=" + code);
                           });
        }
    }

    private String callbackUrl(String configId) {
        return properties.getApiBaseUrl() + CALLBACK_PATH + configId;
    }
}
