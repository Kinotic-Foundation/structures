import {ITypeConverter} from '../ITypeConverter.js'
import {ClassDeclaration, Node, PropertyDeclaration} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {IConversionContext} from '../IConversionContext.js'
import {C3Type, ObjectC3Type} from '@kinotic/continuum-idl'

export class ClassToC3Type implements ITypeConverter<Node, ClassDeclaration, TypescriptConversionState>{

  convert(value: ClassDeclaration, conversionContext: IConversionContext<Node, TypescriptConversionState>): C3Type {
    const ret: ObjectC3Type = new ObjectC3Type()
    ret.name = value.getNameOrThrow()
    ret.namespace = conversionContext.state().namespace

    const properties = value.getProperties()
    properties.forEach((property: PropertyDeclaration) => {
      const propertyName = property.getName()
      let converted
      // if this is an object we must resolve the source file first
      if(property.getType().isObject()){
        const propertyType = property.getTypeNode()?.getText();
        if(!propertyType) {
          throw new Error("Source could not be found")
        }
        const nestedClass = conversionContext.state().project.getSourceFile(property.getSourceFile().getFilePath())?.getClass(propertyType)
        if (!nestedClass){
          throw new Error("Class could not be found")
        }
        converted = conversionContext.convert(nestedClass)

      }else {
        converted = conversionContext.convert(property)
      }
      ret.addProperty(propertyName, converted);
    })

    return ret
  }

  supports(value: Node): boolean {
    return Node.isClassDeclaration(value);
  }

}
