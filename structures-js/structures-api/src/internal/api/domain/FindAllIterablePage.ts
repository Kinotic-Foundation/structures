import {EntitiesService, EntitiesServiceSingleton} from '@/api/IEntitiesService'
import {AbstractIterablePage, Page, Pageable, IterablePage} from '@kinotic/continuum-client'

/**
 * {@link IterablePage} for use when finding all
 */
export class FindAllIterablePage<T> extends AbstractIterablePage<T> {

    private readonly structureId: string
    private readonly entitiesService: EntitiesService

    constructor(pageable: Pageable,
                page: Page<T>,
                structureId: string) {
        super(pageable, page)
        this.structureId = structureId
        this.entitiesService = EntitiesServiceSingleton as EntitiesService
    }

    protected findNext(pageable: Pageable): Promise<Page<T>> {
        return this.entitiesService.findAllSinglePage(this.structureId, pageable)
    }

}
