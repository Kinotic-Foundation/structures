import {BaseEntityService} from '../../src/api/BaseEntityService.js'
import {Person} from '../domain/Person.js'

export class PersonEntityService extends BaseEntityService<Person>{

    constructor() {
        super('org.kinotic.sample', 'Person')
    }
}
