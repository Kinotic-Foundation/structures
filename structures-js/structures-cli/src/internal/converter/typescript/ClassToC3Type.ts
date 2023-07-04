import {Type, Symbol, DecoratableNode} from 'ts-morph'
import {C3Decorator, C3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {IConversionContext} from '../IConversionContext.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {tsDecoratorToC3Decorator} from './Utils.js'

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

      const valueDeclaration = property.getValueDeclarationOrThrow("No value declaration could be found for property "+propertyName)

      const converted = conversionContext.convert(valueDeclaration.getType())

      // Typescript cannot detect that this can be a DecoratableNode, so we have to cast it
      if((valueDeclaration as unknown as DecoratableNode)?.getDecorators()) {
        const nodeWithDecorators = valueDeclaration as unknown as DecoratableNode
        for (const decorator of nodeWithDecorators.getDecorators()) {
          const c3Decorator: C3Decorator = tsDecoratorToC3Decorator(decorator)
          if(!converted.containsDecorator(c3Decorator)){
            converted.addDecorator(c3Decorator)
          }
        }
      }

      ret.addProperty(propertyName, converted);
    }
    return ret
  }

  supports(value: Type): boolean {
    return value.isObject() && !value.isArray();
  }

}
