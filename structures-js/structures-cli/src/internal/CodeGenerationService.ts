import {C3Type, FunctionDefinition} from '@kinotic/continuum-idl'
import fs from 'fs'
import {Liquid} from 'liquidjs'
import path from 'path'
import {Project, Type} from 'ts-morph'
import {fileURLToPath} from 'url'
import {createImportString, StatementMapper} from './converter/codegen/StatementMapper.js'
import {StatementMapperConversionState} from './converter/codegen/StatementMapperConversionState.js'
import {StatementMapperConverterStrategy} from './converter/codegen/StatementMapperConverterStrategy.js'
import {createConversionContext, IConversionContext} from './converter/IConversionContext.js'
import {tsDecoratorToC3Decorator} from './converter/typescript/ConverterUtils.js'
import {TypescriptConversionState} from './converter/typescript/TypescriptConversionState.js'
import {TypescriptConverterStrategy} from './converter/typescript/TypescriptConverterStrategy.js'
import {Logger} from './Logger.js'
import {NamespaceConfiguration} from './state/StructuresProject.js'
import {UtilFunctionLocator} from './UtilFunctionLocator.js'
import {createTsMorphProject, EntityInfo, getRelativeImportPath, tryGetNodeModuleName} from './Utils.js'


export type GeneratedServiceInfo = {
    entityServiceName: string
    namedQueries: FunctionDefinition[] | null
}

/**
 * Helper service for generating code.
 */
export class CodeGenerationService {

    private readonly logger: Logger
    private readonly engine: Liquid
    private readonly tsMorphProject: Project
    private readonly conversionContext: IConversionContext<Type, C3Type, TypescriptConversionState>

    constructor(namespace: string,
                logger: Logger){
        this.logger = logger
        this.engine = new Liquid({
                                     root: path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../templates/'),  // root for templates lookup
                                     extname: '.liquid'
                                 });
        this.tsMorphProject = createTsMorphProject()

        this.conversionContext
           = createConversionContext(new TypescriptConverterStrategy(new TypescriptConversionState(namespace,
                                                                                                   null),
                                                                     logger))
    }

    public async generateEntityService(entityInfo: EntityInfo,
                                       namespaceConfig: NamespaceConfiguration,
                                       utilFunctionLocator: UtilFunctionLocator,
                                       fileExtensionForImports: string): Promise<GeneratedServiceInfo> {

        const generatedPath = namespaceConfig.generatedPath
        const baseEntityServicePath = path.resolve(generatedPath, 'generated', `Base${entityInfo.entity.name}EntityService.ts`)
        const entityServicePath = path.resolve(generatedPath, `${entityInfo.entity.name}EntityService.ts`)

        const entityName = entityInfo.entity.name
        const entityNamespace = entityInfo.entity.namespace
        const defaultExport = entityInfo.defaultExport
        const validate = namespaceConfig.validate

        let entityImportPath = tryGetNodeModuleName(entityInfo.exportedFromFile)
        if(!entityImportPath){
            entityImportPath = getRelativeImportPath(baseEntityServicePath, entityInfo.exportedFromFile, fileExtensionForImports)
        }

        const statement = this.createStatementMapper(baseEntityServicePath,
                                                     entityInfo,
                                                     utilFunctionLocator)
        const validationLogic = statement.toStatementString()
        const importStatements = createImportString(statement, baseEntityServicePath, fileExtensionForImports) || ''

        //  We always generate the base entity service. This way if our internal logic changes we can update it
        fs.mkdirSync(path.dirname(baseEntityServicePath), {recursive: true})
        const baseReadStream= await this.engine.renderFileToNodeStream('BaseEntityService',
                                                                  {
                                                                      entityName,
                                                                      entityNamespace,
                                                                      defaultExport,
                                                                      entityImportPath,
                                                                      validationLogic,
                                                                      importStatements
                                                                  })
        let baseWriteStream = fs.createWriteStream(baseEntityServicePath)
        baseReadStream.pipe(baseWriteStream)

        //  we only generate if the file does not exist
        let namedQueries: FunctionDefinition[] | null = null
        if (!fs.existsSync(entityServicePath)) {
            const readStream= await this.engine.renderFileToNodeStream('EntityService',
                                                                  {
                                                                      entityName,
                                                                      entityNamespace,
                                                                      validate,
                                                                      fileExtensionForImports
                                                                  })
            let writeStream = fs.createWriteStream(entityServicePath)
            readStream.pipe(writeStream)
        } else {
            // if it already exists we check if there are any named queries defined
            namedQueries = await this.createServiceDefinition('Base' + entityName + 'EntityService',
                                                              entityName + 'EntityService',
                                                              baseEntityServicePath,
                                                              entityServicePath)
        }

        return {
            entityServiceName: entityName + 'EntityService',
            namedQueries: namedQueries
        }
    }

    private async createServiceDefinition(baseEntityServiceName: string,
                                          entityServiceName: string,
                                          baseEntityServicePath: string,
                                          entityServicePath: string): Promise<FunctionDefinition[]>{

        const namedQueries: FunctionDefinition[] = []
        this.tsMorphProject.addSourceFileAtPath(baseEntityServicePath)
        this.tsMorphProject.addSourceFileAtPath(entityServicePath)
        const baseEntityServiceSource = this.tsMorphProject.getSourceFile(baseEntityServicePath)
        const entityServiceSource = this.tsMorphProject.getSourceFile(entityServicePath)

        // Currently we only support named queries when added to the generated entity service class
        if(entityServiceSource && baseEntityServiceSource){

            const serviceClass = entityServiceSource.getClass(entityServiceName)
            const baseClass = baseEntityServiceSource.getClass(baseEntityServiceName)
            if(serviceClass && baseClass){

                // Convert all methods that have a @Query decorator to a C3 FunctionDefinition
                // These will be used to define the named queries for the structure
                for(const method of serviceClass?.getInstanceMethods()){

                    const queryDecorator = method.getDecorator('Query')
                    if(queryDecorator){

                        const methodName = method.getName()
                        const functionDefinition = new FunctionDefinition(methodName,
                                                                                 [tsDecoratorToC3Decorator(queryDecorator)!])
                        functionDefinition.returnType = this.conversionContext.convert(method.getReturnType())

                        const argNames: string[] = []
                        let pageArgName: string | null = null
                        for(let parameter of method.getParameters()){
                            const parameterName = parameter.getName()
                            const parameterTypeName = parameter.getType().getSymbol()?.getName();

                            if(parameterTypeName === 'Pageable'
                                || parameterTypeName === 'OffsetPageable'
                                || parameterTypeName === 'CursorPageable') {
                                pageArgName = parameterName
                            } else {
                                argNames.push(parameterName)
                            }

                            functionDefinition.addArgument(parameterName,
                                                           this.conversionContext.convert(parameter.getType()))
                        }

                        const methodStruct = method.getStructure()

                        baseClass.addMethod()

                        namedQueries.push(functionDefinition)
                    }
                }
                await baseEntityServiceSource.save()
            }
        }
        return namedQueries
    }

    private createStatementMapper(generatedServicePath: string,
                                  entityInfo: EntityInfo,
                                  utilFunctionLocator: UtilFunctionLocator): StatementMapper{
        const state = new StatementMapperConversionState(utilFunctionLocator)
        state.entityConfiguration = entityInfo.entityConfiguration

        const conversionContext = createConversionContext(new StatementMapperConverterStrategy(state, this.logger))
        return conversionContext.convert(entityInfo.entity)
    }


}
