import { createStateManager } from './IStateManager.js'
import path from 'path'

const STRUCTURES_KEY = 'structures'

export class NamespaceConfig {
    namespaceName!: string
    entityPath!: string
    generatedPath!: string
}

export class StructuresProject {

    namespaces: NamespaceConfig[] = []

    defaultNamespaceName!: string

    hasNamespaceConfig(name: string): boolean {
        return this.findNamespaceConfig(name) !== null
    }

    findNamespaceConfig(namespaceName: string): NamespaceConfig | null {
        let ret: NamespaceConfig | null = null
        for(const namespace of this.namespaces){
            if(namespace.namespaceName === namespaceName){
                ret = namespace
                break
            }
        }
        return ret
    }
}

export async function loadStructuresProject(): Promise<StructuresProject>{
    const stateManager = createStateManager(path.resolve(process.cwd()))
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
    const stateManager = createStateManager(path.resolve(process.cwd()))
    await stateManager.save(STRUCTURES_KEY, project)
}
