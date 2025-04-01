import {faker} from '@faker-js/faker/locale/en'
import {CodeGenerationService} from '@kinotic/structures-cli/dist/internal/CodeGenerationService.js'
import {ConsoleLogger} from '@kinotic/structures-cli/dist/internal/Logger.js'
import {NamespaceConfiguration} from '@kinotic/structures-cli/dist/internal/state/StructuresProject.js'
import {Continuum, Direction, Order, Pageable} from '@kinotic/continuum-client'
import {
    ObjectC3Type,
    FunctionDefinition
} from '@kinotic/continuum-idl'
import {randomUUID} from 'node:crypto'
import {expect} from 'vitest'
import {IterablePage} from '@kinotic/continuum-client'
import {
    Structures,
    Structure,
    IEntityService,
    IAdminEntityService,
    NamedQueriesDefinition,
    QueryDecorator
} from '@kinotic/structures-api'
import {Alert} from './domain/Alert.js'
import {Person} from './domain/Person.js'
import {inject} from 'vitest'
// @ts-ignore
import path from 'path'
import {PersonWithTenant} from './domain/PersonWithTenant.js'
import {Cat, Dog} from './domain/Pet.js'
import {Vehicle, Wheel} from './domain/Vehicle.js'


type SchemaCreationResult ={
    entityDefinition: ObjectC3Type
    namedQueriesDefinition: NamedQueriesDefinition
}
let schemas: Map<string, SchemaCreationResult> = new Map<string, SchemaCreationResult>()

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

export async function createPersonSchema(namespace: string, withTenant: boolean = false): Promise<SchemaCreationResult> {
    return createSchema(namespace, 'Person'+(withTenant ? 'WithTenant' : ''))
}

export async function createVehicleSchema(namespace: string): Promise<SchemaCreationResult> {
    return createSchema(namespace, 'Vehicle')
}

export async function createSchema(namespace: string, entityName: string): Promise<SchemaCreationResult> {
    if(!schemas.has(entityName)){
        const codeGenerationService = new CodeGenerationService(namespace,
                                                                '.js',
                                                                new ConsoleLogger())
        const namespaceConfig: NamespaceConfiguration = new NamespaceConfiguration()
        namespaceConfig.namespaceName = namespace
        namespaceConfig.validate = false
        namespaceConfig.entitiesPaths = [path.resolve(__dirname, './domain')]
        namespaceConfig.generatedPath = path.resolve(__dirname, './services')
        await codeGenerationService
            .generateAllEntities(namespaceConfig,
                                 false,
                                 async (entityInfo, serviceInfos) =>{
                                    // combine named queries from generated services
                                     const namedQueries: FunctionDefinition[] = []
                                     for(let serviceInfo of serviceInfos){
                                            namedQueries.push(...serviceInfo.namedQueries)
                                     }
                                     const id = (namespace + '.' + entityName).toLowerCase()
                                    const result: SchemaCreationResult = {
                                        entityDefinition: entityInfo.entity,
                                        namedQueriesDefinition: new NamedQueriesDefinition(id,
                                                                                           namespace,
                                                                                           entityName,
                                                                                           namedQueries)
                                    }
                                     schemas.set(entityInfo.entity.name, result)
                                 })
    }
    const result = schemas.get(entityName)
    if(!result){
        throw new Error('Could not find Entity ' + entityName)
    }
    const ret = structuredClone(result)
    if(!ret){
        throw new Error('Could not copy schema for ' + entityName)
    }

    ret.entityDefinition.name = entityName
    ret.namedQueriesDefinition.id = (namespace + '.' + entityName).toLowerCase()
    ret.namedQueriesDefinition.structure = entityName
    replaceAllQueryPlaceholdersWithId(namespace + '.' + entityName, ret.namedQueriesDefinition.namedQueries)
    return ret
}

/**
 * This replaces the PLACEHOLDER string in all @Query decorators applied to the given function definitions
 * @param structureId to replace the PLACEHOLDER with
 * @param functionDefinitions all of the {@link FunctionDefinition}s to replace the PLACEHOLDER in
 */
function replaceAllQueryPlaceholdersWithId(structureId: string, functionDefinitions: FunctionDefinition[]){
    for(const functionDefinition of functionDefinitions){
        if(functionDefinition.decorators) {
            for (const decorator of functionDefinition.decorators) {
                if (decorator.type === 'Query') {
                    const queryDecorator = decorator as QueryDecorator
                    // @ts-ignore stupid intellij error for replaceAll
                    queryDecorator.statements = queryDecorator.statements.replaceAll('PLACEHOLDER', structureId.toLowerCase())
                }
            }
        }
    }
}

// Add these new functions to your existing TestHelpers.ts file

export async function createAlertStructureIfNotExist(namespace: string): Promise<Structure> {
    const structureId = namespace + '.alert'
    let structure = await Structures.getStructureService().findById(structureId)
    if (structure == null) {
        structure = await createAlertStructure(namespace)
    }
    return structure
}

export async function createAlertStructure(namespace: string): Promise<Structure> {
    const {entityDefinition} = await createAlertSchema(namespace)
    const alertStructure = new Structure(
        namespace,
        'Alert',
        entityDefinition,
        'System alerts and notifications stream'
    )

    await Structures.getNamespaceService().createNamespaceIfNotExist(namespace, 'Sample Data Namespace')

    const savedStructure = await Structures.getStructureService().create(alertStructure)

    if (savedStructure.id) {
        await Structures.getStructureService().publish(savedStructure.id)
    } else {
        throw new Error('No Structure id')
    }

    return savedStructure
}

export async function createAlertSchema(namespace: string): Promise<SchemaCreationResult> {
    return createSchema(namespace, 'Alert')
}

// Add this helper function to create test Alert instances
export function createTestAlert(options: Partial<Alert> & { index?: number } = {}): Alert {
    const index = options.index ?? 0
    const ret = new Alert()
    ret.alertId = options.alertId ?? `alert-${index.toString().padStart(3, '0')}`
    ret.message = options.message ?? faker.lorem.sentence()
    ret.severity = options.severity ?? (index % 3 === 0 ? 'LOW' : index % 3 === 1 ? 'MEDIUM' : 'HIGH')
    ret.source = options.source ?? faker.internet.domainName()
    ret.timestamp = options.timestamp ?? new Date(Date.now() - (index * 1000))
    ret.active = options.active ?? (index % 2 === 0)
    return ret
}

// Add this helper function to create multiple test Alerts
export function createTestAlerts(numberToCreate: number): Alert[] {
    const ret: Alert[] = []
    for (let i = 0; i < numberToCreate; i++) {
        ret.push(createTestAlert({index: i}))
    }
    return ret
}

export async function createPersonStructureIfNotExist(namespace: string, withTenant: boolean = false): Promise<Structure>{
    const structureId = namespace + '.person' + ( withTenant ? 'withtenant' : '')
    let structure = await Structures.getStructureService().findById(structureId)
    if(structure == null){
        structure = await createPersonStructure(namespace, withTenant)
    }
    return structure
}

export async function createPersonStructure(namespace: string, withTenant: boolean = false): Promise<Structure>{
    const {entityDefinition} = await createPersonSchema(namespace, withTenant)
    const personStructure = new Structure(namespace,
                                          'Person' + (withTenant ? 'WithTenant' : ''),
                                          entityDefinition,
                                          'Tracks people that are going to mars')

    await Structures.getNamespaceService().createNamespaceIfNotExist(namespace, 'Sample Data Namespace')

    const savedStructure = await Structures.getStructureService().create(personStructure)

    if(savedStructure.id) {
        await Structures.getStructureService().publish(savedStructure.id)
    }else{
        throw new Error('No Structure id')
    }

    return savedStructure
}

export async function createVehicleStructureIfNotExist(namespace: string): Promise<Structure>{
    const structureId = namespace + '.vehicle'
    let structure = await Structures.getStructureService().findById(structureId)
    if(structure == null){
        structure = await createVehicleStructure(namespace)
    }
    return structure
}

export async function createVehicleStructure(namespace: string): Promise<Structure>{
    const {entityDefinition} = await createVehicleSchema(namespace)
    const vehicleStructure = new Structure(namespace,
                                           'Vehicle',
                                           entityDefinition,
                                           'Some form of transportation')

    await Structures.getNamespaceService().createNamespaceIfNotExist(namespace, 'Sample Data Namespace')

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
                                                numberToCreate: number): Promise<void> {
    // Create people
    const people: Person[] = createTestPeople(numberToCreate)
    await expect(entityService.bulkSave(people)).resolves.toBeNull()
    await expect(entityService.syncIndex()).resolves.toBeNull()

    // Count the people
    await expect(entityService.count()).resolves.toBe(numberToCreate)
}

export function createTestPeopleWithTenant(numberToCreate: number, tenantId: string): PersonWithTenant[] {
    const ret: PersonWithTenant[] = []
    for (let i = 0; i < numberToCreate; i++) {
        ret.push(createTestPersonWithTenant(i, tenantId))
    }
    return ret
}

export async function createTestPeopleWithTenantAndVerify(adminEntityService: IAdminEntityService<PersonWithTenant>,
                                                          entityService: IEntityService<PersonWithTenant>,
                                                          tenantId: string,
                                                          numberToCreate: number): Promise<void> {
    // Create people
    const people: PersonWithTenant[] = createTestPeopleWithTenant(numberToCreate, tenantId)
    await expect(entityService.bulkSave(people)).resolves.toBeNull()
    await expect(entityService.syncIndex()).resolves.toBeNull()

    // Count the people
    await expect(adminEntityService.count([tenantId])).resolves.toBe(numberToCreate)
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

export function createTestPersonWithTenant(index: number = 0, tenantId: string): PersonWithTenant {
    let ret: PersonWithTenant = new PersonWithTenant()
    addDataToPerson(index, ret)
    ret.tenantId = tenantId
    return ret
}

export function createTestPerson(index: number = 0): Person {
    let ret: Person = new Person()
    addDataToPerson(index, ret)
    return ret
}

function addDataToPerson(index: number = 0, person: Person | PersonWithTenant){
    if(index % 2 === 0){
        person.firstName = 'John'
        person.lastName = 'Doe'
        person.myPet = new Cat()
        person.myPet.age = 4
        person.myPet.name = 'Fluffy'
    }else{
        person.firstName = 'Steve'
        person.lastName = 'Wozniak'
        person.myPet = new Dog()
        person.myPet.age = 10
        person.myPet.name = 'Zapato'
    }
    person.age = 42
    person.address = {
        street: '123 Main St',
        city: 'Anytown',
        state: 'CA',
        zip: '12345'
    }
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
              'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
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
