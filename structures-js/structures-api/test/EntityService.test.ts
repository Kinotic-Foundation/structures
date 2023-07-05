import {describe, it, expect, beforeAll, afterAll} from 'vitest'
// @ts-ignore for some reason intellij is complaining about this even though esModuleInterop is enabled
import path from 'node:path'
import {v2 as compose} from 'docker-compose'
import {Continuum} from '@kinotic/continuum-client'
import {PersonEntityService} from './services/PersonEntityService.js'
import { WebSocket } from 'ws'

Object.assign(global, { WebSocket})

describe('EntityServiceTest', () => {

    const composeFilePath = '../../'
    const personEntityService = new PersonEntityService()

    beforeAll(async () => {
        try {
            const resolvedPath = path.resolve(composeFilePath)

            await compose.pullAll({cwd: resolvedPath, log: true})

            await compose.upAll({
                cwd: resolvedPath,
                composeOptions: [['--env-file', path.resolve('test/.env.test')]],
                log: true
            })

            await Continuum.connect('ws://127.0.0.1:58503/v1', 'admin', 'structures')
            console.log('Connected to continuum')
        } catch (e) {
            console.error(e)
            throw e
        }
    }, 300000)

    afterAll(async () => {
        try {
            await compose.down({cwd: path.resolve(composeFilePath), log: true})
        } catch (e) {
            console.error(e)
            throw e
        }
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
