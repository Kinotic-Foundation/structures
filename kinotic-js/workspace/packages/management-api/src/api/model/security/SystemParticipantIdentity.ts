import { ParticipantIdentityType } from '@/api/model/security/ParticipantIdentityType'
import { UserParticipantIdentity } from '@/api/model/security/UserParticipantIdentity'

/**
 * A Kinotic OS user who signs in to the system console with a password; unique by email among system users.
 */
export class SystemParticipantIdentity extends UserParticipantIdentity {
    public readonly type: ParticipantIdentityType.SYSTEM_USER = ParticipantIdentityType.SYSTEM_USER
}
