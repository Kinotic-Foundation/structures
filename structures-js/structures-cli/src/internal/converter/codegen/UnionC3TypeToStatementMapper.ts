import {C3Type, ObjectC3Type, UnionC3Type} from '@kinotic/continuum-idl'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'
import {
    StatementMapper,
    MultiStatementMapper,
} from './StatementMapper.js'
import {StatementMapperConversionState} from './StatementMapperConversionState.js'

export class UnionC3TypeToStatementMapper implements ITypeConverter<C3Type, StatementMapper, StatementMapperConversionState> {

    convert(value: C3Type, conversionContext: IConversionContext<C3Type, StatementMapper, StatementMapperConversionState>): StatementMapper {
        const unionC3Type = value as UnionC3Type
        const ret: MultiStatementMapper = new MultiStatementMapper(conversionContext.state())

        // to map the union type we merge all the properties into a single object
        let tempObject = new ObjectC3Type()
        for(let objectC3Type of unionC3Type.types){
            const properties = objectC3Type.properties
            for(const propertyName in properties) {
                const property = properties[propertyName]
                // We only add the property if it doesn't already exist, since this is a union type there will be duplicates
                if(!tempObject.properties[propertyName]){
                    tempObject.addProperty(propertyName, property)
                }
            }
        }

        ret.add(conversionContext.convert(tempObject))

        return ret
    }

    supports(value: C3Type, conversionState: StatementMapperConversionState): boolean {
        return value instanceof UnionC3Type
    }
}
