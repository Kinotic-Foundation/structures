import {GraphQLType, GraphQLList} from 'graphql/type/index.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'
import {GqlConversionState} from './GqlConversionState.js'
import {ArrayC3Type, C3Type} from '@kinotic/continuum-idl'

export class GqlArrayToC3Type implements ITypeConverter<GraphQLType, C3Type, GqlConversionState> {

    convert(value: GraphQLType, conversionContext: IConversionContext<GraphQLType, C3Type, GqlConversionState>): C3Type {
        if (value instanceof GraphQLList) {
            const elementType = conversionContext.convert(value.ofType)
            return new ArrayC3Type(elementType)
        }
        throw new Error('Type could not be found for array type: ' + value.toString())
    }

    supports(value: GraphQLType, conversionState: GqlConversionState): boolean {
        return value instanceof GraphQLList
    }
}
