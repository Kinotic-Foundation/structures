import {Kinotic} from '@kinotic-ai/core'
import {OidcConfigurationType, OidcProviderType, type OwnedOidcConfiguration} from '@kinotic-ai/management-api'
import * as allure from 'allure-js-commons'
import {afterAll, beforeAll, describe, expect, it} from 'vitest'
import {E2E_ORGANIZATION_ID, generateRandomString, initKinoticClient, managementServer, restBase, shutdownKinoticClient} from '../TestHelpers.js'

/** The application this suite copies a configuration to; created by the suite, as no fixture is its own. */
const APPLICATION_ID = 'e2e-owned-oidc'

/**
 * An organization's own OIDC configurations over STOMP, as one of its members: a configuration's life from being
 * saved with a secret through an edit that keeps it, a copy to an application, being disabled and deleted, the
 * callback URL each is registered with, and what the organization's login offers along the way.
 */
describe('Owned OIDC configurations', () => {

    let baseUrl: string
    let okta: OwnedOidcConfiguration

    beforeAll(async () => {
        await allure.suite('e2e-tests/native')
        await allure.subSuite('OwnedOidcConfigurations')
        baseUrl = restBase(managementServer())
        await initKinoticClient()
        await Kinotic.applications.createApplicationIfNotExist(APPLICATION_ID, 'e2e fixture application for the owned OIDC configuration test')
    }, 300000)

    afterAll(async () => {
        await shutdownKinoticClient()
    }, 60000)

    function organizationConfiguration(provider: OidcProviderType, providerAccount: string | null): OwnedOidcConfiguration {
        return {
            id: null,
            type: OidcConfigurationType.ORGANIZATION,
            organizationId: null,
            name: `e2e ${provider}`,
            provider,
            providerAccount,
            clientId: 'e2e-client',
            // derived from the kind and the account on save, whatever is sent
            authority: 'https://evil.example',
            audience: null,
            enabled: true,
            confidentialClient: false,
            createUserOnSignIn: false,
            created: null,
            updated: null
        }
    }

    async function offeredIds(): Promise<string[]> {
        const res = await fetch(`${baseUrl}/api/auth/org/login/${E2E_ORGANIZATION_ID}/providers`)
        expect(res.status).toBe(200)
        return (await res.json() as {id: string}[]).map(p => p.id)
    }

    it('saves an Okta configuration with a secret, derives its authority, and offers it at the organization\'s login', async () => {
        const domain = `e2e-${generateRandomString(8).toLowerCase()}.okta.com`
        okta = await Kinotic.ownedOidcConfigurations.save(organizationConfiguration(OidcProviderType.OKTA, domain), 'e2e-secret')
        expect(okta.id).toBeTruthy()
        expect(okta.organizationId).toBe(E2E_ORGANIZATION_ID)
        expect(okta.confidentialClient).toBe(true)
        expect(okta.authority).toBe(`https://${domain}`)

        const read = await Kinotic.ownedOidcConfigurations.findById(okta.id!)
        expect(read?.confidentialClient).toBe(true)
        expect(read?.authority).toBe(`https://${domain}`)

        // under the management server's API base URL as a browser reaches it, which the suite's mapped port is not
        const callbackUrl = await Kinotic.ownedOidcConfigurations.findCallbackUrl(okta.id!)
        expect(callbackUrl!.endsWith(`/api/auth/org/login/sso/callback/${okta.id}`)).toBe(true)

        expect(await offeredIds()).toContain(okta.id)
    })

    it('keeps the held secret through an edit that sends none, and deletes it only when asked', async () => {
        const renamed = await Kinotic.ownedOidcConfigurations.save({...okta, name: 'e2e Okta renamed', confidentialClient: false}, null)
        expect(renamed.name).toBe('e2e Okta renamed')
        expect(renamed.confidentialClient).toBe(true)
        okta = renamed

        // a sign-in start runs discovery against the provider, so it is shown through Google, whose issuer is real
        const google = await Kinotic.ownedOidcConfigurations.save(organizationConfiguration(OidcProviderType.GOOGLE, null), 'e2e-secret')
        try {
            const edited = await Kinotic.ownedOidcConfigurations.save({...google, name: 'e2e Google renamed'}, null)
            expect(edited.confidentialClient).toBe(true)

            const started = await fetch(`${baseUrl}/api/auth/org/login/${E2E_ORGANIZATION_ID}/start/${google.id}`, {redirect: 'manual'})
            expect(started.status).toBe(302)
            const authorize = new URL(started.headers.get('location')!)
            expect(authorize.host).toBe('accounts.google.com')
            expect(authorize.searchParams.get('client_id')).toBe('e2e-client')
            expect(authorize.searchParams.get('redirect_uri')).toBe(await Kinotic.ownedOidcConfigurations.findCallbackUrl(google.id!))

            const publicClient = await Kinotic.ownedOidcConfigurations.deleteClientSecret(google.id!)
            expect(publicClient?.confidentialClient).toBe(false)
            expect((await Kinotic.ownedOidcConfigurations.findById(google.id!))?.confidentialClient).toBe(false)
            // a save that claims a secret is held gives it none
            expect((await Kinotic.ownedOidcConfigurations.save({...publicClient!, confidentialClient: true}, null)).confidentialClient).toBe(false)
        } finally {
            await Kinotic.ownedOidcConfigurations.deleteById(google.id!)
        }
        expect(await Kinotic.ownedOidcConfigurations.deleteClientSecret(google.id!)).toBeNull()
    })

    it('copies the configuration to an application, with an id and a callback URL of its own', async () => {
        const copy = await Kinotic.ownedOidcConfigurations.copy(okta.id!, APPLICATION_ID)
        try {
            expect(copy.id).toBeTruthy()
            expect(copy.id).not.toBe(okta.id)
            expect(copy.type).toBe(OidcConfigurationType.APPLICATION)
            expect(copy.applicationId).toBe(APPLICATION_ID)
            expect(copy.confidentialClient).toBe(true)

            // the application's login returns to the application's own API host
            const callbackUrl = await Kinotic.ownedOidcConfigurations.findCallbackUrl(copy.id!)
            expect(new URL(callbackUrl!).host.startsWith(`${E2E_ORGANIZATION_ID}--${APPLICATION_ID}.`)).toBe(true)
            expect(callbackUrl!.endsWith(`/api/auth/app/login/oidc/callback/${copy.id}`)).toBe(true)
            expect(await offeredIds()).not.toContain(copy.id)
        } finally {
            await Kinotic.ownedOidcConfigurations.deleteById(copy.id!)
        }
    })

    it('refuses a save that changes the configuration\'s kind, or names an application that is not the organization\'s', async () => {
        await expect(Kinotic.ownedOidcConfigurations.save({...okta, type: OidcConfigurationType.APPLICATION, applicationId: APPLICATION_ID}, null))
            .rejects.toThrow()
        // e2e-other's applications are no more the organization's than one that does not exist
        await expect(Kinotic.ownedOidcConfigurations.save({
            ...organizationConfiguration(OidcProviderType.OKTA, 'e2e.okta.com'),
            type: OidcConfigurationType.APPLICATION,
            applicationId: 'e2e-no-such-application'
        }, null)).rejects.toThrow()
        // an organization's login admits the members its administrators added
        await expect(Kinotic.ownedOidcConfigurations.save({...okta, createUserOnSignIn: true}, null)).rejects.toThrow()
        expect((await Kinotic.ownedOidcConfigurations.findById(okta.id!))?.type).toBe(OidcConfigurationType.ORGANIZATION)
    })

    it('stops offering a disabled configuration, and deletes it', async () => {
        const disabled = await Kinotic.ownedOidcConfigurations.save({...okta, enabled: false}, null)
        expect(disabled.enabled).toBe(false)
        expect(disabled.confidentialClient).toBe(true)
        expect(await offeredIds()).not.toContain(okta.id)

        await Kinotic.ownedOidcConfigurations.deleteById(okta.id!)
        expect(await Kinotic.ownedOidcConfigurations.findById(okta.id!)).toBeNull()
        expect(await Kinotic.ownedOidcConfigurations.findCallbackUrl(okta.id!)).toBeNull()
    })
})
