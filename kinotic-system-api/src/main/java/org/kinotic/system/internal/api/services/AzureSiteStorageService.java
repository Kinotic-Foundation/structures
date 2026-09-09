package org.kinotic.system.internal.api.services;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.BlobServiceAsyncClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobListDetails;
import com.azure.storage.blob.models.BlobStorageException;
import com.azure.storage.blob.models.ListBlobsOptions;
import com.azure.storage.file.datalake.DataLakeDirectoryAsyncClient;
import com.azure.storage.file.datalake.DataLakeServiceAsyncClient;
import com.azure.storage.file.datalake.DataLakeServiceClientBuilder;
import com.azure.storage.file.datalake.sas.DataLakeServiceSasSignatureValues;
import com.azure.storage.file.datalake.sas.PathSasPermission;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.kinotic.system.api.config.KinoticSystemApiProperties;
import org.kinotic.system.api.config.UiDeploymentProperties;
import org.kinotic.system.api.services.SiteStorageService;
import org.kinotic.system.api.services.UiStoragePaths;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;

/**
 * Blob-SDK backed {@link SiteStorageService}. Reaches the sites account at its configured
 * blob endpoint as the server's Azure identity, which signs each upload URL with a user
 * delegation key for the site's directory alone.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AzureSiteStorageService implements SiteStorageService {

    /** How far in the past a delegation key starts, so clock skew between server and storage never rejects a fresh SAS. */
    private static final Duration KEY_START_SKEW = Duration.ofMinutes(5);
    private static final int DELETE_CONCURRENCY = 8;
    /** The metadata the publish workload stamps every blob with: the commit it belongs to. */
    private static final String COMMIT_METADATA = "commit";

    private final Vertx vertx;
    private final KinoticSystemApiProperties kinoticProperties;
    private final TokenCredential credential = new DefaultAzureCredentialBuilder().build();
    // Built on first use, so a server that never publishes a UI opens no storage client
    private volatile BlobServiceAsyncClient blobs;
    private volatile DataLakeServiceAsyncClient directories;

    @Override
    public Future<String> issueUploadUrl(String hostname, Duration ttl) {
        Validate.notNull(ttl, "ttl is required");
        String directory = UiStoragePaths.sitePrefix(hostname);
        BlobContainerAsyncClient container = container();
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime expiry = now.plus(ttl);
        DataLakeDirectoryAsyncClient site = directories().getFileSystemAsyncClient(UiStoragePaths.SITES_CONTAINER)
                                                         .getDirectoryAsyncClient(directory);
        DataLakeServiceSasSignatureValues values = new DataLakeServiceSasSignatureValues(
                expiry, new PathSasPermission().setCreatePermission(true).setWritePermission(true));
        return AzureUtil.toFuture(directories().getUserDelegationKey(now.minus(KEY_START_SKEW), expiry)
                                              .map(key -> site.generateUserDelegationSas(values, key)), vertx)
                        // the workload uploads through the blob endpoint, which honors the directory SAS
                        .map(token -> container.getBlobContainerUrl() + "/" + directory + "?" + token);
    }

    @Override
    public Future<Void> deleteFilesOfOtherCommits(String hostname, String commit) {
        Validate.notBlank(commit, "commit is required");
        BlobContainerAsyncClient container = container();
        ListBlobsOptions options = new ListBlobsOptions().setPrefix(UiStoragePaths.sitePrefix(hostname) + "/")
                                                         .setDetails(new BlobListDetails().setRetrieveMetadata(true));
        return deleteAll(container, container.listBlobs(options).filter(item -> isDirectory(item) || !isOf(item, commit)));
    }

    // A blob without the stamp predates stamping, so no publish keeps it
    private static boolean isOf(BlobItem item, String commit) {
        return item.getMetadata() != null && commit.equals(item.getMetadata().get(COMMIT_METADATA));
    }

    @Override
    public Future<Void> deleteSite(String hostname) {
        BlobContainerAsyncClient container = container();
        ListBlobsOptions options = new ListBlobsOptions().setPrefix(UiStoragePaths.sitePrefix(hostname))
                                                         .setDetails(new BlobListDetails().setRetrieveMetadata(true));
        return deleteAll(container, container.listBlobs(options));
    }

    // The account has a hierarchical namespace, so a flat listing includes directories, which
    // are deletable only once empty: the files go first, then the directories deepest first,
    // and a directory still holding a kept file is left where it is
    private Future<Void> deleteAll(BlobContainerAsyncClient container, Flux<BlobItem> items) {
        return AzureUtil.toFuture(items.collectList().flatMap(all -> {
            List<BlobItem> files = all.stream().filter(item -> !isDirectory(item)).toList();
            List<BlobItem> directories = all.stream()
                                            .filter(AzureSiteStorageService::isDirectory)
                                            .sorted(Comparator.comparingInt((BlobItem item) -> item.getName().length()).reversed())
                                            .toList();
            return Flux.fromIterable(files)
                       .flatMap(item -> container.getBlobAsyncClient(item.getName()).deleteIfExists(), DELETE_CONCURRENCY)
                       .thenMany(Flux.fromIterable(directories))
                       .concatMap(item -> container.getBlobAsyncClient(item.getName()).deleteIfExists()
                                                   .onErrorResume(AzureSiteStorageService::isNotEmpty, error -> Mono.empty()))
                       .then();
        }), vertx);
    }

    private static boolean isDirectory(BlobItem item) {
        return item.getMetadata() != null && "true".equals(item.getMetadata().get("hdi_isfolder"));
    }

    private static boolean isNotEmpty(Throwable error) {
        // a hierarchical namespace error the blob SDK's error codes do not name
        return error instanceof BlobStorageException storage && "DirectoryIsNotEmpty".equals(String.valueOf(storage.getErrorCode()));
    }

    private BlobContainerAsyncClient container() {
        BlobServiceAsyncClient ret = blobs;
        if (ret == null) {
            ret = new BlobServiceClientBuilder().endpoint(properties().getSitesStorageEndpoint())
                                                .credential(credential)
                                                .buildAsyncClient();
            blobs = ret;
        }
        return ret.getBlobContainerAsyncClient(UiStoragePaths.SITES_CONTAINER);
    }

    // The Data Lake client, for the directory SAS; it takes the blob endpoint and derives the
    // account's dfs endpoint from it
    private DataLakeServiceAsyncClient directories() {
        DataLakeServiceAsyncClient ret = directories;
        if (ret == null) {
            ret = new DataLakeServiceClientBuilder().endpoint(properties().getSitesStorageEndpoint())
                                                    .credential(credential)
                                                    .buildAsyncClient();
            directories = ret;
        }
        return ret;
    }

    private UiDeploymentProperties properties() {
        return kinoticProperties.getSystemApi().getUiDeployment();
    }

}
