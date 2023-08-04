import {C3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'
import {DataMapper, MultiDataMapper, AssignmentDataMapper} from './DataMapper.js'
import {DataMapperConversionState} from './DataMapperConversionState.js'

export class ObjectC3TypeToDataMapper  implements ITypeConverter<C3Type, DataMapper, DataMapperConversionState> {

    convert(value: C3Type, conversionContext: IConversionContext<C3Type, DataMapper, DataMapperConversionState>): DataMapper {
        const objectC3Type = value as ObjectC3Type
        const ret: MultiDataMapper = new MultiDataMapper(conversionContext.state())
        const targetName = conversionContext.state().targetName
        const sourceName = conversionContext.state().sourceName
        const lhs = targetName + (conversionContext.currentJsonPath.length > 0 ? '.' + conversionContext.currentJsonPath : '')
        const condition = sourceName + (conversionContext.currentJsonPath.length > 0 ? '.' + conversionContext.currentJsonPath : '')

        ret.addLiteral('if ('+condition+') {')
        conversionContext.state().indentMore()
        ret.add(new AssignmentDataMapper(lhs, '{}'))

        const properties = objectC3Type.properties
        for(const propertyName in properties){
            const property = properties[propertyName]
            conversionContext.beginProcessingProperty(propertyName)

            ret.add(conversionContext.convert(property))

            conversionContext.endProcessingProperty()
        }

        conversionContext.state().indentLess()
        ret.addLiteral('}')

        return ret
    }

    supports(value: C3Type, conversionState: DataMapperConversionState): boolean {
        return value instanceof ObjectC3Type
    }
}
