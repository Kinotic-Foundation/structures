import {Continuum, IServiceProxy} from '@kinotic/continuum'
import { Observable } from 'rxjs'
import { Result } from '../domain/grind/Result'
import { JobDefinition } from '../domain/grind/JobDefinition'
import {reactive} from "vue";


/**
 * Grind Service
 */
export interface IGrindServiceProxy {

    describeJob(methodIdentifier: string, args?: any[] | undefined): Promise<JobDefinition>

    executeJob(methodIdentifier: string, args?: any[] | undefined): Observable<Result>

}

class GrindServiceProxy implements IGrindServiceProxy {

    protected serviceProxy: IServiceProxy

    constructor(serviceProxy: IServiceProxy) {
        this.serviceProxy = serviceProxy
    }

    describeJob(methodIdentifier: string, args?: any[] | undefined): Promise<JobDefinition> {
        return this.serviceProxy.invoke(methodIdentifier, args) as Promise<JobDefinition>
    }

    executeJob(methodIdentifier: string, args?: any[] | undefined): Observable<Result> {
        return this.serviceProxy.invokeStream(methodIdentifier, args) as Observable<Result>
    }


}

export interface IGrindServiceFactory {

    /**
     * Creates a new "Grind" service proxy that can be used to access the desired service.
     * @param serviceIdentifier the identifier of the service to be accessed
     * @return the {@link IGrindServiceProxy} that can be used to access the service
     */
    grindServiceProxy(serviceIdentifier: string): IGrindServiceProxy

}


export class GrindServiceFactory implements IGrindServiceFactory {


    constructor() {
    }

    grindServiceProxy(serviceIdentifier: string): IGrindServiceProxy {
        return new GrindServiceProxy(Continuum.serviceProxy(serviceIdentifier))
    }

}

export const GRIND_SERVICE_FACTORY: IGrindServiceFactory = reactive(new GrindServiceFactory())
