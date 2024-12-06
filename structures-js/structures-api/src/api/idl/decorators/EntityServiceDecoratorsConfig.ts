import {EntityServiceDecorator} from '@/api/idl/decorators/EntityServiceDecorator'
import {IEntityService} from '@/api/IEntityService'

// Helper type to determine if a type is a function
type IfFunction<T, U> = T extends (...args: any[]) => any ? U : never;

// Extracting only method names
type MethodsOf<T> = {
    [K in keyof T]: IfFunction<T[K], K>;
}[keyof T];

// Defining the DecoratedFunction type based on IEntityService methods, and a few more
export type DecoratedFunction = MethodsOf<IEntityService<any>> | 'allCreate' | 'allRead' | 'allUpdate' | 'allDelete';

/**
 * Configuration for the {@link EntityServiceDecoratorsDecorator}
 * This is a map of method names to an array of decorators that should be applied to that method
 */
export type EntityServiceDecoratorsConfig = Partial<Record<DecoratedFunction, EntityServiceDecorator[]>>
