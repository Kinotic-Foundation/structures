import {C3Decorator} from '@kinotic/continuum-idl'

/**
 * Signifies the ID field of an entity, The value must be a string and provided by the client.
 */
export class VersionDecorator extends C3Decorator {

    constructor() {
        super()
        this.type = 'VersionDecorator'
    }
}
