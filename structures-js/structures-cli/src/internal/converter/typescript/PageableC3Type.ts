import {ObjectC3Type} from '@kinotic/continuum-idl'

/**
 * This is a placeholder for the pageable type.
 * This is not a complete type but a placeholder for the server to know what to expect.
 */
export class PageableC3Type extends ObjectC3Type {

    constructor() {
        super('Pageable',
              'org.kinotic',
              null,
              null)
    }
}
