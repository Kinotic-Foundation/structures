package org.kinotic.structuresserver.traits.lifecycle

import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.kinotic.structures.api.domain.Structure
import org.kinotic.structures.api.domain.TypeCheckMap
import org.kinotic.structures.api.domain.traitlifecycle.HasOnAfterGet
import org.kinotic.structures.api.domain.traitlifecycle.HasOnBeforeModify
import org.kinotic.structures.api.domain.traitlifecycle.HasOnBeforeSearch
import org.springframework.stereotype.Component

@Component
class Custom implements HasOnBeforeModify, HasOnBeforeSearch, HasOnAfterGet {

    @Override
    TypeCheckMap beforeModify(TypeCheckMap obj, Structure structure, String fieldName, Map<String, Object> context) throws Exception {
        if(!obj.has("custom")){
            obj.amend("custom", context.get("custom") as String)
        }
        obj
    }

    @Override
    BoolQueryBuilder beforeSearch(BoolQueryBuilder builder, Structure structure, String fieldName, Map<String, Object> context) throws Exception {
        // filter all documents out that has value of custom
        builder.filter(QueryBuilders.termQuery("custom", context.get("custom") as String))
    }

    @Override
    TypeCheckMap afterGet(TypeCheckMap obj, Structure structure, String fieldName, Map<String, Object> context) throws Exception {
        String customValue = context.get("custom") as String
        if(obj.getString("custom") != customValue){
            obj = null // caller shouldn't have access to this record - we can throw error or just return null
        }
        return obj
    }
}
