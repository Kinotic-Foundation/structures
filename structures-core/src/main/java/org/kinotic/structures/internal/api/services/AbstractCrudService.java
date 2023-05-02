package org.kinotic.structures.internal.api.services;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.CountRequest;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.get.GetResult;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.StorageType;
import org.kinotic.continuum.api.Identifiable;
import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveIndexOperations;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Created by Navíd Mitchell 🤪 on 4/24/23.
 */
public abstract class AbstractCrudService<T extends Identifiable<String>> implements IdentifiableCrudService<T, String> {

    private final String indexName;
    private final Class<T> type;
    protected final ElasticsearchAsyncClient esAsyncClient;
    protected final ReactiveElasticsearchOperations esOperations;

    public AbstractCrudService(String indexName,
                               Class<T> type,
                               ElasticsearchAsyncClient esAsyncClient,
                               ReactiveElasticsearchOperations esOperations) {
        this.indexName = indexName;
        this.type = type;
        this.esAsyncClient = esAsyncClient;
        this.esOperations = esOperations;
    }

    @PostConstruct
    public void init(){
        // create mapping for class, we don't check if it has a Document annotation for now since all of these calls require an index to exist
        ReactiveIndexOperations indexOperations = esOperations.indexOps(type);
        indexOperations.exists() //
                       .flatMap(exists -> exists ? Mono.empty() : indexOperations.createWithMapping())
                       .block();
    }

    /**
     * Counts the number of documents in the index. Also allows for customization of the {@link CountRequest}.
     * @param builderConsumer to customize the {@link CountRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with the number of documents in the index
     */
    protected CompletableFuture<Long> count(Consumer<CountRequest.Builder> builderConsumer) {
        return esAsyncClient.count(builder -> {
                                builder.index(indexName);
                                if(builderConsumer != null){
                                    builderConsumer.accept(builder);
                                }
                                return builder;
                            })
                            .thenApply(CountResponse::count);
    }

    /**
     * Creates an index with the given name. Also allows for customization of the {@link CreateIndexRequest}.
     * @param indexName name of the index to create
     * @param failIfExists if true will fail with an exception if the index already exists
     * @param builderConsumer to customize the {@link CreateIndexRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete when the index has been created
     */
    protected CompletableFuture<Void> createIndex(String indexName,
                                                  boolean failIfExists,
                                                  Consumer<CreateIndexRequest.Builder> builderConsumer){
        return esAsyncClient.indices().exists(builder -> builder.index(indexName))
                            .thenCompose(exists -> {
                                if(!exists.value()){
                                    return esAsyncClient.indices()
                                                        .create(builder -> {
                                                            builder.index(indexName);
                                                            builder.settings(s -> s
                                                                    .numberOfShards("5")
                                                                    .numberOfReplicas("2")
                                                                    .refreshInterval(t -> t.time("1s"))
                                                                    .store(st -> st.type(StorageType.Fs))
                                                            );

                                                            if(builderConsumer != null){
                                                                builderConsumer.accept(builder);
                                                            }

                                                            return builder;
                                                        })
                                                        .thenApply(response -> null);
                                }else{
                                    if(failIfExists){
                                        return CompletableFuture.failedFuture(new IllegalArgumentException("Index already exists: " + indexName));
                                    }else{
                                        return CompletableFuture.completedFuture(null);
                                    }
                                }
                            });
    }

    /**
     * Provides base functionality to get a {@link Page} of documents from elasticsearch. With the ability to customize the {@link SearchRequest}.
     * NOTE: not all customizations are supported, only the ones that make sense for a {@link Page} of documents.
     *       For example aggregations are not supported.
     *       This is meant to be used internally by implementors.
     *
     * @param pageable to use for the search
     * @param builderConsumer to customize the {@link SearchRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with a {@link Page} of documents
     */
    protected CompletableFuture<Page<T>> findAll(Pageable pageable, Consumer<SearchRequest.Builder> builderConsumer){
        return esAsyncClient.search(builder -> {
                                builder.index(indexName)
                                       .trackTotalHits(t -> t.enabled(true))
                                       .from(pageable.getPageNumber() * pageable.getPageSize())
                                       .size(pageable.getPageSize());

                                for(Sort.Order order : pageable.getSort()){
                                    builder.sort(s -> s.field(f -> f.field(order.getProperty()).order(order.isAscending() ? SortOrder.Asc : SortOrder.Desc)));
                                }

                                if(builderConsumer != null){
                                    builderConsumer.accept(builder);
                                }

                                return builder;
                            }, type)
                            .thenApply(response -> {
                                HitsMetadata<T> hitsMetadata = response.hits();
                                List<T> content = new ArrayList<>(hitsMetadata.hits().size());
                                for(Hit<T> hit : hitsMetadata.hits()) {
                                    content.add(hit.source());
                                }
                                return new PageImpl<>(content, pageable,
                                                      Objects.requireNonNull(hitsMetadata.total(),
                                                                             "System Error total hits not available").value());
                            });
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
        return this.count(null);
    }

    @Override
    public CompletableFuture<Void> deleteById(String id) {
        return esAsyncClient.delete(builder -> builder.index(indexName).id(id))
                            .thenApply(response -> null);
    }

    @Override
    public CompletableFuture<Page<T>> findAll(Pageable pageable) {
        return findAll(pageable, null);
    }

    @Override
    public CompletableFuture<Page<T>> search(String searchText, Pageable pageable) {
        return null;
    }
}
