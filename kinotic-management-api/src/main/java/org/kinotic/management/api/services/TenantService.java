package org.kinotic.management.api.services;

import io.vertx.core.Future;
import org.kinotic.core.api.annotations.Publish;
import org.kinotic.domain.api.model.Tenant;
import org.kinotic.domain.api.services.ApplicationScopedCrudService;
import org.kinotic.idl.api.annotations.AuthzCheck;
import org.kinotic.idl.api.annotations.AuthzResource;
import org.kinotic.idl.api.annotations.McpTool;
import org.kinotic.idl.api.utils.AuthzUtil;

/**
 * Manages the {@link Tenant}s of the caller's organization's applications. A tenant's id is derived from its
 * slugified name at creation: lowercase letters, digits, and interior dashes, at most
 * {@link org.kinotic.domain.api.utils.HostLabelUtil#MAX_TENANT_ID_LENGTH} characters so it leads the tenant's
 * site labels, and unique within the organization.
 */
@Publish
@McpTool
@AuthzResource(value = AuthzUtil.APPLICATION_TYPE, parent = AuthzUtil.ORGANIZATION_TYPE)
public interface TenantService extends ApplicationScopedCrudService<Tenant, String> {

    @AuthzCheck(permission = AuthzUtil.CAN_EDIT, resourceId = "{entity.applicationId}")
    @Override
    Future<Tenant> save(Tenant entity);

    @AuthzCheck(permission = AuthzUtil.CAN_EDIT, resourceId = "{entity.applicationId}")
    @Override
    Future<Tenant> saveSync(Tenant entity);

    @AuthzCheck(resource = AuthzUtil.ORGANIZATION_TYPE, resourceId = "{@organizationId}")
    @Override
    Future<Tenant> findById(String id);

    @AuthzCheck(permission = AuthzUtil.CAN_EDIT, resource = AuthzUtil.ORGANIZATION_TYPE, resourceId = "{@organizationId}")
    @Override
    Future<Void> deleteById(String id);

    @AuthzCheck(permission = AuthzUtil.CAN_EDIT, resource = AuthzUtil.ORGANIZATION_TYPE, resourceId = "{@organizationId}")
    @Override
    Future<Void> deleteByIdSync(String id);

    /**
     * Creates a tenant of the application if it does not already exist, deriving its id from the slugified
     * name. The organization id is derived from the authenticated participant.
     *
     * @param applicationId the application the tenant belongs to, one of the caller's organization's
     * @param name          the name of the tenant to create
     * @param description   the description of the tenant to create
     * @return {@link Future} emitting the created tenant, or the application's existing tenant whose id
     *         matches the slugified name; fails when that id is a tenant of another of the organization's
     *         applications, since a tenant id is unique within the organization
     */
    @AuthzCheck(permission = AuthzUtil.CAN_EDIT, resourceId = "{applicationId}")
    Future<Tenant> createTenantIfNotExist(String applicationId, String name, String description);

}
