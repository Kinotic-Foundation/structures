package org.kinotic.system.api.services;

import org.apache.commons.lang3.Validate;

/**
 * The layout of published UIs in storage. Every path is built here, so it exists in exactly
 * one place. In the platform's sites account a site is one directory of the {@code sites}
 * container, named by its hostname, which is what the Front Door rule set derives from a
 * request's host:
 *
 * <pre>
 * sites/&lt;hostname&gt;/index.html          the site's entry, replaced last on each publish
 * sites/&lt;hostname&gt;/version.json        {@code { "commitSha": "..." }}
 * sites/&lt;hostname&gt;/assets/...           the build's hashed files, cached for a year
 * sites/&lt;hostname&gt;/...                  the rest of the build, never cached
 * </pre>
 *
 * Every blob is stamped with the commit that published it, so a publish can delete what
 * older commits left. An organization's own account, from before the sites account, lays
 * a UI out under {@code prod/<app>/ui/<ui>/} instead.
 */
public final class UiStoragePaths {

    /** The container of the sites account, and of an organization's account. */
    public static final String SITES_CONTAINER = "sites";

    private static final String ENVIRONMENT = "prod";

    private UiStoragePaths() {
    }

    /**
     * The directory of one site in the sites account: its hostname.
     */
    public static String sitePrefix(String hostname) {
        Validate.notBlank(hostname, "hostname cannot be blank");
        return hostname;
    }

    /**
     * The prefix every UI of the application is published under; a publish workload uploads
     * relative to it.
     */
    public static String applicationPrefix(String applicationId) {
        Validate.notBlank(applicationId, "applicationId cannot be blank");
        return ENVIRONMENT + "/" + applicationId + "/ui";
    }

    /**
     * The prefix one UI is published under: the site's root.
     */
    public static String uiPrefix(String applicationId, String uiName) {
        Validate.notBlank(uiName, "uiName cannot be blank");
        return applicationPrefix(applicationId) + "/" + uiName;
    }

}
