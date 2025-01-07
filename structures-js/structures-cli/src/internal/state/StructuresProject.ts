import {CalculatedPropertyConfiguration} from '../config/CalculatedPropertyConfiguration.js'
import {EntityConfiguration} from '../config/EntityConfiguration.js'
import {TransformConfiguration} from '../config/TransformConfiguration.js'
import {createStateManager} from './IStateManager.js'

const STRUCTURES_KEY = 'structures'

// FIXME: make sure comments are correct

export class NamespaceConfiguration {
    /**
     * The name of the Namespace that all Structures created for this configuration will be created in.
     */
    namespaceName!: string

    /**
     * The paths to search for classes decorated with @Entity that Structures will be created for.
     */
    entitiesPaths!: string[]

    /**
     * The path to where generated files will be placed.
     */
    generatedPath!: string

    /**
     * External EntityConfigurations that are part of an external codebase and that you cannot add @Entity decorators to.
     */
    externalEntities?: {
        [pathPattern: string] : EntityConfiguration[]
    }

    /**
     * The path to look for files that export functions that can be used by a {@link TransformConfiguration} or {@link CalculatedPropertyConfiguration}.
     */
    utilFunctionsPaths?: string[]

    /**
     * If true the generated EntityService classes will validate all data before sending to the server.
     */
    validate?: boolean
}

export class StructuresProject {

    /**
     * The configurations for all namespaces in this project.
     */
    namespaces: NamespaceConfiguration[] = []

    /**
     * The name of the default namespace.
     * This is the namespace that will be used if no namespace is specified.
     */
    defaultNamespaceName!: string

    /**
     * The file extension to use for imports in generated files.
     */
    fileExtensionForImports: string = '.js'

    hasNamespaceConfig(name: string): boolean {
        return this.findNamespaceConfig(name) !== null
    }

    findNamespaceConfigOrDefault(namespaceName?: string): NamespaceConfiguration {
        let ret: NamespaceConfiguration | null
        if(namespaceName){
            ret = this.findNamespaceConfig(namespaceName)
            if(ret === null){
                throw new Error(`No configured namespace found with name ${namespaceName}`)
            }
        }else{
            ret = this.getDefaultNamespaceConfig()
        }
        return ret
    }

    findNamespaceConfig(namespaceName: string): NamespaceConfiguration | null {
        let ret: NamespaceConfiguration | null = null
        for(const namespace of this.namespaces){
            if(namespace.namespaceName === namespaceName){
                ret = namespace
                break
            }
        }
        return ret
    }

    getDefaultNamespaceConfig(): NamespaceConfiguration {
        if(this.defaultNamespaceName) {
            const ret = this.findNamespaceConfig(this.defaultNamespaceName)
            if (ret === null) {
                throw new Error(`No default namespace found with name ${this.defaultNamespaceName}`)
            }
            return ret
        }else{
            throw new Error('No default namespace name has been set')
        }
    }
}

export async function isStructuresProject(): Promise<boolean> {
    const stateManager = createStateManager(process.cwd())
    return await stateManager.containsState(STRUCTURES_KEY)
}

export async function loadStructuresProject(): Promise<StructuresProject>{
    const stateManager = createStateManager(process.cwd())
    if(await stateManager.containsState(STRUCTURES_KEY)){
        const jsonProject = await stateManager.load<StructuresProject>(STRUCTURES_KEY)
        const ret = new StructuresProject() // we do this to ensure that the object has the correct prototype
        if(jsonProject.namespaces){
            ret.namespaces = jsonProject.namespaces
        }
        ret.defaultNamespaceName = jsonProject.defaultNamespaceName
        return ret
    }else {
        return new StructuresProject()
    }
}

export async function saveStructuresProject(project: StructuresProject): Promise<void> {
    const stateManager = createStateManager(process.cwd())
    await stateManager.save(STRUCTURES_KEY, project)
}
