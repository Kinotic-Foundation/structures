import {QueryParameter} from '@/api/domain/QueryParameter.js'
import {EntitiesService, EntitiesServiceSingleton} from '@/api/IEntitiesService'
import {AbstractIterablePage, Page, Pageable, IterablePage} from '@kinotic/continuum-client'

/**
 * {@link IterablePage} for use when searching
 */
export class NamedQueryIterablePage<T> extends AbstractIterablePage<T> {

    private readonly parameters: QueryParameter[]
    private readonly queryName: string
    private readonly structureId: string
    private readonly entitiesService: EntitiesService

    constructor(pageable: Pageable,
                page: Page<T>,
                parameters: QueryParameter[],
                queryName: string,
                structureId: string) {
        super(pageable, page)
        this.parameters = parameters
        this.queryName = queryName
        this.structureId = structureId
        this.entitiesService = EntitiesServiceSingleton as EntitiesService
    }

    protected findNext(pageable: Pageable): Promise<Page<T>> {
        return this.entitiesService.namedQuerySinglePage(this.structureId, this.queryName, this.parameters, pageable)
    }

}
