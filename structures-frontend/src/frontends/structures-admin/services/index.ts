import {IJsonEntitiesService, JSON_ENTITIES_SERVICE} from '@/frontends/structures-admin/services/IJsonEntitiesService'
import {IStructureService, STRUCTURE_SERVICE} from '@/frontends/structures-admin/services/IStructureService'

export * from './IJsonEntitiesService'
export * from './IStructureService'


export namespace Structures {

    export function getJsonEntitiesService(): IJsonEntitiesService {
        return JSON_ENTITIES_SERVICE
    }

    export function getStructureService(): IStructureService {
        return STRUCTURE_SERVICE
    }

}
