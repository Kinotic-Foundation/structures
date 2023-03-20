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

import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.domain.TriFunction;
import org.kinotic.structures.api.domain.TypeCheckMap;
import org.kinotic.structures.api.domain.traitlifecycle.TraitLifecycle;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ItemService {
    TypeCheckMap upsertItem(String structureId, TypeCheckMap item) throws Exception;

    void requestBulkUpdatesForStructure(Structure structure);
    void pushItemForBulkUpdate(Structure structure, TypeCheckMap item) throws Exception;
    void flushAndCloseBulkUpdate(Structure structure) throws Exception;

    long count(String structureId) throws IOException;

    Optional<TypeCheckMap> getById(Structure structure, String id) throws Exception;

    Optional<TypeCheckMap> getItemById(String structureId, String id) throws Exception;

    SearchHits searchForItemsById(String structureId, String... ids) throws IOException;

    SearchHits getAll(String structureId, int numberPerPage, int from) throws IOException;

    SearchHits searchTerms(String structureId, int numberPerPage, int from, String fieldName, Object... searchTerms) throws IOException;

    SearchHits searchFullText(String structureId, int numberPerPage, int from, String search, String... fieldNames) throws IOException;

    SearchHits search(String structureId, String search, int numberPerPage, int from) throws IOException;

    SearchHits search(String structureId, String search, int numberPerPage, int from, String sortField, SortOrder sortOrder) throws IOException;

    List<String> searchDistinct(String structureId, String search, String field, int limit) throws IOException;

    void delete(String structureId, String itemId) throws Exception;

    HashMap<String, TraitLifecycle> getTraitLifecycleMap();

    /**
     *
     * Function will process the Lifecycle Hooks for a given structure.  If any of the hooks throws an exception we try to
     * catch the cause and rethrow to make it more evident what the issue is.
     *
     */
    default Object processLifecycle(Object obj, Structure structure, TriFunction<TraitLifecycle, Object, String, Object> process) throws Exception {
        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
            if (getTraitLifecycleMap().containsKey(traitEntry.getValue().getName())) {
                TraitLifecycle toExecute = getTraitLifecycleMap().get(traitEntry.getValue().getName());
                obj = process.apply(toExecute, obj, traitEntry.getKey());
            }//else if(traitEntry.getValue().getName().contains("Reference ")){
//                TraitLifecycle toExecute = getTraitLifecycleMap().get("ObjectReference");
//                obj = process.apply(toExecute, obj, traitEntry.getKey());
//            }
        }

        return obj;
    }
}
