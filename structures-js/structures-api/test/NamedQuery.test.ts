// @ts-nocheck
import {FunctionDefinition} from '@kinotic/continuum-idl'
import {describe, it, expect, beforeAll, afterAll, beforeEach, afterEach} from 'vitest'
import { WebSocket } from 'ws'
import {getNamedQueriesService} from '../src/api/Structures.js'
import {
    createPersonStructureIfNotExist,
    findAndVerifyPeopleWithCursorPaging,
    createTestPeopleAndVerify,
    createTestPerson,
    deleteStructure,
    generateRandomString,
    initStructuresServer,
    shutdownStructuresServer,
    logFailure, findAndVerifyPeopleWithOffsetPaging
} from './TestHelpers.js'
import {Person} from './domain/Person.js'
import {Page, Pageable, Order, Direction} from '@kinotic/continuum-client'
import {IEntityService, Structures, Structure, QueryDecorator, NamedQueriesDefinition} from '../src'
import delay from 'delay'

Object.assign(global, { WebSocket})

interface LocalTestContext {
    structure: Structure
    entityService: IEntityService<Person>
}

describe('NamedQueryTest', () => {

    beforeAll(async () => {
        await initStructuresServer()
    }, 300000)

    afterAll(async () => {
        await shutdownStructuresServer()
    }, 60000)

    beforeEach<LocalTestContext>(async (context) => {
        context.structure = await createPersonStructureIfNotExist(generateRandomString(5))
        expect(context.structure).toBeDefined()
        context.entityService = Structures.createEntityService(context.structure.namespace, context.structure.name)
        expect(context.entityService).toBeDefined()
    })

    afterEach<LocalTestContext>(async (context) => {
       // await expect(deleteStructure(context.structure.id)).resolves.toBeUndefined()
    })


    it<LocalTestContext>('Test Bulk CRUD',
        async ({entityService}) => {
            // Create people
            await createTestPeopleAndVerify(entityService, 100, 2000)

            // Find all the people
            const page: Page<Person> = await entityService.findAll(Pageable.create(0, 10))
            expect(page).toBeDefined()
            expect(page.totalElements).toBe(100)
            expect(page.content.length).toBe(10)

            const structureId = entityService.structureId
            const query = new QueryDecorator(`SELECT * FROM "struct_${structureId}" where firstName = :name`)
            const namedQuery = new FunctionDefinition('selectAllPeople', [query])
            const namedQueriesDefinition = new NamedQueriesDefinition(structureId,
                                                                      entityService.structureNamespace,
                                                                      entityService.structureName,
                                                                      [namedQuery])
            const namedQueriesService = Structures.getNamedQueriesService()
            await namedQueriesService.save(namedQueriesDefinition)

            const allPeople = await entityService.namedQuery('selectAllPeople', [])

        }
    )

})
