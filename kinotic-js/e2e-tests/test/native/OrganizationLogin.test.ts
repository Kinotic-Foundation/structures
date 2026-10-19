import * as allure from 'allure-js-commons'
import {beforeAll, describe, expect, it} from 'vitest'
import {E2E_FIXTURE_PASSWORD, E2E_ORG_USER_EMAIL, E2E_ORGANIZATION_ID, managementServer, restBase} from '../TestHelpers.js'

/** The second organization the V3 fixtures make the e2e organization user a member of. */
const SECOND_ORGANIZATION_ID = 'e2e-second'
/** The Okta configuration the V3 fixtures give an organization the e2e organization user does not belong to. */
const OTHER_ORGANIZATION_CONFIGURATION_ID = 'e2e-other-okta'

/**
 * The organization login's REST surface on the management server: a login addressed to an organization by its
 * id, the configurations each login offers, the landing page's find-my-organizations email and organization
 * choices, and a signed-in member switching between the organizations it belongs to.
 */
describe('Organization login routes', () => {

    let baseUrl: string

    beforeAll(async () => {
        await allure.suite('e2e-tests/native')
        await allure.subSuite('OrganizationLogin')
        baseUrl = restBase(managementServer())
    }, 300000)

    function post(path: string, body: unknown, cookie?: string): Promise<Response> {
        const headers: Record<string, string> = {'Content-Type': 'application/json'}
        if (cookie) headers.Cookie = cookie
        return fetch(`${baseUrl}${path}`, {method: 'POST', headers, body: JSON.stringify(body)})
    }

    function get(path: string, cookie?: string): Promise<Response> {
        return fetch(`${baseUrl}${path}`, cookie ? {headers: {Cookie: cookie}} : undefined)
    }

    function login(organizationId: string, email: string = E2E_ORG_USER_EMAIL, password: string = E2E_FIXTURE_PASSWORD): Promise<Response> {
        return post(`/api/auth/org/login/${organizationId}`, {email, password})
    }

    // the gateway's session cookie, whose name carries the server's plane
    function sessionCookie(res: Response): string {
        const session = res.headers.getSetCookie().find(c => c.startsWith('__Host-kinotic-') && c.includes('-session='))
        expect(session, 'the response must set the session cookie').toBeDefined()
        return session!.split(';')[0]
    }

    it('signs a member in at the organization and establishes a session', async () => {
        const res = await login(E2E_ORGANIZATION_ID)
        expect(res.status).toBe(204)
        const me = await get('/api/auth/me', sessionCookie(res))
        expect(me.status).toBe(204)
    })

    it('refuses the member at an organization it does not belong to, with the generic 401', async () => {
        expect((await login('nobody')).status).toBe(401)
        // the platform membership's slot is no organization's
        expect((await login('system')).status).toBe(401)
        expect((await login(E2E_ORGANIZATION_ID, E2E_ORG_USER_EMAIL, 'wrong-password')).status).toBe(401)
    })

    it('lists the configurations an organization\'s login offers, the platform\'s GitHub registration among them', async () => {
        const res = await get(`/api/auth/org/login/${E2E_ORGANIZATION_ID}/providers`)
        expect(res.status).toBe(200)
        const providers = await res.json() as {id: string, name: string, provider: string}[]
        expect(providers.some(p => p.id === 'github-platform' && p.provider === 'github')).toBe(true)

        expect((await get('/api/auth/org/login/nobody/providers')).status).toBe(404)
    })

    it('lists the provider keys the landing page offers', async () => {
        const res = await get('/api/auth/org/login/providers')
        expect(res.status).toBe(200)
        expect(await res.json()).toContain('github')
    })

    it('emails a person their organizations for an address alone, behind no challenge in this stack', async () => {
        // the e2e stack configures no Turnstile site, so the page is told to present nothing
        const challenge = await get('/api/auth/org/login/find')
        expect(challenge.status).toBe(200)
        expect(await challenge.json()).toEqual({})

        expect((await post('/api/auth/org/login/find', {})).status).toBe(400)
        // email sending is disabled in the stack, so the send logs the links; the response is the same either way
        expect((await post('/api/auth/org/login/find', {email: E2E_ORG_USER_EMAIL})).status).toBe(204)
        expect((await post('/api/auth/org/login/find', {email: 'nobody@example.test'})).status).toBe(204)
    })

    it('offers no organization choices to a page whose session completed no landing sign-in', async () => {
        const anonymous = await get('/api/auth/org/login/choices')
        expect(anonymous.status).toBe(200)
        expect(await anonymous.json()).toEqual([])
        expect(anonymous.headers.getSetCookie().length, 'a request without a session must create none').toBe(0)

        const signedIn = await get('/api/auth/org/login/choices', sessionCookie(await login(E2E_ORGANIZATION_ID)))
        expect(signedIn.status).toBe(200)
        expect(await signedIn.json()).toEqual([])
    })

    it('connects a sign-in method for the platform\'s registrations and the configurations of organizations the account belongs to only', async () => {
        expect((await post('/api/auth/account/methods/start/github-platform', {})).status).toBe(401)
        const cookie = sessionCookie(await login(E2E_ORGANIZATION_ID))

        // another organization's configuration is refused, so a link cannot bind its directory's identities to an
        // account that organization never admitted
        expect((await post(`/api/auth/account/methods/start/${OTHER_ORGANIZATION_CONFIGURATION_ID}`, {}, cookie)).status).toBe(403)
        expect((await post('/api/auth/account/methods/start/no-such-configuration', {}, cookie)).status).toBe(400)

        // the platform's registration starts the round-trip to its provider
        const started = await fetch(`${baseUrl}/api/auth/account/methods/start/github-platform`,
                                    {method: 'POST', headers: {Cookie: cookie}, redirect: 'manual'})
        expect(started.status).toBe(302)
        expect(started.headers.get('location')).toContain('github.com/login/oauth/authorize')
    })

    it('switches a signed-in member to another organization it belongs to and refuses the rest', async () => {
        expect((await post('/api/auth/org/switch', {organizationId: SECOND_ORGANIZATION_ID})).status).toBe(401)

        let cookie = sessionCookie(await login(E2E_ORGANIZATION_ID))
        const switched = await post('/api/auth/org/switch', {organizationId: SECOND_ORGANIZATION_ID}, cookie)
        expect(switched.status).toBe(204)
        // the switch re-establishes the session, so the page continues with the cookie it set
        cookie = sessionCookie(switched)
        expect((await get('/api/auth/me', cookie)).status).toBe(204)

        expect((await post('/api/auth/org/switch', {organizationId: 'nobody'}, cookie)).status).toBe(403)
        expect((await post('/api/auth/org/switch', {organizationId: 'system'}, cookie)).status).toBe(400)
        expect((await post('/api/auth/org/switch', {}, cookie)).status).toBe(400)
    })
})
