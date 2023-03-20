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

package org.kinotic.structures.api.services;

import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.PermenentTraitException;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface StructureService {


    Structure save(Structure structure) throws AlreadyExistsException;

    Optional<Structure> getStructureById(String id) throws IOException;

    SearchHits getAll(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    SearchHits getAllIdLike(String idLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    SearchHits getAllPublishedAndIdLike(String idLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    SearchHits getAllPublished(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    void delete(String structureId) throws IOException, PermenentTraitException;

    void publish(String structureId) throws IOException;

    void addTraitToStructure(String structureId, String fieldName, Trait newTrait) throws IOException;

    void insertTraitBeforeAnotherForStructure(String structureId, String movingTraitName, String insertBeforeTraitName) throws IOException;

    void insertTraitAfterAnotherForStructure(String structureId, String movingTraitName, String insertAfterTraitName) throws IOException;


    default String getJsonSchema(Structure structure) {
        StringBuilder ret = new StringBuilder();
        StringBuilder properties = new StringBuilder();
        StringBuilder requires = new StringBuilder();
        StringBuilder systemManaged = new StringBuilder();
        for(Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()){
            if(!traitEntry.getValue().isOperational()){// operational traits never get added to schema
                if(properties.length() > 0){
                    properties.append(",");
                }
                properties.append("\"").append(traitEntry.getKey()).append("\":").append(traitEntry.getValue().getSchema());
                if(traitEntry.getValue().isRequired()){
                    if(requires.length() == 0){
                        requires.append("[");
                    }else{
                        requires.append(",");
                    }
                    requires.append("\"").append(traitEntry.getKey()).append("\"");
                }
                if(traitEntry.getValue().isSystemManaged()){
                    if(systemManaged.length() == 0){
                        systemManaged.append("[");
                    }else{
                        systemManaged.append(",");
                    }
                    systemManaged.append("\"").append(traitEntry.getKey()).append("\"");
                }
            }
        }
        properties.append("}");// end properties

        ret.append("{\"$schema\": \"http://json-schema.org/draft-07/schema#\",\"type\": \"object\",\"structure\": \""+structure.getId()+"\",\"properties\": {");
        ret.append(properties);
        if(requires.length() > 0){
            requires.append("]");
            ret.append(",\"required\":");
            ret.append(requires);
        }else{
            ret.append(",\"required\":[]");
        }
        if(systemManaged.length() > 0){
            systemManaged.append("]");
            ret.append(",\"systemManaged\":");
            ret.append(systemManaged);
        }else{
            ret.append(",\"systemManaged\":[]");
        }
        ret.append("}");// end schema

        return ret.toString();
    }

    default String getElasticSearchBaseMapping(Structure structure) {
        StringBuilder ret = new StringBuilder();
        StringBuilder properties = new StringBuilder();
        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
            if (!traitEntry.getValue().isOperational()) {// operational traits never get added to schema
                if (properties.length() == 0) {
                    properties.append("\"properties\": {");
                } else {
                    properties.append(",");
                }
                properties.append("\"").append(traitEntry.getKey()).append("\":").append(traitEntry.getValue().getEsSchema());
            }
        }
        properties.append("}");// end properties

        ret.append("{ \"dynamic\": \"strict\", ");// start object
        ret.append(properties);
        ret.append("}");// end object
        return ret.toString();
    }

}
