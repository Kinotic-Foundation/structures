import {Continuum, CrudServiceProxy, ICrudServiceProxy, Page, Pageable} from '@kinotic/continuum-client'
import {Project} from '@/api/domain/Project'

export interface IProjectService extends ICrudServiceProxy<Project> {

    /**
     * Counts all projects for the given application.
     * @param applicationId the application to find projects for
     * @return Promise emitting the number of projects
     */
    countForApplication(applicationId: string): Promise<number>

    /**
     * Creates a new project if it does not already exist.
     * @param project the project to create
     * @return Promise emitting the created project or the existing project if it already exists
     */
    createProjectIfNotExist(project: Project): Promise<Project>

    /**
     * Finds all projects for the given application.
     * @param applicationId the application to find projects for
     * @param pageable the page to return
     * @return Promise emitting a page of projects
     */
    findAllForApplication(applicationId: string, pageable: Pageable): Promise<Page<Project>>

    /**
     * This operation makes all the recent writes immediately available for search.
     * @return a Promise that resolves when the operation is complete
     */
    syncIndex(): Promise<void>

}

export class ProjectService extends CrudServiceProxy<Project> implements IProjectService{

    constructor() {
        super(Continuum.serviceProxy('org.kinotic.structures.api.services.ProjectService'))
    }

    public countForApplication(applicationId: string): Promise<number> {
        return this.serviceProxy.invoke('countForApplication', [applicationId])
    }

    public createProjectIfNotExist(project: Project): Promise<Project> {
        return this.serviceProxy.invoke('createProjectIfNotExist', [project])
    }

    public findAllForApplication(applicationId: string, pageable: Pageable): Promise<Page<Project>> {
        return this.serviceProxy.invoke('findAllForApplication', [applicationId, pageable])
    }

    public syncIndex(): Promise<void> {
        return this.serviceProxy.invoke('syncIndex', [])
    }

}
