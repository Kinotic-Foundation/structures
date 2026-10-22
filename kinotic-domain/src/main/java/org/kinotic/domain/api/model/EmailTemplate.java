package org.kinotic.domain.api.model;

/**
 * The Handlebars sources of an email, rendered with the variables of the email sent.
 */
public interface EmailTemplate {

    /** Handlebars source for the subject line (rendered as plain text). */
    String getSubject();

    /** Handlebars source for the HTML body. */
    String getHtmlBody();

    /** Handlebars source for the plain-text body. */
    String getTextBody();
}
