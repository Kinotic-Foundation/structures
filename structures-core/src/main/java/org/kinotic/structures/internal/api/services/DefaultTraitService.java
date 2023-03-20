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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.PermenentTraitException;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.services.TraitService;
import org.kinotic.structures.internal.api.services.util.EsHighLevelClientUtil;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.elasticsearch.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

//need to just increment the version instead of using the long this way.
//so we should get the value and check against it.

@Component
public class DefaultTraitService implements TraitService {

    private final ObjectMapper mapper = new ObjectMapper();

    private RestHighLevelClient highLevelClient;

    public DefaultTraitService(RestHighLevelClient highLevelClient){
        this.highLevelClient = highLevelClient;
    }

    @PostConstruct
    void init() {
        createTraitIndex();
    }

    @Override
    public Trait save(Trait saveTrait) throws AlreadyExistsException, PermenentTraitException, IOException {
        if(saveTrait.getCreated() == 0){ // new trait, name must be unique
            Optional<Trait> alreadyCreated = getTraitByName(saveTrait.getName());
            if(alreadyCreated.isPresent()){
                throw new AlreadyExistsException("Trait name must be unique, name already exists.");
            }
            saveTrait.setId(UUID.randomUUID().toString());
            saveTrait.setCreated(System.currentTimeMillis());
            saveTrait.setUpdated(saveTrait.getCreated());
        }else{
            saveTrait.setUpdated(System.currentTimeMillis());
        }


        IndexRequest request = new IndexRequest("trait");
        request.id(saveTrait.getId());
        request.create(true);

        // forces a cluster refresh of the index.. for high volume data this wouldn't work - lets see how it works in our case.
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.field("id", saveTrait.getId());
        builder.field("name", saveTrait.getName());
        builder.field("describeTrait", saveTrait.getDescribeTrait());
        builder.field("schema", saveTrait.getSchema());
        builder.field("esSchema", saveTrait.getEsSchema());
        builder.field("created", saveTrait.getCreated());
        builder.field("required", saveTrait.isRequired());
        builder.field("updated", saveTrait.getUpdated());
        builder.field("systemManaged", saveTrait.isSystemManaged());
        builder.field("operational", saveTrait.isOperational());
        builder.endObject();

        request.source(builder);

        // FIXME: Need to handle exceptions and edge cases.
        highLevelClient.index(request, RequestOptions.DEFAULT);
        return saveTrait;
    }

    @Override
    public Optional<Trait> getTraitById(String id) throws IOException {

        GetResponse response = highLevelClient.get(new GetRequest("trait").id(id), RequestOptions.DEFAULT);
        Trait ret = null;
        if (response.isExists()) {
            ret = EsHighLevelClientUtil.getTypeFromBytesReference(response.getSourceAsBytesRef(), Trait.class);
        }

        return Optional.ofNullable(ret);
    }

    @Override
    public Optional<Trait> getTraitByName(String name) throws IOException {

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery("name", name));

        SearchRequest request = new SearchRequest("trait");
        request.source(new SearchSourceBuilder()
                .query(boolQueryBuilder)
                .from(0)
                .size(10000));

        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

        Trait ret = null;
        if(response.getHits().getTotalHits().value != 0){
            ret = EsHighLevelClientUtil.getTypeFromBytesReference(response.getHits().getHits()[0].getSourceRef(), Trait.class);
        }

        return Optional.ofNullable(ret);
    }

    @Override
    public SearchHits getAll(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
        SearchSourceBuilder builder = EsHighLevelClientUtil.buildGeneric(numberPerPage,page,columnToSortBy,descending);
        return getTraits(builder);
    }

    @Override
    public SearchHits getAllNameLike(String nameLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
        SearchSourceBuilder builder = EsHighLevelClientUtil.buildGeneric(numberPerPage,page,columnToSortBy,descending);
        builder.postFilter(QueryBuilders.wildcardQuery("name", nameLike));
        return getTraits(builder);
    }

    @Override
    public void delete(String traitId) throws PermenentTraitException, IOException {

        Optional<Trait> traitOptional = getTraitById(traitId);

        if(traitOptional.isPresent()){
            Trait toBeDeleted = traitOptional.get();
            if(toBeDeleted.isSystemManaged()){
                throw new PermenentTraitException("Trait that was requested to be deleted is a System Managed Trait - cannot delete.");
            }

            DeleteRequest request = new DeleteRequest("trait");
            request.id(traitId);
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);

            // FIXME: Need to handle exceptions and edge cases.
            highLevelClient.delete(request, RequestOptions.DEFAULT);
        }else{
            // what to do here? if anything.
        }

    }

    @Override
    public void createTraitIndex() {
        try {
            if(!highLevelClient.indices().exists(new GetIndexRequest("trait"), RequestOptions.DEFAULT)){
                HashMap<String, Object> settings = new HashMap<>();
                settings.put("index.number_of_shards", 5);
                settings.put("index.number_of_replicas", 2);
                settings.put("index.refresh_interval", "1s");
                settings.put("index.store.type", "fs");

                CreateIndexRequest indexRequest = new CreateIndexRequest("trait");
                indexRequest.mapping("{ \"dynamic\": \"strict\", \"properties\":{\"created\":{\"type\":\"date\",\"format\":\"epoch_millis\"},\"describeTrait\":{\"type\":\"text\"},\"esSchema\":{\"type\":\"text\"},\"id\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"includeInLabel\":{\"type\":\"boolean\"},\"includeInQRCode\":{\"type\":\"boolean\"},\"name\":{\"type\":\"keyword\"},\"operational\":{\"type\":\"boolean\"},\"required\":{\"type\":\"boolean\"},\"schema\":{\"type\":\"text\"},\"systemManaged\":{\"type\":\"boolean\"},\"updated\":{\"type\":\"date\",\"format\":\"epoch_millis\"}}}}", XContentType.JSON);
                indexRequest.settings(settings);
                highLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);
            }
        } catch (Exception e) {
            throw new IllegalStateException("We were not able to check for 'trait' existence or create 'trait' index.", e);
        }
    }

    private SearchHits getTraits(SearchSourceBuilder builder) throws IOException {
        SearchResponse response = highLevelClient.search(new SearchRequest("trait").source(builder), RequestOptions.DEFAULT);
        return response.getHits();
    }
}
