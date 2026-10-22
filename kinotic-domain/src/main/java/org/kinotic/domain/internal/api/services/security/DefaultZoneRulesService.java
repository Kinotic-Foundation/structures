package org.kinotic.domain.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.authz.api.model.Consistency;
import org.kinotic.authz.api.services.AuthzStoreService;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.core.api.event.ZonePartitioningService;
import org.kinotic.core.api.security.Participant;
import org.kinotic.core.api.security.ParticipantConstants;
import org.kinotic.domain.api.model.security.ZoneRules;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.services.security.ZoneRulesService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultZoneRulesService implements ZoneRulesService {

    private static final String CAN_REGISTER_SERVICES = AuthzUtil.permissionName(AuthzUtil.APPLICATION_TYPE, AuthzUtil.CAN_REGISTER_SERVICES);

    private final ZonePartitioningService zonePartitioningService;
    private final AuthzStoreService stores;
    private final RelationshipService relationships;

    @Override
    public Future<ZoneRules> rulesFor(Participant participant) {
        Validate.notNull(participant, "participant must not be null");
        boolean machine = ParticipantConstants.PARTICIPANT_TYPE_MACHINE.equals(participant.getMetadata()
                                                                                       .get(ParticipantConstants.PARTICIPANT_TYPE_METADATA_KEY));
        Future<List<String>> hostedZones;
        // only a server hosting the organization's application zones asks the engine, so a server that hosts none
        // connects its organization participants without reaching it
        if (participant instanceof OrganizationParticipant organization
                && machine
                && zonePartitioningService.hostsZone(DomainUtil.APP_ZONE_PREFIX + "." + organization.getOrganizationId())) {
            String organizationId = organization.getOrganizationId();
            String prefix = organizationId + ".";
            hostedZones = stores.modelId(AuthzStoreService.SYSTEM)
                                .compose(modelId -> relationships.listObjects(AuthzStoreService.SYSTEM, modelId,
                                                                              DomainUtil.authzUser(participant),
                                                                              CAN_REGISTER_SERVICES,
                                                                              AuthzUtil.APPLICATION_TYPE,
                                                                              Consistency.MINIMIZE_LATENCY))
                                .map(applications -> applications.stream()
                                                                 .map(AuthzUtil::idOf)
                                                                 // a machine is granted only within its organization
                                                                 .filter(id -> id.startsWith(prefix))
                                                                 .map(id -> DomainUtil.applicationZone(organizationId, id.substring(prefix.length())))
                                                                 .toList());
        } else {
            hostedZones = Future.succeededFuture(List.of());
        }
        return hostedZones.map(zones -> ZoneRules.from(participant, zones).restrictedTo(zonePartitioningService));
    }
}
