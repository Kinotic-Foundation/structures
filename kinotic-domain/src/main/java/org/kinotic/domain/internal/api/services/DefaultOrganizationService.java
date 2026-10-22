package org.kinotic.domain.internal.api.services;

import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.authz.api.services.AuthzStoreService;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.core.api.exceptions.AlreadyExistsException;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.internal.api.repositories.OrganizationRepository;
import org.kinotic.domain.internal.api.repositories.OrganizationMembershipRepository;
import org.kinotic.domain.internal.api.repositories.ParticipantIdentityRepository;
import org.kinotic.domain.api.model.security.identity.OrganizationMembership;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.idl.api.utils.AuthzConstants;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DefaultOrganizationService extends AbstractCrudService<Organization> implements OrganizationService {

    private final ParticipantIdentityRepository identityRepository;
    private final OrganizationMembershipRepository membershipRepository;
    private final EmailService emailService;
    private final RelationshipService relationships;

    public DefaultOrganizationService(OrganizationRepository repository,
                                      ParticipantIdentityRepository identityRepository,
                                      OrganizationMembershipRepository membershipRepository,
                                      EmailService emailService,
                                      RelationshipService relationships) {
        super(repository);
        this.identityRepository = identityRepository;
        this.membershipRepository = membershipRepository;
        this.emailService = emailService;
        this.relationships = relationships;
    }

    @Override
    protected Future<Void> beforeSave(Organization entity) {
        Validate.notNull(entity.getName(), "Organization name cannot be null");

        if (entity.getId() == null) {
            entity.setId(DomainUtil.slugifyId(entity.getName()));
            entity.setCreated(new Date());
        }
        // Validate only; re-minting an update's id would silently write a new document
        DomainUtil.validateOrganizationId(entity.getId());

        entity.setUpdated(new Date());
        return Future.succeededFuture();
    }

    /**
     * Creates a new organization, deriving its id from the slugified name. Fails with
     * {@link AlreadyExistsException} if an organization with that name (hence id) already
     * exists, rather than overwriting it.
     */
    @Override
    public Future<Organization> create(Organization entity) {
        Validate.notBlank(entity.getName(), "Organization name cannot be null");
        // Force the id to derive from the name; beforeSave mints it from the slug.
        entity.setId(null);
        return super.create(entity)
                    .recover(ex -> ex instanceof AlreadyExistsException
                            ? Future.failedFuture(new AlreadyExistsException(
                                    "An organization named '" + entity.getName() + "' already exists"))
                            : Future.failedFuture(ex))
                    .compose(this::contained);
    }

    @Override
    public Future<Organization> createSync(Organization entity) {
        Validate.notBlank(entity.getName(), "Organization name cannot be null");
        entity.setId(null);
        return super.createSync(entity).compose(this::contained);
    }

    // The graph before the record: grants that outlived their record would come back with an organization created
    // under the same id, while a record that outlives its graph is deleted again
    @Override
    protected Future<Void> beforeDelete(String id) {
        return relationships.removeObject(AuthzStoreService.SYSTEM, AuthzUtil.object(AuthzConstants.ORGANIZATION_TYPE, id));
    }

    // The organization's place in the graph, written once the record is, so a write that fails leaves an
    // organization nobody can reach rather than one nobody stores
    private Future<Organization> contained(Organization organization) {
        return relationships.ensure(AuthzStoreService.SYSTEM,
                                    List.of(DomainUtil.containment(AuthzUtil.object(AuthzConstants.SYSTEM_TYPE, AuthzConstants.SYSTEM_OBJECT_ID),
                                                                   AuthzUtil.object(AuthzConstants.ORGANIZATION_TYPE, organization.getId()))))
                            .map(organization);
    }

    @Override
    public Future<Void> sendMembershipsEmail(String email) {
        Validate.notBlank(email, "email cannot be blank");
        return identityRepository.findOrganizationUserByEmail(email)
                                 // a disabled organization user signs in nowhere, so it is told of no organizations, as the landing page tells it
                                 .compose(user -> user == null || !user.isEnabled()
                                         ? Future.succeededFuture(List.<OrganizationMembership>of())
                                         : membershipRepository.findByOrganizationUser(user.getId()))
                                 .compose(memberships -> findAllById(memberships.stream()
                                                                                .filter(OrganizationMembership::isEnabled)
                                                                                .map(OrganizationMembership::getOrganizationId)
                                                                                .toList()))
                                 .compose(organizations -> emailService.sendOrganizationsEmail(email, organizations));
    }
}
