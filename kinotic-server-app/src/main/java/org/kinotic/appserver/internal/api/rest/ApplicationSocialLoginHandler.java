package org.kinotic.appserver.internal.api.rest;

import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.appserver.api.config.AppServerProperties;
import org.kinotic.appserver.internal.api.services.security.SocialLoginHandoff;
import org.kinotic.appserver.internal.api.services.security.SocialLoginHandoffService;
import org.kinotic.domain.api.model.ApplicationKey;
import org.kinotic.domain.api.model.security.ApplicationSocialOidcConfiguration;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.rest.SuppliesGatewayRoutes;
import org.kinotic.domain.api.rest.support.CallbackResult;
import org.kinotic.domain.api.rest.support.OidcCallbackException;
import org.kinotic.domain.api.rest.support.OidcErrorCodes;
import org.kinotic.domain.api.rest.support.OidcFlowOrchestrator;
import org.kinotic.domain.api.services.security.LoginProviderService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.HostLabelUtil;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Sign-in to an application through one of the platform's social registrations
 * ({@link ApplicationSocialOidcConfiguration}). The registration is shared by every application that offers
 * it, and a provider returns the browser only to a redirect URI registered in advance, so the provider
 * returns to one host for all of them: the app server's own, {@code kinotic.appServer.apiBaseUrl}. The flow
 * therefore runs across two hosts. The application's API host starts it and answers with the authorize URL
 * on the shared host, which runs the provider round-trip on its own session; the shared host's callback
 * verifies the identity and hands it to the application's API host under a single-use code, where the
 * session is established, so the browser ends signed in at the host the application's UI connects to.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationSocialLoginHandler implements SuppliesGatewayRoutes {

    private static final String AUTHORIZE_PATH = "/api/auth/app/login/social/authorize";
    private static final String CALLBACK_PATH = "/api/auth/app/login/social/callback/";
    private static final String COMPLETE_PATH = "/api/auth/app/login/social/complete/";

    private final ParticipantIdentityService identityService;
    private final LoginProviderService loginProviderService;
    private final OidcFlowOrchestrator oidcFlowOrchestrator;
    private final SocialLoginHandoffService handoffService;
    private final ApplicationAuthEndpointSupport authEndpointSupport;
    private final AppServerProperties properties;

    @Override
    public void mountRoutes(Router router) {
        router.post("/api/auth/app/login/social/start/:configId").handler(this::handleStart);
        router.get(AUTHORIZE_PATH).handler(this::handleAuthorize);
        router.get(CALLBACK_PATH + ":configId").handler(this::handleCallback);
        router.get(COMPLETE_PATH + ":code").handler(this::handleComplete);
    }

    /**
     * {@code POST /api/auth/app/login/social/start/:configId}, at the application's API host — answers
     * {@code {type:"sso", redirect}} with the URL the page sends the browser to, which begins the sign-in on
     * the shared host for this application and the page that asked. {@code 400} for a configuration the
     * application does not offer or that is not one of the platform's social registrations; {@code 403} from
     * a page that is not one of the application's UIs.
     */
    private void handleStart(RoutingContext ctx) {
        ApplicationKey applicationKey = authEndpointSupport.requireApplicationUi(ctx);
        String configId = ctx.pathParam("configId");
        String origin = ctx.request().getHeader("Origin");
        if (origin == null) {
            authEndpointSupport.respondError(ctx, 400, "A social sign-in is started by a page");
            return;
        }

        loginProviderService.findOffered(configId, applicationKey.organizationId(), applicationKey.applicationId())
              .compose(config -> {
                  Future<Void> ret;
                  if (config instanceof ApplicationSocialOidcConfiguration) {
                      ret = authEndpointSupport.respondSsoRedirect(ctx, authorizeUrl(applicationKey, configId, origin));
                  } else {
                      authEndpointSupport.respondError(ctx, 400, "The application offers no social sign-in " + configId);
                      ret = Future.succeededFuture();
                  }
                  return ret;
              })
              .onFailure(err -> {
                  log.warn("Social login start failed for {}/{}: {}", HostLabelUtil.label(applicationKey), configId, err.getMessage());
                  authEndpointSupport.respondError(ctx, 500, "Failed to start sign-in");
              });
    }

    /**
     * {@code GET /api/auth/app/login/social/authorize?organizationId&applicationId&configurationId&origin}, at
     * the shared host — begins the provider round-trip for a sign-in to the application from the page at
     * {@code origin}, redirecting the browser to the provider. {@code 400} for a configuration the application
     * does not offer, or a page that is not one of its UIs.
     */
    private void handleAuthorize(RoutingContext ctx) {
        ApplicationKey applicationKey = new ApplicationKey(ctx.request().getParam("organizationId"),
                                                           ctx.request().getParam("applicationId"));
        String configId = ctx.request().getParam("configurationId");
        String origin = ctx.request().getParam("origin");
        // the completed sign-in is bound to this page, so only a page of the application may be named
        if (origin == null || !authEndpointSupport.isApplicationUi(applicationKey, origin)) {
            authEndpointSupport.respondError(ctx, 400, "The page is not a UI of the application");
            return;
        }

        loginProviderService.findOffered(configId, applicationKey.organizationId(), applicationKey.applicationId())
              .compose(config -> {
                  Future<String> ret;
                  if (config instanceof ApplicationSocialOidcConfiguration) {
                      ret = oidcFlowOrchestrator.startFlow(ctx, config, callbackUrl(configId), applicationKey, origin);
                  } else {
                      ret = Future.failedFuture(new OidcCallbackException(OidcErrorCodes.CONFIG_NOT_FOUND));
                  }
                  return ret;
              })
              .onSuccess(url -> ctx.response().setStatusCode(302).putHeader("Location", url).end())
              .onFailure(err -> {
                  log.warn("Social login authorize failed for {}/{}: {}", HostLabelUtil.label(applicationKey), configId, err.getMessage());
                  // the flow has not started, so the page that asked is still known to send the browser back to
                  authEndpointSupport.redirectError(ctx, origin, err instanceof OidcCallbackException oce
                          ? oce.getErrorCode() : OidcErrorCodes.EXCHANGE_FAILED);
              });
    }

    /**
     * {@code GET /api/auth/app/login/social/callback/:configId}, at the shared host — the provider returns
     * here; validates the callback against the flow session and, for an identity that may sign in, sends
     * the browser on to the application's API host with the single-use code its sign-in is staged under.
     * A callback no flow session answers for is refused with {@code 400}: the host serves every application,
     * so without the session there is no page to send the browser back to.
     */
    private void handleCallback(RoutingContext ctx) {
        String pathConfigId = ctx.pathParam("configId");

        oidcFlowOrchestrator.<OidcConfiguration>handleCallback(
                ctx, pathConfigId, callbackUrl(pathConfigId),
                (orgId, applicationId) -> loginProviderService.findOffered(pathConfigId, orgId, applicationId))
                .onSuccess(result -> handOff(ctx, result))
                .onFailure(ex -> {
                    String code = ex instanceof OidcCallbackException oce ? oce.getErrorCode() : OidcErrorCodes.EXCHANGE_FAILED;
                    log.warn("Social login callback failed for config {}: {}", pathConfigId, ex.getMessage());
                    authEndpointSupport.respondError(ctx, 400, "Sign-in failed: " + code);
                });
    }

    private void handOff(RoutingContext ctx, CallbackResult<OidcConfiguration> result) {
        String sub = authEndpointSupport.verifiedSubject(ctx, result);
        if (sub != null) {
            ApplicationKey applicationKey = new ApplicationKey(result.orgId(), result.applicationId());
            String code = handoffService.stage(new SocialLoginHandoff()
                                                       .setOrganizationId(applicationKey.organizationId())
                                                       .setApplicationId(applicationKey.applicationId())
                                                       .setConfigurationId(result.config().getId())
                                                       .setSubject(sub)
                                                       .setOrigin(result.origin()));
            ctx.response().setStatusCode(302)
               .putHeader("Location", HostLabelUtil.apiUrl(applicationKey, properties.getApiBaseUrl()) + COMPLETE_PATH + code)
               .end();
        }
    }

    /**
     * {@code GET /api/auth/app/login/social/complete/:code}, at the application's API host — consumes the
     * staged sign-in and logs its identity into the application, bound to the page that started it, as
     * {@link ApplicationLoginHandler}'s own callback does. A code that is unknown, spent, or staged for
     * another application sends the browser to the application's login page with {@code handoff_invalid}.
     */
    private void handleComplete(RoutingContext ctx) {
        ApplicationKey applicationKey = authEndpointSupport.applicationKey(ctx);
        SocialLoginHandoff handoff = handoffService.consume(ctx.pathParam("code"));
        // the code is bound to the host it was issued for, so a code carried to another application's host
        // cannot sign its identity in there
        if (handoff == null
                || !applicationKey.equals(new ApplicationKey(handoff.getOrganizationId(), handoff.getApplicationId()))) {
            authEndpointSupport.redirectError(ctx, null, OidcErrorCodes.HANDOFF_INVALID);
        } else {
            authEndpointSupport.completeLogin(ctx, handoff.getOrigin(),
                    identityService.findByOidcIdentity(handoff.getSubject(), handoff.getConfigurationId(),
                                                       applicationKey.organizationId(), applicationKey.applicationId()));
        }
    }

    private String authorizeUrl(ApplicationKey applicationKey, String configId, String origin) {
        return properties.getApiBaseUrl() + AUTHORIZE_PATH
                + "?organizationId=" + encode(applicationKey.organizationId())
                + "&applicationId=" + encode(applicationKey.applicationId())
                + "&configurationId=" + encode(configId)
                + "&origin=" + encode(origin);
    }

    // the shared host is the callback's base: the one redirect URI the registration lists for every application
    private String callbackUrl(String configId) {
        return properties.getApiBaseUrl() + CALLBACK_PATH + configId;
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
