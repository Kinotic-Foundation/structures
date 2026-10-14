package org.kinotic.domain.api.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.OrganizationScoped;

/**
 * An OIDC provider configuration owned by an {@link Organization}, saved by that organization
 * alone. Where it is used is expressed by the {@link LoginProvider} rows that name it, one per
 * sign-in scope — the organization's own login, or one of its applications' — rather than by a
 * flag on the row itself, so one configuration serves every scope that offers it, e.g. an org uses
 * the same Okta tenant for org-admin SSO and for one of its customer-facing apps.
 *
 * <p>The organization names no URL. It chooses a {@link OidcProviderKind} that
 * {@link OidcProviderKind#offeredToOrganizations() is offered to organizations} and gives its
 * {@link #providerAccount} at that provider; the authority is derived from the kind's template when the
 * configuration is saved, replacing any value sent, so the server connects to the provider's own hosts
 * only.
 *
 * <p>The client secret is given to {@code OidcConfigurationService.save(configuration, clientSecret)}
 * and held in the owning organization's scope of the secret storage, keyed by the configuration's
 * id; nothing on the configuration names it, so a configuration read back carries only
 * {@link #confidentialClient}, which says whether one is held.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public final class OrganizationOidcConfiguration extends OidcConfiguration implements OrganizationScoped<String> {

    /**
     * Owning organization: the one of the participant saving the configuration, which the service
     * enforces.
     */
    private String organizationId;

    /**
     * The organization's account at the provider, in the form
     * {@link OidcProviderKind#providerAccountLabel()} asks for: an Entra directory's tenant id, an Okta
     * domain. {@code null} for a kind with one issuer for everyone, such as Google.
     */
    private String providerAccount;

    /**
     * Whether a client secret is held for this configuration and sent on the code exchange, which
     * OAuth 2.0 calls a confidential client; {@code false} for a public client. Set by
     * {@code OidcConfigurationService.save(configuration, clientSecret)} from whether a secret was
     * given, and kept as it is by a save of the configuration alone.
     */
    private boolean confidentialClient;

    @Override
    public OidcConfigurationType getType() {
        return OidcConfigurationType.ORGANIZATION;
    }
}
