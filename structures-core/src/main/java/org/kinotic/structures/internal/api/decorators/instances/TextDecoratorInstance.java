package org.kinotic.structures.internal.api.decorators.instances;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import org.kinotic.continuum.idl.api.schema.StringC3Type;
import org.kinotic.structures.api.decorators.TextDecorator;
import org.kinotic.structures.api.decorators.runtime.MappingContext;
import org.kinotic.structures.api.decorators.runtime.ElasticMappingPreProcessor;
import org.kinotic.structures.api.domain.Structure;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Component
public class TextDecoratorInstance implements ElasticMappingPreProcessor<TextDecorator> {

    @Override
    public Class<TextDecorator> implementsDecorator() {
        return TextDecorator.class;
    }

    @Override
    public String decoratorTypeName() {
        return "Text";
    }

    @Override
    public Property process(Structure structure, String fieldName, TextDecorator decorator, MappingContext<Property> context) {
        if(!(context.value() instanceof StringC3Type)){
            throw new IllegalStateException("Text Decorator can only be used on StringC3Type");
        }
        return TextProperty.of(f -> f)._toProperty();
    }
}
