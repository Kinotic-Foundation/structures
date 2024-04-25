package org.kinotic.structures.internal.api.services.impl;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.QueryParameter;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.EntityServiceFactory;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Navíd Mitchell 🤪on 5/10/23.
 */
@Component
public class DefaultEntitiesService implements EntitiesService {

    private final AsyncLoadingCache<String, EntityService> cache;

    public DefaultEntitiesService(StructureDAO structureDAO,
                                  EntityServiceFactory entityServiceFactory){
        cache = Caffeine.newBuilder()
                        .expireAfterAccess(20, TimeUnit.HOURS)
                        .maximumSize(10_000)
                        .buildAsync((key, executor) -> structureDAO.findById(key)
                                                                   .thenApply(structure -> {
                                                                       if(structure == null){
                                                                           throw new IllegalArgumentException("No structure found for id: " + key);
                                                                       }
                                                                       return structure;
                                                                   })
                                                                   .thenComposeAsync(entityServiceFactory::createEntityService,
                                                                                     executor));
    }

    @Override
    public <T> CompletableFuture<Void> bulkSave(String structureId, T entities, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.bulkSave(entities, context));
    }

    @Override
    public <T> CompletableFuture<Void> bulkUpdate(String structureId, T entities, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.bulkUpdate(entities, context));
    }

    @Override
    public CompletableFuture<Long> count(String structureId, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.count(context));
    }

    @Override
    public CompletableFuture<Long> countByQuery(String structureId, String query, EntityContext context) {
        return cache.get(structureId)
                .thenCompose(entityService -> entityService.countByQuery(query, context));
    }

    @Override
    public CompletableFuture<Void> deleteById(String structureId, String id, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.deleteById(id, context));
    }

    @Override
    public CompletableFuture<Void> deleteByQuery(String structureId, String query, EntityContext context) {
        return cache.get(structureId)
                .thenCompose(entityService -> entityService.deleteByQuery(query, context));
    }

    @Override
    public void evictCachesFor(Structure structure) {
        Validate.notNull(structure, "structure must not be null");
        cache.asMap().remove(structure.getId());
    }

    @Override
    public <T> CompletableFuture<Page<T>> findAll(String structureId,
                                                  Pageable pageable,
                                                  Class<T> type,
                                                  EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.findAll(pageable, type, context));
    }

    @Override
    public <T> CompletableFuture<T> findById(String structureId, String id, Class<T> type, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.findById(id, type, context));
    }

    @Override
    public <T> CompletableFuture<List<T>> findByIds(String structureId, List<String> ids, Class<T> type, EntityContext context) {
        return cache.get(structureId)
                .thenCompose(entityService -> entityService.findByIds(ids, type, context));
    }

    @Override
    public <T> CompletableFuture<T> namedQuery(String structureId,
                                               String queryName,
                                               List<QueryParameter> parameters,
                                               Class<T> type,
                                               EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.namedQuery(queryName, parameters, type, context));
    }

    @Override
    public <T> CompletableFuture<Page<T>> namedQueryPage(String structureId,
                                                         String queryName,
                                                         List<QueryParameter> parameters,
                                                         Pageable pageable,
                                                         Class<T> type,
                                                         EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.namedQueryPage(queryName, parameters, pageable, type, context));
    }

    @Override
    public <T> CompletableFuture<T> save(String structureId, T entity, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.save(entity, context));
    }

    @Override
    public <T> CompletableFuture<Page<T>> search(String structureId,
                                                 String searchText,
                                                 Pageable pageable,
                                                 Class<T> type,
                                                 EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.search(searchText, pageable, type, context));
    }

    @Override
    public <T> CompletableFuture<T> update(String structureId, T entity, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.update(entity, context));
    }
}
