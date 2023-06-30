import {C3Type, ObjectC3Type, UnionC3Type} from '@kinotic/continuum-idl'
import {ClassDeclaration, Node, Type} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'

/**
 * Converts a typescript union type to a C3Type
 */
export class UnionToC3Type implements ITypeConverter<Node, Node, TypescriptConversionState> {

  convert(value: Node, conversionContext: IConversionContext<Node, TypescriptConversionState>): C3Type {
    const ret: UnionC3Type = new UnionC3Type()

    value.getType().getUnionTypes().forEach((unionType: Type) => {
      const unionDeclaration = unionType?.getSymbol()?.getValueDeclaration()
      if(unionDeclaration) {
        if(ClassDeclaration.isClassDeclaration(unionDeclaration)) {
          ret.types.push(conversionContext.convert(unionDeclaration) as ObjectC3Type)
        }else{
          throw new Error("Structures only supports classes in union types")
        }
      }else{
        throw new Error("Class could not be found for union type "+unionType.getText())
      }
    })
    return ret
  }

  supports(value: Node): boolean {
    return value.getType().isUnion();
  }

}
