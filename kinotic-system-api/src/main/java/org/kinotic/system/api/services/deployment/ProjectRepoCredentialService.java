package org.kinotic.system.api.services.deployment;

import io.vertx.core.Future;
import org.kinotic.core.api.annotations.Publish;
import org.kinotic.management.api.model.ProjectRepoToken;

/**
 * Short-lived credentials for writing a project's backing repository, for a platform service that commits code
 * into it on the project's organization's behalf. Published in the system zone. Callers are
 * trusted: the caller authorizes the organization it names, and this service checks only that the project belongs
 * to that organization.
 */
@Publish
public interface ProjectRepoCredentialService {

    /**
     * Issues a token authorizing writes to the contents of the backing repository of the organization's project:
     * commits, branches and tags. A push to the repository's default branch deploys the project.
     *
     * @param organizationId the organization the project must belong to
     * @param projectId      the project whose repository the token is for
     * @return a future completing with the token, when it expires, and the repository's clone URL and default branch
     */
    Future<ProjectRepoToken> issueWriteToken(String organizationId, String projectId);
}
