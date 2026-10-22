package org.kinotic.domain.api.services.security;

import io.vertx.core.Future;
import org.kinotic.core.api.event.ZonePartitioningService;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.model.security.ZoneRules;

/**
 * The {@link ZoneRules} a connected participant is held to on this server.
 */
public interface ZoneRulesService {

    /**
     * The participant's zone rules narrowed to what this server's {@link ZonePartitioningService} serves. An
     * organization's machine hosts the services of the applications it may register services of, so on a server
     * that hosts application zones it subscribes in those applications' zones.
     *
     * @param participant the authenticated participant
     * @return the participant's zone rules on this server
     */
    Future<ZoneRules> rulesFor(Participant participant);

}
