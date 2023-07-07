import {describe, it, expect, beforeAll, afterAll} from 'vitest'
import {PersonEntityService} from './services/PersonEntityService.js'
import { WebSocket } from 'ws'
import {initStructuresServer, shutdownStructuresServer} from './TestHelpers.js'

Object.assign(global, { WebSocket})

describe('EntityServiceTest', () => {

    const personEntityService = new PersonEntityService()

    beforeAll(async () => {
        await initStructuresServer()
    }, 300000)

    afterAll(async () => {
        await shutdownStructuresServer()
    }, 60000)


    it('Test Find All',
        () => {
            return personEntityService.findAll({pageNumber: 0, pageSize: 10, sort: null})
                                      .then((page) => {
                                          expect(page.totalElements).toBeGreaterThan(0)
                                          expect(page.size).toEqual(10)
                                      })
        }
    )

    it('Test Count',
        () => {
            return personEntityService.count()
                                      .then((count) => {
                                          expect(count).toBeGreaterThan(0)
                                      })
        }
    )

})
