package org.kinotic.domain.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.core.api.crud.Identifiable;

import java.util.Date;

/**
 * An invitation or sign-up verification email that a UI built on Kinotic sends in its own name, chosen by the call
 * that sends it naming the template's {@link #id}. A call that names none sends {@link #DEFAULT_INVITATION_ID} or
 * {@link #DEFAULT_VERIFICATION_ID}. Subject and bodies are Handlebars sources rendered with the variables of the email
 * the call sends. An application's own {@link InviteEmailTemplate} takes precedence over the content of an
 * invitation's template.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class SystemEmailTemplate implements Identifiable<String>, EmailTemplate {

    /**
     * The invitation template sent when a call names none, which cannot be deleted. Variables: {@code inviterName},
     * {@code organizationName}, {@code applicationName} (absent for an organization invitation), {@code acceptUrl}
     * and {@code expiresInDays}.
     */
    public static final String DEFAULT_INVITATION_ID = "kinotic-invitation";

    /**
     * The sign-up verification template sent when a call names none, which cannot be deleted. Variables:
     * {@code displayName} and {@code verificationUrl}.
     */
    public static final String DEFAULT_VERIFICATION_ID = "kinotic-verification";

    /** The name callers choose the template by, such as {@code builder-invitation}. */
    private String id;

    /**
     * The base URL of the UI whose page the email's link opens, such as {@code https://builder.example.com}; the
     * link opens the portal's page when it is null.
     */
    private String uiBaseUrl;

    /** Handlebars source for the subject line (rendered as plain text). */
    private String subject;

    /** Handlebars source for the HTML body. */
    private String htmlBody;

    /** Handlebars source for the plain-text body. */
    private String textBody;

    private Date created;

    private Date updated;
}
