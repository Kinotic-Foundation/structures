import {QueryParameter} from '@/api/domain/QueryParameter'
import {EntitiesService} from '@/api/IEntitiesService'
import {AbstractIterablePage, Page, Pageable, IterablePage} from '@kinotic/continuum-client'

/**
 * {@link IterablePage} for use when searching
 */
export class NamedQueryIterablePage<T> extends AbstractIterablePage<T> {

    private readonly parameters: QueryParameter[]
    private readonly queryName: string
    private readonly structureId: string
    private readonly entitiesService: EntitiesService

    constructor(entitiesService: EntitiesService,
                pageable: Pageable,
                page: Page<T>,
                parameters: QueryParameter[],
                queryName: string,
                structureId: string) {
        super(pageable, page)
        this.entitiesService = entitiesService
        this.parameters = parameters
        this.queryName = queryName
        this.structureId = structureId
    }

    protected findNext(pageable: Pageable): Promise<Page<T>> {
        return this.entitiesService.namedQuerySinglePage(this.structureId, this.queryName, this.parameters, pageable)
    }

}
