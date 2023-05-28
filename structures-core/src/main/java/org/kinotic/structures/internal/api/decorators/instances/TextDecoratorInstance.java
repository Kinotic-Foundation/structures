package org.kinotic.structures.internal.api.decorators.instances;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import org.kinotic.continuum.idl.api.schema.StringC3Type;
import org.kinotic.structures.api.decorators.TextDecorator;
import org.kinotic.structures.api.decorators.runtime.ElasticMappingPreProcessor;
import org.kinotic.structures.api.decorators.runtime.MappingContext;
import org.kinotic.structures.api.domain.Structure;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Component
public class TextDecoratorInstance implements ElasticMappingPreProcessor<TextDecorator, StringC3Type> {

    @Override
    public Class<TextDecorator> implementsDecorator() {
        return TextDecorator.class;
    }

    @Override
    public Property process(Structure structure,
                            String fieldName,
                            TextDecorator decorator,
                            StringC3Type type,
                            MappingContext<Property> context) {

        return TextProperty.of(f -> f)._toProperty();
    }
}
