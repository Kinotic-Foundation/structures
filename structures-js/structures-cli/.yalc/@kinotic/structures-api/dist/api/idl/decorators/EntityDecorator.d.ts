import { MultiTenancyType } from './MultiTenancyType';
import { C3Decorator } from '@kinotic/continuum-idl';

/**
 * Signifies that a class is an entity.
 */
export declare class EntityDecorator extends C3Decorator {
    multiTenancyType: MultiTenancyType;
    constructor();
    withMultiTenancyType(type: MultiTenancyType): EntityDecorator;
}
