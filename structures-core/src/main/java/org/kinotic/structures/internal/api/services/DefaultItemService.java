/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.structures.internal.api.services;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.domain.TypeCheckMap;
import org.kinotic.structures.api.domain.traitlifecycle.*;
import org.kinotic.structures.api.services.ItemService;
import org.kinotic.structures.api.services.StructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

@Component
public class DefaultItemService implements ItemService {

    private static final Logger log = LoggerFactory.getLogger(DefaultItemService.class);

    private RestHighLevelClient highLevelClient;
    private StructureService structureService;
    private List<TraitLifecycle> traitLifecycles;

    private HashMap<String, TraitLifecycle> traitLifecycleMap = new HashMap<>();

    private ConcurrentHashMap<String, BulkProcessor> bulkRequests = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, AtomicLong> activeBulkRequests = new ConcurrentHashMap<>();

    public DefaultItemService(RestHighLevelClient highLevelClient, StructureService structureService, List<TraitLifecycle> traitLifecycles) {
        this.highLevelClient = highLevelClient;
        this.structureService = structureService;
        this.traitLifecycles = traitLifecycles;
    }

    @PostConstruct
    public void init() {

        for(TraitLifecycle hook : traitLifecycles){
            traitLifecycleMap.put(hook.getClass().getSimpleName(), hook);
        }

    }

    @PreDestroy
    void cleanup() {
        // if we have any outstanding bulk requests, flush and close them.
        for(Map.Entry<String, BulkProcessor> entry : bulkRequests.entrySet()){
            try {
                BulkProcessor processor = this.bulkRequests.remove(entry.getKey());
                processor.awaitClose(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.warn("Encountered an error when trying to flush/close bulk update.",e);
            }
        }
    }

    @Override
    public TypeCheckMap upsertItem(String structureId, TypeCheckMap item) throws Exception {
        Optional<Structure> optional = structureService.getStructureById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        final Structure structure = optional.get();// will throw null pointer/element not available
        if (!structure.isPublished()) {
            throw new IllegalStateException("\'" + structure.getId() + "\' Structure is not published and cannot have had Items modified for it");
        }

        // ensure required fields are present, system managed fields are automatically processed by hooks; so don't require them
        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
            if (!traitEntry.getValue().isSystemManaged() && traitEntry.getValue().isRequired() && !item.has(traitEntry.getKey())) {
                throw new IllegalStateException("\'" + structure.getId() + "\' Structure create/modify has been called without all required fields");
            }
        }

        // perform before create/update hooks - id is created if it does not already exist
        TypeCheckMap toUpsert = (TypeCheckMap) processLifecycle(item, structure, (hook, obj, fieldName) -> {
            if(hook instanceof HasOnBeforeModify){
                obj = ((HasOnBeforeModify)hook).beforeModify((TypeCheckMap) obj, structure, fieldName);
            }
            return obj;
        });

        // process upsert
        processUpdateRequest(structure, toUpsert, true);

        // get value fresh from db
        //noinspection OptionalGetWithoutIsPresent
        TypeCheckMap ret = getItemById(structureId, toUpsert.getString("id")).get();
        return (TypeCheckMap) processLifecycle(ret, structure, (hook, obj, fieldName) -> {
            if(hook instanceof HasOnAfterModify){
                obj = ((HasOnAfterModify)hook).afterModify((TypeCheckMap) obj, structure, fieldName);
            }
            return obj;
        });
    }

    @Override
    public void requestBulkUpdatesForStructure(Structure structure) {
        if(!this.bulkRequests.containsKey(structure.getId())){
            BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer =
                    (request, bulkListener) -> highLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
            BulkProcessor bulkProcessor = BulkProcessor.builder(bulkConsumer, new BulkProcessor.Listener() {
                        private AtomicLong count = new AtomicLong(0);
                        @Override
                        public void beforeBulk(long executionId,
                                               BulkRequest request) {

                        }

                        @Override
                        public void afterBulk(long executionId,
                                              BulkRequest request,
                                              BulkResponse response) {
                            if(response.hasFailures()){
                                for(BulkItemResponse itemResponse: response.getItems()){
                                    log.error("DefaultItemService: Encountered an error while ingesting data.  for Structure: '" + structure.getId() + "'    Index: " + itemResponse.getIndex() + " \n\r    "+itemResponse.getFailureMessage(), itemResponse.getFailure());
                                }
                            }

                            long currentCount = count.addAndGet(request.numberOfActions());
                            log.debug("DefaultItemService: bulk processing for Structure '" + structure.getId() + "' finished indexing : " + currentCount);
                        }

                        @Override
                        public void afterBulk(long executionId,
                                              BulkRequest request,
                                              Throwable failure) {
                            log.error("DefaultItemService: Bulk Ingestion encountered an error. ", failure);
                        }
                    })
                    .setFlushInterval(TimeValue.timeValueSeconds(60))
                    .setBulkActions(2500)
                    .build();

            this.bulkRequests.put(structure.getId(), bulkProcessor);
            this.activeBulkRequests.put(structure.getId(), new AtomicLong(1));
        }else{
            AtomicLong number = this.activeBulkRequests.get(structure.getId());
            number.addAndGet(1);
            this.activeBulkRequests.put(structure.getId(), number);
        }
    }

    @Override
    public void pushItemForBulkUpdate(Structure structure, TypeCheckMap item) throws Exception {
        Assert.notNull(structure, "Must provide valid structure");
        Assert.isTrue(this.bulkRequests.containsKey(structure.getId()), "Your structure not set up for bulk processing, please request new bulk updates for structure");

        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
            if (!traitEntry.getValue().isSystemManaged() && traitEntry.getValue().isRequired() && !item.has(traitEntry.getKey())) {
                throw new IllegalStateException("\'" + structure.getId() + "\' Structure create/modify has been called without all required fields");
            }
        }

        TypeCheckMap ret = (TypeCheckMap) processLifecycle(item, structure, (hook, obj, fieldName) -> {
            if(hook instanceof HasOnBeforeModify){
                obj = ((HasOnBeforeModify)hook).beforeModify((TypeCheckMap) obj, structure, fieldName);
            }
            return obj;
        });

        UpdateRequest request = new UpdateRequest(structure.getId().toLowerCase(), item.getString("id"));
        request.docAsUpsert(true);
        request.doc(item, XContentType.JSON);

        this.bulkRequests.get(structure.getId()).add(request);

        ret = (TypeCheckMap) processLifecycle(ret, structure, (hook, obj, fieldName) -> {
            if(hook instanceof HasOnAfterModify){
                obj = ((HasOnAfterModify)hook).afterModify((TypeCheckMap) obj, structure, fieldName);
            }
            return obj;
        });

    }

    @Override
    public void flushAndCloseBulkUpdate(Structure structure) throws Exception {
        Assert.notNull(structure, "Must provide structure. ");
        Assert.isTrue(this.bulkRequests.containsKey(structure.getId()), "Your structure not set up for bulk processing, please request new bulk update for structure.");
        if(this.activeBulkRequests.get(structure.getId()).get() == 1){
            this.bulkRequests.get(structure.getId()).awaitClose(30, TimeUnit.SECONDS);
            this.bulkRequests.remove(structure.getId());
            this.activeBulkRequests.remove(structure.getId());
        }else{
            // current bulk updates will continue to work, any items pushed by closing process
            // will be processed at the next threshold or interval
            AtomicLong number = this.activeBulkRequests.get(structure.getId());
            number.addAndGet(-1);
            this.activeBulkRequests.put(structure.getId(), number);
        }
    }

    @Override
    public long count(String structureId) throws IOException {
        Optional<Structure> optional = structureService.getStructureById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery("deleted", false));

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(boolQueryBuilder);
        builder.size(0);
        SearchRequest request = new SearchRequest(structure.getId().toLowerCase());
        request.source(builder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

        return response.getHits().getTotalHits().value;
    }

    @Override
    public Optional<TypeCheckMap> getById(Structure structure, String id) throws Exception {
        GetResponse response = highLevelClient.get(new GetRequest(structure.getId().toLowerCase()).id(id), RequestOptions.DEFAULT);
        TypeCheckMap ret = null;
        if (response.isExists()) {
            ret = new TypeCheckMap(response.getSourceAsMap());
            ret = (TypeCheckMap) processLifecycle(ret, structure, (hook, obj, fieldName) -> {
                if(hook instanceof HasOnAfterGet){
                    obj = ((HasOnAfterGet)hook).afterGet((TypeCheckMap) obj, structure, fieldName);
                }
                return obj;
            });
        }

        return Optional.ofNullable(ret);
    }

    /**
     *
     * This function will act ast the ObjectReference Resolver function.  The ObjectReference will already
     * have the structureName, which we use as the index name in ES.  This means we don't have to do a
     * $Structure lookup, just the item lookup.
     *
     */
    @Override
    public Optional<TypeCheckMap> getItemById(String structureId, String id) throws Exception {
        Optional<Structure> optional = structureService.getStructureById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        return getById(structure, id);
    }

    /**
     *
     * Below are the SearchHits functions, we do not attempt any reference resolution b/c we want to
     * lazy load any references when the user decides they want to view a single item.  The JavaScript
     * side should be able to know when it needs to resolve a reference object, it will have a specific
     * structure to it.. please see ObjectReference trait lifecycle for more information on structure.
     *
     */
    @Override
    public SearchHits searchForItemsById(String structureId, String... ids) throws IOException {
        Optional<Structure> optional = structureService.getStructureById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(new IdsQueryBuilder().addIds(ids))
                .postFilter(QueryBuilders.termQuery("deleted", false));

        SearchRequest request = new SearchRequest(structureId.toLowerCase());
        request.source(builder);

        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

        return response.getHits();
    }

    @Override
    public SearchHits getAll(String structureId, int numberPerPage, int from) throws IOException {
        Optional<Structure> optional = structureService.getStructureById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery("deleted", false));

        SearchRequest request = new SearchRequest(structure.getId().toLowerCase());
        request.source(new SearchSourceBuilder()
                            .query(boolQueryBuilder)
                            .from(from*numberPerPage)
                            .size(numberPerPage));

        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

        return response.getHits();
    }


    /**
     *
     * Provides a terms search functionality, a keyword type search over provided fields.
     * <p>
     * https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-terms-query.html
     *
     */
    @Override
    public SearchHits searchTerms(String structureId, int numberPerPage, int from, String fieldName, Object... searchTerms) throws IOException {
        Optional<Structure> optional = structureService.getStructureById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termsQuery(fieldName, searchTerms)).filter(QueryBuilders.termQuery("deleted", false));

        SearchRequest request = new SearchRequest(structure.getId().toLowerCase());
        request.source(new SearchSourceBuilder()
                .query(boolQueryBuilder)
                .from(from*numberPerPage)
                .size(numberPerPage));

        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

        return response.getHits();
    }

    /**
     *
     * Provides a multisearch functionality, a full text search type.
     * <p>
     * https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-multi-match-query.html
     *
     */
    @Override
    public SearchHits searchFullText(String structureId, int numberPerPage, int from, String search, String... fieldNames) throws IOException {
        Optional<Structure> optional = structureService.getStructureById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery("deleted", false)).filter(QueryBuilders.multiMatchQuery(search, fieldNames));

        SearchRequest request = new SearchRequest(structure.getId().toLowerCase());
        request.source(new SearchSourceBuilder()
                .query(boolQueryBuilder)
                .from(from*numberPerPage)
                .size(numberPerPage));

        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

        return response.getHits();
    }

    /**
     *
     * Provides an option for expert level searching, using standard lucene query structure.
     * <p>
     * https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-query-string-query.html
     *
     */
    @Override
    public SearchHits search(String structureId, String search, int numberPerPage, int from) throws IOException {
        return search(structureId,search,numberPerPage,from, null, null);
    }

    @Override
    public SearchHits search(String structureId, String search, int numberPerPage, int from, String sortField, SortOrder sortOrder) throws IOException {
        Optional<Structure> optional = structureService.getStructureById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(new QueryStringQueryBuilder(search))
                .postFilter(QueryBuilders.termQuery("deleted", false))
                .from(from*numberPerPage)
                .size(numberPerPage);

        if(sortField != null){
            builder.sort(sortField, sortOrder);
        }

        SearchRequest request = new SearchRequest(structure.getId().toLowerCase());
        request.source(builder);

        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

        return response.getHits();
    }

    @Override
    public List<String> searchDistinct(String structureId, String search, String field, int limit) throws IOException {
        Optional<Structure> optional = structureService.getStructureById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        SearchRequest request = new SearchRequest(structure.getId().toLowerCase());
        request.source(new SearchSourceBuilder()
                .aggregation(AggregationBuilders.terms(field).field(field).size(500))
                .query(new QueryStringQueryBuilder(search))
                .postFilter(QueryBuilders.termQuery("deleted", false))
                .size(limit));

        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

        ArrayList<String> keys = new ArrayList<>();
        Terms byCoach = response.getAggregations().get(field);
        for(Terms.Bucket bucket : byCoach.getBuckets()){
            keys.add(bucket.getKeyAsString());
        }
        return keys;
    }

    @Override
    public void delete(String structureId, String itemId) throws Exception {
        Optional<Structure> optional = structureService.getStructureById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();

        TypeCheckMap item = new TypeCheckMap();
        item.amend("id", itemId);
        TypeCheckMap ret = (TypeCheckMap) processLifecycle(item, structure, (hook, obj, fieldName) -> {
            if(hook instanceof HasOnBeforeDelete){
                obj = ((HasOnBeforeDelete)hook).beforeDelete((TypeCheckMap) obj, structure, fieldName);
            }
            return obj;
        });

        processUpdateRequest(structure, ret, false);

        //TODO: find out how this will operate concurrently
        processLifecycle(ret, structure, (hook, obj, fieldName) -> {
            if(hook instanceof HasOnAfterDelete){
                obj = ((HasOnAfterDelete)hook).afterDelete((TypeCheckMap) obj, structure, fieldName);
            }
            return obj;
        });
    }

    public HashMap<String, TraitLifecycle> getTraitLifecycleMap(){
        return traitLifecycleMap;
    }

    private void processUpdateRequest(Structure structure, TypeCheckMap ret, boolean asUpsert) throws IOException {
        UpdateRequest request = new UpdateRequest(structure.getId().toLowerCase(), ret.getString("id"));
        request.docAsUpsert(asUpsert);
        request.doc(ret, XContentType.JSON);
        // forces a cluster refresh of the index.. for high volume data this wouldn't work - lets see how it works in our case.
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);

        highLevelClient.update(request, RequestOptions.DEFAULT);
    }
}
