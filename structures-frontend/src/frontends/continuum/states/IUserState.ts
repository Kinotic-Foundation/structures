import {container, inject, injectable} from 'inversify-props'
import {IEventBus} from '@kinotic/continuum'
import Keycloak from "keycloak-js"

export interface IUserState {

    isAuthenticated(): boolean

    authenticate(url: string, accessKey: string, secretToken: string): Promise<void>

    authenticateKeycloak(url: string, keycloak: Keycloak): Promise<void>

    getUri(): string
}

@injectable()
export class UserState implements IUserState{

    @inject()
    private eventBus!: IEventBus

    private keycloak!: Keycloak

    private authenticated: boolean = false

    constructor() {

    }

    authenticate(url: string, accessKey: string, secretToken: string): Promise<void> {
        return new Promise((resolve, reject) => {
            this.eventBus.connect(url, accessKey, secretToken)
                .then(value => {
                    this.authenticated = true
                    resolve()
                }).catch(reason => {
                    if(reason){
                        reject(new Error(reason));
                    }else{
                        reject(new Error("Credentials invalid"));
                    }
            })
        })
    }

    authenticateKeycloak(url: string, keycloak: Keycloak): Promise<void> {
        this.keycloak = keycloak
        return this.authenticate(url, this.keycloak.tokenParsed?.email as string, this.keycloak.token as string)
    }

    isAuthenticated(): boolean {
        return this.authenticated || (this.keycloak !== undefined && this.keycloak.authenticated === true);
    }

    getUri(): string {
        let uri: string = 'ws://127.0.0.1:58503/v1'
        if(window.location.hostname !== "127.0.0.1"
            && window.location.hostname !== "localhost"){
            let prefix: string = 'ws'
            if(window.location.protocol.startsWith('https')){
                prefix = 'wss'
            }
            let port: string = ''
            if(window.location.port !== ''){
                // non standard prod deployment, use our specific port
                port = ':58503'
            }
            uri = `${prefix}://${window.location.hostname}${port}/v1`
        }
        return uri
    }

}

container.addSingleton<IUserState>(UserState)
