package org.kinotic.structures.api.domain;

import org.kinotic.continuum.api.security.Participant;

import java.util.List;

/**
 * Contains information about the security context of the current operation
 */
public interface SecurityContext {

    /**
     * @return the {@link Participant} that is performing the operation
     */
    Participant getParticipant();

    /**
     * @return the lists of tenants that data is being requested for
     */
    List<String> getTenantSelection();

    /**
     * Sets the tenant selection for the current operation
     * @param tenantSelection the lists of tenants that data is being requested for
     */
    SecurityContext setTenantSelection(List<String> tenantSelection);

}
