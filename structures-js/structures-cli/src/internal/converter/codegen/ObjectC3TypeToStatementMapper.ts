import {C3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import {FunctionDeclaration} from 'ts-morph'
import {getRelativeImportPath} from '../../Utils.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'
import {
    StatementMapper,
    MultiStatementMapper,
    LiteralStatementMapper
} from './StatementMapper.js'
import {StatementMapperConversionState} from './StatementMapperConversionState.js'

export class ObjectC3TypeToStatementMapper implements ITypeConverter<C3Type, StatementMapper, StatementMapperConversionState> {

    convert(value: C3Type, conversionContext: IConversionContext<C3Type, StatementMapper, StatementMapperConversionState>): StatementMapper {
        const state = conversionContext.state()
        const objectC3Type = value as ObjectC3Type
        const ret: MultiStatementMapper = new MultiStatementMapper(state)
        const targetName = state.targetName
        const sourceName = state.sourceName
        const lhs = targetName + (conversionContext.currentJsonPath.length > 0 ? '.' + conversionContext.currentJsonPath : '')
        const condition = sourceName + (conversionContext.currentJsonPath.length > 0 ? '.' + conversionContext.currentJsonPath : '')

        ret.addLiteral('if ('+condition+') {')
        state.indentMore()
        ret.add(new LiteralStatementMapper(`${lhs} = (${lhs} ? ${lhs} : {})`))

        const properties = objectC3Type.properties
        for(const propertyName in properties){
            const property = properties[propertyName]
            conversionContext.beginProcessingProperty(propertyName)

            const functionStatement = this.handleCalculatedPropsIfNeeded(conversionContext);

            if(functionStatement){
                ret.add(functionStatement)
            }else{
                ret.add(conversionContext.convert(property))
            }

            conversionContext.endProcessingProperty()
        }

        state.indentLess()
        ret.addLiteral('}')

        return ret
    }

    private handleCalculatedPropsIfNeeded(conversionContext: IConversionContext<C3Type, StatementMapper, StatementMapperConversionState>): LiteralStatementMapper | null{
        const state = conversionContext.state()
        let ret: LiteralStatementMapper | null = null

        const calculatedProperties = state.getCalculatedProperties(conversionContext.currentJsonPath)
        if(calculatedProperties){
            if(calculatedProperties.length > 1){
                throw new Error(`Only one calculated property is allowed per property. Found ${calculatedProperties.length} for ${conversionContext.currentJsonPath}`)
            }
            const functionDeclaration = state.getUtilFunctionByName(calculatedProperties[0].calculatedPropertyFunctionName)
            if(!functionDeclaration){
                throw new Error(`Could not find function declaration for ${calculatedProperties[0].calculatedPropertyFunctionName}`)
            }
            ret = this.createStatementForFunction(functionDeclaration, conversionContext)
        }

        if(!ret){
            const transformerFunction = state.getTransformFunction(conversionContext.currentJsonPath)
            if(transformerFunction) {
                ret = this.createStatementForFunction(transformerFunction, conversionContext)
            }
        }
        return ret
    }

    private createStatementForFunction(functionDeclaration: FunctionDeclaration,
                                       conversionContext: IConversionContext<C3Type, StatementMapper, StatementMapperConversionState>): LiteralStatementMapper{
        const targetName = conversionContext.state().targetName
        const lhs = targetName + (conversionContext.currentJsonPath.length > 0 ? '.' + conversionContext.currentJsonPath : '')
        const rhs = conversionContext.state().sourceName
        const functionName = functionDeclaration.getNameOrThrow('Could not find function name')
        let ret: LiteralStatementMapper = new LiteralStatementMapper(`${lhs} = ${functionName}(${rhs})`)
        let importPath = getRelativeImportPath(conversionContext.state().generatedServicePath, functionDeclaration.getSourceFile().getFilePath())
        ret.neededImports.push({importName: functionName, importPath: importPath, defaultExport: functionDeclaration.isDefaultExport()})
        return ret
    }

    supports(value: C3Type, conversionState: StatementMapperConversionState): boolean {
        return value instanceof ObjectC3Type
    }
}
