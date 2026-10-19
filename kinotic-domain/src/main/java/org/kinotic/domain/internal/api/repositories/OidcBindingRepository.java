package org.kinotic.domain.internal.api.repositories;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import io.vertx.core.Future;
import java.util.Collection;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.security.identity.OidcBinding;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OidcBindingRepository extends AbstractRepository<OidcBinding> {

    // the most provider identities one user signs in with
    private static final int MAX_BINDINGS = 50;

    public OidcBindingRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_oidc_binding", OidcBinding.class, crudServiceTemplate);
    }

    /** The bindings of user {@code identityId}, oldest first. */
    public Future<List<OidcBinding>> findByIdentity(String identityId) {
        Validate.notBlank(identityId, "identityId cannot be blank");
        return findAll(Pageable.ofSize(MAX_BINDINGS), b -> b.query(termFilter("identityId", identityId))
                                                             .sort(s -> s.field(f -> f.field("created"))))
                .map(Page::getContent);
    }

    /** The bindings of the users {@code identityIds}, in one read. */
    public Future<List<OidcBinding>> findByIdentities(Collection<String> identityIds) {
        Future<List<OidcBinding>> ret;
        if (identityIds.isEmpty()) {
            ret = Future.succeededFuture(List.of());
        } else {
            List<FieldValue> ids = identityIds.stream().map(FieldValue::of).toList();
            ret = findAll(Pageable.ofSize(MAX_BINDINGS * identityIds.size()),
                          b -> b.query(Query.of(q -> q.terms(t -> t.field("identityId").terms(v -> v.value(ids))))))
                    .map(Page::getContent);
        }
        return ret;
    }
}
