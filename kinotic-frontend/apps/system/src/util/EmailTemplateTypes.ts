import { EmailTemplateType } from '@kinotic-ai/system-api'

/** How the console names each type of email, and the Handlebars variables its templates render with. */
export const EMAIL_TEMPLATE_TYPES: Record<EmailTemplateType, { label: string, variables: string[] }> = {
    [EmailTemplateType.INVITATION]: {
        label: 'Invitation',
        variables: ['inviterName', 'organizationName', 'applicationName', 'acceptUrl', 'expiresInDays']
    },
    [EmailTemplateType.VERIFICATION]: {
        label: 'Sign-up verification',
        variables: ['displayName', 'verificationUrl']
    }
}
