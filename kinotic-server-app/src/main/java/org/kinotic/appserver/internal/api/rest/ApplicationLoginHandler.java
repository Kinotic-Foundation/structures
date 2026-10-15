package org.kinotic.appserver.internal.api.rest;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.appserver.api.config.AppServerProperties;
import org.kinotic.core.api.security.SessionBinding;
import org.kinotic.domain.api.model.ApplicationKey;
import org.kinotic.domain.api.utils.HostLabelUtil;
import org.kinotic.domain.api.rest.support.CallbackResult;
import org.kinotic.domain.api.rest.support.OidcCallbackException;
import org.kinotic.domain.api.rest.support.OidcErrorCodes;
import org.kinotic.domain.api.rest.support.OidcFlowOrchestrator;
import org.kinotic.domain.api.model.TenantManagement;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.model.security.ApplicationSocialOidcConfiguration;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.repositories.ApplicationRepository;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.services.security.LocalAuthenticationService;
import org.kinotic.domain.api.rest.SuppliesGatewayRoutes;
import org.springframework.stereotype.Component;

/**
 * Login routes for end-users of an application built on Kinotic, served at the application's own API host.
 * Distinct from the org-login handler: the user is logging into an application's own user base (an
 * APP-scope {@link UserParticipantIdentity} — {@code organizationId} + {@code applicationId} both set),
 * not into the platform-managed org admin surface. The application is the one whose API host the request
 * was addressed to (see {@link ApplicationAuthEndpointSupport#applicationKey}), a login is made only from a page that is one of
 * that application's UIs, and a page offers the methods the login supports — email and password, and the
 * configurations {@code GET /api/auth/app/login/providers} lists — without asking about an email first, so no
 * endpoint tells whether an email has an account. An OIDC flow started here returns to this handler's own
 * callback at the application's API host; a sign-in through one of the platform's social registrations, whose
 * callback every application shares, runs through {@link ApplicationSocialLoginHandler}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationLoginHandler implements SuppliesGatewayRoutes {

    private final ParticipantIdentityService identityService;
    private final OidcConfigurationService oidcConfigurationService;
    private final ApplicationRepository applicationRepository;
    private final LocalAuthenticationService localAuthenticationService;
    private final OidcFlowOrchestrator oidcFlowOrchestrator;
    private final ApplicationAuthEndpointSupport authEndpointSupport;
    private final AppServerProperties properties;

    @Override
    public void mountRoutes(Router router) {
        router.get("/api/auth/app/login/providers").handler(this::handleProviders);
        router.post("/api/auth/app/login/oidc/start/:configId").handler(this::handleStart);
        router.post("/api/auth/app/login").handler(this::handleLogin);
        router.get("/api/auth/app/login/oidc/callback/:configId").handler(this::handleCallback);
    }

    /**
     * {@code GET /api/auth/app/login/providers} — lists the enabled configurations the application's
     * login offers.
     */
    private void handleProviders(RoutingContext ctx) {
        ApplicationKey applicationKey = authEndpointSupport.applicationKey(ctx);
        oidcConfigurationService.findEnabledForScope(applicationKey.organizationId(), applicationKey.applicationId())
              .onSuccess(configs -> authEndpointSupport.respondProvidersList(ctx, configs))
              .onFailure(err -> {
                  log.warn("Failed to list app providers for {}: {}", HostLabelUtil.label(applicationKey), err.getMessage());
                  authEndpointSupport.respondError(ctx, 500, "Failed to list providers");
              });
    }

    /**
     * {@code POST /api/auth/app/login/oidc/start/:configId} — begins a sign-in through a configuration the
     * application's login offers, answering {@code {redirect}} with the URL the page sends the browser to:
     * the provider's authorization URL for a configuration of the organization, whose flow returns to this
     * handler's callback, or the shared host's authorize URL for one of the platform's social registrations
     * (see {@link ApplicationSocialLoginHandler}). {@code 400} for a configuration the login does not offer;
     * {@code 403} from a page that is not one of the application's UIs.
     */
    private void handleStart(RoutingContext ctx) {
        ApplicationKey applicationKey = authEndpointSupport.requireApplicationUi(ctx);
        String configId = ctx.pathParam("configId");
        String origin = SessionBinding.origin(ctx);
        if (origin == null) {
            authEndpointSupport.respondError(ctx, 400, "A sign-in is started by a page");
            return;
        }

        oidcConfigurationService.findOffered(configId, applicationKey.organizationId(), applicationKey.applicationId())
              .compose(config -> {
                  Future<String> ret;
                  switch (config) {
                      case null -> ret = Future.failedFuture(new OidcCallbackException(OidcErrorCodes.CONFIG_NOT_FOUND));
                      // the registration is shared, so its provider returns to the shared host, not to this one
                      case ApplicationSocialOidcConfiguration _ ->
                              ret = Future.succeededFuture(authEndpointSupport.socialAuthorizeUrl(applicationKey, configId, origin));
                      default -> ret = oidcFlowOrchestrator.startFlow(ctx, config, callbackUrl(applicationKey, configId), null);
                  }
                  return ret;
              })
              .onSuccess(url -> ctx.response().putHeader("Content-Type", "application/json")
                                   .end(new JsonObject().put("redirect", url).encode()))
              .onFailure(err -> {
                  if (err instanceof OidcCallbackException) {
                      authEndpointSupport.respondError(ctx, 400, "The application's login does not offer " + configId);
                  } else {
                      log.warn("App login start failed for {}/{}: {}", HostLabelUtil.label(applicationKey), configId, err.getMessage());
                      authEndpointSupport.respondError(ctx, 500, "Failed to start sign-in");
                  }
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
     * An identity no user is bound to becomes a user of the application when the configuration
     * creates users on sign-in and the platform manages the application's tenants, which gives
     * the new user a tenant; under tenant administrators a user is created by an administrator or
     * an invitation, since a user without a tenant is not allowed there.
     */
    private void handleCallback(RoutingContext ctx) {
        ApplicationKey applicationKey = authEndpointSupport.applicationKey(ctx);
        String pathConfigId = ctx.pathParam("configId");

        oidcFlowOrchestrator.<OidcConfiguration>handleCallback(
                ctx, pathConfigId, callbackUrl(applicationKey, pathConfigId),
                (_, _) -> oidcConfigurationService.findOffered(pathConfigId, applicationKey.organizationId(), applicationKey.applicationId()))
                .onSuccess(result -> completeAppLogin(ctx, result, applicationKey))
                .onFailure(ex -> authEndpointSupport.redirectCallbackFailure(ctx, ex));
    }

    private void completeAppLogin(RoutingContext ctx,
                                  CallbackResult<OidcConfiguration> result,
                                  ApplicationKey applicationKey) {
        authEndpointSupport.completeOidcLogin(ctx, result,
                sub -> identityService.findByOidcIdentity(sub, result.config().getId(),
                                                         applicationKey.organizationId(), applicationKey.applicationId()),
                () -> newUserOf(applicationKey));
    }

    // the unsaved user an unknown identity becomes, or null while the application's tenants are its administrators'
    // to assign, since a user there is never without a tenant
    private Future<UserParticipantIdentity> newUserOf(ApplicationKey applicationKey) {
        return applicationRepository.findById(applicationKey.applicationId(), applicationKey.organizationId())
                                    .map(application -> {
                                        UserParticipantIdentity ret = null;
                                        if (application != null && application.getTenantManagement() == TenantManagement.PLATFORM) {
                                            ret = new UserParticipantIdentity();
                                            ret.setOrganizationId(applicationKey.organizationId())
                                               .setApplicationId(applicationKey.applicationId());
                                        }
                                        return ret;
                                    });
    }

    // the application's API host is the callback's base, so the IdP returns the browser to the host it left
    private String callbackUrl(ApplicationKey applicationKey, String configId) {
        return HostLabelUtil.apiUrl(applicationKey, properties.getApiBaseUrl()) + "/api/auth/app/login/oidc/callback/" + configId;
    }
}
