import { ParticipantIdentityType } from '@/api/model/security/ParticipantIdentityType'
import { UserParticipantIdentity } from '@/api/model/security/UserParticipantIdentity'

/**
 * A Kinotic OS user who signs in to the system console with a password. Carries no organizationId or
 * applicationId, and is unique by email among system users.
 */
export class SystemUserParticipantIdentity extends UserParticipantIdentity {
    public readonly type: ParticipantIdentityType.SYSTEM_USER = ParticipantIdentityType.SYSTEM_USER
}
