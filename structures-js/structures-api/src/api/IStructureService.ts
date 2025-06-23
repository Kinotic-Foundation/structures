import {Continuum, CrudServiceProxy, ICrudServiceProxy, Page, Pageable} from '@kinotic/continuum-client'
import {Structure} from '@/api/domain/Structure'


export interface IStructureService extends ICrudServiceProxy<Structure> {

    /**
     * Finds all published structures for the given application.
     * @param applicationId the application to find structures for
     * @param pageable the page to return
     * @return a future that will complete with a page of structures
     */
    findAllPublishedForApplication(applicationId: string, pageable: Pageable): Promise<Page<Structure>>

    /**
     * Counts all structures for the given application.
     * @param applicationId the application to find structures for
     * @return a future that will complete with a page of structures
     */
    countForApplication(applicationId: string): Promise<number>

    /**
     * Publishes the structure with the given id.
     * This will make the structure available for use to read and write items for.
     * @param structureId the id of the structure to publish
     * @return a future that will complete when the structure has been published
     */
    publish(structureId: string): Promise<void>

    /**
     * This operation makes all the recent writes immediately available for search.
     * @return a Promise that resolves when the operation is complete
     */
    syncIndex(): Promise<void>

    /**
     * Un-publish the structure with the given id.
     * @param structureId the id of the structure to un-publish
     * @return a future that will complete when the structure has been unpublished
     */
    unPublish(structureId: string): Promise<void>
}

export class StructureService extends CrudServiceProxy<Structure> implements IStructureService{

    constructor() {
        super(Continuum.serviceProxy('org.kinotic.structures.api.services.StructureService'))
    }

    public findAllPublishedForApplication(applicationId: string, pageable: Pageable): Promise<Page<Structure>> {
        return this.serviceProxy.invoke('findAllPublishedForApplication', [applicationId, pageable])
    }

    public countForApplication(applicationId: string): Promise<number> {
        return this.serviceProxy.invoke('countForApplication', [applicationId])
    }

    public publish(structureId: string): Promise<void> {
        return this.serviceProxy.invoke('publish', [structureId])
    }

    public unPublish(structureId: string): Promise<void> {
        return this.serviceProxy.invoke('unPublish', [structureId])
    }

    public syncIndex(): Promise<void> {
        return this.serviceProxy.invoke('syncIndex', [])
    }
}
