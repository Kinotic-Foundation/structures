package org.kinotic.system.internal.api.services.deployment;

import com.azure.storage.file.datalake.sas.DataLakeServiceSasSignatureValues;
import com.azure.storage.file.datalake.sas.PathSasPermission;
import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.system.api.config.UiDeploymentProperties;
import org.kinotic.system.api.services.deployment.SiteStorageService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * {@link SiteStorageService} whose URLs name the site's directory in the sites container, at
 * {@code kinotic.systemApi.uiDeployment.sitesStorageEndpoint}, and carry a SAS for that directory alone.
 * A subclass signs the SAS with its account's credential.
 */
public abstract class DirectorySasSiteStorageService implements SiteStorageService {

    @Override
    public Future<String> issueUploadUrl(String hostname, Duration ttl) {
        // list and delete within the directory let the workload clear the files of other commits
        return issue(hostname, ttl, new PathSasPermission().setCreatePermission(true)
                                                           .setWritePermission(true)
                                                           .setListPermission(true)
                                                           .setDeletePermission(true));
    }

    @Override
    public Future<String> issueRemovalUrl(String hostname, Duration ttl) {
        return issue(hostname, ttl, new PathSasPermission().setListPermission(true).setDeletePermission(true));
    }

    /**
     * Signs a SAS with the given values for the directory of the site with the given hostname in
     * {@link UiDeploymentProperties#SITES_CONTAINER}.
     *
     * @return a future emitting the SAS query, without the leading {@code ?}
     */
    protected abstract Future<String> signDirectorySas(String hostname, DataLakeServiceSasSignatureValues values);

    /**
     * Where the sites storage account is reached.
     */
    protected abstract UiDeploymentProperties uiDeploymentProperties();

    private Future<String> issue(String hostname, Duration ttl, PathSasPermission permission) {
        Validate.notBlank(hostname, "hostname cannot be blank");
        Validate.notNull(ttl, "ttl is required");
        DataLakeServiceSasSignatureValues values =
                new DataLakeServiceSasSignatureValues(OffsetDateTime.now(ZoneOffset.UTC).plus(ttl), permission);
        return signDirectorySas(hostname, values)
                .map(token -> uiDeploymentProperties().resolveSiteDirectoryUrl(hostname) + "?" + token);
    }

}
