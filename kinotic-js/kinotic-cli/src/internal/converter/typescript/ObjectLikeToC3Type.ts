import {DecoratableNode, Node, Type} from 'ts-morph'
import {ArrayC3Type, C3Type, DateC3Type, ObjectC3Type, PropertyDefinition} from '@kinotic-ai/idl'
import {ConverterConstants} from '@/internal/converter/ConverterConstants'
import {TypescriptConversionState} from './TypescriptConversionState'
import {IConversionContext} from '@/internal/converter/IConversionContext'
import {ITypeConverter} from '@/internal/converter/ITypeConverter'
import {tsDecoratorToC3Decorator, convertPrecisionToC3Type} from './ConverterUtils'
import {TenantIdDecorator} from '@kinotic-ai/management-api'

/**
 * Converts a typescript Class, Interface, or Type to a C3Type
 */
export class ObjectLikeToC3Type implements ITypeConverter<Type, C3Type, TypescriptConversionState>{

    convert(value: Type, conversionContext: IConversionContext<Type, C3Type, TypescriptConversionState>): C3Type {
        let ret: ObjectC3Type

        const name = this.nameOf(value)
        const namespace = conversionContext.state().application
        ret = new ObjectC3Type(name, namespace)

        // Object stack name is used to help name properties that are not defined such as with union literals
        conversionContext.state().objectNameStack.push(name)

        // We store the original source path so, it can be used later
        const declarations = value.getSymbol()?.getDeclarations()
        if(conversionContext.state().shouldAddSourcePathToMetadata
            && declarations
            && declarations.length > 0){
            if(!ret.metadata){
                ret.metadata = {}
            }
            ret.metadata[ConverterConstants.SOURCE_FILE_PATH] = declarations[0].getSourceFile().getFilePath()
        }

        // Get value declaration so we can get the decorators
        this.convertDecorators(value, ret)

        // Translate all the typescript properties
        this.convertProperties(value, conversionContext, ret)

        conversionContext.state().objectNameStack.pop()

        return ret
    }

    supports(value: Type): boolean {
        return (value.isObject() && !value.isArray())
            || (value.isIntersection() && value.getIntersectionTypes().every(t => t.isObject() && !t.isArray()))
    }

    // The type's own name; an instance of a generic type adds its type arguments, so Page<Report> and Page<User>
    // stay apart, and an unnamed intersection joins the names of its parts
    private nameOf(value: Type): string {
        let ret: string
        if (value.isIntersection() && !value.getAliasSymbol()) {
            ret = value.getIntersectionTypes().map(t => this.nameOf(t)).join('_')
        } else {
            const symbol = value.getSymbol()
            const alias = value.getAliasSymbol()
            const aliasArguments = value.getAliasTypeArguments()
            let base: string
            let typeArguments: Type[]
            if (alias && (aliasArguments.length > 0 || !symbol)) {
                base = alias.getName()
                typeArguments = aliasArguments
            } else if (symbol) {
                base = symbol.getName()
                typeArguments = value.getTypeArguments()
            } else {
                throw new Error("No Symbol could be found for object: " + value.getText())
            }
            ret = [base, ...typeArguments.map(t => (t.getAliasSymbol() ?? t.getSymbol())?.getName() ?? t.getText())]
                .join('_')
                .replace(/[^\w$]/g, '_')
        }
        return ret
    }

    private convertDecorators(value: Type, ret: ObjectC3Type) {
        // an interface or a type literal has no value declaration, and nothing to decorate
        const valueDeclaration = value.getSymbol()?.getValueDeclaration()
        if (valueDeclaration && Node.isDecoratable(valueDeclaration)) {
            for (const decorator of valueDeclaration.getDecorators()) {
                const c3Decorator = tsDecoratorToC3Decorator(decorator)
                if (c3Decorator) {
                    ret.addDecorator(c3Decorator)
                }
            }
        }
    }

    private convertProperties(value: Type, conversionContext: IConversionContext<Type, C3Type, TypescriptConversionState>, ret: ObjectC3Type) {
        const properties = value.getProperties()
        for (const property of properties) {
            const propertyName = property.getName()
            // a mapped type's property, as Partial and Pick make, has the declaration it was mapped from but no value declaration
            const declaration = property.getValueDeclaration() ?? property.getDeclarations()[0]
            if (!declaration) {
                throw new Error("No declaration could be found for property " + propertyName)
            }
            // the property's type in this type, so a generic type's property has its type argument
            const propertyType = property.getTypeAtLocation(declaration)

            // JSON leaves out a method or a function-valued property
            if (propertyType.getNonNullableType().getCallSignatures().length === 0) {
                conversionContext.beginProcessingProperty(propertyName)

                let propertyDefinition = this.convertProperty(declaration, propertyType, propertyName, conversionContext)

                ret.addPropertyDefinition(propertyDefinition);

                conversionContext.endProcessingProperty()
            }
        }
    }

    private convertProperty(valueDeclaration: Node,
                            propertyType: Type,
                            propertyName: string,
                            conversionContext: IConversionContext<Type, C3Type, TypescriptConversionState>): PropertyDefinition {
        let propertyDefinition: PropertyDefinition

        if(propertyType.isUnion()){
            conversionContext.state().unionPropertyNameStack.push(propertyName)
        }

        // Typescript cannot detect that this can be a DecoratableNode, so we have to cast it twice
        const decoratableNode = valueDeclaration as unknown as DecoratableNode

        if(decoratableNode && decoratableNode.getDecorators) { // decoratableNode.getDecorators checks for existence of function, which will only be present if it is a DecoratableNode

            const precisionDecorator = decoratableNode?.getDecorator('Precision')
            if (precisionDecorator) {
                // TODO: Verify this is a number type. This could also be a union type that has a number in the case of something like
                // public myNumber: number | null
                // or public myNumber?: number
                // Additionally, this should be moved to the standard decorator pattern
                const converted = convertPrecisionToC3Type(precisionDecorator)
                propertyDefinition = new PropertyDefinition(propertyName, converted)
            } else if (decoratableNode.getDecorator('DateTime')) {
                const converted = this.convertDateTime(propertyType, propertyName)
                propertyDefinition = new PropertyDefinition(propertyName, converted)
            } else {
                const converted = conversionContext.convert(propertyType)
                propertyDefinition = new PropertyDefinition(propertyName, converted)
            }

            if (decoratableNode?.getDecorators()) {
                for (const decorator of decoratableNode.getDecorators()) {
                    // We have already handled Precision above
                    if (decorator.getName() !== 'Precision') {
                        const c3Decorator = tsDecoratorToC3Decorator(decorator)
                        if(c3Decorator) {

                            if(c3Decorator instanceof TenantIdDecorator){
                                conversionContext.state().multiTenantSelectionEnabled = true

                            }

                            if (!propertyDefinition.containsDecorator(c3Decorator)) {
                                propertyDefinition.addDecorator(c3Decorator)
                            }
                        }
                    }
                }
            }

        }else{
            const converted = conversionContext.convert(propertyType)
            propertyDefinition = new PropertyDefinition(propertyName, converted)
        }

        if(propertyType.isUnion()){
            conversionContext.state().unionPropertyNameStack.pop()
        }

        return propertyDefinition
    }

    private convertDateTime(type: Type, propertyName: string): C3Type {
        const nonNullableType = type.getNonNullableType()
        const elementType = nonNullableType.isArray() ? nonNullableType.getArrayElementTypeOrThrow().getNonNullableType() : null
        let ret: C3Type
        if (nonNullableType.isString()) {
            ret = new DateC3Type()
        } else if (elementType?.isString()) {
            ret = new ArrayC3Type(new DateC3Type())
        } else {
            throw new Error(`@DateTime requires a string or string array property, but ${propertyName} is ${type.getText()}`)
        }
        return ret
    }
}
