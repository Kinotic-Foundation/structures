package org.kinotic.management.internal.api.services;

import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.authz.api.services.AuthzStoreService;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.core.api.exceptions.AlreadyExistsException;
import org.kinotic.core.api.security.SecurityContext;
import org.kinotic.domain.api.model.ApplicationKey;
import org.kinotic.domain.api.utils.HostLabelUtil;
import org.kinotic.domain.api.model.Application;
import org.kinotic.domain.api.model.TenantManagement;
import org.kinotic.domain.api.model.security.LoginProviderSummary;
import org.kinotic.domain.api.repositories.ApplicationRepository;
import org.kinotic.domain.api.repositories.AuthzStoreRepository;
import org.kinotic.domain.api.repositories.EntityDefinitionRepository;
import org.kinotic.domain.internal.api.services.AbstractOrganizationScopedService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.idl.api.utils.AuthzConstants;
import org.kinotic.management.api.repositories.ProjectRepository;
import org.kinotic.management.api.repositories.UiDeploymentRepository;
import org.kinotic.management.api.services.ApplicationService;
import org.kinotic.management.api.services.EntityDefinitionService;
import org.kinotic.management.api.services.ProjectService;
import org.kinotic.management.internal.api.services.security.DefaultPermissionService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DefaultApplicationService extends AbstractOrganizationScopedService<Application> implements ApplicationService {

    // every site label, <app>--<ui>, must leave room for at least this long a UI name
    private static final int MIN_UI_NAME_LENGTH = 1;

    private final ProjectRepository projectRepository;
    private final EntityDefinitionRepository entityDefinitionRepository;
    private final DefaultPermissionService permissions;
    private final OidcConfigurationService oidcConfigurationService;
    private final UiDeploymentRepository uiDeploymentRepository;
    private final RelationshipService relationships;
    private final ApplicationStoreProvisioner storeProvisioner;
    private final AuthzStoreService storeService;
    private final AuthzStoreRepository stores;

    public DefaultApplicationService(ApplicationRepository repository,
                                     ProjectRepository projectRepository,
                                     EntityDefinitionRepository entityDefinitionRepository,
                                     DefaultPermissionService permissions,
                                     OidcConfigurationService oidcConfigurationService,
                                     UiDeploymentRepository uiDeploymentRepository,
                                     SecurityContext securityContext,
                                     RelationshipService relationships,
                                     ApplicationStoreProvisioner storeProvisioner,
                                     AuthzStoreService storeService,
                                     AuthzStoreRepository stores) {
        super(repository, securityContext);
        this.projectRepository = projectRepository;
        this.entityDefinitionRepository = entityDefinitionRepository;
        this.permissions = permissions;
        this.oidcConfigurationService = oidcConfigurationService;
        this.uiDeploymentRepository = uiDeploymentRepository;
        this.relationships = relationships;
        this.storeProvisioner = storeProvisioner;
        this.storeService = storeService;
        this.stores = stores;
    }

    // Read before the engine is asked, so a missing id never leaves a denial in the engine's caches for the
    // record's creation to outlive; the functions the gateway checked on the application read it with
    // super.findById
    @Override
    public Future<Application> findById(String id) {
        return super.findById(id).compose(application -> application == null
                ? Future.succeededFuture(null)
                : visibleIds().map(ids -> ids == null || ids.contains(id) ? application : null));
    }

    @Override
    public Future<Long> count() {
        return visibleIds().compose(ids -> scopedRepository.count(requireOrganizationId(), ids));
    }

    @Override
    public Future<Page<Application>> findAll(Pageable pageable) {
        return visibleIds().compose(ids -> scopedRepository.findAll(requireOrganizationId(), ids, pageable));
    }

    @Override
    public Future<Page<Application>> search(String searchText, Pageable pageable) {
        return visibleIds().compose(ids -> scopedRepository.search(searchText, requireOrganizationId(), ids, pageable));
    }

    // What the caller may see, null for every application of the organization: the applications it reads, and
    // those containing a project or an entity definition it reads, so a grant anywhere inside an application makes
    // the application reachable
    private Future<Set<String>> visibleIds() {
        String organizationId = requireOrganizationId();
        return permissions.findAccessibleIds(AuthzConstants.APPLICATION_TYPE, AuthzConstants.CAN_READ).compose(applications -> applications == null
                ? Future.succeededFuture(null)
                : Future.all(permissions.listAccessible(ProjectService.RESOURCE_TYPE, AuthzConstants.CAN_READ)
                                        .compose(ids -> projectRepository.findApplicationIdsOf(ids, organizationId)),
                             permissions.listAccessible(EntityDefinitionService.RESOURCE_TYPE, AuthzConstants.CAN_READ)
                                        .compose(ids -> entityDefinitionRepository.findApplicationIdsOf(ids, organizationId)))
                        .map(containing -> {
                            Set<String> ret = new HashSet<>(applications);
                            for (int i = 0; i < containing.size(); i++) {
                                ret.addAll(containing.<Collection<String>>resultAt(i));
                            }
                            return ret;
                        }));
    }

    @Override
    public Future<Application> createApplicationIfNotExist(String name, String description, TenantManagement tenantManagement) {
        String applicationId = DomainUtil.slugifyId(name);
        String organizationId = requireOrganizationId();
        return super.findById(applicationId)
                .compose(application -> {
                    Future<Application> ret;
                    if(application != null){
                        // an existing application keeps its tenant policy: flipping it here would
                        // split its users into tenanted and untenanted halves, since only users
                        // created while it is enabled receive a tenant
                        ret = Future.succeededFuture(application);
                    }else{
                        Application newApplication = new Application(name, description);
                        newApplication.setOrganizationId(organizationId);
                        // a caller with no tenancy opinion, such as the CLI ensuring the app row exists,
                        // omits the argument and leaves tenants to their administrators
                        newApplication.setTenantManagement(tenantManagement != null ? tenantManagement : TenantManagement.TENANT_ADMINISTRATORS);
                        ret = save(newApplication).compose(storeProvisioner::provision);
                    }
                    return ret;
                });
    }

    @Override
    public Future<Application> create(Application entity) {
        // Force the id to derive from the name; beforeSave mints it from the slug.
        entity.setId(null);
        return failOnDuplicateName(super.create(entity), entity).compose(storeProvisioner::provision);
    }

    @Override
    public Future<Application> createSync(Application entity) {
        entity.setId(null);
        return failOnDuplicateName(super.createSync(entity), entity).compose(storeProvisioner::provision);
    }

    @Override
    public Future<Void> deleteById(String id) {
        return super.deleteById(id).compose(v -> released(id));
    }

    @Override
    public Future<Void> deleteByIdSync(String id) {
        return super.deleteByIdSync(id).compose(v -> released(id));
    }

    // The application leaves the graph with every grant made on it, and its store goes with everything in it, so an
    // application created under the same id afterwards starts with no grant and an empty store
    private Future<Void> released(String applicationId) {
        String organizationId = requireOrganizationId();
        String store = DomainUtil.authzApplicationId(organizationId, applicationId);
        return relationships.removeObject(AuthzStoreService.SYSTEM, DomainUtil.authzApplication(organizationId, applicationId))
                            .compose(v -> storeService.deleteStore(store))
                            .compose(v -> stores.deleteByIdSync(store));
    }

    // The caller supplied a name, not the derived id an AlreadyExistsException would reference
    private static Future<Application> failOnDuplicateName(Future<Application> created,
                                                           Application entity) {
        return created.recover(ex -> AlreadyExistsException.isCause(ex)
                ? Future.failedFuture(new AlreadyExistsException(
                        "An application named '" + entity.getName() + "' already exists"))
                : Future.failedFuture(ex));
    }

    @Override
    protected Future<Void> beforeDelete(String id) {
        return projectRepository.countForApplication(id, requireOrganizationId()).compose(count -> {
            if(count > 0){
                throw new IllegalStateException("Cannot delete an application with projects in it.");
            }
            return Future.succeededFuture();
        });
    }

    @Override
    protected Future<Void> beforeSave(Application entity) {
        Validate.notNull(entity.getName(), "Application name cannot be null");

        if (entity.getId() == null) {
            entity.setId(DomainUtil.slugifyId(entity.getName()));
        }
        // Validate only; re-minting an update's id would silently write a new document
        DomainUtil.validateApplicationId(entity.getId());
        ApplicationKey applicationKey = new ApplicationKey(requireOrganizationId(), entity.getId());
        // the id never changes after creation, so an application whose API host label exceeds DNS's limit could
        // never be reached, and one too long for a site label could never publish a UI
        Validate.isTrue(HostLabelUtil.label(applicationKey).length() <= HostLabelUtil.MAX_HOST_LABEL_LENGTH,
                        "The application's API host label '%s' exceeds %d characters; shorten the application name",
                        HostLabelUtil.label(applicationKey), HostLabelUtil.MAX_HOST_LABEL_LENGTH);
        Validate.isTrue(entity.getId().length() + HostLabelUtil.HOST_LABEL_SEPARATOR.length() + MIN_UI_NAME_LENGTH <= HostLabelUtil.MAX_SITE_LABEL_LENGTH,
                        "The application id '%s' leaves no room for a UI name in its sites' labels, which are held to"
                                + " %d characters so a tenant's name fits in front; shorten the application name",
                        entity.getId(), HostLabelUtil.MAX_SITE_LABEL_LENGTH);
        if (entity.getSocialProviders() == null) {
            entity.setSocialProviders(new ArrayList<>());
        }
        Validate.noNullElements(entity.getSocialProviders(), "socialProviders cannot hold null");
        Validate.isTrue(entity.getSocialProviders().stream().distinct().count() == entity.getSocialProviders().size(),
                        "socialProviders names a provider twice");
        entity.setUpdated(new Date());
        Future<String> primaryUiUrl;
        if (entity.getPrimaryUiId() == null) {
            primaryUiUrl = Future.succeededFuture();
        } else {
            // resolved only when it changes, so removing the primary UI's deployment never fails the application's other edits
            primaryUiUrl = super.findById(entity.getId())
                    .compose(stored -> stored != null && entity.getPrimaryUiId().equals(stored.getPrimaryUiId())
                            ? Future.succeededFuture(stored.getPrimaryUiUrl())
                            : publishedUiUrl(applicationKey, entity.getPrimaryUiId()));
        }
        return primaryUiUrl.compose(url -> {
            entity.setPrimaryUiUrl(url);
            return Future.succeededFuture();
        });
    }

    private Future<String> publishedUiUrl(ApplicationKey applicationKey, String uiName) {
        // a site's id names its organization, application and UI, so a site with this id is one of this application's UIs
        return uiDeploymentRepository.findById(HostLabelUtil.siteId(applicationKey, uiName))
                .map(site -> {
                    Validate.isTrue(site != null, "The application '%s' has no published UI named '%s'",
                                    applicationKey.applicationId(), uiName);
                    return site.getUrl();
                });
    }

    @Override
    public Future<List<LoginProviderSummary>> getLoginProviders(String applicationId) {
        Validate.notNull(applicationId, "applicationId cannot be null");
        return super.findById(applicationId)
                .compose(application -> {
                    Validate.isTrue(application != null, "Application not found: %s", applicationId);
                    return oidcConfigurationService.findEnabledForScope(application.getOrganizationId(), applicationId)
                                               .map(offered -> offered.stream().map(LoginProviderSummary::of).toList());
                });
    }

}
