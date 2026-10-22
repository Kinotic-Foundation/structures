import { EmailTemplateKind } from '@/api/model/email/EmailTemplateKind'

/**
 * The email of one kind sent for a flow started from the UI at origin, in place of the built-in one, so a UI built
 * on Kinotic sends emails in its own name. At most one per origin and kind. Subject and bodies are Handlebars
 * sources rendered with the kind's variables. An application's own invitation email template takes precedence for
 * its invitations.
 */
export class OriginEmailTemplate {

    public id: string | null = null

    /**
     * The origin of the UI, scheme and host with no path, such as https://builder.example.com. The template is used
     * only for flows started from a page there that the server's CORS allowlist trusts.
     */
    public origin: string

    public kind: EmailTemplateKind

    /** Handlebars source for the subject line (rendered as plain text). */
    public subject: string

    /** Handlebars source for the HTML body. */
    public htmlBody: string

    /** Handlebars source for the plain-text body. */
    public textBody: string

    public created: string | null = null

    public updated: string | null = null

    constructor(origin: string, kind: EmailTemplateKind, subject: string, htmlBody: string, textBody: string) {
        this.origin = origin
        this.kind = kind
        this.subject = subject
        this.htmlBody = htmlBody
        this.textBody = textBody
    }
}
