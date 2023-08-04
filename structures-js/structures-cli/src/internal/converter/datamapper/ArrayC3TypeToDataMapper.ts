import {ArrayC3Type, C3Type} from '@kinotic/continuum-idl'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'
import {
    DataMapper,
    MultiDataMapper,
} from './DataMapper.js'
import {DataMapperConversionState} from './DataMapperConversionState.js'

export class ArrayC3TypeToDataMapper  implements ITypeConverter<C3Type, DataMapper, DataMapperConversionState> {

    convert(value: C3Type, conversionContext: IConversionContext<C3Type, DataMapper, DataMapperConversionState>): DataMapper {
        const arrayC3Type = value as ArrayC3Type

        const ret: MultiDataMapper = new MultiDataMapper(conversionContext.state())
        const currentJsonPath = conversionContext.currentJsonPath
        const currentPropertyStack = conversionContext.propertyStack
        const targetName = conversionContext.state().targetName
        const lhs = targetName + (currentJsonPath.length > 0 ? '.' + currentJsonPath : '')
        const sourceName = conversionContext.state().sourceName
        const sourceJsonPath = sourceName + (currentJsonPath.length > 0 ? '.' + currentJsonPath : '')

        const itemName = sourceJsonPath.replaceAll('.', '') + 'Item'
        const internalLhsName = lhs.replaceAll('.', '') + 'Value'

        ret.addLiteral(`if (${sourceJsonPath}) {`);
        conversionContext.state().indentMore()
        ret.addLiteral(`${lhs} = []`);
        ret.addLiteral(`for (let ${itemName} of ${sourceJsonPath}) {`);
        conversionContext.state().indentMore()
        ret.addLiteral(`let ${internalLhsName}`)

        if(arrayC3Type.contains) {
            conversionContext.state().sourceName = itemName
            conversionContext.state().targetName = internalLhsName
            conversionContext.currentJsonPath = ''
            conversionContext.propertyStack = []

            ret.add(conversionContext.convert(arrayC3Type.contains))

            conversionContext.currentJsonPath = currentJsonPath
            conversionContext.propertyStack = currentPropertyStack
            conversionContext.state().sourceName = sourceName
            conversionContext.state().targetName = targetName
        }else{
            throw new Error('ArrayC3Type.contains is undefined')
        }
        ret.addLiteral(`${lhs}.push(${internalLhsName})`)

        conversionContext.state().indentLess()
        ret.addLiteral('}')

        conversionContext.state().indentLess()
        ret.addLiteral('}')

        return ret
    }

    supports(value: C3Type, conversionState: DataMapperConversionState): boolean {
        return value instanceof ArrayC3Type
    }
}
