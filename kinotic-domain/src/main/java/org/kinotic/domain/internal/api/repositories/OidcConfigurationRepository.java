package org.kinotic.domain.internal.api.repositories;

import co.elastic.clients.elasticsearch._types.query_dsl.IdsQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.security.ApplicationSocialOidcConfiguration;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.model.security.OidcConfigurationType;
import org.kinotic.domain.api.model.security.OidcProviderKind;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.OrgSignupOidcConfiguration;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Every {@link OidcConfiguration} in one index, the platform's managed registrations and the organizations' own alike, told
 * apart by the persisted {@code type} discriminator. The owner-specific finders filter on it, so a
 * lookup for one kind never answers with the other.
 */
@Component
public class OidcConfigurationRepository extends AbstractRepository<OidcConfiguration> {

    private static final String TYPE_FIELD = "type";
    private static final String ORGANIZATION_ID_FIELD = "organizationId";
    private static final String ENABLED_FIELD = "enabled";

    public OidcConfigurationRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_oidc_configuration", OidcConfiguration.class, crudServiceTemplate);
    }

    /**
     * Every enabled registration offered to organization sign-up.
     */
    public Future<List<OrgSignupOidcConfiguration>> findEnabledOrgSignup() {
        return findAll(Pageable.ofSize(100), b -> b.query(composeFilter(
                termFilter(TYPE_FIELD, OidcConfigurationType.ORG_SIGNUP.name()),
                termFilter(ENABLED_FIELD, true))))
                .map(page -> page.map(OrgSignupOidcConfiguration.class::cast))
                .map(Page::getContent);
    }

    /**
     * The enabled org sign-up registration of the given provider kind, or {@code null} when there is none.
     */
    public Future<OrgSignupOidcConfiguration> findEnabledOrgSignupByProvider(OidcProviderKind provider) {
        return findFirst(b -> b.query(composeFilter(
                termFilter(TYPE_FIELD, OidcConfigurationType.ORG_SIGNUP.name()),
                termFilter("provider", provider.key()),
                termFilter(ENABLED_FIELD, true))))
                .map(OrgSignupOidcConfiguration.class::cast);
    }

    /**
     * The org sign-up registration {@code id}, enabled or not, or {@code null} when no
     * {@link OrgSignupOidcConfiguration} has that id.
     */
    public Future<OrgSignupOidcConfiguration> findOrgSignupById(String id) {
        return findById(id).map(config -> config instanceof OrgSignupOidcConfiguration social ? social : null);
    }

    /**
     * The configuration {@code id} of organization {@code orgId}, enabled or not, or {@code null} when
     * that organization has none by that id.
     */
    public Future<OrganizationOidcConfiguration> findById(String id, String orgId) {
        Validate.notBlank(orgId, "orgId cannot be blank");
        return findById(id).map(config -> config instanceof OrganizationOidcConfiguration owned
                                          && Objects.equals(orgId, owned.getOrganizationId()) ? owned : null);
    }

    /**
     * The configuration {@code id} that organization {@code orgId} may offer at a sign-in, enabled or not:
     * its own {@link OrganizationOidcConfiguration}, or an {@link ApplicationSocialOidcConfiguration}, which
     * every organization may offer. {@code null} when no such configuration has that id.
     */
    public Future<OidcConfiguration> findOfferable(String id, String orgId) {
        Validate.notBlank(orgId, "orgId cannot be blank");
        return findById(id).map(config -> switch (config) {
            case OrganizationOidcConfiguration owned when Objects.equals(orgId, owned.getOrganizationId()) -> owned;
            case ApplicationSocialOidcConfiguration social -> social;
            case null, default -> null;
        });
    }

    /**
     * Returns the enabled configurations among {@code ids} that organization {@code orgId} may offer at a
     * sign-in: its own and the {@link ApplicationSocialOidcConfiguration}s.
     */
    public Future<List<OidcConfiguration>> findEnabledByIds(List<String> ids, String orgId) {
        Validate.notBlank(orgId, "orgId cannot be blank");
        Validate.notEmpty(ids, "ids cannot be null or empty");
        Query ownedByOrg = composeFilter(termFilter(TYPE_FIELD, OidcConfigurationType.ORGANIZATION.name()),
                                         termFilter(ORGANIZATION_ID_FIELD, orgId));
        Query offerable = Query.of(q -> q.bool(b -> b
                .should(ownedByOrg, termFilter(TYPE_FIELD, OidcConfigurationType.APP_SOCIAL.name()))
                .minimumShouldMatch("1")));
        return findAll(Pageable.ofSize(ids.size()), b -> b.query(composeFilter(
                IdsQuery.of(i -> i.values(ids))._toQuery(),
                offerable,
                termFilter(ENABLED_FIELD, true))))
                .map(Page::getContent);
    }
}
