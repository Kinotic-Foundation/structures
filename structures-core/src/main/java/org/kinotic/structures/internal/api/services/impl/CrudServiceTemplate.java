package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.ErrorResponse;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.get.GetResult;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.StorageType;
import co.elastic.clients.json.JsonpDeserializer;
import co.elastic.clients.json.JsonpMapperBase;
import co.elastic.clients.transport.JsonEndpoint;
import co.elastic.clients.transport.endpoints.EndpointWithResponseMapperAttr;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.internal.serializer.RawJsonDeserializerDelegator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
@Component
public class CrudServiceTemplate {

    private final ElasticsearchAsyncClient esAsyncClient;
    private final ObjectMapper objectMapper;


    public CrudServiceTemplate(ElasticsearchAsyncClient esAsyncClient, ObjectMapper objectMapper) {
        this.esAsyncClient = esAsyncClient;
        this.objectMapper = objectMapper;
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
     * For example aggregations are not supported.
     * This is meant to be used internally by implementors.
     *
     * @param indexName       name of the index to search
     * @param pageable        to use for the search
     * @param type            of the documents to return
     * @param builderConsumer to customize the {@link SearchRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with a {@link Page} of documents
     */
    public <T> CompletableFuture<Page<T>> findAll(String indexName,
                                                  Pageable pageable,
                                                  Class<T> type,
                                                  Consumer<SearchRequest.Builder> builderConsumer){

        return findAll(indexName, pageable, getDeserializer(type), builderConsumer)
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
     * Provides base functionality to get a {@link Page} of documents from elasticsearch. With the ability to customize the {@link SearchRequest}.
     * @param indexName name of the index to search
     * @param pageable to use for the search
     * @param deserializer to use to deserialize the documents
     * @param builderConsumer to customize the {@link SearchRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with a {@link SearchResponse} of documents
     * @param <T> type of the documents to return
     */
    public <T> CompletableFuture<SearchResponse<T>> findAll(String indexName,
                                                            Pageable pageable,
                                                            JsonpDeserializer<T> deserializer,
                                                            Consumer<SearchRequest.Builder> builderConsumer) {
        @SuppressWarnings("unchecked")
        JsonEndpoint<SearchRequest, SearchResponse<T>, ErrorResponse> endpoint = (JsonEndpoint<SearchRequest, SearchResponse<T>, ErrorResponse>) SearchRequest._ENDPOINT;
        endpoint = new EndpointWithResponseMapperAttr<>(endpoint,
                                                        "co.elastic.clients:Deserializer:_global.search.TDocument", deserializer);

        SearchRequest.Builder builder = new SearchRequest.Builder();

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

        //noinspection resource
        return esAsyncClient._transport().performRequestAsync(builder.build(), endpoint, esAsyncClient._transportOptions());
    }


    /**
     * Finds a document by id. Also allows for customization of the {@link GetRequest}.
     *
     * @param <T>             type of the document to return
     * @param indexName       name of the index to search
     * @param id              of the document to return
     * @param type            of the document to return
     * @param builderConsumer to customize the {@link GetRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with the document
     */
    public <T> CompletableFuture<T> findById(String indexName,
                                             String id,
                                             Class<T> type,
                                             Consumer<GetRequest.Builder> builderConsumer) {
        return this.findById(indexName, id, getDeserializer(type), builderConsumer)
                   .thenApply(GetResult::source);
    }


    /**
     * Finds a document by id. Also allows for customization of the {@link GetRequest}.
     * @param indexName name of the index to search
     * @param id of the document to return
     * @param deserializer to use to deserialize the document
     * @param builderConsumer to customize the {@link GetRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with the document
     * @param <T> type of the document to return
     */
    public <T> CompletableFuture<GetResponse<T>> findById(String indexName,
                                                          String id,
                                                          JsonpDeserializer<T> deserializer,
                                                          Consumer<GetRequest.Builder> builderConsumer){
        //noinspection unchecked
        JsonEndpoint<GetRequest, GetResponse<T>, ErrorResponse> endpoint =
                (JsonEndpoint<GetRequest, GetResponse<T>, ErrorResponse>) GetRequest._ENDPOINT;

        endpoint = new EndpointWithResponseMapperAttr<>(endpoint, "co.elastic.clients:Deserializer:_global.get.TDocument", deserializer);

        GetRequest.Builder builder = new GetRequest.Builder();

        builder.index(indexName).id(id);
        if(builderConsumer != null){
            builderConsumer.accept(builder);
        }

        //noinspection resource
        return esAsyncClient._transport().performRequestAsync(builder.build(), endpoint, esAsyncClient._transportOptions());
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

    private <T> JsonpDeserializer<T> getDeserializer(Class<T> type) {
        if(RawJson.class.isAssignableFrom(type)){
            //noinspection unchecked
            return (JsonpDeserializer<T>) new RawJsonDeserializerDelegator(objectMapper);
        }

        // Try the built-in deserializers first to avoid repeated lookups in the Jsonp mapper for client-defined classes
        JsonpDeserializer<T> result = JsonpMapperBase.findDeserializer(type);
        if (result != null) {
            return result;
        }

        return JsonpDeserializer.of(type);
    }

}
