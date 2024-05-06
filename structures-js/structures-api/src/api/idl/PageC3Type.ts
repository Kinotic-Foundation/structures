import {C3Type, ObjectC3Type} from '@kinotic/continuum-idl'

/**
 * This is a placeholder for the page type.
 * This is not a complete type but a placeholder for the server to know what to expect.
 */
export class PageC3Type extends ObjectC3Type {

    constructor(contentType: C3Type) {
        super('Page',
              'org.kinotic',
              null,
              null)
        this.addProperty('content', contentType)
    }
}
