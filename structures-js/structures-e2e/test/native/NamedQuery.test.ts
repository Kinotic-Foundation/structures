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

describe('NamedQueryTest', () => {

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


    it<LocalTestContext>('Aggregate Test',
     async ({entityService}) => {
         // Create people
         await createTestPeopleAndVerify(entityService, 100)

         // Find all the people
         const page: Page<Person> = await entityService.findAll(Pageable.create(0, 10))
         expect(page).toBeDefined()
         expect(page.totalElements).toBe(100)
         expect(page.content?.length).toBe(10)

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

         const countResult: any = await entityService.namedQuery('countAllPeople', [])
         expect(countResult).toBeDefined()
         expect(countResult).toHaveLength(1)
         expect(countResult[0]).toBeDefined()
         expect(countResult[0].count).toBe(100)
     }
    )

    it<LocalTestContext>('Aggregate With Parameter Test',
         async ({entityService}) => {
             // Create people
             await createTestPeopleAndVerify(entityService, 100)

             // Find all the people
             const page: Page<Person> = await entityService.findAll(Pageable.create(0, 10))
             expect(page).toBeDefined()
             expect(page.totalElements).toBe(100)
             expect(page.content?.length).toBe(10)

             const structureId = entityService.structureId

             const query = new QueryDecorator(`SELECT COUNT(firstName) as count, lastName FROM "struct_${structureId}" WHERE lastName = ? GROUP BY lastName`)
             const namedQuery = new FunctionDefinition('countPeopleByLastNameWithLastName', [query])
             namedQuery.addParameter('lastName', new StringC3Type())
             const contentType = new ObjectC3Type('CountByLastName', entityService.structureNamespace)
                 .addProperty("count", new LongC3Type())
                 .addProperty("lastName", new StringC3Type())
             namedQuery.returnType = new ArrayC3Type(contentType)

             const namedQueriesDefinition = new NamedQueriesDefinition(structureId,
                                                                       entityService.structureNamespace,
                                                                       entityService.structureName,
                                                                       [namedQuery])


             const namedQueriesService = Structures.getNamedQueriesService()
             await namedQueriesService.save(namedQueriesDefinition)

             const countResult: any = await entityService.namedQuery('countPeopleByLastNameWithLastName',
                                                                                 [{key: 'lastName', value: 'Doe'}])

             expect(countResult).toBeDefined()
             expect(countResult).toHaveLength(1)
             expect(countResult[0]).toBeDefined()
             expect(countResult[0].count).toBe(50)
         }
    )

    it<LocalTestContext>('Aggregate Pageable Test',
     async ({entityService}) => {
         // Create people
         await createTestPeopleAndVerify(entityService, 100)

         // Find all the people
         const page: Page<Person> = await entityService.findAll(Pageable.create(0, 10))
         expect(page).toBeDefined()
         expect(page.totalElements).toBe(100)
         expect(page.content?.length).toBe(10)

         const structureId = entityService.structureId
         const query = new QueryDecorator(`SELECT COUNT(firstName) as count, lastName FROM "struct_${structureId}" GROUP BY lastName`)
         const namedQuery = new FunctionDefinition('countPeopleByLastNamePage', [query])
         namedQuery.addParameter('pageable', new PageableC3Type())
         const contentType = new ObjectC3Type('CountByLastName', entityService.structureNamespace)
                                               .addProperty("count", new LongC3Type())
                                               .addProperty("lastName", new StringC3Type())
         namedQuery.returnType = new PageC3Type(contentType)

         const namedQueriesDefinition = new NamedQueriesDefinition(structureId,
                                                                   entityService.structureNamespace,
                                                                   entityService.structureName,
                                                                   [namedQuery])


         const namedQueriesService = Structures.getNamedQueriesService()
         await namedQueriesService.save(namedQueriesDefinition)

         const pageable = Pageable.createWithCursor(null, 1)
         const personPage: Page<Person> = await entityService.namedQueryPage('countPeopleByLastNamePage',
                                                                             [],
                                                                             pageable)
         expect(personPage.cursor).toBeDefined()
         expect(personPage.content).toHaveLength(1)

         const personPage2: Page<Person> = await entityService.namedQueryPage('countPeopleByLastNamePage',
                                                                             [],
                                                                              Pageable.createWithCursor(personPage.cursor as string, 1))
         expect(personPage2.cursor).toBeDefined()
         expect(personPage2.content).toHaveLength(1)

         const personPage3: Page<Person> = await entityService.namedQueryPage('countPeopleByLastNamePage',
                                                                              [],
                                                                              Pageable.createWithCursor(personPage2.cursor as string, 1))
         expect(personPage3.cursor).toBeNull()
         expect(personPage3.content).toHaveLength(0)
     }
    )

    it<LocalTestContext>('Test Save Multiple',
         async ({entityService}) => {
             const structureId = entityService.structureId
             const namedQueriesService = Structures.getNamedQueriesService()

             const query = new QueryDecorator(`SELECT COUNT(firstName) as count FROM "struct_${structureId}"`)
             const namedQuery = new FunctionDefinition('countAllPeople', [query])
             namedQuery.returnType = new ArrayC3Type(new ObjectC3Type('PeopleCount', entityService.structureNamespace)
                                                         .addProperty("count", new LongC3Type()))


             const query2 = new QueryDecorator(`SELECT COUNT(firstName) as count, lastName FROM "struct_${structureId}" WHERE lastName = ? GROUP BY lastName`)
             const namedQuery2 = new FunctionDefinition('countPeopleByLastNameWithLastName', [query2])
             namedQuery2.addParameter('lastName', new StringC3Type())
             const contentType2 = new ObjectC3Type('CountByLastName', entityService.structureNamespace)
                 .addProperty("count", new LongC3Type())
                 .addProperty("lastName", new StringC3Type())
             namedQuery2.returnType = new ArrayC3Type(contentType2)


             const query3 = new QueryDecorator(`SELECT COUNT(firstName) as count, lastName FROM "struct_${structureId}" GROUP BY lastName`)
             const namedQuery3 = new FunctionDefinition('countPeopleByLastNamePage', [query3])
             namedQuery3.addParameter('pageable', new PageableC3Type())
             const contentType3 = new ObjectC3Type('CountByLastName', entityService.structureNamespace)
                 .addProperty("count", new LongC3Type())
                 .addProperty("lastName", new StringC3Type())
             namedQuery3.returnType = new PageC3Type(contentType3)

             // Save the named queries
             const namedQueriesDefinition = new NamedQueriesDefinition(structureId,
                                                                        entityService.structureNamespace,
                                                                        entityService.structureName,
                                                                        [namedQuery, namedQuery2, namedQuery3])
             await namedQueriesService.save(namedQueriesDefinition)
         }
    )


})
