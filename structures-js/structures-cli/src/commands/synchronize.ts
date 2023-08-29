import {Args, Command, Flags} from '@oclif/core'
import path from 'path'
import {ObjectC3Type} from '@kinotic/continuum-idl'
import {StatementMapper, createImportString} from '../internal/converter/codegen/StatementMapper.js'
import {StatementMapperConversionState} from '../internal/converter/codegen/StatementMapperConversionState.js'
import {StatementMapperConverterStrategy} from '../internal/converter/codegen/StatementMapperConverterStrategy.js'
import {createConversionContext} from '../internal/converter/IConversionContext.js'
import {resolveServer} from '../internal/state/Environment.js'
import {
    Continuum
} from '@kinotic/continuum-client'
import {Structures, IStructureService, Structure} from '@kinotic/structures-api'
import {
    isStructuresProject,
    loadStructuresProject,
    NamespaceConfiguration
} from '../internal/state/StructuresProject.js'
import {
    EntityInfo,
    connectAndUpgradeSession,
    convertAllEntities,
    ConversionConfiguration,
    writeEntityJsonToFilesystem,
    getRelativeImportPath, tryGetNodeModuleName
} from '../internal/Utils.js'
import {UtilFunctionLocator} from '../internal/UtilFunctionLocator.js'
import inquirer from 'inquirer'
import chalk from 'chalk'
import { Liquid } from 'liquidjs'
import fs from 'fs'
import {fileURLToPath} from 'url'
import { WebSocket } from 'ws'

// This is required when running Continuum from node
Object.assign(global, { WebSocket})

const filename = fileURLToPath(import.meta.url)
const engine = new Liquid({
    root: path.resolve(path.dirname(filename), '../templates/'),  // root for templates lookup
    extname: '.liquid'
});

export class Synchronize extends Command {
    static description = 'Synchronize the local Entity definitions with the Structures Server'

    static examples = [
        '$ structures synchronize my.namespace --server http://localhost:9090 --publish --verbose',
        '$ structures synchronize my.namespace -p',
        '$ structures synchronize',
    ]

    static flags = {
        server:     Flags.string({char: 's', description: 'The structures server to connect to'}),
        publish:    Flags.boolean({char: 'p', description: 'Publish each Entity after save/update'}),
        verbose:    Flags.boolean({char: 'v', description: 'Enable verbose logging'}),
        dryRun:     Flags.boolean({description: 'Dry run enables verbose logging and does not save any changes to the server'})
    }

    static args = {
        namespace: Args.string({description: 'The namespace the Entities belong to', required: false}),
    }

    async run(): Promise<void> {
        const {args, flags} = await this.parse(Synchronize)

        try {

            if(!(await isStructuresProject())){
                this.error('The working directory is not a Structures Project')
                return
            }

            const structuresProject= await loadStructuresProject()

            let namespaceConfig
            if(args.namespace){
                namespaceConfig = structuresProject.findNamespaceConfig(args.namespace)
                if(namespaceConfig === null){
                    this.error(`No configured namespace found with name ${args.namespace}`)
                    return
                }
            }else{
                namespaceConfig = structuresProject.getDefaultNamespaceConfig()
            }

            let serverUrl = ''
            if(!flags.dryRun) {
                const serverConfig = await resolveServer(this.config.configDir, flags.server)
                serverUrl = serverConfig.url
            }

            if (flags.dryRun || await connectAndUpgradeSession(serverUrl, this)) {
                try {

                    if(!flags.dryRun) {
                        await Structures.getNamespaceService().createNamespaceIfNotExist(namespaceConfig.namespaceName, '')
                    }

                    const utilFunctionLocator = new UtilFunctionLocator(namespaceConfig || [], flags.verbose)

                    for(const entitiesPath of namespaceConfig.entitiesPaths) {
                        const config: ConversionConfiguration = {
                            namespace: namespaceConfig.namespaceName,
                            entitiesPath: entitiesPath,
                            utilFunctionLocator: utilFunctionLocator,
                            verbose: flags.verbose || flags.dryRun,
                            dryRun: flags.dryRun,
                            logger: this
                        }
                        await this.processEntities(config, namespaceConfig, structuresProject.fileExtensionForImports, flags.publish)
                    }

                    for(const [externalEntitiesPath, entityConfigurations] of Object.entries(namespaceConfig.externalEntities || {})){
                        const config: ConversionConfiguration = {
                            namespace: namespaceConfig.namespaceName,
                            entitiesPath: externalEntitiesPath,
                            utilFunctionLocator: utilFunctionLocator,
                            entityConfigurations: entityConfigurations,
                            verbose: flags.verbose || flags.dryRun,
                            dryRun: flags.dryRun,
                            logger: this
                        }
                        await this.processEntities(config, namespaceConfig, structuresProject.fileExtensionForImports, flags.publish)
                    }

                } catch (e) {
                    if (e instanceof Error) {
                        this.error(e.message)
                    }
                }
            }
            await Continuum.disconnect()
        } catch (e) {
            if(e instanceof Error){
                this.log(chalk.red('Error: ') + e.message)
            }else{
                this.log(chalk.red('Error: ') + e as string)
            }
            await Continuum.disconnect()
        }
        return
    }

    private async processEntities(config: ConversionConfiguration,
                                  namespaceConfig: NamespaceConfiguration,
                                  fileExtensionForImports: string,
                                  publish: boolean){

        if (!fs.existsSync(config.entitiesPath)) {
            throw new Error(`Entities path does not exist: ${config.entitiesPath}`)
        }

        const convertedEntities: EntityInfo[] = convertAllEntities(config)

        if (convertedEntities.length > 0) {

            for (const entityInfo of convertedEntities) {

                this.logVerbose(`Generated Structure Mapping for ${entityInfo.entity.namespace}.${entityInfo.entity.name}`, config.verbose)

                await this.generateEntityService(entityInfo, namespaceConfig, config.utilFunctionLocator, fileExtensionForImports)

                if(config.verbose){
                    await writeEntityJsonToFilesystem(namespaceConfig.generatedPath, entityInfo.entity, this)
                }

                if(!config.dryRun) {
                    await this.synchronizeEntity(entityInfo.entity, publish, config.verbose)
                }
            }

            this.logVerbose(`Synchronization Complete For namespace: ${config.namespace} and Entities Path: ${config.entitiesPath}`, config.verbose)
        } else {
            this.logVerbose(`No Entities found to Synchronize For namespace: ${config.namespace} and Entities Path: ${config.entitiesPath}`, config.verbose)
        }
    }

    private async synchronizeEntity(entity:  ObjectC3Type,
                                    publish: boolean,
                                    verbose: boolean): Promise<void> {
        const structureService: IStructureService = Structures.getStructureService()
        const namespace = entity.namespace
        const name = entity.name
        const structureId = (namespace + '.' + name).toLowerCase()

        this.log(`Synchronizing Structure: ${namespace}.${name}`)

        try {
            let structure = await structureService.findById(structureId)
            if (structure) {
                if (structure.published) {
                    this.log(chalk.bold(`Structure ${namespace}.${name} is Published.`)+' (You must Un-Publish to save the Structure)')
                    this.log(chalk.bold.red('CAUTION: This will Delete all of your data.'))
                    const answers = await inquirer.prompt({
                        type: 'input',
                        name: 'input',
                        message: `Type ${chalk.blue(name)} to Up-Publish or Press Enter to Skip.`,
                    })
                    if (answers.input === name) {
                        this.logVerbose(`Un-Publishing Structure: ${namespace}.${name}`, verbose)
                        await structureService.unPublish(structureId)
                    } else {
                        this.logVerbose(`Skipping Synchronization of Structure: ${namespace}.${name}`, verbose)
                        return
                    }
                }
                // update existing structure
                structure.entityDefinition = entity
                this.logVerbose(`Updating Structure: ${namespace}.${name}`, verbose)

                structure = await structureService.save(structure)
            } else {
                structure = new Structure(namespace, name, entity)
                this.logVerbose(`Creating Structure: ${namespace}.${name}`, verbose)

                structure = await structureService.create(structure)
            }
            // publish if we need to
            if(publish && structure.id !== null){
                this.logVerbose(`Publishing Structure: ${namespace}.${name}`, verbose)

                await structureService.publish(structure.id)
            }
        } catch (e: any) {
            const message = e?.message || e
            this.log(chalk.red('Error') + ` Synchronizing Structure: ${namespace}.${name}, Exception: ${message}`)
        }
    }

    private async generateEntityService(entityInfo: EntityInfo,
                                        namespaceConfig: NamespaceConfiguration,
                                        utilFunctionLocator: UtilFunctionLocator,
                                        fileExtensionForImports: string): Promise<void> {

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
        const baseReadStream= await engine.renderFileToNodeStream('BaseEntityService',
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
        if (!fs.existsSync(entityServicePath)) {
            const readStream= await engine.renderFileToNodeStream('EntityService',
                {
                    entityName,
                    entityNamespace,
                    validate,
                    fileExtensionForImports
                })
            let writeStream = fs.createWriteStream(entityServicePath)
            readStream.pipe(writeStream)
        }
    }

    private createStatementMapper(generatedServicePath: string,
                                  entityInfo: EntityInfo,
                                  utilFunctionLocator: UtilFunctionLocator): StatementMapper{
        const state = new StatementMapperConversionState(utilFunctionLocator)
        state.entityConfiguration = entityInfo.entityConfiguration

        const conversionContext =
                  createConversionContext(new StatementMapperConverterStrategy(state, this))
        return conversionContext.convert(entityInfo.entity)
    }

    private logVerbose(message: string | ( () => string ), verbose: boolean): void {
        if (verbose) {
            if (typeof message === 'function') {
                this.log(message())
            }else{
                this.log(message)
            }
        }
    }
}


