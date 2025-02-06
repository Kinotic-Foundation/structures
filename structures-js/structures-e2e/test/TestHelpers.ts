import {faker} from '@faker-js/faker/locale/en'
import {CodeGenerationService} from '@kinotic/structures-cli/dist/internal/CodeGenerationService.js'
import {ConsoleLogger} from '@kinotic/structures-cli/dist/internal/Logger.js'
import {NamespaceConfiguration} from '@kinotic/structures-cli/dist/internal/state/StructuresProject.js'
import delay from 'delay'
import {Continuum, Direction, Order, Pageable} from '@kinotic/continuum-client'
import {
    ObjectC3Type
} from '@kinotic/continuum-idl'
import {randomUUID} from 'node:crypto'
import {expect} from 'vitest'
import {IterablePage} from '@kinotic/continuum-client'
import {
    Structures,
    Structure,
    IEntityService
} from '@kinotic/structures-api'
import {Person} from './domain/Person.js'
import {inject} from 'vitest'
import path from 'path'
import {Cat, Dog} from './domain/Pet.js'
import {Vehicle, Wheel} from './domain/Vehicle.js'

let schemas: Map<string, ObjectC3Type> = new Map<string, ObjectC3Type>()

export async function initContinuumClient(): Promise<void> {
    try {
        // @ts-ignore
        const host = inject('STRUCTURES_HOST')
        // @ts-ignore
        const port = inject('STRUCTURES_PORT')

        console.log('Connecting to continuum at ' + host)

        await Continuum.connect({
                                    host:host,
                                    port:port,
                                    connectHeaders:{login: 'admin', passcode: 'structures'}
                                })

        console.log('Connected to continuum')
    } catch (e) {
        console.error(e)
        throw e
    }
}

export async function shutdownContinuumClient(): Promise<void> {
    try {
        await Continuum.disconnect()
    } catch (e) {
        console.error(e)
        throw e
    }
}

export async function createPersonSchema(suffix: string): Promise<ObjectC3Type> {
    return createSchema(suffix, 'Person')
}

export async function createVehicleSchema(suffix: string): Promise<ObjectC3Type> {
    return createSchema(suffix, 'Vehicle')
}

export async function createSchema(suffix: string, entityName: string): Promise<ObjectC3Type> {
    if(!schemas.has(entityName)){
        const codeGenerationService = new CodeGenerationService('structures.api.tests',
                                                                '.js',
                                                                new ConsoleLogger())
        const namespaceConfig: NamespaceConfiguration = new NamespaceConfiguration()
        namespaceConfig.namespaceName = 'structures.api.tests'
        namespaceConfig.validate = false
        namespaceConfig.entitiesPaths = [path.resolve(__dirname, './domain')]
        namespaceConfig.generatedPath = path.resolve(__dirname, './services')
        await codeGenerationService
            .generateAllEntities(namespaceConfig,
                                 false,
                                 async (entityInfo) =>{
                                     schemas.set(entityInfo.entity.name, entityInfo.entity)
                                 })
    }
    const ret = structuredClone(schemas.get(entityName))
    if(ret) {
        ret.name = entityName+suffix
    }else{
        throw new Error('Could not copy schema')
    }
    return ret
}

export async function createPersonStructureIfNotExist(suffix: string): Promise<Structure>{
    const structureId = 'structures.api.tests.person' + suffix
    let structure = await Structures.getStructureService().findById(structureId)
    if(structure == null){
        structure = await createPersonStructure(suffix)
    }
    return structure
}

export async function createPersonStructure(suffix: string): Promise<Structure>{
    const schema: ObjectC3Type = await createPersonSchema(suffix)
    const personStructure = new Structure('structures.api.tests',
                                          'Person' + suffix,
                                          schema,
                                          'Tracks people that are going to mars')

    await Structures.getNamespaceService().createNamespaceIfNotExist('structures.api.tests', 'Sample Data Namespace')

    const savedStructure = await Structures.getStructureService().create(personStructure)

    if(savedStructure.id) {
        await Structures.getStructureService().publish(savedStructure.id)
    }else{
        throw new Error('No Structure id')
    }

    return savedStructure
}

export async function createVehicleStructureIfNotExist(suffix: string): Promise<Structure>{
    const structureId = 'structures.api.tests.vehicle' + suffix
    let structure = await Structures.getStructureService().findById(structureId)
    if(structure == null){
        structure = await createVehicleStructure(suffix)
    }
    return structure
}

export async function createVehicleStructure(suffix: string): Promise<Structure>{
    const schema: ObjectC3Type = await createVehicleSchema(suffix)
    const vehicleStructure = new Structure('structures.api.tests',
                                          'Vehicle' + suffix,
                                          schema,
                                          'Some form of transportation')

    await Structures.getNamespaceService().createNamespaceIfNotExist('structures.api.tests', 'Sample Data Namespace')

    const savedStructure = await Structures.getStructureService().create(vehicleStructure)

    if(savedStructure.id) {
        await Structures.getStructureService().publish(savedStructure.id)
    }else{
        throw new Error('No Structure id')
    }

    return savedStructure
}


export async function deleteStructure(structureId: string): Promise<void>{
    await Structures.getStructureService().unPublish(structureId)
    await Structures.getStructureService().deleteById(structureId)
}

export function createTestPeople(numberToCreate: number): Person[] {
    const ret: Person[] = []
    for (let i = 0; i < numberToCreate; i++) {
        ret.push(createTestPerson(i))
    }
    return ret
}

export async function createTestPeopleAndVerify(entityService: IEntityService<Person>,
                                                numberToCreate: number,
                                                delayAfterUpdate: number): Promise<void> {
    // Create people
    const people: Person[] = createTestPeople(numberToCreate)
    await expect(entityService.bulkSave(people)).resolves.toBeNull()

    await delay(delayAfterUpdate)

    // Count the people
    await expect(entityService.count()).resolves.toBe(numberToCreate)
}

export async function findAndVerifyPeopleWithCursorPaging(entityService: IEntityService<Person>,
                                                          numberToExpect: number){
    let elementsFound = 0
    const pageable = Pageable.createWithCursor(null,
                                               10,
                                               { orders: [
                                                       new Order('firstName', Direction.ASC),
                                                       new Order('id', Direction.ASC)
                                                   ] })
    const firstPage: IterablePage<Person> = await entityService.findAll(pageable)
    expect(firstPage).toBeDefined()
    for await(const page of firstPage){
        // @ts-ignore
        elementsFound += page.content.length
    }
    expect(elementsFound, `Should have found ${numberToExpect} Entities`).toBe(numberToExpect)
}

export async function findAndVerifyPeopleWithOffsetPaging(entityService: IEntityService<Person>,
                                                          numberToExpect: number){
    let elementsFound = 0
    const pageable = Pageable.create(0,
                                     10,
                                     { orders: [
                                             new Order('firstName', Direction.ASC),
                                             new Order('id', Direction.ASC)
                                         ] })
    const firstPage: IterablePage<Person> = await entityService.findAll(pageable)
    expect(firstPage).toBeDefined()
    for await(const page of firstPage){
        // @ts-ignore
        elementsFound += page.content.length
    }
    expect(elementsFound, `Should have found ${numberToExpect} Entities`).toBe(numberToExpect)
}

export function createTestPerson(index: number = 0): Person {
    const ret = new Person()
    if(index % 2 === 0){
        ret.firstName = 'John'
        ret.lastName = 'Doe'
        ret.myPet = new Cat()
        ret.myPet.age = 4
        ret.myPet.name = 'Fluffy'
    }else{
        ret.firstName = 'Steve'
        ret.lastName = 'Wozniak'
        ret.myPet = new Dog()
        ret.myPet.age = 10
        ret.myPet.name = 'Zapato'
    }
    ret.age = 42
    ret.address = {
        street: '123 Main St',
        city: 'Anytown',
        state: 'CA',
        zip: '12345'
    }
    return ret
}

export function createTestVehicles(numberToCreate: number): Vehicle[] {
    const ret: Vehicle[] = []
    for (let i = 0; i < numberToCreate; i++) {
        ret.push(createTestVehicle())
    }
    return ret
}

export function createTestVehicle(): Vehicle {
    const ret = new Vehicle();
    ret.id = randomUUID()
    ret.manufacturer = faker.vehicle.manufacturer()
    ret.model = faker.vehicle.model()
    ret.color = faker.vehicle.color()
    ret.wheelType = new Wheel()
    ret.wheelType.brand = 'BFG'
    ret.wheelType.size = 35
    return ret
}

export function generateRandomString(length: number){
    let result = ''
    const characters =
              'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
    const charactersLength = characters.length
    for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength))
    }
    return result
}

/**
 * Logs the failure of a promise and then rethrows the error
 * @param promise to log failure of
 * @param message to log
 */
export async function logFailure<T>(promise: Promise<T>, message: string): Promise<T> {
    try {
        return await promise
    } catch (e) {
        console.error(message, e)
        throw e
    }
}
