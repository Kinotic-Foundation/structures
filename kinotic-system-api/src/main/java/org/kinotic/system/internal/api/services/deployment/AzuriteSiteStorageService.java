package org.kinotic.system.internal.api.services.deployment;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.sas.BlobContainerSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.StorageSharedKeyCredential;
import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.system.api.config.KinoticSystemApiProperties;
import org.kinotic.system.api.config.UiDeploymentProperties;
import org.kinotic.system.api.services.deployment.SiteStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * {@link SiteStorageService} of the Azurite emulator a machine runs as its sites storage account. Each URL
 * names the site's directory, but its SAS allows its operations on the whole sites container, which holds
 * every organization's sites: a workload holding one can write or delete any site's files.
 */
@Component
@RequiredArgsConstructor
// Active for DISABLED too, where nothing calls it, so the services that take a SiteStorageService wire in every mode
@ConditionalOnExpression("'${kinotic.systemApi.uiDeployment.siteStorage:AZURE}'.toUpperCase() != 'AZURE'")
public class AzuriteSiteStorageService implements SiteStorageService {

    // Azurite's well-known development account, the same on every machine; it signs only for the emulator
    private static final String ACCOUNT_NAME = "devstoreaccount1";
    private static final String ACCOUNT_KEY = "Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==";

    private final KinoticSystemApiProperties kinoticProperties;

    @Override
    public Future<String> issueUploadUrl(String hostname, Duration ttl) {
        // list and delete let the workload clear the files of other commits
        return issue(hostname, ttl, new BlobContainerSasPermission().setCreatePermission(true)
                                                                    .setWritePermission(true)
                                                                    .setListPermission(true)
                                                                    .setDeletePermission(true));
    }

    @Override
    public Future<String> issueRemovalUrl(String hostname, Duration ttl) {
        return issue(hostname, ttl, new BlobContainerSasPermission().setListPermission(true).setDeletePermission(true));
    }

    // Azurite signs no directory SAS, so the narrowest scope it honors is the container
    private Future<String> issue(String hostname, Duration ttl, BlobContainerSasPermission permission) {
        Validate.notBlank(hostname, "hostname cannot be blank");
        Validate.notNull(ttl, "ttl is required");
        UiDeploymentProperties properties = kinoticProperties.getSystemApi().getUiDeployment();
        BlobContainerClient container = new BlobContainerClientBuilder()
                .endpoint(properties.getSitesStorageEndpoint())
                .containerName(UiDeploymentProperties.SITES_CONTAINER)
                .credential(new StorageSharedKeyCredential(ACCOUNT_NAME, ACCOUNT_KEY))
                .buildClient();
        String token = container.generateSas(new BlobServiceSasSignatureValues(OffsetDateTime.now(ZoneOffset.UTC).plus(ttl), permission));
        return Future.succeededFuture(properties.resolveSiteDirectoryUrl(hostname) + "?" + token);
    }

}
