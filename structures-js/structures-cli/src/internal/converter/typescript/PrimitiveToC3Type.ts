import {SpecificTypesConverter} from '../SpecificTypesConverter.js'
import {Type} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {IConversionContext} from '../IConversionContext.js'
import {BooleanC3Type, C3Type, StringC3Type, IntC3Type, DateC3Type} from '@kinotic/continuum-idl'

/**
 * Converts typescript primitive types to C3Types
 */
export class PrimitiveToC3Type extends SpecificTypesConverter<Type, TypescriptConversionState, string>{

    constructor() {
        const map: Map<string, (type: Type, context: IConversionContext<Type, TypescriptConversionState>) => C3Type> = new Map()
        map.set('string', () => {
            return new StringC3Type()
        })

        map.set('boolean', () => {
            return new BooleanC3Type()
        })

        map.set('number', () => {
            return new IntC3Type()
        })

        map.set('Date', () => {
            return new DateC3Type()
        })

        super((arg: Type) => {
            return arg.getText()
        }, map)
    }
}
