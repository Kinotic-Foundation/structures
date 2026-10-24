package org.kinotic.system.internal.api.repositories;

import io.vertx.core.Future;
import org.kinotic.domain.internal.api.repositories.AbstractRepository;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.kinotic.system.api.model.llm.LlmConfiguration;
import org.springframework.stereotype.Component;

@Component
public class LlmConfigurationRepository extends AbstractRepository<LlmConfiguration> {

    public LlmConfigurationRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_llm_configuration", LlmConfiguration.class, crudServiceTemplate);
    }

    /** Finds the configuration named {@code name}, or {@code null} if none exists. */
    public Future<LlmConfiguration> findByName(String name) {
        return findFirst(b -> b.query(termFilter("name", name)));
    }
}
