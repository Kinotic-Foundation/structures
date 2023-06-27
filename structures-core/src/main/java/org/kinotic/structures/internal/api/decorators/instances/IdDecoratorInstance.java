package org.kinotic.structures.internal.api.decorators.instances;

import org.kinotic.structures.api.decorators.IdDecorator;
import org.kinotic.structures.api.decorators.runtime.crud.UpsertFieldPreProcessor;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.Structure;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
@Component
public class IdDecoratorInstance implements UpsertFieldPreProcessor<IdDecorator, String, String> {

    @Override
    public Class<IdDecorator> implementsDecorator() {
        return IdDecorator.class;
    }

    @Override
    public Class<String> supportsFieldType() {
        return String.class;
    }

    @Override
    public String process(Structure structure, String fieldName, IdDecorator decorator, String fieldValue, EntityContext context) {
        if(fieldValue == null || fieldValue.isBlank()){
            throw new IllegalArgumentException("Id field cannot be null or blank");
        }
        return fieldValue;
    }
}