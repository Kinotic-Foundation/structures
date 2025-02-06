import {Page, Pageable} from '@kinotic/continuum-client'
import {IEntityService, Structure, Structures} from '@kinotic/structures-api'
import delay from 'delay'
import {afterAll, afterEach, beforeAll, beforeEach, describe, expect, it} from 'vitest'
import {WebSocket} from 'ws'
import {Person} from '../domain/Person.js'
import {Vehicle} from '../domain/Vehicle.js'
import {
    createTestPeople,
    createTestPeopleAndVerify,
    createTestVehicle, createTestVehicles,
    createVehicleStructureIfNotExist,
    deleteStructure,
    generateRandomString,
    initContinuumClient, logFailure,
    shutdownContinuumClient
} from '../TestHelpers.js'

Object.assign(global, { WebSocket})

interface LocalTestContext {
    structure: Structure
    entityService: IEntityService<Vehicle>
}

describe('VersionedTests', () => {

    beforeAll(async () => {
        await initContinuumClient()
    }, 300000)

    afterAll(async () => {
        await shutdownContinuumClient()
    }, 60000)

    beforeEach<LocalTestContext>(async (context) => {
        context.structure = await createVehicleStructureIfNotExist(generateRandomString(5))
        expect(context.structure).toBeDefined()
        context.entityService = Structures.createEntityService(context.structure.namespace, context.structure.name)
        expect(context.entityService).toBeDefined()
    })

    afterEach<LocalTestContext>(async (context) => {
        await expect(deleteStructure(context.structure.id as string)).resolves.toBeUndefined()
    })

    it<LocalTestContext>('Test Basic CRUD',
        async ({entityService}) => {
            const vehicle = createTestVehicle()
            let savedVehicle: Vehicle = await logFailure(entityService.save(vehicle), 'Failed to save vehicle')

            expect(savedVehicle).toBeDefined()
            expect(savedVehicle.id).toBeDefined()
            expect(savedVehicle.version).toBeDefined()

            // Update and save again
            savedVehicle.color = 'Grey'
            savedVehicle = await logFailure(entityService.update(savedVehicle), 'Failed to update vehicle')

            expect(savedVehicle).toBeDefined()
            expect(savedVehicle.id).toBeDefined()
            expect(savedVehicle.version).toBeDefined()

            const loaded = await logFailure(entityService.findById(savedVehicle.id), 'Failed to find vehicle')
            expect(savedVehicle.id).toEqual(loaded.id)
            expect(savedVehicle.version).toEqual(loaded.version)

            // Count
            await expect(entityService.count()).resolves.toBe(1)

            // Delete
            await expect(entityService.deleteById(loaded.id)).resolves.toBeNull()
        })

    it<LocalTestContext>('Test Optimistic Locking',
        async ({entityService}) => {
            const vehicle = createTestVehicle()
            let savedVehicle: Vehicle = await logFailure(entityService.save(vehicle), 'Failed to save vehicle')

            expect(savedVehicle).toBeDefined()
            expect(savedVehicle.id).toBeDefined()
            expect(savedVehicle.version).toBeDefined()

            savedVehicle.color = 'Grey'
            await logFailure(entityService.update(savedVehicle), 'Failed to update vehicle')

            // try and save same one again
            try {
                await entityService.update(savedVehicle)
            } catch (e: any) {
                expect(e.message).toEqual(expect.stringContaining('version conflict'))
            }

            await expect(entityService.deleteById(savedVehicle.id)).resolves.toBeNull()
        })

    it<LocalTestContext>('Test Bulk CRUD',
        async ({entityService}) => {
            // Create Vehicles
            const vehicles: Vehicle[] = createTestVehicles(100)
            await expect(entityService.bulkSave(vehicles)).resolves.toBeNull()

            await expect(entityService.syncIndex()).resolves.toBeDefined()

            await expect(entityService.count()).resolves.toBe(100)

            const page: Page<Vehicle> = await entityService.findAll(Pageable.create(0, 10))
            expect(page).toBeDefined()
            expect(page.totalElements).toBe(100)
            expect(page.content).toBeDefined()
            if(page.content) {
                expect(page.content.length).toBe(10)

                // Update the first 10 people
                for (let vehicle of page.content) {
                    expect(vehicle.version).toBeDefined()
                    vehicle.color = 'CLEAR'
                }

                await expect(entityService.bulkUpdate(page.content)).resolves.toBeNull()
            }
            await expect(entityService.syncIndex()).resolves.toBeDefined()

            // Search for all the people
            const searchPage: Page<Vehicle> = await entityService.search('color:CLEAR',Pageable.create(0, 10))
            expect(searchPage).toBeDefined()
            expect(searchPage.totalElements).toBe(10)
            expect(searchPage.content).toBeDefined()
            // @ts-ignore
            expect(searchPage.content.length).toBe(10)
        })

})