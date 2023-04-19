import { Authenticator } from '@/frontends/iam/models/Authenticator'
import { v4 as uuidv4 } from 'uuid'

export class LegacySharedSecretAuthenticator implements Authenticator {

    public accessKey: string = uuidv4()

    public version: number | null = null

    public type: string = 'legacy'

    public sharedSecret: string


    constructor(accessKey: string, sharedSecret: string) {
        this.accessKey = accessKey
        this.sharedSecret = sharedSecret
    }
}
