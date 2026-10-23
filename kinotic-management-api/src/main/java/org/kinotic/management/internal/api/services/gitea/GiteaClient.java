package org.kinotic.management.internal.api.services.gitea;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.kinotic.management.api.config.GiteaProperties;
import org.kinotic.management.api.config.KinoticManagementApiProperties;
import org.kinotic.management.internal.api.services.github.client.CreatedRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * The calls the platform makes to the Gitea server projects' repositories are hosted on, through its {@code /api/v1}
 * API as the configured administrator, or as one of the accounts a repository is read and written through. Each
 * call that creates something succeeds when the thing already exists, so a repeated or retried call is harmless.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kinotic.managementApi.repoHost", havingValue = "GITEA")
public class GiteaClient {

    /** Gitea's limit on the entries one page of a repository tree lists. */
    private static final int TREE_PAGE_SIZE = 1000;
    private static final String TOKEN_NAME_PREFIX = "kinotic-";

    private final Vertx vertx;
    private final KinoticManagementApiProperties properties;
    private WebClient webClient;

    @PostConstruct
    public void start() {
        GiteaProperties gitea = gitea();
        Validate.notBlank(gitea.getApiUrl(), "kinotic.managementApi.gitea.apiUrl is required when Gitea hosts the repositories");
        Validate.notBlank(gitea.getWebBaseUrl(), "kinotic.managementApi.gitea.webBaseUrl is required when Gitea hosts the repositories");
        Validate.notBlank(gitea.getWorkloadBaseUrl(), "kinotic.managementApi.gitea.workloadBaseUrl is required when Gitea hosts the repositories");
        Validate.notBlank(gitea.getWebhookBaseUrl(), "kinotic.managementApi.gitea.webhookBaseUrl is required when Gitea hosts the repositories");
        Validate.notBlank(gitea.getAdminUsername(), "kinotic.managementApi.gitea.adminUsername is required when Gitea hosts the repositories");
        Validate.notBlank(gitea.getAdminPassword(), "kinotic.managementApi.gitea.adminPassword is required when Gitea hosts the repositories");
        webClient = WebClient.create(vertx);
    }

    @PreDestroy
    public void stop() {
        if (webClient != null) {
            webClient.close();
        }
    }

    /** The administrator every repository and organization this client creates is administered by. */
    public String adminUsername() {
        return gitea().getAdminUsername();
    }

    /** Creates the organization {@code name}, private, when it doesn't exist. */
    public Future<Void> ensureOrganization(String name) {
        return send(asAdmin(HttpMethod.POST, "/orgs"), new JsonObject().put("username", name).put("visibility", "private"))
                .compose(response -> expect(response, "create the organization " + name, 201, 422));
    }

    /**
     * Makes {@code adminUsername/name} a template repository holding a copy of the Git repository at
     * {@code cloneUrl}, importing it when it doesn't exist or an earlier import left it empty.
     *
     * @return a future emitting the template's full name
     */
    public Future<String> ensureTemplate(String name, String cloneUrl) {
        String fullName = adminUsername() + "/" + name;
        return send(asAdmin(HttpMethod.GET, "/repos/" + fullName), null).compose(found -> {
            Future<Void> imported;
            if (found.statusCode() == 404) {
                imported = importRepository(name, cloneUrl);
            } else if (found.statusCode() == 200 && found.bodyAsJsonObject().getBoolean("empty", false)) {
                // a failed import leaves the repository behind with nothing in it
                imported = send(asAdmin(HttpMethod.DELETE, "/repos/" + fullName), null)
                        .compose(deleted -> expect(deleted, "delete the empty template " + fullName, 204))
                        .compose(v -> importRepository(name, cloneUrl));
            } else {
                imported = expect(found, "find the template " + fullName, 200);
            }
            return imported;
        }).compose(v -> send(asAdmin(HttpMethod.PATCH, "/repos/" + fullName), new JsonObject().put("template", true)))
          .compose(response -> expect(response, "mark " + fullName + " a template", 200))
          .map(fullName);
    }

    private Future<Void> importRepository(String name, String cloneUrl) {
        log.info("Importing the project template {} into Gitea as {}/{}", cloneUrl, adminUsername(), name);
        JsonObject body = new JsonObject().put("clone_addr", cloneUrl)
                                          .put("repo_name", name)
                                          .put("repo_owner", adminUsername())
                                          .put("private", true)
                                          .put("service", "git");
        return send(asAdmin(HttpMethod.POST, "/repos/migrate"), body)
                .compose(response -> expect(response, "import " + cloneUrl, 201));
    }

    /** Creates the repository {@code owner/name} with the contents of the template {@code templateFullName}. */
    public Future<CreatedRepository> generateRepository(String templateFullName,
                                                        String owner,
                                                        String name,
                                                        String description,
                                                        boolean isPrivate) {
        JsonObject body = new JsonObject().put("owner", owner)
                                          .put("name", name)
                                          .put("private", isPrivate)
                                          .put("git_content", true);
        if (description != null && !description.isBlank()) {
            body.put("description", description);
        }
        return send(asAdmin(HttpMethod.POST, "/repos/" + templateFullName + "/generate"), body)
                .compose(response -> expect(response, "create the repository " + owner + "/" + name, 201)
                        .map(v -> {
                            JsonObject repo = response.bodyAsJsonObject();
                            return new CreatedRepository(repo.getLong("id"), repo.getString("full_name"),
                                                         repo.getString("default_branch"));
                        }));
    }

    /** The gzipped tarball of {@code branch} of the repository, every file under one root folder. */
    public Future<byte[]> downloadArchive(String fullName, String branch) {
        return send(asAdmin(HttpMethod.GET, "/repos/" + fullName + "/archive/" + branch + ".tar.gz"), null)
                .compose(response -> expect(response, "download " + fullName, 200)
                        .map(v -> response.body().getBytes()));
    }

    /** The blob SHA of every file on {@code branch} of the repository, by its path. */
    public Future<Map<String, String>> fileShas(String fullName, String branch) {
        return fileShas(fullName, branch, 1, new HashMap<>());
    }

    private Future<Map<String, String>> fileShas(String fullName, String branch, int page, Map<String, String> found) {
        String uri = "/repos/" + fullName + "/git/trees/" + branch + "?recursive=true&per_page=" + TREE_PAGE_SIZE + "&page=" + page;
        return send(asAdmin(HttpMethod.GET, uri), null).compose(response -> {
            Future<Map<String, String>> ret = expect(response, "list the files of " + fullName, 200).map(found);
            JsonObject tree = response.bodyAsJsonObject();
            tree.getJsonArray("tree", new JsonArray()).stream()
                .map(JsonObject.class::cast)
                .filter(entry -> "blob".equals(entry.getString("type")))
                .forEach(entry -> found.put(entry.getString("path"), entry.getString("sha")));
            if (page * TREE_PAGE_SIZE < tree.getInteger("total_count", 0)) {
                ret = fileShas(fullName, branch, page + 1, found);
            }
            return ret;
        });
    }

    /**
     * Commits {@code files}, Gitea's file operations, to {@code branch} of the repository in one commit.
     *
     * @return a future emitting the commit's SHA
     */
    public Future<String> changeFiles(String fullName, String branch, String message, JsonArray files) {
        JsonObject body = new JsonObject().put("branch", branch).put("message", message).put("files", files);
        return send(asAdmin(HttpMethod.POST, "/repos/" + fullName + "/contents"), body)
                .compose(response -> expect(response, "commit to " + fullName, 201)
                        .map(v -> response.bodyAsJsonObject().getJsonObject("commit").getString("sha")));
    }

    /** Gives the repository a push webhook delivering to {@code url}, signed with {@code secret}, when it has none. */
    public Future<Void> ensurePushWebhook(String fullName, String url, String secret) {
        return send(asAdmin(HttpMethod.GET, "/repos/" + fullName + "/hooks"), null).compose(found -> {
            Future<Void> ret;
            boolean exists = found.statusCode() == 200
                    && found.bodyAsJsonArray().stream()
                            .map(JsonObject.class::cast)
                            .anyMatch(hook -> url.equals(hook.getJsonObject("config", new JsonObject()).getString("url")));
            if (exists) {
                ret = Future.succeededFuture();
            } else {
                JsonObject body = new JsonObject()
                        .put("type", "gitea")
                        .put("active", true)
                        .put("events", new JsonArray().add("push"))
                        .put("config", new JsonObject().put("url", url).put("content_type", "json").put("secret", secret));
                ret = send(asAdmin(HttpMethod.POST, "/repos/" + fullName + "/hooks"), body)
                        .compose(response -> expect(response, "add the webhook of " + fullName, 201));
            }
            return ret;
        });
    }

    /**
     * Makes {@code username} a collaborator of the repository with {@code permission} ({@code read} or
     * {@code write}), creating the account with {@code password}, or setting its password, first.
     */
    public Future<Void> ensureCollaborator(String fullName, String username, String password, String permission) {
        JsonObject account = new JsonObject().put("username", username)
                                              .put("email", username + "@kinotic.invalid")
                                              .put("password", password)
                                              .put("must_change_password", false)
                                              .put("visibility", "private");
        return send(asAdmin(HttpMethod.POST, "/admin/users"), account).compose(created -> {
            Future<Void> ret;
            if (created.statusCode() == 409 || created.statusCode() == 422) {
                // the account exists; its password is set again in case the administrator's, which it derives from, changed
                JsonObject update = new JsonObject().put("login_name", username).put("source_id", 0).put("password", password);
                ret = send(asAdmin(HttpMethod.PATCH, "/admin/users/" + username), update)
                        .compose(response -> expect(response, "update the account " + username, 200));
            } else {
                ret = expect(created, "create the account " + username, 201);
            }
            return ret;
        }).compose(v -> send(asAdmin(HttpMethod.PUT, "/repos/" + fullName + "/collaborators/" + username),
                             new JsonObject().put("permission", permission)))
          .compose(response -> expect(response, "add " + username + " to " + fullName, 204));
    }

    /**
     * Creates an access token of {@code username} with the given scope, and deletes the tokens this client created
     * for the account more than {@code lifetime} ago.
     *
     * @return a future emitting the token
     */
    public Future<String> createToken(String username, String password, String scope, Duration lifetime) {
        long now = System.currentTimeMillis();
        String userUri = "/users/" + username + "/tokens";
        return send(asUser(HttpMethod.GET, userUri, username, password), null)
                .compose(listed -> expect(listed, "list the tokens of " + username, 200).map(v -> listed.bodyAsJsonArray()))
                .compose(tokens -> Future.all(tokens.stream()
                                                    .map(JsonObject.class::cast)
                                                    .filter(token -> createdBefore(token.getString("name"), now - lifetime.toMillis()))
                                                    .map(token -> send(asUser(HttpMethod.DELETE, userUri + "/" + token.getLong("id"), username, password), null))
                                                    .toList()))
                .compose(v -> send(asUser(HttpMethod.POST, userUri, username, password),
                                   new JsonObject().put("name", TOKEN_NAME_PREFIX + now + "-" + UUID.randomUUID())
                                                   .put("scopes", new JsonArray().add(scope))))
                .compose(created -> expect(created, "create a token of " + username, 201)
                        .map(v -> created.bodyAsJsonObject().getString("sha1")));
    }

    // A token this client created is named kinotic-<creation time in milliseconds>-<uuid>
    private static boolean createdBefore(String name, long instant) {
        boolean ret = false;
        if (name != null && name.startsWith(TOKEN_NAME_PREFIX)) {
            String[] parts = name.substring(TOKEN_NAME_PREFIX.length()).split("-", 2);
            try {
                ret = Long.parseLong(parts[0]) < instant;
            } catch (NumberFormatException e) {
                // a token named by someone else stays
            }
        }
        return ret;
    }

    private HttpRequest<Buffer> asAdmin(HttpMethod method, String uri) {
        return asUser(method, uri, gitea().getAdminUsername(), gitea().getAdminPassword());
    }

    private HttpRequest<Buffer> asUser(HttpMethod method, String uri, String username, String password) {
        return webClient.requestAbs(method, gitea().getApiUrl() + "/api/v1" + uri)
                        .basicAuthentication(username, password);
    }

    private static Future<HttpResponse<Buffer>> send(HttpRequest<Buffer> request, JsonObject body) {
        return body != null ? request.sendJsonObject(body) : request.send();
    }

    private static Future<Void> expect(HttpResponse<Buffer> response, String operation, Integer... accepted) {
        Future<Void> ret;
        if (Set.of(accepted).contains(response.statusCode())) {
            ret = Future.succeededFuture();
        } else {
            ret = Future.failedFuture(new IllegalStateException("Gitea answered " + response.statusCode()
                                                                + " when asked to " + operation + ": " + response.bodyAsString()));
        }
        return ret;
    }

    private GiteaProperties gitea() {
        return properties.getManagementApi().getGitea();
    }

}
