package org.kinotic.domain.api.model.security.identity;

/**
 * What kind of principal a {@link ParticipantIdentity} represents. Mirrors the concrete
 * subtype one-to-one — {@link #SYSTEM_USER} for {@link SystemParticipantIdentity},
 * {@link #ORGANIZATION_USER} for {@link OrganizationParticipantIdentity}, {@link #APPLICATION_USER}
 * for {@link ApplicationParticipantIdentity}, {@link #DELEGATE} for
 * {@link DelegatingParticipantIdentity}, {@link #MACHINE} for {@link MachineParticipantIdentity} — and is
 * persisted as the document's polymorphic discriminator, so it is also the value queries filter identities
 * by kind with.
 */
public enum ParticipantIdentityType {
    SYSTEM_USER,
    ORGANIZATION_USER,
    APPLICATION_USER,
    DELEGATE,
    MACHINE
}
