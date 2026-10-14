package org.kinotic.domain.api.model.security;

/**
 * Who owns an {@link OidcConfiguration} and whom it signs in. Mirrors the concrete subtype one-to-one —
 * {@link #ORG_SIGNUP} for {@link OrgSignupOidcConfiguration}, {@link #APP_SOCIAL} for
 * {@link ApplicationSocialOidcConfiguration}, {@link #ORGANIZATION} for
 * {@link OrganizationOidcConfiguration} — and is persisted as the document's polymorphic
 * discriminator, so it is also the value queries filter configurations by kind with.
 */
public enum OidcConfigurationType {
    /**
     * A registration the platform operator holds with a provider, through which organization users sign up and in.
     */
    ORG_SIGNUP,
    /**
     * A registration the platform operator holds with a social provider, which any organization may offer
     * at its applications' sign-in.
     */
    APP_SOCIAL,
    /**
     * A provider an organization configured for its own members or its applications' users.
     */
    ORGANIZATION
}
