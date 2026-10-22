package org.kinotic.testserver;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.kinotic.core.api.event.ZonePartitioningService;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.model.security.ZoneRules;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.services.security.ZoneRulesService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@link ZoneRulesService} of the test server, which runs without the domain module's services and so without
 * the authorization engine: an organization participant hosts services in every application zone of its
 * organization, so the {@code @kinotic-ai/core} suite can publish a service in any application's zone.
 */
@Component
@RequiredArgsConstructor
public class TestZoneRulesService implements ZoneRulesService {

    private final ZonePartitioningService zonePartitioningService;

    @Override
    public Future<ZoneRules> rulesFor(Participant participant) {
        List<String> hostedZones = participant instanceof OrganizationParticipant organization
                ? List.of(DomainUtil.APP_ZONE_PREFIX + "." + organization.getOrganizationId())
                : List.of();
        return Future.succeededFuture(ZoneRules.from(participant, hostedZones).restrictedTo(zonePartitioningService));
    }
}
