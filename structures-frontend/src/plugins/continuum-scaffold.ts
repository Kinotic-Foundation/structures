
import { container } from 'inversify-props'
import {EventBus, IEventBus, IServiceProxy, IServiceRegistry, ServiceProxy} from '@kinotic/continuum'

export class ServiceRegistry implements IServiceRegistry {

    public eventBus?: IEventBus

    public serviceProxy(serviceIdentifier: string): IServiceProxy {
        if(this.eventBus) {
            return new ServiceProxy(serviceIdentifier, this.eventBus)
        } else {
            throw new Error('No event bus has been configured')
        }
    }
}

container.addSingleton<IEventBus>(EventBus)
container.addSingleton<IServiceRegistry>(ServiceRegistry)
container.get(ServiceRegistry).eventBus = container.get(EventBus)
