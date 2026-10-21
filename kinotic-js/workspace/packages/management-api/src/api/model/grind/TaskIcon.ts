/**
 * The icon a console shows for a task, mirroring TaskIcon on the server.
 */
export enum TaskIcon {
    /** The console's default icon for a task. */
    DEFAULT = 'DEFAULT',
    /** Choosing where the work runs. */
    TARGET = 'TARGET',
    /** Source checked out from a repository. */
    GIT = 'GIT',
    /** Built artifacts. */
    PACKAGE = 'PACKAGE',
    /** Server workloads. */
    SERVER = 'SERVER',
    /** Sites published to the web. */
    GLOBE = 'GLOBE',
    /** A software bill of materials. */
    SBOM = 'SBOM'
}
