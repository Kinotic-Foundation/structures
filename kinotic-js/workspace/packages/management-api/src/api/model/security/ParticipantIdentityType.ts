/**
 * What kind of principal a {@link ParticipantIdentity} represents; the persisted polymorphic
 * discriminator of the document.
 */
export enum ParticipantIdentityType {
    SYSTEM_USER = 'SYSTEM_USER',
    ORGANIZATION_USER = 'ORGANIZATION_USER',
    APPLICATION_USER = 'APPLICATION_USER',
    DELEGATE = 'DELEGATE',
    MACHINE = 'MACHINE'
}
