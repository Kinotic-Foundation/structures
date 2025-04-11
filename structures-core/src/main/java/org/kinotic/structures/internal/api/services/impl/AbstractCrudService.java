package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import org.kinotic.continuum.api.Identifiable;
import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveIndexOperations;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/24/23.
 */
public abstract class AbstractCrudService<T extends Identifiable<String>> implements IdentifiableCrudService<T, String> {

    protected final CrudServiceTemplate crudServiceTemplate;
    protected final ElasticsearchAsyncClient esAsyncClient;
    protected final ReactiveElasticsearchOperations esOperations;
    protected final String indexName;
    protected final Class<T> type;

    public AbstractCrudService(String indexName,
                               Class<T> type,
                               ElasticsearchAsyncClient esAsyncClient,
                               ReactiveElasticsearchOperations esOperations,
                               CrudServiceTemplate crudServiceTemplate) {
        this.indexName = indexName;
        this.type = type;
        this.esAsyncClient = esAsyncClient;
        this.esOperations = esOperations;
        this.crudServiceTemplate = crudServiceTemplate;
    }

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

    @PostConstruct
    public void init(){
        // create mapping for class, we don't check if it has a Document annotation for now since all of these calls require an index to exist
        ReactiveIndexOperations indexOperations = esOperations.indexOps(type);
        indexOperations.exists() //
                       .flatMap(exists -> exists ? indexOperations.putMapping() : indexOperations.createWithMapping())
                       .block();
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
