import { ParticipantIdentity } from '@/api/model/security/ParticipantIdentity'
import { ParticipantIdentityType } from '@/api/model/security/ParticipantIdentityType'

/**
 * A person, at any scope: what every kind of user has, an email and sign-in methods of its own. The concrete
 * subtype is the scope the person signs in to — {@link SystemParticipantIdentity},
 * {@link OrganizationParticipantIdentity} or {@link ApplicationParticipantIdentity} — and one email may
 * be a user of each, each with its own sign-in methods.
 */
export abstract class UserParticipantIdentity extends ParticipantIdentity {
    public abstract override readonly type: ParticipantIdentityType.SYSTEM_USER | ParticipantIdentityType.ORGANIZATION_USER | ParticipantIdentityType.APPLICATION_USER
    /** Email address, unique among the users of the same scope. */
    public email: string = ''
}
