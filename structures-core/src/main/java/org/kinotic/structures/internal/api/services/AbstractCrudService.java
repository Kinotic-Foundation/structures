package org.kinotic.structures.internal.api.services;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.core.get.GetResult;
import org.kinotic.continuum.api.Identifiable;
import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveIndexOperations;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/24/23.
 */
public abstract class AbstractCrudService<T extends Identifiable<String>> implements IdentifiableCrudService<T, String> {

    protected final String indexName;
    protected final Class<T> type;
    protected final ElasticsearchAsyncClient esAsyncClient;
    protected final ReactiveElasticsearchOperations esOperations;
    protected final CrudServiceTemplate crudServiceTemplate;

    public AbstractCrudService(String indexName,
                               Class<T> type,
                               ElasticsearchAsyncClient esAsyncClient,
                               ReactiveElasticsearchOperations esOperations) {
        this.indexName = indexName;
        this.type = type;
        this.esAsyncClient = esAsyncClient;
        this.esOperations = esOperations;
        this.crudServiceTemplate = new CrudServiceTemplate(esAsyncClient);
    }

    @PostConstruct
    public void init(){
        // create mapping for class, we don't check if it has a Document annotation for now since all of these calls require an index to exist
        ReactiveIndexOperations indexOperations = esOperations.indexOps(type);
        indexOperations.exists() //
                       .flatMap(exists -> exists ? Mono.empty() : indexOperations.createWithMapping())
                       .block();
    }


    @Override
    public CompletableFuture<T> save(T entity) {
        // FIXME: add support for versioning, and optimistic locking with errors if version is out of date
        return esAsyncClient.index(i -> i
                .index(indexName)
                .id(entity.getId())
                .document(entity))
                .thenCompose(indexResponse -> findById(indexResponse.id()));
    }

    @Override
    public CompletableFuture<T> findById(String id) {
        return esAsyncClient.get(builder -> builder.index(indexName).id(id), type)
                            .thenApply(GetResult::source);
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
        return crudServiceTemplate.findAll(indexName, type, pageable, null);
    }

}
