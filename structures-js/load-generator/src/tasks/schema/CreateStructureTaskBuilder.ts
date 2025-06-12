import { ITask } from "../ITask"
import { IStructureService, IEntityService, Structures, Structure } from '@kinotic/structures-api'
import { ObjectC3Type } from '@kinotic/continuum-idl'

export interface CreateStructureTaskConfig {
    namespace: string
    name: string
    description: string
    entityDefinition: ObjectC3Type
    onServiceCreated?: (service: IEntityService<any>) => void
}

export class CreateStructureTaskBuilder {
    private static instance: CreateStructureTaskBuilder
    private readonly structureService: IStructureService

    private constructor() {
        this.structureService = Structures.getStructureService()
    }

    static getInstance(): CreateStructureTaskBuilder {
        if (!CreateStructureTaskBuilder.instance) {
            CreateStructureTaskBuilder.instance = new CreateStructureTaskBuilder()
        }
        return CreateStructureTaskBuilder.instance
    }

    buildTask(config: CreateStructureTaskConfig): ITask {
        return {
            name: () => `Create ${config.name} Structure`,
            execute: async () => {
                const structureId = `${config.namespace}.${config.name.toLowerCase()}`
                const existingStructure = await this.structureService.findById(structureId)
                
                if (!existingStructure) {
                    const structure = new Structure(
                        config.namespace,
                        config.name,
                        config.entityDefinition,
                        config.description
                    )
                    const savedStructure = await this.structureService.create(structure)
                    if (savedStructure.id) {
                        await this.structureService.publish(savedStructure.id)
                    }
                } else {
                    console.log(`${config.name} structure already exists, skipping creation`)
                }
                
                if (config.onServiceCreated) {
                    const service = Structures.createEntityService(config.namespace, config.name)
                    config.onServiceCreated(service)
                }
            }
        }
    }
} 