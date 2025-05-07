<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Person } from '../domain/Person.js'
import { PersonEntityService } from '../services/PersonEntityService.js'
import { Pageable, Continuum } from '@kinotic/continuum-client'
import { Dog, Cat } from '../domain/Pet.js'

const people = ref<Person[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

async function loadPeople() {
    try {
        await Continuum.connect({
            host: '127.0.0.1',
            port: 58503,
            connectHeaders: {
                login: 'admin',
                passcode: 'structures'
            }
        })
        const personService = new PersonEntityService()
        if (people.value.length === 0) {
            const max = new Dog()
            max.name = 'Max'
            max.breed = 'Golden Retriever'
            max.isGoodBoy = true

            const luna = new Cat()
            luna.name = 'Luna'
            luna.lives = 9
            luna.isIndoor = true

            const dummyPeople = [
                {
                    id: null,
                    version: null,
                    firstName: 'John',
                    lastName: 'Doe',
                    bio: 'Software Engineer with 10 years of experience in full-stack development',
                    age: 32,
                    privateNotes: 'Prefers remote work',
                    addresses: [
                        {
                            street: '123 Tech Lane',
                            city: 'San Francisco',
                            state: 'CA',
                            zipCode: '94105',
                            additionalInfo: 'Apt 4B'
                        },
                        {
                            street: '456 Startup Blvd',
                            city: 'Mountain View',
                            state: 'CA',
                            zipCode: '94043'
                        }
                    ],
                    pet: max
                },
                {
                    id: null,
                    version: null,
                    firstName: 'Jane',
                    lastName: 'Smith',
                    bio: 'Product Manager specializing in AI and Machine Learning products',
                    age: 28,
                    addresses: [
                        {
                            street: '789 Innovation Drive',
                            city: 'Seattle',
                            state: 'WA',
                            zipCode: '98101'
                        }
                    ],
                    pet: luna
                },
                {
                    id: null,
                    version: null,
                    firstName: 'Bob',
                    lastName: 'Johnson',
                    bio: 'UX Designer passionate about creating intuitive user experiences',
                    age: 35,
                    privateNotes: 'Available for freelance projects',
                    addresses: [
                        {
                            street: '321 Design Street',
                            city: 'Austin',
                            state: 'TX',
                            zipCode: '78701',
                            additionalInfo: 'Studio 5'
                        }
                    ],
                    pet: null
                }
            ]
            for (const person of dummyPeople) {
                await personService.save(person)
            }
        }
        const pageable = Pageable.create(0, 100)
        const page = await personService.findAll(pageable)
        people.value = page.content || []
    } catch (e) {
        error.value = e instanceof Error ? e.message : 'Failed to load people'
    } finally {
        loading.value = false
    }
}

onMounted(loadPeople)
</script>

<template>
    <div class="modern-container">
        <h1 class="main-title">People Directory</h1>
        <div v-if="loading" class="loading">Loading...</div>
        <div v-else-if="error" class="error">{{ error }}</div>
        <div v-else class="card-grid">
            <div v-for="person in people" :key="person.id ?? person.firstName + person.lastName" class="person-card">
                <div class="card-header">
                    <span class="person-name">{{ person.firstName }} {{ person.lastName }}</span>
                </div>
                <div class="card-body">
                    <p v-if="person.bio" class="bio">{{ person.bio }}</p>
                    <p v-if="person.age" class="age">Age: <b>{{ person.age }}</b></p>
                    <div v-if="person.addresses.length" class="addresses">
                        <h4>Addresses</h4>
                        <ul>
                            <li v-for="address in person.addresses" :key="address.street">
                                <span class="address-line">{{ address.street }}, {{ address.city }}, {{ address.state }} {{ address.zipCode }}</span>
                                <span v-if="address.additionalInfo" class="address-info">({{ address.additionalInfo }})</span>
                            </li>
                        </ul>
                    </div>
                    <div v-if="person.pet" class="pet">
                        <h4>Pet</h4>
                        <div class="pet-info">
                            <span class="pet-name">{{ person.pet.name }}</span>
                            <span class="pet-type">({{ person.pet.type }})</span>
                            <span v-if="person.pet.type === 'DOG'">- {{ (person.pet as any).breed }}</span>
                            <span v-if="person.pet.type === 'CAT'">- {{ (person.pet as any).isIndoor ? 'Indoor' : 'Outdoor' }} cat</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.modern-container {
    min-height: 100vh;
    background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);
    padding: 40px 0;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.main-title {
    font-size: 2.8rem;
    font-weight: 800;
    color: #22223b;
    margin-bottom: 32px;
    letter-spacing: 1px;
    text-align: center;
}

.card-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
    gap: 32px;
    width: 100%;
    max-width: 1100px;
}

.person-card {
    background: #fff;
    border-radius: 18px;
    box-shadow: 0 4px 24px 0 rgba(34, 34, 59, 0.08), 0 1.5px 4px 0 rgba(34, 34, 59, 0.04);
    padding: 28px 32px 24px 32px;
    transition: box-shadow 0.2s, transform 0.2s;
    display: flex;
    flex-direction: column;
    min-height: 220px;
    border: 1px solid #e0e0e0;
}
.person-card:hover {
    box-shadow: 0 8px 32px 0 rgba(34, 34, 59, 0.16), 0 3px 8px 0 rgba(34, 34, 59, 0.08);
    transform: translateY(-4px) scale(1.02);
}
.card-header {
    margin-bottom: 10px;
}
.person-name {
    font-size: 1.5rem;
    font-weight: 700;
    color: #22223b;
}
.card-body {
    flex: 1;
}
.bio {
    color: #4a4e69;
    font-size: 1.05rem;
    margin-bottom: 8px;
}
.age {
    color: #22223b;
    margin-bottom: 10px;
}
.addresses {
    margin-bottom: 10px;
}
.addresses h4 {
    color: #3a3a40;
    font-size: 1.08rem;
    margin-bottom: 4px;
    font-weight: 600;
}
.address-line {
    color: #22223b;
}
.address-info {
    color: #7c7c7c;
    font-size: 0.95em;
    margin-left: 4px;
}
.pet {
    margin-top: 10px;
}
.pet h4 {
    color: #3a3a40;
    font-size: 1.08rem;
    margin-bottom: 4px;
    font-weight: 600;
}
.pet-info {
    color: #4a4e69;
    font-size: 1.05rem;
}
.pet-name {
    font-weight: 600;
    color: #22223b;
}
.pet-type {
    color: #7c7c7c;
    margin-left: 6px;
}
.loading {
    text-align: center;
    color: #666;
    padding: 20px;
    font-size: 1.2rem;
}
.error {
    color: #dc3545;
    padding: 20px;
    text-align: center;
    background: #f8d7da;
    border-radius: 8px;
    font-size: 1.1rem;
}
</style> 