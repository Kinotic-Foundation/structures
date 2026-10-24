import { OidcProviderType } from '@/api/model/security/OidcProviderType'
import type { OrganizationOidcProvider } from '@/api/model/security/OrganizationOidcProvider'

/**
 * The provider kinds an organization may configure for itself, in the order to offer them, with the account each
 * asks for: the kinds `OidcProviderType` gives an authority template on the server, which validates every save
 * against the same patterns.
 */
export const ORGANIZATION_OIDC_PROVIDERS: OrganizationOidcProvider[] = [
    { provider: OidcProviderType.GOOGLE, providerAccountLabel: null, providerAccountPattern: null },
    {
        provider: OidcProviderType.AZURE_AD,
        providerAccountLabel: 'Directory (tenant) ID, or common or organizations',
        providerAccountPattern: /^(common|organizations|[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})$/
    },
    {
        provider: OidcProviderType.OKTA,
        providerAccountLabel: 'Okta domain',
        providerAccountPattern: /^[a-z0-9-]{1,63}\.(okta\.com|oktapreview\.com|okta-emea\.com)$/
    }
]

/** The kind `provider` among {@link ORGANIZATION_OIDC_PROVIDERS}, or undefined when organizations may not configure it. */
export function findOrganizationOidcProvider(provider: OidcProviderType): OrganizationOidcProvider | undefined {
    return ORGANIZATION_OIDC_PROVIDERS.find(offered => offered.provider === provider)
}

/**
 * Whether the server accepts `providerAccount` as the organization's account at a `provider` kind: in the form the
 * kind asks for, or empty for a kind that takes no account. False for a kind organizations may not configure.
 */
export function isProviderAccountValid(provider: OidcProviderType, providerAccount: string | null): boolean {
    const offered = findOrganizationOidcProvider(provider)
    let ret = false
    if (offered?.providerAccountPattern) {
        ret = providerAccount !== null && offered.providerAccountPattern.test(providerAccount)
    } else if (offered) {
        ret = providerAccount === null || providerAccount.trim() === ''
    }
    return ret
}
