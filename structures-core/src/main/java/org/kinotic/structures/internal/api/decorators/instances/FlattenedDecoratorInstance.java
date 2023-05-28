package org.kinotic.structures.internal.api.decorators.instances;

import co.elastic.clients.elasticsearch._types.mapping.FlattenedProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.structures.api.decorators.FlattenedDecorator;
import org.kinotic.structures.api.decorators.runtime.ElasticMappingPreProcessor;
import org.kinotic.structures.api.decorators.runtime.MappingContext;
import org.kinotic.structures.api.domain.Structure;

/**
 * Created by Navíd Mitchell 🤪 on 5/26/23.
 */
public class FlattenedDecoratorInstance implements ElasticMappingPreProcessor<FlattenedDecorator, ObjectC3Type> {

    @Override
    public Class<FlattenedDecorator> implementsDecorator() {
        return FlattenedDecorator.class;
    }

    @Override
    public Class<ObjectC3Type> supportC3Type() {
        return ObjectC3Type.class;
    }

    @Override
    public Property process(Structure structure,
                            String fieldName,
                            FlattenedDecorator decorator,
                            ObjectC3Type type,
                            MappingContext<Property> context) {

        return FlattenedProperty.of(f -> f.depthLimit(decorator.getDepthLimit())
                                          .index(decorator.isIndex()))
                                ._toProperty();
    }
}
