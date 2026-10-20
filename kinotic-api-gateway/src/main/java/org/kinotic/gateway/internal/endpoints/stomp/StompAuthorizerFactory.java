package org.kinotic.gateway.internal.endpoints.stomp;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.event.ZonePartitioningService;
import org.kinotic.core.api.security.ConnectedInfo;
import org.kinotic.domain.api.model.security.ZoneRules;
import org.kinotic.gateway.api.config.ApiGatewayProperties;
import org.springframework.stereotype.Component;

/**
 * Creates the {@link StompAuthorizer} for a connected participant. Zone authorization is derived from the
 * participant type by {@link ZoneRules}, which documents the full participant routing matrix, with organization
 * participants also sending to {@link ApiGatewayProperties#getOrganizationServiceZones()}, narrowed to the
 * zones this server's {@link ZonePartitioningService} serves.
 */
@Component
@RequiredArgsConstructor
public class StompAuthorizerFactory {

    private final ZonePartitioningService zonePartitioningService;
    private final ApiGatewayProperties apiGatewayProperties;

    public StompAuthorizer create(ConnectedInfo connectedInfo) {
        Validate.notNull(connectedInfo, "connectedInfo must not be null");
        Validate.notNull(connectedInfo.getParticipant(), "participant must not be null");
        Validate.notEmpty(connectedInfo.getReplyToId(), "replyToId must not be empty");
        return new StompAuthorizer(ZoneRules.from(connectedInfo.getParticipant(), apiGatewayProperties.getOrganizationServiceZones())
                                            .restrictedTo(zonePartitioningService),
                                   connectedInfo.getReplyToId());
    }

}
