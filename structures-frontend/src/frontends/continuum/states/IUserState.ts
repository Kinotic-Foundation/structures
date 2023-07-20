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

    async authenticate(login: string, passcode: string): Promise<void> {
        let connectionInfo: ConnectionInfo =  this.createConnectionInfo()
        connectionInfo.connectHeaders = {
            login: login,
            passcode: passcode
        }
        try {
            this.connectedInfo = await Continuum.connect(connectionInfo)
            this.authenticated = true
        }catch(reason: any){
            if(reason){
                throw new Error(reason)
            }else{
                throw new Error("Credentials invalid")
            }
        }
    }

    async authenticateKeycloak(keycloak: Keycloak): Promise<void> {
        this.keycloak = keycloak
        if(process.env.VUE_APP_KEYCLOAK_ROLE !== "none") {
            if(!this.keycloak.hasRealmRole(process.env.VUE_APP_KEYCLOAK_ROLE)){
                return CONTINUUM_UI.navigate("/access-denied").then()
            }else{
                await this.authenticate(this.keycloak.tokenParsed?.email as string, this.keycloak.token as string)
            }
        }else{
            await this.authenticate(this.keycloak.tokenParsed?.email as string, this.keycloak.token as string)
        }
    }

    isAuthenticated(): boolean {
        return this.authenticated && (this.keycloak !== undefined && this.keycloak.authenticated === true)
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
