import {Args, Command, Flags} from '@oclif/core'
import path from 'path'
import {ClassDeclaration, Project} from 'ts-morph'
import {C3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import {createConversionContext} from '../internal/converter/IConversionContext.js'
import {TypescriptConverterStrategy} from '../internal/converter/typescript/TypescriptConverterStrategy.js'
import {TypescriptConversionState} from '../internal/converter/typescript/TypescriptConversionState.js'
import {tsDecoratorToC3Decorator} from '../internal/converter/typescript/ConverterUtils.js'
import {resolveServer} from '../internal/state/Environment.js'
import {
    Continuum
} from '@kinotic/continuum-client'
import {Structures, IStructureService, Structure} from '@kinotic/structures-api'
import {connectAndUpgradeSession} from '../internal/Utils.js'
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

type EntityInfo = {
    exportedFromFile: string,
    exportedAs?: string,
    defaultExport: boolean,
    entity: ObjectC3Type
}

export class Synchronize extends Command {
    static description = 'Synchronize the local Entity definitions with the Structures Server'

    static examples = [
        '$ structures synchronize my.namespace --entities path/to/entities --generated path/to/services --server http://localhost:9090 --publish --verbose',
        '$ structures synchronize my.namespace -e path/to/entities -g path/to/services -p',
    ]

    static flags = {
        entities:   Flags.string({char: 'e', description: 'Path to the directory containing the Entity definitions', required: true}),
        generated:  Flags.string({char: 'g', description: 'Path to the directory to write generated Services', required: true}),
        server:     Flags.string({char: 's', description: 'The structures server to connect to'}),
        publish:    Flags.boolean({char: 'p', description: 'Publish each Entity after save/update'}),
        verbose:    Flags.boolean({char: 'v', description: 'Enable verbose logging'}),
    }

    static args = {
        namespace: Args.string({description: 'The namespace the Entities belong to', required: true}),
    }

    async run(): Promise<void> {
        const {args, flags} = await this.parse(Synchronize)

        try {

            const entitiesPath = path.resolve(flags.entities)
            const generatedPath = path.resolve(flags.generated)

            if(!fs.existsSync(entitiesPath)){
                this.error(`Entities path does not exist: ${entitiesPath}`)
                return
            }
            if(!fs.existsSync(generatedPath)){
                this.error(`Generated path does not exist: ${generatedPath}`)
                return
            }

            const serverConfig = await resolveServer(this.config.configDir, flags.server)

            if (await connectAndUpgradeSession(serverConfig.url, this)) {
                try {

                    const convertedEntities: EntityInfo[] = this.findAllEntities(entitiesPath, args.namespace, flags.verbose)

                    await Structures.getNamespaceService().createNamespaceIfNotExist(args.namespace, '')

                    if (convertedEntities.length > 0) {

                        for (const entityInfo of convertedEntities) {
                            await this.synchronizeEntity(entityInfo.entity, flags.publish, flags.verbose)

                            await this.generateEntityService(entityInfo, generatedPath)
                        }

                        this.log('Synchronization Complete')

                    } else {
                        this.log('No Entities found to Synchronize')
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
                this.error(e.message)
            }else{
                this.error(e as string)
            }
        }
        return
    }

    private findAllEntities(entitiesPath: string, namespace: string, verbose: boolean): EntityInfo[]{
        const entities: EntityInfo[] = []
        const tsConfigFilePath = path.resolve('tsconfig.json')
        if(!fs.existsSync(tsConfigFilePath)){
            this.error(`No tsconfig.json found in working directory: ${process.cwd()}`)
        }
        const project = new Project({
            tsConfigFilePath: tsConfigFilePath
        })

        if(verbose) {
            project.enableLogging(true)
        }
        project.addSourceFilesAtPaths(entitiesPath + '/*.ts')

        const sourceFiles = project.getSourceFiles(entitiesPath +'/*.ts')
        for (const sourceFile of sourceFiles) {

            const conversionContext =
                      createConversionContext(new TypescriptConverterStrategy(new TypescriptConversionState(namespace, project), this))

            const exportedDeclarations = sourceFile.getExportedDeclarations()
            exportedDeclarations.forEach((exportedDeclarationEntries, name) => {
                exportedDeclarationEntries.forEach((exportedDeclaration) => {
                    if (ClassDeclaration.isClassDeclaration(exportedDeclaration)) {
                        const classDeclaration = exportedDeclaration as ClassDeclaration
                        // We only convert entities
                        const decorator = classDeclaration.getDecorator('Entity')
                        if(decorator != null) {

                            let c3Type: C3Type | null = null
                            try {
                                c3Type = conversionContext.convert(classDeclaration.getType())
                            } catch (e) {} // We ignore this error since the converter will print any errors

                            if (c3Type != null) {

                                c3Type.addDecorator(tsDecoratorToC3Decorator(decorator))

                                if (c3Type instanceof ObjectC3Type) {
                                    entities.push({
                                        exportedFromFile: classDeclaration.getSourceFile().getFilePath(),
                                        exportedAs: classDeclaration.getName(),
                                        defaultExport: classDeclaration.isDefaultExport(),
                                        entity: c3Type
                                    })
                                }else{
                                    throw new Error(`Error: Could not convert ${name} to a C3Type`)
                                }
                            }else{
                                throw new Error(`Error: Could not convert ${name} to a C3Type`)
                            }
                        }
                    }
                })
            })
        }
        return entities
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
                        if(verbose) {
                            this.log(`Un-Publishing Structure: ${namespace}.${name}`)
                        }
                        await structureService.unPublish(structureId)
                    } else {
                        if(verbose) {
                            this.log(`Skipping Synchronization of Structure: ${namespace}.${name}`)
                        }
                        return
                    }
                }
                // update existing structure
                structure.entityDefinition = entity
                if(verbose) {
                    this.log(`Updating Structure: ${namespace}.${name}`)
                }
                structure = await structureService.save(structure)
            } else {
                structure = new Structure(namespace, name, entity)
                if(verbose) {
                    this.log(`Creating Structure: ${namespace}.${name}`)
                }
                structure = await structureService.create(structure)
            }
            // publish if we need to
            if(publish && structure.id !== null){
                if(verbose) {
                    this.log(`Publishing Structure: ${namespace}.${name}`)
                }
                await structureService.publish(structure.id)
            }
        } catch (e) {
            this.log(`Error Synchronizing Structure: ${namespace}.${name}`)
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

}


