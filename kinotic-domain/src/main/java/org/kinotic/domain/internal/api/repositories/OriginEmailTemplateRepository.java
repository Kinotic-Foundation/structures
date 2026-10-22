package org.kinotic.domain.internal.api.repositories;

import io.vertx.core.Future;
import org.kinotic.domain.api.model.EmailTemplateKind;
import org.kinotic.domain.api.model.OriginEmailTemplate;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

@Component
public class OriginEmailTemplateRepository extends AbstractRepository<OriginEmailTemplate> {

    public OriginEmailTemplateRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_origin_email_template", OriginEmailTemplate.class, crudServiceTemplate);
    }

    /** Finds the template of {@code kind} for the UI at {@code origin}, or {@code null} if none exists. */
    public Future<OriginEmailTemplate> findByOriginAndKind(String origin, EmailTemplateKind kind) {
        return findFirst(b -> b.query(composeFilter(termFilter("origin", origin), termFilter("kind", kind.name()))));
    }
}
