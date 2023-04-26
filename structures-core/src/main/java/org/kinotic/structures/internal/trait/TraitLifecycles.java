package org.kinotic.structures.internal.trait;

import org.kinotic.structures.api.domain.traitlifecycle.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
public class TraitLifecycles {

    private final List<HasOnAfterDelete> afterDeleteLifecycles;
    private final List<HasOnAfterGet> afterGetLifecycles;
    private final List<HasOnAfterModify> afterModifyLifecycles;
    private final List<HasOnBeforeDelete> beforeDeleteLifecycles;
    private final List<HasOnBeforeModify> beforeModifyLifecycles;
    private final List<HasOnBeforeSearch> beforeSearchLifecycles;

    private final HashMap<String, HasOnAfterDelete> afterDeleteLifecycleMap = new HashMap<>();
    private final HashMap<String, HasOnAfterGet> afterGetLifecycleMap = new HashMap<>();
    private final HashMap<String, HasOnAfterModify> afterModifyLifecycleMap = new HashMap<>();
    private final HashMap<String, HasOnBeforeDelete> beforeDeleteLifecycleMap = new HashMap<>();
    private final HashMap<String, HasOnBeforeModify> beforeModifyLifecycleMap = new HashMap<>();
    private final HashMap<String, HasOnBeforeSearch> beforeSearchLifecycleMap = new HashMap<>();

    public TraitLifecycles(List<HasOnAfterDelete> afterDeleteLifecycles,
                           List<HasOnAfterGet> afterGetLifecycles,
                           List<HasOnAfterModify> afterModifyLifecycles,
                           List<HasOnBeforeDelete> beforeDeleteLifecycles,
                           List<HasOnBeforeModify> beforeModifyLifecycles,
                           List<HasOnBeforeSearch> beforeSearchLifecycles){
        this.afterDeleteLifecycles = afterDeleteLifecycles;
        this.afterGetLifecycles = afterGetLifecycles;
        this.afterModifyLifecycles = afterModifyLifecycles;
        this.beforeDeleteLifecycles = beforeDeleteLifecycles;
        this.beforeModifyLifecycles = beforeModifyLifecycles;
        this.beforeSearchLifecycles = beforeSearchLifecycles;
    }

    @PostConstruct
    public void init() {

        for (HasOnAfterDelete hook : afterDeleteLifecycles) {
            afterDeleteLifecycleMap.put(hook.getClass().getSimpleName(), hook);
        }

        for (HasOnAfterGet hook : afterGetLifecycles) {
            afterGetLifecycleMap.put(hook.getClass().getSimpleName(), hook);
        }

        for (HasOnAfterModify hook : afterModifyLifecycles) {
            afterModifyLifecycleMap.put(hook.getClass().getSimpleName(), hook);
        }

        for (HasOnBeforeDelete hook : beforeDeleteLifecycles) {
            beforeDeleteLifecycleMap.put(hook.getClass().getSimpleName(), hook);
        }

        for (HasOnBeforeModify hook : beforeModifyLifecycles) {
            beforeModifyLifecycleMap.put(hook.getClass().getSimpleName(), hook);
        }

        for (HasOnBeforeSearch hook : beforeSearchLifecycles) {
            beforeSearchLifecycleMap.put(hook.getClass().getSimpleName(), hook);
        }
    }

    /**
     *
     * Functions that will process the Lifecycle Hooks for a given structure.  If any of the hooks throws an exception we try to
     * catch the cause and rethrow to make it more evident what the issue is.
     *
     */

//    public TypeCheckMap processAfterDeleteLifecycle(TypeCheckMap obj, Structure structure, Map<String, Object> context) throws Exception {
//        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
//            if (getAfterDeleteLifecycleMap().containsKey(traitEntry.getValue().getName())) {
//                HasOnAfterDelete toExecute = getAfterDeleteLifecycleMap().get(traitEntry.getValue().getName());
//                obj = toExecute.afterDelete(obj, structure, traitEntry.getKey(), context);
//            }
//        }
//        return obj;
//    }
//    public TypeCheckMap processAfterGetLifecycle(TypeCheckMap obj, Structure structure, Map<String, Object> context) throws Exception {
//        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
//            if (getAfterGetLifecycleMap().containsKey(traitEntry.getValue().getName())) {
//                HasOnAfterGet toExecute = getAfterGetLifecycleMap().get(traitEntry.getValue().getName());
//                if(obj != null){
//                    obj = toExecute.afterGet(obj, structure, traitEntry.getKey(), context);
//                }else if(traitEntry.getValue().isOperational()){
//                    obj = toExecute.afterGet(null, structure, traitEntry.getKey(), context);
//                }
//            }
//        }
//        return obj;
//    }
//    public TypeCheckMap processAfterModifyLifecycle(TypeCheckMap obj, Structure structure, Map<String, Object> context) throws Exception {
//        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
//            if (getAfterModifyLifecycleMap().containsKey(traitEntry.getValue().getName())) {
//                HasOnAfterModify toExecute = getAfterModifyLifecycleMap().get(traitEntry.getValue().getName());
//                obj = toExecute.afterModify(obj ,structure, traitEntry.getKey(), context);
//            }
//        }
//        return obj;
//    }
//    public TypeCheckMap processBeforeDeleteLifecycle(TypeCheckMap obj, Structure structure, Map<String, Object> context) throws Exception {
//        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
//            if (getBeforeDeleteLifecycleMap().containsKey(traitEntry.getValue().getName())) {
//                HasOnBeforeDelete toExecute = getBeforeDeleteLifecycleMap().get(traitEntry.getValue().getName());
//                obj = toExecute.beforeDelete(obj, structure, traitEntry.getKey(), context);
//            }
//        }
//        return obj;
//    }
//    public TypeCheckMap processBeforeModifyLifecycle(TypeCheckMap obj, Structure structure, Map<String, Object> context) throws Exception {
//        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
//            if (getBeforeModifyLifecycleMap().containsKey(traitEntry.getValue().getName())) {
//                HasOnBeforeModify toExecute = getBeforeModifyLifecycleMap().get(traitEntry.getValue().getName());
//                obj = toExecute.beforeModify(obj, structure, traitEntry.getKey(), context);
//            }
//        }
//        return obj;
//    }
//    public BoolQueryBuilder processBeforeSearchLifecycle(BoolQueryBuilder boolQueryBuilder, Structure structure, Map<String, Object> context) throws Exception {
//        for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
//            if (getBeforeSearchLifecycleMap().containsKey(traitEntry.getValue().getName())) {
//                HasOnBeforeSearch toExecute = getBeforeSearchLifecycleMap().get(traitEntry.getValue().getName());
//                boolQueryBuilder = toExecute.beforeSearch(boolQueryBuilder, structure, traitEntry.getKey(), context);
//            }
//        }
//        return boolQueryBuilder;
//    }

    public HashMap<String, HasOnAfterDelete> getAfterDeleteLifecycleMap() {
        return afterDeleteLifecycleMap;
    }

    public HashMap<String, HasOnAfterGet> getAfterGetLifecycleMap() {
        return afterGetLifecycleMap;
    }

    public HashMap<String, HasOnAfterModify> getAfterModifyLifecycleMap() {
        return afterModifyLifecycleMap;
    }

    public HashMap<String, HasOnBeforeDelete> getBeforeDeleteLifecycleMap() {
        return beforeDeleteLifecycleMap;
    }

    public HashMap<String, HasOnBeforeModify> getBeforeModifyLifecycleMap() {
        return beforeModifyLifecycleMap;
    }

    public HashMap<String, HasOnBeforeSearch> getBeforeSearchLifecycleMap() {
        return beforeSearchLifecycleMap;
    }
}
