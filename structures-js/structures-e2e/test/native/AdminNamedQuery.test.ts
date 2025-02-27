import {FunctionDefinition, LongC3Type, ObjectC3Type, ArrayC3Type, StringC3Type} from '@kinotic/continuum-idl'
import * as allure from 'allure-js-commons'
import {describe, it, expect, beforeAll, afterAll, beforeEach, afterEach} from 'vitest'
import { WebSocket } from 'ws'
import {PageableC3Type, PageC3Type, IEntityService, Structures, Structure, QueryDecorator, NamedQueriesDefinition} from '@kinotic/structures-api'
import {
    createPersonStructureIfNotExist,
    createTestPeopleAndVerify,
    deleteStructure,
    generateRandomString,
    initContinuumClient,
    shutdownContinuumClient,
} from '../TestHelpers.js'
import {Person} from '../domain/Person.js'
import {Page, Pageable} from '@kinotic/continuum-client'

Object.assign(global, { WebSocket})

interface LocalTestContext {
    structure: Structure
    entityService: IEntityService<Person>
}

describe('Admin Named Query Tests', () => {

    beforeAll(async () => {
        await allure.parentSuite('End To End Tests')
        await allure.suite('Native Typescript Client')
        await initContinuumClient()
    }, 300000)

    afterAll(async () => {
        await shutdownContinuumClient()
    }, 60000)

    beforeEach<LocalTestContext>(async (context) => {
        context.structure = await createPersonStructureIfNotExist('_' + generateRandomString(5))
        expect(context.structure).toBeDefined()
        context.entityService = Structures.createEntityService(context.structure.namespace, context.structure.name)
        expect(context.entityService).toBeDefined()
    })

    afterEach<LocalTestContext>(async (context) => {
        await expect(deleteStructure(context.structure.id as string)).resolves.toBeUndefined()
    })

})