import { SYSTEM_API_ZONE } from '@kinotic-ai/management-api'
import type { IKinotic, IServiceProxy, Page, Pageable } from '@kinotic-ai/core'
import type { OriginEmailTemplate } from '@/api/model/email/OriginEmailTemplate'

/**
 * The emails each UI built on Kinotic sends in its own name, each an OriginEmailTemplate that system users manage:
 * the invitation and sign-up verification emails of the flows started from a page at the template's origin.
 * Published in the system zone, which only SYSTEM participants and the platform's own cluster members may address.
 */
export interface IOriginEmailTemplateService {

    findAll(pageable: Pageable): Promise<Page<OriginEmailTemplate>>

    /** Resolves null when no template has the given id. */
    findById(id: string): Promise<OriginEmailTemplate | null>

    /**
     * Saves a template, new or changed, resolving to it as saved with its origin in lower case. Its origin and kind
     * must be ones no other template has, its origin a scheme and host with no path, and its sources must render
     * with sample values of its kind's variables.
     */
    save(template: OriginEmailTemplate): Promise<OriginEmailTemplate>

    /** Deletes the template, so its UI sends the built-in email again. */
    deleteById(id: string): Promise<void>
}

export class OriginEmailTemplateService implements IOriginEmailTemplateService {

    private readonly serviceProxy: IServiceProxy

    constructor(kinotic: IKinotic) {
        this.serviceProxy = kinotic.serviceProxy(`${SYSTEM_API_ZONE}~org.kinotic.system.api.services.OriginEmailTemplateService`)
    }

    public findAll(pageable: Pageable): Promise<Page<OriginEmailTemplate>> {
        return this.serviceProxy.invoke('findAll', [pageable])
    }

    public findById(id: string): Promise<OriginEmailTemplate | null> {
        return this.serviceProxy.invoke('findById', [id])
    }

    public save(template: OriginEmailTemplate): Promise<OriginEmailTemplate> {
        return this.serviceProxy.invoke('save', [template])
    }

    public deleteById(id: string): Promise<void> {
        return this.serviceProxy.invoke('deleteById', [id])
    }
}
