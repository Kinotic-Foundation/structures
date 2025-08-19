import { ConnectedInfo, ConnectionInfo, Continuum } from '@kinotic/continuum-client'
import { reactive } from 'vue'
import Cookies from 'js-cookie'
import { User } from 'oidc-client-ts'

export interface IUserState {
    connectedInfo: ConnectedInfo | null
    oidcUser: User | null

    isAccessDenied(): boolean
    isAuthenticated(): boolean
    authenticate(login: string, passcode: string): Promise<void>
    handleOidcLogin(user: User): Promise<void>
    logout(): Promise<void>
}

export class UserState implements IUserState {
    public connectedInfo: ConnectedInfo | null = null
    public oidcUser: User | null = null
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

    public async handleOidcLogin(user: User): Promise<void> {
        const connectionInfo: ConnectionInfo = this.createConnectionInfo()
        
        // Determine which token to use
        let tokenToUse = user.access_token;
        
        // For Microsoft social login, check if access_token is a valid JWT
        // If not, use the ID token instead
        if (user.access_token && !this.isValidJWT(user.access_token)) {
            console.log('Access token is not a valid JWT, using ID token for Microsoft social login');
            tokenToUse = user.id_token || user.access_token;
        }
        
        connectionInfo.connectHeaders = {
            Authorization: `Bearer ${tokenToUse}`
        }

        try {
            this.connectedInfo = await Continuum.connect(connectionInfo)
            this.authenticated = true
            this.accessDenied = false
            this.oidcUser = user

            // Store the token in a cookie
            Cookies.set('token', tokenToUse, {
                sameSite: 'strict',
                secure: true,
                expires: new Date(user.expires_at! * 1000) // Convert Unix timestamp to Date
            })

            // Store refresh token if available
            if (user.refresh_token) {
                Cookies.set('oidc_refresh_token', user.refresh_token, {
                    sameSite: 'strict',
                    secure: true,
                    expires: 30 // 30 days for refresh token
                })
            }
        } catch (reason: any) {
            this.accessDenied = true
            if (reason) {
                throw new Error(reason)
            } else {
                throw new Error('OIDC authentication failed')
            }
        }
    }

    public async logout(): Promise<void> {
        // Clear all auth-related cookies
        Cookies.remove('token')
        Cookies.remove('oidc_refresh_token')

        // Reset state
        this.connectedInfo = null
        this.oidcUser = null
        this.authenticated = false
        this.accessDenied = false

        // Disconnect from Continuum if connected
        if (this.connectedInfo) {
            try {
                await Continuum.disconnect()
            } catch (error) {
                console.error('Error disconnecting from Continuum:', error)
            }
        }
    }

    public isAccessDenied(): boolean {
        return this.accessDenied
    }

    public isAuthenticated(): boolean {
        return this.authenticated && (
            // Either we have a basic auth token
            Cookies.get('token') !== undefined ||
            // Or we have a valid OIDC token
            (this.oidcUser !== null && 
             this.oidcUser.expires_at !== undefined && 
             this.oidcUser.expires_at * 1000 > Date.now())
        )
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

    /**
     * Check if a token is a valid JWT format
     */
    private isValidJWT(token: string): boolean {
        try {
            // Check if token has the JWT format (3 parts separated by dots)
            const parts = token.split('.');
            if (parts.length !== 3) {
                return false;
            }
            
            // Try to decode the header and payload
            const header = JSON.parse(atob(parts[0]));
            const payload = JSON.parse(atob(parts[1]));
            
            // Check for required JWT claims
            return !!(header.alg && payload.iss && payload.aud);
        } catch (error) {
            return false;
        }
    }
}

export const USER_STATE: IUserState = reactive(new UserState())
