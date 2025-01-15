package org.kinotic.structures.api.domain;

import org.kinotic.continuum.api.security.Participant;

/**
 * Contains information about the security context of the current operation
 */
public interface SecurityContext {

    /**
     * @return the {@link Participant} that is performing the operation
     */
    Participant getParticipant();

}
