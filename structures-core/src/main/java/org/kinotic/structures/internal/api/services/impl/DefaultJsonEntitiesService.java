package org.kinotic.structures.internal.api.services.impl;

import com.fasterxml.jackson.databind.util.TokenBuffer;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.FastestType;
import org.kinotic.structures.api.domain.QueryParameter;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.JsonEntitiesService;
import org.kinotic.structures.internal.api.services.sql.ListParameterHolder;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public CompletableFuture<Void> bulkSave(String structureId, TokenBuffer entities, Participant participant) {
        return defaultEntitiesService.bulkSave(structureId, entities, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Void> bulkUpdate(String structureId, TokenBuffer entities, Participant participant) {
        return defaultEntitiesService.bulkUpdate(structureId, entities, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Long> count(String structureId, Participant participant) {
        return defaultEntitiesService.count(structureId, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Long> countByQuery(String structureId, String query, Participant participant) {
        return defaultEntitiesService.countByQuery(structureId, query, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Void> deleteById(String structureId, String id, Participant participant) {
        return defaultEntitiesService.deleteById(structureId, id, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Void> deleteByQuery(String structureId, String query, Participant participant) {
        return defaultEntitiesService.deleteByQuery(structureId, query, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Page<FastestType>> findAll(String structureId,
                                                        Pageable pageable,
                                                        Participant participant) {
        return defaultEntitiesService.findAll(structureId, pageable, FastestType.class, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<FastestType> findById(String structureId, String id, Participant participant) {
        return defaultEntitiesService.findById(structureId, id, FastestType.class, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<List<FastestType>> findByIds(String structureId, List<String> ids, Participant participant) {
        return defaultEntitiesService.findByIds(structureId, ids, FastestType.class, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<List<RawJson>> namedQuery(String structureId,
                                                       String queryName,
                                                       List<QueryParameter> queryParameters,
                                                       Participant participant) {
        return defaultEntitiesService.namedQuery(structureId,
                                                 queryName,
                                                 new ListParameterHolder(queryParameters),
                                                 RawJson.class,
                                                 new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Page<RawJson>> namedQueryPage(String structureId,
                                                           String queryName,
                                                           List<QueryParameter> queryParameters,
                                                           Pageable pageable,
                                                           Participant participant) {
        return defaultEntitiesService.namedQueryPage(structureId,
                                                     queryName,
                                                     new ListParameterHolder(queryParameters),
                                                     pageable,
                                                     RawJson.class,
                                                     new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Void> syncIndex(String structureId, Participant participant) {
        return defaultEntitiesService.syncIndex(structureId, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<TokenBuffer> save(String structureId, TokenBuffer entity, Participant participant) {
        return defaultEntitiesService.save(structureId, entity, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<Page<FastestType>> search(String structureId,
                                                       String searchText,
                                                       Pageable pageable,
                                                       Participant participant) {
        return defaultEntitiesService.search(structureId, searchText, pageable, FastestType.class, new DefaultEntityContext(participant));
    }

    @Override
    public CompletableFuture<TokenBuffer> update(String structureId, TokenBuffer entity, Participant participant) {
        return defaultEntitiesService.update(structureId, entity, new DefaultEntityContext(participant));
    }

}
