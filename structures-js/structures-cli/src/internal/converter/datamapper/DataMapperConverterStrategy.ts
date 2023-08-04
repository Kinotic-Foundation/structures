import {C3Type} from '@kinotic/continuum-idl'
import {IConverterStrategy, Logger} from '../IConverterStrategy.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {ArrayC3TypeToDataMapper} from './ArrayC3TypeToDataMapper.js'
import {DataMapper} from './DataMapper.js'
import {DataMapperConversionState} from './DataMapperConversionState.js'
import {ObjectC3TypeToDataMapper} from './ObjectC3TypeToDataMapper.js'
import {PrimitiveC3TypeToDataMapper} from './PrimitiveC3TypeToDataMapper.js'
import {UnionC3TypeToDataMapper} from './UnionC3TypeToDataMapper.js'

export class DataMapperConverterStrategy implements IConverterStrategy<C3Type, DataMapper, DataMapperConversionState>{

    private readonly _initialState: (() => DataMapperConversionState) | DataMapperConversionState
    private readonly _logger: Logger
    private readonly _typeConverters = [
        new PrimitiveC3TypeToDataMapper(),
        new ArrayC3TypeToDataMapper(),
        new ObjectC3TypeToDataMapper(),
        new UnionC3TypeToDataMapper()
    ]

    constructor(initialState: (() => DataMapperConversionState) | DataMapperConversionState, logger: Logger) {
        this._initialState = initialState
        this._logger = logger
    }

    initialState(): (() => DataMapperConversionState) | DataMapperConversionState {
        return this._initialState
    }

    logger(): Logger | (() => Logger) {
        return this._logger
    }

    typeConverters(): Array<ITypeConverter<C3Type, DataMapper, DataMapperConversionState>> {
        return this._typeConverters
    }

    valueToString(value: C3Type): string {
        return value.type
    }

}
