import {
    ConnectedInfo,
    ConnectionInfo,
    Continuum,
    EventConstants,
    IEvent,
    Event,
    ParticipantConstants
} from '@kinotic/continuum-client'
import {EntityDecorator} from '@kinotic/structures-api'
import fs from 'fs'
import {Node, Project} from 'ts-morph'
import { v4 as uuidv4 } from 'uuid'
import inquirer from 'inquirer'
import open from 'open'
import pTimeout from 'p-timeout'
import {C3Type, ObjectC3Type} from '@kinotic/continuum-idl'
import path from 'path'
import fsPromises from 'fs/promises'
import {createConversionContext} from './converter/IConversionContext.js'
import {Logger} from './converter/IConverterStrategy.js'
import {tsDecoratorToC3Decorator} from './converter/typescript/ConverterUtils.js'
import {TypescriptConversionState} from './converter/typescript/TypescriptConversionState.js'
import {TypescriptConverterStrategy} from './converter/typescript/TypescriptConverterStrategy.js'
import {EntityConfiguration} from './state/StructuresProject.js'
import {TransformerFunctionLocator} from './TransformerFunctionLocator.js'

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

export function jsonStringifyReplacer(key: any, value: any) {
    return isEmpty(value)
           ? undefined
           : value;
}

/**
 * Connects to the server and upgrades the session to a CLI session
 * Currently this works by connecting and waiting for the clients session id on the event bus
 * The cli then disconnects and reconnects using the clients' session.
 * This will be replaced when the server supports a session upgrade command
 * @param server the server to connect to
 * @param logger the logger to use
 * @return true if the session was upgraded successfully
 */
export async function connectAndUpgradeSession(server: string, logger: Logger): Promise<boolean>{
    try {
        const serverURL: URL = new URL(server)

        if(serverURL.protocol !== 'http:' && serverURL.protocol !== 'https:'){
            logger.log('Invalid server URL, only http and https are supported')
            return false
        }

        let connectionInfo: ConnectionInfo = {host: ''}
        if (serverURL.hostname === 'localhost' || serverURL.hostname === '127.0.0.1') {
            connectionInfo.host = serverURL.hostname
            connectionInfo.port = 58503
        } else {
            connectionInfo.host = serverURL.hostname
            if(serverURL.protocol === 'https:'){
                connectionInfo.useSSL = true
            }
            if(serverURL.port){
                connectionInfo.port = 58503
            }
        }

        connectionInfo.connectHeaders = {
            login: ParticipantConstants.CLI_PARTICIPANT_ID
        }
        const connectedInfo: ConnectedInfo = await pTimeout(Continuum.connect(connectionInfo), {
            milliseconds: 30000,
            message: 'Connection timeout trying to connect to the Structures Server'
        })

        if (connectedInfo) {

            const scope = connectedInfo.replyToId + ':' + uuidv4()
            const url = server + (server.endsWith('/') ? '' : '/') + '#/sessionUpgrade/' + encodeURIComponent(scope)
            logger.log('Authenticate your account at:')
            logger.log(url)

            const answers = inquirer.prompt({
                type: 'confirm',
                name: 'confirm',
                message: 'Open in browser?',
                default: true,
            })
            answers.then((value: any) => {
                if(value?.confirm){
                    open(url)
                }else{
                    logger.log('Browser will not be opened. You must authenticate your account before continuing.')
                }
            }, (reason: any) => {
                // noop, since canceling the promise throws an error
            })

            const sessionId = await receiveSessionId(scope)

            // We got session id we don't care about the prompt anymore
            // This happens if the user opens the url in the browser and authenticates
            // @ts-ignore
            answers.ui.close()

            await Continuum.disconnect()

            connectionInfo.connectHeaders = {
                session: sessionId
            }

            await Continuum.connect(connectionInfo)
            logger.log('Authenticated successfully\n')
            return true
        }else{
            logger.log("Could not connect to the Structures Server. Please check the server is running and the URL is correct.")
            return false
        }
    } catch (e) {
        logger.log("Could not connect to the Structures Server. Please check the server is running and the URL is correct.", e)
        return false
    }
}

function receiveSessionId(scope: string): Promise<string> {
    const subscribeCRI = EventConstants.SERVICE_DESTINATION_PREFIX + scope + '@continuum.cli.SessionUpgradeService'

    return new Promise<string>(( resolve, reject) => {
        const subscription = Continuum.eventBus.observe(subscribeCRI).subscribe((value: IEvent) => {
            // send reply to user
            const replyTo = value.getHeader(EventConstants.REPLY_TO_HEADER)
            if (replyTo) {
                const replyEvent = new Event(replyTo)
                const correlationId = value.getHeader(EventConstants.CORRELATION_ID_HEADER)
                if (correlationId) {
                    replyEvent.setHeader(EventConstants.CORRELATION_ID_HEADER, correlationId)
                }
                replyEvent.setHeader(EventConstants.CONTENT_TYPE_HEADER, EventConstants.CONTENT_JSON)
                Continuum.eventBus.send(replyEvent)
            }

            subscription.unsubscribe()

            const jsonObj: Array<string> = JSON.parse(value.getDataString())
            if(jsonObj?.length > 0){
                resolve(jsonObj[0])
            }else {
                reject('No Session Id found in data')
            }
        })
    })
}

export type EntityInfo = {
    exportedFromFile: string,
    exportedAs?: string,
    defaultExport: boolean,
    entity: ObjectC3Type
    entityConfiguration?: EntityConfiguration
}

export type ConversionConfiguration = {
    namespace: string,
    entitiesPath: string,
    verbose: boolean,
    transformerFunctionLocator: TransformerFunctionLocator,
    entityConfigurations?: EntityConfiguration[]
    logger: Logger,
}

function getEntityDecoratorIfExists(node: Node){
    if(Node.isClassDeclaration(node)){
        return node.getDecorator('Entity')
    }
}

export function pathToTsGlobPath(path: string): string{
    return path.endsWith('.ts') ? path : (path.endsWith('/') ? path + '*.ts' : path + '/*.ts')
}

export function convertAllEntities(config: ConversionConfiguration): EntityInfo[]{
    const entities: EntityInfo[] = []
    const tsConfigFilePath = path.resolve('tsconfig.json')
    if(!fs.existsSync(tsConfigFilePath)){
        throw new Error(`No tsconfig.json found in working directory: ${process.cwd()}`)
    }

    const entityConfigMap = new Map<string, EntityConfiguration>()
    if(config.entityConfigurations) {
        for (const entityConfiguration of config.entityConfigurations) {
            entityConfigMap.set(entityConfiguration.entityName, entityConfiguration)
        }
    }

    const project = new Project({
        tsConfigFilePath: tsConfigFilePath
    })

    if(config.verbose) {
        project.enableLogging(true)
    }

    project.addSourceFilesAtPaths(pathToTsGlobPath(config.entitiesPath))

    const sourceFiles = project.getSourceFiles()
    for (const sourceFile of sourceFiles) {

        const conversionContext =
                  createConversionContext(new TypescriptConverterStrategy(new TypescriptConversionState(config.namespace,
                      config.transformerFunctionLocator),
                      config.logger))

        const exportedDeclarations = sourceFile.getExportedDeclarations()
        exportedDeclarations.forEach((exportedDeclarationEntries, name) => {
            exportedDeclarationEntries.forEach((exportedDeclaration) => {
                if (Node.isClassDeclaration(exportedDeclaration) || Node.isInterfaceDeclaration(exportedDeclaration)){
                    const declaration = exportedDeclaration
                    // We only convert entities
                    const decorator = getEntityDecoratorIfExists(exportedDeclaration)
                    const declarationName = declaration.getName()
                    const entityConfig = (declarationName ? entityConfigMap.get(declarationName) : undefined)
                    if(decorator || entityConfig) {

                        let c3Type: C3Type | null = null
                        try {
                            conversionContext.state().entityConfiguration = entityConfig
                            c3Type = conversionContext.convert(declaration.getType())
                        } catch (e) {} // We ignore this error since the converter will print any errors

                        if (c3Type != null) {

                            if(decorator) {
                                c3Type.addDecorator(tsDecoratorToC3Decorator(decorator))
                            }else if(entityConfig){
                                const entityDecorator= new EntityDecorator()
                                entityDecorator.multiTenancyType = entityConfig.multiTenancyType
                                c3Type.addDecorator(entityDecorator)
                            }

                            if (c3Type instanceof ObjectC3Type) {
                                entities.push({
                                    exportedFromFile: declaration.getSourceFile().getFilePath(),
                                    exportedAs: declaration.getName(),
                                    defaultExport: declaration.isDefaultExport(),
                                    entity: c3Type,
                                    entityConfiguration: entityConfig
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

/**
 * Will save the C3Type(s) to the local filesystem
 * @param namespace to save the entities to
 * @param entities to save
 */
export async function writeEntitiesJsonToFilesystem(namespace: string, entities: ObjectC3Type[]): Promise<void> {
    const json = JSON.stringify(entities, jsonStringifyReplacer, 2)
    if (json && json.length > 0) {
        const outputPath = path.resolve('.structures', 'entity-definitions', namespace + '.json')
        await fsPromises.mkdir(path.dirname(outputPath), {recursive: true})
        await fsPromises.writeFile(outputPath, json)
    }
}
