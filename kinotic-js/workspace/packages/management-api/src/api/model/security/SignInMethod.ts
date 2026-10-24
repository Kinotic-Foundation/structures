import type { OidcProviderType } from '@/api/model/security/OidcProviderType'
import type { AuthType } from '@/api/model/security/AuthType'

/** The id of the user's password as a sign-in method. */
export const PASSWORD_SIGN_IN_METHOD_ID = 'password'

/**
 * One way the signed-in user authenticates, as the profile page lists them: the user's password, or a
 * provider identity bound to it. Nothing of the credential itself travels.
 */
export interface SignInMethod {
    /** What names the method to removeSignInMethod: the binding's id for a provider identity, 'password' for the password. */
    id: string
    type: AuthType
    /** The name to show: the configuration the identity was asserted under, or "Password". */
    name: string
    /** The provider type, for branding, or null for the password. */
    provider: OidcProviderType | null
    /** When the method was added, or null for the password. */
    created: string | null
}
