import {Kinotic, Pageable} from '@kinotic-ai/core'
import * as allure from 'allure-js-commons'
import {afterAll, beforeAll, describe, expect, it} from 'vitest'
import {E2E_ORG_USER_EMAIL, generateRandomString, initKinoticClient, shutdownKinoticClient} from '../TestHelpers.js'

/**
 * An organization's members over STOMP, as one of them: the member list and search built from memberships,
 * and an invitation's life from creation to cancellation.
 */
describe('Members', () => {

    beforeAll(async () => {
        await allure.suite('e2e-tests/native')
        await allure.subSuite('Members')
        await initKinoticClient()
    }, 300000)

    afterAll(async () => {
        await shutdownKinoticClient()
    }, 60000)

    it('lists the organization\'s members, the signed-in account among them', async () => {
        const page = await Kinotic.members.findMembers(null, Pageable.create(0, 50))
        const me = page.content?.find(m => m.email === E2E_ORG_USER_EMAIL)
        expect(me).toBeDefined()
        expect(me!.enabled).toBe(true)
        expect(me!.id).toBeTruthy()
    })

    it('finds a member by name', async () => {
        const page = await Kinotic.members.searchMembers('Kinotic Test', null, Pageable.create(0, 10))
        expect(page.content?.some(m => m.email === E2E_ORG_USER_EMAIL)).toBe(true)
    })

    it('invites an address, lists the invitation among the pending ones and cancels it', async () => {
        const email = `e2e-invite-${generateRandomString(8).toLowerCase()}@example.test`
        const invite = await Kinotic.members.inviteMember(email, 'Invited Person', null)
        expect(invite.id).toBeTruthy()
        expect(invite.email).toBe(email)
        expect(invite.applicationId).toBeNull()

        const pending = await Kinotic.members.findPendingInvites(null, Pageable.create(0, 100))
        expect(pending.content?.some(i => i.id === invite.id)).toBe(true)

        await Kinotic.members.cancelInvite(invite.id!)
        const afterCancel = await Kinotic.members.findPendingInvites(null, Pageable.create(0, 100))
        expect(afterCancel.content?.some(i => i.id === invite.id)).toBe(false)
    })

    it('refuses an invitation for an address that is already a member', async () => {
        await expect(Kinotic.members.inviteMember(E2E_ORG_USER_EMAIL, null, null))
            .rejects.toThrow(/already exists in this organization/)
    })
})
