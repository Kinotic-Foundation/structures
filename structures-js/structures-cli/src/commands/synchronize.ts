import {Args, Command, Flags} from '@oclif/core'
import path from 'path'
import {ObjectC3Type} from '@kinotic/continuum-idl'
import {resolveServer} from '../internal/state/Environment.js'
import {
    Continuum
} from '@kinotic/continuum-client'
import {Structures, IStructureService, Structure} from '@kinotic/structures-api'
import {isStructuresProject, loadStructuresProject} from '../internal/state/StructuresProject.js'
import {EntityInfo, connectAndUpgradeSession, convertAllEntities, ConversionConfiguration} from '../internal/Utils.js'
import {TransformerFunctionLocator} from '../internal/TransformerFunctionLocator.js'
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

            const serverConfig = await resolveServer(this.config.configDir, flags.server)

            if (await connectAndUpgradeSession(serverConfig.url, this)) {
                try {

                    await Structures.getNamespaceService().createNamespaceIfNotExist(namespaceConfig.namespaceName, '')

                    const transformerFunctionLocator = new TransformerFunctionLocator(namespaceConfig.transformerFunctionsPaths || [], flags.verbose)

                    for(const entitiesPath of namespaceConfig.entitiesPaths) {
                        const config: ConversionConfiguration = {
                            namespace: namespaceConfig.namespaceName,
                            entitiesPath: entitiesPath,
                            verbose: flags.verbose,
                            transformerFunctionLocator: transformerFunctionLocator,
                            logger: this
                        }
                        await this.processEntityPath(config, namespaceConfig.generatedPath, flags.publish)
                    }

                    for(const [externalEntitiesPath, entityConfigurations] of Object.entries(namespaceConfig.externalEntitiesPaths || {})){
                        const config: ConversionConfiguration = {
                            namespace: namespaceConfig.namespaceName,
                            entitiesPath: externalEntitiesPath,
                            verbose: flags.verbose,
                            transformerFunctionLocator: transformerFunctionLocator,
                            entityConfigurations: entityConfigurations,
                            logger: this
                        }
                        await this.processEntityPath(config, namespaceConfig.generatedPath, flags.publish)
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

    private async processEntityPath(config: ConversionConfiguration, generatedPath: string, publish: boolean){
        if (!fs.existsSync(config.entitiesPath)) {
            throw new Error(`Entities path does not exist: ${config.entitiesPath}`)
        }

        const convertedEntities: EntityInfo[] = convertAllEntities(config)

        if (convertedEntities.length > 0) {

            for (const entityInfo of convertedEntities) {
                await this.synchronizeEntity(entityInfo.entity, publish, config.verbose)

                await this.generateEntityService(entityInfo, generatedPath)
            }

            this.logVerbose(`Synchronization Complete For namespace: ${config.namespace} and Entities Path: ${config.entitiesPath}`, config.verbose)
        } else {
            this.logVerbose(`No Entities found to Synchronize For namespace: ${config.namespace} and Entities Path: ${config.entitiesPath}`, config.verbose)
        }
    }

    private async synchronizeEntity(entity:  ObjectC3Type, publish: boolean, verbose: boolean): Promise<void> {
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
        } catch (e) {
            this.log(chalk.red('Error') + ` Synchronizing Structure: ${namespace}.${name}`)
        }
    }

    private async generateEntityService(entityInfo: EntityInfo, generatedPath: string): Promise<void> {
        const entityServicePath = path.resolve(generatedPath, entityInfo.entity.name + 'EntityService.ts')
        //  we only generate if the file does not exist
        if (!fs.existsSync(entityServicePath)) {
            const entityName = entityInfo.entity.name
            const entityNamespace = entityInfo.entity.namespace
            const defaultExport = entityInfo.defaultExport
            const exportName = entityInfo.exportedAs
            const importPath = this.getRelativeImportPath(entityServicePath, entityInfo.exportedFromFile)
            const readStream= await engine.renderFileToNodeStream('EntityService',
                {
                    entityName,
                    entityNamespace,
                    defaultExport,
                    exportName,
                    importPath
                })
            let writeStream = fs.createWriteStream(entityServicePath)
            readStream.pipe(writeStream)
        }
    }

    private getRelativeImportPath(from: string, to: string) {
        const fromDir = path.dirname(from);
        let relativePath = path.relative(fromDir, to)

        // Make sure path starts with './' or '../'
        if (!relativePath.startsWith('../') && !relativePath.startsWith('./')) {
            relativePath = `./${relativePath}`
        }

        // Remove '.ts' extension
        relativePath = relativePath.replace(/\.ts$/, '')
        return relativePath;
    }

    private logVerbose(message: string, verbose: boolean): void {
        if (verbose) {
            this.log(message)
        }
    }
}


