package org.kinotic.management.api.services.github;

import io.vertx.core.Future;
import org.kinotic.management.api.model.ProjectRepoToken;
import org.kinotic.management.api.services.ProjectRepoTokenProvider;

/**
 * Operations against a Kinotic Project's backing GitHub repository: minting
 * short-lived installation tokens for worker clones (the platform's
 * {@link ProjectRepoTokenProvider}) and for writing the repository, and creating
 * tags or branches as part of release flows.
 * <p>
 * In-process only: callers are trusted server-side code passing the
 * {@code organizationId} explicitly.
 */
// @Publish TODO: not exposed until we are ready to use by worker nodes directly — publishing
// requires participant enforcement (caller's org must match organizationId) to come back first
public interface GitHubProjectRepoService extends ProjectRepoTokenProvider {

    /**
     * Issues a token authorizing writes to the contents of the given project's backing repository: commits,
     * branches and tags.
     *
     * @param organizationId the organization the project must belong to
     * @param projectId      the project whose repository the token is for
     * @return a future completing with the token and its clone metadata
     */
    Future<ProjectRepoToken> issueRepoWriteToken(String organizationId, String projectId);

    /**
     * Creates a lightweight tag on the project's backing repo.
     *
     * @param organizationId the Kinotic org id the project must belong to
     * @param projectId      the project whose repo to tag
     * @param tagName        e.g. {@code v1.2.0}
     * @param sha            full 40-character commit SHA the tag should point at
     */
    Future<Void> createTag(String organizationId,
                           String projectId,
                           String tagName,
                           String sha);

    /**
     * Creates a branch on the project's backing repo pointing at {@code sha}.
     */
    Future<Void> createBranch(String organizationId,
                              String projectId,
                              String branchName,
                              String sha);
}
