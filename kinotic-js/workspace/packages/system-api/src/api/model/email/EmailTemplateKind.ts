/**
 * The emails an OriginEmailTemplate can replace, each rendered with its own variables.
 */
export enum EmailTemplateKind {
    /**
     * The invitation into an organization or one of its applications. Variables: inviterName, organizationName,
     * applicationName (absent for an organization invitation), acceptUrl and expiresInDays.
     */
    INVITATION = 'INVITATION',
    /** The link that verifies a new account's email address at sign-up. Variables: displayName and verificationUrl. */
    VERIFICATION = 'VERIFICATION'
}
