package org.kinotic.system.api.services.deployment;

import io.vertx.core.Future;
import org.kinotic.core.api.annotations.Publish;
import org.kinotic.management.api.model.ProjectRepoToken;

/**
 * Short-lived credentials for writing a project's backing repository, for a platform service that commits code
 * into it for a user of the project's organization. Published in the system zone. The caller must act as that
 * user's {@code OrganizationParticipant}, which a cluster member binds with {@code SecurityContext.callAs}, and
 * tokens are issued only for projects of that participant's organization. A remote service trusts the participant a
 * cluster member sends, so the member must have authenticated it.
 */
@Publish
public interface ProjectRepoCredentialService {

    /**
     * Issues a token authorizing writes to the contents of the backing repository of the organization's project:
     * commits, branches and tags. A push to the repository's default branch deploys the project.
     *
     * @param projectId the project of the calling participant's organization whose repository the token is for
     * @return a future completing with the token, when it expires, and the repository's clone URL and default branch
     */
    Future<ProjectRepoToken> issueWriteToken(String projectId);
}
