package org.kinotic.system.internal.api.services.deployment;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.management.api.model.ProjectRepoToken;
import org.kinotic.management.api.services.github.GitHubProjectRepoService;
import org.kinotic.system.api.services.deployment.ProjectRepoCredentialService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultProjectRepoCredentialService implements ProjectRepoCredentialService {

    private final GitHubProjectRepoService gitHubProjectRepoService;
    private final SecurityContext securityContext;

    @Override
    public Future<ProjectRepoToken> issueWriteToken(String projectId) {
        Validate.notBlank(projectId, "projectId is required");
        OrganizationParticipant participant = securityContext.requireParticipant(OrganizationParticipant.class);
        return gitHubProjectRepoService.issueRepoWriteToken(participant.getOrganizationId(), projectId);
    }
}
