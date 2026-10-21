import { ParticipantIdentityType } from '@/api/model/security/ParticipantIdentityType'
import { UserParticipantIdentity } from '@/api/model/security/UserParticipantIdentity'

/**
 * A person who signs in to organizations: one user, unique by email among organization users, whose sign-in
 * methods reach every organization it is a member of. It acts in one organization at a time, through its
 * membership there.
 */
export class OrganizationParticipantIdentity extends UserParticipantIdentity {
    public readonly type: ParticipantIdentityType.ORGANIZATION_USER = ParticipantIdentityType.ORGANIZATION_USER
}
