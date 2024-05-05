import {QueryParameter} from '@/api/domain/QueryParameter.js'
import {IEntitiesService} from '@/api/IEntitiesService'
import {AbstractIterablePage, Page, Pageable, IterablePage} from '@kinotic/continuum-client'

/**
 * {@link IterablePage} for use when searching
 */
export class NamedQueryIterablePage<T> extends AbstractIterablePage<T> {

    private readonly pageableIndex: number
    private readonly parameters: QueryParameter[]
    private readonly queryName: string
    private readonly structureId: string
    private readonly entitiesService: IEntitiesService

    constructor(entitiesService: IEntitiesService,
                pageableIndex: number,
                pageable: Pageable,
                page: Page<T>,
                parameters: QueryParameter[],
                queryName: string,
                structureId: string) {
        super(pageable, page)
        this.entitiesService = entitiesService
        this.pageableIndex = pageableIndex
        this.parameters = parameters
        this.queryName = queryName
        this.structureId = structureId
    }

    protected findNext(pageable: Pageable): Promise<Page<T>> {
        this.parameters[this.pageableIndex].value = pageable
        return this.entitiesService.namedQuery(this.structureId, this.queryName, this.parameters)
    }

}
