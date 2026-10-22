package org.kinotic.domain.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * An application's customized invitation email — at most one per application. Subject and
 * bodies are Handlebars sources rendered with the {@link EmailTemplateType#INVITATION} variables; when an
 * application has no row here, the {@link PlatformEmailTemplate} the invitation is sent with is used.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class InviteEmailTemplate implements ApplicationScoped<String>, EmailTemplate {

    private String id;

    /** The organization that owns {@link #applicationId}. */
    private String organizationId;

    /** The application whose invitation email this template customizes. Always set. */
    private String applicationId;

    /** Handlebars source for the subject line (rendered as plain text). */
    private String subject;

    /** Handlebars source for the HTML body. */
    private String htmlBody;

    /** Handlebars source for the plain-text body. */
    private String textBody;

    private Date created;

    private Date updated;
}
