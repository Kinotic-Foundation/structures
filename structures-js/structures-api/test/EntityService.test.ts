import {describe, it, expect, beforeAll, afterAll, beforeEach, afterEach} from 'vitest'
import { WebSocket } from 'ws'
import {
    createPersonStructureIfNotExist,
    createTestPeople,
    createTestPerson,
    deleteStructure,
    generateRandomString,
    initStructuresServer,
    shutdownStructuresServer,
    logFailure
} from './TestHelpers.js'
import {Person} from './domain/Person.js'
import {Page, Pageable, Order, Direction} from '@kinotic/continuum-client'
import {IEntityService, Structures, Structure} from '../src'
import delay from 'delay'

Object.assign(global, { WebSocket})

interface LocalTestContext {
    structure: Structure
    entityService: IEntityService<Person>
}

describe('EntityServiceTest', () => {

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
        await expect(deleteStructure(context.structure.id)).resolves.toBeUndefined()
    })

    it<LocalTestContext>('Test Basic CRUD',
        async ({entityService}) => {
            // Create a person
            const person = createTestPerson()
            const savedPerson: Person = await logFailure(entityService.save(person), 'Failed to save person')

            expect(savedPerson).toBeDefined()
            expect(savedPerson.id).toBeDefined()

            // Find the person
            const foundPerson: Person = await logFailure(entityService.findById(savedPerson.id), 'Failed to find person')
            expect(foundPerson).toBeDefined()
            expect(foundPerson.id).toBe(savedPerson.id)

            // Update the person
            foundPerson.firstName = 'Walter'
            foundPerson.lastName = 'White'
            const updatedPerson: Person = await logFailure(entityService.update(foundPerson), 'Failed to update person')
            expect(updatedPerson).toBeDefined()
            expect(updatedPerson.id).toBe(foundPerson.id)

            // Find the updated person
            const foundUpdatedPerson: Person = await logFailure(entityService.findById(updatedPerson.id), 'Failed to find updated person')
            expect(foundUpdatedPerson).toBeDefined()
            expect(foundUpdatedPerson.id).toBe(updatedPerson.id)
            expect(foundUpdatedPerson.firstName).toBe('Walter')
            expect(foundUpdatedPerson.lastName).toBe('White')

            // Count the people
            await expect(entityService.count()).resolves.toBe(1)

            // Delete the person
            await expect(entityService.deleteById(savedPerson.id)).resolves.toBeNull()
        }
    )

    it<LocalTestContext>('Test Cursor Based Paging',
         async ({entityService}) => {
             // Create people
             const people: Person[] = createTestPeople(100)
             await expect(entityService.bulkSave(people)).resolves.toBeNull()

             await delay(2000)

             // Count the people
             await expect(entityService.count()).resolves.toBe(100)

             // Find all the people
             let cursor: string | null = null
             let done = false
             let elementsFound = 0
             while(!done) {
                 const pageable = Pageable.createWithCursor(cursor,
                                                            10,
                                                            { orders: [
                                                                new Order('firstName', Direction.ASC),
                                                                new Order('id', Direction.ASC)
                                                                ] })
                 const page: Page<Person> = await entityService.findAll(pageable)
                 expect(page).toBeDefined()
                 if(page.cursor) {
                     cursor = page.cursor
                     expect(page.content.length).toBe(10)
                     elementsFound += page.content.length
                 }else{
                     done = true
                 }
             }
             expect(elementsFound, 'Should have found 100 Entities').toBe(100)
         }
    )

    // FIXME: This does not persist the bulkUpdate to elastic search. I am uncertain why when other bulk updates are working.
    it<LocalTestContext>('Test Bulk CRUD',
        async ({entityService}) => {
            // Create people
            const people: Person[] = createTestPeople(100)
            await expect(entityService.bulkSave(people)).resolves.toBeNull()

            await delay(2000)

            // Count the people
            await expect(entityService.count()).resolves.toBe(100)

            // Find all the people
            const page: Page<Person> = await entityService.findAll(Pageable.create(0, 10))
            expect(page).toBeDefined()
            expect(page.totalElements).toBe(100)
            expect(page.content.length).toBe(10)

            // Update the first 10 people
            for (let person of page.content){
                person.firstName = 'Walter'
                person.lastName = 'White'
                // We do this to ensure the update performs a partial update properly
               // delete person.address
            }

            await expect(entityService.bulkUpdate(page.content)).resolves.toBeNull()

            await delay(2000)

            // Search for all the people
            const searchPage: Page<Person> = await entityService.search('firstName:Walter',Pageable.create(0, 10))
            expect(searchPage).toBeDefined()
            expect(searchPage.totalElements).toBe(10)
            expect(searchPage.content.length).toBe(10)

            // ensure all the people still have an address
            for (let person of searchPage.content){
                expect(person.address).toBeDefined()
            }
        }
    )

})
