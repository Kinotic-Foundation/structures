package org.kinotic.structures.api.domain;

import org.kinotic.continuum.api.security.Participant;

/**
 * Holds information for all "Entity" related operations.
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public interface EntityContext {

    /**
     * @return the {@link Participant} that is performing the operation
     */
    Participant getParticipant();

}
