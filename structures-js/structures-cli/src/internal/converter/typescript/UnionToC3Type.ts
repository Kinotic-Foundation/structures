import {C3Type, EnumC3Type, ObjectC3Type, UnionC3Type} from '@kinotic/continuum-idl'
import {Type} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'
import { capitalize } from 'radash'

/**
 * Converts a typescript union type to a C3Type
 */
export class UnionToC3Type implements ITypeConverter<Type, Type, TypescriptConversionState> {

  convert(value: Type, conversionContext: IConversionContext<Type, TypescriptConversionState>): C3Type {
    let ret: C3Type

    const converted: C3Type[] = []
    let primitiveCount = 0
    let arrayCount = 0
    let enumCount = 0

    // Unions are interesting because there are unions that are not invalid but not a union when it comes to C3
    // For example: string | undefined | null
    // This is not a union in C3 because it is not a union of objects

    // Another example: let var?: string
    // Which is the same as string | undefined

    // Another example: let var?: MyEnum
    // Which provides the union types of undefined | MyEnum.FIRST | MyEnum.SECOND ect..

    value.getUnionTypes().forEach((unionType: Type) => {

      if(!unionType.isUndefined() && !unionType.isNull()) {

        // Array has to be checked before object because arrays are objects
        if(unionType.isArray()){
          converted.push(conversionContext.convert(unionType))
          arrayCount++
        }else if(unionType.isObject()){
          converted.push(conversionContext.convert(unionType))
        }else if(this.isPrimitive(unionType)) {
          converted.push(conversionContext.convert(unionType))
          primitiveCount++
        }else if(unionType.isEnumLiteral()){
          // Since the enums come back as a bunch of enum literals we only convert the first one we encounter
          if(enumCount === 0){
            const toConvert = unionType.getSymbol()?.getValueDeclaration()?.getParent()?.getType()
            if(toConvert) {
              converted.push(conversionContext.convert(toConvert))
            }else{
              throw new Error("Could not find the parent type of the enum literal: "+unionType.getText())
            }
          }
          enumCount++
        }else{
          throw new Error("Union type contains a type that is not supported: "+unionType.getText())
        }
      }
    })

    if(primitiveCount > 1){
      throw new Error("Union type contains more than one primitive type: "+value.getText())
    }

    if(arrayCount > 1){
      throw new Error("Union type contains more than one array type: "+value.getText())
    }

    // Now we check that a union is only objets or is single non object type
    // Since structures does not support unions of primitives, arrays, or enums
    if(primitiveCount === 1){
      if(converted.length === 1){
        ret = converted[0]
      }else{
        throw new Error("You cannot create a Union with a primitive type and other types: "+value.getText())
      }
    }else if(arrayCount === 1) {
      if (converted.length === 1) {
        ret = converted[0]
      } else {
        throw new Error("You cannot create a Union with an array type and other types: " + value.getText())
      }
    }else if(enumCount >= 1){
      if (converted.length === 1) {
        ret = converted[0]
        if((ret as EnumC3Type).values.length !== enumCount){
          throw new Error("There were more enums found in the union than were converted: " + value.getText() +"\n(Sorry if this error is kind of confusing, it is a bit of a edge case)")
        }
      } else {
        throw new Error("You cannot create a Union with an enum type and other types, or more than one enum type: " + value.getText())
      }
    }else{
      const unionType = new UnionC3Type()
      // if(conversionContext.state().currentPropertyName !== null){
      //   unionType.name = capitalize(conversionContext.state().currentPropertyName as string)
      // }else{
      //   throw new Error("The current property name is not set in the conversion state")
      // }
      unionType.namespace = conversionContext.state().namespace
      unionType.types = converted as ObjectC3Type[]
      ret = unionType
    }

    return ret
  }

  private isPrimitive(type: Type): boolean{
    return type.isString() || type.isNumber() || type.isBoolean()
  }

  supports(value: Type): boolean {
    return value.isUnion();
  }

}
