package org.kinotic.system.internal.api.services.deployment;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.management.api.model.ProjectRepoToken;
import org.kinotic.management.api.services.github.GitHubProjectRepoService;
import org.kinotic.system.api.services.deployment.ProjectRepoCredentialService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultProjectRepoCredentialService implements ProjectRepoCredentialService {

    private final GitHubProjectRepoService gitHubProjectRepoService;

    @Override
    public Future<ProjectRepoToken> issueWriteToken(String organizationId, String projectId) {
        Validate.notBlank(organizationId, "organizationId is required");
        Validate.notBlank(projectId, "projectId is required");
        return gitHubProjectRepoService.issueRepoWriteToken(organizationId, projectId);
    }
}
