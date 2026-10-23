package org.kinotic.system.internal.api.services.deployment;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.file.datalake.DataLakeServiceAsyncClient;
import com.azure.storage.file.datalake.DataLakeServiceClientBuilder;
import com.azure.storage.file.datalake.sas.DataLakeServiceSasSignatureValues;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.RequiredArgsConstructor;
import org.kinotic.system.api.config.KinoticSystemApiProperties;
import org.kinotic.system.api.config.UiDeploymentProperties;
import org.kinotic.system.api.services.deployment.SiteStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Data Lake SDK backed {@link SiteStorageService}. Reaches the sites account at its configured
 * blob endpoint as the server's Azure identity, which signs each URL with a user delegation
 * key for the site's directory alone.
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kinotic.systemApi.uiDeployment.siteStorage", havingValue = "AZURE", matchIfMissing = true)
public class AzureSiteStorageService extends DirectorySasSiteStorageService {

    /** How far in the past a delegation key starts, so clock skew between server and storage never rejects a fresh SAS. */
    private static final Duration KEY_START_SKEW = Duration.ofMinutes(5);

    private final Vertx vertx;
    private final KinoticSystemApiProperties kinoticProperties;
    private final TokenCredential credential = new DefaultAzureCredentialBuilder().build();
    // Built on first use, so a server that never publishes a UI opens no storage client
    private volatile DataLakeServiceAsyncClient directories;

    @Override
    protected Future<String> signDirectorySas(String hostname, DataLakeServiceSasSignatureValues values) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        return Future.fromCompletionStage(directories().getUserDelegationKey(now.minus(KEY_START_SKEW), values.getExpiryTime())
                                                       .map(key -> directories().getFileSystemAsyncClient(UiDeploymentProperties.SITES_CONTAINER)
                                                                                .getDirectoryAsyncClient(hostname)
                                                                                .generateUserDelegationSas(values, key))
                                                       .toFuture(), vertx.getOrCreateContext());
    }

    @Override
    protected UiDeploymentProperties uiDeploymentProperties() {
        return kinoticProperties.getSystemApi().getUiDeployment();
    }

    // The Data Lake client signs the directory SAS; it takes the blob endpoint and derives the
    // account's dfs endpoint from it
    private DataLakeServiceAsyncClient directories() {
        DataLakeServiceAsyncClient ret = directories;
        if (ret == null) {
            ret = new DataLakeServiceClientBuilder().endpoint(uiDeploymentProperties().getSitesStorageEndpoint())
                                                    .credential(credential)
                                                    .buildAsyncClient();
            directories = ret;
        }
        return ret;
    }

}
