package org.kinotic.domain.api.model;

/**
 * The emails an {@link OriginEmailTemplate} can replace, each rendered with its own variables.
 */
public enum EmailTemplateKind {
    /**
     * The invitation into an organization or one of its applications. Variables: {@code inviterName},
     * {@code organizationName}, {@code applicationName} (absent for an organization invitation), {@code acceptUrl}
     * and {@code expiresInDays}.
     */
    INVITATION,
    /**
     * The link that verifies a new account's email address at sign-up. Variables: {@code displayName} and
     * {@code verificationUrl}.
     */
    VERIFICATION
}
