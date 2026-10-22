/**
 * The emails a PlatformEmailTemplate renders, each with its own variables.
 */
export enum EmailTemplateType {
    /**
     * The invitation into an organization or one of its applications. Variables: inviterName, organizationName,
     * applicationName (absent for an organization invitation), acceptUrl and expiresInDays.
     */
    INVITATION = 'INVITATION',
    /** The link that verifies a new account's email address at sign-up. Variables: displayName and verificationUrl. */
    VERIFICATION = 'VERIFICATION'
}

/** The id of each type's default PlatformEmailTemplate, sent by every call that names none, which cannot be deleted. */
export const DEFAULT_EMAIL_TEMPLATE_IDS: Record<EmailTemplateType, string> = {
    [EmailTemplateType.INVITATION]: 'kinotic-invitation',
    [EmailTemplateType.VERIFICATION]: 'kinotic-verification'
}
