import {Type} from 'ts-morph'
import {C3Type, EnumC3Type} from '@kinotic/continuum-idl'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {IConversionContext} from '../IConversionContext.js'
import {ITypeConverter} from '../ITypeConverter.js'

/**
 * Converts a typescript enum to a C3 Enum
 */
export class EnumToC3Type implements ITypeConverter<Type, C3Type, TypescriptConversionState> {

    convert(value: Type, conversionContext: IConversionContext<Type, C3Type, TypescriptConversionState>): C3Type {
        const ret: EnumC3Type = new EnumC3Type()

        ret.name = value.getSymbolOrThrow("No Symbol could be found for object: "+value.getText()).getName()
        ret.namespace = conversionContext.state().namespace

        // this is a little strange, but it is the easiest way to get the name of the enum
        value.getUnionTypes().forEach((unionType: Type) => {
            const name = unionType?.getSymbol()?.getName()
            if(name){
                ret.addValue(name)
            }else {
                throw new Error("Could not find name for enum: " + value.getText())
            }
        })

        return ret
    }

    supports(value: Type, conversionState: TypescriptConversionState): boolean {
        return value.isEnum()
    }

}
