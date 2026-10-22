package org.kinotic.domain.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The emails a {@link PlatformEmailTemplate} renders, each with its own variables.
 */
@Getter
@RequiredArgsConstructor
public enum EmailTemplateType {
    /**
     * The invitation into an organization or one of its applications. Variables: {@code inviterName},
     * {@code organizationName}, {@code applicationName} (absent for an organization invitation), {@code acceptUrl}
     * and {@code expiresInDays}.
     */
    INVITATION("kinotic-invitation"),
    /**
     * The link that verifies a new account's email address at sign-up. Variables: {@code displayName} and
     * {@code verificationUrl}.
     */
    VERIFICATION("kinotic-verification");

    /** The id of the {@link PlatformEmailTemplate} sent when a call names none, which cannot be deleted. */
    private final String defaultTemplateId;
}
