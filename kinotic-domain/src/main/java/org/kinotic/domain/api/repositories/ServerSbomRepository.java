package org.kinotic.domain.api.repositories;

import org.kinotic.domain.api.model.ServerSbom;
import org.kinotic.domain.internal.api.repositories.AbstractRepository;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

/**
 * Stores the {@link ServerSbom} of each server node, keyed by the node's Ignite id.
 */
@Component
public class ServerSbomRepository extends AbstractRepository<ServerSbom> {

    public ServerSbomRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_server_sbom", ServerSbom.class, crudServiceTemplate);
    }

}
