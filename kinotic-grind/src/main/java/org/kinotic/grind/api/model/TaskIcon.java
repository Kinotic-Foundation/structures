package org.kinotic.grind.api.model;

/**
 * The icon a console shows for a {@link Task}, declared with {@link Task#icon(TaskIcon)} or
 * {@link org.kinotic.grind.api.annotations.Task#icon()}.
 */
public enum TaskIcon {

    /** The console's default icon for a task. */
    DEFAULT,

    /** Choosing where the work runs. */
    TARGET,

    /** Source checked out from a repository. */
    GIT,

    /** Built artifacts. */
    PACKAGE,

    /** Server workloads. */
    SERVER,

    /** Sites published to the web. */
    GLOBE,

    /** A software bill of materials. */
    SBOM

}
