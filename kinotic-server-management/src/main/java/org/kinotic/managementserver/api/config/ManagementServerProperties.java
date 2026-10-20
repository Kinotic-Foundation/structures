package org.kinotic.managementserver.api.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * The portal and OAuth issuer the management server's browser flows use and the Turnstile site its open routes are
 * guarded by, {@code kinotic.managementServer.*}, set per deployment. The URL the management server's API is reached
 * at is {@code kinotic.domain.managementApiBaseUrl}.
 */
@Getter
@Setter
public class ManagementServerProperties {

    /**
     * Base URL of the management server's OAuth 2.1 surface as the internet reaches it. Set only where that differs from
     * {@code kinotic.domain.managementApiBaseUrl}: a development gateway whose OAuth surface is tunnelled while its OIDC
     * callbacks stay on {@code localhost}.
     */
    private String issuerBaseUrl;

    /** Base URL of the portal, where the management server's browser flows send the user. */
    @NotBlank
    private String portalBaseUrl;

    /** The Cloudflare Turnstile site the portal's find-my-organizations form presents and this server verifies. */
    @Valid
    private TurnstileProperties turnstile = new TurnstileProperties();

    /**
     * The OAuth issuer: {@link #issuerBaseUrl} where set, otherwise {@code managementApiBaseUrl}.
     *
     * @param managementApiBaseUrl the management server's API base URL, {@code kinotic.domain.managementApiBaseUrl}
     */
    public String resolveIssuerBaseUrl(String managementApiBaseUrl) {
        return issuerBaseUrl != null && !issuerBaseUrl.isBlank() ? issuerBaseUrl : managementApiBaseUrl;
    }
}
