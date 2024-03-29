import {IEntitiesService, EntitiesService} from './IEntitiesService.js'
import {IStructureService, StructureService} from './IStructureService.js'
import {INamespaceService, NamespaceService} from '@/api/INamespaceService.js'
import {EntityService, IEntityService} from '@/api/IEntityService.js'

const NAMESPACE_SERVICE: INamespaceService = new NamespaceService()
const STRUCTURE_SERVICE: IStructureService = new StructureService()
const ENTITIES_SERVICE: IEntitiesService = new EntitiesService()

export namespace Structures {

    export function getNamespaceService(): INamespaceService {
        return NAMESPACE_SERVICE
    }

    export function getStructureService(): IStructureService {
        return STRUCTURE_SERVICE
    }

    export function getEntitiesService(): IEntitiesService {
        return ENTITIES_SERVICE
    }

    export function createEntityService(structureNamespace: string, structureName: string): IEntityService<any> {
        return new EntityService(structureNamespace, structureName)
    }

}
