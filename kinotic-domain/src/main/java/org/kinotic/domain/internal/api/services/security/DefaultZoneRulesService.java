package org.kinotic.domain.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.authz.api.model.AuthzObject;
import org.kinotic.authz.api.model.AuthzRelation;
import org.kinotic.authz.api.model.AuthzType;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.core.api.event.ZonePartitioningService;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.model.security.ZoneRules;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.services.security.ZoneRulesService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DefaultZoneRulesService implements ZoneRulesService {

    private final ZonePartitioningService zonePartitioningService;
    private final RelationshipService relationships;

    @Override
    public Future<ZoneRules> rulesFor(Participant participant) {
        Validate.notNull(participant, "participant must not be null");
        AuthzObject machine = DomainUtil.authzMachine(participant);
        Future<List<String>> hostedZones;
        // only a server hosting the organization's application zones asks the engine, so a server that hosts none
        // connects its organization participants without reaching it
        if (participant instanceof OrganizationParticipant organization
                && machine != null
                && zonePartitioningService.hostsZone(DomainUtil.APP_ZONE_PREFIX + "." + organization.getOrganizationId())) {
            String organizationId = organization.getOrganizationId();
            hostedZones = relationships.listObjects(machine, AuthzRelation.CAN_REGISTER_SERVICES, AuthzType.APPLICATION)
                                       .map(applications -> applications.stream()
                                                                        .map(application -> DomainUtil.authzApplicationId(application, organizationId))
                                                                        .filter(Objects::nonNull)
                                                                        .map(applicationId -> DomainUtil.applicationZone(organizationId, applicationId))
                                                                        .toList());
        } else {
            hostedZones = Future.succeededFuture(List.of());
        }
        return hostedZones.map(zones -> ZoneRules.from(participant, zones).restrictedTo(zonePartitioningService));
    }
}
