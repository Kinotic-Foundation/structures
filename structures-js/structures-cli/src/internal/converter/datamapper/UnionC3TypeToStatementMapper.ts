import {ArrayC3Type, C3Type, UnionC3Type} from '@kinotic/continuum-idl'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'
import {
    StatementMapper,
    MultiStatementMapper,
} from './StatementMapper'
import {StatementMapperConversionState} from './StatementMapperConversionState'

export class UnionC3TypeToStatementMapper implements ITypeConverter<C3Type, StatementMapper, StatementMapperConversionState> {

    convert(value: C3Type, conversionContext: IConversionContext<C3Type, StatementMapper, StatementMapperConversionState>): StatementMapper {
        const unionC3Type = value as UnionC3Type
        const ret: MultiStatementMapper = new MultiStatementMapper(conversionContext.state())
        for(let value of unionC3Type.types){
            ret.add(conversionContext.convert(value))
        }
        return ret
    }

    supports(value: C3Type, conversionState: StatementMapperConversionState): boolean {
        return value instanceof UnionC3Type
    }
}
