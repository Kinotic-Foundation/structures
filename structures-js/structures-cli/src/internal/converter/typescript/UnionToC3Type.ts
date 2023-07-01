import {C3Type, NotNullDecorator, ObjectC3Type, UnionC3Type} from '@kinotic/continuum-idl'
import {Type} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'

/**
 * Converts a typescript union type to a C3Type
 */
export class UnionToC3Type implements ITypeConverter<Type, Type, TypescriptConversionState> {

  convert(value: Type, conversionContext: IConversionContext<Type, TypescriptConversionState>): C3Type {
    let ret: C3Type

    const converted: C3Type[] = []
    let primitiveCount = 0
    let arrayCount = 0
    // Unions are interesting because there are unions that are not invalid but not a union when it comes to C3
    // For example: string | undefined | null
    // This is not a union in C3 because it is not a union of objects
    // Another example: let var?: string
    // Which is the same as string | undefined
    value.getUnionTypes().forEach((unionType: Type) => {

      if(!unionType.isUndefined() && !unionType.isNull()) {

        if(unionType.isArray()){
          converted.push(conversionContext.convert(unionType))
          arrayCount++
        }else if(unionType.isObject()){
          converted.push(conversionContext.convert(unionType))
        }else if(this.isPrimitive(unionType)){
          converted.push(conversionContext.convert(unionType))
          primitiveCount++
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

    if(primitiveCount === 1){
      if(converted.length === 1){
        ret = converted[0]
      }else{
        throw new Error("You cannot create a Union with a primitive type and other types: "+value.getText())
      }
    }else if(arrayCount === 1){
      if(converted.length === 1){
        ret = converted[0]
      }else{
        throw new Error("You cannot create a Union with an array type and other types: "+value.getText())
      }
    }else{
      const unionType = new UnionC3Type()
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
