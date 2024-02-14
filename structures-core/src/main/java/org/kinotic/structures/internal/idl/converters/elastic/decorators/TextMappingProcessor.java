package org.kinotic.structures.internal.idl.converters.elastic.decorators;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.StringC3Type;
import org.kinotic.structures.api.decorators.TextDecorator;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.common.MappingContext;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConversionState;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Component
public class TextMappingProcessor implements ElasticDecoratorMappingProcessor<TextDecorator> {

    @Override
    public Class<TextDecorator> implementsDecorator() {
        return TextDecorator.class;
    }

    @Override
    public boolean supportC3Type(C3Type c3Type) {
        return StringC3Type.class.isAssignableFrom(c3Type.getClass());
    }

    @Override
    public Property process(Structure structure,
                            String fieldName,
                            TextDecorator decorator,
                            C3Type type,
                            MappingContext<Property, ElasticConversionState> context) {

        return TextProperty.of(f -> f)._toProperty();
    }
}
