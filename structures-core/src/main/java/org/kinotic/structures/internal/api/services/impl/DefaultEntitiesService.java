package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.util.BinaryData;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.kinotic.continuum.core.api.crud.CrudService;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.EntityServiceFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
@Component
public class DefaultEntitiesService implements EntitiesService {

    private final AsyncLoadingCache<String, EntityService> entityServiceCache;

    public DefaultEntitiesService(StructureService structureService,
                                  EntityServiceFactory entityServiceFactory){
        this.entityServiceCache = Caffeine.newBuilder()
                                          .buildAsync((key, executor) ->
                                              structureService.findById(key)
                                                              .thenComposeAsync(entityServiceFactory::createEntityService, executor));
    }

    @Override
    public CompletableFuture<BinaryData> save(String structureId, BinaryData entity) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.save(entity));
    }

    @Override
    public CompletableFuture<BinaryData> findById(String structureId, String id) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.findById(id));
    }

    @Override
    public CompletableFuture<Long> count(String structureId) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(CrudService::count);
    }

    @Override
    public CompletableFuture<Void> deleteById(String structureId, String id) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.deleteById(id));
    }

    @Override
    public CompletableFuture<Page<BinaryData>> findAll(String structureId, Pageable pageable) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.findAll(pageable));
    }

    @Override
    public CompletableFuture<Page<BinaryData>> search(String structureId, String searchText, Pageable pageable) {
        return entityServiceCache.get(structureId)
                                 .thenCompose(entityService -> entityService.search(searchText, pageable));
    }

}
