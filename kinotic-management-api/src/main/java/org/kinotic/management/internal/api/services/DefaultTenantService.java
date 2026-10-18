package org.kinotic.management.internal.api.services;

import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.model.Tenant;
import org.kinotic.domain.api.repositories.ApplicationRepository;
import org.kinotic.domain.api.repositories.TenantRepository;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.domain.internal.api.services.AbstractApplicationScopedService;
import org.kinotic.management.api.services.TenantService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DefaultTenantService extends AbstractApplicationScopedService<Tenant> implements TenantService {

    private final TenantRepository tenantRepository;
    private final ApplicationRepository applicationRepository;

    public DefaultTenantService(TenantRepository repository,
                                ApplicationRepository applicationRepository,
                                SecurityContext securityContext) {
        super(repository, securityContext);
        this.tenantRepository = repository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Future<Tenant> createTenantIfNotExist(String applicationId, String name, String description) {
        Validate.notBlank(applicationId, "applicationId cannot be blank");
        Validate.notBlank(name, "name cannot be blank");
        String organizationId = requireOrganizationId();
        String tenantId = DomainUtil.slugifyId(name);
        return tenantRepository.findById(tenantId, organizationId)
                               .compose(tenant -> {
                                   Future<Tenant> ret;
                                   if (tenant == null) {
                                       Tenant newTenant = new Tenant().setOrganizationId(organizationId)
                                                                      .setApplicationId(applicationId)
                                                                      .setName(name)
                                                                      .setDescription(description);
                                       // create, so a tenant made under the id meanwhile is refused rather than written over
                                       ret = create(newTenant);
                                   } else if (applicationId.equals(tenant.getApplicationId())) {
                                       ret = Future.succeededFuture(tenant);
                                   } else {
                                       // stored under the organization, so an id names one tenant of one of its applications
                                       ret = Future.failedFuture(new IllegalArgumentException("The tenant id '" + tenantId
                                               + "' belongs to a tenant of application " + tenant.getApplicationId()
                                               + "; a tenant id is unique within the organization, so choose another name"));
                                   }
                                   return ret;
                               });
    }

    @Override
    protected Future<Void> beforeSave(Tenant entity) {
        Validate.notBlank(entity.getName(), "Tenant name cannot be blank");
        Validate.notBlank(entity.getApplicationId(), "Tenant applicationId cannot be blank");
        if (entity.getId() == null) {
            entity.setId(DomainUtil.slugifyId(entity.getName()));
            entity.setCreated(new Date());
        }
        // Validate only; re-minting an update's id would silently write a new document
        DomainUtil.validateTenantId(entity.getId());
        entity.setUpdated(new Date());
        return applicationRepository.requireById(entity.getApplicationId(), requireOrganizationId()).mapEmpty();
    }
}
