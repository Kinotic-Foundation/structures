import { IEntityService } from './IEntityService.js';
import { INamespaceService } from './INamespaceService.js';
import { IStructureService } from './IStructureService.js';
import { IEntitiesService } from './IEntitiesService.js';

export declare namespace Structures {
    function getNamespaceService(): INamespaceService;
    function getStructureService(): IStructureService;
    function getEntitiesService(): IEntitiesService;
    function createEntityService(structureNamespace: string, structureName: string): IEntityService<any>;
}
