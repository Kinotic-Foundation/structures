package org.kinotic.domain.internal.api.repositories;

import org.kinotic.domain.api.model.security.PendingApplicationSignUp;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

/**
 * Persistence for {@link PendingApplicationSignUp} records, with the token-based, single-use, expiry-aware
 * consumption inherited from {@link AbstractTokenVerificationRepository}.
 */
@Component
public class PendingApplicationSignUpRepository extends AbstractTokenVerificationRepository<PendingApplicationSignUp> {

    public PendingApplicationSignUpRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_pending_application_sign_up", PendingApplicationSignUp.class, crudServiceTemplate);
    }
}
