package org.kinotic.appserver.internal.api.rest;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.appserver.api.config.AppServerProperties;
import org.kinotic.domain.api.model.ApplicationKey;
import org.kinotic.domain.api.utils.HostLabelUtil;
import org.kinotic.domain.api.rest.support.CallbackResult;
import org.kinotic.domain.api.rest.support.OidcFlowOrchestrator;
import org.kinotic.domain.api.model.security.AuthType;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.services.security.LocalAuthenticationService;
import org.kinotic.domain.api.services.security.LoginProviderService;
import org.kinotic.domain.api.rest.SuppliesGatewayRoutes;
import org.springframework.stereotype.Component;

/**
 * Login routes for end-users of an application built on Kinotic, served at the application's own API host.
 * Distinct from the org-login handler: the user is logging into an application's own user base (an
 * APP-scope {@link UserParticipantIdentity} — {@code organizationId} + {@code applicationId} both set),
 * not into the platform-managed org admin surface. The application is the one whose API host the request
 * was addressed to (see {@link ApplicationAuthEndpointSupport#applicationKey}), a login is made only from a page that is one of
 * that application's UIs, and OIDC flows started here return to this handler's own callback at the application's
 * API host. A sign-in through one of the platform's social registrations, whose callback every application
 * shares, is {@link ApplicationSocialLoginHandler}'s.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationLoginHandler implements SuppliesGatewayRoutes {

    private final ParticipantIdentityService identityService;
    private final LoginProviderService loginProviderService;
    private final LocalAuthenticationService localAuthenticationService;
    private final OidcFlowOrchestrator oidcFlowOrchestrator;
    private final ApplicationAuthEndpointSupport authEndpointSupport;
    private final AppServerProperties properties;

    @Override
    public void mountRoutes(Router router) {
        router.get("/api/auth/app/login/providers").handler(this::handleProviders);
        router.post("/api/auth/app/login/lookup").handler(this::handleLookup);
        router.post("/api/auth/app/login").handler(this::handleLogin);
        router.get("/api/auth/app/login/oidc/callback/:configId").handler(this::handleCallback);
    }

    /**
     * {@code GET /api/auth/app/login/providers} — lists the enabled configurations the application's
     * login offers.
     */
    private void handleProviders(RoutingContext ctx) {
        ApplicationKey applicationKey = authEndpointSupport.applicationKey(ctx);
        loginProviderService.findEnabledForScope(applicationKey.organizationId(), applicationKey.applicationId())
              .onSuccess(configs -> authEndpointSupport.respondProvidersList(ctx, configs))
              .onFailure(err -> {
                  log.warn("Failed to list app providers for {}: {}", HostLabelUtil.label(applicationKey), err.getMessage());
                  authEndpointSupport.respondError(ctx, 500, "Failed to list providers");
              });
    }

    /**
     * {@code POST /api/auth/app/login/lookup {email}} — email-first SSO/password
     * decision: {@code {type:"sso", redirect:"…"}} when the user is OIDC with a live
     * config, otherwise {@code {type:"password"}}. Refused with {@code 403} from a page that is
     * not one of the application's UIs.
     */
    private void handleLookup(RoutingContext ctx) {
        ApplicationKey applicationKey = authEndpointSupport.requireApplicationUi(ctx);
        JsonObject body = authEndpointSupport.readJsonBody(ctx);
        String email = body.getString("email");
        if (email == null || email.isBlank()) {
            authEndpointSupport.respondError(ctx, 400, "email is required");
            return;
        }

        identityService.findByEmail(email, applicationKey.organizationId(), applicationKey.applicationId())
              .compose(user -> resolveSsoOrPassword(ctx, applicationKey, user))
              .onFailure(err -> {
                  log.warn("App login lookup failed for {}/{}: {}", HostLabelUtil.label(applicationKey), email, err.getMessage());
                  authEndpointSupport.respondError(ctx, 500, "Lookup failed");
              });
    }

    private Future<Void> resolveSsoOrPassword(RoutingContext ctx, ApplicationKey applicationKey, UserParticipantIdentity user) {
        if (user == null
                || user.getAuthType() != AuthType.OIDC
                || user.getOidcConfigId() == null) {
            return authEndpointSupport.respondPasswordPath(ctx);
        }

        String configId = user.getOidcConfigId();
        return loginProviderService.findOffered(configId, applicationKey.organizationId(), applicationKey.applicationId())
                     .compose(match -> {
                         if (match == null) {
                             return authEndpointSupport.respondPasswordPath(ctx);
                         }
                         return oidcFlowOrchestrator.startFlow(ctx, match, callbackUrl(applicationKey, match.getId()), null)
                                 .compose(url -> authEndpointSupport.respondSsoRedirect(ctx, url));
                     });
    }

    /**
     * {@code POST /api/auth/app/login {email, password}} — local password auth,
     * scoped to the application so a stray cross-scope match can't authenticate here. Refused
     * with {@code 403} from a page that is not one of the application's UIs.
     */
    private void handleLogin(RoutingContext ctx) {
        ApplicationKey applicationKey = authEndpointSupport.requireApplicationUi(ctx);
        authEndpointSupport.handlePasswordLogin(ctx,
                (email, password) -> localAuthenticationService.authenticateLocal(
                        email, password, applicationKey.organizationId(), applicationKey.applicationId()));
    }

    /**
     * {@code GET /api/auth/app/login/oidc/callback/:configId} — the IdP returns here;
     * validates the callback and logs the user into the application. The pre-auth flow has
     * no participant bound, so the config lookup is scoped by the application's organization.
     */
    private void handleCallback(RoutingContext ctx) {
        ApplicationKey applicationKey = authEndpointSupport.applicationKey(ctx);
        String pathConfigId = ctx.pathParam("configId");

        oidcFlowOrchestrator.<OidcConfiguration>handleCallback(
                ctx, pathConfigId, callbackUrl(applicationKey, pathConfigId),
                (_, _) -> loginProviderService.findOffered(pathConfigId, applicationKey.organizationId(), applicationKey.applicationId()))
                .onSuccess(result -> completeAppLogin(ctx, result, applicationKey))
                .onFailure(ex -> authEndpointSupport.redirectCallbackFailure(ctx, ex));
    }

    private void completeAppLogin(RoutingContext ctx,
                                  CallbackResult<OidcConfiguration> result,
                                  ApplicationKey applicationKey) {
        authEndpointSupport.completeOidcLogin(ctx, result,
                sub -> identityService.findByOidcIdentity(sub, result.config().getId(),
                                                         applicationKey.organizationId(), applicationKey.applicationId()));
    }

    // the application's API host is the callback's base, so the IdP returns the browser to the host it left
    private String callbackUrl(ApplicationKey applicationKey, String configId) {
        return HostLabelUtil.apiUrl(applicationKey, properties.getApiBaseUrl()) + "/api/auth/app/login/oidc/callback/" + configId;
    }
}
