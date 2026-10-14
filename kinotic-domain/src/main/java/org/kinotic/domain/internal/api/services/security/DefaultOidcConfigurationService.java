package org.kinotic.domain.internal.api.services.security;

import io.vertx.core.Future;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.model.security.ManagedOidcConfiguration;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.model.security.OidcProviderKind;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.OrgSignupOidcConfiguration;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.model.security.participant.SystemParticipant;
import org.kinotic.domain.api.services.SecretStorageService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.internal.api.repositories.OidcConfigurationRepository;
import org.kinotic.domain.internal.api.services.AbstractCrudService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class DefaultOidcConfigurationService extends AbstractCrudService<OidcConfiguration> implements OidcConfigurationService {

    private final OidcConfigurationRepository oidcRepository;
    private final SecretStorageService secretStorageService;
    private final SecurityContext securityContext;

    public DefaultOidcConfigurationService(OidcConfigurationRepository repository,
                                           SecretStorageService secretStorageService,
                                           SecurityContext securityContext) {
        super(repository);
        this.oidcRepository = repository;
        this.secretStorageService = secretStorageService;
        this.securityContext = securityContext;
    }

    @Override
    protected Future<Void> beforeSave(OidcConfiguration entity) {
        Validate.notNull(entity.getName(), "OidcConfiguration name cannot be null");
        Validate.notNull(entity.getProvider(), "OidcConfiguration provider cannot be null");
        requireOwner(entity);
        if (entity instanceof OrganizationOidcConfiguration owned) {
            // the organization names its account at the provider, never a URL: the kind's template
            // decides the issuer, so the server only ever connects to the provider's own hosts
            owned.setAuthority(owned.getProvider().authority(owned.getProviderAccount()));
        }
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
            entity.setCreated(new Date());
        }
        entity.setUpdated(new Date());
        return Future.succeededFuture();
    }

    @Override
    protected Future<Void> beforeDelete(String id) {
        return oidcRepository.findById(id).compose(existing -> {
            Future<Void> ret;
            if (existing == null) {
                ret = Future.succeededFuture();
            } else {
                requireOwner(existing);
                ret = existing instanceof OrganizationOidcConfiguration owned
                        ? secretStorageService.deleteSecret(owned.getOrganizationId(), id)
                        : Future.succeededFuture();
            }
            return ret;
        });
    }

    /**
     * Requires the bound participant to be the owner of {@code configuration}'s kind: a system participant
     * for a managed registration, since that row names a secret of the platform's and is offered to
     * every organization; a participant of the named organization for an organization's.
     *
     * @throws IllegalStateException  if no participant is bound to the current context
     * @throws AuthorizationException if the participant is not the owner
     */
    private void requireOwner(OidcConfiguration configuration) {
        switch (configuration) {
            case ManagedOidcConfiguration _ -> securityContext.requireParticipant(SystemParticipant.class);
            case OrganizationOidcConfiguration owned -> {
                Validate.notBlank(owned.getOrganizationId(), "Organization id must be set on OrganizationOidcConfiguration");
                String orgId = securityContext.requireParticipant(OrganizationParticipant.class).getOrganizationId();
                if (!orgId.equals(owned.getOrganizationId())) {
                    throw new AuthorizationException("Cannot write OrganizationOidcConfiguration with organizationId '"
                                                     + owned.getOrganizationId() + "' while authenticated as organization '"
                                                     + orgId + "'");
                }
            }
        }
    }

    @Override
    public Future<List<OrgSignupOidcConfiguration>> findEnabledOrgSignupProviders() {
        return oidcRepository.findEnabledOrgSignup();
    }

    @Override
    public Future<OrgSignupOidcConfiguration> findEnabledOrgSignupByProvider(OidcProviderKind provider) {
        Validate.notNull(provider, "provider cannot be null");
        return oidcRepository.findEnabledOrgSignupByProvider(provider);
    }

    @Override
    public Future<OrgSignupOidcConfiguration> findOrgSignupById(String id) {
        Validate.notBlank(id, "id cannot be blank");
        return oidcRepository.findOrgSignupById(id);
    }

    @Override
    public Future<List<OidcConfiguration>> findEnabledByIds(List<String> ids, String orgId) {
        Validate.notEmpty(ids, "ids cannot be null or empty");
        Validate.notBlank(orgId, "orgId cannot be blank");
        return oidcRepository.findEnabledByIds(ids, orgId);
    }

    @Override
    public Future<OidcConfiguration> findOfferable(String id, String organizationId) {
        Validate.notBlank(id, "id cannot be blank");
        Validate.notBlank(organizationId, "organizationId cannot be blank");
        return oidcRepository.findOfferable(id, organizationId);
    }

    @Override
    public Future<OrganizationOidcConfiguration> findById(String id, String organizationId) {
        Validate.notBlank(id, "id cannot be blank");
        Validate.notBlank(organizationId, "organizationId cannot be blank");
        return oidcRepository.findById(id, organizationId);
    }

    @Override
    public Future<OrganizationOidcConfiguration> save(OrganizationOidcConfiguration configuration, String clientSecret) {
        boolean confidential = StringUtils.isNotBlank(clientSecret);
        // saved first: a new configuration is given the id its secret is keyed by in beforeSave
        return save(configuration.setConfidentialClient(confidential)).compose(saved -> {
            OrganizationOidcConfiguration owned = (OrganizationOidcConfiguration) saved;
            Future<Void> stored = confidential
                    ? secretStorageService.setSecret(owned.getOrganizationId(), owned.getId(), clientSecret)
                    : secretStorageService.deleteSecret(owned.getOrganizationId(), owned.getId());
            return stored.map(owned);
        });
    }

    @Override
    public Future<String> findClientSecret(OrganizationOidcConfiguration configuration) {
        Validate.notBlank(configuration.getId(), "The configuration has no id");
        Validate.notBlank(configuration.getOrganizationId(), "The configuration names no organization");
        return configuration.isConfidentialClient()
                ? secretStorageService.getSecret(configuration.getOrganizationId(), configuration.getId())
                : Future.succeededFuture();
    }
}
