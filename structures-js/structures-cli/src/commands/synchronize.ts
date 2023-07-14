import {Args, Command, Flags} from '@oclif/core'
import path from 'path'
import fsPromises from 'fs/promises'
import {ClassDeclaration, Project} from 'ts-morph'
import {C3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import {createConversionContext} from '../internal/converter/IConversionContext.js'
import {TypescriptConverterStrategy} from '../internal/converter/typescript/TypescriptConverterStrategy.js'
import {TypescriptConversionState} from '../internal/converter/typescript/TypescriptConversionState.js'
import {tsDecoratorToC3Decorator} from '../internal/converter/typescript/Utils.js'
import {resolveServer} from '../internal/state/Environment.js'
import {
    Continuum
} from '@kinotic/continuum-client'
import {Structures, IStructureService, Structure} from '@kinotic/structures-api'
import {connectAndUpgradeSession, Logger} from '../internal/Utils.js'
import inquirer from 'inquirer'
import { WebSocket } from 'ws'
import chalk from 'chalk'

// This is required when running Continuum from node
Object.assign(global, { WebSocket})

function isEmpty(value: any): boolean {
    if (value === null || value === undefined) {
        return true;
    }

    if (Array.isArray(value)) {
        return value.every(isEmpty);
    }
    else if (typeof (value) === 'object') {
        return Object.values(value).every(isEmpty);
    }

    return false;
}

function replacer(key: any, value: any) {
    return isEmpty(value)
           ? undefined
           : value;
}

export class Synchronize extends Command {
    static description = 'Synchronize the local Entity definitions with the Structures Server'

    static examples = [
        `$ structures synchronize my.namespace --entities path/to/entities --server http://localhost:8080 --debug`,
    ]

    static flags = {
        entities: Flags.string({char: 'e', description: 'Path to the directory containing the Entity definitions', required: true}),
        server: Flags.string({char: 's', description: 'The structures server to connect to'}),
        debug: Flags.boolean({char: 'd', description: 'Enable debug logging', required: false, default: false}),
    }

    static args = {
        namespace: Args.string({description: 'The namespace the Entities belong to', required: true}),
    }

    async run(): Promise<void> {
        const {args, flags} = await this.parse(Synchronize)

        try {
            const serverConfig = await resolveServer(this.config.configDir, flags.server)

            if (await connectAndUpgradeSession(serverConfig.url, this)) {
                try {
                    const entitiesPath = path.resolve(flags.entities)
                    const entities: ObjectC3Type[] = this.findAllEntities(entitiesPath, args.namespace, flags.debug)

                    await Structures.getNamespaceService().createNamespaceIfNotExist(args.namespace, '')

                    if (entities.length > 0) {

                        for (const entity of entities) {
                            await this.synchronizeEntity(entity, this)
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

    private async writeEntitiesJsonToFilesystem(namespace: string, entities: ObjectC3Type[]): Promise<void> {
        // save the c3types to the local filesystem
        const json = JSON.stringify(entities, replacer, 2)
        if(json && json.length > 0) {
            const outputPath = path.resolve('.structures', 'entity-definitions', namespace + '.json')
            await fsPromises.mkdir(path.dirname(outputPath), {recursive: true})
            await fsPromises.writeFile(outputPath, json)
        }
    }

    private findAllEntities(entitiesPath: string, namespace: string, debug: boolean): ObjectC3Type[]{
        const entities: ObjectC3Type[] = []
        const project = new Project({ // TODO: make sure there is a tsconfig.json in the working directory
            tsConfigFilePath: path.resolve('tsconfig.json')
        })

        if(debug) {
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
                        // We only convert entities TODO: see if we can insure this is actually a structures decorator Entity
                        const decorator = exportedDeclaration.getDecorator('Entity')
                        if(decorator != null) {

                            let c3Type: C3Type | null = null
                            try {
                                c3Type = conversionContext.convert(exportedDeclaration.getType())
                            } catch (e) {} // We ignore this error since the converter will print any errors

                            if (c3Type != null) {

                                c3Type.addDecorator(tsDecoratorToC3Decorator(decorator))

                                if (c3Type instanceof ObjectC3Type) {
                                    entities.push(c3Type)
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

    private async synchronizeEntity(entity:  ObjectC3Type, logger: Logger): Promise<void> {
        const structureService: IStructureService = Structures.getStructureService()
        const namespace = entity.namespace
        const name = entity.name
        const structureId = (namespace + '.' + name).toLowerCase()

        logger.log(`\nSynchronizing Structure: ${namespace}.${name}\n`)

        try {
            let structure = await structureService.findById(structureId)
            if (structure) {
                if (structure.published) {
                    logger.log(chalk.bold(`Structure ${namespace}.${name} is Published.`)+' (You must Un-Publish to save the Structure)')
                    logger.log(chalk.bold.red('CAUTION: This will Delete all of your data.'))
                    const answers = await inquirer.prompt({
                        type: 'input',
                        name: 'input',
                        message: `Type ${chalk.blue(name)} to Up-Publish or Press Enter to Skip.`,
                    })
                    if (answers.input === name) {
                        logger.log(`Un-Publishing Structure: ${namespace}.${name}`)
                        await structureService.unPublish(structureId)
                    } else {
                        logger.log(`Skipping Synchronization of Structure: ${namespace}.${name}`)
                        return
                    }
                }
                // update existing structure
                structure.entityDefinition = entity
                logger.log(`Updating Structure: ${namespace}.${name}`)
                await structureService.save(structure)
            } else {
                structure = new Structure(namespace, name, entity)
                console.log(`Creating Structure: ${namespace}.${name}`)
                await structureService.create(structure)
            }
        } catch (e) {
            logger.log(`Error Synchronizing Structure: ${namespace}.${name}`)
        }
    }

}


