package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import org.kinotic.continuum.api.Identifiable;
import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/24/23.
 */
@RequiredArgsConstructor
public abstract class AbstractCrudService<T extends Identifiable<String>> implements IdentifiableCrudService<T, String> {

    protected final String indexName;
    protected final Class<T> type;
    protected final ElasticsearchAsyncClient esAsyncClient;
    protected final CrudServiceTemplate crudServiceTemplate;

    @Override
    public CompletableFuture<Long> count() {
        return crudServiceTemplate.count(indexName, null);
    }

    @Override
    public CompletableFuture<Void> deleteById(String id) {
        return crudServiceTemplate.deleteById(indexName, id, null)
                                  .thenApply(response -> null);
    }

    @Override
    public CompletableFuture<Page<T>> findAll(Pageable pageable) {
        return crudServiceTemplate.search(indexName, pageable, type, null);
    }

    @Override
    public CompletableFuture<T> findById(String id) {
        return crudServiceTemplate.findById(indexName, id, type, null);
    }

    @Override
    public CompletableFuture<T> save(T entity) {
        return esAsyncClient.index(i -> i
                .index(indexName)
                .id(entity.getId())
                .document(entity)
                .refresh(Refresh.True))
                .thenCompose(indexResponse -> findById(indexResponse.id()));
    }

}
