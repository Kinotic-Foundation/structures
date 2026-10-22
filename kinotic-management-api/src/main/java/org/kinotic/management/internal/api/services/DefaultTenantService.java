package org.kinotic.management.internal.api.services;

import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.exceptions.AlreadyExistsException;
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
    private final ApplicationRepository applications;
    private final ApplicationStoreProvisioner provisioner;

    public DefaultTenantService(TenantRepository repository,
                                ApplicationRepository applications,
                                ApplicationStoreProvisioner provisioner,
                                SecurityContext securityContext) {
        super(repository, securityContext);
        this.tenantRepository = repository;
        this.applications = applications;
        this.provisioner = provisioner;
    }

    @Override
    public Future<Tenant> createTenantIfNotExist(String applicationId, String name, String description) {
        Validate.notBlank(applicationId, "applicationId cannot be blank");
        Validate.notBlank(name, "name cannot be blank");
        String organizationId = requireOrganizationId();
        Tenant tenant = new Tenant().setOrganizationId(organizationId)
                                    .setApplicationId(applicationId)
                                    .setName(name)
                                    .setDescription(description);
        // create refuses an id another call took first, and the tenant it made is the one wanted when it is the
        // application's own; beforeSave's lookup has no such guarantee against a call made at the same time
        return create(tenant).recover(err -> err instanceof AlreadyExistsException
                ? tenantRepository.findById(tenant.getId(), organizationId)
                                  .compose(stored -> stored != null ? requireOfApplication(stored, applicationId) : Future.failedFuture(err))
                : Future.failedFuture(err));
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
        String organizationId = requireOrganizationId();
        return applications.requireById(entity.getApplicationId(), organizationId).mapEmpty();
    }

    // the graph first, then the record: a record that outlives its graph is deleted again, while grants that outlived
    // their record would come back with a tenant created under the same id
    @Override
    protected Future<Void> beforeDelete(String id) {
        return tenantRepository.findById(id, requireOrganizationId())
                               .compose(tenant -> tenant == null ? Future.succeededFuture() : provisioner.removeTenant(tenant));
    }

    private static Future<Tenant> requireOfApplication(Tenant stored, String applicationId) {
        return applicationId.equals(stored.getApplicationId())
                ? Future.succeededFuture(stored)
                : Future.failedFuture(new IllegalArgumentException("The tenant id '" + stored.getId()
                        + "' belongs to a tenant of application " + stored.getApplicationId()
                        + "; a tenant id is unique within the organization, so choose another name"));
    }
}
