package org.kinotic.structuresserver.items

import org.elasticsearch.search.SearchHits
import org.elasticsearch.search.sort.SortOrder
import org.kinotic.structures.api.domain.Structure
import org.kinotic.structures.api.domain.TypeCheckMap
import org.kinotic.structures.api.services.ItemService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ItemManager implements IItemManager {

    private static final Logger log = LoggerFactory.getLogger(ItemManager.class)

    @Autowired
    ItemService itemService

    @Override
    TypeCheckMap upsertItem(String structureId, TypeCheckMap item) throws Exception {
        itemService.upsertItem(structureId, item)
    }

    @Override
    long count(String structureId) {
        itemService.count(structureId)
    }

    @Override
    Optional<TypeCheckMap> getById(Structure structure, String id) throws Exception {
        itemService.getById(structure, id)
    }

    @Override
    Optional<TypeCheckMap> getItemById(String structureId, String id) throws Exception {
        itemService.getItemById(structureId, id)
    }

    @Override
    SearchHits searchForItemsById(String structureId, String... ids) {
        itemService.searchForItemsById(structureId, ids)
    }

    @Override
    SearchHits getAll(String structureId, int numberPerPage, int from) {
        itemService.getAll(structureId, numberPerPage, from)
    }

    @Override
    SearchHits searchTerms(String structureId, int numberPerPage, int from, String fieldName, Object... searchTerms) {
        itemService.searchTerms(structureId, numberPerPage, from, fieldName, searchTerms)
    }

    @Override
    SearchHits searchFullText(String structureId, int numberPerPage, int from, String search, String... fieldNames) {
        itemService.searchFullText(structureId, numberPerPage, from, search, fieldNames)
    }

    @Override
    SearchHits search(String structureId, String search, int numberPerPage, int from) {
        itemService.search(structureId, search, numberPerPage, from)
    }

    @Override
    SearchHits searchWithSort(String structureId, String search, int numberPerPage, int from, String sortField, boolean descending) {
        log.debug("search text: "+search)
        itemService.search(structureId, search, numberPerPage, from, sortField, descending ? SortOrder.DESC : SortOrder.ASC)
    }

    @Override
    void delete(String structureId, String itemId) throws Exception {
        itemService.delete(structureId, itemId)
    }
}
