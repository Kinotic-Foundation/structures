import {container, inject, injectable} from 'inversify-props'
import {IEventBus} from '@kinotic-foundation/continuum-js'

export interface IUserState {

    isAuthenticated(): boolean

    authenticate(url: string, accessKey: string, secretToken: string): Promise<void>

}

@injectable()
export class UserState implements IUserState{

    @inject()
    private eventBus!: IEventBus

    private authenticated: boolean = false

    constructor() {
    }

    authenticate(url: string, accessKey: string, secretToken: string): Promise<void> {
        return new Promise((resolve, reject) => {
            this.eventBus.connect(url, accessKey, secretToken)
                .then(value => {
                    this.authenticated = true
                    resolve()
                }).catch(reason => reject(reason))
        })
    }

    isAuthenticated(): boolean {
        return this.authenticated;
    }

}

container.addSingleton<IUserState>(UserState)
