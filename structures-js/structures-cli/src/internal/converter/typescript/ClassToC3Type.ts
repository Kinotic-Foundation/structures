import {ITypeConverter} from '../ITypeConverter'
import {ClassDeclaration, Decorator, Node, PropertyDeclaration} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState'
import {IConversionContext} from '../IConversionContext'
import {C3Type, ObjectC3Type} from '@kinotic/continuum-idl'

export class ClassToC3Type implements ITypeConverter<Node, ClassDeclaration, TypescriptConversionState>{

  convert(value: ClassDeclaration, conversionContext: IConversionContext<Node, TypescriptConversionState>): C3Type {
    const ret: ObjectC3Type = new ObjectC3Type()
value.getType()


    const properties = value.getProperties()
    properties.forEach((property: PropertyDeclaration) => {
      const propertyName = property.getName()
      property.getSourceFile()
      ret.addProperty(propertyName, conversionContext.convert(property));
    })

    return ret
  }

  supports(value: Node): boolean {
    return Node.isClassDeclaration(value);
  }

}
