import {ArrayC3Type, C3Type, UnionC3Type} from '@kinotic/continuum-idl'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'
import {
    DataMapper,
    MultiDataMapper,
} from './DataMapper.js'
import {DataMapperConversionState} from './DataMapperConversionState.js'

export class UnionC3TypeToDataMapper  implements ITypeConverter<C3Type, DataMapper, DataMapperConversionState> {

    convert(value: C3Type, conversionContext: IConversionContext<C3Type, DataMapper, DataMapperConversionState>): DataMapper {
        const unionC3Type = value as UnionC3Type
        const ret: MultiDataMapper = new MultiDataMapper(conversionContext.state())
        for(let value of unionC3Type.types){
            ret.add(conversionContext.convert(value))
        }
        return ret
    }

    supports(value: C3Type, conversionState: DataMapperConversionState): boolean {
        return value instanceof UnionC3Type
    }
}
