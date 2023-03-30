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
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.TypeCheckMap;

import java.io.IOException;
import java.util.Optional;

@Publish
public interface ItemService {

    TypeCheckMap upsertItem(String structureId, TypeCheckMap item) throws Exception;

    long count(String structureId) throws IOException;

    Optional<TypeCheckMap> getById(Structure structure, String id) throws Exception;

    Optional<TypeCheckMap> getItemById(String structureId, String id) throws Exception;

    SearchHits searchForItemsById(String structureId, String... ids) throws IOException;

    SearchHits getAll(String structureId, int numberPerPage, int from) throws IOException;

    SearchHits searchTerms(String structureId, int numberPerPage, int from, String fieldName, Object... searchTerms) throws IOException;

    SearchHits searchFullText(String structureId, int numberPerPage, int from, String search, String... fieldNames) throws IOException;

    SearchHits search(String structureId, String search, int numberPerPage, int from) throws IOException;

    SearchHits searchWithSort(String structureId, String search, int numberPerPage, int from, String sortField, boolean descending) throws IOException;

    void delete(String structureId, String itemId) throws Exception;


}
