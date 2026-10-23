package org.kinotic.grind.api.model;

/**
 * Who owns a {@link JobRun}: an organization, or the system itself.
 */
public enum JobOwnerType {

    /** Owned by an organization, as a project's deployments are. */
    ORGANIZATION,

    /** Owned by no organization: a run the system executes on its own behalf, see {@link JobOwner#system()}. */
    SYSTEM

}
