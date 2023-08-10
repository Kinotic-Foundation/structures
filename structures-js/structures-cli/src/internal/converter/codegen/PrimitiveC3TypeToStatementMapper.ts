import {
    BooleanC3Type,
    ByteC3Type,
    C3Type,
    CharC3Type,
    DateC3Type,
    DoubleC3Type, EnumC3Type, FloatC3Type, IntC3Type, LongC3Type,
    ShortC3Type, StringC3Type
} from '@kinotic/continuum-idl'
import {IConversionContext} from '../IConversionContext.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {LiteralStatementMapper, StatementMapper} from './StatementMapper.js'
import {StatementMapperConversionState} from './StatementMapperConversionState.js'

export class PrimitiveC3TypeToStatementMapper implements ITypeConverter<C3Type, StatementMapper, StatementMapperConversionState> {

    convert(value: C3Type, conversionContext: IConversionContext<C3Type, StatementMapper, StatementMapperConversionState>): StatementMapper {
        const targetName = conversionContext.state().targetName
        const sourceName = conversionContext.state().sourceName
        const lhs = targetName + (conversionContext.currentJsonPath.length > 0 ? '.' + conversionContext.currentJsonPath : '')
        const rhs = sourceName + (conversionContext.currentJsonPath.length > 0 ? '.' + conversionContext.currentJsonPath : '')
        return new LiteralStatementMapper(`${lhs} = ${rhs}`)
    }

    supports(value: C3Type, conversionState: StatementMapperConversionState): boolean {
        return value instanceof BooleanC3Type
            || value instanceof ByteC3Type
            || value instanceof CharC3Type
            || value instanceof DateC3Type
            || value instanceof DoubleC3Type
            || value instanceof EnumC3Type
            || value instanceof FloatC3Type
            || value instanceof IntC3Type
            || value instanceof LongC3Type
            || value instanceof ShortC3Type
            || value instanceof StringC3Type
    }
}
