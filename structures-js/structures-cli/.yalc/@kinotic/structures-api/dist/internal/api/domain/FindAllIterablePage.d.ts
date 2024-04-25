import { AbstractIterablePage, Page, Pageable } from '@kinotic/continuum-client';

/**
 * {@link IterablePage} for use when finding all
 */
export declare class FindAllIterablePage<T> extends AbstractIterablePage<T> {
    private readonly structureId;
    private readonly entitiesService;
    constructor(pageable: Pageable, page: Page<T>, structureId: string);
    protected findNext(pageable: Pageable): Promise<Page<T>>;
}
