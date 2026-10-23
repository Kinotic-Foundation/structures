package org.kinotic.management.internal.api.services.gitea;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.kinotic.management.api.config.KinoticManagementApiProperties;
import org.kinotic.management.api.config.RepoHostType;
import org.kinotic.management.api.model.Project;
import org.kinotic.management.api.model.RepositoryHost;
import org.kinotic.management.api.model.RepositoryConnectionStatus;
import org.kinotic.management.api.services.ProjectRepoProvisioner;
import org.kinotic.management.api.services.github.GitHubWebhookProcessor;
import org.kinotic.management.internal.api.services.github.client.CreatedRepository;
import org.kinotic.management.internal.api.services.template.ProjectTemplateRenderer;
import org.kinotic.management.internal.api.services.template.RenderedTemplate;
import org.kinotic.management.internal.api.services.template.TarballFile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Provisions a new {@link Project}'s repository on the Gitea server the platform administers: in the Gitea
 * organization named by the project's organization id, generated from the platform's project template, which Gitea
 * imports from GitHub ({@code kinotic.managementApi.github.repoTemplate}) the first time it is needed, with the
 * template's contents rendered for the project ({@link ProjectTemplateRenderer}) in a second commit. The repository is
 * named {@link ProjectTemplateRenderer#repoName}, and given a push webhook to the management server, signed with
 * {@code kinotic.managementApi.github.webhookSecret}, once its contents are rendered, so a push deploys the project.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kinotic.managementApi.repoHost", havingValue = "GITEA")
public class GiteaProjectRepoProvisioner implements ProjectRepoProvisioner {

    private final Vertx vertx;
    private final GiteaClient giteaClient;
    private final ProjectTemplateRenderer templateRenderer;
    private final KinoticManagementApiProperties properties;
    // The one in-flight or completed import of the template, shared by every provisioning on this server
    private Future<String> template;

    @Override
    public Future<Project> provision(Project project) {
        Validate.notBlank(project.getName(), "Project name must not be blank");
        String owner = project.getOrganizationId();
        return giteaClient.ensureOrganization(owner)
                          .compose(v -> template())
                          .compose(templateFullName -> giteaClient.generateRepository(templateFullName, owner,
                                                                                      templateRenderer.repoName(project),
                                                                                      project.getDescription(),
                                                                                      project.isRepoPrivate()))
                          .map(repo -> stamp(project, repo))
                          // Adopt-not-orphan: the repo exists now, so an initialization failure marks the project for
                          // retry instead of failing the create and leaving an untracked repo behind
                          .compose(p -> initializeRepo(p).recover(err -> {
                              log.error("Initialization failed for {}; repository created, marking project {} {}",
                                        p.getRepoFullName(), p.getId(), RepositoryConnectionStatus.INITIALIZATION_FAILED, err);
                              p.setRepoConnectionStatus(RepositoryConnectionStatus.INITIALIZATION_FAILED);
                              return Future.succeededFuture(p);
                          }));
    }

    @Override
    public Future<Project> reinitialize(Project project) {
        Validate.notBlank(project.getRepoFullName(), "Project repoFullName must be set to reinitialize");
        Validate.notBlank(project.getRepoDefaultBranch(), "Project repoDefaultBranch must be set to reinitialize");
        return initializeRepo(project);
    }

    @Override
    public RepositoryHost repositoryHost() {
        return new RepositoryHost(RepoHostType.GITEA, properties.getManagementApi().getGitea().getWebBaseUrl());
    }

    // synchronized: two provisionings on different event loops must share one import, since a second would delete the
    // repository the first is still importing into as an empty one
    private synchronized Future<String> template() {
        Future<String> ret = template;
        if (ret == null || ret.failed()) {
            String source = properties.getManagementApi().getGithub().getRepoTemplate();
            ret = giteaClient.ensureTemplate(source.substring(source.indexOf('/') + 1), "https://github.com/" + source + ".git");
            template = ret;
        }
        return ret;
    }

    /**
     * Renders the template contents the repository was generated with into a commit on its default branch, then
     * gives it its push webhook. Rendering contents that are already rendered changes nothing, so a retry of a
     * failed initialization commits only what is still missing.
     */
    private Future<Project> initializeRepo(Project project) {
        String fullName = project.getRepoFullName();
        String branch = project.getRepoDefaultBranch();
        return giteaClient.downloadArchive(fullName, branch)
                          // the render is CPU-bound and blocking; ordered=false lets concurrent creations render in parallel
                          .compose(tarball -> vertx.executeBlocking(() -> templateRenderer.render(project, tarball), false))
                          .compose(rendered -> giteaClient.fileShas(fullName, branch).map(shas -> fileChanges(rendered, shas)))
                          .compose(changes -> changes.isEmpty()
                                  ? Future.succeededFuture()
                                  : giteaClient.changeFiles(fullName, branch, "Initialize " + project.getName(), changes).mapEmpty())
                          .compose(v -> giteaClient.ensurePushWebhook(fullName,
                                                                      properties.getManagementApi().getGitea().getWebhookBaseUrl()
                                                                              + GitHubWebhookProcessor.WEBHOOK_PATH,
                                                                      properties.getManagementApi().getGithub().getWebhookSecret()))
                          .map(v -> {
                              project.setRepoConnectionStatus(RepositoryConnectionStatus.CONNECTED);
                              log.info("Initialized {} with rendered baseline for project {}", fullName, project.getId());
                              return project;
                          });
    }

    /**
     * The file operations that turn the repository's files, by their path with their blob SHA, into the rendered
     * baseline: a rendered file at its source's path replaces it, one at a new path moves its source there (a move
     * keeps the source's executable bit), and a template text file nothing was rendered from is deleted. Binary
     * files are left as they are.
     */
    private static JsonArray fileChanges(RenderedTemplate rendered, Map<String, String> shas) {
        JsonArray ret = new JsonArray();
        Set<String> moved = new HashSet<>();
        rendered.renderedFiles().forEach((path, content) -> {
            String source = rendered.sources().get(path);
            String encoded = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
            if (shas.containsKey(path)) {
                if (!content.equals(templateText(rendered, path))) {
                    ret.add(new JsonObject().put("operation", "update").put("path", path)
                                            .put("sha", shas.get(path)).put("content", encoded));
                }
            } else if (source != null && shas.containsKey(source) && !rendered.renderedFiles().containsKey(source)
                    && moved.add(source)) {
                ret.add(new JsonObject().put("operation", "update").put("from_path", source).put("path", path)
                                        .put("sha", shas.get(source)).put("content", encoded));
            } else {
                ret.add(new JsonObject().put("operation", "create").put("path", path).put("content", encoded));
            }
        });
        shas.forEach((path, sha) -> {
            if (!rendered.renderedFiles().containsKey(path) && !rendered.binaryFiles().containsKey(path) && !moved.contains(path)) {
                ret.add(new JsonObject().put("operation", "delete").put("path", path).put("sha", sha));
            }
        });
        return ret;
    }

    // The text of a template file as the repository holds it, or null for a path the template has no file at
    private static String templateText(RenderedTemplate rendered, String path) {
        TarballFile file = rendered.templateFiles().get(path);
        return file != null ? new String(file.content(), StandardCharsets.UTF_8) : null;
    }

    private Project stamp(Project project, CreatedRepository repo) {
        project.setRepoFullName(repo.fullName());
        project.setRepoId(repo.id());
        project.setRepoDefaultBranch(repo.defaultBranch());
        log.info("Provisioned Gitea repo {} for project {} (org {})",
                 project.getRepoFullName(), project.getId(), project.getOrganizationId());
        return project;
    }

}
