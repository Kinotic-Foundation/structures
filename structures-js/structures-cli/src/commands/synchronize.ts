import {Args, Command, Flags} from '@oclif/core'
import path from 'node:path'
import {promises as fs} from 'node:fs'
import {ClassDeclaration, Project} from 'ts-morph'
import {C3Type} from '@kinotic/continuum-idl'
import {createConversionContext} from '../internal/converter/IConversionContext.js'
import {TypescriptConverterStrategy} from '../internal/converter/typescript/TypescriptConverterStrategy.js'
import {TypescriptConversionState} from '../internal/converter/typescript/TypescriptConversionState.js'
import {tsDecoratorToC3Decorator} from '../internal/converter/typescript/Utils.js'
import {Environment, loadEnvironment, saveEnvironment} from '../internal/state/Environment.js'
import {
  ConnectedInfo,
  ConnectionInfo,
  Continuum,
  EventConstants,
  IEvent,
  ParticipantConstants
} from '@kinotic/continuum-client'
import { v4 as uuidv4 } from 'uuid'

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
    `$ structures synchronize my.namespace --entities path/to/entities`,
  ]

  static flags = {
    entities: Flags.string({char: 'e', description: 'Path to the directory containing the Entity definitions', required: true}),
    server: Flags.string({char: 's', description: 'The structures server to connect to', required: false}),
    debug: Flags.boolean({char: 'd', description: 'Enable debug logging', required: false, default: false}),
  }

  static args = {
    namespace: Args.string({description: 'The namespace the Entities belong to', required: true}),
  }

  async run(): Promise<void> {
    const {args, flags} = await this.parse(Synchronize)
    const entities: C3Type[] = []

    const environment = await loadEnvironment(this.config.configDir)
    let server: string

    if(flags.server){
      server = flags.server
      // save server as default
      environment.defaultServer = server
      await saveEnvironment(this.config.configDir, environment)
    } else {
      if(!environment.defaultServer){
        this.log("No default server configured. Please specify a server with --server or set a default server.")
        return;
      }
      server = environment.defaultServer
    }


    const project = new Project({ // TODO: make sure there is a tsconfig.json in the entities directory
      tsConfigFilePath: path.resolve('tsconfig.json')
    })

    if(flags.debug) {
      project.enableLogging(true)
    }
    const entitiesPath = path.resolve(flags.entities)
    project.addSourceFilesAtPaths(entitiesPath + '/*.ts')

    const sourceFiles = project.getSourceFiles(entitiesPath +'/*.ts')
    for (const sourceFile of sourceFiles) {

      const conversionContext = createConversionContext(new TypescriptConverterStrategy(new TypescriptConversionState(args.namespace, project), this))

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

                entities.push(c3Type)
              }else{
                this.log(`Error: Could not convert ${name} to a C3Type. The process will terminate.`)
                return;
              }
            }
          }
        })
      })
    }

    // save the c3types to the local filesystem
    const json = JSON.stringify(entities, replacer, 2)
    if(flags.debug){
      this.log("Entities JSON:")
      this.log(json)
    }

    if(json && json.length > 0) {
      const outputPath = path.resolve('.structures', 'entity-definitions', args.namespace + '.json')
      await fs.mkdir(path.dirname(outputPath), {recursive: true})
      await fs.writeFile(outputPath, json)
    }else{
      this.log("No entities found to synchronize.")
    }
  }

  /**
   * Connects to the server and upgrades the session to a CLI session
   * Currently this works by connecting and waiting for the clients session id on the event bus
   * The cli then disconnects and reconnects using the clients' session.
   * This will be replaced when the server supports a session upgrade command
   * @param server the server to connect to
   * @return true if the session was upgraded successfully
   */
  private async connectAndUpgradeSession(server: string): Promise<boolean>{
    let ret: boolean = false
    try {
      const serverURL: URL = new URL(server)

      let connectionInfo: ConnectionInfo = {host: ''}
      if (serverURL.host === 'localhost' || serverURL.host === '127.0.0.1') {
        connectionInfo.host = serverURL.host
        connectionInfo.port = parseInt(serverURL.port)
      } else {
        connectionInfo.host = serverURL.host
        if(serverURL.protocol === 'https:'){
          connectionInfo.useSSL = true
        }
        if(serverURL.port){
          connectionInfo.port = parseInt(serverURL.port)
        }
      }

      connectionInfo.connectHeaders = {
        login: ParticipantConstants.CLI_PARTICIPANT_ID
      }
      const connectedInfo: ConnectedInfo = await Continuum.connect(connectionInfo)

      if (connectedInfo) {
        const subscribeCRI = EventConstants.SERVICE_DESTINATION_PREFIX
          + ParticipantConstants.CLI_PARTICIPANT_ID
          + ':' + uuidv4() + '@continuum.cli.EventBus/sessionHandler'

        const session: Promise<string> = new Promise<string>((resolve) => {
          Continuum.eventBus.observe(subscribeCRI).subscribe((value: IEvent) => {
              resolve(value.getDataString())
          })
        })

        const sessionId = await session

        await Continuum.disconnect()

        connectionInfo.connectHeaders = {
          session: sessionId
        }

        await Continuum.connect(connectionInfo)

        ret = true
      }else{
        this.log("Could not connect to the Structures Server. Please check the server is running and the URL is correct.")
      }
    } catch (e) {
      this.log("Could not connect to the Structures Server. Please check the server is running and the URL is correct.", e)
    }
    return ret
  }

  private checkIfServerExists(environment: Environment, server: string): boolean {
    return Object.hasOwn(environment.servers, server)
  }

}


