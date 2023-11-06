package org.kinotic.structures.internal.api.services.impl;

import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.JsonEntitiesService;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Nic Padilla 🤪on 6/18/23.
 */
@Component
public class DefaultJsonEntitiesService implements JsonEntitiesService {

    private final DefaultEntitiesService defaultEntitiesService;

    public DefaultJsonEntitiesService(DefaultEntitiesService defaultEntitiesService){
        this.defaultEntitiesService = defaultEntitiesService;
    }

    @Override
    public CompletableFuture<RawJson> save(String structureId, RawJson entity, Participant participant) {
        return defaultEntitiesService.save(structureId, entity, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Void> bulkSave(String structureId, RawJson entities, Participant participant) {
        return defaultEntitiesService.bulkSave(structureId, entities, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<RawJson> update(String structureId, RawJson entity, Participant participant) {
        return defaultEntitiesService.update(structureId, entity, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Void> bulkUpdate(String structureId, RawJson entities, Participant participant) {
        return defaultEntitiesService.bulkUpdate(structureId, entities, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<RawJson> findById(String structureId, String id, Participant participant) {
        return defaultEntitiesService.findById(structureId, id, RawJson.class, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Long> count(String structureId, Participant participant) {
        return defaultEntitiesService.count(structureId, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Void> deleteById(String structureId, String id, Participant participant) {
        return defaultEntitiesService.deleteById(structureId, id, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Page<RawJson>> findAll(String structureId,
                                                  Pageable pageable,
                                                  Participant participant) {
        return defaultEntitiesService.findAll(structureId, pageable, RawJson.class, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Page<RawJson>> search(String structureId,
                                                 String searchText,
                                                 Pageable pageable,
                                                 Participant participant) {
        return defaultEntitiesService.search(structureId, searchText, pageable, RawJson.class, new DefaultEntityContext(participant));
    }

}
