package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.ErrorResponse;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.get.GetResult;
import co.elastic.clients.elasticsearch.core.mget.MultiGetResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.PutMappingRequest;
import co.elastic.clients.elasticsearch.indices.StorageType;
import co.elastic.clients.json.JsonpDeserializer;
import co.elastic.clients.json.JsonpMapperBase;
import co.elastic.clients.transport.JsonEndpoint;
import co.elastic.clients.transport.endpoints.EndpointWithResponseMapperAttr;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.core.api.crud.*;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.internal.serializer.RawJsonJsonpDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
@Component
public class CrudServiceTemplate {

    private static final Logger log = LoggerFactory.getLogger(CrudServiceTemplate.class);

    private final ElasticsearchAsyncClient esAsyncClient;
    private final ObjectMapper objectMapper;
    private final RawJsonJsonpDeserializer rawJsonJsonpDeserializer;

    public CrudServiceTemplate(ElasticsearchAsyncClient esAsyncClient,
                               ObjectMapper objectMapper) {
        this.esAsyncClient = esAsyncClient;
        this.objectMapper = objectMapper;
        rawJsonJsonpDeserializer = new RawJsonJsonpDeserializer(objectMapper);
    }


    /**
     * Counts the number of documents in the index. Also allows for customization of the {@link CountRequest}.
     *
     * @param indexName       name of the index to count
     * @param builderConsumer to customize the {@link CountRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with the number of documents in the index
     */
    public CompletableFuture<Long> count(String indexName,
                                         Consumer<CountRequest.Builder> builderConsumer) {
        return esAsyncClient.count(builder -> {
                                builder.index(indexName);
                                if (builderConsumer != null) {
                                    builderConsumer.accept(builder);
                                }
                                return builder;
                            })
                            .thenApply(CountResponse::count);
    }


    /**
     * Creates an index with the given name. Also allows for customization of the {@link CreateIndexRequest}.
     *
     * @param indexName       name of the index to create
     * @param failIfExists    if true will fail with an exception if the index already exists
     * @param builderConsumer to customize the {@link CreateIndexRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete when the index has been created
     */
    public CompletableFuture<Void> createIndex(String indexName,
                                               boolean failIfExists,
                                               Consumer<CreateIndexRequest.Builder> builderConsumer) {
        return esAsyncClient.indices().exists(builder -> builder.index(indexName))
                            .thenCompose(exists -> {
                                if (!exists.value()) {
                                    return esAsyncClient.indices()
                                                        .create(builder -> {
                                                            builder.index(indexName);
                                                            builder.settings(s -> s
                                                                    .numberOfShards("3")
                                                                    .numberOfReplicas("2")
                                                                    .store(st -> st.type(StorageType.Fs))
                                                            );

                                                            if (builderConsumer != null) {
                                                                builderConsumer.accept(builder);
                                                            }

                                                            return builder;
                                                        })
                                                        .thenApply(response -> null);
                                } else {
                                    if (failIfExists) {
                                        return CompletableFuture.failedFuture(new IllegalArgumentException(
                                                "Index already exists: " + indexName));
                                    } else {
                                        return CompletableFuture.completedFuture(null);
                                    }
                                }
                            });
    }

    /**
     * Deletes a document by id. Also allows for customization of the {@link DeleteRequest}.
     *
     * @param indexName       name of the index to delete from
     * @param id              of the document to delete
     * @param builderConsumer to customize the {@link DeleteRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with the {@link DeleteResponse}
     */
    public CompletableFuture<DeleteResponse> deleteById(String indexName,
                                                        String id,
                                                        Consumer<DeleteRequest.Builder> builderConsumer) {
        return esAsyncClient.delete(builder -> {
            builder.index(indexName).id(id);
            if (builderConsumer != null) {
                builderConsumer.accept(builder);
            }
            return builder;
        });
    }

    /**
     * Deletes a list of documents by provided query. Also allows for customization of the {@link DeleteRequest}.
     *
     * @param indexName       name of the index to delete from
     * @param builderConsumer to customize the {@link DeleteRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with the {@link DeleteResponse}
     */
    public CompletableFuture<DeleteByQueryResponse> deleteByQuery(String indexName,
                                                                  Consumer<DeleteByQueryRequest.Builder> builderConsumer) {
        return esAsyncClient.deleteByQuery(builder -> {
            builder.index(indexName);
            if (builderConsumer != null) {
                builderConsumer.accept(builder);
            }
            return builder;
        });
    }


    /**
     * Finds a document by id. Also allows for customization of the {@link GetRequest}.
     *
     * @param indexName       name of the index to search
     * @param id              of the document to return
     * @param type            of the document to return
     * @param builderConsumer to customize the {@link GetRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with the document
     */
    public <T> CompletableFuture<T> findById(String indexName,
                                             String id,
                                             Class<T> type,
                                             Consumer<GetRequest.Builder> builderConsumer){
        return findById(indexName, id, type, builderConsumer, null);
    }

    /**
     * Finds a document by id. Also allows for customization of the {@link GetRequest}.
     *
     * @param indexName       name of the index to search
     * @param id              of the document to return
     * @param type            of the document to return
     * @param builderConsumer to customize the {@link GetRequest}, or null if no customization is needed
     * @param resultMapper to map the {@link GetResult} to the desired type or null if the source should be returned directly
     * @return a {@link CompletableFuture} that will complete with the document
     */
    public <T, R> CompletableFuture<R> findById(String indexName,
                                                String id,
                                                Class<T> type,
                                                Consumer<GetRequest.Builder> builderConsumer,
                                                Function<GetResult<T>, R> resultMapper) {

        //noinspection unchecked
        JsonEndpoint<GetRequest, GetResponse<T>, ErrorResponse> endpoint =
                (JsonEndpoint<GetRequest, GetResponse<T>, ErrorResponse>) GetRequest._ENDPOINT;

        endpoint = new EndpointWithResponseMapperAttr<>(endpoint,
                                                        "co.elastic.clients:Deserializer:_global.get.Response.TDocument",
                                                        getDeserializer(type));

        GetRequest.Builder builder = new GetRequest.Builder();

        builder.index(indexName).id(id);
        if (builderConsumer != null) {
            builderConsumer.accept(builder);
        }

        //noinspection resource
        return esAsyncClient._transport()
                            .performRequestAsync(builder.build(),
                                                 endpoint,
                                                 esAsyncClient._transportOptions())
                            .thenApply(tGetResponse -> {
                                if(resultMapper != null) {
                                    return resultMapper.apply(tGetResponse);
                                }else{
                                    //noinspection unchecked
                                    return (R)tGetResponse.source();
                                }
                            });
    }


    /**
     * Finds a list document by their id. Also allows for customization of the {@link MgetRequest}.
     *
     * @param indexName       name of the index to search
     * @param ids             of the documents to return
     * @param type            of the document to return
     * @param builderConsumer to customize the {@link GetRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with the documents requested
     */
    public <T> CompletableFuture<List<T>> findByIds(String indexName,
                                                    List<String> ids,
                                                    Class<T> type,
                                                    Consumer<MgetRequest.Builder> builderConsumer){
        return findByIds(indexName, ids, type, builderConsumer, null);
    }

    /**
     * Finds a list document by their id. Also allows for customization of the {@link MgetRequest}.
     *
     * @param indexName       name of the index to search
     * @param ids             of the documents to return
     * @param type            of the document to return
     * @param builderConsumer to customize the {@link GetRequest}, or null if no customization is needed
     * @param resultMapper to map the {@link GetResult} to the desired type or null if the source should be returned directly
     * @return a {@link CompletableFuture} that will complete with the documents requested
     */
    public <T, R> CompletableFuture<List<R>> findByIds(String indexName,
                                                       List<String> ids,
                                                       Class<T> type,
                                                       Consumer<MgetRequest.Builder> builderConsumer,
                                                       Function<GetResult<T>, R> resultMapper) {

        @SuppressWarnings("unchecked")
        JsonEndpoint<MgetRequest, MgetResponse<T>, ErrorResponse> endpoint =
                (JsonEndpoint<MgetRequest, MgetResponse<T>, ErrorResponse>) MgetRequest._ENDPOINT;

        endpoint = new EndpointWithResponseMapperAttr<>(endpoint,
                                                        "co.elastic.clients:Deserializer:_global.mget.Response.TDocument",
                                                        getDeserializer(type));

        MgetRequest.Builder builder = new MgetRequest.Builder();

        builder.index(indexName).ids(ids);
        if (builderConsumer != null) {
            builderConsumer.accept(builder);
        }

        //noinspection resource
        return esAsyncClient._transport()
                            .performRequestAsync(builder.build(),
                                                 endpoint,
                                                 esAsyncClient._transportOptions())
                            .thenApply(response -> {

                                List<MultiGetResponseItem<T>> recordsResponse = response.docs();
                                ArrayList<R> content = new ArrayList<>();

                                if(resultMapper != null) {
                                    for (MultiGetResponseItem<T> hit : recordsResponse) {
                                        if (hit.isResult() && hit.result().found()) {
                                            content.add(resultMapper.apply(hit.result()));
                                        }
                                    }
                                }else{
                                    for (MultiGetResponseItem<T> hit : recordsResponse) {
                                        if(hit.isResult() && hit.result().found()){
                                            //noinspection unchecked
                                            content.add((R)hit.result().source());
                                        }
                                    }
                                }
                                return content;
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
    public <T> CompletableFuture<Page<T>> search(String indexName,
                                                 Pageable pageable,
                                                 Class<T> type,
                                                 Consumer<SearchRequest.Builder> builderConsumer){
        return search(indexName, pageable, type, builderConsumer, null);
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
     * @param hitMapper       to map the {@link Hit} to the desired type or null if the source should be returned directly
     * @return a {@link CompletableFuture} that will complete with a {@link Page} of documents
     */
    public <T,R> CompletableFuture<Page<R>> search(String indexName,
                                                   Pageable pageable,
                                                   Class<T> type,
                                                   Consumer<SearchRequest.Builder> builderConsumer,
                                                   Function<Hit<T>, R> hitMapper) {

        return searchFullResponse(indexName, pageable, type, builderConsumer)
                .thenApply(response -> {

                    HitsMetadata<T> hitsMetadata = response.hits();
                    List<R> content = new ArrayList<>(hitsMetadata.hits().size());
                    List<FieldValue> lastSort = null;

                    if(hitMapper != null) {
                        for (Hit<T> hit : hitsMetadata.hits()) {
                            content.add(hitMapper.apply(hit));
                            lastSort = hit.sort();
                        }
                    }else {
                        for (Hit<T> hit : hitsMetadata.hits()) {
                            //noinspection unchecked
                            content.add((R)hit.source());
                            lastSort = hit.sort();
                        }
                    }

                    if(pageable instanceof CursorPageable) {
                        String cursor = null;
                        if (lastSort != null) {
                            try {
                                cursor = objectMapper.writeValueAsString(lastSort);
                            } catch(JsonProcessingException e){
                                throw new IllegalStateException("Sort Array could not be serialized to JSON", e);
                            }
                        }
                        return new CursorPage<>(content,
                                                cursor,
                                                null);
                    }else{
                        return new Page<>(content,
                                          Objects.requireNonNull(hitsMetadata.total(),
                                                                 "System Error total hits not available")
                                                 .value());
                    }
                });
    }

    /**
     * Provides base functionality to get a {@link Page} of documents from elasticsearch. With the ability to customize the {@link SearchRequest}.
     *
     * @param indexName       name of the index to search
     * @param pageable        to use for the search
     * @param type            of the documents to return
     * @param builderConsumer to customize the {@link SearchRequest}, or null if no customization is needed
     * @return a {@link CompletableFuture} that will complete with a {@link SearchResponse} of documents
     */
    private <T> CompletableFuture<SearchResponse<T>> searchFullResponse(String indexName,
                                                                        Pageable pageable,
                                                                        Class<T> type,
                                                                        Consumer<SearchRequest.Builder> builderConsumer) {

        Validate.notNull(indexName, "indexName cannot be null");
        Validate.notNull(pageable, "pageable cannot be null");

        @SuppressWarnings("unchecked")
        JsonEndpoint<SearchRequest, SearchResponse<T>, ErrorResponse> endpoint =
                (JsonEndpoint<SearchRequest, SearchResponse<T>, ErrorResponse>) SearchRequest._ENDPOINT;
        endpoint = new EndpointWithResponseMapperAttr<>(endpoint,
                                                        "co.elastic.clients:Deserializer:_global.search.Response.TDocument",
                                                        getDeserializer(type));

        SearchRequest.Builder builder = new SearchRequest.Builder();

        builder.index(indexName)
               .size(pageable.getPageSize());

        if(pageable instanceof OffsetPageable){

            builder.from(((OffsetPageable)pageable).getPageNumber() * pageable.getPageSize())
                   .trackTotalHits(t -> t.enabled(true));

        } else if (pageable instanceof CursorPageable){

            try {
                CursorPageable cursorPageable = (CursorPageable) pageable;
                if(pageable.getSort() == null || pageable.getSort().isUnsorted()){
                    throw new IllegalArgumentException("When using Cursor based paging you MUST provide a Sort value.");
                }

                String cursorJson = cursorPageable.getCursor();
                // this can be null or empty to indicate the first page
                if(cursorJson != null && !cursorJson.isEmpty()) {
                    TypeFactory typeFactory = objectMapper.getTypeFactory();
                    List<FieldValue> searchAfter = objectMapper.readValue(cursorJson,
                                                                          typeFactory.constructCollectionType(List.class,
                                                                                                              FieldValue.class));
                    builder.searchAfter(searchAfter);
                }
            } catch (JsonProcessingException e) {
                throw new IllegalStateException("Cursor could not be deserialized", e);
            }

        } else {
            throw new IllegalArgumentException("Unsupported Pageable type: "+pageable.getClass().getName());
        }

        if(pageable.getSort() != null) {
            for (Order order : pageable.getSort()) {
                builder.sort(s -> s.field(f -> {
                    String property = order.getProperty();
                    FieldSort.Builder fieldSortBuilder
                            = f.field(property)
                               .order(order.isAscending() ? SortOrder.Asc : SortOrder.Desc);

                    // This is a nested sort, so we must set an additional field
                    // TODO: This must only be applied if the property is a nested field, meaning it has the nested annotation.
//                    if (property.contains(".")) {
//                        String baseField = property.substring(0, property.lastIndexOf("."));
//                        fieldSortBuilder.nested(n -> n.path(baseField));
//                    }

                    return fieldSortBuilder;
                }));
            }
        }

        if (builderConsumer != null) {
            builderConsumer.accept(builder);
        }

        SearchRequest request = builder.build();

        if(log.isTraceEnabled()) {
            // wrapped, so toString() will not be called if trace is not enabled
            log.trace("Query: \n {}", request.toString());
        }

        //noinspection resource
        return esAsyncClient._transport()
                            .performRequestAsync(request, endpoint, esAsyncClient._transportOptions());
    }

    private <T> JsonpDeserializer<T> getDeserializer(Class<T> type) {
        if (RawJson.class.isAssignableFrom(type)) {
            //noinspection unchecked
            return (JsonpDeserializer<T>) rawJsonJsonpDeserializer;
        }

        // Try the built-in deserializers first to avoid repeated lookups in the Jsonp mapper for client-defined classes
        JsonpDeserializer<T> result = JsonpMapperBase.findDeserializer(type);
        if (result != null) {
            return result;
        }

        return JsonpDeserializer.of(type);
    }

    CompletableFuture<Void> updateIndexMapping(String indexName,
                                               Consumer<PutMappingRequest.Builder> builderConsumer){
        return esAsyncClient.indices().exists(builder -> builder.index(indexName))
                            .thenCompose(exists -> {
                                if (exists.value()) {
                                    return esAsyncClient.indices()
                                                        .putMapping(builder -> {
                                                            builder.index(indexName);
                                                            if (builderConsumer != null) {
                                                                builderConsumer.accept(builder);
                                                            }
                                                            return builder;
                                                        })
                                                        .thenApply(response -> null);
                                } else {
                                    return CompletableFuture.failedFuture(new IllegalArgumentException(
                                            "Index "+indexName+" does not exist"));
                                }
                            });
    }

}
