/**
 * How an account signs in: what a sign-up chose to establish its email with, and what each method the
 * profile lists authenticates with.
 */
export enum AuthType {
    /** The account's password. */
    PASSWORD = 'PASSWORD',
    /** An identity a provider asserts, bound to the account. */
    OIDC = 'OIDC'
}
