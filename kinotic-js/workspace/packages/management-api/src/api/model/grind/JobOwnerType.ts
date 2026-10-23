/**
 * Who owns a JobRun: an organization, or the system itself.
 */
export enum JobOwnerType {
    /** Owned by an organization, as a project's deployments are. */
    ORGANIZATION = 'ORGANIZATION',
    /** Owned by no organization: a run the system executes on its own behalf. */
    SYSTEM = 'SYSTEM'
}
