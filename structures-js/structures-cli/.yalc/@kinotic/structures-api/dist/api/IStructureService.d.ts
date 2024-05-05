import { Structure } from './domain/Structure.js';
import { CrudServiceProxy, ICrudServiceProxy, Page, Pageable } from '@kinotic/continuum-client';

export interface IStructureService extends ICrudServiceProxy<Structure> {
    /**
     * Finds all published structures for the given namespace.
     * @param namespace the namespace to find structures for
     * @param pageable the page to return
     * @return a future that will complete with a page of structures
     */
    findAllPublishedForNamespace(namespace: string, pageable: Pageable): Promise<Page<Structure>>;
    /**
     * Counts all structures for the given namespace.
     * @param namespace the namespace to find structures for
     * @return a future that will complete with a page of structures
     */
    countForNamespace(namespace: string): Promise<number>;
    /**
     * Publishes the structure with the given id.
     * This will make the structure available for use to read and write items for.
     * @param structureId the id of the structure to publish
     * @return a future that will complete when the structure has been published
     */
    publish(structureId: string): Promise<void>;
    /**
     * Un-publish the structure with the given id.
     * @param structureId the id of the structure to un-publish
     * @return a future that will complete when the structure has been unpublished
     */
    unPublish(structureId: string): Promise<void>;
}
export declare class StructureService extends CrudServiceProxy<Structure> implements IStructureService {
    constructor();
    findAllPublishedForNamespace(namespace: string, pageable: Pageable): Promise<Page<Structure>>;
    countForNamespace(namespace: string): Promise<number>;
    publish(structureId: string): Promise<void>;
    unPublish(structureId: string): Promise<void>;
}
