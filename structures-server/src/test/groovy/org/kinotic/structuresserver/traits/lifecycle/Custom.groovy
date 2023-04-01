package org.kinotic.structuresserver.traits.lifecycle

import org.kinotic.structures.api.domain.Structure
import org.kinotic.structures.api.domain.TypeCheckMap
import org.kinotic.structures.api.domain.traitlifecycle.HasOnBeforeModify
import org.springframework.stereotype.Component

@Component
class Custom implements HasOnBeforeModify {

    @Override
    TypeCheckMap beforeModify(TypeCheckMap obj, Structure structure, String fieldName) throws Exception {
        if(!obj.has("custom")){
            obj.amend("custom", "custom")
        }
        obj
    }

}
