import {describe, it, expect, beforeAll, afterAll} from 'vitest'
import {AlwaysPullPolicy, DockerComposeEnvironment, Wait} from 'testcontainers'
import {Continuum} from '@kinotic/continuum'
import {PersonEntityService} from './services/PersonEntityService.js'

describe('EntityServiceTest', () => {

    const composeFilePath = '../../'
    const composeFile = 'docker-compose.yml'
    const personEntityService = new PersonEntityService()
    let environment


    beforeAll(async () => {
        try {
            environment = await new DockerComposeEnvironment(composeFilePath, composeFile)
                .withWaitStrategy('structures-server', Wait.forHealthCheck())
                .withEnvironment({"STRUCTURES_INITIALIZE_WITH_SAMPLE_DATA": "true"})
                .up()


            await Continuum.connect('ws://127.0.0.1:58503/v1', 'admin', 'structures')
        } catch (e) {
            console.error(e)
            throw e
        }
    }, 300000)

    afterAll(async () => {
        try {
            await environment.down();
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
