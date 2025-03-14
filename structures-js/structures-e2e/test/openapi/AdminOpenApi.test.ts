import axios from 'axios'
import { faker } from '@faker-js/faker'
import { Structure, Structures } from '@kinotic/structures-api'
import * as allure from 'allure-js-commons'
import { afterAll, beforeAll, describe, expect, inject, it } from 'vitest'
import { createPersonStructureIfNotExist, createSchema, initContinuumClient, shutdownContinuumClient } from '../TestHelpers.js'
import { loadOpenAPISchema } from './OpenApiHelpers.js'
import { PersonWithTenant } from '../domain/PersonWithTenant.js'
import { Address } from '../domain/Address.js'
import { Pet } from '../domain/Pet.js'

interface LocalTestContext {
    personWithTenantStructure: Structure
}

const namespace = 'openapi.admin'
const BASE_AUTH = 'Basic YWRtaW46c3RydWN0dXJlcw=='
const DEFAULT_TENANT = 'kinotic'

const axiosInstance = axios.create({
                                       headers: {
                                           'Authorization': BASE_AUTH,
                                           'Content-Type': 'application/json'
                                       }
                                   })

const generatePerson = (tenantId: string): PersonWithTenant => {
    const person = new PersonWithTenant()
    person.firstName = faker.person.firstName()
    person.lastName = faker.person.lastName()
    person.age = faker.number.int({ min: 18, max: 80 })
    person.address = Object.assign(new Address(), {
        street: faker.location.streetAddress(),
        city: faker.location.city(),
        state: faker.location.state({ abbreviated: true }),
        zip: faker.location.zipCode()
    })
    person.myPet = {
        type: faker.helpers.arrayElement(['Dog', 'Cat']),
        name: faker.animal[faker.helpers.arrayElement(['dog', 'cat'])](),
        age: faker.number.int({ min: 1, max: 15 })
    } as Pet
    person.tenantId = tenantId
    return person
}

describe('Admin OpenApi Tests', () => {
    let context: LocalTestContext = { personWithTenantStructure: null! }
    let baseUrl: string

    beforeAll(async () => {
        await allure.parentSuite('End To End Tests')
        await initContinuumClient()

        context.personWithTenantStructure = await createPersonStructureIfNotExist(namespace, true)
        expect(context.personWithTenantStructure).toBeDefined()

        const { namedQueriesDefinition } = await createSchema(namespace, 'PersonWithTenant')
        const namedQueriesService = Structures.getNamedQueriesService()
        await namedQueriesService.save(namedQueriesDefinition)

        // @ts-ignore
        const host = inject('STRUCTURES_HOST') as string
        // @ts-ignore
        const port = inject('STRUCTURES_OPENAPI_PORT') as string
        baseUrl = `http://${host}:${port}`
    }, 300000)

    afterAll(async () => {
        await shutdownContinuumClient()
    }, 60000)

    it('OpenApi Schema loads', async () => {
        const schemaUrl = `${baseUrl}/api-docs/openapi.admin/openapi.json`
        const schema = await loadOpenAPISchema(schemaUrl)

        expect(schema).toBeDefined()
        expect(schema.openapi).toBe('3.0.1')
        expect(schema.info?.title).toBe('openapi.admin Structures API')
    })

    describe('PersonWithTenant CRUD Operations', () => {
        it('Can create a person', async () => {
            const testPerson = generatePerson(DEFAULT_TENANT)
            const response = await axiosInstance.post<PersonWithTenant>(
                `${baseUrl}/api/openapi.admin/personwithtenant`,
                testPerson
            )

            expect(response.status).toBe(200)
            expect(response.data.id).toBeDefined()
            expect(response.data.firstName).toBe(testPerson.firstName)
            expect(response.data.tenantId).toBe(DEFAULT_TENANT)
        })

        it('Can get person by ID', async () => {
            // Create a person first
            const testPerson = generatePerson(DEFAULT_TENANT)
            const createResponse = await axiosInstance.post<PersonWithTenant>(
                `${baseUrl}/api/openapi.admin/personwithtenant`,
                testPerson
            )
            const createdId = createResponse.data.id!

            const response = await axiosInstance.get<PersonWithTenant>(
                `${baseUrl}/api/openapi.admin/personwithtenant/${createdId}`
            )

            expect(response.status).toBe(200)
            expect(response.data.id).toBe(createdId)
            expect(response.data.firstName).toBe(testPerson.firstName)
            expect(response.data.lastName).toBe(testPerson.lastName)
        })

        it('Can search for person', async () => {
            // Create a person to search for
            const testPerson = generatePerson(DEFAULT_TENANT)
            await axiosInstance.post<PersonWithTenant>(
                `${baseUrl}/api/openapi.admin/personwithtenant`,
                testPerson
            )

            const searchResponse = await axiosInstance.post<{
                content: PersonWithTenant[];
                totalElements: number;
                cursor?: string;
            }>(
                `${baseUrl}/api/openapi.admin/personwithtenant/search`,
                `firstName:${testPerson.firstName}`,
                {
                    params: {
                        size: 10,
                        sort: 'lastName'
                    }
                }
            )

            expect(searchResponse.status).toBe(200)
            expect(searchResponse.data.content.length).toBeGreaterThan(0)
            expect(searchResponse.data.content.some(
                p => p.firstName === testPerson.firstName && p.lastName === testPerson.lastName
            )).toBe(true)
        })

        it('Can count persons', async () => {
            // Ensure there's at least one person
            const testPerson = generatePerson(DEFAULT_TENANT)
            await axiosInstance.post<PersonWithTenant>(
                `${baseUrl}/api/openapi.admin/personwithtenant`,
                testPerson
            )

            const countResponse = await axiosInstance.get<{ count: number }>(
                `${baseUrl}/api/openapi.admin/personwithtenant/count/all`
            )

            expect(countResponse.status).toBe(200)
            expect(countResponse.data.count).toBeGreaterThan(0)
        })

        it('Can delete person', async () => {
            // Create a person to delete
            const testPerson = generatePerson(DEFAULT_TENANT)
            const createResponse = await axiosInstance.post<PersonWithTenant>(
                `${baseUrl}/api/openapi.admin/personwithtenant`,
                testPerson
            )
            const createdId = createResponse.data.id!

            const deleteResponse = await axiosInstance.delete(
                `${baseUrl}/api/openapi.admin/personwithtenant/${createdId}`
            )

            expect(deleteResponse.status).toBe(200)

            await expect(axiosInstance.get(
                `${baseUrl}/api/openapi.admin/personwithtenant/${createdId}`
            )).rejects.toHaveProperty('response.status', 404)
        })
    })

    describe('Multi-Tenant Selection Tests', () => {
        const tenantA = 'tenantA'
        const tenantB = 'tenantB'
        const tenantC = 'tenantC'
        const tenants = [tenantA, tenantB, tenantC]

        it('Can bulk save people across multiple tenants and retrieve them selectively', async () => {
            // Generate 12 people, 4 per tenant
            const people: PersonWithTenant[] = []
            tenants.forEach(tenant => {
                for (let i = 0; i < 4; i++) {
                    people.push(generatePerson(tenant))
                }
            })

            // Bulk save all 12 people
            const bulkSaveResponse = await axiosInstance.post(
                `${baseUrl}/api/openapi.admin/personwithtenant/bulk`,
                people,
                { headers: { tenantId: tenantA } }
            )
            expect(bulkSaveResponse.status).toBe(200)

            // Test retrieval with tenant selection (tenantA only)
            // Lucene syntax: Use field name with space-separated terms in parentheses for OR condition
            const query = `firstName:(${people.map(p => `"${p.firstName}"`).join(' ')})`;
            const searchTenantA = await axiosInstance.post<{
                content: PersonWithTenant[];
                totalElements: number;
                cursor?: string;
            }>(
                `${baseUrl}/admin/api/openapi.admin/personwithtenant/search`,
                {
                    query: query,
                    tenantSelection: [tenantA]
                },
                { params: { size: 20 } }
            )
            expect(searchTenantA.status).toBe(200)
            expect(searchTenantA.data.content.length).toBe(4) // Should only return 4 from tenantA
            expect(searchTenantA.data.content.every(p => p.tenantId === tenantA)).toBe(true)

            // Test retrieval with multiple tenants (tenantA and tenantB)
            const searchTenantAB = await axiosInstance.post<{
                content: PersonWithTenant[];
                totalElements: number;
                cursor?: string;
            }>(
                `${baseUrl}/admin/api/openapi.admin/personwithtenant/search`,
                {
                    query: query,
                    tenantSelection: [tenantA, tenantB]
                },
                { params: { size: 20 } }
            )
            expect(searchTenantAB.status).toBe(200)
            expect(searchTenantAB.data.content.length).toBe(8) // 4 from tenantA + 4 from tenantB
            expect(searchTenantAB.data.content.every(p =>
                                                         [tenantA, tenantB].includes(p.tenantId)
            )).toBe(true)
        })

        it('Can count people across specific tenants', async () => {
            // Generate and save 12 people (4 per tenant)
            const people: PersonWithTenant[] = []
            tenants.forEach(tenant => {
                for (let i = 0; i < 4; i++) {
                    people.push(generatePerson(tenant))
                }
            })
            await axiosInstance.post(
                `${baseUrl}/api/openapi.admin/personwithtenant/bulk`,
                people,
                { headers: { tenantId: tenantA } }
            )

            // Count for tenantA only
            // Lucene syntax: Quote terms to ensure exact matches
            const query = `firstName:(${people.map(p => `"${p.firstName}"`).join(' ')})`;
            const countTenantA = await axiosInstance.post<{ count: number }>(
                `${baseUrl}/admin/api/openapi.admin/personwithtenant/count/by-query`,
                {
                    query: query,
                    tenantSelection: [tenantA]
                }
            )
            expect(countTenantA.status).toBe(200)
            expect(countTenantA.data.count).toBe(4)

            // Count for tenantA and tenantC
            const countTenantAC = await axiosInstance.post<{ count: number }>(
                `${baseUrl}/admin/api/openapi.admin/personwithtenant/count/by-query`,
                {
                    query: query,
                    tenantSelection: [tenantA, tenantC]
                }
            )
            expect(countTenantAC.status).toBe(200)
            expect(countTenantAC.data.count).toBe(8)
        })

        it('Can find people by IDs across specific tenants', async () => {
            // Generate and save 12 people
            const people: PersonWithTenant[] = []
            tenants.forEach(tenant => {
                for (let i = 0; i < 4; i++) {
                    people.push(generatePerson(tenant))
                }
            })
            await axiosInstance.post(
                `${baseUrl}/api/openapi.admin/personwithtenant/bulk`,
                people,
                { headers: { tenantId: tenantA } }
            )

            // Get IDs by searching with Lucene syntax
            const query = `firstName:(${people.map(p => `"${p.firstName}"`).join(' ')})`;
            const searchResponse = await axiosInstance.post<{
                content: PersonWithTenant[];
                totalElements: number;
                cursor?: string;
            }>(
                `${baseUrl}/api/openapi.admin/personwithtenant/search`,
                query,
                { params: { size: 20 } }
            )
            people.forEach((person, index) => {
                const match = searchResponse.data.content.find(
                    p => p.firstName === person.firstName && p.lastName === person.lastName
                )
                if (match) people[index].id = match.id!
            })

            // Find by IDs for tenantB only
            const tenantBIds = people
                .filter(p => p.tenantId === tenantB)
                .map(p => ({ entityId: p.id!, tenantId: p.tenantId }))
            const findTenantB = await axiosInstance.post<PersonWithTenant[]>(
                `${baseUrl}/admin/api/openapi.admin/personwithtenant/find/by-ids`,
                tenantBIds
            )
            expect(findTenantB.status).toBe(200)
            expect(findTenantB.data.length).toBe(4)
            expect(findTenantB.data.every(p => p.tenantId === tenantB)).toBe(true)

            // Find by IDs for tenantA and tenantC
            const tenantACIds = people
                .filter(p => [tenantA, tenantC].includes(p.tenantId))
                .map(p => ({ entityId: p.id!, tenantId: p.tenantId }))
            const findTenantAC = await axiosInstance.post<PersonWithTenant[]>(
                `${baseUrl}/admin/api/openapi.admin/personwithtenant/find/by-ids`,
                tenantACIds
            )
            expect(findTenantAC.status).toBe(200)
            expect(findTenantAC.data.length).toBe(8)
            expect(findTenantAC.data.every(p => [tenantA, tenantC].includes(p.tenantId))).toBe(true)
        })

        it('Can delete people across specific tenants', async () => {
            // Generate and save 12 people
            const people: PersonWithTenant[] = []
            tenants.forEach(tenant => {
                for (let i = 0; i < 4; i++) {
                    people.push(generatePerson(tenant))
                }
            })
            await axiosInstance.post(
                `${baseUrl}/api/openapi.admin/personwithtenant/bulk`,
                people,
                { headers: { tenantId: tenantA } }
            )

            // Delete from tenantB only
            const query = `firstName:(${people.map(p => `"${p.firstName}"`).join(' ')})`;
            const deleteTenantB = await axiosInstance.post(
                `${baseUrl}/admin/api/openapi.admin/personwithtenant/delete/by-query`,
                {
                    query: query,
                    tenantSelection: [tenantB]
                }
            )
            expect(deleteTenantB.status).toBe(200)

            // Verify deletion
            const verifyB = await axiosInstance.post<{
                content: PersonWithTenant[];
                totalElements: number;
                cursor?: string;
            }>(
                `${baseUrl}/admin/api/openapi.admin/personwithtenant/search`,
                {
                    query: query,
                    tenantSelection: [tenantB]
                },
                { params: { size: 20 } }
            )
            expect(verifyB.data.content.length).toBe(0)

            // Verify others remain
            const verifyAC = await axiosInstance.post<{
                content: PersonWithTenant[];
                totalElements: number;
                cursor?: string;
            }>(
                `${baseUrl}/admin/api/openapi.admin/personwithtenant/search`,
                {
                    query: query,
                    tenantSelection: [tenantA, tenantC]
                },
                { params: { size: 20 } }
            )
            expect(verifyAC.data.content.length).toBe(8)
        })
    })
})