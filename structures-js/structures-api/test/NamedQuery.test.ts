import {FunctionDefinition, LongC3Type, ObjectC3Type, ArrayC3Type} from '@kinotic/continuum-idl'
import {describe, it, expect, beforeAll, afterAll, beforeEach, afterEach} from 'vitest'
import { WebSocket } from 'ws'
import {PageableC3Type} from '../src/api/idl/PageableC3Type.js'
import {PageC3Type} from '../src/api/idl/PageC3Type.js'
import {
    createPersonStructureIfNotExist,
    createTestPeopleAndVerify, deleteStructure,
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

         const query = new QueryDecorator(`SELECT COUNT(firstName) as count FROM "struct_${structureId}"`)
         const namedQuery = new FunctionDefinition('countAllPeople', [query])
         namedQuery.returnType = new ArrayC3Type(new ObjectC3Type('PeopleCount', entityService.structureNamespace)
                                                    .addProperty("count", new LongC3Type()))

         const namedQueriesDefinition = new NamedQueriesDefinition(structureId,
                                                                   entityService.structureNamespace,
                                                                   entityService.structureName,
                                                                   [namedQuery])


         const namedQueriesService = Structures.getNamedQueriesService()
         await namedQueriesService.save(namedQueriesDefinition)

         const result: any[] = await entityService.namedQuery('countAllPeople', [])
         expect(result).toBeDefined()
         expect(result.length).toBe(1)
         const peopleCount = result[0]
         expect(peopleCount.count).toBe(100)
     }
    )

    it<LocalTestContext>('Select All Test',
     async ({entityService, structure}) => {
         // Create people
         await createTestPeopleAndVerify(entityService, 100, 2000)

         // Find all the people
         const page: Page<Person> = await entityService.findAll(Pageable.create(0, 10))
         expect(page).toBeDefined()
         expect(page.totalElements).toBe(100)
         expect(page.content.length).toBe(10)

         const structureId = entityService.structureId
         const query = new QueryDecorator(`SELECT * FROM "struct_${structureId}"`)
         const namedQuery = new FunctionDefinition('getAllPeople', [query])
         namedQuery.addParameter('pageable', new PageableC3Type())
         namedQuery.returnType = new PageC3Type(structure.entityDefinition)

         const namedQueriesDefinition = new NamedQueriesDefinition(structureId,
                                                                   entityService.structureNamespace,
                                                                   entityService.structureName,
                                                                   [namedQuery])


         const namedQueriesService = Structures.getNamedQueriesService()
         await namedQueriesService.save(namedQueriesDefinition)

         const pageable = Pageable.createWithCursor(null, 10)
         const personPage: Page<Person> = await entityService.namedQueryPage('getAllPeople',
                                                                             [],
                                                                             pageable)
     }
    )

})
