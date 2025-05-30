import 'reflect-metadata'
import {EntityType} from '@/api/idl/decorators/EntityType.js'
import {EsIndexConfigurationData} from '@/api/idl/decorators/EsIndexConfigurationData.js'
import {MultiTenancyType} from '@/api/idl/decorators/MultiTenancyType'
import {PrecisionType} from '@/api/idl/decorators/PrecisionType'

export enum StructuresDecorator {
    Entity = '__structures-entity__',
    Id = '__structures-id__',
    AutoGeneratedId = '__structures-auto-generated-id__',
    Flattened = '__structures-flattened__',
    Nested = '__structures-nested__',
    Text = '__structures-text__',
    NotNull = '__structures-notnull__',
    Precision = '__structures-precision__',
    Discriminator = '__structures-discriminator__',
    Query = '__structures-query__'
}

/**
 * Signifies that a property is an auto-generated ID for the entity.
 */
export function AutoGeneratedId(target: any, propertyKey: string) {
    Reflect.defineMetadata(StructuresDecorator.AutoGeneratedId, {}, target, propertyKey)

}

export class DiscriminatorConfig {
    public propertyName: string

    constructor(propertyName: string) {
        this.propertyName = propertyName
    }
}

/**
 * Signifies that a property is a discriminator for the entity.
 * This is used to determine the type of the entity when deserializing from JSON.
 * @param propertyName the name of the property to use as the discriminator
 */
export function Discriminator(propertyName: string){
    return function(target: any, propertyKey: string) {
        Reflect.defineMetadata(StructuresDecorator.Discriminator, new DiscriminatorConfig(propertyName), target, propertyKey)
    }
}

export class EntityConfig {
    public multiTenancyType: MultiTenancyType
    public entityType: EntityType

    constructor(multiTenancyType: MultiTenancyType, entityType: EntityType = EntityType.TABLE) {
        this.multiTenancyType = multiTenancyType
        this.entityType = entityType
    }
}

/**
 * Signifies that a class is a Structures Entity.
 * This is required to define the class as an entity in the Structures framework.
 * @param multiTenancyType the type of multi-tenancy to use for this entity
 * @param entityType the type of entity to use for this entity
 */
export function Entity(multiTenancyType: MultiTenancyType = MultiTenancyType.NONE,
                       entityType: EntityType = EntityType.TABLE) {
    return function (constructor: Function) {
        Reflect.defineMetadata(StructuresDecorator.Entity, new EntityConfig(multiTenancyType, entityType), constructor)
    }
}

/**
 * A class Decorator when applied to an Entity will configure Decorators to be applied to the EntityService
 * @param config the {@link EntityServiceDecoratorsConfig} to use
 */
// @ts-ignore
export function EntityServiceDecorators(config: EntityServiceDecoratorsConfig) {
    // @ts-ignore
    return function (constructor: Function) {

    }
}

/**
 * Provides configuration for the Elasticsearch index.
 * This can be used to override the default index settings and mappings used by Structures.
 */
// @ts-ignore
export function EsIndexConfiguration(value: EsIndexConfigurationData) {
    // @ts-ignore
    return function (constructor: Function) {

    }
}

export function Flattened(target: any, propertyKey: string) {
    Reflect.defineMetadata(StructuresDecorator.Flattened, {}, target, propertyKey)
}

/**
 * Signifies that a property is the ID of the entity.
 */
export function Id(target: any, propertyKey: string) {
    Reflect.defineMetadata(StructuresDecorator.Id, {}, target, propertyKey)
}

export function Nested(target: any, propertyKey: string) {
    Reflect.defineMetadata(StructuresDecorator.Nested, {}, target, propertyKey)
}

/**
 * Signifies that a property is not indexed in Elasticsearch.
 */
// @ts-ignore
export function NotIndexed(target: any, propertyKey: string) {

}

export function NotNull(target: any, propertyKey: string) {
    Reflect.defineMetadata(StructuresDecorator.NotNull, {}, target, propertyKey)
}

// @ts-ignore
export function Policy(policies: string[][]) {
    // Definition below allows decorator to be used for class, method, or properties
    // @ts-ignore
    return function (target: any, propertyKey?: string, descriptor?: PropertyDescriptor) {
        // if (descriptor) {
        //     // Method Decorator
        // } else if (propertyKey) {
        //     // Property Decorator
        // } else {
        //     // Class Decorator
        // }
    }
}

export class PrecisionConfig {
    public precisionType: PrecisionType

    constructor(precisionType: PrecisionType) {
        this.precisionType = precisionType
    }
}

export function Precision(precisionType: PrecisionType = PrecisionType.INT){
    return function(target: any, propertyKey: string) {
        Reflect.defineMetadata(StructuresDecorator.Precision, new PrecisionConfig(precisionType), target, propertyKey)
    }
}

export function Query(statements: string) {
    return function (target: any, key: string, descriptor: PropertyDescriptor) {

        Reflect.defineMetadata(StructuresDecorator.Query, statements, target, key)

        /** Code below will be needed if we want the auto generated invocation logic to live in the base service class for the entity service **/
        // const originalMethod = descriptor.value;
        //
        // // Access the super class and its method
        // const superClass = Object.getPrototypeOf(target.constructor.prototype);
        // const superMethod = superClass[key];
        //
        // descriptor.value = function (...args: any[]) {
        //     // Check if the super method exists
        //     if (typeof superMethod === 'function') {
        //         return superMethod.apply(this, args);
        //     } else {
        //         return originalMethod.apply(this, args);
        //     }
        // };

        return descriptor;
    }
}

// @ts-ignore
export function Role(roles: string[]) {
    // @ts-ignore
    return function (target: any, propertyKey?: string, descriptor?: PropertyDescriptor) {
    }
}

export function Text(target: any, propertyKey: string) {
    Reflect.defineMetadata(StructuresDecorator.Text, {}, target, propertyKey)
}

/**
 * This is used for data streams to signify the field that should be used as the time reference.
 */
// @ts-ignore
export function TimeReference(target: any, propertyKey: string) {
}

// @ts-ignore
export function Version(target: any, propertyKey: string) {

}

// @ts-ignore
export function TenantId(target: any, propertyKey: string) {

}
