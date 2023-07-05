import {EntityService} from '../../src/index.js'
import {Person} from '../domain/Person.js'

export class PersonEntityService extends EntityService<Person>{

    constructor() {
        super('org.kinotic.sample', 'Person')
    }
}
