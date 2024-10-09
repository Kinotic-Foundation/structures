import {Person} from '@/entity/domain/Person.js'
import {faker} from '@faker-js/faker'
import { v4 as uuidv4 } from 'uuid'

export function generatePeople(number: number): Person[] {
    const persons: Person[] = []
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
        persons.push(person)
    }
    return persons
}
