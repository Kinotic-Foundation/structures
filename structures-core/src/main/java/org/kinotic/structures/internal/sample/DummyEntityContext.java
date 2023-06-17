package org.kinotic.structures.internal.sample;

import org.kinotic.continuum.core.api.security.DefaultParticipant;
import org.kinotic.continuum.core.api.security.MetadataConstants;
import org.kinotic.continuum.core.api.security.Participant;
import org.kinotic.structures.api.domain.EntityContext;

import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 6/8/23.
 */
public class DummyEntityContext implements EntityContext {

    private final Participant participant;

    public DummyEntityContext() {
        participant = new DefaultParticipant("kinotic",
                                             "dummy",
                                             Map.of(MetadataConstants.TYPE_KEY, "dummy"),
                                             List.of("ADMIN"));
    }

    public DummyEntityContext(String tenantId,
                              String participantId) {
        participant = new DefaultParticipant(tenantId,
                                             participantId,
                                             Map.of(MetadataConstants.TYPE_KEY, "dummy"),
                                             List.of("ADMIN"));
    }

    @Override
    public Participant getParticipant() {
        return participant;
    }
}
