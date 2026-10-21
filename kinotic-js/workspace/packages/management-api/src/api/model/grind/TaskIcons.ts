/**
 * The icon keys a task can declare, mirroring TaskIcons on the server. An icon is a key rather
 * than an enum, so a console adds icons, or offers them to choose from, with no change to the
 * server. A key a console does not know shows its default icon.
 */
export const TaskIcons = {
    /** Choosing where the work runs. */
    TARGET: 'target',
    /** Source checked out from a repository. */
    GIT: 'git',
    /** Built artifacts. */
    PACKAGE: 'package',
    /** Server workloads. */
    SERVER: 'server',
    /** Sites published to the web. */
    GLOBE: 'globe',
    /** A software bill of materials. */
    SBOM: 'sbom'
} as const
