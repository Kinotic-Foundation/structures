package org.kinotic.structures.internal.api.services;

import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.TypeCheckMap;
import org.kinotic.structures.api.services.ItemService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Navíd Mitchell 🤪 on 3/30/23.
 */
public interface ItemServiceInternal extends ItemService {

    Optional<TypeCheckMap> getById(Structure structure, String id, Map<String, Object> context) throws Exception;

    SearchHits search(String structureId, String search, int numberPerPage, int from, String sortField, SortOrder sortOrder, Map<String, Object> context) throws Exception;

    List<String> searchDistinct(String structureId, String search, String field, int limit, Map<String, Object> context) throws Exception;

}
