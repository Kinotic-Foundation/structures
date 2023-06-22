package org.kinotic.structures.internal.api.services.impl;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.EntityServiceFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪on 5/10/23.
 */
@Component
public class DefaultEntitiesService implements EntitiesService {

    private final AsyncLoadingCache<String, EntityService> entityServiceCache;

    public DefaultEntitiesService(StructureService structureService,
                                  EntityServiceFactory entityServiceFactory){

        this.entityServiceCache =
                Caffeine.newBuilder()
                        .buildAsync((key, executor) ->
                                            structureService.findById(key)
                                                            .thenComposeAsync(entityServiceFactory::createEntityService,
                                                                              executor));
    }

    @Override
    public <T> CompletableFuture<T> save(String structureId, T entity, EntityContext context) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.save(entity, context));
    }

    @Override
    public <T> CompletableFuture<Void> bulkSave(String structureId, T entities, EntityContext context) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.bulkSave(entities, context));
    }

    @Override
    public <T> CompletableFuture<T> findById(String structureId, String id, Class<T> type, EntityContext context) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.findById(id, type, context));
    }

    @Override
    public CompletableFuture<Long> count(String structureId, EntityContext context) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.count(context));
    }

    @Override
    public CompletableFuture<Void> deleteById(String structureId, String id, EntityContext context) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.deleteById(id, context));
    }

    @Override
    public <T> CompletableFuture<Page<T>> findAll(String structureId,
                                                  Pageable pageable,
                                                  Class<T> type,
                                                  EntityContext context) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.findAll(pageable, type, context));
    }

    @Override
    public <T> CompletableFuture<Page<T>> search(String structureId,
                                                 String searchText,
                                                 Pageable pageable,
                                                 Class<T> type,
                                                 EntityContext context) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.search(searchText, pageable, type, context));
    }

}
