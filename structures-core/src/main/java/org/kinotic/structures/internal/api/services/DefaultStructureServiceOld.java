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

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import org.kinotic.structures.internal.config.StructuresProperties;

import javax.annotation.PostConstruct;

public class DefaultStructureServiceOld {

    private final ElasticsearchAsyncClient esAsyncClient;
    private final StructuresProperties structuresProperties;

    public DefaultStructureServiceOld(ElasticsearchAsyncClient esAsyncClient,
                                      StructuresProperties structuresProperties) {
        this.esAsyncClient = esAsyncClient;
        this.structuresProperties = structuresProperties;
    }

    @PostConstruct
    void init(){
//        try {
//
//            // need to make sure we have created the trait index before booting.
//            traitService.createTraitIndex();
//
//            /**
//             * These are system type traits that cannot be modified or deleted, default fields for all types/personalities.
//             */
//
//            Optional<Trait> idOptional = traitService.getTraitByName("Id");
//            if(idOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Id");
//                temp.setDescribeTrait("String field that defines the ID given to it in ElasticSearch.");
//                temp.setSchema("{ \"type\": \"string\" }");
//                temp.setEsSchema("{ \"type\": \"keyword\" }");
//                this.id = traitService.save(temp);
//            }else{
//                this.id = idOptional.get();
//            }
//            Optional<Trait> deletedOptional = traitService.getTraitByName("Deleted");
//            if(deletedOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Deleted");
//                temp.setDescribeTrait("Boolean field that says if an item is deleted or not.");
//                temp.setSchema("{ \"type\": \"boolean\" }");
//                temp.setEsSchema("{ \"type\": \"boolean\" }");
//                temp.setRequired(true);
//                temp.setSystemManaged(true);
//                this.deleted = traitService.save(temp);
//            }else{
//                this.deleted = deletedOptional.get();
//            }
//            Optional<Trait> deletedTimeOptional = traitService.getTraitByName("DeletedTime");
//            if(deletedTimeOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("DeletedTime");
//                temp.setDescribeTrait("A long field that indicates the timestamp of when the item was deleted.");
//                temp.setSchema("{ \"type\": \"date\", \"format\": { \"style\": \"unix\" } }");
//                temp.setEsSchema("{ \"type\": \"date\", \"format\": \"epoch_millis\" }");
//                temp.setRequired(false);
//                temp.setSystemManaged(true);
//                this.deletedTime = traitService.save(temp);
//            }else{
//                this.deletedTime = deletedTimeOptional.get();
//            }
//            Optional<Trait> updatedTimeOptional = traitService.getTraitByName("UpdatedTime");
//            if(updatedTimeOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("UpdatedTime");
//                temp.setDescribeTrait("A long field that indicates the timestamp of when the item was last updated.");
//                temp.setSchema("{ \"type\": \"date\", \"format\": { \"style\": \"unix\" } }");
//                temp.setEsSchema("{ \"type\": \"date\", \"format\": \"epoch_millis\" }");
//                temp.setRequired(true);
//                temp.setSystemManaged(true);
//                this.updatedTime = traitService.save(temp);
//            }else{
//                this.updatedTime = updatedTimeOptional.get();
//            }
//            Optional<Trait> structureIdOptional = traitService.getTraitByName("StructureId");
//            if(structureIdOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("StructureId");
//                temp.setDescribeTrait("String field that provides the Structure ID that the item belongs to.");
//                temp.setSchema("{ \"type\": \"string\" }");
//                temp.setEsSchema("{ \"type\": \"keyword\" }");
//                temp.setRequired(true);
//                temp.setSystemManaged(true);
//                this.structureId = traitService.save(temp);
//            }else{
//                this.structureId = structureIdOptional.get();
//            }
//
//            /**
//             * Below are system traits that do not make it to every Structure, but can be added to any structure.
//             * You cannot modify these traits at any point in time nor can you change the actual values after setting.
//             */
//
//            Optional<Trait> macOptional = traitService.getTraitByName("Mac");
//            if(macOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Mac");
//                temp.setDescribeTrait("Hardware Mac address (unique key) that is associated with primary ethernet on LAN.");
//                temp.setSchema("{ \"type\": \"string\", \"minLength\": 12, \"maxLength\": 12, \"pattern\": \"^[0-9A-Fa-f]{12}$\" }");
//                temp.setEsSchema("{ \"type\": \"keyword\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//
//            /**
//             * Below are generic fields that provide some quick access. They can be modified within the Structure frontend.
//             */
//
//            Optional<Trait> createdTimeOptional = traitService.getTraitByName("CreatedTime");
//            if(createdTimeOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("CreatedTime");
//                temp.setDescribeTrait("A long field that indicates the timestamp of when the item was created.");
//                temp.setSchema("{ \"type\": \"date\", \"format\": { \"style\": \"unix\" } }");
//                temp.setEsSchema("{ \"type\": \"date\", \"format\": \"epoch_millis\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//            Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
//            if(ipOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Ip");
//                temp.setDescribeTrait("IP address that the devices should be provided on the LAN.");
//                temp.setSchema("{ \"type\": \"string\", \"format\": \"ipv4\" }");
//                temp.setEsSchema("{ \"type\": \"ip\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//            Optional<Trait> keywordStringOptional = traitService.getTraitByName("KeywordString");
//            if(keywordStringOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("KeywordString");
//                temp.setDescribeTrait("Generic String that is structured content such as email addresses, hostnames, status codes, zip codes or tags.");
//                temp.setSchema("{ \"type\": \"string\" }");
//                temp.setEsSchema("{ \"type\": \"keyword\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//            Optional<Trait> geoPointOptional = traitService.getTraitByName("GeoPoint");
//            if(geoPointOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("GeoPoint");
//                temp.setDescribeTrait("References a geo point type. Please see https://www.elastic.co/guide/en/elasticsearch/reference/current/geo-point.html");
//                temp.setSchema("{\"type\": \"object\", \"required\": [\"lat\",\"lon\"],\"properties\": {\"lat\": {type\": \"number\"},\"lon\": {\"type\": \"number\"}}}");
//                temp.setEsSchema("{ \"type\": \"geo_point\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//            Optional<Trait> textStringOptional = traitService.getTraitByName("TextString");
//            if(textStringOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("TextString");
//                temp.setDescribeTrait("Generic String that is full-text values, such as the body of an email or the description of a product.");
//                temp.setSchema("{ \"type\": \"string\", \"description\": \"no-sort\" }");
//                temp.setEsSchema("{ \"type\": \"text\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//            Optional<Trait> dateOptional = traitService.getTraitByName("Date-EpochMillis");
//            if(dateOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Date-EpochMillis");
//                temp.setDescribeTrait("Generic Date field that is configured for search as time using Epoch time in milliseconds.");
//                temp.setSchema("{ \"type\": \"date\", \"format\": { \"style\": \"unix\" } }");
//                temp.setEsSchema("{ \"type\": \"date\", \"format\": \"epoch_millis\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//            Optional<Trait> longOptional = traitService.getTraitByName("Long");
//            if(longOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Long");
//                temp.setDescribeTrait("Generic Long field.");
//                temp.setSchema("{ \"type\": \"number\", \"minimum\": "+Long.MIN_VALUE+", \"maximum\": "+Long.MAX_VALUE+" }");
//                temp.setEsSchema("{ \"type\": \"long\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//            Optional<Trait> integerOptional = traitService.getTraitByName("Integer");
//            if(integerOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Integer");
//                temp.setDescribeTrait("Generic Integer field.");
//                temp.setSchema("{ \"type\": \"number\", \"minimum\": "+Integer.MIN_VALUE+", \"maximum\": "+Integer.MAX_VALUE+" }");
//                temp.setEsSchema("{ \"type\": \"integer\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//            Optional<Trait> shortOptional = traitService.getTraitByName("Short");
//            if(shortOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Short");
//                temp.setDescribeTrait("Generic Short field.");
//                temp.setSchema("{ \"type\": \"number\", \"minimum\": "+Short.MIN_VALUE+", \"maximum\": "+Short.MAX_VALUE+" }");
//                temp.setEsSchema("{ \"type\": \"short\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//            Optional<Trait> doubleOptional = traitService.getTraitByName("Double");
//            if(doubleOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Double");
//                temp.setDescribeTrait("Generic Double field.");
//                temp.setSchema("{ \"type\": \"number\", \"minimum\": "+Double.MIN_VALUE+", \"maximum\": "+Double.MAX_VALUE+" }");
//                temp.setEsSchema("{ \"type\": \"double\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//            Optional<Trait> booleanOptional = traitService.getTraitByName("Boolean");
//            if(booleanOptional.isEmpty()){
//                Trait temp = new Trait();
//                temp.setName("Boolean");
//                temp.setDescribeTrait("Generic Boolean field.");
//                temp.setSchema("{ \"type\": \"boolean\" }");
//                temp.setEsSchema("{ \"type\": \"boolean\" }");
//                temp.setRequired(true);
//                traitService.save(temp);
//            }
//        }catch (Exception e){
//            log.error("StructureService encountered an error trying to load Traits.", e);
//        }
    }

}
