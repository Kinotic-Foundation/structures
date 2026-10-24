import type { Identifiable } from '@kinotic-ai/core'

/**
 * A member of one organization, for listing the organization's members: an organization user joined with its
 * membership of the organization.
 */
export class OrganizationMember implements Identifiable<string> {
    /** The id of the organization user, which member operations name. */
    public id: string | null = null

    public email: string = ''

    public displayName: string | null = null

    /** Whether the membership is enabled, so the member may sign in to this organization. */
    public enabled: boolean = true

    /** When the membership was created. */
    public joined: string | null = null

    /**
     * Whether the member has yet to sign in for the first time: an administrator added them and no sign-in method
     * claims the organization user yet, so the invitation they were sent, or the organization's own provider, is what
     * admits them.
     */
    public pending: boolean = false
}
