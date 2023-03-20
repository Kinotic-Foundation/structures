import { Identifiable } from '@kinotic-foundation/continuum-js'
import { AccessPolicy } from '@/frontends/iam/models/AccessPolicy'

export class Role implements Identifiable<string> {

    public identity: string
    public description?: string
    public accessPolicies: AccessPolicy[] = []

    constructor(identity: string) {
        this.identity = identity
    }

}
