import {ArrayC3Type, FunctionDefinition, LongC3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import {
    IAdminEntityService,
    IEntityService,
    NamedQueriesDefinition,
    QueryDecorator,
    Structure, Structures,
} from '@kinotic/structures-api'
import * as allure from 'allure-js-commons'
import {afterAll, afterEach, beforeAll, beforeEach, describe, expect, inject, it} from 'vitest'
import {
    createPersonStructureIfNotExist, createSchema, createTestPeopleAndVerify, createVehicleStructureIfNotExist, deleteStructure,
    generateRandomString,
    initContinuumClient,
    shutdownContinuumClient
} from '../TestHelpers.js'
import {loadOpenAPISchema} from './OpenApiHelpers.js'


interface LocalTestContext {
    personStructure: Structure
    personWithTenantStructure: Structure
    vehicleStructure: Structure
}

const suffix = '_openapi'

describe('OpenApi Tests', () => {

    let context: LocalTestContext = {} as LocalTestContext

    beforeAll(async () => {
        await allure.parentSuite('End To End Tests')
        await initContinuumClient()

        context.personStructure = await createPersonStructureIfNotExist(suffix)
        expect(context.personStructure).toBeDefined()
        context.personWithTenantStructure = await createPersonStructureIfNotExist(suffix, true)
        expect(context.personWithTenantStructure).toBeDefined()
        context.vehicleStructure = await createVehicleStructureIfNotExist(suffix)
        expect(context.vehicleStructure).toBeDefined()

        // This wil get any NamedQueries defined in the EntityServices
        const {namedQueriesDefinition} = await createSchema(suffix, 'PersonWithTenant')
        const namedQueriesService = Structures.getNamedQueriesService()
        await namedQueriesService.save(namedQueriesDefinition)
    }, 300000)

    afterAll(async () => {
        // await shutdownContinuumClient()
        // await expect(deleteStructure(context.personStructure.id as string)).resolves.toBeUndefined()
        // await expect(deleteStructure(context.personWithTenantStructure.id as string)).resolves.toBeUndefined()
        // await expect(deleteStructure(context.vehicleStructure.id as string)).resolves.toBeUndefined()
    }, 60000)


    it<LocalTestContext>(
        'OpenApi Schema loads',
        async () => {
            // @ts-ignore
            const host = inject('STRUCTURES_HOST')
            // @ts-ignore
            const port = inject('STRUCTURES_OPENAPI_PORT')
            const schemaUrl = `http://${host}:${port}/api-docs/structures.api.tests/openapi.json`

            const schema = await loadOpenAPISchema(schemaUrl)

            expect(schema).toBeDefined()
            expect(schema.openapi).toBe('3.0.1')
            expect(schema.info?.title).toBe('structures.api.tests Structures API')
        }
    )

})