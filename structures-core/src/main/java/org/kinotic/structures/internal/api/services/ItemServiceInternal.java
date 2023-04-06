package org.kinotic.structures.internal.api.services;

import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.domain.TraitFunction;
import org.kinotic.structures.api.domain.TypeCheckMap;
import org.kinotic.structures.api.domain.traitlifecycle.TraitLifecycle;
import org.kinotic.structures.api.services.ItemService;

import java.util.HashMap;
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

    HashMap<String, TraitLifecycle> getTraitLifecycleMap();

    /**
     *
     * Function will process the Lifecycle Hooks for a given structure.  If any of the hooks throws an exception we try to
     * catch the cause and rethrow to make it more evident what the issue is.
     *
     */
    default Object processLifecycle(Object obj, Structure structure, Map<String, Object> context, TraitFunction<TraitLifecycle, Object, String, Object> process) throws Exception {
        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
            if (getTraitLifecycleMap().containsKey(traitEntry.getValue().getName())) {
                TraitLifecycle toExecute = getTraitLifecycleMap().get(traitEntry.getValue().getName());
                obj = process.apply(toExecute, obj, traitEntry.getKey(), context);
            }//else if(traitEntry.getValue().getName().contains("Reference ")){
//                TraitLifecycle toExecute = getTraitLifecycleMap().get("ObjectReference");
//                obj = process.apply(toExecute, obj, traitEntry.getKey());
//            }
        }

        return obj;
    }

}
