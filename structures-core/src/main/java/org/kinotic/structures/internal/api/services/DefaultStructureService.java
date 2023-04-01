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

import net.logstash.logback.encoder.org.apache.commons.lang.WordUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.kinotic.structures.api.domain.*;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.api.services.TraitService;
import org.kinotic.structures.internal.api.services.util.EsHighLevelClientUtil;
import org.kinotic.structures.internal.api.services.util.StructureHelper;
import org.kinotic.structures.internal.config.StructuresProperties;
import org.kinotic.structures.internal.repositories.StructureElasticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Component
public class DefaultStructureService implements StructureService, StructureServiceInternal { // TODO: after continuum fix remove StructureService

    private static final Logger log = LoggerFactory.getLogger(DefaultStructureService.class);

    private RestHighLevelClient highLevelClient;

    private TraitService traitService;

    private StructureElasticRepository structureElasticRepository;
    private StructuresProperties structuresProperties;

    private Trait id;
    private Trait deleted;
    private Trait deletedTime;
    private Trait updatedTime;
    private Trait structureId;
    public DefaultStructureService(RestHighLevelClient highLevelClient,
                                   TraitService traitService,
                                   StructureElasticRepository structureElasticRepository,
                                   StructuresProperties structuresProperties){
        this.highLevelClient = highLevelClient;
        this.traitService = traitService;
        this.structureElasticRepository = structureElasticRepository;
        this.structuresProperties = structuresProperties;
    }

    @PostConstruct
    void init(){
        try {

            // need to make sure we have created the trait index before booting.
            traitService.createTraitIndex();

            /**
             * These are system type traits that cannot be modified or deleted, default fields for all types/personalities.
             */

            Optional<Trait> idOptional = traitService.getTraitByName("Id");
            if(idOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("Id");
                temp.setDescribeTrait("String field that defines the ID given to it in ElasticSearch.");
                temp.setSchema("{ \"type\": \"string\" }");
                temp.setEsSchema("{ \"type\": \"keyword\" }");
                this.id = traitService.save(temp);
            }else{
                this.id = idOptional.get();
            }
            Optional<Trait> deletedOptional = traitService.getTraitByName("Deleted");
            if(deletedOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("Deleted");
                temp.setDescribeTrait("Boolean field that says if an item is deleted or not.");
                temp.setSchema("{ \"type\": \"boolean\" }");
                temp.setEsSchema("{ \"type\": \"boolean\" }");
                temp.setRequired(true);
                temp.setSystemManaged(true);
                this.deleted = traitService.save(temp);
            }else{
                this.deleted = deletedOptional.get();
            }
            Optional<Trait> deletedTimeOptional = traitService.getTraitByName("DeletedTime");
            if(deletedTimeOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("DeletedTime");
                temp.setDescribeTrait("A long field that indicates the timestamp of when the item was deleted.");
                temp.setSchema("{ \"type\": \"date\", \"format\": { \"style\": \"unix\" } }");
                temp.setEsSchema("{ \"type\": \"date\", \"format\": \"epoch_millis\" }");
                temp.setRequired(true);
                temp.setSystemManaged(true);
                this.deletedTime = traitService.save(temp);
            }else{
                this.deletedTime = deletedTimeOptional.get();
            }
            Optional<Trait> updatedTimeOptional = traitService.getTraitByName("UpdatedTime");
            if(updatedTimeOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("UpdatedTime");
                temp.setDescribeTrait("A long field that indicates the timestamp of when the item was last updated.");
                temp.setSchema("{ \"type\": \"date\", \"format\": { \"style\": \"unix\" } }");
                temp.setEsSchema("{ \"type\": \"date\", \"format\": \"epoch_millis\" }");
                temp.setRequired(true);
                temp.setSystemManaged(true);
                this.updatedTime = traitService.save(temp);
            }else{
                this.updatedTime = updatedTimeOptional.get();
            }
            Optional<Trait> structureIdOptional = traitService.getTraitByName("StructureId");
            if(structureIdOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("StructureId");
                temp.setDescribeTrait("String field that provides the Structure ID that the item belongs to.");
                temp.setSchema("{ \"type\": \"string\" }");
                temp.setEsSchema("{ \"type\": \"keyword\" }");
                temp.setRequired(true);
                temp.setSystemManaged(true);
                this.structureId = traitService.save(temp);
            }else{
                this.structureId = structureIdOptional.get();
            }

            /**
             * Below are system traits that do not make it to every Structure, but can be added to any structure.
             * You cannot modify these traits at any point in time nor can you change the actual values after setting.
             */

            Optional<Trait> macOptional = traitService.getTraitByName("Mac");
            if(macOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("Mac");
                temp.setDescribeTrait("Hardware Mac address (unique key) that is associated with primary ethernet on LAN.");
                temp.setSchema("{ \"type\": \"string\", \"minLength\": 12, \"maxLength\": 12, \"pattern\": \"^[0-9A-Fa-f]{12}$\" }");
                temp.setEsSchema("{ \"type\": \"keyword\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }

            /**
             * Below are generic fields that provide some quick access. They can be modified within the Structure frontend.
             */

            Optional<Trait> createdTimeOptional = traitService.getTraitByName("CreatedTime");
            if(createdTimeOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("CreatedTime");
                temp.setDescribeTrait("A long field that indicates the timestamp of when the item was created.");
                temp.setSchema("{ \"type\": \"date\", \"format\": { \"style\": \"unix\" } }");
                temp.setEsSchema("{ \"type\": \"date\", \"format\": \"epoch_millis\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
            Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
            if(ipOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("Ip");
                temp.setDescribeTrait("IP address that the devices should be provided on the LAN.");
                temp.setSchema("{ \"type\": \"string\", \"format\": \"ipv4\" }");
                temp.setEsSchema("{ \"type\": \"ip\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
            Optional<Trait> keywordStringOptional = traitService.getTraitByName("KeywordString");
            if(keywordStringOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("KeywordString");
                temp.setDescribeTrait("Generic String that is structured content such as email addresses, hostnames, status codes, zip codes or tags.");
                temp.setSchema("{ \"type\": \"string\" }");
                temp.setEsSchema("{ \"type\": \"keyword\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
            Optional<Trait> geoPointOptional = traitService.getTraitByName("GeoPoint");
            if(geoPointOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("GeoPoint");
                temp.setDescribeTrait("References a geo point type. Please see https://www.elastic.co/guide/en/elasticsearch/reference/current/geo-point.html");
                temp.setSchema("{\"type\": \"object\", \"required\": [\"lat\",\"lon\"],\"properties\": {\"lat\": {type\": \"number\"},\"lon\": {\"type\": \"number\"}}}");
                temp.setEsSchema("{ \"type\": \"geo_point\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
            Optional<Trait> textStringOptional = traitService.getTraitByName("TextString");
            if(textStringOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("TextString");
                temp.setDescribeTrait("Generic String that is full-text values, such as the body of an email or the description of a product.");
                temp.setSchema("{ \"type\": \"string\", \"description\": \"no-sort\" }");
                temp.setEsSchema("{ \"type\": \"text\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
            Optional<Trait> dateOptional = traitService.getTraitByName("Date-EpochMillis");
            if(dateOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("Date-EpochMillis");
                temp.setDescribeTrait("Generic Date field that is configured for search as time using Epoch time in milliseconds.");
                temp.setSchema("{ \"type\": \"date\", \"format\": { \"style\": \"unix\" } }");
                temp.setEsSchema("{ \"type\": \"date\", \"format\": \"epoch_millis\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
            Optional<Trait> longOptional = traitService.getTraitByName("Long");
            if(longOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("Long");
                temp.setDescribeTrait("Generic Long field.");
                temp.setSchema("{ \"type\": \"number\", \"minimum\": "+Long.MIN_VALUE+", \"maximum\": "+Long.MAX_VALUE+" }");
                temp.setEsSchema("{ \"type\": \"long\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
            Optional<Trait> integerOptional = traitService.getTraitByName("Integer");
            if(integerOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("Integer");
                temp.setDescribeTrait("Generic Integer field.");
                temp.setSchema("{ \"type\": \"number\", \"minimum\": "+Integer.MIN_VALUE+", \"maximum\": "+Integer.MAX_VALUE+" }");
                temp.setEsSchema("{ \"type\": \"integer\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
            Optional<Trait> shortOptional = traitService.getTraitByName("Short");
            if(shortOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("Short");
                temp.setDescribeTrait("Generic Short field.");
                temp.setSchema("{ \"type\": \"number\", \"minimum\": "+Short.MIN_VALUE+", \"maximum\": "+Short.MAX_VALUE+" }");
                temp.setEsSchema("{ \"type\": \"short\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
            Optional<Trait> doubleOptional = traitService.getTraitByName("Double");
            if(doubleOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("Double");
                temp.setDescribeTrait("Generic Double field.");
                temp.setSchema("{ \"type\": \"number\", \"minimum\": "+Double.MIN_VALUE+", \"maximum\": "+Double.MAX_VALUE+" }");
                temp.setEsSchema("{ \"type\": \"double\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
            Optional<Trait> booleanOptional = traitService.getTraitByName("Boolean");
            if(booleanOptional.isEmpty()){
                Trait temp = new Trait();
                temp.setName("Boolean");
                temp.setDescribeTrait("Generic Boolean field.");
                temp.setSchema("{ \"type\": \"boolean\" }");
                temp.setEsSchema("{ \"type\": \"boolean\" }");
                temp.setRequired(true);
                traitService.save(temp);
            }
        }catch (Exception e){
            log.error("StructureService encountered an error trying to load Traits.", e);
        }
    }


    @Override
    public Structure save(Structure structure) throws AlreadyExistsException, IOException {

        if(structure.getName() == null || structure.getName().isBlank()){
            throw new IllegalArgumentException("Structures must provide proper Structure Name.");
        }
        if(structure.getNamespace() == null || structure.getNamespace().isBlank()){
            throw new IllegalArgumentException("Structures must provide proper Structure Namespace.");
        }

        String logicalIndexName = (structure.getNamespace().trim()+structure.getName().trim()).toLowerCase();

        // will throw an exception if invalid
        StructureHelper.indexNameValidation(logicalIndexName);

        // check to ensure we either have this unique index or not
        Optional<Structure> alreadyCreated = structureElasticRepository.findById(logicalIndexName);

        // ensure we are not trying to do something odd here, if you are updating we must have the same
        // version(updated field) from the request as we have in the database.
        if(alreadyCreated.isPresent() && !Objects.equals(alreadyCreated.get().getUpdated(), structure.getUpdated())){
            if(structure.getCreated() == 0 && structure.getUpdated() == 0 && (structure.getId() == null || structure.getId().isBlank())){
                // new structure request, but we already have a structure by the same id
                // structures must have globally unique ID's.
                throw new AlreadyExistsException("Structure Namespace+Name must be unique, '"+structure.getId()+"' already exists.");
            }else{
                throw new OptimisticLockingFailureException("Attempting to update a Structure, but out of sync with database; please re-fetch from database and try again");
            }
        }else if(alreadyCreated.isEmpty()){
            // new structure
            structure.setCreated(System.currentTimeMillis());
            structure.setUpdated(structure.getCreated());
            // Store name of the elastic search index for items
            structure.setItemIndex(this.structuresProperties.getIndexPrefix().trim().toLowerCase()+logicalIndexName);
        }else{
            // version type field - updating structure
            structure.setUpdated(System.currentTimeMillis());
        }

        for(Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()){
            checkFieldNameFormat(traitEntry.getKey());
        }

        // defaults - we add our defaults in a ordered way (our tests depend on that when testing reordering)

        // id is not a "system managed" trait anymore
        if(!structure.getTraits().containsKey("id")){
            structure.getTraits().put("id", this.id);
        }
        if(!structure.getTraits().containsKey("deleted")){
            structure.getTraits().put("deleted", this.deleted);
        }
        if(!structure.getTraits().containsKey("deletedTime")){
            structure.getTraits().put("deletedTime", this.deletedTime);
        }
        if(!structure.getTraits().containsKey("updatedTime")){
            structure.getTraits().put("updatedTime", this.updatedTime);
        }
        if(!structure.getTraits().containsKey("structureId")){
            structure.getTraits().put("structureId", this.structureId);
        }

        // now allow any user defined default traits to be added
        ArrayList<Trait> defaultTraits = new ArrayList<>(this.traitService.getAllSystemManaged());
        for(Trait trait : defaultTraits){
            boolean hasTrait = false;
            for(Map.Entry<String, Trait> entry : structure.getTraits().entrySet()){
                if(entry.getKey().equalsIgnoreCase(trait.getName())){
                    hasTrait = true;
                    break;
                }
            }
            if(!hasTrait){
                structure.getTraits().put(WordUtils.uncapitalize(trait.getName().trim()), trait);
            }
        }

        Structure ret;
        if(alreadyCreated.isPresent() && alreadyCreated.get().isPublished()){
            // can only update 'description' and 'metadata' after publishing, we know we have already saved by this time
            alreadyCreated.get().setDescription(structure.getDescription());
            alreadyCreated.get().setMetadata(structure.getMetadata());
            alreadyCreated.get().setUpdated(System.currentTimeMillis());
            ret = structureElasticRepository.save(alreadyCreated.get());
        }else{
            // can update everything until published
            structure.setId(logicalIndexName);
            ret = structureElasticRepository.save(structure);
        }

        return ret;
    }

    @Override
    public Optional<Structure> getById(String id) throws IOException {
        GetResponse response = highLevelClient.get(new GetRequest("structure").id(id.toLowerCase()), RequestOptions.DEFAULT);
        Structure ret = null;
        if (response.isExists()) {
            ret = EsHighLevelClientUtil.getTypeFromBytesReference(response.getSourceAsBytesRef(), Structure.class);
        }
        return Optional.ofNullable(ret);
    }

    @Override
    public StructureHolder save(StructureHolder structureHolder) throws AlreadyExistsException, IOException {
        LinkedHashMap<String, Trait> traits = new LinkedHashMap<>();
        structureHolder.getTraits().sort((o1, o2) -> o1.getOrder() - o2.getOrder());

        for(TraitHolder holder : structureHolder.getTraits()){
            traits.put(holder.getFieldName(), holder.getFieldTrait());
        }

        structureHolder.getStructure().setTraits(traits);

        return new StructureHolder(this.save(structureHolder.getStructure()), structureHolder.getTraits());
    }

    @Override
    public StructureHolder getStructureById(String id) throws IOException {
        Structure structure = this.getById(id).get();
        LinkedList<TraitHolder> traits = new LinkedList<>();
        int index = 0;
        for(Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()){
            traits.add(new TraitHolder(index, traitEntry.getKey(), traitEntry.getValue()));
            index++;
        }
        return new StructureHolder(structure, traits);
    }

    @Override
    public Structures getAll(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
        return getStructures(EsHighLevelClientUtil.buildGeneric(numberPerPage,page,columnToSortBy,descending));
    }

    @Override
    public Structures getAllIdLike(String idLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
        SearchSourceBuilder builder = EsHighLevelClientUtil.buildGeneric(numberPerPage,page,columnToSortBy,descending);
        builder.postFilter(QueryBuilders.wildcardQuery("id", idLike));
        return getStructures(builder);
    }

    @Override
    public Structures getAllPublishedAndIdLike(String idLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
        SearchSourceBuilder builder = EsHighLevelClientUtil.buildGeneric(numberPerPage,page,columnToSortBy,descending);
        builder.query(QueryBuilders.termQuery("published", true));
        builder.postFilter(QueryBuilders.wildcardQuery("id", idLike));
        return getStructures(builder);
    }

    @Override
    public Structures getAllNamespaceEquals(String namespace, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
        SearchSourceBuilder builder = EsHighLevelClientUtil.buildGeneric(numberPerPage, page, columnToSortBy, descending);
        builder.postFilter(QueryBuilders.termQuery("namespace", namespace));
        return getStructures(builder);
    }

    @Override
    public Structures getAllPublishedForNamespace(String namespace, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
        SearchSourceBuilder builder = EsHighLevelClientUtil.buildGeneric(numberPerPage, page, columnToSortBy, descending);
        builder.query(QueryBuilders.termQuery("published", true));
        builder.postFilter(QueryBuilders.termQuery("namespace", namespace));
        return getStructures(builder);
    }

    @Override
    public Structures getAllPublished(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
        SearchSourceBuilder builder = EsHighLevelClientUtil.buildGeneric(numberPerPage,page,columnToSortBy,descending);
        builder.query(QueryBuilders.termQuery("published", true));
        return getStructures(builder);
    }

    @Override
    public void delete(String structureId) throws IOException, PermenentTraitException {
        Optional<Structure> optional = getById(structureId);
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        if(structure.isPublished()){
            // if its published we should check to see if we can remove the
            // ElasticSearch index, but only if there are not any items created
            if(highLevelClient.indices().exists(new GetIndexRequest(structure.getItemIndex()), RequestOptions.DEFAULT)){
                long countOfItemsForStructure = this.count(structure.getItemIndex());

                if(countOfItemsForStructure > 0){
                    throw new IllegalStateException("you cannot delete a Structure until all Items associated are also deleted.");
                }
                DeleteIndexRequest request = new DeleteIndexRequest(structure.getItemIndex());
                AcknowledgedResponse response = highLevelClient.indices().delete(request, RequestOptions.DEFAULT);
                if(!response.isAcknowledged()){
                    response = highLevelClient.indices().delete(request, RequestOptions.DEFAULT);
                    if(!response.isAcknowledged()){
                        throw new IllegalStateException("We were not able to delete requested index, please review and try again.");
                    }
                }
            }

            // remove Object Reference Trait that was created when published.
            // if there are not more items then there can be no more reference logs
            // so we are good cleaning up here.
//            Optional<Trait> objectIdOptional = traitService.getTraitByName("Reference "+structure.getId().trim());
//            Trait objRefTrait = objectIdOptional.get();
//
//            DeleteRequest request = new DeleteRequest("trait");
//            request.id(objRefTrait.getId());
//            request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
//            // FIXME: Need to handle exceptions and edge cases.
//            highLevelClient.delete(request, RequestOptions.DEFAULT);

        }

        structureElasticRepository.delete(structure);
    }

    @Override
    public void publish(String structureId) throws IOException {
        Optional<Structure> optional = getById(structureId.toLowerCase());
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        if(!structure.isPublished()){
            // this is when we create the index
            HashMap<String, Object> settings = new HashMap<>();
            settings.put("index.number_of_shards", 5);
            settings.put("index.number_of_replicas", 2);
            settings.put("index.refresh_interval", "1s");

            /**
             * fs
             * Default file system implementation. This will pick the best implementation depending on the operating environment, which is currently mmapfs on all supported systems but is subject to change.
             * simplefs
             * The Simple FS type is a straightforward implementation of file system storage (maps to Lucene SimpleFsDirectory) using a random access file. This implementation has poor concurrent performance (multiple threads will bottleneck). It is usually better to use the niofs when you need index persistence.
             * niofs
             * The NIO FS type stores the shard index on the file system (maps to Lucene NIOFSDirectory) using NIO. It allows multiple threads to read from the same file concurrently. It is not recommended on Windows because of a bug in the SUN Java implementation.
             * mmapfs
             * The MMap FS type stores the shard index on the file system (maps to Lucene MMapDirectory) by mapping a file into memory (mmap). Memory mapping uses up a portion of the virtual memory address space in your process equal to the size of the file being mapped. Before using this class, be sure you have allowed plenty of virtual address space.
             * https://www.elastic.co/guide/en/elasticsearch/reference/6.4/vm-max-map-count.html
             */
            settings.put("index.store.type", "fs");

            // Item ES Index
            CreateIndexRequest indexRequest = new CreateIndexRequest(structure.getItemIndex());
            String mapping = getElasticSearchBaseMapping(structure);
            indexRequest.mapping(mapping, XContentType.JSON);
            indexRequest.settings(settings);
            highLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);

            structure.setPublished(true);
            structure.setPublishedTimestamp(System.currentTimeMillis());
            // version type field
            structure.setUpdated(structure.getPublishedTimestamp());
            structureElasticRepository.save(structure);


            // we can check that we have an object reference for it, if not create it.
            // we do this here b/c we only ever want to create it once per structure,
            // also we cannot ever query or actually select an structure for object references
            // until you can save some items.. duh.
//            Optional<Trait> objectIdOptional = traitService.getTraitByName("Reference "+structure.getId().trim());
//            if(objectIdOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Reference "+structure.getId().trim());
//                temp.setDescribeTrait("Stores a '"+structure.getId().trim()+"' object reference that will be retrieved when building the item.");
//                temp.setSchema("{ \"type\": \"ref\", \"urn\": \""+structure.getId().trim()+"\" }");
//                temp.setEsSchema("{ \"properties\": { \"structureId\":  { \"type\": \"keyword\" }, \"id\":  { \"type\": \"keyword\" } } }");
//                temp.setRequired(true);
//                temp.setModifiable(false);// only system can manage
//                try {
//                    traitService.save(temp);
//                } catch (AlreadyExistsException e) {
//                    log.error("For some reason the system attempted to create an already created Object Trait. We caught a AlreadyExistsException when trying to save an ObjectReference for new Structure.");
//                    // we should never encounter this condition as we only create once per structure.
//                }catch (PermenentTraitException ex){
//                    log.error("For some reason the system attempted to create an already created Object Trait. We caught a PermenentTraitException when trying to save an ObjectReference for new Structure.");
//                    // these are only system level objects, cannot modify but all of these should never have duplicates
//                }
//            }
        }
    }

    @Override
    public void addTraitToStructure(String structureId, String fieldName, Trait newTrait) throws IOException {
        Optional<Structure> optional = getById(structureId.toLowerCase());
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        checkFieldNameFormat(fieldName);

        if(structure.isPublished()){
            if(structure.getTraits().containsKey(fieldName)){
                throw new IllegalStateException("Field Name '"+fieldName+"' is already used, you cannot modify a published schema - only add to it.");
            }
        }

        structure.getTraits().put(fieldName, newTrait);
        // version type field
        structure.setUpdated(System.currentTimeMillis());
        structureElasticRepository.save(structure);

        if(structure.isPublished()){
            String mapping = "{ \"properties\": { \""+fieldName+"\": "+newTrait.getEsSchema()+" } }";
            PutMappingRequest putMappingRequest = new PutMappingRequest(structure.getItemIndex());
            putMappingRequest.source(mapping, XContentType.JSON);
            highLevelClient.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
        }
    }

    @Override
    public void insertTraitBeforeAnotherForStructure(String structureId, String movingTraitName, String insertBeforeTraitName) throws IOException {
        Optional<Structure> optional = getById(structureId.toLowerCase());
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        Trait movingTrait = null;
        String movingTraitFieldName = null;
        String insertBeforeTraitFieldName = null;
        for(Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()){
            if(traitEntry.getKey().equals(movingTraitName)){
                movingTrait = traitEntry.getValue();
                movingTraitFieldName = traitEntry.getKey();
            }else if(traitEntry.getKey().equals(insertBeforeTraitName)){
                insertBeforeTraitFieldName = traitEntry.getKey();
            }
        }
        if(movingTrait == null){
            throw new IllegalStateException("ID for the 'Trait to Move' was not found in this structure '${structure.getName()}'");
        }else if(insertBeforeTraitFieldName == null){
            throw new IllegalStateException("ID for the 'Insert Before Trait' was not found in this structure '${structure.getName()}'");
        }

        structure.getTraits().remove(movingTraitFieldName);

        LinkedHashMap<String, Trait> traits = new LinkedHashMap<>();
        for(Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()){
            if(traitEntry.getKey().equals(insertBeforeTraitName)){
                traits.put(movingTraitFieldName, movingTrait);
            }
            traits.put(traitEntry.getKey(), traitEntry.getValue());
        }

        structure.setTraits(traits);
        // version type field
        structure.setUpdated(System.currentTimeMillis());

        structureElasticRepository.save(structure);
    }

    @Override
    public void insertTraitAfterAnotherForStructure(String structureId, String movingTraitName, String insertAfterTraitName) throws IOException {
        Optional<Structure> optional = getById(structureId.toLowerCase());
        //noinspection OptionalGetWithoutIsPresent
        Structure structure = optional.get();// will throw null pointer/element not available

        Trait movingTrait = null;
        String movingTraitFieldName = null;
        String insertAfterTraitFieldName = null;
        for(Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()){
            if(traitEntry.getKey().equals(movingTraitName)){
                movingTrait = traitEntry.getValue();
                movingTraitFieldName = traitEntry.getKey();
            }else if(traitEntry.getKey().equals(insertAfterTraitName)){
                insertAfterTraitFieldName = traitEntry.getKey();
            }
        }
        if(movingTrait == null){
            throw new IllegalStateException("ID for the 'Trait to Move' was not found in this structure '${structure.getName()}'");
        }else if(insertAfterTraitFieldName == null){
            throw new IllegalStateException("ID for the 'Insert After Trait' was not found in this structure '${structure.getName()}'");
        }

        structure.getTraits().remove(movingTraitFieldName);

        LinkedHashMap<String, Trait> traits = new LinkedHashMap<>();
        for(Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()){
            traits.put(traitEntry.getKey(), traitEntry.getValue());
            if(traitEntry.getKey().equals(insertAfterTraitName)){
                traits.put(movingTraitFieldName, movingTrait);
            }
        }

        structure.setTraits(traits);
        // version type field
        structure.setUpdated(System.currentTimeMillis());

        structureElasticRepository.save(structure);

    }

    @Override
    public String getJsonSchema(String structureId) throws IOException{
        Structure structure = getById(structureId).get();
        return getJsonSchema(structure);
    }

    @Override
    public String getElasticSearchBaseMapping(String structureId) throws IOException{
        Structure structure = getById(structureId).get();
        return getElasticSearchBaseMapping(structure);
    }

    private long count(String indexName) throws IOException {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery("deleted", false));

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(boolQueryBuilder);
        builder.size(0);
        SearchRequest request = new SearchRequest(indexName);
        request.source(builder);
        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

        return response.getHits().getTotalHits().value;
    }

    static void checkFieldNameFormat(String fieldName){
        if(fieldName.contains("-")
                || fieldName.contains("+")
                || fieldName.contains(".")
                || fieldName.contains("..")
                || fieldName.contains("\\")
                || fieldName.contains("/")
                || fieldName.contains("*")
                || fieldName.contains("?")
                || fieldName.contains("\"")
                || fieldName.contains("<")
                || fieldName.contains(">")
                || fieldName.contains("|")
                || fieldName.contains(" ")
                || fieldName.contains(",")
                || fieldName.contains("#")
                || fieldName.contains(":")
                || fieldName.contains(";")
                || fieldName.getBytes().length > 255){
            throw new IllegalStateException("Field Name is not in correct format, \ncannot contain - + . .. \\ / * ? \" < > | , # : ; \ncannot contain a space or be longer than 255 bytes");
        }
    }

    private Structures getStructures(SearchSourceBuilder builder) throws IOException {
        SearchResponse response = highLevelClient.search(new SearchRequest("structure").source(builder), RequestOptions.DEFAULT);
        LinkedList<StructureHolder> holderList = new LinkedList<>();
        for(SearchHit hit : response.getHits()){
            Structure structure = EsHighLevelClientUtil.getTypeFromBytesReference(hit.getSourceRef(), Structure.class);
            LinkedList<TraitHolder> traits = new LinkedList<>();
            int index = 0;
            for(Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()){
                traits.add(new TraitHolder(index, traitEntry.getKey(), traitEntry.getValue()));
                index++;
            }
            holderList.add(new StructureHolder(structure, traits));
        }
        return new Structures(holderList, response.getHits().getTotalHits().value);
    }
}
