import {Person} from "./Person"
import {C3Decorators} from "./C3Decorators"

export class Services {

    @C3Decorators.query("adfadfasdf")
    abstract someNamedQuery(param1: string, param2: number): Promise<Person[]>

}

