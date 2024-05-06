import {FunctionDefinition, LongC3Type, ObjectC3Type, StringC3Type} from '@kinotic/continuum-idl'
import {describe, it, expect, beforeAll, afterAll, beforeEach, afterEach} from 'vitest'
import { WebSocket } from 'ws'
import {
    createPersonStructureIfNotExist,
    createTestPeopleAndVerify,
    generateRandomString,
    initStructuresServer,
    shutdownStructuresServer,
} from './TestHelpers.js'
import {Person} from './domain/Person.js'
import {Page, Pageable} from '@kinotic/continuum-client'
import {IEntityService, Structures, Structure, QueryDecorator, NamedQueriesDefinition} from '../src'

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


    it<LocalTestContext>('Aggregate Test',
        async ({entityService}) => {
            // Create people
            await createTestPeopleAndVerify(entityService, 100, 2000)

            // Find all the people
            const page: Page<Person> = await entityService.findAll(Pageable.create(0, 10))
            expect(page).toBeDefined()
            expect(page.totalElements).toBe(100)
            expect(page.content.length).toBe(10)

            const structureId = entityService.structureId

            const returnType = new ObjectC3Type('CountByLastName',
                                                entityService.structureNamespace)
                .addProperty("count", new LongC3Type())
                .addProperty("lastName", new StringC3Type())
            const query = new QueryDecorator(`SELECT COUNT(firstName) as count FROM "struct_${structureId}"`)
            const namedQuery = new FunctionDefinition('countAllPeople', [query])
            namedQuery.addParameter('pageable', new ObjectC3Type('Pageable', 'org.kinotic'))
            namedQuery.returnType = returnType

            const namedQueriesDefinition = new NamedQueriesDefinition(structureId,
                                                                      entityService.structureNamespace,
                                                                      entityService.structureName,
                                                                      [namedQuery])


            const namedQueriesService = Structures.getNamedQueriesService()
            await namedQueriesService.save(namedQueriesDefinition)

            const pageable = Pageable.createWithCursor(null, 10)
            const parameters = [
                {key: 'pageable' , value:pageable}
            ]
            const allPeople = await entityService.namedQuery('countAllPeople', parameters)
            console.log(allPeople)
        }
    , 300000)

})
