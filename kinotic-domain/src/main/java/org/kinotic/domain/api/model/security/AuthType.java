package org.kinotic.domain.api.model.security;

/**
 * How a user signs in: what a sign-up chose to establish its email with, and what each method the
 * profile lists authenticates with.
 */
public enum AuthType {
    /** The user's password. */
    PASSWORD,
    /** An identity a provider asserts, bound to the user. */
    OIDC
}
