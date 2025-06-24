import {AdminEntityService, IAdminEntityService} from '@/api/IAdminEntityService'
import {INamedQueriesService, NamedQueriesService} from '@/api/INamedQueriesService'
import {IEntitiesService, EntitiesService} from '@/api/IEntitiesService'
import {IStructureService, StructureService} from '@/api/IStructureService'
import {IApplicationService, ApplicationService} from '@/api/IApplicationService'
import {EntityService, IEntityService} from '@/api/IEntityService'
import { IProjectService, ProjectService } from '@/api/IProjectService'

const APPLICATION_SERVICE: IApplicationService = new ApplicationService()
const PROJECT_SERVICE: IProjectService = new ProjectService()
const STRUCTURE_SERVICE: IStructureService = new StructureService()
const ENTITIES_SERVICE: IEntitiesService = new EntitiesService()
const NAMED_QUERIES_SERVICE: INamedQueriesService = new NamedQueriesService()

export namespace Structures {

    export function getApplicationService(): IApplicationService {
        return APPLICATION_SERVICE
    }

    export function getProjectService(): IProjectService {
        return PROJECT_SERVICE
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

    export function createEntityService<T>(structureApplicationId: string, structureName: string): IEntityService<T> {
        return new EntityService(structureApplicationId, structureName)
    }

    export function createAdminEntityService<T>(structureApplicationId: string, structureName: string): IAdminEntityService<T>{
        return new AdminEntityService(structureApplicationId, structureName);
    }

}
