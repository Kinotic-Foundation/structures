import {Type, Symbol, DecoratableNode} from 'ts-morph'
import {C3Decorator, C3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {IConversionContext} from '../IConversionContext.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {tsDecoratorToC3Decorator, convertPrecisionToC3Type} from './ConverterUtils.js'

/**
 * Converts a typescript class to a C3Type
 */
export class ClassToC3Type implements ITypeConverter<Type, Type, TypescriptConversionState>{

    convert(value: Type, conversionContext: IConversionContext<Type, TypescriptConversionState>): C3Type {
        const ret: ObjectC3Type = new ObjectC3Type()

        ret.name = value.getSymbolOrThrow("No Symbol could be found for object: "+value.getText()).getName()
        ret.namespace = conversionContext.state().namespace

        const properties = value.getProperties()
        for(const property of properties){
            const propertyName = property.getName()

            conversionContext.state().currentPropertyName = propertyName

            if(!conversionContext.state().convertingUnion){
                conversionContext.state().nearestPropertyNameNotInUnion = propertyName
            }

            const valueDeclaration = property.getValueDeclarationOrThrow("No value declaration could be found for property "+propertyName)
            // Typescript cannot detect that this can be a DecoratableNode, so we have to cast it
            const decoratableNode = valueDeclaration as unknown as DecoratableNode

            let converted: C3Type

            const precisionDecorator = decoratableNode?.getDecorator('Precision')
            if(precisionDecorator){
                // TODO: Verify this is a number type. This could also be a union type that has a number in the case of somehting like
                // public myNumber: number | null
                // or public myNumber?: number
                converted = convertPrecisionToC3Type(precisionDecorator)
            }else{
                converted = conversionContext.convert(valueDeclaration.getType())
            }

            if(decoratableNode?.getDecorators()) {
                const nodeWithDecorators = valueDeclaration as unknown as DecoratableNode
                for (const decorator of nodeWithDecorators.getDecorators()) {
                    // We have already handled Precision above
                    if(decorator.getName() !== 'Precision'){
                        const c3Decorator: C3Decorator = tsDecoratorToC3Decorator(decorator)
                        if(!converted.containsDecorator(c3Decorator)){
                            converted.addDecorator(c3Decorator)
                        }
                    }
                }
            }

            ret.addProperty(propertyName, converted);

            conversionContext.state().currentPropertyName = null

            if(!conversionContext.state().convertingUnion){
                conversionContext.state().nearestPropertyNameNotInUnion = null
            }
        }
        return ret
    }

    supports(value: Type): boolean {
        return value.isObject() && !value.isArray();
    }

}
