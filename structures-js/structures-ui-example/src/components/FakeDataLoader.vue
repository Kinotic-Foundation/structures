<script setup lang="ts">
import {Continuum} from '@kinotic/continuum-client'
import {Person} from '../city/domain/Person.js'
import {PersonEntityService} from '../city/services/PersonEntityService.js'
import {Vehicle} from '../mvd/domain/Vehicle.js'
import {VehicleEntityService} from '../mvd/services/VehicleEntityService.js'
import { v4 as uuidv4 } from 'uuid'
import {faker} from '@faker-js/faker'
import { ref } from 'vue'

const loading = ref(false)
const count = ref(0)

async function generateFakeData(): Promise<void> {

    await Continuum.connect({
                                host: '127.0.0.1',
                                port: 58503,
                                connectHeaders:{login: 'admin', passcode: 'structures'}
                            })

    const data = generateData(20)
    const personService = new PersonEntityService()
    const vehicleService = new VehicleEntityService()
    loading.value = true
    await personService.bulkSave(data.persons)
    await vehicleService.bulkSave(data.vehicles)
    count.value = data.persons.length
    loading.value = false
}

function generateData(number: number): {persons: Person[], vehicles: Vehicle[]} {
    const persons: Person[] = []
    const vehicles: Vehicle[] = []
    for(let i = 0; i < number; i++){
        let person = {
            id: uuidv4(),
            firstName: faker.person.firstName(),
            lastName: faker.person.lastName(),
            birthDate: faker.date.birthdate({ min: 18, max: 65, mode: 'age' }),
            age: faker.number.int({ min: 18, max: 65 }),
            address: {
                street: faker.location.streetAddress(false),
                city: faker.location.city(),
                state: faker.location.state(),
                zip: faker.location.zipCode()
            }
        }
        let vehicle: Vehicle = {
            id: uuidv4(),
            manufacturer: faker.vehicle.manufacturer(),
            model: faker.vehicle.model(),
            type: faker.vehicle.type(),
            color: faker.vehicle.color(),
            owner: {
                id: person.id,
                __typename: 'Person'
            }
        }
        persons.push(person)
        vehicles.push(vehicle)
    }
    return {persons, vehicles}
}

</script>

<template>
    <div class="card">
        <button type="button" @click="generateFakeData">Generate</button>
        <div v-if="loading">
            Loading...
        </div>
        <div v-else>
            Generated {{ count }} fakes records
        </div>
    </div>
</template>

<style scoped>
.read-the-docs {
    color: #888;
}
</style>
