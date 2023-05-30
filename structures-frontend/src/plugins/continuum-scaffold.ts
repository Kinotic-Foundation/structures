
import { container, cid, injectable } from 'inversify-props'
import {EventBus as EB, IEventBus, IServiceProxy, IServiceRegistry, ServiceProxy} from '@kinotic/continuum'

@injectable()
class ServiceRegistry implements IServiceRegistry {

    public eventBus?: IEventBus

    public serviceProxy(serviceIdentifier: string): IServiceProxy {
        if(this.eventBus) {
            return new ServiceProxy(serviceIdentifier, this.eventBus)
        } else {
            throw new Error('No event bus has been configured')
        }
    }
}

@injectable()
class EventBus extends EB {

    constructor() {
        super()
    }
}

container.addSingleton<IEventBus>(EventBus)
container.addSingleton<IServiceRegistry>(ServiceRegistry)
const registry: ServiceRegistry = container.get<IServiceRegistry>(cid.IServiceRegistry)
registry.eventBus = container.get<IEventBus>(cid.IEventBus)
