import {INamedQueriesService, NamedQueriesService} from '@/api/INamedQueriesService'
import {IEntitiesService, EntitiesService} from '@/api/IEntitiesService'
import {IStructureService, StructureService} from '@/api/IStructureService'
import {INamespaceService, NamespaceService} from '@/api/INamespaceService'
import {EntityService, IEntityService} from '@/api/IEntityService'

const NAMESPACE_SERVICE: INamespaceService = new NamespaceService()
const STRUCTURE_SERVICE: IStructureService = new StructureService()
const ENTITIES_SERVICE: IEntitiesService = new EntitiesService()
const NAMED_QUERIES_SERVICE: INamedQueriesService = new NamedQueriesService()

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

    export function getNamedQueriesService(): INamedQueriesService {
        return NAMED_QUERIES_SERVICE
    }

    export function createEntityService(structureNamespace: string, structureName: string): IEntityService<any> {
        return new EntityService(structureNamespace, structureName)
    }

}
