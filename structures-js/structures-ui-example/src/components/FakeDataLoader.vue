<script setup lang="ts">
import {Continuum} from '@kinotic/continuum-client'
import {Animal, AnimalDen} from '../animal/domain/Animal.js'
import {AnimalDenEntityService} from '../animal/services/AnimalDenEntityService.js'
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
    const animalDenService = new AnimalDenEntityService()
    loading.value = true
    await personService.bulkSave(data.persons)
    await vehicleService.bulkSave(data.vehicles)
    await animalDenService.bulkSave(data.animals)
    count.value = data.persons.length
    loading.value = false

    await Continuum.disconnect()
}

type TypeAndFunction = {
    type: string,
    fakerFunction: () => string
}

function generateData(number: number): {persons: Person[], vehicles: Vehicle[], animals: AnimalDen[]} {
    const persons: Person[] = []
    const vehicles: Vehicle[] = []
    const animals: AnimalDen[] = []
    const animalTypes: TypeAndFunction[] = [
        {
            type: 'Dog',
            fakerFunction: faker.animal.dog
        },
        {
            type: 'Cat',
            fakerFunction: faker.animal.cat
        },
        {
            type: 'Bear',
            fakerFunction: faker.animal.bear
        },
        {
            type: 'Rabbit',
            fakerFunction: faker.animal.rabbit
        }
    ]

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
            }
        }
        let typeAndFunction = animalTypes[faker.number.int({ min: 0, max: animalTypes.length - 1 })]
        let animal: Animal = {
            type: typeAndFunction.type,
            species: typeAndFunction.fakerFunction(),
        }
        animals.push({
                         id: uuidv4(),
                         livesHere: animal,
                         madeOf: 'Dirt'
                     })
        persons.push(person)
        vehicles.push(vehicle)
    }
    return {persons, vehicles, animals}
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
