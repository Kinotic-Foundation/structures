package org.kinotic.system.internal.api.services;

import com.azure.core.credential.TokenCredential;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.resourcemanager.storage.StorageManager;
import com.azure.resourcemanager.storage.models.BlobContainer;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobStorageException;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.kinotic.domain.api.model.DeploymentStatus;
import org.kinotic.domain.api.model.DeploymentStatusType;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.OrganizationStorage;
import org.kinotic.management.api.model.UiDeployment;
import org.kinotic.system.api.config.KinoticSystemApiProperties;
import org.kinotic.system.api.config.OrganizationStorageProperties;
import org.kinotic.system.api.config.UiDeploymentProperties;
import org.kinotic.system.api.services.OrganizationStorageProvisioner;
import org.kinotic.system.api.services.UiStoragePaths;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Provisions one organization's storage and publishes one site against a real Azure
 * subscription, the way the provisioning job and a deployment do, and reads back what Azure
 * serves. Runs only on a developer machine set up as the contributing guide describes: the
 * {@code local} profile in {@code kinotic-server/src/main/resources/application-local.yml}
 * names the subscription, resource group, sites account and domain, and the service principal's
 * {@code AZURE_*} variables are in the environment, which the module's test task takes from
 * {@code .env.local}. Skipped everywhere else. Everything it creates is idempotent and left in
 * place, so a second run reads through what the first created.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AzureProvisioningIntegrationTest {

    private static final Path LOCAL_PROFILE = Path.of("..", "kinotic-server", "src", "main", "resources", "application-local.yml");
    private static final String ORGANIZATION_ID = "kinotic-azure-it";
    private static final String APPLICATION_ID = "azure-it-app";
    private static final String PROJECT_ID = "azure-it-project";
    private static final String UI_NAME = "web";
    private static final String SITE_LABEL = "azure-it";
    private static final String COMMIT_SHA = "0000000000000000000000000000000000000000";
    private static final String INDEX_HTML = "<!doctype html><title>azure-it</title>";
    /** A storage account or a Front Door write takes minutes; a step past this is stuck. */
    private static final long STEP_TIMEOUT_MINUTES = 15;
    /** A site serves once its configuration reaches Front Door's edges, which Microsoft bounds at 15 minutes per change and longer when changes queue. */
    private static final long SITE_TIMEOUT_MINUTES = 45;
    private static final long SITE_POLL_MS = 30_000;

    private Vertx vertx;
    private KinoticSystemApiProperties properties;
    private TokenCredential credential;
    private AzureOrganizationStorageProvisioner storageProvisioner;
    private FrontDoorUiDeploymentProvisioner siteProvisioner;
    private AzureSiteStorageService siteStorage;
    private final HttpClient http = HttpClient.newHttpClient();
    private Organization organization;

    @BeforeAll
    void requireAzure() throws IOException {
        assumeTrue(Files.exists(LOCAL_PROFILE), "no " + LOCAL_PROFILE + ": not a developer machine set up for Azure");
        assumeTrue(System.getenv("AZURE_CLIENT_ID") != null, "AZURE_CLIENT_ID is not set: .env.local is not in the environment");
        properties = load(LOCAL_PROFILE);
        assumeFalse(storageProperties().isDisableProvisioner(), "the local profile disables the storage provisioner");
        assumeFalse(uiProperties().isDisableProvisioner(), "the local profile disables the site provisioner");

        vertx = Vertx.vertx();
        credential = new DefaultAzureCredentialBuilder().build();
        StubOrganizationService organizations = new StubOrganizationService();
        organizations.saved.put(ORGANIZATION_ID, new Organization().setId(ORGANIZATION_ID)
                                                                   .setName("Azure integration test")
                                                                   .setCreated(new Date()));
        storageProvisioner = new AzureOrganizationStorageProvisioner(organizations, vertx, properties);
        siteProvisioner = new FrontDoorUiDeploymentProvisioner(vertx, properties, new StubUiDeploymentRepository());
        siteStorage = new AzureSiteStorageService(vertx, properties);
    }

    @AfterAll
    void close() {
        if (vertx != null) {
            vertx.close();
        }
    }

    @Test
    @Order(1)
    @Timeout(value = STEP_TIMEOUT_MINUTES, unit = TimeUnit.MINUTES)
    void provisionsTheStorageAccount() {
        organization = await(storageProvisioner.ensureStorage(ORGANIZATION_ID));

        OrganizationStorage storage = organization.getStorage();
        assertEquals(DeploymentStatusType.READY, storage.getStatus().type(), storage.getStatus().message());
        assertTrue(storage.getAzureBlobEndpoint().startsWith("https://"), storage.getAzureBlobEndpoint());

        StorageManager storageManager = StorageManager.authenticate(credential, profileOf(storage.getAzureSubscriptionId()));
        BlobContainer container = storageManager.blobContainers().get(storageProperties().getResourceGroup(),
                                                                      storage.getAzureAccountName(),
                                                                      OrganizationStorageProvisioner.UI_CONTAINER);
        assertNotNull(container, "the " + OrganizationStorageProvisioner.UI_CONTAINER + " container exists");
    }

    @Test
    @Order(3)
    @Timeout(value = SITE_TIMEOUT_MINUTES, unit = TimeUnit.MINUTES)
    void servesASite() throws Exception {
        assumeTrue(uiProperties().getSitesStorageEndpoint() != null, "the local profile names no sites account: apply the dev root first");
        String hostname = uiProperties().resolveHostname(SITE_LABEL);
        // the files go up through the site's upload URL before the site is checked, as the publish task orders it
        String uploadUrl = await(siteStorage.issueUploadUrl(hostname, Duration.ofMinutes(STEP_TIMEOUT_MINUTES)));
        upload(uploadUrl, "version.json", new JsonObject().put("commitSha", COMMIT_SHA).encode(), "application/json", COMMIT_SHA);
        upload(uploadUrl, "index.html", INDEX_HTML, "text/html", COMMIT_SHA);
        UiDeployment deployment = new UiDeployment().setId(SITE_LABEL)
                                                    .setOrganizationId(ORGANIZATION_ID)
                                                    .setApplicationId(APPLICATION_ID)
                                                    .setProjectId(PROJECT_ID)
                                                    .setName(UI_NAME)
                                                    .setCommitSha(COMMIT_SHA)
                                                    .setStatus(new DeploymentStatus(DeploymentStatusType.PROVISIONING))
                                                    .setCreated(new Date())
                                                    .setUpdated(new Date());

        UiDeployment site = await(siteProvisioner.provision(deployment));

        // nothing is provisioned per site: the wildcard domain serves the hostname from its first request
        while (site.getStatus().type() == DeploymentStatusType.PROVISIONING) {
            Thread.sleep(SITE_POLL_MS);
            site = await(siteProvisioner.checkProvisioning(site));
        }
        assertEquals(DeploymentStatusType.READY, site.getStatus().type(), site.getStatus().message());

        String url = uiProperties().resolveSiteUrl(SITE_LABEL);
        assertEquals(new JsonObject().put("commitSha", COMMIT_SHA).encode(), get(url + "/version.json").body(), "a file is served as it is");
        assertEquals(INDEX_HTML, get(url + "/").body(), "the spa rule serves index.html at the root");
        assertEquals(INDEX_HTML, get(url + "/some/route").body(), "the spa rule serves index.html for a route");
        assertEquals(INDEX_HTML, get(url + "/?code=abc&state=xyz").body(), "a query string, as an OAuth callback carries, reaches the index");
        assertEquals(404, get(url + "/missing.js").statusCode(), "a file that is not published is not the index");
    }

    private HttpResponse<String> get(String url) throws Exception {
        return http.send(HttpRequest.newBuilder(URI.create(url)).GET().build(), HttpResponse.BodyHandlers.ofString());
    }

    @Test
    @Order(4)
    @Timeout(value = STEP_TIMEOUT_MINUTES, unit = TimeUnit.MINUTES)
    void publishesIntoTheSitesAccount() throws Exception {
        assumeTrue(uiProperties().getSitesStorageEndpoint() != null, "the local profile names no sites account: apply the dev root first");
        String hostname = uiProperties().resolveHostname(SITE_LABEL);
        String other = uiProperties().resolveHostname(SITE_LABEL + "-other");
        String container = uiProperties().getSitesStorageEndpoint().replaceAll("/$", "") + "/" + UiStoragePaths.SITES_CONTAINER;

        String uploadUrl = await(siteStorage.issueUploadUrl(hostname, Duration.ofMinutes(STEP_TIMEOUT_MINUTES)));
        assertTrue(uploadUrl.startsWith(container + "/" + hostname + "?"), "the upload URL names the site's directory: " + uploadUrl);
        String sas = uploadUrl.substring(uploadUrl.indexOf('?') + 1);

        upload(uploadUrl, "index.html", INDEX_HTML, "text/html", COMMIT_SHA);
        upload(uploadUrl, "assets/old.js", "// stale", "text/javascript", "1".repeat(40));
        // the SAS is scoped to the directory: a sibling site's directory refuses it
        BlobStorageException refused = assertThrows(BlobStorageException.class,
                                                    () -> upload(container + "/" + other + "?" + sas, "index.html", INDEX_HTML, "text/html", COMMIT_SHA));
        assertEquals(403, refused.getStatusCode(), "an upload outside the site's directory is refused");

        await(siteStorage.deleteFilesOfOtherCommits(hostname, COMMIT_SHA));
        assertFalse(siteBlob(hostname + "/assets/old.js").exists(), "the other commit's file is deleted");
        assertTrue(siteBlob(hostname + "/index.html").exists(), "the current commit's file stays");

        await(siteStorage.deleteSite(hostname));
        assertFalse(siteBlob(hostname + "/index.html").exists(), "removing the site deletes its files");
    }

    /** Uploads one file the way the publish workload does: through the site's upload URL, stamped with its commit. */
    private static void upload(String uploadUrl, String path, String content, String contentType, String commitSha) {
        int query = uploadUrl.indexOf('?');
        BlobClient blob = new BlobClientBuilder().endpoint(uploadUrl.substring(0, query) + "/" + path + uploadUrl.substring(query))
                                                 .buildClient();
        blob.upload(BinaryData.fromString(content), true);
        blob.setHttpHeaders(new BlobHttpHeaders().setContentType(contentType).setCacheControl("no-cache"));
        blob.setMetadata(Map.of("commit", commitSha));
    }

    /** A blob of the sites container, read as the test's own identity. */
    private BlobClient siteBlob(String name) {
        return new BlobClientBuilder().endpoint(uiProperties().getSitesStorageEndpoint())
                                      .containerName(UiStoragePaths.SITES_CONTAINER)
                                      .blobName(name)
                                      .credential(credential)
                                      .buildClient();
    }

    private <T> T await(Future<T> future) {
        T ret = null;
        try {
            ret = future.toCompletionStage().toCompletableFuture().get(STEP_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        } catch (ExecutionException e) {
            fail(e.getCause().getMessage(), e.getCause());
        } catch (Exception e) {
            fail(e);
        }
        return ret;
    }

    private OrganizationStorageProperties storageProperties() {
        return properties.getSystemApi().getOrganizationStorage();
    }

    private UiDeploymentProperties uiProperties() {
        return properties.getSystemApi().getUiDeployment();
    }

    private static AzureProfile profileOf(String subscriptionId) {
        return new AzureProfile(null, subscriptionId, AzureEnvironment.AZURE);
    }

    /** Binds the two property blocks the provisioners read from the local profile's YAML. */
    @SuppressWarnings("unchecked")
    private static KinoticSystemApiProperties load(Path localProfile) throws IOException {
        Map<String, Object> yaml;
        try (Reader reader = Files.newBufferedReader(localProfile)) {
            yaml = new Yaml().load(reader);
        }
        Map<String, Object> systemApi = (Map<String, Object>) ((Map<String, Object>) yaml.get("kinotic")).get("systemApi");
        Map<String, Object> storage = (Map<String, Object>) systemApi.get("organizationStorage");
        Map<String, Object> sites = (Map<String, Object>) systemApi.get("uiDeployment");

        KinoticSystemApiProperties ret = new KinoticSystemApiProperties();
        ret.getSystemApi().getOrganizationStorage()
           .setDisableProvisioner(Boolean.TRUE.equals(storage.get("disableProvisioner")))
           // the development profile turns private endpoints off for every developer machine; the local profile inherits that
           .setDisablePrivateEndpoint(!Boolean.FALSE.equals(storage.get("disablePrivateEndpoint")))
           .setSubscriptionIds((List<String>) storage.get("subscriptionIds"))
           .setResourceGroup((String) storage.get("resourceGroup"))
           .setLocation((String) storage.get("location"));
        ret.getSystemApi().getUiDeployment()
           .setDisableProvisioner(Boolean.TRUE.equals(sites.get("disableProvisioner")))
           .setSitesDomain((String) sites.get("sitesDomain"))
           .setDnsZoneId((String) sites.get("dnsZoneId"))
           .setFrontDoorProfileId((String) sites.get("frontDoorProfileId"))
           .setFrontDoorEndpointHostName((String) sites.get("frontDoorEndpointHostName"))
           .setSitesStorageEndpoint((String) sites.get("sitesStorageEndpoint"));
        return ret;
    }

}
