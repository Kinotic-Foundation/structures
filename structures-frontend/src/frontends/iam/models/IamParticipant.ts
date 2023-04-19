import { Identifiable } from '@kinotic-foundation/continuum-js'
import { Role } from '@/frontends/iam/models/Role'
import { Authenticator } from '@/frontends/iam/models/Authenticator'

export class IamParticipant implements Identifiable<string> {

    public identity: string
    public metadata: any
    public authenticators: Authenticator[] | null = []
    public roles: Role[] = []

    constructor(identity: string) {
        this.identity = identity
    }

}
