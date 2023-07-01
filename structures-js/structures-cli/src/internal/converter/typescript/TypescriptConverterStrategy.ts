import {IConverterStrategy, Logger} from '../IConverterStrategy.js'
import {Type} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {ClassToC3Type} from './ClassToC3Type.js'
import {PrimitiveToC3Type} from './PrimitiveToC3Type.js'
import {UnionToC3Type} from './UnionToC3Type.js'
import {ArrayToC3Type} from './ArrayToC3Type.js'

export class TypescriptConverterStrategy implements IConverterStrategy<Type, TypescriptConversionState>{

  private readonly _initialState: (() => TypescriptConversionState) | TypescriptConversionState
  private readonly _logger: Logger
  private readonly _typeConverters = [new PrimitiveToC3Type(),
                                      new ArrayToC3Type(),
                                      new ClassToC3Type(),
                                      new UnionToC3Type()]

  constructor(initialState: (() => TypescriptConversionState) | TypescriptConversionState, logger: Logger) {
    this._initialState = initialState
    this._logger = logger
  }

  initialState(): (() => TypescriptConversionState) | TypescriptConversionState {
    return this._initialState
  }

  logger(): Logger | (() => Logger) {
    return this._logger
  }

  typeConverters(): Array<ITypeConverter<Type, Type, TypescriptConversionState>> {
    return this._typeConverters
  }

  valueToString(value: Type): string {
    // TODO: work on making this as helpful for debugging as possible
    let ret = value?.getSymbol()?.getValueDeclaration()?.print()
    if(!ret){
      ret = value.getText()
    }
    return ret
  }

}
