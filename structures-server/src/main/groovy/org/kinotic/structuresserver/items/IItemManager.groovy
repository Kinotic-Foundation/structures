package org.kinotic.structuresserver.items

import org.elasticsearch.search.SearchHits
import org.kinotic.continuum.api.annotations.Publish
import org.kinotic.continuum.api.annotations.Version
import org.kinotic.structures.api.domain.Structure
import org.kinotic.structures.api.domain.TypeCheckMap

@Publish
@Version("1.0.0")
interface IItemManager {

    TypeCheckMap upsertItem(String structureId, TypeCheckMap item) throws Exception

    long count(String structureId)

    Optional<TypeCheckMap> getById(Structure structure, String id) throws Exception

    Optional<TypeCheckMap> getItemById(String structureId, String id) throws Exception

    SearchHits searchForItemsById(String structureId, String... ids)

    SearchHits getAll(String structureId, int numberPerPage, int from)

    SearchHits searchTerms(String structureId, int numberPerPage, int from, String fieldName, Object... searchTerms)

    SearchHits searchFullText(String structureId, int numberPerPage, int from, String search, String... fieldNames)

    SearchHits search(String structureId, String search, int numberPerPage, int from)

    SearchHits searchWithSort(String structureId, String search, int numberPerPage, int from, String sortField, boolean descending)

    void delete(String structureId, String itemId) throws Exception
}