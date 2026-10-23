package org.kinotic.management.api.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The Gitea server projects' repositories are hosted on when {@code kinotic.managementApi.repoHost} is
 * {@link RepoHostType#GITEA}, bound under {@code kinotic.managementApi.gitea.*}. Gitea is reached at a different
 * address from each place that reaches it, so each has its own URL (no trailing slash). Every field is required
 * when Gitea hosts the repositories, and checked when the server starts.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class GiteaProperties {

    /**
     * Gitea as this server reaches it, the base of its {@code /api/v1} API, e.g. {@code http://kinotic-gitea:3000}.
     */
    private String apiUrl;

    /**
     * Gitea as a person's browser and Git client reach it, e.g. {@code http://localhost:3001}: the base of the clone
     * URL a write credential is issued with, and of the links the portal shows. Gitea's own {@code ROOT_URL}.
     */
    private String webBaseUrl;

    /**
     * Gitea as a deployment's workloads reach it from their micro VMs, e.g. {@code http://192.168.127.254:3001}: the
     * base of the clone URL a sync workload fetches the project from.
     */
    private String workloadBaseUrl;

    /**
     * The management server as Gitea reaches it, the base of the push webhook each repository is given, e.g.
     * {@code http://kinotic-server-management:58503}.
     */
    private String webhookBaseUrl;

    /** The Gitea administrator this server creates organizations, repositories and their access with. */
    private String adminUsername;

    /**
     * The administrator's password. Also the key the passwords of the accounts each repository is read and written
     * through are derived from, so a server that knows it can always issue them a credential.
     */
    private String adminPassword;

}
