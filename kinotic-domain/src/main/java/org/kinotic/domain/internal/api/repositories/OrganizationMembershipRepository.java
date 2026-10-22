package org.kinotic.domain.internal.api.repositories;

import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.security.identity.OrganizationMembership;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationMembershipRepository extends AbstractRepository<OrganizationMembership> {

    // the most organizations one organization user is a member of
    private static final int MAX_MEMBERSHIPS = 100;
    // the most members one organization is searched across
    private static final int MAX_MEMBERS = 10_000;

    public OrganizationMembershipRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_organization_membership", OrganizationMembership.class, crudServiceTemplate);
    }

    /** The memberships of organization {@code organizationId}, oldest first. */
    public Future<Page<OrganizationMembership>> findByOrganization(String organizationId, Pageable pageable) {
        Validate.notBlank(organizationId, "organizationId cannot be blank");
        return findAll(pageable, b -> b.query(termFilter("organizationId", organizationId))
                                       .sort(s -> s.field(f -> f.field("created"))));
    }

    /** Every membership of organization {@code organizationId}, oldest first. */
    public Future<List<OrganizationMembership>> findAllByOrganization(String organizationId) {
        return findByOrganization(organizationId, Pageable.ofSize(MAX_MEMBERS)).map(Page::getContent);
    }

    /** Every membership of organization user {@code organizationUserId}, oldest first. */
    public Future<List<OrganizationMembership>> findByOrganizationUser(String organizationUserId) {
        Validate.notBlank(organizationUserId, "organizationUserId cannot be blank");
        return findAll(Pageable.ofSize(MAX_MEMBERSHIPS), b -> b.query(termFilter("organizationUserId", organizationUserId))
                                                                .sort(s -> s.field(f -> f.field("created"))))
                .map(Page::getContent);
    }
}
