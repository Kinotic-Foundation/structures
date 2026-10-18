package org.kinotic.management.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.model.security.OwnedOidcConfiguration;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.management.api.services.security.OwnedOidcConfigurationService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultOwnedOidcConfigurationService implements OwnedOidcConfigurationService {

    private final OidcConfigurationService oidcConfigurationService;
    private final SecurityContext securityContext;

    @Override
    public Future<Page<OwnedOidcConfiguration>> findAll(Pageable pageable) {
        return oidcConfigurationService.findOwnedByOrganization(requireOrganizationId(), pageable);
    }

    @Override
    public Future<OwnedOidcConfiguration> findById(String id) {
        Validate.notBlank(id, "id is required");
        return oidcConfigurationService.findOwnedById(id, requireOrganizationId());
    }

    @Override
    public Future<OwnedOidcConfiguration> save(OwnedOidcConfiguration configuration, String clientSecret) {
        Validate.notNull(configuration, "configuration is required");
        configuration.setOrganizationId(requireOrganizationId());
        return oidcConfigurationService.save(configuration, clientSecret);
    }

    @Override
    public Future<OwnedOidcConfiguration> copy(String sourceId, String applicationId) {
        return oidcConfigurationService.copy(sourceId, applicationId);
    }

    @Override
    public Future<Void> deleteById(String id) {
        // looked up as the organization's first, so an id of another owner's row is a no-op rather than a refusal
        return findById(id).compose(found -> found == null
                ? Future.succeededFuture()
                : oidcConfigurationService.deleteById(id));
    }

    private String requireOrganizationId() {
        return securityContext.requireParticipant(OrganizationParticipant.class).getOrganizationId();
    }
}
