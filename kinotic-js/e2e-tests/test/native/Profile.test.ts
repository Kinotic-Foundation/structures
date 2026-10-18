import {Kinotic} from '@kinotic-ai/core'
import {OidcConfigurationType, OidcProviderKind, PASSWORD_SIGN_IN_METHOD_ID, SignInMethodKind} from '@kinotic-ai/management-api'
import * as allure from 'allure-js-commons'
import {afterAll, beforeAll, describe, expect, it} from 'vitest'
import {E2E_ORG_USER_EMAIL, E2E_ORGANIZATION_ID, initKinoticClient, shutdownKinoticClient} from '../TestHelpers.js'

/**
 * The signed-in account's own profile over STOMP: who it is, how it signs in, which providers it may connect,
 * and which organizations it belongs to. Connected as the e2e organization user.
 */
describe('Profile', () => {

    beforeAll(async () => {
        await allure.suite('e2e-tests/native')
        await allure.subSuite('Profile')
        await initKinoticClient()
    }, 300000)

    afterAll(async () => {
        await shutdownKinoticClient()
    }, 60000)

    it('describes the signed-in account', async () => {
        const profile = await Kinotic.profile.findMyProfile()
        expect(profile.email).toBe(E2E_ORG_USER_EMAIL)
        expect(profile.id).toBeTruthy()
    })

    it('lists the account\'s sign-in methods, its password among them', async () => {
        const methods = await Kinotic.profile.findMySignInMethods()
        expect(methods.some(m => m.id === PASSWORD_SIGN_IN_METHOD_ID && m.kind === SignInMethodKind.PASSWORD)).toBe(true)
    })

    it('offers the platform\'s GitHub registration to connect', async () => {
        const providers = await Kinotic.profile.findConnectableProviders()
        const github = providers.find(p => p.configurationId === 'github-platform')
        expect(github).toBeDefined()
        expect(github!.provider).toBe(OidcProviderKind.GITHUB)
        expect(github!.type).toBe(OidcConfigurationType.ORG_SIGNUP)
    })

    it('lists every organization the account is a member of', async () => {
        const ids = (await Kinotic.profile.findMyOrganizations()).map(o => o.id)
        expect(ids).toContain(E2E_ORGANIZATION_ID)
        expect(ids).toContain('e2e-second')
    })

    it('renames the account and reads the name back', async () => {
        const original = (await Kinotic.profile.findMyProfile()).displayName
        try {
            const renamed = await Kinotic.profile.updateDisplayName('Kinotic Test (renamed)')
            expect(renamed.displayName).toBe('Kinotic Test (renamed)')
            expect((await Kinotic.profile.findMyProfile()).displayName).toBe('Kinotic Test (renamed)')
        } finally {
            await Kinotic.profile.updateDisplayName(original ?? 'Kinotic Test')
        }
    })
})
