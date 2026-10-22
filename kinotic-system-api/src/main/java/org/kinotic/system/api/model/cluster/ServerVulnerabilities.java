package org.kinotic.system.api.model.cluster;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.management.api.model.vulnerability.VulnerabilityFinding;

import java.util.ArrayList;
import java.util.List;

/**
 * The advisories affecting the libraries one build of a Kinotic server runs, with the cluster nodes
 * running that build.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ServerVulnerabilities {

    /**
     * The server kind the build is, such as {@code management}, {@code system} or {@code app}.
     */
    private String serverName;

    /**
     * The Kinotic version of the build, or {@code null} when its nodes run from classes rather than a
     * packaged jar.
     */
    private String version;

    /**
     * The ids of the nodes running the build.
     */
    private List<String> nodeIds = new ArrayList<>();

    /**
     * How many libraries the build runs; none when its nodes run from classes rather than a packaged
     * jar, which lists no libraries.
     */
    private int componentCount;

    /**
     * The advisories affecting the build's libraries, the most severe first.
     */
    private List<VulnerabilityFinding> findings = new ArrayList<>();

}
