package org.kinotic.domain.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * An invitation or sign-up verification email that a UI built on Kinotic sends in place of Kinotic's own, whose link
 * opens that UI's page. Subject and bodies are Handlebars sources rendered with the variables of the email sent:
 * {@code inviterName}, {@code organizationName}, {@code applicationName} (absent for an organization invitation),
 * {@code acceptUrl} and {@code expiresInDays} for an invitation, and {@code displayName} and {@code verificationUrl}
 * for a verification.
 * <p>
 * A call passing a template fails with {@link IllegalArgumentException}, before it saves anything, unless
 * {@code uiBaseUrl} is an absolute http or https URL with no query, fragment or trailing slash and every source is
 * present and compiles and renders.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class EmailTemplate {

    /**
     * The base URL of the UI whose {@code /invite/accept} or {@code /signup/verify} page the email's link opens, such
     * as {@code https://builder.example.com}, with no trailing slash.
     */
    private String uiBaseUrl;

    /** Handlebars source for the subject line (rendered as plain text). */
    private String subject;

    /** Handlebars source for the HTML body. */
    private String htmlBody;

    /** Handlebars source for the plain-text body. */
    private String textBody;
}
