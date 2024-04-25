import {NamedQueryServiceDefinition} from '@/api/domain/NamedQueryServiceDefinition'
import {Continuum, CrudServiceProxy, ICrudServiceProxy,} from '@kinotic/continuum-client'

export interface INamedQueriesService extends ICrudServiceProxy<NamedQueryServiceDefinition> {

}

export class NamedQueriesService extends CrudServiceProxy<NamedQueryServiceDefinition> implements INamedQueriesService{

    constructor() {
        super(Continuum.serviceProxy('org.kinotic.structures.api.services.NamedQueriesService'))
    }

}
