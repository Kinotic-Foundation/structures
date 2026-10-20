package org.kinotic.managementserver.internal.api.rest;

import io.vertx.ext.web.RoutingContext;
import org.kinotic.management.api.config.KinoticManagementApiProperties;
import org.kinotic.domain.api.rest.OAuthExtensionGrant;
import org.kinotic.domain.api.rest.OAuthServerHandler;
import org.kinotic.domain.api.rest.support.AuthEndpointSupport;
import org.kinotic.domain.api.services.security.OAuthAuthorizationService;
import org.kinotic.domain.api.services.security.RefreshTokenService;
import org.kinotic.managementserver.api.config.ManagementServerProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The management server's authorization server, issued under {@code kinotic.managementServer.issuerBaseUrl} or
 * {@code kinotic.managementApi.apiBaseUrl}.
 */
@Component
public class ManagementOAuthServerHandler extends OAuthServerHandler {

    private final ManagementServerProperties properties;
    private final KinoticManagementApiProperties managementApiProperties;

    public ManagementOAuthServerHandler(ManagementServerProperties properties,
                                 KinoticManagementApiProperties managementApiProperties,
                                 AuthEndpointSupport authEndpointSupport,
                                 OAuthAuthorizationService oauthAuthorizationService,
                                 RefreshTokenService refreshTokenService,
                                 List<OAuthExtensionGrant> extensionGrants) {
        super(authEndpointSupport, oauthAuthorizationService, refreshTokenService, extensionGrants);
        this.properties = properties;
        this.managementApiProperties = managementApiProperties;
    }

    @Override
    public String issuer(RoutingContext ctx) {
        return properties.resolveIssuerBaseUrl(managementApiProperties.getManagementApi().getApiBaseUrl());
    }
}
