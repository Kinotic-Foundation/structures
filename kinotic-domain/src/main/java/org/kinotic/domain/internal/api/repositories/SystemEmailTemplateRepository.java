package org.kinotic.domain.internal.api.repositories;

import org.kinotic.domain.api.model.SystemEmailTemplate;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

@Component
public class SystemEmailTemplateRepository extends AbstractRepository<SystemEmailTemplate> {

    public SystemEmailTemplateRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_system_email_template", SystemEmailTemplate.class, crudServiceTemplate);
    }
}
