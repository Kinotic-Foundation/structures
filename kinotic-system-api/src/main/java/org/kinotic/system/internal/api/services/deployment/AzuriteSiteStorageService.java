package org.kinotic.system.internal.api.services.deployment;

import com.azure.storage.common.StorageSharedKeyCredential;
import com.azure.storage.file.datalake.DataLakeServiceClientBuilder;
import com.azure.storage.file.datalake.sas.DataLakeServiceSasSignatureValues;
import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.kinotic.system.api.config.KinoticSystemApiProperties;
import org.kinotic.system.api.config.UiDeploymentProperties;
import org.kinotic.system.api.services.deployment.SiteStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * {@link SiteStorageService} of the Azurite emulator a machine runs as its sites storage account, reached through
 * the storage gateway of the local edge ({@code deployment/docker-compose/sites-edge.ts}) at
 * {@code kinotic.systemApi.uiDeployment.sitesStorageEndpoint}. Each URL carries a SAS for the site's directory
 * alone, signed with Azurite's well-known development key, which the gateway checks before passing the request
 * on to the emulator.
 */
@Component
@RequiredArgsConstructor
// Active for DISABLED too, where nothing calls it, so the services that take a SiteStorageService wire in every mode
@ConditionalOnExpression("'${kinotic.systemApi.uiDeployment.siteStorage:AZURE}'.toUpperCase() != 'AZURE'")
public class AzuriteSiteStorageService extends DirectorySasSiteStorageService {

    // Azurite's well-known development account, the same on every machine; it signs only for the emulator
    private static final String ACCOUNT_NAME = "devstoreaccount1";
    private static final String ACCOUNT_KEY = "Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==";

    private final KinoticSystemApiProperties kinoticProperties;

    @Override
    protected Future<String> signDirectorySas(String hostname, DataLakeServiceSasSignatureValues values) {
        // signing is local, so the client never connects
        String token = new DataLakeServiceClientBuilder().endpoint(uiDeploymentProperties().getSitesStorageEndpoint())
                                                         .credential(new StorageSharedKeyCredential(ACCOUNT_NAME, ACCOUNT_KEY))
                                                         .buildClient()
                                                         .getFileSystemClient(UiDeploymentProperties.SITES_CONTAINER)
                                                         .getDirectoryClient(hostname)
                                                         .generateSas(values);
        return Future.succeededFuture(token);
    }

    @Override
    protected UiDeploymentProperties uiDeploymentProperties() {
        return kinoticProperties.getSystemApi().getUiDeployment();
    }

}
