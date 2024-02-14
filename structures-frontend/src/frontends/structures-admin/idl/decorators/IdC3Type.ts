import {C3Type} from '@kinotic/continuum-idl'
import {IdDecorator} from '@kinotic/structures-api'

/**
 * The id type is used for ids.
 * Created by navid on 2023-4-13.
 */
export class IdC3Type extends C3Type {

    constructor() {
        super()
        this.type = 'string'
        this.addDecorator(new IdDecorator())
    }
}

