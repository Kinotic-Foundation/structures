import { IEntityService } from './IEntityService.js';
import { INamespaceService } from './INamespaceService.js';
import { IStructureService } from './IStructureService.js';
import { IEntitiesService } from './IEntitiesService.js';
import { INamedQueriesService } from './INamedQueriesService.js';

export declare namespace Structures {
    function getNamespaceService(): INamespaceService;
    function getStructureService(): IStructureService;
    function getEntitiesService(): IEntitiesService;
    function getNamedQueriesService(): INamedQueriesService;
    function createEntityService(structureNamespace: string, structureName: string): IEntityService<any>;
}
