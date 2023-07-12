import {ConnectedInfo, ConnectionInfo, Continuum} from '@kinotic/continuum-client'
import Keycloak from "keycloak-js"
import {reactive} from "vue"
import {CONTINUUM_UI} from "@/frontends/continuum"

export interface IUserState {

    connectedInfo: ConnectedInfo | null

    isAuthenticated(): boolean

    authenticate(login: string, passcode: string): Promise<void>

    authenticateKeycloak(keycloak: Keycloak): Promise<void>

}

export class UserState implements IUserState{

    private keycloak!: Keycloak

    private authenticated: boolean = false

    public connectedInfo: ConnectedInfo | null = null

    constructor() {

    }

    authenticate(login: string, passcode: string): Promise<void> {
        return new Promise((resolve, reject) => {
            let connectionInfo: ConnectionInfo =  this.createConnectionInfo()
            connectionInfo.connectHeaders = {
                login: login,
                passcode: passcode
            }
            Continuum.connect(connectionInfo)
                .then(value => {
                    this.authenticated = true
                    this.connectedInfo = value
                    resolve()
                }).catch(reason => {
                    if(reason){
                        reject(new Error(reason))
                    }else{
                        reject(new Error("Credentials invalid"))
                    }
            })
        })
    }

    authenticateKeycloak(keycloak: Keycloak): Promise<void> {
        this.keycloak = keycloak
        if(process.env.VUE_APP_KEYCLOAK_ROLE !== "none") {
            if(!this.keycloak.hasRealmRole(process.env.VUE_APP_KEYCLOAK_ROLE)){
                return CONTINUUM_UI.navigate("/access-denied").then()
            }else{
                return this.authenticate(this.keycloak.tokenParsed?.email as string, this.keycloak.token as string)
            }
        }else{
            return this.authenticate(this.keycloak.tokenParsed?.email as string, this.keycloak.token as string)
        }
    }

    isAuthenticated(): boolean {
        return this.authenticated || (this.keycloak !== undefined && this.keycloak.authenticated === true)
    }

    createConnectionInfo(): ConnectionInfo {
        let connectionInfo: ConnectionInfo = {
            host: '127.0.0.1',
            port: 58503
        }
        if(window.location.hostname !== "127.0.0.1"
            && window.location.hostname !== "localhost"){
            if(window.location.protocol.startsWith('https')){
                connectionInfo.useSSL = true
            }
            if(window.location.port !== ''){
                connectionInfo.port = 58503
            }else {
                connectionInfo.port = null
            }
            connectionInfo.host = window.location.hostname
        }
        return connectionInfo
    }

}

export const USER_STATE: IUserState = reactive(new UserState())
