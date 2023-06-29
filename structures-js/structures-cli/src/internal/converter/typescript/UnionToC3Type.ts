import {ITypeConverter} from '../ITypeConverter'
import {Node, UnionTypeNode} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState'
import {IConversionContext} from '../IConversionContext'
import {C3Type, UnionC3Type} from '@kinotic/continuum-idl'

export class UnionToC3Type  implements ITypeConverter<Node, UnionTypeNode, TypescriptConversionState> {
  convert(value: UnionTypeNode, conversionContext: IConversionContext<Node, TypescriptConversionState>): C3Type {
    return new UnionC3Type();
  }

  supports(value: Node): boolean {
    return false
  }

}
