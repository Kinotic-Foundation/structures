import {ArrayC3Type, C3Type} from '@kinotic/continuum-idl'
import {Node} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {IConversionContext} from '../IConversionContext.js'
import {ITypeConverter} from '../ITypeConverter.js'

export class ArrayToC3Type  implements ITypeConverter<Node, Node, TypescriptConversionState> {

  convert(value: Node, conversionContext: IConversionContext<Node, TypescriptConversionState>): C3Type {
    const ret: ArrayC3Type = new ArrayC3Type()

    const arrayValueDeclaration = value.getType()?.getArrayElementType()?.getSymbol()?.getValueDeclaration()
    if(arrayValueDeclaration) {
      ret.contains = conversionContext.convert(arrayValueDeclaration)
    }else{
      throw new Error("Type could not be found for array type "+value.getType().getText())
    }
    return ret
  }

  supports(value: Node, conversionState: TypescriptConversionState): boolean {
    return value.getType().isArray()
  }

}
