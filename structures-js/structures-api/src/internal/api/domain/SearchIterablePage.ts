import {EntitiesService} from '@/api/IEntitiesService'
import {AbstractIterablePage, Page, Pageable, IterablePage} from '@kinotic/continuum-client'

/**
 * {@link IterablePage} for use when searching
 */
export class SearchIterablePage<T> extends AbstractIterablePage<T> {

    private readonly searchText: string
    private readonly structureId: string
    private readonly entitiesService: EntitiesService

    constructor(entitiesService: EntitiesService, pageable: Pageable,
                page: Page<T>,
                searchText: string,
                structureId: string) {
        super(pageable, page)
        this.entitiesService = entitiesService
        this.searchText = searchText
        this.structureId = structureId
    }

    protected findNext(pageable: Pageable): Promise<Page<T>> {
        return this.entitiesService.searchSinglePage(this.structureId, this.searchText, pageable)
    }

}
