package org.kinotic.structures.internal.api.services.impl;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.EntityServiceFactory;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.kinotic.structures.internal.api.services.sql.ParameterHolder;
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
                        .maximumSize(2_000)
                        .buildAsync((key, executor) -> structureDAO.findById(key)
                                                                   .thenApply(structure -> {
                                                                       Validate.notNull(structure, "No Structure found for key: " + key);
                                                                       return structure;
                                                                   })
                                                                   .thenComposeAsync(entityServiceFactory::createEntityService,
                                                                                     executor));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Void> bulkSave(@SpanAttribute("structureId") String structureId, T entities, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.bulkSave(entities, context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Void> bulkUpdate(@SpanAttribute("structureId") String structureId, T entities, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.bulkUpdate(entities, context));
    }

    @WithSpan
    @Override
    public CompletableFuture<Long> count(@SpanAttribute("structureId") String structureId, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.count(context));
    }

    @WithSpan
    @Override
    public CompletableFuture<Long> countByQuery(@SpanAttribute("structureId") String structureId, String query, EntityContext context) {
        return cache.get(structureId)
                .thenCompose(entityService -> entityService.countByQuery(query, context));
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> deleteById(@SpanAttribute("structureId") String structureId, String id, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.deleteById(id, context));
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> deleteByQuery(@SpanAttribute("structureId") String structureId, String query, EntityContext context) {
        return cache.get(structureId)
                .thenCompose(entityService -> entityService.deleteByQuery(query, context));
    }

    @WithSpan
    @Override
    public void evictCachesFor(@SpanAttribute("structure") Structure structure) {
        Validate.notNull(structure, "structure must not be null");
        cache.asMap().remove(structure.getId());
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Page<T>> findAll(@SpanAttribute("structureId") String structureId,
                                                  Pageable pageable,
                                                  Class<T> type,
                                                  EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.findAll(pageable, type, context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<T> findById(@SpanAttribute("structureId") String structureId, String id, Class<T> type, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.findById(id, type, context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<List<T>> findByIds(@SpanAttribute("structureId") String structureId, List<String> ids, Class<T> type, EntityContext context) {
        return cache.get(structureId)
                .thenCompose(entityService -> entityService.findByIds(ids, type, context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<List<T>> namedQuery(@SpanAttribute("structureId") String structureId,
                                                     @SpanAttribute("queryName") String queryName,
                                                     ParameterHolder parameterHolder,
                                                     Class<T> type,
                                                     EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.namedQuery(queryName, parameterHolder, type, context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Page<T>> namedQueryPage(@SpanAttribute("structureId") String structureId,
                                                         @SpanAttribute("queryName") String queryName,
                                                         ParameterHolder parameterHolder,
                                                         Pageable pageable,
                                                         Class<T> type,
                                                         EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.namedQueryPage(queryName,
                                                                               parameterHolder,
                                                                               pageable,
                                                                               type,
                                                                               context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<T> save(@SpanAttribute("structureId") String structureId, T entity, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.save(entity, context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Page<T>> search(@SpanAttribute("structureId") String structureId,
                                                 String searchText,
                                                 Pageable pageable,
                                                 Class<T> type,
                                                 EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.search(searchText, pageable, type, context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<T> update(@SpanAttribute("structureId") String structureId, T entity, EntityContext context) {
        return cache.get(structureId)
                    .thenCompose(entityService -> entityService.update(entity, context));
    }
}
