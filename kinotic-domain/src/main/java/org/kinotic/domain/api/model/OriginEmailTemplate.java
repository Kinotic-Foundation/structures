package org.kinotic.domain.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.core.api.crud.Identifiable;

import java.util.Date;

/**
 * The email of one {@link EmailTemplateKind} sent for a flow started from the UI at {@link #origin}, so each UI sends
 * emails in its own name; a UI with none of its own sends the portal's. At most one per origin and kind. Subject and
 * bodies are Handlebars sources rendered with the kind's variables. An application's own
 * {@link InviteEmailTemplate} takes precedence for its invitations.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class OriginEmailTemplate implements Identifiable<String>, EmailTemplate {

    private String id;

    /**
     * The origin of the UI, scheme and authority with no path, such as {@code https://builder.example.com}. The
     * template is used only for flows started from a page there that the server's CORS allowlist trusts.
     */
    private String origin;

    private EmailTemplateKind kind;

    /** Handlebars source for the subject line (rendered as plain text). */
    private String subject;

    /** Handlebars source for the HTML body. */
    private String htmlBody;

    /** Handlebars source for the plain-text body. */
    private String textBody;

    private Date created;

    private Date updated;
}
