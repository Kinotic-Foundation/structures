package org.kinotic.management.internal.api.services.github;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.kinotic.management.api.config.GithubProperties;
import org.kinotic.management.api.model.github.GitHubAppInstallation;
import org.kinotic.management.internal.api.services.github.client.GitHubApiClient;
import org.springframework.stereotype.Component;

/**
 * The installation that holds the project repositories of organizations that have not linked GitHub,
 * {@link GithubProperties#getPlatformInstallationId()}.
 */
@Component
@RequiredArgsConstructor
public class PlatformGitHubInstallation {

    private static final String ID = "platform";

    private final GithubProperties githubProperties;
    private final GitHubApiClient apiClient;

    private Future<GitHubAppInstallation> platformInstallation;

    /**
     * @param install an organization's own installation, or null when it has not linked GitHub
     * @return {@code install} when it is not null, otherwise the platform installation, or null when none is configured
     */
    public Future<GitHubAppInstallation> orPlatform(GitHubAppInstallation install) {
        Future<GitHubAppInstallation> ret;
        if (install != null || githubProperties.getPlatformInstallationId() == null) {
            ret = Future.succeededFuture(install);
        } else {
            ret = platformInstallation();
        }
        return ret;
    }

    /**
     * @return whether {@code install} is the platform installation, whose repositories every organization without
     * its own installation shares
     */
    public boolean isPlatform(GitHubAppInstallation install) {
        return ID.equals(install.getId());
    }

    private synchronized Future<GitHubAppInstallation> platformInstallation() {
        // a failed lookup is not kept, so the next project retries it
        if (platformInstallation == null || platformInstallation.failed()) {
            platformInstallation = apiClient.getInstallation(githubProperties.getPlatformInstallationId())
                                            .map(details -> new GitHubAppInstallation()
                                                    .setId(ID)
                                                    .setGithubInstallationId(details.id())
                                                    .setAccountLogin(details.accountLogin())
                                                    .setAccountType(details.accountType()));
        }
        return platformInstallation;
    }
}
