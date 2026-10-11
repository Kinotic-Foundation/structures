package org.kinotic.domain.api.model.security;

/**
 * Who owns an {@link OidcConfiguration} and whom it signs in. Mirrors the concrete subtype one-to-one —
 * {@link #ORG_SIGNUP} for {@link OrgSignupOidcConfiguration}, {@link #ORGANIZATION} for
 * {@link OrganizationOidcConfiguration} — and is persisted as the document's polymorphic
 * discriminator, so it is also the value queries filter configurations by kind with.
 */
public enum OidcConfigurationType {
    /**
     * A social provider the platform operator curates, through which organization users sign up and in.
     */
    ORG_SIGNUP,
    /**
     * A provider an organization configured for its own members or its applications' users.
     */
    ORGANIZATION
}
