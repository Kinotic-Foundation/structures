import {MultiTenancyType} from '@kinotic/structures-api'
import {C3Type, C3Decorator, PropertyDefinition} from '@kinotic/continuum-idl'
import { createStateManager } from './IStateManager.js'

const STRUCTURES_KEY = 'structures'

// FIXME: make sure comments are correct

/**
 * The configuration for a property that should be overridden.
 * This allows you to specify a static {@link PropertyDefinition} for a property.
 */
export class OverrideConfiguration {
    /**
     * The json path to the property that should be overridden.
     */
    jsonPath!: string

    /**
     * The {@link PropertyDefinition} that should be used for the property.
     */
    propertyDefinition!: PropertyDefinition
}

/**
 * The configuration for a function that will transform a value before it is sent to the server.
 * The function must be exported from a file that is specified in {@link StructuresProject.utilFunctionsPaths}
 * The function return argument will be the resulting {@link C3Type} that will be configured for the Entity
 */
export class TransformConfiguration {
    /**
     * The json path to the property that should be transformed.
     * There must only be one {@link TransformConfiguration} per jsonPath.
     */
    jsonPath!: string

    /**
     * The name of the function that will be used to transform the value.
     */
    transformerFunctionName!: string
}

export class CalculatedPropertyConfiguration {

    /**
     * The json path to the Entity that this property will be calculated for.
     * If this is the root Entity then this should be and empty string.
     */
    entityJsonPath!: string

    /**
     * The name of the property that will be calculated and added to the given Entity.
     */
    propertyName!: string

    /**
     * The name of the function that will be used to calculate the value for the property.
     */
    calculatedPropertyFunctionName!: string

    /**
     * Any decorators that should be applied to the calculated property.
     * These will be added to the {@link C3Type} that is created for the Entity.
     */
    decorators?: C3Decorator[]
}

/**
 * The configuration for an External Entity.
 * This allows you to specify an Entity that is part of an external codebase.
 */
export class EntityConfiguration {
    /**
     * The name of the Entity that will be used to create the Structure.
     */
    entityName!: string

    /**
     * The multi tenancy type for this Entity.s
     */
    multiTenancyType!: MultiTenancyType

    /**
     * Json paths to properties that should be excluded from the Entity.
     * These have a higher priority than {@link OverrideConfiguration} and {@link TransformConfiguration}
     */
    excludeJsonPaths?: string[]

    /**
     * Array of {@link OverrideConfiguration} that should be applied to the Entity.
     * These have a higher priority than {@link TransformConfiguration}s
     */
    overrides?: OverrideConfiguration[]

    /**
     * Array of {@link TransformConfiguration} that should be applied to the Entity.
     * These have a lower priority than {@link OverrideConfiguration}s.
     */
    transforms?: TransformConfiguration[]

    /**
     * Array of {@link CalculatedPropertyConfiguration} that should be applied to the Entity.
     * The calculated property function will be called for every instance of the Entity.
     * And is passed the instance of the Entity as the first argument.
     * And the json path as the second argument.
     * These have the highest priority.
     */
    calculatedProperties?: CalculatedPropertyConfiguration[]
}

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
