package org.kinotic.domain.api.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A Kinotic-curated social IdP configuration (Google, Microsoft Live, GitHub, etc.) that powers
 * the "Continue with X" buttons on both the new-org signup page and the email-first org login
 * fallback. Belongs to no organization, because signup happens before one exists. Seeded via SQL
 * migration and saved by the platform operator only. Its client secret is one the operator
 * placed in the platform's secret storage under the name {@link #secretNameRef}, and it alone may
 * name a provider's endpoints explicitly, for a provider such as GitHub that publishes no OIDC
 * discovery document.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public final class OrgSignupOidcConfiguration extends OidcConfiguration {

    /**
     * Name of the OAuth client secret in the platform's secret storage — the Azure Key Vault of
     * the deployment, or a {@code KINOTIC_AKV_*} property in development; the latest version is
     * read at sign-in. {@code null} for a public client.
     */
    private String secretNameRef;

    /**
     * Full URL of the provider's OAuth 2.0 authorization endpoint. {@code null} when the
     * provider supports OIDC discovery, which supplies it from the authority.
     */
    private String authorizationUri;

    /**
     * Full URL of the provider's OAuth 2.0 token endpoint. {@code null} when the provider
     * supports OIDC discovery, which supplies it from the authority.
     */
    private String tokenUri;

    /**
     * Full URL of the identity endpoint queried with the access token when the provider
     * issues no id_token (e.g. {@code https://api.github.com/user}). {@code null} for
     * OIDC providers, whose identity claims come from the id_token.
     */
    private String userInfoUri;

    /**
     * Full URL of a GitHub-style emails endpoint — an array of {@code {email, primary,
     * verified}} — supplying the verified email when the {@link #userInfoUri} profile
     * omits it. {@code null} when the identity endpoint or id_token carries the email.
     */
    private String userEmailsUri;

    /**
     * Space-delimited OAuth scope string sent on the authorization request (RFC 6749 wire
     * format). {@code null} requests the standard OIDC {@code openid email profile}.
     */
    private String scopes;

    @Override
    public OidcConfigurationType getType() {
        return OidcConfigurationType.ORG_SIGNUP;
    }
}
