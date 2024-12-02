import {EntityServiceDecoratorsConfig} from '@/api/idl/decorators/EntityServiceDecoratorsConfig'
import {C3Decorator} from '@kinotic/continuum-idl'

/**
 * {@link C3Decorator} representation for the {@link EntityServiceDecorators}
 */
export class EntityServiceDecoratorsDecorator extends C3Decorator {

    public config!: EntityServiceDecoratorsConfig

    constructor() {
        super()
        this.type = 'EntityServiceDecoratorsDecorator'
    }

}
