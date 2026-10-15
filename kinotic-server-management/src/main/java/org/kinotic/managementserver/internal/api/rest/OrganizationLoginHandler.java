package org.kinotic.managementserver.internal.api.rest;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.core.api.security.SessionBinding;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.model.security.OrgSignupOidcConfiguration;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.rest.SuppliesGatewayRoutes;
import org.kinotic.domain.api.rest.support.AuthEndpointSupport;
import org.kinotic.domain.api.rest.support.CallbackResult;
import org.kinotic.domain.api.rest.support.OidcErrorCodes;
import org.kinotic.domain.api.rest.support.OidcFlowOrchestrator;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.security.LocalAuthenticationService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.managementserver.api.config.ManagementServerProperties;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Login routes for an organization's members. A login is addressed to the organization by its id, the
 * slug of the portal's {@code /login/<orgId>} page, since an email or a social identity may be a member
 * of any number of organizations: the page offers the methods that organization's login supports,
 * email and password and the configurations {@code GET /api/auth/org/login/:orgId/providers} lists,
 * without asking about an email first, so no route tells whether an email has an account. The portal's
 * landing page, which knows no organization yet, offers the platform's social registrations: a
 * sign-in there finds the one organization the identity is a member of, and emails a person who does
 * not remember their organizations the list. On success each login establishes the browser session;
 * the STOMP WebSocket handshake then authenticates from that session cookie, so the browser never
 * handles a token. OIDC flows started here return to this handler's own callbacks.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrganizationLoginHandler implements SuppliesGatewayRoutes {

    private final AuthEndpointSupport authEndpointSupport;
    private final ManagementServerProperties properties;
    private final ParticipantIdentityService identityService;
    private final OrganizationService organizationService;
    private final LocalAuthenticationService localAuthenticationService;
    private final OidcConfigurationService oidcConfigurationService;
    private final OidcFlowOrchestrator oidcFlowOrchestrator;

    @Override
    public void mountRoutes(Router router) {
        // the landing page, addressed to no organization
        router.get("/api/auth/org/login/providers").handler(this::handleProviders);
        router.post("/api/auth/org/login/find").handler(this::handleFind);
        router.post("/api/auth/org/login/social/start/:provider").handler(this::handleSocialStart);
        router.get("/api/auth/org/login/social/callback/:configId").handler(this::handleSocialCallback);
        router.get("/api/auth/org/login/sso/callback/:configId").handler(this::handleSsoCallback);
        // one organization's login
        router.get("/api/auth/org/login/:orgId/providers").handler(this::handleOrganizationProviders);
        router.post("/api/auth/org/login/:orgId").handler(this::handleLogin);
        router.post("/api/auth/org/login/:orgId/start/:configId").handler(this::handleStart);
        // a link, as from a provider's own tile of applications, starts the same flow
        router.get("/api/auth/org/login/:orgId/start/:configId").handler(this::handleStart);
    }

    /**
     * {@code GET /api/auth/org/login/providers} — the unique provider keys (e.g. {@code "github"}) of the
     * platform's org sign-up registrations, for the landing page's social-button row and the invite
     * dialog: one key per provider kind, even when several configs share a kind.
     */
    private void handleProviders(RoutingContext ctx) {
        oidcConfigurationService.findEnabledOrgSignupProviders()
              .onSuccess(configs -> {
                  JsonArray providers = new JsonArray();
                  Set<String> seen = new LinkedHashSet<>();
                  for (OrgSignupOidcConfiguration c : configs) {
                      if (c.getProvider() == null) continue;
                      String key = c.getProvider().key();
                      if (seen.add(key)) providers.add(key);
                  }
                  ctx.response().putHeader("Content-Type", "application/json").end(providers.encode());
              })
              .onFailure(err -> {
                  log.warn("Failed to list platform providers: {}", err.getMessage());
                  authEndpointSupport.respondError(ctx, 500, "Failed to list providers");
              });
    }

    /**
     * {@code POST /api/auth/org/login/find {email}} — emails the address the organizations it is a member
     * of, each with a link to that organization's login. Always {@code 204}: the email says when the
     * address belongs to no organization, so the response reveals nothing about it.
     */
    private void handleFind(RoutingContext ctx) {
        JsonObject body = authEndpointSupport.readJsonBody(ctx);
        String email = body.getString("email");
        if (email == null || email.isBlank()) {
            authEndpointSupport.respondError(ctx, 400, "email is required");
            return;
        }
        organizationService.sendMembershipsEmail(email)
                           .onSuccess(v -> ctx.response().setStatusCode(204).end())
                           .onFailure(err -> {
                               log.warn("Could not email the organizations of {}: {}", email, err.getMessage());
                               authEndpointSupport.respondError(ctx, 500, "Could not send the email");
                           });
    }

    /**
     * {@code GET /api/auth/org/login/:orgId/providers} — the enabled configurations the organization's
     * login offers, {@code [{id, name, provider}]}: the platform's org sign-up registrations followed by
     * the organization's own. {@code 404} for an organization that does not exist.
     */
    private void handleOrganizationProviders(RoutingContext ctx) {
        String orgId = ctx.pathParam("orgId");
        organizationService.findById(orgId)
                           .compose(organization -> {
                               Future<List<OidcConfiguration>> ret;
                               if (organization == null) {
                                   ret = Future.failedFuture(new UnknownOrganizationException());
                               } else {
                                   ret = oidcConfigurationService.findEnabledForScope(orgId, null);
                               }
                               return ret;
                           })
                           .onSuccess(configs -> authEndpointSupport.respondProvidersList(ctx, configs))
                           .onFailure(err -> {
                               if (err instanceof UnknownOrganizationException) {
                                   authEndpointSupport.respondError(ctx, 404, "Unknown organization: " + orgId);
                               } else {
                                   log.warn("Failed to list the providers of organization {}: {}", orgId, err.getMessage());
                                   authEndpointSupport.respondError(ctx, 500, "Failed to list providers");
                               }
                           });
    }

    /**
     * {@code POST /api/auth/org/login/:orgId {email, password}} — verifies the password of the organization's
     * member with that email and establishes the browser session. Generic {@code 401} on any failure.
     */
    private void handleLogin(RoutingContext ctx) {
        String orgId = ctx.pathParam("orgId");
        authEndpointSupport.handlePasswordLogin(ctx,
                (email, password) -> localAuthenticationService.authenticateLocal(email, password, orgId, null));
    }

    /**
     * {@code POST|GET /api/auth/org/login/:orgId/start/:configId} — begins a sign-in to the organization
     * through a configuration its login offers, redirecting the browser to the provider: an org sign-up
     * registration's flow returns to the social callback shared with the landing page, carrying the
     * organization, and an organization's own configuration's to the SSO callback. {@code 400} for a
     * configuration the login does not offer. The completed login is bound to the page that started it,
     * or to the portal for a start reached by a link, which names no page.
     */
    private void handleStart(RoutingContext ctx) {
        String orgId = ctx.pathParam("orgId");
        String configId = ctx.pathParam("configId");
        String origin = SessionBinding.origin(ctx) != null ? SessionBinding.origin(ctx) : portalOrigin();

        offeredConfiguration(orgId, configId)
              .onSuccess(config -> {
                  if (config == null) {
                      authEndpointSupport.respondError(ctx, 400, "The organization's login does not offer " + configId);
                  } else {
                      String callbackUrl = config instanceof OrgSignupOidcConfiguration
                              ? socialCallbackUrl(configId) : ssoCallbackUrl(configId);
                      authEndpointSupport.redirectToIdp(ctx, config, callbackUrl, orgId, origin);
                  }
              })
              .onFailure(err -> {
                  log.warn("Login start failed for {}/{}: {}", orgId, configId, err.getMessage());
                  authEndpointSupport.respondError(ctx, 500, "Failed to start sign-in");
              });
    }

    // the enabled configuration the organization's login offers under the id, or null: every organization's
    // login offers the platform's org sign-up registrations, which findOffered never answers by id
    private Future<OidcConfiguration> offeredConfiguration(String orgId, String configId) {
        return oidcConfigurationService.findOrgSignupById(configId)
                                       .compose(signup -> {
                                           Future<OidcConfiguration> ret;
                                           if (signup != null) {
                                               ret = Future.succeededFuture(signup.isEnabled() ? signup : null);
                                           } else {
                                               ret = oidcConfigurationService.findOffered(configId, orgId, null);
                                           }
                                           return ret;
                                       });
    }

    /**
     * {@code POST /api/auth/org/login/social/start/:provider} — begins a social (OIDC) login from the landing
     * page by redirecting the browser to the chosen Kinotic-curated provider. The flow names no
     * organization, so its callback finds the one the identity is a member of.
     */
    private void handleSocialStart(RoutingContext ctx) {
        authEndpointSupport.handleSocialStart(ctx, this::socialCallbackUrl);
    }

    /**
     * {@code GET /api/auth/org/login/social/callback/:configId} — the social IdP returns here; validates the
     * callback and logs the user in. A flow the landing page started names no organization: the identity
     * signs in to the one organization it is a member of, and one that is a member of several is sent back
     * with {@link OidcErrorCodes#MULTIPLE_ORGANIZATIONS} and those organizations, to choose one and sign in at
     * its own login.
     */
    private void handleSocialCallback(RoutingContext ctx) {
        String pathConfigId = ctx.pathParam("configId");

        oidcFlowOrchestrator.handleCallback(ctx,
                                            pathConfigId,
                                            socialCallbackUrl(pathConfigId),
                                            (_, _, _) -> oidcConfigurationService.findOrgSignupById(pathConfigId))
                            .onSuccess(result -> completeSocialLogin(ctx, result))
                            .onFailure(ex -> authEndpointSupport.redirectCallbackFailure(ctx, ex));
    }

    private void completeSocialLogin(RoutingContext ctx, CallbackResult<OrgSignupOidcConfiguration> result) {
        String configId = result.config().getId();
        if (result.orgId() != null) {
            authEndpointSupport.completeOidcLogin(ctx, result,
                    sub -> identityService.findByOidcIdentity(sub, configId, result.orgId(), null));
        } else {
            String sub = authEndpointSupport.verifiedSubject(ctx, result);
            if (sub != null) {
                identityService.findOrgUsersByOidcIdentity(sub, configId)
                               .onSuccess(memberships -> completeLandingLogin(ctx, result.origin(), memberships))
                               .onFailure(err -> authEndpointSupport.completeLogin(ctx, result.origin(), Future.failedFuture(err)));
            }
        }
    }

    // an identity that is a member of several organizations has proven who it is, so the landing page may
    // offer those organizations for the person to choose from, where a stranger learns nothing
    private void completeLandingLogin(RoutingContext ctx, String origin, List<UserParticipantIdentity> memberships) {
        if (memberships.size() > 1) {
            String organizations = memberships.stream().map(UserParticipantIdentity::getOrganizationId).collect(Collectors.joining(","));
            authEndpointSupport.redirectError(ctx, origin, OidcErrorCodes.MULTIPLE_ORGANIZATIONS, Map.of("organizations", organizations));
        } else {
            authEndpointSupport.completeLogin(ctx, origin, Future.succeededFuture(memberships.isEmpty() ? null : memberships.getFirst()));
        }
    }

    /**
     * {@code GET /api/auth/org/login/sso/callback/:configId} — the org's own IdP returns here; validates the
     * callback and logs the user into that organization. An identity no member is bound to becomes a
     * member when the configuration creates users on sign-in.
     */
    private void handleSsoCallback(RoutingContext ctx) {
        String pathConfigId = ctx.pathParam("configId");

        // the pre-auth callback has no participant bound, so the configuration is resolved against the orgId
        // stashed on the flow session at startFlow, which also confirms the organization offers it.
        // The configId is trusted — it came from the IdP redirect we issued ourselves.
        oidcFlowOrchestrator.handleCallback(ctx,
                                            pathConfigId,
                                            ssoCallbackUrl(pathConfigId),
                                            (orgId, _, _) -> oidcConfigurationService.findOffered(pathConfigId, orgId, null))
                            .onSuccess(result -> completeSsoLogin(ctx, result))
                            .onFailure(ex -> authEndpointSupport.redirectCallbackFailure(ctx, ex));
    }

    private void completeSsoLogin(RoutingContext ctx, CallbackResult<OidcConfiguration> result) {
        authEndpointSupport.completeOidcLogin(ctx, result,
                sub -> identityService.findByOidcIdentity(sub, result.config().getId(), result.orgId(), null),
                () -> {
                    UserParticipantIdentity member = new UserParticipantIdentity();
                    member.setOrganizationId(result.orgId());
                    return Future.succeededFuture(member);
                });
    }

    private String socialCallbackUrl(String configId) {
        return properties.getApiBaseUrl() + "/api/auth/org/login/social/callback/" + configId;
    }

    private String ssoCallbackUrl(String configId) {
        return properties.getApiBaseUrl() + "/api/auth/org/login/sso/callback/" + configId;
    }

    // the origin of the portal's pages, which a start reached by a link belongs to
    private String portalOrigin() {
        URI portal = URI.create(properties.getPortalBaseUrl());
        return portal.getScheme() + "://" + portal.getAuthority();
    }

    private static class UnknownOrganizationException extends RuntimeException {}
}
