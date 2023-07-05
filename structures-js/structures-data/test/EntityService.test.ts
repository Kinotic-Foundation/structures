import {describe, it, expect, beforeAll, afterAll} from 'vitest'
import path from 'node:path'
import {v2 as compose} from 'docker-compose'
import {Continuum} from '@kinotic/continuum'
import {PersonEntityService} from './services/PersonEntityService.js'

describe('EntityServiceTest', () => {

    const composeFilePath = '../../'
    const personEntityService = new PersonEntityService()

    beforeAll(async () => {
        try {
            //await compose.upAll({cwd: path.resolve(composeFilePath), log: true})

            await Continuum.connect('ws://127.0.0.1:58503/v1', 'admin', 'structures')
        } catch (e) {
            console.error(e)
            throw e
        }
    }, 300000)

    afterAll(async () => {
        try {
          //  await compose.down({cwd: path.resolve(composeFilePath), log: true})
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
        })

})
