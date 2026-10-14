package org.kinotic.domain.internal.api.repositories;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import io.vertx.core.Future;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.security.LoginProvider;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginProviderRepository extends AbstractOrganizationScopedRepository<LoginProvider> {

    private static final String APPLICATION_ID_FIELD = "applicationId";
    private static final String CONFIGURATION_ID_FIELD = "configurationId";

    public LoginProviderRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_login_provider", LoginProvider.class, crudServiceTemplate);
    }

    /**
     * The rows of one sign-in scope, oldest first: the organization's own login when {@code applicationId}
     * is null, else that application's.
     */
    public Future<List<LoginProvider>> findByScope(String orgId, String applicationId) {
        return findAll(Pageable.ofSize(100), b -> b.routing(orgId)
                                                   .query(composeOrgFilter(orgId, scopeFilter(applicationId)))
                                                   .sort(s -> s.field(f -> f.field("created"))))
                .map(Page::getContent);
    }

    /**
     * The row offering {@code configurationId} at a scope, or {@code null} when the scope does not offer it.
     */
    public Future<LoginProvider> findByScopeAndConfiguration(String orgId, String applicationId, String configurationId) {
        return findFirst(b -> b.routing(orgId)
                               .query(composeOrgFilter(orgId, scopeFilter(applicationId),
                                                       termFilter(CONFIGURATION_ID_FIELD, configurationId))));
    }

    private Query scopeFilter(String applicationId) {
        return applicationId == null
                ? crudServiceTemplate.missingFilter(APPLICATION_ID_FIELD)
                : termFilter(APPLICATION_ID_FIELD, applicationId);
    }
}
