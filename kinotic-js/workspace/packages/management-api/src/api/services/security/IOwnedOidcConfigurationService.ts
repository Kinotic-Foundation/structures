import { MANAGEMENT_API_ZONE } from '@/api/PlatformZones'
import type { IKinotic, IServiceProxy } from '@kinotic-ai/core'
import { FunctionalIterablePage, type IterablePage, type Page, type Pageable } from '@kinotic-ai/core'
import type { OwnedOidcConfiguration } from '@/api/model/security/OwnedOidcConfiguration'

/**
 * The caller's organization's own OIDC configurations: the providers it offers at its own login, at one of its
 * applications' and at one tenant's, each a configuration of that scope's kind. Every operation acts for the
 * organization of the authenticated participant and reaches no other organization's configurations. A
 * configuration's client secret is given on save and held in the organization's scope of the secret storage;
 * nothing here reads it back.
 */
export interface IOwnedOidcConfigurationService {

    /** The organization's configurations, of every kind and enabled or not, oldest first, a page at a time. */
    findAll(pageable: Pageable): Promise<IterablePage<OwnedOidcConfiguration>>

    /** The organization's configuration, or null when the organization owns none by that id. */
    findById(id: string): Promise<OwnedOidcConfiguration | null>

    /**
     * Saves a configuration of the organization, new or changed, and makes `clientSecret` the secret it sends its
     * provider; null or blank makes a public client, whose stored secret is deleted. The configuration's
     * `organizationId` is the caller's, whatever it carried. Returns the configuration as saved, with its id.
     */
    save(configuration: OwnedOidcConfiguration, clientSecret: string | null): Promise<OwnedOidcConfiguration>

    /**
     * Creates a copy of the organization's configuration for another of its sign-in scopes: the login of
     * `applicationId`, or the organization's own login when it is null. The copy has an id, and so a callback URL,
     * of its own, which the provider must be given before a sign-in through it succeeds.
     */
    copy(sourceId: string, applicationId: string | null): Promise<OwnedOidcConfiguration>

    /** Deletes the organization's configuration and the client secret held for it. */
    deleteById(id: string): Promise<void>

}

export class OwnedOidcConfigurationService implements IOwnedOidcConfigurationService {

    private readonly serviceProxy: IServiceProxy

    constructor(kinotic: IKinotic) {
        this.serviceProxy = kinotic.serviceProxy(`${MANAGEMENT_API_ZONE}~org.kinotic.management.api.services.security.OwnedOidcConfigurationService`)
    }

    public async findAll(pageable: Pageable): Promise<IterablePage<OwnedOidcConfiguration>> {
        const page: Page<OwnedOidcConfiguration> = await this.findAllSinglePage(pageable)
        return new FunctionalIterablePage(pageable, page, (pageable: Pageable) => this.findAllSinglePage(pageable))
    }

    public findById(id: string): Promise<OwnedOidcConfiguration | null> {
        return this.serviceProxy.invoke('findById', [id])
    }

    public save(configuration: OwnedOidcConfiguration, clientSecret: string | null): Promise<OwnedOidcConfiguration> {
        return this.serviceProxy.invoke('save', [configuration, clientSecret])
    }

    public copy(sourceId: string, applicationId: string | null): Promise<OwnedOidcConfiguration> {
        return this.serviceProxy.invoke('copy', [sourceId, applicationId])
    }

    public deleteById(id: string): Promise<void> {
        return this.serviceProxy.invoke('deleteById', [id])
    }

    private findAllSinglePage(pageable: Pageable): Promise<Page<OwnedOidcConfiguration>> {
        return this.serviceProxy.invoke('findAll', [pageable])
    }

}
