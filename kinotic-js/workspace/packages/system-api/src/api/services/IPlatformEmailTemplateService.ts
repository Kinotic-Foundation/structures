import { SYSTEM_API_ZONE } from '@kinotic-ai/management-api'
import type { IKinotic, IServiceProxy, Page, Pageable } from '@kinotic-ai/core'
import type { PlatformEmailTemplate } from '@/api/model/email/PlatformEmailTemplate'

/**
 * The emails UIs built on Kinotic send in their own name, each a PlatformEmailTemplate that system users manage: the
 * invitation and sign-up verification emails a call names by id.
 * Published in the system zone, which only SYSTEM participants and the platform's own cluster members may address.
 */
export interface IPlatformEmailTemplateService {

    findAll(pageable: Pageable): Promise<Page<PlatformEmailTemplate>>

    /** Resolves null when no template has the given id. */
    findById(id: string): Promise<PlatformEmailTemplate | null>

    /**
     * Saves a template, replacing the one with its id if there is one, and resolves to it as saved, its UI base URL
     * without a trailing slash. Its id must be lowercase letters, digits and interior dashes, its UI base URL an http
     * or https URL, a type's default must stay that type, and its sources must render with sample values of its
     * type's variables.
     */
    save(template: PlatformEmailTemplate): Promise<PlatformEmailTemplate>

    /** Deletes the template; a type's default cannot be deleted. */
    deleteById(id: string): Promise<void>
}

export class PlatformEmailTemplateService implements IPlatformEmailTemplateService {

    private readonly serviceProxy: IServiceProxy

    constructor(kinotic: IKinotic) {
        this.serviceProxy = kinotic.serviceProxy(`${SYSTEM_API_ZONE}~org.kinotic.system.api.services.PlatformEmailTemplateService`)
    }

    public findAll(pageable: Pageable): Promise<Page<PlatformEmailTemplate>> {
        return this.serviceProxy.invoke('findAll', [pageable])
    }

    public findById(id: string): Promise<PlatformEmailTemplate | null> {
        return this.serviceProxy.invoke('findById', [id])
    }

    public save(template: PlatformEmailTemplate): Promise<PlatformEmailTemplate> {
        return this.serviceProxy.invoke('save', [template])
    }

    public deleteById(id: string): Promise<void> {
        return this.serviceProxy.invoke('deleteById', [id])
    }
}
