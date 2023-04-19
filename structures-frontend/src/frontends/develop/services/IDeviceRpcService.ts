import { IServiceProxy, IServiceRegistry, IEventFactory, TextEventFactory} from '@kinotic-foundation/continuum-js'
import { inject, injectable, container } from 'inversify-props'


/**
 * Service generic Device Rpc service invocation
 */
export interface IDeviceRpcService {

    /**
     * Invokes any Device RPC service
     * @param mac the specific device to invoke the service on
     * @param service the identifier of the service
     * @param data to pass to the service or null if not data
     */
    invoke(mac: string, service: string, data: string | null): Promise<any>

}

const textEventFactory: IEventFactory = new TextEventFactory()

@injectable()
export class DeviceRpcService implements IDeviceRpcService {

    private serviceProxy: IServiceProxy

    constructor(@inject() serviceRegistry: IServiceRegistry) {
        this.serviceProxy = serviceRegistry.serviceProxy('continuum.cpp.RpcService')
    }

    public invoke(mac: string, service: string, data: string | null): Promise<any> {
        if (data != null) {
            return this.serviceProxy.invoke(service,[ data ], mac, textEventFactory)
        } else {
            return this.serviceProxy.invoke(service,null, mac, textEventFactory)
        }
    }

}

container.addSingleton<IDeviceRpcService>(DeviceRpcService)
