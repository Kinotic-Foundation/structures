package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.internal.api.services.sql.ParameterHolder;
import org.kinotic.structures.internal.api.services.sql.QueryExecutorFactory;
import org.kinotic.structures.internal.api.services.sql.executors.QueryExecutor;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Navíd Mitchell 🤪 on 4/23/24.
 */
@Component
public class DefaultNamedQueriesService extends AbstractCrudService<NamedQueriesDefinition> implements NamedQueriesService {

    private final AsyncLoadingCache<CacheKey, QueryExecutor> cache;
    private final ConcurrentHashMap<String, List<CacheKey>> cacheKeyTracker = new ConcurrentHashMap<>();

    public DefaultNamedQueriesService(ElasticsearchAsyncClient esAsyncClient,
                                      ReactiveElasticsearchOperations esOperations,
                                      CrudServiceTemplate crudServiceTemplate,
                                      QueryExecutorFactory queryExecutorFactory) {
        super("named_query_service_definition",
              NamedQueriesDefinition.class,
              esAsyncClient,
              esOperations,
              crudServiceTemplate);

        cache = Caffeine.newBuilder()
                        .expireAfterAccess(20, TimeUnit.HOURS)
                        .maximumSize(10_000)
                        .buildAsync((key, executor) -> findByNamespaceAndStructure(key.getStructure().getNamespace(),
                                                                                   key.getStructure().getName())
                                .thenApplyAsync(namedQueriesDefinition -> {

                                    Validate.notNull(namedQueriesDefinition, "No Named Query found for Structure: "
                                            + key.getStructure()
                                            + " and Query: "
                                            + key.getQueryName());

                                    QueryExecutor ret = queryExecutorFactory.createQueryExecutor(key.getStructure(),
                                                                                                 key.getQueryName(),
                                                                                                 namedQueriesDefinition);

                                    // Track the cache key so, we can invalidate it when the named query is updated
                                    cacheKeyTracker.compute(namedQueriesDefinition.getId(), (s, cacheKeys) -> {
                                        if(cacheKeys == null){
                                            cacheKeys = new ArrayList<>();
                                        }
                                        cacheKeys.add(key);
                                        return cacheKeys;
                                    });
                                    return ret;
                                }, executor));

    }

    @Override
    public void evictCachesFor(NamedQueriesDefinition namedQueriesDefinition) {
        cacheKeyTracker.computeIfPresent(namedQueriesDefinition.getId(), (s, cacheKeys) -> {
            for (CacheKey cacheKey : cacheKeys) {
                cache.synchronous().invalidate(cacheKey);
            }
            return null;
        });
    }

    @Override
    public <T> CompletableFuture<List<T>> executeNamedQuery(Structure structure,
                                                            String queryName,
                                                            ParameterHolder parameterHolder,
                                                            Class<T> type,
                                                            EntityContext context) {
        return cache.get(new CacheKey(queryName, structure))
                    .thenCompose(queryExecutor -> queryExecutor.execute(parameterHolder, type, context));
    }

    @Override
    public <T> CompletableFuture<Page<T>> executeNamedQueryPage(Structure structure,
                                                                String queryName,
                                                                ParameterHolder parameterHolder,
                                                                Pageable pageable,
                                                                Class<T> type,
                                                                EntityContext context) {
        return cache.get(new CacheKey(queryName, structure))
                    .thenCompose(queryExecutor -> queryExecutor.executePage(parameterHolder, pageable, type, context));
    }

    @Override
    public CompletableFuture<NamedQueriesDefinition> findByNamespaceAndStructure(String namespace, String structure) {
        return crudServiceTemplate.search(indexName, Pageable.ofSize(1), type, builder -> builder
                .query(q -> q
                        .bool(b -> b
                                .filter(TermQuery.of(tq -> tq.field("namespace").value(namespace))._toQuery(),
                                        TermQuery.of(tq -> tq.field("structure").value(structure))._toQuery())
                        )
                )).thenApply(page -> page.getContent() != null && !page.getContent().isEmpty()
                ? page.getContent().getFirst()
                : null);
    }

    @Override
    public CompletableFuture<NamedQueriesDefinition> save(NamedQueriesDefinition entity) {
        // TODO: preprocess queries to correct index name and add Metadata about query type to be used by other parts of the system
        //       The Query type information will speed up other areas the need this as well
        return super.save(entity)
                    .thenApply(namedQueriesDefinition -> {
                        evictCachesFor(namedQueriesDefinition);
                        return namedQueriesDefinition;
                    });
    }


    @Override
    public CompletableFuture<Page<NamedQueriesDefinition>> search(String searchText, Pageable pageable) {
        return crudServiceTemplate.search(indexName,
                                          pageable,
                                          NamedQueriesDefinition.class,
                                          builder -> builder.q(searchText));
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    private static class CacheKey {
        private final String queryName;
        private final Structure structure;
    }
}
