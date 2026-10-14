package org.kinotic.appserver.internal.api.rest;

import io.vertx.core.Future;
import io.vertx.core.net.HostAndPort;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.core.api.security.SessionBinding;
import org.kinotic.appserver.api.config.AppServerProperties;
import org.kinotic.domain.api.model.ApplicationKey;
import org.kinotic.domain.api.utils.HostLabelUtil;
import org.kinotic.domain.api.model.Application;
import org.kinotic.domain.api.repositories.ApplicationRepository;
import org.kinotic.domain.api.rest.support.AuthEndpointSupport;
import org.kinotic.domain.api.rest.support.OidcFlowOrchestrator;
import org.kinotic.domain.api.services.security.KinoticJwtIssuer;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.services.security.RefreshTokenService;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * The app server's {@link AuthEndpointSupport}. Every request the app server serves is addressed to one
 * application's API host, {@code <organizationId>--<applicationId>} under {@code kinotic.appServer.apiBaseUrl},
 * and {@link #applicationKey} names that application; the browser flows send the user to its primary UI.
 */
@Component
public class ApplicationAuthEndpointSupport extends AuthEndpointSupport {

    /**
     * Path on the shared host, {@code kinotic.appServer.apiBaseUrl}, that begins a sign-in through one of the
     * platform's social registrations; {@link #socialAuthorizeUrl} builds the URL a start answers with.
     */
    public static final String SOCIAL_AUTHORIZE_PATH = "/api/auth/app/login/social/authorize";

    private final AppServerProperties properties;
    private final ApplicationRepository applicationRepository;

    public ApplicationAuthEndpointSupport(AppServerProperties properties,
                                          ApplicationRepository applicationRepository,
                                          KinoticJwtIssuer jwtIssuer,
                                          OidcConfigurationService oidcConfigurationService,
                                          OidcFlowOrchestrator oidcFlowOrchestrator,
                                          ParticipantIdentityService identityService,
                                          RefreshTokenService refreshTokenService) {
        super(jwtIssuer, oidcConfigurationService, oidcFlowOrchestrator, identityService, refreshTokenService);
        this.properties = properties;
        this.applicationRepository = applicationRepository;
    }

    /**
     * The application whose API host the request was addressed to.
     *
     * @param ctx the request
     * @return the application
     * @throws HttpException with {@code 404} when the request names no application's API host
     */
    public ApplicationKey applicationKey(RoutingContext ctx) {
        ApplicationKey ret = findApplicationKey(ctx);
        if (ret == null) {
            throw new HttpException(404);
        }
        return ret;
    }

    /**
     * The application the request was addressed to. Fails the request with {@code 403} when the
     * page that sent it is not one of the application's UIs.
     */
    public ApplicationKey requireApplicationUi(RoutingContext ctx) {
        ApplicationKey applicationKey = applicationKey(ctx);
        String origin = SessionBinding.origin(ctx);
        // a login authenticates the page that made it, so a login made from another application's UI would
        // hand that UI this application's user; a request naming no page comes from a client outside a browser
        if (origin != null && !isApplicationUi(applicationKey, origin)) {
            throw new AuthorizationException("The page is not a UI of this application");
        }
        return applicationKey;
    }

    /**
     * Whether the page at {@code origin} is one of the application's UIs: one of its published sites, or a
     * local origin {@code kinotic.appServer.localUiOriginPattern} admits.
     */
    public boolean isApplicationUi(ApplicationKey applicationKey, String origin) {
        String host = URI.create(origin).getHost();
        Pattern localUiOrigins = properties.getLocalUiOriginPattern();
        // the CORS pattern holds sites to the sites domain, so a site is named by the first label of its host
        return (host != null && HostLabelUtil.isSiteLabel(applicationKey, StringUtils.substringBefore(host, ".")))
                || (localUiOrigins != null && localUiOrigins.matcher(origin).matches());
    }

    /**
     * The URL on the shared host that begins a sign-in to {@code applicationKey} through the platform social
     * registration {@code configId}, for the page at {@code origin}.
     */
    public String socialAuthorizeUrl(ApplicationKey applicationKey, String configId, String origin) {
        return properties.getApiBaseUrl() + SOCIAL_AUTHORIZE_PATH
                + "?organizationId=" + URLEncoder.encode(applicationKey.organizationId(), StandardCharsets.UTF_8)
                + "&applicationId=" + URLEncoder.encode(applicationKey.applicationId(), StandardCharsets.UTF_8)
                + "&configurationId=" + URLEncoder.encode(configId, StandardCharsets.UTF_8)
                + "&origin=" + URLEncoder.encode(origin, StandardCharsets.UTF_8);
    }

    /**
     * {@inheritDoc} The UI is the primary UI of the application the request is addressed to.
     *
     * @throws HttpException with {@code 404} when the request names no application's API host, or names an
     *                       application that does not exist
     * @throws NullPointerException     while the application has no primary UI designated, which the
     *                                  gateway answers with {@code 400}
     */
    @Override
    public Future<String> uiUrl(RoutingContext ctx, String path) {
        ApplicationKey applicationKey = findApplicationKey(ctx);
        Future<Application> application = applicationKey != null
                ? applicationRepository.findById(applicationKey.applicationId(), applicationKey.organizationId())
                : Future.succeededFuture();
        return application.map(found -> {
            if (found == null) {
                throw new HttpException(404);
            }
            Validate.notNull(found.getPrimaryUiUrl(), "The application '%s' has no primary UI", found.getId());
            return found.getPrimaryUiUrl() + path;
        });
    }

    // null when the request was addressed to a host that is no application's API host
    private ApplicationKey findApplicationKey(RoutingContext ctx) {
        // authority() is the request's Host header, parsed; null on an HTTP/1.0 request that sent none
        HostAndPort authority = ctx.request().authority();
        return authority != null ? HostLabelUtil.fromHost(authority.host(), properties.getApiBaseUrl()) : null;
    }
}
