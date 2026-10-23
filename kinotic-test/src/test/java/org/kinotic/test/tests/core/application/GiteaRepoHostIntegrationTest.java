package org.kinotic.test.tests.core.application;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kinotic.management.api.config.GiteaProperties;
import org.kinotic.management.api.config.KinoticManagementApiProperties;
import org.kinotic.management.api.config.RepoHostType;
import org.kinotic.management.api.model.Project;
import org.kinotic.management.api.model.RepositoryConnectionStatus;
import org.kinotic.management.internal.api.services.gitea.GiteaClient;
import org.kinotic.management.internal.api.services.gitea.GiteaProjectRepoProvisioner;
import org.kinotic.management.internal.api.services.template.GraalJsSpawnRenderer;
import org.kinotic.management.internal.api.services.template.ProjectTemplateRenderer;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercises the Gitea repository host against a real Gitea, run as {@code compose.gitea.yml} runs it: a project's
 * repository generated from the template and rendered, its push webhook, and the tokens of the accounts it is read
 * and written through. Skips when Docker or git is unavailable.
 */
class GiteaRepoHostIntegrationTest {

    private static final Path COMPOSE = Path.of("../deployment/docker-compose/compose.gitea.yml");
    private static final String WEBHOOK_BASE_URL = "http://kinotic-server-management:58503";

    private static GenericContainer<?> gitea;
    private static Vertx vertx;
    private static GiteaClient giteaClient;
    private static GiteaProjectRepoProvisioner provisioner;
    private static String apiUrl;
    private static String adminUsername;
    private static String adminPassword;
    private static final HttpClient http = HttpClient.newHttpClient();

    @BeforeAll
    static void setup() throws Exception {
        Assumptions.assumeTrue(DockerClientFactory.instance().isDockerAvailable(), "Docker is required for the Gitea integration test");
        Assumptions.assumeTrue(run(Path.of("."), "git", "--version") == 0, "git is required for the Gitea integration test");

        Map<String, Object> service = composeService();
        @SuppressWarnings("unchecked")
        Map<String, Object> environment = (Map<String, Object>) service.get("environment");
        @SuppressWarnings("unchecked")
        List<String> command = ((List<String>) service.get("command")).stream()
                                                                         // compose's escape for a literal $
                                                                         .map(part -> part.replace("$$", "$"))
                                                                         .toList();
        gitea = new GenericContainer<>(DockerImageName.parse((String) service.get("image")))
                .withEnv(environment.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue()))))
                .withCommand(command.toArray(String[]::new))
                .withExposedPorts(3000)
                .waitingFor(Wait.forHttp("/api/healthz").forPort(3000).withStartupTimeout(Duration.ofMinutes(3)));
        gitea.start();

        apiUrl = "http://" + gitea.getHost() + ":" + gitea.getMappedPort(3000);
        adminUsername = String.valueOf(environment.get("KINOTIC_GITEA_ADMIN_USERNAME"));
        adminPassword = String.valueOf(environment.get("KINOTIC_GITEA_ADMIN_PASSWORD"));
        seedTemplate();

        KinoticManagementApiProperties properties = new KinoticManagementApiProperties();
        properties.getManagementApi().setRepoHost(RepoHostType.GITEA);
        properties.getManagementApi().getGithub().setRepoTemplate("kinotic-ai/project-template").setWebhookSecret("s3cret");
        properties.getManagementApi().setGitea(new GiteaProperties().setApiUrl(apiUrl)
                                                                    .setWebBaseUrl(apiUrl)
                                                                    .setWorkloadBaseUrl(apiUrl)
                                                                    .setWebhookBaseUrl(WEBHOOK_BASE_URL)
                                                                    .setAdminUsername(adminUsername)
                                                                    .setAdminPassword(adminPassword));
        vertx = Vertx.vertx();
        giteaClient = new GiteaClient(vertx, properties);
        giteaClient.start();
        provisioner = new GiteaProjectRepoProvisioner(vertx, giteaClient, new ProjectTemplateRenderer(new GraalJsSpawnRenderer()), properties);
    }

    @AfterAll
    static void tearDown() {
        if (giteaClient != null) {
            giteaClient.stop();
        }
        if (vertx != null) {
            vertx.close();
        }
        if (gitea != null) {
            gitea.stop();
        }
    }

    @Test
    void provisionsARepositoryWithTheRenderedTemplateAndItsPushWebhook() throws Exception {
        Project project = provisioner.provision(project("demo")).await();

        assertEquals("org-1/demo", project.getRepoFullName());
        assertEquals("main", project.getRepoDefaultBranch());
        assertEquals(RepositoryConnectionStatus.CONNECTED, project.getRepoConnectionStatus());

        Map<String, JsonObject> files = tree("org-1/demo");
        // .liquid content rendered with the project context, suffix stripped
        assertEquals("{\"name\": \"demo\", \"display\": \"demo\", \"org\": \"org-1\", \"app\": \"app-1\", \"project\": \"app-1-demo\"}",
                     content("org-1/demo", "package.json"));
        assertFalse(files.containsKey("package.json.liquid"));
        // liquid in paths rendered, and spawn.json consumed by the engine
        assertTrue(files.containsKey("src/Demo.ts"));
        assertFalse(files.containsKey("spawn.json"));
        // the executable bit survives for untemplated files and for templated paths
        assertEquals("100755", files.get("gradlew").getString("mode"));
        assertEquals("#!/bin/sh\necho demo\n", content("org-1/demo", "bin/demo.sh"));
        assertEquals("100755", files.get("bin/demo.sh").getString("mode"));
        // binary files are left as the template holds them
        assertTrue(files.containsKey("assets/logo.png"));
        // the server's npm package versions override the template's
        assertLinesMatch(List.of("\\^\\d+\\.\\d+\\.\\d+(-[0-9A-Za-z.-]+)?", "\\^\\d+\\.\\d+\\.\\d+(-[0-9A-Za-z.-]+)?"),
                         content("org-1/demo", "versions.txt").lines().toList());

        JsonArray hooks = new JsonArray(get("/repos/org-1/demo/hooks").body());
        assertEquals(1, hooks.size());
        assertEquals(WEBHOOK_BASE_URL + "/api/github/webhook", hooks.getJsonObject(0).getJsonObject("config").getString("url"));
        assertEquals(List.of("push"), hooks.getJsonObject(0).getJsonArray("events").getList());
    }

    @Test
    void reinitializesARenderedRepositoryWithoutAnotherCommit() {
        Project project = provisioner.provision(project("again")).await();
        String head = branchHead("org-1/again");

        Project reinitialized = provisioner.reinitialize(project).await();

        assertEquals(RepositoryConnectionStatus.CONNECTED, reinitialized.getRepoConnectionStatus());
        assertEquals(head, branchHead("org-1/again"));
        assertEquals(1, new JsonArray(get("/repos/org-1/again/hooks").body()).size());
    }

    @Test
    void readsAndWritesARepositoryOnlyThroughTheAccountOfItsAccess() throws Exception {
        provisioner.provision(project("tokens")).await();
        provisioner.provision(project("other")).await();
        String readPassword = "Read-" + UUID.randomUUID();
        String writePassword = "Write-" + UUID.randomUUID();
        giteaClient.ensureCollaborator("org-1/tokens", "kr-test", readPassword, "read").await();
        giteaClient.ensureCollaborator("org-1/tokens", "kw-test", writePassword, "write").await();
        String readToken = giteaClient.createToken("kr-test", readPassword, "read:repository", Duration.ofHours(1)).await();
        String writeToken = giteaClient.createToken("kw-test", writePassword, "write:repository", Duration.ofHours(1)).await();

        Path checkout = Files.createTempDirectory("gitea-tokens");
        assertEquals(0, run(checkout, "git", "clone", "-q", cloneUrl("org-1/tokens", readToken), "read"));
        // a repository the account is no collaborator of stays out of reach
        assertNotEquals(0, run(checkout, "git", "ls-remote", cloneUrl("org-1/other", readToken)));

        Path read = checkout.resolve("read");
        Files.writeString(read.resolve("NOTES.md"), "notes\n");
        run(read, "git", "add", "NOTES.md");
        run(read, "git", "-c", "user.email=e2e@kinotic.test", "-c", "user.name=e2e", "commit", "-q", "-m", "Notes");
        assertNotEquals(0, run(read, "git", "push", "-q", cloneUrl("org-1/tokens", readToken), "HEAD:main"));
        assertEquals(0, run(read, "git", "push", "-q", cloneUrl("org-1/tokens", writeToken), "HEAD:main"));
    }

    @Test
    void deletesAnAccountsTokensOlderThanTheirLifetimeWhenIssuingAnother() {
        provisioner.provision(project("lifetime")).await();
        String password = "Life-" + UUID.randomUUID();
        giteaClient.ensureCollaborator("org-1/lifetime", "kr-life", password, "read").await();
        String first = giteaClient.createToken("kr-life", password, "read:repository", Duration.ofHours(1)).await();
        String second = giteaClient.createToken("kr-life", password, "read:repository", Duration.ZERO).await();

        assertEquals(0, run(Path.of("."), "git", "ls-remote", cloneUrl("org-1/lifetime", second)));
        assertNotEquals(0, run(Path.of("."), "git", "ls-remote", cloneUrl("org-1/lifetime", first)));
    }

    private static Project project(String name) {
        Project project = new Project();
        project.setId("app-1-" + name);
        project.setName(name);
        project.setOrganizationId("org-1");
        project.setApplicationId("app-1");
        project.setRepoPrivate(true);
        return project;
    }

    // The template the provisioner generates from, pushed with git so its files keep their executable bits
    private static void seedTemplate() throws Exception {
        Path template = Files.createTempDirectory("gitea-template");
        write(template, "spawn.json", "{\"globals\": {\"kinoticCoreVersion\": \"^0.0.1\"}, \"propertySchema\": {\"projectName\": {\"type\": \"string\"}}}", false);
        write(template, "versions.txt.liquid", "{{ kinoticCoreVersion }}\n{{ kinoticCliVersion }}\n", false);
        write(template, "package.json.liquid",
              "{\"name\": \"{{projectSlug}}\", \"display\": \"{{projectName}}\", \"org\": \"{{organizationId}}\", \"app\": \"{{applicationId}}\", \"project\": \"{{projectId}}\"}",
              false);
        write(template, "src/{{ projectName | camelCase | upperFirst }}.ts.liquid", "export class {{ projectName | camelCase | upperFirst }} {}", false);
        write(template, "gradlew", "#!/bin/sh\n", true);
        write(template, "bin/{{ projectName }}.sh.liquid", "#!/bin/sh\necho {{ projectName }}\n", true);
        Files.createDirectories(template.resolve("assets"));
        Files.write(template.resolve("assets/logo.png"), new byte[]{(byte) 0x89, 'P', 'N', 'G', 0, 1});

        HttpResponse<String> created = send("POST", "/user/repos", new JsonObject().put("name", "project-template").put("private", true));
        assertEquals(201, created.statusCode(), created.body());
        run(template, "git", "init", "-q", "-b", "main");
        run(template, "git", "add", "-A");
        run(template, "git", "-c", "user.email=e2e@kinotic.test", "-c", "user.name=e2e", "commit", "-q", "-m", "Template");
        String pushUrl = apiUrl.replace("http://", "http://" + adminUsername + ":" + adminPassword + "@") + "/" + adminUsername + "/project-template.git";
        assertEquals(0, run(template, "git", "push", "-q", pushUrl, "main"));
    }

    private static void write(Path root, String path, String content, boolean executable) throws IOException {
        Path file = root.resolve(path);
        Files.createDirectories(file.getParent());
        Files.writeString(file, content);
        file.toFile().setExecutable(executable);
    }

    private static Map<String, JsonObject> tree(String fullName) {
        JsonObject tree = new JsonObject(get("/repos/" + fullName + "/git/trees/main?recursive=true").body());
        return tree.getJsonArray("tree").stream()
                   .map(JsonObject.class::cast)
                   .filter(entry -> "blob".equals(entry.getString("type")))
                   .collect(Collectors.toMap(entry -> entry.getString("path"), entry -> entry));
    }

    private static String content(String fullName, String path) {
        JsonObject file = new JsonObject(get("/repos/" + fullName + "/contents/" + path + "?ref=main").body());
        return new String(Base64.getMimeDecoder().decode(file.getString("content")), StandardCharsets.UTF_8);
    }

    private static String branchHead(String fullName) {
        return new JsonObject(get("/repos/" + fullName + "/branches/main").body()).getJsonObject("commit").getString("id");
    }

    private static String cloneUrl(String fullName, String token) {
        return apiUrl.replace("http://", "http://x-access-token:" + token + "@") + "/" + fullName + ".git";
    }

    private static HttpResponse<String> get(String uri) {
        return send("GET", uri, null);
    }

    private static HttpResponse<String> send(String method, String uri, JsonObject body) {
        String auth = Base64.getEncoder().encodeToString((adminUsername + ":" + adminPassword).getBytes(StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl + "/api/v1" + uri))
                                         .header("Authorization", "Basic " + auth)
                                         .header("Content-Type", "application/json")
                                         .method(method, body != null ? HttpRequest.BodyPublishers.ofString(body.encode())
                                                                      : HttpRequest.BodyPublishers.noBody())
                                         .build();
        try {
            return http.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Gitea request " + method + " " + uri + " failed", e);
        }
    }

    private static int run(Path directory, String... command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command).directory(directory.toFile()).redirectErrorStream(true);
            // a refused credential fails the command rather than prompting for another
            builder.environment().put("GIT_TERMINAL_PROMPT", "0");
            Process process = builder.start();
            process.getInputStream().readAllBytes();
            return process.waitFor();
        } catch (IOException | InterruptedException e) {
            return -1;
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> composeService() throws IOException {
        Map<String, Object> compose = new Yaml().load(Files.readString(COMPOSE));
        return (Map<String, Object>) ((Map<String, Object>) compose.get("services")).get("kinotic-gitea");
    }

}
