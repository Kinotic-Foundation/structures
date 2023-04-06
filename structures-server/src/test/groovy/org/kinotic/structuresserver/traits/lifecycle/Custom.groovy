package org.kinotic.structuresserver.traits.lifecycle

import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.kinotic.structures.api.domain.Structure
import org.kinotic.structures.api.domain.TypeCheckMap
import org.kinotic.structures.api.domain.traitlifecycle.HasOnBeforeModify
import org.kinotic.structures.api.domain.traitlifecycle.HasOnBeforeSearch
import org.springframework.stereotype.Component

@Component
class Custom implements HasOnBeforeModify, HasOnBeforeSearch {

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
}
