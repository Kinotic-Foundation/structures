import { EmailTemplateKind } from '@/api/model/email/EmailTemplateKind'

/**
 * An email of one kind that a UI built on Kinotic sends in its own name, chosen by the call that sends it naming the
 * template's id. A call that names none sends its kind's default (DEFAULT_EMAIL_TEMPLATE_IDS). Subject and bodies are
 * Handlebars sources rendered with the kind's variables. An application's own invitation email template takes
 * precedence over the content of an invitation's template.
 */
export class PlatformEmailTemplate {

    /** The name callers choose the template by, such as builder-invitation. */
    public id: string

    public kind: EmailTemplateKind

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

    constructor(id: string, kind: EmailTemplateKind, subject: string, htmlBody: string, textBody: string) {
        this.id = id
        this.kind = kind
        this.subject = subject
        this.htmlBody = htmlBody
        this.textBody = textBody
    }
}
