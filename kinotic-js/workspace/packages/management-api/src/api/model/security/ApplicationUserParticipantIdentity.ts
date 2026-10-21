import { ParticipantIdentityType } from '@/api/model/security/ParticipantIdentityType'
import { UserParticipantIdentity } from '@/api/model/security/UserParticipantIdentity'

/**
 * A user of one application's own user base, signing in at that application's login. Carries the organizationId
 * and applicationId of its application, and the tenantId of its tenant when the application has tenants; unique
 * by email within the application.
 */
export class ApplicationUserParticipantIdentity extends UserParticipantIdentity {
    public readonly type: ParticipantIdentityType.APPLICATION_USER = ParticipantIdentityType.APPLICATION_USER
}
