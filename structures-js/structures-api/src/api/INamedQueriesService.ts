import {NamedQueriesDefinition} from '@/api/domain/NamedQueriesDefinition'
import {Continuum, CrudServiceProxy, ICrudServiceProxy,} from '@kinotic/continuum-client'

export interface INamedQueriesService extends ICrudServiceProxy<NamedQueriesDefinition> {

}

export class NamedQueriesService extends CrudServiceProxy<NamedQueriesDefinition> implements INamedQueriesService{

    constructor() {
        super(Continuum.serviceProxy('org.kinotic.structures.api.services.NamedQueriesService'))
    }

}
