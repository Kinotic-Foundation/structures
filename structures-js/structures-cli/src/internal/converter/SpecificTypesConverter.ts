import {ITypeConverter} from './ITypeConverter.js'
import {IConversionContext} from './IConversionContext.js'
import {C3Type} from '@kinotic/continuum-idl'

export class SpecificTypesConverter<BASE_TYPE, S, MATCH_VALUE> implements ITypeConverter<BASE_TYPE, BASE_TYPE, S> {

  private readonly matchValueExtractor: (arg: BASE_TYPE) => MATCH_VALUE
  private typeConverterFunctions: Map<MATCH_VALUE, ((type: BASE_TYPE, context: IConversionContext<BASE_TYPE, S>) => C3Type)>


  constructor(matchValueExtractor: (arg: BASE_TYPE) => MATCH_VALUE,
              typeConverterFunctions: Map<MATCH_VALUE, ((type: BASE_TYPE, context: IConversionContext<BASE_TYPE, S>) => C3Type)>) {
    this.matchValueExtractor = matchValueExtractor
    this.typeConverterFunctions = typeConverterFunctions
  }

  convert(value: BASE_TYPE, conversionContext: IConversionContext<BASE_TYPE, S>): C3Type {
    const matchValue: MATCH_VALUE = this.matchValueExtractor(value)
    const converterFunction = this.typeConverterFunctions.get(matchValue)
    if(!converterFunction){
      // Should never happen due to supports call!
      throw new Error("Converter function could not be resolved, WAT!")
    }
    return converterFunction(value, conversionContext)
  }

  supports(value: BASE_TYPE): boolean {
    const matchValue: MATCH_VALUE = this.matchValueExtractor(value)
    return this.typeConverterFunctions.has(matchValue)
  }

}
