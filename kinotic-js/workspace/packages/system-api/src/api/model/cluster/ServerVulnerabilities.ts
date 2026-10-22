import type { VulnerabilityFinding } from '@kinotic-ai/management-api'

/**
 * The advisories affecting the libraries one build of a Kinotic server runs, with the cluster nodes
 * running that build. Mirrors the server's org.kinotic.system.api.model.cluster.ServerVulnerabilities.
 */
export class ServerVulnerabilities {
    /** The server kind the build is, such as management, system or app. */
    public serverName: string = ''
    /** The Kinotic version of the build; null when its nodes run from classes rather than a packaged jar. */
    public version: string | null = null
    /** The ids of the nodes running the build. */
    public nodeIds: string[] = []
    /** How many libraries the build runs; none when its nodes run from classes rather than a packaged jar, which lists no libraries. */
    public componentCount: number = 0
    /** The advisories affecting the build's libraries, the most severe first. */
    public findings: VulnerabilityFinding[] = []
}
