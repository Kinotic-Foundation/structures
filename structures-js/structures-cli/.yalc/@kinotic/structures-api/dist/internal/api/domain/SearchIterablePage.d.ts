import { AbstractIterablePage, Page, Pageable } from '@kinotic/continuum-client';

/**
 * {@link IterablePage} for use when searching
 */
export declare class SearchIterablePage<T> extends AbstractIterablePage<T> {
    private readonly searchText;
    private readonly structureId;
    private readonly entitiesService;
    constructor(pageable: Pageable, page: Page<T>, searchText: string, structureId: string);
    protected findNext(pageable: Pageable): Promise<Page<T>>;
}
