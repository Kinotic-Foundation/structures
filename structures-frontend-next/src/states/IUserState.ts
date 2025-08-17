import { ConnectedInfo, ConnectionInfo, Continuum } from '@kinotic/continuum-client'
import { reactive } from 'vue'
import Cookies from 'js-cookie'

export interface IUserState {

    connectedInfo: ConnectedInfo | null

    isAccessDenied(): boolean

    isAuthenticated(): boolean

    authenticate(login: string, passcode: string): Promise<void>
}

export class UserState implements IUserState {

    public connectedInfo: ConnectedInfo | null = null
    private authenticated: boolean = false
    private accessDenied: boolean = false

    public async authenticate(login: string, passcode: string): Promise<void> {
        const connectionInfo: ConnectionInfo = this.createConnectionInfo()
        connectionInfo.connectHeaders = {
            login,
            passcode
        }
        const btoaToken = btoa(`${login}:${passcode}`)

        try {
            this.connectedInfo = await Continuum.connect(connectionInfo)
            this.authenticated = true
            this.accessDenied = false

            Cookies.set('token', btoaToken, {
                sameSite: 'strict',
                secure: true,
                expires: 1
            })
        } catch (reason: any) {
            this.accessDenied = true
            if (reason) {
                throw new Error(reason)
            } else {
                throw new Error('Credentials invalid')
            }
        }
    }

    public isAccessDenied(): boolean {
        return this.accessDenied
    }

    public isAuthenticated(): boolean {
        return this.authenticated
    }

    public createConnectionInfo(): ConnectionInfo {
        const connectionInfo: ConnectionInfo = {
            host: '127.0.0.1',
            port: 58503
        }
        if (window.location.hostname !== '127.0.0.1'
            && window.location.hostname !== 'localhost') {
            if (window.location.protocol.startsWith('https')) {
                connectionInfo.useSSL = true
            }
            if (window.location.port !== '') {
                connectionInfo.port = 58503
            } else {
                connectionInfo.port = null
            }
            connectionInfo.host = window.location.hostname
        }
        return connectionInfo
    }

}

export const USER_STATE: IUserState = reactive(new UserState())
