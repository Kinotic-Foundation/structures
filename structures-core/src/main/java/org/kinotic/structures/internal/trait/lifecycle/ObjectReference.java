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

package org.kinotic.structures.internal.trait.lifecycle;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.kinotic.structures.api.domain.ReferenceLog;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.domain.TypeCheckMap;
import org.kinotic.structures.api.domain.traitlifecycle.HasOnAfterGet;
import org.kinotic.structures.api.domain.traitlifecycle.HasOnAfterModify;
import org.kinotic.structures.api.domain.traitlifecycle.HasOnBeforeModify;
import org.kinotic.structures.api.services.ItemService;
import org.kinotic.structures.internal.repositories.ReferenceLogElasticRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * We use a particular syntax for describing the relationships between different items.
 * <p>
 * Pattern is...
 * <p>
 * { "structureId":  { "type": "keyword" }, "structureName":  { "type": "keyword" }, "id":  { "type": "keyword" } }
 * <p>
 * This gives us everything we could possibly want to query and analyze the relationship.
 */
@Component
public class ObjectReference implements HasOnBeforeModify, HasOnAfterModify, HasOnAfterGet {

    private final ItemService itemService;
    private final ReferenceLogElasticRepository referenceLogElasticRepository;

    public ObjectReference(@Lazy ItemService itemService, ReferenceLogElasticRepository referenceLogElasticRepository){
        this.itemService = itemService;
        this.referenceLogElasticRepository = referenceLogElasticRepository;
    }

    @Override
    public TypeCheckMap beforeModify(TypeCheckMap obj, Structure structure, String fieldName) throws Exception {
        return manageSave(obj, structure, fieldName);
    }

    @Override
    public TypeCheckMap afterModify(TypeCheckMap obj, Structure structure, String fieldName) throws Exception {
        return manageReferenceLog(obj, structure, fieldName);
    }

    @Override
    public TypeCheckMap afterGet(TypeCheckMap obj, Structure structure, String fieldName) throws Exception {
        Trait fieldTrait = structure.getTraits().get(fieldName);
        if (fieldTrait.getName().contains("Reference ")) {
            // we need to fill up the field with the current version of the referenced object
            TypeCheckMap field = obj.getTypeCheckMap(fieldName);

            if (field.has("structureId") && field.has("id")) {
                SearchHits hits = itemService.searchForItemsById(field.getString("structureId"), field.getString("id"));
                for (SearchHit hit : hits) {
                    if (hit.getId().equals(field.getString("id"))) {
                        obj.amend(fieldName, new TypeCheckMap(hit.getSourceAsMap()));
                        break;
                    }
                }
            }
        }
        return obj;
    }

    public static TypeCheckMap manageSave(TypeCheckMap obj, Structure structure, final String fieldName) throws Exception {
        Trait fieldTrait = structure.getTraits().get(fieldName);
        if (fieldTrait.getName().contains("Reference ")) {
            // we need to fill up the field with the current version of the referenced object
            TypeCheckMap field = obj.getTypeCheckMap(fieldName);

            if (field.has("structureId") && field.has("id")) {
                // name is defaulted to ObjectReference.StructureName.StructureId
                String referenceId = fieldTrait.getName().replace("Reference ", "");

                if (field.getString("structureId").equals(structure.getId())) {
                    throw new IllegalArgumentException("Can not use items of the same structure. Can only use items with Structure Id '"+referenceId+"' for field '"+fieldName+".");
                }

                if(!field.getString("structureId").equals(referenceId)){
                    throw new IllegalArgumentException("Must use items of Structure Id '"+referenceId+"' for field '"+fieldName+"' only, please update and try again.");
                }

                TypeCheckMap reference = new TypeCheckMap();
                reference.amend("structureId", field.getString("structureId"));
                reference.amend("id", field.getString("id"));

                obj.amend(fieldName, reference);
            } else {
                if (fieldTrait.isRequired()) {
                    throw new IllegalStateException("Field \'" + fieldName + "\' requires a value.");
                }
                obj.amend(fieldName, null);
            }
        }

        return obj;
    }

    public TypeCheckMap manageReferenceLog(TypeCheckMap obj, Structure structure, String fieldName) throws Exception {
        Trait fieldTrait = structure.getTraits().get(fieldName);
        if (fieldTrait.getName().contains("Reference ") && obj.has(fieldName)) {
            // we need to fill up the field with the current version of the referenced object
            TypeCheckMap field = obj.getTypeCheckMap(fieldName);

            if (field.has("structureId") && field.has("id")) {
                Optional<ReferenceLog> logOpt = referenceLogElasticRepository.findByOwnerIdAndOwnerStructureId(obj.getString("id"), structure.getId().toLowerCase());
                ReferenceLog log = null;
                if (logOpt.isEmpty()) {
                    log = new ReferenceLog();
                    log.setOwnerStructureId(structure.getId().toLowerCase());
                    log.setOwnerId(obj.getString("id"));
                } else {
                    log = logOpt.get();
                }
                if (!log.getReferences().contains(field.getString("structureId").toLowerCase() + "_" + field.getString("id"))) {
                    log.getReferences().add(field.getString("structureId").toLowerCase() + "_" + field.getString("id"));
                    referenceLogElasticRepository.save(log);
                }
            }
        }

        return obj;
    }

}
