import { EmailTemplateKind } from '@kinotic-ai/system-api'

/** How the console names each kind of email, and the Handlebars variables its templates render with. */
export const EMAIL_TEMPLATE_KINDS: Record<EmailTemplateKind, { label: string, variables: string[] }> = {
    [EmailTemplateKind.INVITATION]: {
        label: 'Invitation',
        variables: ['inviterName', 'organizationName', 'applicationName', 'acceptUrl', 'expiresInDays']
    },
    [EmailTemplateKind.VERIFICATION]: {
        label: 'Sign-up verification',
        variables: ['displayName', 'verificationUrl']
    }
}
