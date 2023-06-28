import {SpecificTypesConverter} from '../SpecificTypesConverter.js'
import {Node} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {IConversionContext} from '../IConversionContext.js'
import {BooleanC3Type, C3Type, StringC3Type, IntC3Type} from '@kinotic/continuum-idl'

/**
 * Converts typescript primitive types to C3Types
 */
export class PrimitiveToC3Type extends SpecificTypesConverter<Node, TypescriptConversionState, string>{

  constructor() {
    const map: Map<string, (type: Node, context: IConversionContext<Node, TypescriptConversionState>) => C3Type> = new Map()
    map.set('string', () => {
      return new StringC3Type()
    })

    map.set('boolean', () => {
      return new BooleanC3Type()
    })

    map.set('number', () => {
      return new IntC3Type()
    })

    super((arg: Node) => {
     return arg.getType().getText()
    }, map)
  }
}
