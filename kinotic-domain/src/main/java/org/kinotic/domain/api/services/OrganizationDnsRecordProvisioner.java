package org.kinotic.domain.api.services;

import io.vertx.core.Future;

/**
 * Creates the DNS record an organization's published sites resolve by: one wildcard record for
 * {@code *.<org>.<published sites domain>}, under which each site is {@code <app>--<ui>.<org>.<domain>} and
 * from which the edge's certificate for the organization follows. Called when an organization is created
 * and again before its first site is published, so an organization whose record could not be created at
 * sign-up gets it the moment a site needs serving.
 */
public interface OrganizationDnsRecordProvisioner {

    /**
     * Ensures the organization's record exists, creating it when there is none and leaving an existing one
     * as it is.
     *
     * @param organizationId the organization's id, which is the record's label under the domain
     * @return completes once the record exists, or fails when the DNS provider refused it
     */
    Future<Void> ensure(String organizationId);
}
