package org.kinotic.structures.internal.api.services;

import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.kinotic.structures.api.domain.*;
import org.kinotic.structures.api.domain.traitlifecycle.TraitLifecycle;
import org.kinotic.structures.api.services.ItemService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 3/30/23.
 */
public interface ItemServiceInternal extends ItemService {

    void requestBulkUpdatesForStructure(String structureId) throws IOException, NotFoundException;

    void pushItemForBulkUpdate(String structureId, TypeCheckMap item) throws Exception;

    void flushAndCloseBulkUpdate(String structureId) throws Exception;

    SearchHits search(String structureId, String search, int numberPerPage, int from, String sortField, SortOrder sortOrder) throws IOException;

    List<String> searchDistinct(String structureId, String search, String field, int limit) throws IOException;

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
