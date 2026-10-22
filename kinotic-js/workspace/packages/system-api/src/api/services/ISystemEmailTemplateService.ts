import { SYSTEM_API_ZONE } from '@kinotic-ai/management-api'
import type { IKinotic, IServiceProxy, Page, Pageable } from '@kinotic-ai/core'
import type { SystemEmailTemplate } from '@/api/model/email/SystemEmailTemplate'

/**
 * The emails UIs built on Kinotic send in their own name, each a SystemEmailTemplate that system users manage: the
 * invitation and sign-up verification emails a call names by id.
 * Published in the system zone, which only SYSTEM participants and the platform's own cluster members may address.
 */
export interface ISystemEmailTemplateService {

    findAll(pageable: Pageable): Promise<Page<SystemEmailTemplate>>

    /** Resolves null when no template has the given id. */
    findById(id: string): Promise<SystemEmailTemplate | null>

    /**
     * Saves a template, replacing the one with its id if there is one, and resolves to it as saved, its UI base URL
     * without a trailing slash. Its id must be lowercase letters, digits and interior dashes, its UI base URL an http
     * or https URL, and its sources must render with sample values of the invitation and verification variables.
     */
    save(template: SystemEmailTemplate): Promise<SystemEmailTemplate>

    /** Deletes the template; a default (DEFAULT_EMAIL_TEMPLATE_IDS) cannot be deleted. */
    deleteById(id: string): Promise<void>
}

export class SystemEmailTemplateService implements ISystemEmailTemplateService {

    private readonly serviceProxy: IServiceProxy

    constructor(kinotic: IKinotic) {
        this.serviceProxy = kinotic.serviceProxy(`${SYSTEM_API_ZONE}~org.kinotic.system.api.services.SystemEmailTemplateService`)
    }

    public findAll(pageable: Pageable): Promise<Page<SystemEmailTemplate>> {
        return this.serviceProxy.invoke('findAll', [pageable])
    }

    public findById(id: string): Promise<SystemEmailTemplate | null> {
        return this.serviceProxy.invoke('findById', [id])
    }

    public save(template: SystemEmailTemplate): Promise<SystemEmailTemplate> {
        return this.serviceProxy.invoke('save', [template])
    }

    public deleteById(id: string): Promise<void> {
        return this.serviceProxy.invoke('deleteById', [id])
    }
}
