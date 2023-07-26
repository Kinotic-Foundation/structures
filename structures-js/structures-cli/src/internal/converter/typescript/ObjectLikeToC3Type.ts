import {Type, Symbol, DecoratableNode} from 'ts-morph'
import {C3Decorator, C3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {IConversionContext} from '../IConversionContext.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {tsDecoratorToC3Decorator, convertPrecisionToC3Type} from './ConverterUtils.js'

/**
 * Converts a typescript Class, Interface, or Type to a C3Type
 */
export class ObjectLikeToC3Type implements ITypeConverter<Type, Type, TypescriptConversionState>{

    convert(value: Type, conversionContext: IConversionContext<Type, TypescriptConversionState>): C3Type {
        const ret: ObjectC3Type = new ObjectC3Type()

        ret.name = value.getSymbolOrThrow("No Symbol could be found for object: "+value.getText()).getName()
        ret.namespace = conversionContext.state().namespace

        const properties = value.getProperties()
        for(const property of properties){
            const propertyName = property.getName()

            conversionContext.state().beginProcessingProperty(propertyName)

            if(!conversionContext.state().convertingUnion){
                conversionContext.state().nearestPropertyNameNotInUnion = propertyName
            }

            if(!conversionContext.state().shouldExclude()) {

                let converted: C3Type | null = null
                const override = conversionContext.state().getOverrideType()
                if(override){
                    converted = override
                }else{
                    const transform = conversionContext.state().getTransformFunction()
                    if(transform){
                        converted = conversionContext.convert(transform.getReturnType())
                    }else{
                        converted = this.convertProperty(property, propertyName, conversionContext)
                    }
                }

                if(converted) {
                    ret.addProperty(propertyName, converted);
                }else{
                    throw new Error(`Unable to convert property ${conversionContext.state().currentJsonPath}}`)
                }
            }

            conversionContext.state().endProcessingProperty()

            if(!conversionContext.state().convertingUnion){
                conversionContext.state().nearestPropertyNameNotInUnion = null
            }
        }
        return ret
    }

    private convertProperty(property: Symbol, propertyName: string, conversionContext: IConversionContext<Type, TypescriptConversionState>) {
        const valueDeclaration = property.getValueDeclarationOrThrow("No value declaration could be found for property " + propertyName)

        let converted: C3Type

        if(valueDeclaration instanceof DecoratableNode){
            // Typescript cannot detect that this can be a DecoratableNode, so we have to cast it
            const decoratableNode = valueDeclaration as unknown as DecoratableNode
            const precisionDecorator = decoratableNode?.getDecorator('Precision')
            if (precisionDecorator) {
                // TODO: Verify this is a number type. This could also be a union type that has a number in the case of something like
                // public myNumber: number | null
                // or public myNumber?: number
                converted = convertPrecisionToC3Type(precisionDecorator)
            } else {
                converted = conversionContext.convert(valueDeclaration.getType())
            }

            if (decoratableNode?.getDecorators()) {
                const nodeWithDecorators = valueDeclaration as unknown as DecoratableNode
                for (const decorator of nodeWithDecorators.getDecorators()) {
                    // We have already handled Precision above
                    if (decorator.getName() !== 'Precision') {
                        const c3Decorator: C3Decorator = tsDecoratorToC3Decorator(decorator)
                        if (!converted.containsDecorator(c3Decorator)) {
                            converted.addDecorator(c3Decorator)
                        }
                    }
                }
            }
        }else{
            converted = conversionContext.convert(valueDeclaration.getType())
        }
        return converted
    }

    supports(value: Type): boolean {
        return value.isObject() && !value.isArray();
    }
}
