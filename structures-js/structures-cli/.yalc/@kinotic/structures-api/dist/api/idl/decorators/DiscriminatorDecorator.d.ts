import { C3Decorator } from '@kinotic/continuum-idl';

/**
 * Specifies the discriminator field for polymorphic entities.
 */
export declare class DiscriminatorDecorator extends C3Decorator {
    propertyName?: string;
    constructor();
    withPropertyName(propertyName: string): DiscriminatorDecorator;
}
