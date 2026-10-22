package org.kinotic.domain.internal.api.repositories;

import org.kinotic.domain.api.model.PlatformEmailTemplate;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlatformEmailTemplateRepository extends AbstractRepository<PlatformEmailTemplate> {

    public PlatformEmailTemplateRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_platform_email_template", PlatformEmailTemplate.class, crudServiceTemplate);
    }
}
