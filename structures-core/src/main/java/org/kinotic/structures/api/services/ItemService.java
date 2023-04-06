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

import org.elasticsearch.search.SearchHits;
import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.structures.api.domain.NotFoundException;
import org.kinotic.structures.api.domain.TypeCheckMap;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Publish
public interface ItemService {

    TypeCheckMap upsertItem(String structureId, TypeCheckMap item, Map<String, Object> context) throws Exception;

    long count(String structureId, Map<String, Object> context) throws Exception;

    Optional<TypeCheckMap> getItemById(String structureId, String id, Map<String, Object> context) throws Exception;

    SearchHits searchForItemsById(String structureId, Map<String, Object> context, String... ids) throws Exception;

    SearchHits getAll(String structureId, int numberPerPage, int from, Map<String, Object> context) throws Exception;

    SearchHits searchTerms(String structureId, int numberPerPage, int from, String fieldName, Map<String, Object> context, Object... searchTerms) throws Exception;

    SearchHits searchFullText(String structureId, int numberPerPage, int from, String search, Map<String, Object> context, String... fieldNames) throws Exception;

    SearchHits search(String structureId, String search, int numberPerPage, int from, Map<String, Object> context) throws Exception;

    SearchHits searchWithSort(String structureId, String search, int numberPerPage, int from, String sortField, boolean descending, Map<String, Object> context) throws Exception;

    void delete(String structureId, String itemId, Map<String, Object> context) throws Exception;

    void requestBulkUpdatesForStructure(String structureId) throws IOException, NotFoundException;

    void pushItemForBulkUpdate(String structureId, TypeCheckMap item, Map<String, Object> context) throws Exception;

    void flushAndCloseBulkUpdate(String structureId) throws Exception;

}
