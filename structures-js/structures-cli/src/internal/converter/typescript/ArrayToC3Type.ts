import {ArrayC3Type, C3Type} from '@kinotic/continuum-idl'
import {Type} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {IConversionContext} from '../IConversionContext.js'
import {ITypeConverter} from '../ITypeConverter.js'

export class ArrayToC3Type  implements ITypeConverter<Type, Type, TypescriptConversionState> {

  convert(value: Type, conversionContext: IConversionContext<Type, TypescriptConversionState>): C3Type {
    const ret: ArrayC3Type = new ArrayC3Type()

    const arrayElementType = value?.getArrayElementType()
    if(arrayElementType) {
      ret.contains = conversionContext.convert(arrayElementType)
    }else{
      throw new Error("Type could not be found for array type "+value.getText())
    }
    return ret
  }

  supports(value: Type, conversionState: TypescriptConversionState): boolean {
    return value.isArray()
  }

}
