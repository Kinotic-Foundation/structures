import {Continuum} from '@kinotic/continuum-client'
import {FunctionDefinition, ObjectC3Type} from '@kinotic/continuum-idl'
import {INamedQueriesService, IStructureService, NamedQueriesDefinition, Structure, Structures} from '@kinotic/structures-api'
import {Args, Command, Flags} from '@oclif/core'
import chalk from 'chalk'
import {WebSocket} from 'ws'
import {CodeGenerationService} from '../internal/CodeGenerationService.js'
import {resolveServer} from '../internal/state/Environment.js'
import {isStructuresProject, loadStructuresProject} from '../internal/state/StructuresProject.js'
import {connectAndUpgradeSession} from '../internal/Utils.js'

// This is required when running Continuum from node
Object.assign(global, { WebSocket})

export class Synchronize extends Command {
    static aliases = ['sync']

    static description = 'Synchronize the local Entity definitions with the Structures Server'

    static examples = [
        '$ structures synchronize',
        '$ structures sync',
        '$ structures synchronize my.namespace --server http://localhost:9090 --publish --verbose',
        '$ structures sync my.namespace -p -v -s http://localhost:9090'
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
            }

            const structuresProject= await loadStructuresProject()

            let namespaceConfig = structuresProject.findNamespaceConfigOrDefault(args.namespace)

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

                    const codeGenerationService = new CodeGenerationService(namespaceConfig.namespaceName,
                                                                                                 structuresProject.fileExtensionForImports,
                                                                                                 this)

                    await codeGenerationService
                            .generateAllEntities(namespaceConfig,
                                                 flags.verbose || flags.dryRun,
                                                 async (entityInfo, serviceInfo) =>{
                                                     // We sync named queries first since currently the backend cache eviction logic is a little dumb
                                                     // i.e. The cache eviction for the structure deletes the GraphQL schema
                                                     //      This will evict the named query execution plan cache
                                                     //      We want to make sure the GraphQL schema is updated after both these are updated and the structure below
                                                     if(!flags.dryRun && serviceInfo.namedQueries.length > 0){
                                                         await this.synchronizeNamedQueries(entityInfo.entity, serviceInfo.namedQueries)
                                                     }

                                                     if(!flags.dryRun) {
                                                         await this.synchronizeEntity(entityInfo.entity, flags.publish, flags.verbose)
                                                     }
                                                 })

                    this.log(`Synchronization Complete For namespace: ${namespaceConfig.namespaceName}`, flags.verbose)

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

    public logVerbose(message: string | ( () => string ), verbose: boolean): void {
        if (verbose) {
            if (typeof message === 'function') {
                this.log(message())
            }else{
                this.log(message)
            }
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
                    this.log(chalk.bold(`Structure ${chalk.blue(namespace + '.' + name)} is Published. ${chalk.yellow('(Supported Modifications: New Fields. Un-Publish for all other changes.)')}`))
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
            if(!structure.published && publish && structure?.id){
                this.logVerbose(`Publishing Structure: ${namespace}.${name}`, verbose)

                await structureService.publish(structure.id)
            }
        } catch (e: any) {
            const message = e?.message || e
            this.log(chalk.red('Error') + ` Synchronizing Structure: ${structureId}, Exception: ${message}`)
        }
    }

    private async synchronizeNamedQueries(entity:       ObjectC3Type,
                                          namedQueries: FunctionDefinition[]): Promise<void> {
        const namedQueriesService: INamedQueriesService = Structures.getNamedQueriesService()
        const namespace = entity.namespace
        const structure = entity.name
        const id = (namespace + '.' + structure).toLowerCase()

        this.log(`Synchronizing Named Queries for Structure: ${namespace}.${structure}`)

        try {
            const namedQueriesDefinition = new NamedQueriesDefinition(id,
                                                                      namespace,
                                                                      structure,
                                                                      namedQueries)
            await namedQueriesService.save(namedQueriesDefinition)
        } catch (e: any) {
            const message = e?.message || e
            this.log(chalk.red('Error') + ` Synchronizing Named Queries for Structure: ${id}, Exception: ${message}`)
        }
    }
}


