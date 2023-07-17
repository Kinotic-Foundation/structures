import {C3Decorator} from '@kinotic/continuum-idl'
import {MultiTenancyType} from '@/api/idl/decorators/MultiTenancyType'

/**
 * Signifies that a class is an entity.
 */
export class EntityDecorator extends C3Decorator {
    public multiTenancyType: MultiTenancyType = MultiTenancyType.NONE
    constructor() {
        super()
        this.type = 'Entity'
    }

    public withMultiTenancyType(type: MultiTenancyType): EntityDecorator {
        this.multiTenancyType = type
        return this
    }
}
