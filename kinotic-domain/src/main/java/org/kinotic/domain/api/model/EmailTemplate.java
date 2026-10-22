package org.kinotic.domain.api.model;

/**
 * The Handlebars sources of an email that replaces a built-in one, rendered with the variables of its
 * {@link EmailTemplateKind}.
 */
public interface EmailTemplate {

    /** Handlebars source for the subject line (rendered as plain text). */
    String getSubject();

    /** Handlebars source for the HTML body. */
    String getHtmlBody();

    /** Handlebars source for the plain-text body. */
    String getTextBody();
}
