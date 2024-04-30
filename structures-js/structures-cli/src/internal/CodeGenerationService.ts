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
import {
    createTsMorphProject,
    EntityInfo,
    GeneratedServiceInfo,
    getRelativeImportPath,
    tryGetNodeModuleName
} from './Utils.js'



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
        let namedQueries: FunctionDefinition[] = []
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
            namedQueries = await this.processNamedQueries(entityName + 'EntityService',
                                                          entityServicePath)

            // Below will be used if we go back to the concept of providing the implementation in the base service class

            // namedQueries = await this.createServiceDefinition('Base' + entityName + 'EntityService',
            //                                                   entityName + 'EntityService',
            //                                                   baseEntityServicePath,
            //                                                   entityServicePath)
        }

        return {
            entityServiceName: entityName + 'EntityService',
            namedQueries: namedQueries
        }
    }

    /**
     * Adds invocation logic to named queries and returns the {@link FunctionDefinition}s that define them.
     */
    private async processNamedQueries(entityServiceName: string,
                                      entityServicePath: string): Promise<FunctionDefinition[]>{

        const namedQueries: FunctionDefinition[] = []
        // this.tsMorphProject.addSourceFileAtPath(baseEntityServicePath)
        this.tsMorphProject.addSourceFileAtPath(entityServicePath)
        // const baseEntityServiceSource = this.tsMorphProject.getSourceFile(baseEntityServicePath)
        const entityServiceSource = this.tsMorphProject.getSourceFile(entityServicePath)

        // Currently we only support named queries when added to the generated entity service class
        if(entityServiceSource){

            const serviceClass = entityServiceSource.getClass(entityServiceName)
            // const baseClass = baseEntityServiceSource.getClass(baseEntityServiceName)
            if(serviceClass){

                // Convert all methods that have a @Query decorator to a C3 FunctionDefinition
                // These will be used to define the named queries for the structure
                for(const method of serviceClass?.getInstanceMethods()){

                    const queryDecorator = method.getDecorator('Query')
                    if(queryDecorator){

                        const methodName = method.getName()
                        const functionDefinition = new FunctionDefinition(methodName,
                                                                          [tsDecoratorToC3Decorator(queryDecorator)!])
                        // we assume this is a promise for now
                        if(method.getReturnType().getText().startsWith('Promise')){
                            const typeArguments = method.getReturnType().getTypeArguments()
                            if(typeArguments?.length !== 1){
                                throw new Error('Promise must have exactly one type argument')
                            }
                            const returnType = typeArguments[0]
                            functionDefinition.returnType = this.conversionContext.convert(returnType)
                        }else{
                            throw new Error('Only methods that return a Promise are supported for named queries')
                        }

                        // Find page parameter if any and store all parameter names for later
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

                        // ******
                        // We intended to implement the entitiesService invocation for namedQueries in the Structure service base class.
                        // However, to get this done faster we have decided to add the invocation in the child Structure service
                        // The code directly below is the cut corner, the commented out was the original direction
                        // ******

                        // const parameters = [
                        //     {key: 'who', value: who},
                        //     {key: 'howMany', value: howMany}
                        // ]
                        // return this.namedQueryPage('namedQueryTest', parameters, page)

                        method.setBodyText(writer => {
                            writer.writeLine('/** Autogenerated code, changes will be overwritten. **/')
                            if(argNames.length > 0) {
                                writer.writeLine('const parameters = [')
                                      .indent(() => {
                                          for (let i = 0; i < argNames.length; i++) {
                                              let argName = argNames[i];
                                              writer.write(`{key: '${argName}', value: ${argName}}`)
                                              if (i != argNames.length - 1) {
                                                  writer.write(',')
                                              }
                                              writer.newLine()
                                          }
                                      })
                                      .writeLine(']')
                            }

                            if(pageArgName){
                                writer.writeLine(`return this.namedQueryPage('${methodName}', ${argNames.length > 0 ? 'parameters' : '[]'}, ${pageArgName})`)
                            }else{
                                writer.writeLine(`return this.namedQuery('${methodName}', ${argNames.length > 0 ? 'parameters' : '[]'})`)
                            }
                        })

                        // Code below was the beginning of using the base class.
                        // The logic needs to handle all imports properly since any used would not be in the base class.

                        // remove existing method if it exists
                        // const baseClassMethod = baseClass.getMethod(methodName)
                        // if(baseClassMethod){
                        //     baseClassMethod.remove()
                        // }
                        //
                        // const baseMethod = baseClass.addMethod({
                        //                                            isStatic: false,
                        //                                            name: methodName,
                        //                                            returnType: method.getStructure().returnType,
                        //                                            parameters: method.getStructure().parameters
                        // })
                        namedQueries.push(functionDefinition)
                    }
                }
                await entityServiceSource.save()
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
