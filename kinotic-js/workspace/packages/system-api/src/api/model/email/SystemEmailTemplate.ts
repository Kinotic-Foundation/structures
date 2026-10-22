/** The invitation and sign-up verification templates sent when a call names none, which cannot be deleted. */
export const DEFAULT_EMAIL_TEMPLATE_IDS: readonly string[] = ['kinotic-invitation', 'kinotic-verification']

/**
 * An invitation or sign-up verification email that a UI built on Kinotic sends in its own name, chosen by the call
 * that sends it naming the template's id. A call that names none sends the default (DEFAULT_EMAIL_TEMPLATE_IDS).
 * Subject and bodies are Handlebars sources rendered with the variables of the email the call sends. An application's
 * own invitation email template takes precedence over the content of an invitation's template.
 */
export class SystemEmailTemplate {

    /** The name callers choose the template by, such as builder-invitation. */
    public id: string

    /**
     * The base URL of the UI whose page the email's link opens, such as https://builder.example.com; the link opens
     * the portal's page when it is null.
     */
    public uiBaseUrl: string | null = null

    /** Handlebars source for the subject line (rendered as plain text). */
    public subject: string

    /** Handlebars source for the HTML body. */
    public htmlBody: string

    /** Handlebars source for the plain-text body. */
    public textBody: string

    public created: string | null = null

    public updated: string | null = null

    constructor(id: string, subject: string, htmlBody: string, textBody: string) {
        this.id = id
        this.subject = subject
        this.htmlBody = htmlBody
        this.textBody = textBody
    }
}
