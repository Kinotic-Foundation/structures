import type { Identifiable } from '@kinotic-ai/core'
import { ParticipantIdentityType } from '@/api/model/security/ParticipantIdentityType'

/**
 * A persisted identity that can authenticate — a person of one scope ({@link UserParticipantIdentity}),
 * a client acting on a person's behalf ({@link DelegatingParticipantIdentity}) or a machine
 * ({@link MachineParticipantIdentity}), discriminated by {@link type}. Scope is encoded structurally by
 * which of {@link organizationId} / {@link applicationId} is set:
 *
 * - both null → SYSTEM; an {@link OrganizationUserParticipantIdentity} too carries neither, since it acts
 *   in the organizations its memberships name
 * - {@link organizationId} only → ORGANIZATION
 * - both set → APPLICATION, with {@link tenantId} identifying the end-user data slice
 */
export abstract class ParticipantIdentity implements Identifiable<string> {
    public id: string | null = null
    public abstract readonly type: ParticipantIdentityType
    public displayName: string | null = null
    public organizationId: string | null = null
    public applicationId: string | null = null
    public tenantId: string | null = null
    public enabled: boolean = true
    public created: string | null = null
    public updated: string | null = null
}
