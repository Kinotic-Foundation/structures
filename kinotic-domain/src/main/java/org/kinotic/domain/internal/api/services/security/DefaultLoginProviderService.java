package org.kinotic.domain.internal.api.services.security;

import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.model.security.LoginProvider;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.repositories.ApplicationRepository;
import org.kinotic.domain.api.services.security.LoginProviderService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.internal.api.repositories.LoginProviderRepository;
import org.kinotic.domain.internal.api.services.AbstractOrganizationScopedService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DefaultLoginProviderService extends AbstractOrganizationScopedService<LoginProvider> implements LoginProviderService {

    private final LoginProviderRepository loginProviderRepository;
    private final OidcConfigurationService oidcConfigurationService;
    private final ApplicationRepository applicationRepository;

    public DefaultLoginProviderService(LoginProviderRepository repository,
                                       OidcConfigurationService oidcConfigurationService,
                                       ApplicationRepository applicationRepository,
                                       SecurityContext securityContext) {
        super(repository, securityContext);
        this.loginProviderRepository = repository;
        this.oidcConfigurationService = oidcConfigurationService;
        this.applicationRepository = applicationRepository;
    }

    @Override
    protected Future<Void> beforeSave(LoginProvider entity) {
        Validate.notBlank(entity.getOrganizationId(), "LoginProvider organizationId cannot be blank");
        Validate.notBlank(entity.getConfigurationId(), "LoginProvider configurationId cannot be blank");
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
            entity.setCreated(new Date());
        }
        entity.setUpdated(new Date());
        String orgId = entity.getOrganizationId();
        return oidcConfigurationService.findOfferable(entity.getConfigurationId(), orgId)
                .compose(configuration -> {
                    Validate.isTrue(configuration != null, "Organization %s has no OIDC configuration %s to offer", orgId, entity.getConfigurationId());
                    // Kinotic's social registrations sign in applications' users; the organization's own login
                    // offers the organization's own configurations only
                    Validate.isTrue(entity.getApplicationId() != null || configuration instanceof OrganizationOidcConfiguration,
                                    "Configuration %s is offered at an application's login only", entity.getConfigurationId());
                    return entity.getApplicationId() == null
                            ? Future.succeededFuture()
                            : applicationRepository.findById(entity.getApplicationId(), orgId)
                                                   .map(application -> {
                                                       Validate.isTrue(application != null, "Organization %s has no application %s", orgId, entity.getApplicationId());
                                                       return null;
                                                   });
                })
                .compose(v -> loginProviderRepository.findByScopeAndConfiguration(orgId, entity.getApplicationId(), entity.getConfigurationId()))
                .map(existing -> {
                    // one row per scope and configuration; a save of the row itself passes its own
                    Validate.isTrue(existing == null || existing.getId().equals(entity.getId()),
                                    "The scope already offers configuration %s", entity.getConfigurationId());
                    return null;
                });
    }

    @Override
    public Future<List<OidcConfiguration>> findEnabledForScope(String organizationId, String applicationId) {
        Validate.notBlank(organizationId, "organizationId cannot be blank");
        Future<List<OidcConfiguration>> own = loginProviderRepository.findByScope(organizationId, applicationId)
                                                                     .compose(rows -> enabledInOrder(rows, organizationId));
        Future<List<OidcConfiguration>> ret;
        if (applicationId != null) {
            ret = own;
        } else {
            ret = Future.all(oidcConfigurationService.findEnabledOrgSignupProviders(), own)
                        .map(cf -> {
                            List<OidcConfiguration> providers = new ArrayList<>(cf.<List<OidcConfiguration>>resultAt(0));
                            providers.addAll(cf.resultAt(1));
                            return providers;
                        });
        }
        return ret;
    }

    @Override
    public Future<OidcConfiguration> findOffered(String configurationId, String organizationId, String applicationId) {
        Validate.notBlank(configurationId, "configurationId cannot be blank");
        Validate.notBlank(organizationId, "organizationId cannot be blank");
        return loginProviderRepository.findByScopeAndConfiguration(organizationId, applicationId, configurationId)
                .compose(row -> row == null
                        ? Future.succeededFuture()
                        : oidcConfigurationService.findOfferable(configurationId, organizationId))
                .map(configuration -> configuration != null && configuration.isEnabled() ? configuration : null);
    }

    // The enabled configurations the rows name, in the rows' order
    private Future<List<OidcConfiguration>> enabledInOrder(List<LoginProvider> rows, String organizationId) {
        Future<List<OidcConfiguration>> ret;
        if (rows.isEmpty()) {
            ret = Future.succeededFuture(List.of());
        } else {
            List<String> ids = rows.stream().map(LoginProvider::getConfigurationId).toList();
            ret = oidcConfigurationService.findEnabledByIds(ids, organizationId)
                                          .map(configurations -> {
                                              Map<String, OidcConfiguration> byId = configurations.stream()
                                                      .collect(Collectors.toMap(OidcConfiguration::getId, Function.identity()));
                                              return ids.stream()
                                                        .map(byId::get)
                                                        .filter(Objects::nonNull)
                                                        .toList();
                                          });
        }
        return ret;
    }
}
