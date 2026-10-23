package org.kinotic.appserver.internal.api.rest;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.HttpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.core.api.security.SessionBinding;
import org.kinotic.domain.api.exceptions.ExistingUserException;
import org.kinotic.domain.api.model.Application;
import org.kinotic.domain.api.rest.SuppliesGatewayRoutes;
import org.kinotic.domain.api.services.security.SignUpService;
import org.springframework.stereotype.Component;

/**
 * Email and password sign-up for end-users of an application built on Kinotic, served at the application's own API
 * host for an application that allows {@link Application#isPasswordSignUp() password sign-up}. The page signing up
 * posts the email and name, Kinotic emails a link to that page's {@code /signup/verify?token=<token>}, and the
 * verify page posts the token with the new password, which creates the user and signs the page in. Each user that
 * signs up gets a tenant of its own. A sign-up is made only from a page that is one of the application's UIs.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationSignUpHandler implements SuppliesGatewayRoutes {

    private final SignUpService signUpService;
    private final ApplicationAuthEndpointSupport authEndpointSupport;

    @Override
    public void mountRoutes(Router router) {
        router.post("/api/auth/app/signup").handler(this::handleSignUp);
        router.post("/api/auth/app/signup/complete").handler(this::handleComplete);
    }

    /**
     * {@code POST /api/auth/app/signup {email, displayName}} — starts a sign-up, emailing a verification link to the
     * {@code /signup/verify} page of the page that sent it, and answers {@code 202} whether or not the email has a
     * user already. {@code 400} for a missing field or a request that names no page; {@code 403} when the
     * application does not allow sign-up, or from a page that is not one of the application's UIs.
     */
    private void handleSignUp(RoutingContext ctx) {
        String origin = SessionBinding.origin(ctx);
        if (origin == null) {
            authEndpointSupport.respondError(ctx, 400, "A sign-up is made from a page");
            return;
        }
        JsonObject body = authEndpointSupport.readJsonBody(ctx);
        authEndpointSupport.loginScope(ctx, null)
              .compose(scope -> signUpService.initiateApplicationSignUp(scope.application(), body.getString("email"),
                                                                      body.getString("displayName"), origin))
              .onSuccess(v -> ctx.response().setStatusCode(202).end())
              .onFailure(err -> fail(ctx, err, "Failed to start sign-up"));
    }

    /**
     * {@code POST /api/auth/app/signup/complete {token, password}} — completes a sign-up from its verify page: creates
     * the user with the verified email and the password, signs the page in, and answers {@code 204}. {@code 400} for a
     * token that is unknown, spent, expired or issued for another application; {@code 409} when the email became a
     * user meanwhile; {@code 403} as for the start.
     */
    private void handleComplete(RoutingContext ctx) {
        JsonObject body = authEndpointSupport.readJsonBody(ctx);
        authEndpointSupport.loginScope(ctx, null)
              .compose(scope -> signUpService.completeApplicationSignUp(scope.application(), body.getString("token"),
                                                                      body.getString("password")))
              .onSuccess(participant -> authEndpointSupport.respondSuccess(ctx, participant))
              .onFailure(err -> {
                  if (err instanceof ExistingUserException) {
                      authEndpointSupport.respondError(ctx, 409, "This email has an account already. Sign in instead.");
                  } else {
                      fail(ctx, err, "Failed to complete sign-up");
                  }
              });
    }

    // a refused scope or application is answered with the status it names, which the router's failure handler renders
    private void fail(RoutingContext ctx, Throwable err, String message) {
        if (err instanceof HttpException || err instanceof AuthorizationException) {
            ctx.fail(err);
        } else if (err instanceof IllegalArgumentException) {
            authEndpointSupport.respondError(ctx, 400, err.getMessage());
        } else {
            log.warn("{} at {}: {}", message, ctx.request().authority(), err.getMessage());
            authEndpointSupport.respondError(ctx, 500, message);
        }
    }
}
