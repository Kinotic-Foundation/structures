import {IServiceProxy, IServiceRegistry} from '@kinotic-foundation/continuum-js'
import { inject, injectable, container } from 'inversify-props'
import { Observable } from 'rxjs'


/**
 * Service for testing of continuum proxy logic
 */
export interface ITestService {

    getFreeMemory(): Promise<number>

    testUser(): Promise<any> // TODO: use POJO like object instead

    testFlux(): Observable<string>
}


@injectable()
export class TestService implements ITestService {

    private serviceProxy: IServiceProxy

    constructor(@inject() serviceRegistry: IServiceRegistry) {
        this.serviceProxy = serviceRegistry.serviceProxy('org.kinotic.testapplication.services.TestService')
    }

    public getFreeMemory(): Promise<number> {
        return this.serviceProxy.invoke('getFreeMemory')
    }

    public testUser(): Promise<any> {
        return this.serviceProxy.invoke('testuser@example.com')
    }

    public testFlux(): Observable<string> {
        return this.serviceProxy.invokeStream('testFlux')
    }

}

container.addSingleton<ITestService>(TestService)
