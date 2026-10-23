package org.kinotic.domain.internal.api.repositories;

import io.vertx.core.Future;
import org.kinotic.domain.api.model.ApplicationKey;
import org.kinotic.domain.api.model.security.PendingSignUp;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

/**
 * Persistence for {@link PendingSignUp} records: email lookup plus the token-based, single-use,
 * expiry-aware consumption inherited from {@link AbstractTokenVerificationRepository}, over one
 * index serving every sign-up flow.
 */
@Component
public class PendingSignUpRepository extends AbstractTokenVerificationRepository<PendingSignUp> {

    public PendingSignUpRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_pending_signup", PendingSignUp.class, crudServiceTemplate);
    }

    /** Finds a pending sign-up that founds an organization by email, or {@code null} — used to block duplicate submissions. */
    public Future<PendingSignUp> findByEmail(String email) {
        return findFirst(b -> b.query(composeFilter(termFilter("email", email), missingFilter("applicationId"))));
    }

    /** Finds a pending sign-up to the application by email, or {@code null}. */
    public Future<PendingSignUp> findByApplicationAndEmail(ApplicationKey application, String email) {
        return findFirst(b -> b.query(composeFilter(termFilter("email", email),
                                                    termFilter("organizationId", application.organizationId()),
                                                    termFilter("applicationId", application.applicationId()))));
    }
}
