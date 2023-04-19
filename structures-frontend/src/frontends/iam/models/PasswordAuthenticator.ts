import { Authenticator } from '@/frontends/iam/models/Authenticator'

export class PasswordAuthenticator implements Authenticator {

    public accessKey: string

    public version: number | null = null

    public type: string = 'password'

    public hash: string


    constructor(accessKey: string, hash: string) {
        this.accessKey = accessKey
        this.hash = hash
    }
}
