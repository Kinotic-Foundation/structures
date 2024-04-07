import {Type, Symbol, DecoratableNode} from 'ts-morph'
import {C3Decorator, C3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import {ConverterConstants} from '../ConverterConstants.js'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {IConversionContext} from '../IConversionContext.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {tsDecoratorToC3Decorator, convertPrecisionToC3Type} from './ConverterUtils.js'

/**
 * Converts a typescript Class, Interface, or Type to a C3Type
 */
export class ObjectLikeToC3Type implements ITypeConverter<Type, C3Type, TypescriptConversionState>{

    convert(value: Type, conversionContext: IConversionContext<Type, C3Type, TypescriptConversionState>): C3Type {
        const ret: ObjectC3Type = new ObjectC3Type()

        ret.name = value.getSymbolOrThrow("No Symbol could be found for object: "+value.getText()).getName()
        ret.namespace = conversionContext.state().namespace

        // We store the original source path so it can be used later
        const declarations = value.getSymbol()?.getDeclarations()
        if(declarations && declarations.length > 0){
            if(!ret.metadata){
                ret.metadata = {}
            }
            ret.metadata[ConverterConstants.SOURCE_FILE_PATH] = declarations[0].getSourceFile().getFilePath()
        }

        // Translate all the typescript properties
        const properties = value.getProperties()
        for(const property of properties){
            const propertyName = property.getName()

            conversionContext.beginProcessingProperty(propertyName)

            if(!conversionContext.state().shouldExclude(conversionContext.currentJsonPath)) {

                let converted: C3Type | null = null
                const override = conversionContext.state().getOverrideType(conversionContext.currentJsonPath)
                if(override){
                    converted = override
                }else{
                    const transform = conversionContext.state().getTransformFunction(conversionContext.currentJsonPath)
                    if(transform){
                        converted = conversionContext.convert(transform.getReturnType())
                    }else{
                        converted = this.convertProperty(property, propertyName, conversionContext)
                    }
                }

                ret.addProperty(propertyName, converted);
            }

            conversionContext.endProcessingProperty()
        }

        // Now add any calculated properties, we do this, so wee can override any of the default properties
        const calculatedProperties = conversionContext.state().getCalculatedProperties(conversionContext.currentJsonPath)
        if(calculatedProperties){
            for(const calcProp of calculatedProperties){
                const functionName = calcProp.calculatedPropertyFunctionName
                const calcFunction = conversionContext.state().getUtilFunctionByName(functionName)
                if(!calcFunction){
                    throw new Error(`Could not find util function: ${functionName} for calculated property ${calcProp.entityJsonPath}.${calcProp.propertyName}`)
                }
                const converted = conversionContext.convert(calcFunction.getReturnType())
                if(calcProp.decorators){
                    converted.decorators = calcProp.decorators
                }
                ret.properties[calcProp.propertyName] = converted
            }
        }

        return ret
    }

    private convertProperty(property: Symbol, propertyName: string, conversionContext: IConversionContext<Type, C3Type, TypescriptConversionState>): C3Type {
        const valueDeclaration = property.getValueDeclarationOrThrow("No value declaration could be found for property " + propertyName)

        let converted: C3Type

        if(valueDeclaration.getType().isUnion()){
            conversionContext.state().unionPropertyNameStack.push(propertyName)
        }

        // Typescript cannot detect that this can be a DecoratableNode, so we have to cast it twice
        const decoratableNode = valueDeclaration as unknown as DecoratableNode

        if(decoratableNode && decoratableNode.getDecorator) { // ensure we have a DecoratableNode

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
                for (const decorator of decoratableNode.getDecorators()) {
                    // We have already handled Precision above
                    if (decorator.getName() !== 'Precision') {
                        const c3Decorator = tsDecoratorToC3Decorator(decorator)
                        if(c3Decorator) {
                            if (!converted.containsDecorator(c3Decorator)) {
                                converted.addDecorator(c3Decorator)
                            }
                        }
                    }
                }
            }

        }else{
            converted = conversionContext.convert(valueDeclaration.getType())
        }

        if(valueDeclaration.getType().isUnion()){
            conversionContext.state().unionPropertyNameStack.pop()
        }

        return converted
    }

    supports(value: Type): boolean {
        return value.isObject() && !value.isArray();
    }
}
