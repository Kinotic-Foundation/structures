package org.kinotic.structures.internal.api.decorators.instances;

import co.elastic.clients.elasticsearch._types.mapping.FlattenedProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.UnionC3Type;
import org.kinotic.structures.api.decorators.FlattenedDecorator;
import org.kinotic.structures.api.decorators.runtime.mapping.ElasticMappingPreProcessor;
import org.kinotic.structures.api.decorators.runtime.mapping.MappingContext;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConversionState;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 5/26/23.
 */
@Component
public class FlattenedDecoratorInstance implements ElasticMappingPreProcessor<FlattenedDecorator> {

    @Override
    public Class<FlattenedDecorator> implementsDecorator() {
        return FlattenedDecorator.class;
    }

    @Override
    public boolean supportC3Type(C3Type c3Type) {
        boolean ret = false;
        if(c3Type instanceof ObjectC3Type || c3Type instanceof UnionC3Type){
            ret = true;
        }
        return ret;
    }

    @Override
    public Property process(Structure structure,
                            String fieldName,
                            FlattenedDecorator decorator,
                            C3Type type,
                            MappingContext<Property, ElasticConversionState> context) {

        return FlattenedProperty.of(f -> f.depthLimit(decorator.getDepthLimit())
                                          .index(decorator.isIndex()))
                                ._toProperty();
    }
}
