/** localStorage key of the id of the organization this browser last signed in to. */
export const LAST_ORGANIZATION_KEY = 'kinotic.lastOrganization'

/** The portal path of an organization's login page. */
export function organizationLoginPath(organizationId: string): string {
    return '/login/' + encodeURIComponent(organizationId)
}

/**
 * The organization id named by what a person typed: the id itself, or the organization's host
 * (`acme.kinotic.io`) or sites URL, whose first label is the id. Empty when nothing was typed.
 */
export function organizationSlug(typed: string): string {
    let ret = typed.trim().toLowerCase()
    ret = ret.replace(/^[a-z]+:\/\//, '')
    const label = ret.split(/[./]/)[0] ?? ''
    return label
}
