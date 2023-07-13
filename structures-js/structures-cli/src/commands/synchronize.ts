import {Args, Command, Flags} from '@oclif/core'
import path from 'node:path'
import {promises as fs} from 'node:fs'
import {ClassDeclaration, Project} from 'ts-morph'
import {C3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import {createConversionContext} from '../internal/converter/IConversionContext.js'
import {TypescriptConverterStrategy} from '../internal/converter/typescript/TypescriptConverterStrategy.js'
import {TypescriptConversionState} from '../internal/converter/typescript/TypescriptConversionState.js'
import {tsDecoratorToC3Decorator} from '../internal/converter/typescript/Utils.js'
import {Environment, loadEnvironment, saveEnvironment} from '../internal/state/Environment.js'
import {
  Continuum
} from '@kinotic/continuum-client'
import {Structures, IStructureService, Structure} from '@kinotic/structures-api'
import {connectAndUpgradeSession, Logger} from '../internal/Utils.js'
import { input } from '@inquirer/prompts'
import { WebSocket } from 'ws'

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
    server: Flags.string({char: 's', description: 'The structures server to connect to', required: true}),
    debug: Flags.boolean({char: 'd', description: 'Enable debug logging', required: false, default: false}),
  }

  static args = {
    namespace: Args.string({description: 'The namespace the Entities belong to', required: true}),
  }

  async run(): Promise<void> {
    const {args, flags} = await this.parse(Synchronize)

    //const environment = await loadEnvironment(this.config.configDir)
    let server: string = flags.server || ''

    // if(flags.server){
    //   server = flags.server
    //   // save server as default
    //   environment.defaultServer = server
    //   await saveEnvironment(this.config.configDir, environment)
    // } else {
    //   if(!environment.defaultServer){
    //     this.log("No default server configured. Please specify a server with --server or set a default server.")
    //     return;
    //   }
    //   server = environment.defaultServer
    // }

    if(await connectAndUpgradeSession(server, this)) {

      try {
        const entitiesPath = path.resolve(flags.entities)
        const entities: ObjectC3Type[] = this.findAllEntities(entitiesPath, args.namespace, flags.debug)

        if(entities.length> 0) {

          for (const entity of entities) {
            await this.synchronizeEntity(entity, this)
          }
          this.log('Synchronization complete')

        }else{
          this.log('No entities found to synchronize')
        }

      } catch (e) {
        if(e instanceof Error) {
          this.error(e.message)
        }
      }
    }
    await Continuum.disconnect()
    return
  }

  private async writeEntitiesJsonToFilesystem(namespace: string, entities: ObjectC3Type[]): Promise<void> {
    // save the c3types to the local filesystem
    const json = JSON.stringify(entities, replacer, 2)
    if(json && json.length > 0) {
      const outputPath = path.resolve('.structures', 'entity-definitions', namespace + '.json')
      await fs.mkdir(path.dirname(outputPath), {recursive: true})
      await fs.writeFile(outputPath, json)
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

    logger.log(`Synchronizing structure: ${namespace}.${name}`)

    try {
      let structure = await structureService.findById(structureId)
      if (structure) {
        if (structure.published) {
          const answer = await input({message: `The structure ${namespace}.${name} is published.\nYou must Un-Publish to save.\nCAUTION: This will delete all of your data.\nType "${name}" to Up-Publish or Press Enter to Skip.`})
          if (answer === name) {
            logger.log(`Un-Publishing structure: ${namespace}.${name}`)
            await structureService.unPublish(structureId)
          } else {
            logger.log(`Skipping synchronization of structure: ${namespace}.${name}`)
            return
          }
        }
        // update existing structure
        structure.entityDefinition = entity
        logger.log(`Updating structure: ${namespace}.${name}`)
        await structureService.save(structure)
      } else {
        structure = new Structure(namespace, name, entity)
        console.log(`Creating structure: ${namespace}.${name}`)
        await structureService.create(structure)
      }
    } catch (e) {
      logger.log(`Error synchronizing structure: ${namespace}.${name}`)
    }
  }

  private checkIfServerExists(environment: Environment, server: string): boolean {
    return Object.hasOwn(environment.servers, server)
  }

}


