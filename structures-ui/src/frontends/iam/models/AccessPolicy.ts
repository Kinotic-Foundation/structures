import { Identifiable } from '@kinotic-foundation/continuum-js'
import { AccessPattern } from '@/frontends/iam/models/AccessPattern'

/**
 * AccessPolicy defines what a {@link IamParticipant} can access
 */
export class AccessPolicy implements Identifiable<string> {

    public identity: string
    public description?: string
    public allowedSendPatterns: AccessPattern[] = []
    public allowedSubscriptionPatterns: AccessPattern[] = []

    constructor(identity: string) {
        this.identity = identity
    }
}
