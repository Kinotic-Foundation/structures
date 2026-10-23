package org.kinotic.domain.api.repositories;

import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.domain.api.model.ServerSbom;
import org.kinotic.domain.internal.api.repositories.AbstractRepository;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Stores the {@link ServerSbom} of each server node, keyed by the node's Ignite id.
 */
@Component
public class ServerSbomRepository extends AbstractRepository<ServerSbom> {

    public ServerSbomRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_server_sbom", ServerSbom.class, crudServiceTemplate);
    }

    /**
     * Deletes the SBOMs recorded before the given time by nodes other than those listed.
     *
     * @param nodeIds  the Ignite ids of the nodes whose SBOMs are kept
     * @param recorded the time before which the other nodes' SBOMs were recorded
     */
    public Future<Void> deleteAllOfOtherNodes(Collection<String> nodeIds, Date recorded) {
        Validate.notNull(nodeIds, "nodeIds cannot be null");
        Validate.notNull(recorded, "recorded cannot be null");
        List<String> kept = List.copyOf(nodeIds);
        String before = recorded.toInstant().toString();
        return crudServiceTemplate.deleteByQuery(indexName, b -> b.query(q -> q.bool(bool -> bool.mustNot(n -> n.ids(i -> i.values(kept)))
                                                                                         .filter(f -> f.range(r -> r.date(d -> d.field("started").lt(before)))))))
                                  .mapEmpty();
    }

}
