package org.kinotic.structures.api.domain;

import org.kinotic.continuum.api.security.Participant;

import java.util.List;

/**
 * Holds information for all "Entity" related operations.
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public interface EntityContext {

    /**
     * @return the {@link Participant} that is performing the operation
     */
    Participant getParticipant();

    /**
     * If defined this will restrict the response to only include the fields listed here.
     * @return a list of included fields, or an empty {@link List} or null if all fields should be included
     */
    List<String> getIncludedFieldsFilter();

    /**
     * Returns whether there is an included fields filter defined.
     * @return true if an included fields filter is defined, false otherwise
     */
    boolean hasIncludedFieldsFilter();

}
