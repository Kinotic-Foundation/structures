import { EmailTemplateType } from '@/api/model/email/EmailTemplateType'

/**
 * An email of one type that a UI built on Kinotic sends in its own name, chosen by the call that sends it naming the
 * template's id. A call that names none sends its type's default (DEFAULT_EMAIL_TEMPLATE_IDS). Subject and bodies are
 * Handlebars sources rendered with the type's variables. An application's own invitation email template takes
 * precedence over the content of an invitation's template.
 */
export class PlatformEmailTemplate {

    /** The name callers choose the template by, such as builder-invitation. */
    public id: string

    public type: EmailTemplateType

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

    constructor(id: string, type: EmailTemplateType, subject: string, htmlBody: string, textBody: string) {
        this.id = id
        this.type = type
        this.subject = subject
        this.htmlBody = htmlBody
        this.textBody = textBody
    }
}
