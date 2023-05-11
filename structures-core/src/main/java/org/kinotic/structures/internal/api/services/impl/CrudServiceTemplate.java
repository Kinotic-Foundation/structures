package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.StorageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public class CrudServiceTemplate {

    private final ElasticsearchAsyncClient esAsyncClient;

    public CrudServiceTemplate(ElasticsearchAsyncClient esAsyncClient) {
        this.esAsyncClient = esAsyncClient;
    }

    /**
     * Counts the number of documents in the index. Also allows for customization of the {@link CountRequest}.
     * @param indexName name of the index to count
     * @param builderConsumer to customize the {@link CountRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with the number of documents in the index
     */
    public CompletableFuture<Long> count(String indexName,
                                         Consumer<CountRequest.Builder> builderConsumer) {
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
    public CompletableFuture<Void> createIndex(String indexName,
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
     * @param indexName name of the index to search
     * @param type of the documents to return
     * @param pageable to use for the search
     * @param builderConsumer to customize the {@link SearchRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with a {@link Page} of documents
     */
    public <T> CompletableFuture<Page<T>> findAll(String indexName,
                                                  Class<T> type,
                                                  Pageable pageable,
                                                  Consumer<SearchRequest.Builder> builderConsumer){
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

    /**
     * Deletes a document by id. Also allows for customization of the {@link DeleteRequest}.
     * @param indexName name of the index to delete from
     * @param id of the document to delete
     * @param builderConsumer to customize the {@link DeleteRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with the {@link DeleteResponse}
     */
    public CompletableFuture<DeleteResponse> deleteById(String indexName,
                                                        String id,
                                                        Consumer<DeleteRequest.Builder> builderConsumer){
        return esAsyncClient.delete(builder -> {
                                builder.index(indexName).id(id);
                                if(builderConsumer != null){
                                    builderConsumer.accept(builder);
                                }
                                return builder;
                            });
    }

}
