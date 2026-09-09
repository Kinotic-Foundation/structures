package org.kinotic.system.api.services;

import io.vertx.core.Future;

import java.time.Duration;

/**
 * The data plane of the platform's sites storage account: what the platform does with one
 * site's files, which live under the site's hostname as {@link UiStoragePaths} lays them out.
 */
public interface SiteStorageService {

    /**
     * Issues the URL a publish workload uploads one site's files through: the site's
     * directory in the sites account, with a query carrying a SAS that allows creating and
     * writing blobs within that directory alone until {@code ttl} has passed. The workload
     * appends each file's path before the query.
     *
     * @param hostname the site's hostname, which names its directory
     * @param ttl      how long the SAS stays valid
     * @return a future emitting the upload URL
     */
    Future<String> issueUploadUrl(String hostname, Duration ttl);

    /**
     * Deletes every blob of the site that a publish of another commit wrote: the publish
     * workload stamps each blob with its commit, and the blobs of the given commit stay.
     *
     * @param hostname the site's hostname
     * @param commit   the commit whose files stay
     * @return a future completing once the other commits' files are gone
     */
    Future<Void> deleteFilesOfOtherCommits(String hostname, String commit);

    /**
     * Deletes every blob of the site. A site with no files is not a failure.
     */
    Future<Void> deleteSite(String hostname);

}
