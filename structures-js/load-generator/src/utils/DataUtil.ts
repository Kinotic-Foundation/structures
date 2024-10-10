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


export function formatDuration(durationMs: number): string {
    // Convert milliseconds to hours, minutes, seconds, and remaining milliseconds
    const hours = Math.floor(durationMs / 3600000);
    const minutes = Math.floor((durationMs % 3600000) / 60000);
    const seconds = Math.floor((durationMs % 60000) / 1000);
    const milliseconds = Math.floor(durationMs % 1000);

    // Build human-readable time string
    const timeParts = [];
    if (hours > 0) timeParts.push(`${hours}h`);
    if (minutes > 0) timeParts.push(`${minutes}m`);
    if (seconds > 0) timeParts.push(`${seconds}s`);
    if (milliseconds > 0 || timeParts.length === 0) timeParts.push(`${milliseconds}ms`);

    return timeParts.join(' ');
}
