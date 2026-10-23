/** Who owns a job run: an organization, or the platform itself. */
export enum JobRunOwnerType {
  /** Owned by an organization, as a project's deployments are. */
  ORGANIZATION = 'ORGANIZATION',
  /** Owned by no organization, as the platform's system jobs are. */
  PLATFORM = 'PLATFORM'
}
